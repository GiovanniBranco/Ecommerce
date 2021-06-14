package org.serratec.com.backend.ecommerce.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.serratec.com.backend.ecommerce.configs.MailConfig;
import org.serratec.com.backend.ecommerce.entities.CarrinhoEntity;
import org.serratec.com.backend.ecommerce.entities.ClienteEntity;
import org.serratec.com.backend.ecommerce.entities.PedidoEntity;
import org.serratec.com.backend.ecommerce.entities.dto.CadastroPedidoDto;
import org.serratec.com.backend.ecommerce.entities.dto.CarrinhoDto;
import org.serratec.com.backend.ecommerce.entities.dto.PedidoDto;
import org.serratec.com.backend.ecommerce.entities.dto.PedidoFinalizadoDto;
import org.serratec.com.backend.ecommerce.entities.dto.ProdutoDto;
import org.serratec.com.backend.ecommerce.entities.dto.ProdutosPedidosDto;
import org.serratec.com.backend.ecommerce.enums.StatusCompra;
import org.serratec.com.backend.ecommerce.exceptions.CarrinhoException;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.exceptions.PedidoException;
import org.serratec.com.backend.ecommerce.exceptions.ProdutoException;
import org.serratec.com.backend.ecommerce.mappers.PedidoMapper;
import org.serratec.com.backend.ecommerce.mappers.ProdutoMapper;
import org.serratec.com.backend.ecommerce.repositories.CarrinhoRepository;
import org.serratec.com.backend.ecommerce.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

	@Autowired
	PedidoRepository pedidoRepository;

	@Autowired
	CarrinhoRepository carrinhoRepository;

	@Autowired
	PedidoMapper pedidoMapper;

	@Autowired
	ProdutoMapper produtoMapper;

	@Autowired
	ClienteService clienteService;

	@Autowired
	ProdutoService produtoService;

	@Autowired
	CarrinhoService carrinhoService;

	@Autowired
	MailConfig mailConfig;

	public PedidoEntity findById(Long id) throws EntityNotFoundException {
		return pedidoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id + " não encontrado."));
	}

	public List<PedidoDto> getAll() {
		 return pedidoRepository.findAll().stream().map(pedidoMapper::toDto).collect(Collectors.toList());
		
	}

	public PedidoEntity findByNumeroPedido(String numeroPedido) {
		return pedidoRepository.findByNumeroPedido(numeroPedido);
	}

	public PedidoDto getByNumeroPedido(String numeroPedido) throws EntityNotFoundException, PedidoException {
		PedidoEntity pedido = pedidoRepository.findByNumeroPedido(numeroPedido);

		if (pedido == null) {
			throw new PedidoException("Pedido com número: " + numeroPedido + " nâo encontrado");
		}

		List<CarrinhoEntity> carrinho = pedido.getCarts();
		List<ProdutosPedidosDto> produtos = new ArrayList<>();

		for (CarrinhoEntity carrinhoEntity : carrinho) {
			produtos.add(produtoMapper.toProdutosPedidos(carrinhoEntity.getProdutos()));
		}

		PedidoDto pedidoDto = pedidoMapper.toDto(pedido);
		pedidoDto.setProduto(this.coletarProdutosCarrinho(pedido.getId()));

		return pedidoDto;
	}

	public List<PedidoEntity> getByCliente(ClienteEntity cliente) {
		return pedidoRepository.findByCliente(cliente);
	}

	public PedidoEntity create(PedidoDto pedidoDto) throws EntityNotFoundException {
		PedidoEntity pedidoEntity = pedidoMapper.toEntity(pedidoDto);
		pedidoEntity.setCliente(clienteService.findById(pedidoDto.getCliente()));

		pedidoDto.setStatus(StatusCompra.NAO_FINALIZADO);

		pedidoDto.setNumeroPedido(this.generateNumber());

		return pedidoRepository.save(pedidoMapper.toEntity(pedidoDto));

	}

	public void delete(Long id) throws EntityNotFoundException, PedidoException {
		if (this.findById(id) != null) {
			if (this.findById(id).getStatus().equals(StatusCompra.NAO_FINALIZADO)) {
				List<CarrinhoEntity> listaCarrinhoEntity = carrinhoRepository.findByPedidos(this.findById(id));
				for (CarrinhoEntity carrinhoEntity : listaCarrinhoEntity) {
					if (carrinhoEntity.getPedidos().getId().equals(id)) {
						carrinhoRepository.delete(carrinhoEntity);
					}
				}
				pedidoRepository.deleteById(id);
			} else {
				throw new PedidoException("Pedido com id: " + id + " já finalizado");
			}
		} else {
			throw new EntityNotFoundException("Pedido com id: " + id + "não encontrado!");
		}

	}

	private String generateNumber() {
		String numeroPedido = "";
		Random number = new Random();
		for (int i = 1; i <= 8; i++) {
			numeroPedido = numeroPedido + number.nextInt(9);
		}
		return numeroPedido;
	}

	public CadastroPedidoDto order(PedidoDto pedidoDto) throws EntityNotFoundException, ProdutoException {
		Long idPedido = this.create(pedidoDto).getId();

		List<ProdutoDto> listaProdutoDto = new ArrayList<>();
		List<CarrinhoDto> listaCarrinhoDto = new ArrayList<>();

		List<ProdutosPedidosDto> listaProdutosPedidosDto = pedidoDto.getProduto();

		for (ProdutosPedidosDto produtosPedidosDto : listaProdutosPedidosDto) {
			ProdutoDto dto = produtoService.getByName(produtosPedidosDto.getNome().toLowerCase());
			dto.setQuantidade(produtosPedidosDto.getQuantidade());

			listaProdutoDto.add(dto);
		}

		for (ProdutoDto produtoDto : listaProdutoDto) {
			CarrinhoDto carrinhoDto = new CarrinhoDto();
			carrinhoDto.setPreco(produtoDto.getPreco());

			carrinhoDto.setProduto(produtoService.findByName(produtoDto.getNome()).getId());

			carrinhoDto.setQuantidade(produtoDto.getQuantidade());
			carrinhoDto.setPedido(idPedido);
			listaCarrinhoDto.add(carrinhoDto);
		}

		carrinhoService.create(listaCarrinhoDto);

		PedidoEntity pedidoEntity = this.findById(idPedido);
		pedidoEntity.setValorTotal(carrinhoService.calcularTotal(idPedido));

		pedidoRepository.save(pedidoEntity);

		PedidoDto pedido = pedidoMapper.toDto(pedidoEntity);
		pedido.setProduto(listaProdutosPedidosDto);

		return pedidoMapper.toCadastroPedidoDto(pedido);
	}

	public PedidoDto adicionarProdutoPedido(String numeroPedido, List<ProdutosPedidosDto> listaProdutosPedidosDto)
			throws EntityNotFoundException, ProdutoException {

		List<CarrinhoDto> listaCarrinhoDto = new ArrayList<>();
		List<ProdutoDto> listaProdutoDto = new ArrayList<>();

		for (ProdutosPedidosDto produtosPedidosDto : listaProdutosPedidosDto) {
			ProdutoDto produtoDto = produtoService.getByName(produtosPedidosDto.getNome().toLowerCase());
			produtoDto.setQuantidade(produtosPedidosDto.getQuantidade());
			listaProdutoDto.add(produtoDto);
		}

		for (ProdutoDto produtoDto : listaProdutoDto) {
			CarrinhoDto carrinhoDto = new CarrinhoDto();
			carrinhoDto.setPedido(pedidoRepository.findByNumeroPedido(numeroPedido).getId());
			carrinhoDto.setQuantidade(produtoDto.getQuantidade());
			carrinhoDto.setProduto(produtoService.findByName(produtoDto.getNome()).getId());
			carrinhoDto.setPreco(produtoDto.getPreco());

			listaCarrinhoDto.add(carrinhoDto);
		}

		for (CarrinhoDto carrinhoDto : listaCarrinhoDto) {
			carrinhoService.adicionarProdutoNoCarrinho(carrinhoDto);
		}

		PedidoEntity pedidoEntity = pedidoRepository.findByNumeroPedido(numeroPedido);
		pedidoEntity.setValorTotal(carrinhoService.calcularTotal(pedidoEntity.getId()));

		pedidoRepository.save(pedidoEntity);

		PedidoDto pedidoDto = pedidoMapper.toDto(pedidoEntity);
		pedidoDto.setProduto(this.coletarProdutosCarrinho(pedidoEntity.getId()));

		return pedidoDto;
	}

	public PedidoDto alterarQuantidadeProdutoPedido(String numeroPedido,
			List<ProdutosPedidosDto> listaProdutosPedidosDto)
			throws EntityNotFoundException, ProdutoException, PedidoException {
		if (carrinhoRepository.findByPedidos(pedidoRepository.findByNumeroPedido(numeroPedido)).isEmpty()) {
			throw new PedidoException("Pedido com o número: " + numeroPedido + " não encontrado no carrinho");
		} else if (pedidoRepository.findByNumeroPedido(numeroPedido).getStatus().equals(StatusCompra.FINALIZADO)) {
			throw new PedidoException("Pedido com o número: " + numeroPedido + " já finalizado, favor verificar");
		} else {

			List<CarrinhoEntity> carrinho = carrinhoRepository
					.findByPedidos(pedidoRepository.findByNumeroPedido(numeroPedido));
			List<ProdutoDto> listaProdutoDto = new ArrayList<>();

			for (ProdutosPedidosDto produtosPedidosDto : listaProdutosPedidosDto) {
				ProdutoDto produtoDto = produtoService.getByName(produtosPedidosDto.getNome().toLowerCase());
				produtoDto.setQuantidade(produtosPedidosDto.getQuantidade());
				listaProdutoDto.add(produtoDto);
			}
			for (CarrinhoEntity entity : carrinho) {
				for (ProdutoDto produtoDto : listaProdutoDto) {
					if (produtoDto.getNome().equals(entity.getProdutos().getNome())) {
						entity.setQuantidade(produtoDto.getQuantidade() - entity.getQuantidade());
						carrinhoService.atualizarQuantidade(entity);
					}
				}
			}

			PedidoEntity pedidoEntity = pedidoRepository.findByNumeroPedido(numeroPedido);
			pedidoEntity.setValorTotal(carrinhoService.calcularTotal(pedidoEntity.getId()));

			pedidoRepository.save(pedidoEntity);

			PedidoDto pedidoDto = pedidoMapper.toDto(pedidoEntity);
			pedidoDto.setProduto(this.coletarProdutosCarrinho(pedidoEntity.getId()));

			return pedidoDto;
		}
	}

	public void devolverProdutosEstoque(String numeroPedido, List<ProdutoDto> produtos)
			throws EntityNotFoundException, ProdutoException {
		PedidoEntity pedidoEntity = pedidoRepository.findByNumeroPedido(numeroPedido);
		List<CarrinhoEntity> carrinho = carrinhoRepository.findByPedidos(pedidoEntity);

		for (CarrinhoEntity carrinhoEntity : carrinho) {
			produtoService.devolverEstoque(carrinhoEntity.getProdutos().getId(), carrinhoEntity.getQuantidade());
		}
	}

	public void devolverProdutosEstoque(String numeroPedido) throws EntityNotFoundException, ProdutoException {
		PedidoEntity pedidoEntity = pedidoRepository.findByNumeroPedido(numeroPedido);
		List<CarrinhoEntity> carrinho = carrinhoRepository.findByPedidos(pedidoEntity);

		for (CarrinhoEntity carrinhoEntity : carrinho) {
			produtoService.devolverEstoque(carrinhoEntity.getProdutos().getId(), carrinhoEntity.getQuantidade());
		}
	}

	public PedidoDto deletarProdutoOrder(String numeroPedido, List<ProdutosPedidosDto> listaProdutosPedidosDto)
			throws EntityNotFoundException, CarrinhoException, ProdutoException, PedidoException {

		PedidoEntity pedidoEntity = this.findByNumeroPedido(numeroPedido);

		if (pedidoEntity == null) {
			throw new PedidoException("Pedido com número: " + numeroPedido + " não encontrado.");
		} else if (this.getByNumeroPedido(numeroPedido).getStatus().equals(StatusCompra.FINALIZADO)) {
			throw new PedidoException(
					"Não foi possível remover os produto do pedido: " + numeroPedido + ", pedido já finalizado");
		} else {
			List<CarrinhoEntity> listaCarrinho = carrinhoRepository.findByPedidos(pedidoEntity);
			List<ProdutoDto> listaProdutoDto = new ArrayList<>();

			for (ProdutosPedidosDto produtosPedidosDto : listaProdutosPedidosDto) {
				listaProdutoDto.add(produtoService.getByName(produtosPedidosDto.getNome()));
			}

			for (CarrinhoEntity entity : listaCarrinho) {
				for (ProdutoDto produtoDto : listaProdutoDto) {
					if (produtoDto.getNome().equals(entity.getProdutos().getNome())) {
						this.devolverProdutosEstoque(numeroPedido, listaProdutoDto);
						pedidoEntity.setCarts(carrinhoService.removerProdutoCarrinho(entity));
					}
				}
			}

			List<CarrinhoEntity> listaCarrinhoAtualizada = carrinhoRepository.findByPedidos(pedidoEntity);

			if (listaCarrinhoAtualizada.isEmpty()) {
				this.delete(pedidoEntity.getId());
				throw new PedidoException("Carrinho zerado, favor realizar um novo pedido");
			}

			PedidoDto pedidoDto = new PedidoDto();

			for (CarrinhoEntity carrinhoEntity : listaCarrinhoAtualizada) {
				PedidoEntity pedidoAtualizado = carrinhoEntity.getPedidos();
				pedidoAtualizado.setValorTotal(carrinhoService.calcularTotal(carrinhoEntity.getPedidos().getId()));

				pedidoRepository.save(pedidoAtualizado);
				pedidoDto = pedidoMapper.toDto(pedidoAtualizado);
				pedidoDto.setProduto(this.coletarProdutosCarrinho(pedidoAtualizado.getId()));
			}

			return pedidoDto;
		}
	}

	public PedidoFinalizadoDto finalizarPedido(String numeroPedido) throws EntityNotFoundException, PedidoException {
		PedidoEntity pedidoEntity = pedidoRepository.findByNumeroPedido(numeroPedido);
		if (pedidoEntity == null) {
			throw new PedidoException("Pedido com número: " + numeroPedido + " não encontrado");
		} else if (!StatusCompra.FINALIZADO.equals(pedidoEntity.getStatus())) {
			List<ProdutosPedidosDto> listaProdutosPedidosDto = new ArrayList<>();
			List<CarrinhoEntity> listaCarrinhoEntity = carrinhoRepository.findByPedidos(pedidoEntity);

			for (CarrinhoEntity carrinhoEntity : listaCarrinhoEntity) {
				ProdutosPedidosDto produtosPedidosDto = produtoMapper
						.toProdutosPedidos(produtoService.findByName(carrinhoEntity.getProdutos().getNome()));
				produtosPedidosDto.setQuantidade(carrinhoEntity.getQuantidade());

				listaProdutosPedidosDto.add(produtosPedidosDto);
			}

			pedidoEntity.setDataPedido(LocalDate.now());
			pedidoEntity.setDataEntrega(LocalDate.now().plusDays(7));
			pedidoEntity.setStatus(StatusCompra.FINALIZADO);

			PedidoFinalizadoDto pedidoFinalizadoDto = pedidoMapper.toPedidoFinalizadoDto(pedidoEntity);

			String msg = "<!DOCTYPE html><html> <head> <meta charset=\"UTF-8\"/> <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"/> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/> <title>Nota Fiscal</title> <style>.container{background-color: #e6e8eb; border: thin, solid; margin: 0 auto; width: 50rem; height: 40rem; padding: 2rem; color: black;}</style> </head> <body> <div class=\"container\"> <div class=\"emitente\"> <h2>Emitente</h2> <hr/> <p>Razão Social</p><p>Spring Play Ecommerce de Jogos S.A.</p><p>CNPJ: 07.714.105/0002-07</p><p>Inscrição Estadual: 083078665</p><p>UF: RJ</p></div><div class=\"content-tomador\"> <h2>Destinatário</h2> <hr/> <p>Nome Cliente:{username}</p><p>Cpf:{cpf}</p></div><div class=\"dados-nfe\"> <h2>Dados Pedido</h2> <hr/> <p>Número Pedido:{numeroPedido}</p><p>Data de entrega:{dataEntrega}</p><p>Nome do produto:{nome}</p><p>Total do pedido:{valorTotal}</p></div></div></body></html>";
			String listaProdutos = "";

			int i = 1;
			for (ProdutosPedidosDto produto : listaProdutosPedidosDto) {

				if (listaProdutosPedidosDto.size() != i) {
					listaProdutos += produto.getNome() + " - ";
					listaProdutos += produto.getQuantidade() + ", ";
				} else {
					listaProdutos += produto.getNome() + " - ";
					listaProdutos += produto.getQuantidade() + ".";
				}
				i++;
			}

			msg = msg.replaceAll(Pattern.quote("{username}"), pedidoEntity.getCliente().getNome());
			msg = msg.replaceAll(Pattern.quote("{cpf}"), pedidoEntity.getCliente().getCpf());
			msg = msg.replaceAll(Pattern.quote("{numeroPedido}"), pedidoEntity.getNumeroPedido());
			msg = msg.replaceAll(Pattern.quote("{dataEntrega}"), pedidoEntity.getDataEntrega().toString());
			msg = msg.replaceAll(Pattern.quote("{nome}"), listaProdutos);
			msg = msg.replaceAll(Pattern.quote("{valorTotal}"), pedidoFinalizadoDto.getValorTotal().toString());

			mailConfig.sendMail(pedidoEntity.getCliente().getEmail(), "Pedido recebido com sucesso", msg);

			pedidoFinalizadoDto.setProduto(listaProdutosPedidosDto);

			pedidoRepository.save(pedidoEntity);
			return pedidoFinalizadoDto;
		} else {
			throw new PedidoException("Pedido já finalizado");
		}
	}

	public List<ProdutosPedidosDto> coletarProdutosCarrinho(Long pedidoId) throws EntityNotFoundException {
		List<CarrinhoEntity> carrinho = carrinhoRepository.findByPedidos(this.findById(pedidoId));
		List<ProdutosPedidosDto> produtos = new ArrayList<>();

		for (CarrinhoEntity carrinhoEntity : carrinho) {
			ProdutosPedidosDto dto = new ProdutosPedidosDto();
			dto.setNome(produtoService.getById(carrinhoEntity.getProdutos().getId()).getNome());
			dto.setQuantidade(carrinhoEntity.getQuantidade());

			produtos.add(dto);
		}

		return produtos;
	}

	public String cancelarPedido(String numeroPedido)
			throws EntityNotFoundException, ProdutoException, CarrinhoException, PedidoException {
		PedidoEntity pedido = this.findByNumeroPedido(numeroPedido);
		if(pedido == null) {
			throw new PedidoException("Pedido com número " + numeroPedido + " não encontrado");
		}
		
		if (pedido.getStatus().equals(StatusCompra.NAO_FINALIZADO)) {
			this.devolverProdutosEstoque(numeroPedido);

			PedidoEntity pedidoEntity = pedidoRepository.findByNumeroPedido(numeroPedido);
			List<CarrinhoEntity> entity = carrinhoRepository.findByPedidos(pedidoEntity);
			for (CarrinhoEntity carrinhoEntity : entity) {
				carrinhoRepository.deleteById(carrinhoEntity.getId());
			}
			pedidoRepository.delete(pedidoEntity);
			return "Pedido Cancelado com sucesso!";
		}
		else {
			return "Pedido já finalizado, favor verificar";
		}
	}
}

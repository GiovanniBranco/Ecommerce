package org.serratec.com.backend.ecommerce.configs;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@Configuration
public class MailConfig {

	@Autowired
	JavaMailSender javaMailSender;

	public String sendMail(String para, String assunto, String msg) {
		try {

			MimeMessage mail = javaMailSender.createMimeMessage();
			mail.setSubject(assunto);
			MimeMessageHelper helper = new MimeMessageHelper(mail, true);
			helper.setFrom("apirestful.backend@gmail.com");
			helper.setTo(para);
			helper.setText(msg, true);
			javaMailSender.send(mail);
			return "E-mail enviado com sucesso";
		} catch (MessagingException ex) {
			return null;
		}
	}

}

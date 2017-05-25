package hu.barcode.config;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * @author Ollé Csaba
 * @project Generate Barcode
 * @Created 18/05/2017
 *
 *          Configure mail server (SMTP Authentication) Properties:
 *          email.properties
 */
@Configuration
@ComponentScan(basePackages = "hu.barcode")
@PropertySource("classpath:email.properties")
public class MailConfig {

	@Autowired
	private Environment env;

	@Bean
	public JavaMailSender getMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		Properties mailProperties = new Properties();
		mailProperties.put("mail.smtp.auth", env.getProperty("mail.smtp.auth"));
		mailProperties.put("mail.smtp.starttls.enable", env.getProperty("mail.smtp.starttls"));
		mailProperties.put("mail.smtp.starttls.required", env.getProperty("mail.smtp.starttls"));
		mailSender.setJavaMailProperties(mailProperties);
		mailSender.setHost(env.getProperty("mail.host"));
		mailSender.setPort(Integer.parseInt(env.getProperty("mail.port")));
		mailSender.setProtocol(env.getProperty("mail.protocol"));
		mailSender.setUsername(env.getProperty("mail.username"));
		mailSender.setPassword(env.getProperty("mail.password"));
		return mailSender;
	}

}

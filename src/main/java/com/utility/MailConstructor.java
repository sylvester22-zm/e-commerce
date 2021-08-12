package com.utility;

import java.util.Locale;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.domain.User;



@Component
public class MailConstructor {
	@Autowired
	private Environment env;
	
	@Autowired
	private TemplateEngine templateEngine;
	@Autowired
	 private JavaMailSender mailSender;  
	
	public SimpleMailMessage constructResetTokenEmail(
			String contextPath, Locale locale, String token, User user, String password
			) {
		
		String url = contextPath + "/passwordReset?token="+token;
		String message = "\nPlease click on this link to change your   password. Your temprol generated password is: \n"+password;
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(user.getEmail());
		email.setSubject("oxen Initiative - Reset Password");
		email.setText(url+message);
		email.setFrom(env.getProperty("support.email"));
		return email;
		
	}
	
	public void setMailSender(JavaMailSender mailSender) {  
        this.mailSender = mailSender;  
    }  
   
    public SimpleMailMessage constructOrderConfirmationEmail(User user,  Locale locale) {  
              
    	Context context = new Context();
		//context.setVariable("order", order);
		context.setVariable("user", user);
		String text = templateEngine.process("orderConfirmationEmailTemplate", context);
		
		SimpleMailMessage messagePreparator = new SimpleMailMessage() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper email = new MimeMessageHelper(mimeMessage);
				email.setTo(user.getEmail());
				//email.setSubject("Order Confirmation - "+order.getId());
				email.setText(text, true);
				email.setFrom(new InternetAddress("sylvesterkalumbi12@gmail.com"));
			}
		};
        mailSender.send(messagePreparator);  
        return  messagePreparator;

    }  
}

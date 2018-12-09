package com.idms.notification.idmsnotification;

import java.io.IOException;
import java.util.Date;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

	@RequestMapping(value = "/sendemail")
	public String sendEmail(@RequestParam(value = "to") String to, @RequestParam(value = "subject") String subject, @RequestParam(value = "text") String text) throws AddressException, MessagingException, IOException {
		sendmail(to, subject, text);
		return "Email sent successfully";
	}
	
	
	
	private void sendmail(String to, String subject, String text) throws AddressException, MessagingException, IOException {
		   java.util.Properties props = new java.util.Properties();
		   props.put("mail.smtp.auth", "true");
		   props.put("mail.smtp.starttls.enable", "true");
		   props.put("mail.smtp.host", "smtp.gmail.com");
		   props.put("mail.smtp.port", "587");
		   props.put("mail.smtp.ssl.trust", "*");
		   
		   Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			   @Override
		      protected PasswordAuthentication getPasswordAuthentication() {
		         return new PasswordAuthentication("gemaltoprojects@gmail.com", "Gemalto123456");
		      }
		   });
		   Message msg = new MimeMessage(session);
		   msg.setFrom(new InternetAddress("gemaltoprojects@gemalto.com", false));

		   msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		   msg.setSubject(subject);
		   msg.setContent(text, "text/html");
		   msg.setSentDate(new Date());

		   MimeBodyPart messageBodyPart = new MimeBodyPart();
		   messageBodyPart.setContent("Tutorials point email", "text/html");

		   Multipart multipart = new MimeMultipart();
		   multipart.addBodyPart(messageBodyPart);
		   MimeBodyPart attachPart = new MimeBodyPart();

		   attachPart.attachFile("/var/tmp/image19.png");
		   multipart.addBodyPart(attachPart);
//		   msg.setContent(multipart);
		   Transport.send(msg);   
		}
}

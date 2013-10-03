package edu.unsw.comp9321.assign2.notifications;

import java.util.List;
import java.util.Properties;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.mail.javamail.MimeMessageHelper;

public class EmailService {

	private static final String USERNAME = "cs9321.ibuy@gmail.com";
	private static final String PASSWORD = "comp9321";
	
	public static final String EMAIL = "cs9321.ibuy@gmail.com";
	
	private Properties props;
	
	private static final EmailService instance = new EmailService();

	public static EmailService getInstance(){
		return instance;
	}
	
	private EmailService(){
		props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
	}

	public void sendEmail(Email email) throws MessagingException {
		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(
								USERNAME, PASSWORD);
					}
				});
		MimeMessage mimeMessage = new MimeMessage(session);
		// use the true flag to indicate you need a multipart message
		boolean hasAttachments = (email.getAttachments() != null && email
				.getAttachments().size() > 0);
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
				hasAttachments);
		helper.setTo(email.getTo());
		helper.setFrom(email.getFrom());
		helper.setSubject(email.getSubject());
		helper.setText(email.getText(), true);

		List<Attachment> attachments = email.getAttachments();
		if (attachments != null && attachments.size() > 0) {
			for (Attachment attachment : attachments) {
				String filename = attachment.getFilename();
				DataSource dataSource = new ByteArrayDataSource(
						attachment.getData(), attachment.getMimeType());
				if (attachment.isInline()) {
					helper.addInline(filename, dataSource);
				} else {
					helper.addAttachment(filename, dataSource);
				}
			}
		}
		Transport.send(mimeMessage);
	}
}
package de.pentasys.SilverPen.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public final class Email {

	Properties mailServerProperties;
	Session getMailSession;
	MimeMessage msg;
	Authenticator auth;

	private class SMTPAuthenticator extends Authenticator {
		private PasswordAuthentication authentication;

		public SMTPAuthenticator(String login, String password) {
			authentication = new PasswordAuthentication(login, password);
		}

		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return authentication;
		}
	}

	/**
	 * Sendet eine Registrierungsbestätigung an einen Nutzer
	 * 
	 * @param username
	 *            Name des Nutzers der sich registriert hat
	 * @param mailAdress
	 *            Mailadresse an die die Registrierungsbestätigung verschickt
	 *            werden soll
	 * @throws AddressException
	 * @throws MessagingException
	 * 
	 * 
	 */
	public void sendRegistrationConfirmationMail(String username, String mailAdress)
			throws AddressException, MessagingException {
		msg = new MimeMessage(getMailSession);
		try {
			msg.setText("Sie haben sich unter dem Nutzernamen: " + username + " erfolgreich bei SilverPen registriert");
			msg.setSubject("Nutzer " + username + " erfolgreich registriert");
			msg.setFrom(new InternetAddress("silverpentest@gmail.com"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(mailAdress));
			Transport.send(msg);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param mailHost
	 * @param port
	 * @param login
	 * @param pass
	 */
	public Email(String mailHost, int port, String login, String pass) {
		mailServerProperties = new Properties();
		mailServerProperties.setProperty("mail.host", mailHost);
		mailServerProperties.setProperty("mail.smtp.port", "" + port);
		mailServerProperties.setProperty("mail.smtp.auth", "true");
		mailServerProperties.setProperty("mail.smtp.starttls.enable", "true");
		auth = new SMTPAuthenticator(login, pass);
		getMailSession = Session.getInstance(mailServerProperties, auth);
	}
}

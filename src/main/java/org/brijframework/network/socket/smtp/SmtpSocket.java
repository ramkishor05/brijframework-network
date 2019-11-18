package org.brijframework.network.socket.smtp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import org.brijframework.network.beans.EmailBean;

public class SmtpSocket {

	private String host;
	private int port;
	private String userName;
	private String password;
	private boolean isTLSEnabled;
	private Properties properties;
	private HashMap<String, Object> otherProperties;

	public SmtpSocket() {
	}

	public SmtpSocket(String ip, int port, String userName, String password) {
		this(ip, port, userName, password, false);
	}

	public SmtpSocket(String ip, int port, String userName, String password, boolean isTLSEnabled) {
		this(ip, port, userName, password, isTLSEnabled, null);
	}

	public SmtpSocket(String host, int port, String userName, String password, boolean isTLSEnabled, HashMap<String, Object> otherProperties) {
		this.host = host;
		this.port = port;
		this.userName = userName;
		this.password = password;
		this.isTLSEnabled = isTLSEnabled;
		this.otherProperties = otherProperties;
		this.init();
	}

	public void setHost(String host) {
		this.host = host;
	}
	
	public String getHost() {
		return host;
	}

	public int getPort() {
		return this.port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public SmtpSocket init() {
		this.properties = new Properties(System.getProperties());
		this.properties.put("mail.smtp.starttls.enable", this.isTLSEnabled ? "true" : "false"); //starttls.enable flag true for using secure smpts.
		this.properties.put("mail.smtp.host", this.host);
		String auth = (this.userName != null && this.userName.length() > 0) ? "true" : "false";
		this.properties.put("mail.smtp.auth", auth);
		this.properties.put("mail.smtp.user", this.userName);
		this.properties.put("mail.smtp.connectiontimeout", String.valueOf(60 * 1000));
		this.properties.put("mail.smtp.timeout", String.valueOf(60 * 1000));
		if (this.otherProperties != null) {
			this.properties.putAll(this.otherProperties);
		}
		return this;
	}

	public boolean sendEmail(EmailBean emailObject) {
		EmailBean[] emails = { emailObject };
		ArrayList<EmailBean> emailsA = new ArrayList<EmailBean>();
		for (EmailBean emailBean : emails) {
			emailsA.add(emailBean);
		}
		return this.sendEmail(emailsA);
	}

	public boolean sendEmail(Iterable<EmailBean> emailObjectItr) {
		Transport transport = null;
		try {
			Session session = Session.getDefaultInstance(this.properties, null);
			transport = session.getTransport("smtp");
			transport.connect(this.host, this.port, this.userName, this.password);
			for (EmailBean emailObject : emailObjectItr) {
				try {
					Message message = new MimeMessage(session);
					emailObject.setMessage(message);
					transport.sendMessage(message, message.getAllRecipients());
				} catch (Exception exp) {
					throw new RuntimeException("Exception in sending email with subject: " + emailObject.getSubject() + " AND all further emails", exp);
				}
			}
			return true;
		} catch (Exception exp) {
			exp.printStackTrace();
			throw new RuntimeException("Exception in connecting to SMTP@ " + this.host + ":" + this.port, exp);
		} finally {
			try {
				transport.close();
			} catch (Exception ignored) {
			}
		}
	}

	public HashMap<String, Object> getOtherProperties() {
		return this.otherProperties;
	}

	public void setOtherProperties(HashMap<String, Object> otherProperties) {
		this.otherProperties = otherProperties;
	}
}

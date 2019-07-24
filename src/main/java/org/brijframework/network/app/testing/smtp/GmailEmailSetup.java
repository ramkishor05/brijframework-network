package org.brijframework.network.app.testing.smtp;

import java.util.Hashtable;

import org.brijframework.network.beans.EmailBean;
import org.brijframework.network.core.EmailAddress;
import org.brijframework.network.core.EmailGroup;
import org.brijframework.network.socket.smtp.SmtpSocket;

public class GmailEmailSetup {
	public static final String HOST="smtp.gmail.com";
	public static final String USER_NAME="ramkishor0509@gmail.com";
	public static final String PASSWORD="Ram_9450";
	public static final int PORT=587;
	public static final boolean isTLSEnabled=true;
	public void email(Hashtable<String, Object> attachmentHash){
		SmtpSocket smtp=new SmtpSocket(HOST, PORT, USER_NAME, PASSWORD,true);
		EmailGroup emailGroup=new EmailGroup();
		EmailAddress toEmail=new EmailAddress();
		toEmail.setName("Brij frame");
		toEmail.setEmailAddress("ramkishor0509@gmail.com");
		emailGroup.addToEmailAddress(toEmail);
		
		toEmail.setEmailAddress("ramkishor0509@gmail.com");
		emailGroup.addToEmailAddress(toEmail);
		toEmail.setEmailAddress("ramkishor0509@gmail.com");
		
		emailGroup.addToEmailAddress(toEmail);
		
		EmailBean email=new EmailBean();
		email.setSubject("Email Testing");
		email.setContentType("text/html");
		email.setBodyText("This is body");
		email.setEmailGroup(emailGroup);
		email.setFrom(toEmail);
		//email.addAttachment(attachmentHash);
		Boolean isSend=smtp.sendEmail(email);
		if(isSend)
		System.out.println("Email sent.");
		else
		System.out.println("Email fail");
	}
public static void main(String[] args) {
	GmailEmailSetup setup=new GmailEmailSetup();
	setup.email(new Hashtable<String,Object>());
}
}

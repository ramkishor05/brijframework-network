package org.brijframework.network.beans;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.brijframework.network.core.EmailAddress;
import org.brijframework.network.core.EmailGroup;
import org.brijframework.util.resouces.FileUtil;

public class EmailBean {

	private static final String MailsDir = "mail";
	private EmailAddress from;
	private String contentType = "text/plain";
	private String subject;
	private String bodyText;
	private EmailGroup emailGroup;
	private Vector<Hashtable<String, Object>> attachmentArray;
	private String fileName;
	private String fileNamePrefix;

	private File outgoingMailDir;
	private static int fileCounter = 0;
	private static Random random = new Random(); // this is used just in case multiple threads produce same file name

	public EmailAddress getFrom() {
		return this.from;
	}

	public void setFrom(EmailAddress from) {
		this.from = from;
	}

	public String getContentType() {
		return this.contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBodyText() {
		return this.bodyText;
	}

	public void setBodyText(String bodyText) {
		this.bodyText = bodyText;
	}

	public EmailGroup getEmailGroup() {
		return this.emailGroup;
	}

	public void setEmailGroup(EmailGroup emailGroup) {
		this.emailGroup = emailGroup;
	}

	public Vector<Hashtable<String, Object>> getAttachmentArray() {
		return this.attachmentArray;
	}

	public void setAttachmentArray(Vector<Hashtable<String, Object>> attachmentArray) {
		this.attachmentArray = attachmentArray;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileNamePrefix() {
		return this.fileNamePrefix;
	}

	public void setFileNamePrefix(String fileNamePrefix) {
		this.fileNamePrefix = fileNamePrefix;
	}

	/*
	 * {
	 *  fileName = "ABC.txt",
	 *  ispdfXML = true,
	 *  byteArray = 06dhjdk7dfjfdkj566509jdfkdfkjfkd"
	 * }
	 */
	public void addAttachment(Hashtable<String, Object> attachmentHash) {
		if (this.attachmentArray == null) {
			this.attachmentArray = new Vector<Hashtable<String, Object>>();
		}
		if (attachmentHash.get("ispdfXML") == null) {
			attachmentHash.put("ispdfXML", new Boolean(false));
		}
		if (this.isValidAttachmentHash(attachmentHash)) {
			this.attachmentArray.add(attachmentHash);
		} else {
		}
	}

	private boolean isValidAttachmentHash(Hashtable<String, Object> attachmentHash) {
		return attachmentHash.get("fileName") != null && attachmentHash.get("byteArray") != null;
	}

	public void addAttachment(String attachmentFileName, boolean isPdf, byte[] fileBytesArray) {
		Hashtable<String, Object> attachmentHash = new Hashtable<String, Object>();
		attachmentHash.put("fileName", attachmentFileName);
		attachmentHash.put("ispdfXML", new Boolean(isPdf));
		attachmentHash.put("byteArray", fileBytesArray);
		this.addAttachment(attachmentHash);
	}

	public void addAttachment(String attachmentFileName, boolean isPdf, File file) throws IOException {
		this.addAttachment(attachmentFileName, isPdf, FileUtil.loadByteFile(file));
	}

	public void addAttachment(String attachmentFileName, boolean isPdf, String filePath) throws IOException {
		this.addAttachment(attachmentFileName, isPdf, new File(filePath));
	}

	public Hashtable<String, Object> getEmailHash() {
		Hashtable<String, Object> emailHash = new Hashtable<String, Object>();
		emailHash.put("subject", this.subject);
		emailHash.put("bodyText", this.bodyText.toString());
		emailHash.putAll(this.emailGroup.getEmailGroupHash());
		emailHash.putAll(this.from.getAddressHash());
		emailHash.put("contentType", this.contentType);
		if (this.attachmentArray != null && this.attachmentArray.size() > 0) {
			emailHash.put("attachments", this.attachmentArray);
		}
		return emailHash;
	}

	public void setData(String contentType, String subject, String bodyText) {
		this.contentType = contentType;
		this.setContent(subject, bodyText);
	}

	public void setContent(String subject, String bodyText) {
		this.subject = subject;
		this.bodyText = bodyText;
	}

	public void setHeader(EmailAddress from, File outgoingMailDir) {
		this.from = from;
		this.outgoingMailDir = outgoingMailDir;
	}

	public void createEmailGroup(EmailGroup anotherEmailGroup) {
		this.emailGroup = new EmailGroup();
		if (anotherEmailGroup != null) {
			this.emailGroup.addEmailGroup(anotherEmailGroup);
		}
	}

	public void writeEmail() throws IOException {
		String calcFileName = "";
		if (this.fileName == null) {
			calcFileName = (this.fileNamePrefix == null ? "" : this.fileNamePrefix) + this.emailGroup.getNameStr() + "-" + random.nextInt(1000) + "-" + getNextFileCounter();
		} else {
			calcFileName = this.fileName;
		}
		if (calcFileName.length() > 40) {
			calcFileName = calcFileName.substring(0, 40);
		}
		File outGoingDir = this.outgoingMailDir == null ? new File(MailsDir) : this.outgoingMailDir;
		File outgoingMailFile = new File(outGoingDir, calcFileName + ".ser");
		if (outgoingMailFile.exists()) {
			outgoingMailFile.delete();
		}
		FileUtil.writeObject(outgoingMailFile, this.getEmailHash());
	}

	public void writeEmailAndLog() {
		try {
			this.writeEmail();
		} catch (Exception e) {
		}
	}

	public static int getNextFileCounter() {
		if (fileCounter > 99999999) {
			fileCounter = 0;
		}
		return fileCounter++;
	}

	public void setMessage(Message message) throws AddressException, MessagingException, IOException {
		message.setFrom(this.from.getInternetAddress());
		message.setSubject(this.subject);
		message.setContent(this.bodyText, this.contentType);
		message.setSentDate(new java.util.Date());
		this.emailGroup.setRecipients(message);
		this.setAttachments(message);
	}

	private void setAttachments(Message message) throws MessagingException, IOException {
		if (this.attachmentArray == null || this.attachmentArray.size() == 0) {
			return;
		}
		Multipart multipart = new MimeMultipart();
		BodyPart messageBodyPart = new MimeBodyPart();
		if (this.bodyText != null) {
			messageBodyPart.setContent(this.bodyText, this.contentType);
		}
		multipart.addBodyPart(messageBodyPart);
		for (Hashtable<String, Object> attachmentHash : this.attachmentArray) {
			String fileName = (String) attachmentHash.get("fileName");
			byte[] bytes = (byte[]) attachmentHash.get("byteArray");
			ByteArrayDataSource byteArrayDataSource = new ByteArrayDataSource(bytes, "");
			BodyPart attachmentBodyPart = new MimeBodyPart();
			attachmentBodyPart.setDataHandler(new DataHandler(byteArrayDataSource));
			attachmentBodyPart.setFileName(fileName);
			multipart.addBodyPart(attachmentBodyPart);
		}
		message.setContent(multipart);
	}
}

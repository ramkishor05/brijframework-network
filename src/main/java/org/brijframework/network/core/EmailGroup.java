package org.brijframework.network.core;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Vector;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.brijframework.util.text.StringUtil;

public class EmailGroup  {

	private String key;
	private boolean isReadOnly;
	private ArrayList<EmailAddress> toEmailAddressArray = new ArrayList<EmailAddress>();
	private ArrayList<EmailAddress> ccEmailAddressArray = new ArrayList<EmailAddress>();
	private ArrayList<EmailAddress> bccEmailAddressArray = new ArrayList<EmailAddress>();
	private HashSet<String> emailAddressSet = new HashSet<String>(); 
	private Hashtable<String, Vector<Hashtable<String, String>>> emailGroupHash;

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public ArrayList<EmailAddress> getToEmailAddressArray() {
		Collections.unmodifiableList(this.toEmailAddressArray);
		return this.toEmailAddressArray;
	}

	public void setToEmailAddressArray(ArrayList<EmailAddress> toEmailAddressArray) {
		this.throwExceptionIfReadOnly();
		this.toEmailAddressArray = toEmailAddressArray;
	}

	public ArrayList<EmailAddress> getCcEmailAddressArray() {
		Collections.unmodifiableList(this.ccEmailAddressArray);
		return this.ccEmailAddressArray;
	}

	public void setCcEmailAddressArray(ArrayList<EmailAddress> ccEmailAddressArray) {
		this.throwExceptionIfReadOnly();
		this.ccEmailAddressArray = ccEmailAddressArray;
	}

	public ArrayList<EmailAddress> getBccEmailAddressArray() {
		Collections.unmodifiableList(this.bccEmailAddressArray);
		return this.bccEmailAddressArray;
	}

	public void setBccEmailAddressArray(ArrayList<EmailAddress> bccEmailAddressArray) {
		this.throwExceptionIfReadOnly();
		this.bccEmailAddressArray = bccEmailAddressArray;
	}

	public void addToEmailAddress(EmailAddress emailAddress) {
		this.addEmailAddress(this.toEmailAddressArray, emailAddress);
	}

	public void addCcEmailAddress(EmailAddress emailAddress) {
		this.addEmailAddress(this.ccEmailAddressArray, emailAddress);
	}

	public void addBccEmailAddress(EmailAddress emailAddress) {
		this.addEmailAddress(this.bccEmailAddressArray, emailAddress);
	}

	private void addEmailAddress(ArrayList<EmailAddress> toArray, EmailAddress emailAddress) {
		this.throwExceptionIfReadOnly();
		if (!this.emailAddressSet.contains(emailAddress.getEmailAddress())) {
			toArray.add(emailAddress);
			this.emailAddressSet.add(emailAddress.getEmailAddress());
		}
	}

	public void addEmailGroup(EmailGroup anotherEmailGroup) {
		if (anotherEmailGroup.toEmailAddressArray != null && anotherEmailGroup.toEmailAddressArray.size() > 0) {
			this.addAllEmailAddresss(anotherEmailGroup.toEmailAddressArray, this.toEmailAddressArray);
		}
		if (anotherEmailGroup.ccEmailAddressArray != null && anotherEmailGroup.ccEmailAddressArray.size() > 0) {
			this.addAllEmailAddresss(anotherEmailGroup.ccEmailAddressArray, this.ccEmailAddressArray);
		}
		if (anotherEmailGroup.bccEmailAddressArray != null && anotherEmailGroup.bccEmailAddressArray.size() > 0) {
			this.addAllEmailAddresss(anotherEmailGroup.bccEmailAddressArray, this.bccEmailAddressArray);
		}
	}

	private void addAllEmailAddresss(ArrayList<EmailAddress> fromArray, ArrayList<EmailAddress> toArray) {
		this.throwExceptionIfReadOnly();
		for (EmailAddress emailAddress : fromArray) {
			this.addEmailAddress(toArray, emailAddress);
		}
	}

	public void addEmailAddress(EmailAddress emailAddress) {
		this.throwExceptionIfReadOnly();
		if (emailAddress.getRecepientType().equalsIgnoreCase("Bcc")) {
			this.addBccEmailAddress(emailAddress);
		} else if (emailAddress.getRecepientType().equalsIgnoreCase("Cc")) {
			this.addCcEmailAddress(emailAddress);
		} else {
			this.addToEmailAddress(emailAddress);
		}
	}

	private void throwExceptionIfReadOnly() {
		if (this.isReadOnly) {
			throw new RuntimeException("A read only Email group with key " + this.key + " and to csl " + this.getToCsl() + " has been modified");
		}
	}

	/*	public String toString() {
			StringBuffer strBuf = new StringBuffer();
			this.appendDetails(strBuf, "to", this.toEmailAddressArray);
			this.appendDetails(strBuf, "cc", this.ccEmailAddressArray);
			this.appendDetails(strBuf, "bcc", this.bccEmailAddressArray);
			return strBuf.toString();
		}
	*/
	/*	private void appendDetails(StringBuffer strBuf, String type, ArrayList<EmailAddress> emailAddressArray) {
			strBuf.append("\r\n\t" + type + " array " + FNCmcUtil.getCsl(emailAddressArray, "addressStr"));
			for (EmailAddress emailAddress : emailAddressArray) {
				strBuf.append(emailAddress + ", ");
			}
		}
	*/
	public Hashtable<String, Vector<Hashtable<String, String>>> getEmailGroupHash() {
		if (this.emailGroupHash != null) {
			return this.emailGroupHash;
		}
		this.emailGroupHash = new Hashtable<String, Vector<Hashtable<String, String>>>();
		this.populateRecipientList("recipientList", this.toEmailAddressArray);
		this.populateRecipientList("ccRecipientList", this.ccEmailAddressArray);
		this.populateRecipientList("bccRecipientList", this.bccEmailAddressArray);
		return this.emailGroupHash;
	}

	private void populateRecipientList(String key, ArrayList<EmailAddress> emailAddressArray) {
		if (emailAddressArray.size() > 0) {
			Vector<Hashtable<String, String>> recipientList = new Vector<Hashtable<String, String>>();
			for (EmailAddress emailAddress : emailAddressArray) {
				recipientList.add(emailAddress.getAddressHash());
			}
			this.emailGroupHash.put(key, recipientList);
		}
	}

	public String getToCsl() {
		return StringUtil.getCsl(this.toEmailAddressArray, "addressStr");
	}

	public String getNameStr() {
		return StringUtil.getDelimString(this.toEmailAddressArray, "~");
	}

	public boolean isReadOnly() {
		return this.isReadOnly;
	}

	public void setReadOnly(boolean isReadOnly) {
		this.isReadOnly = isReadOnly;
	}

	public void setRecipients(Message message) throws AddressException, UnsupportedEncodingException, MessagingException {
		for (EmailAddress emailAddress : this.toEmailAddressArray) {
			message.addRecipient(Message.RecipientType.TO, emailAddress.getInternetAddress());
		}
		for (EmailAddress emailAddress : this.ccEmailAddressArray) {
			message.addRecipient(Message.RecipientType.CC, emailAddress.getInternetAddress());
		}
		for (EmailAddress emailAddress : this.bccEmailAddressArray) {
			message.addRecipient(Message.RecipientType.BCC, emailAddress.getInternetAddress());
		}
	}
}

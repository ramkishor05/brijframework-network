package org.brijframework.network.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.brijframework.network.beans.EmailBean;

public class EmailSet {

	private static final String MailsDir = null;
	private Hashtable<String, String> codeKeyMap;
	private Hashtable<String, String> discreteEmailAddressMap;
	private ArrayList<EmailGroup> emailGroupArray = new ArrayList<EmailGroup>();
	private EmailAddress from;

	private final HashMap<String, EmailAddress> emailAddressMap = new HashMap<String, EmailAddress>();
	private final HashMap<String, EmailGroup> emailGroupMap = new HashMap<String, EmailGroup>();
	private final HashMap<String, EmailGroup> codeKeyEmailGroupMap = new HashMap<String, EmailGroup>();

	public Hashtable<String, String> getCodeKeyMap() {
		return this.codeKeyMap;
	}

	public void setCodeKeyMap(Hashtable<String, String> codeKeyMap) {
		this.codeKeyMap = codeKeyMap;
	}

	public Hashtable<String, String> getDiscreteEmailAddressMap() {
		return this.discreteEmailAddressMap;
	}

	public void setDiscreteEmailAddressMap(Hashtable<String, String> discreteEmailAddressMap) {
		this.discreteEmailAddressMap = discreteEmailAddressMap;
	}

	public ArrayList<EmailGroup> getEmailGroupArray() {
		return this.emailGroupArray;
	}

	public void setEmailGroupArray(ArrayList<EmailGroup> emailGroupArray) {
		this.emailGroupArray = emailGroupArray;
	}

	public HashMap<String, EmailAddress> getEmailAddressMap() {
		return this.emailAddressMap;
	}

	public HashMap<String, EmailGroup> getEmailGroupMap() {
		return this.emailGroupMap;
	}

	public EmailAddress getFrom() {
		return this.from;
	}

	public void setFrom(EmailAddress from) {
		this.from = from;
	}

	public void writePlainEmail(String codeKey, String subject, String bodyText) throws IOException {
		EmailBean emailObject = this.getPlainEmailObject(codeKey);
		if (emailObject != null) {
			emailObject.setContent(subject, bodyText);
			emailObject.writeEmail();
		}
	}

	public void writePlainEmailAndLog(String codeKey, String subject, String bodyText) {
		EmailBean emailObject = this.getPlainEmailObject(codeKey);
		if (emailObject != null) {
			emailObject.setContent(subject, bodyText);
			emailObject.writeEmailAndLog();
		}
	}

	/*	public void writePlainEmailAndLog(AppObject appObject, String codeKey, String subject, String bodyText) {
			EmailObject emailObject = this.getPlainEmailObject(codeKey);
			if (emailObject != null) {
				emailObject.writeEmailAndLog(appObject, subject, bodyText);
			}
		}
	*/
	public void writeHtmlEmail(String codeKey, String subject, String bodyText) throws IOException {
		EmailBean emailObject = this.getHtmlEmailObject(codeKey);
		if (emailObject != null) {
			emailObject.setContent(subject, bodyText);
			emailObject.writeEmail();
		}
	}

	public void writeHtmlEmailAndLog(String codeKey, String subject, String bodyText) {
		EmailBean emailObject = this.getHtmlEmailObject(codeKey);
		if (emailObject != null) {
			emailObject.setContent(subject, bodyText);
			emailObject.writeEmailAndLog();
		}
	}

	/*public void writeHtmlEmailAndLog(AppObject appObject, String codeKey, String subject, String bodyText) {
		EmailObject emailObject = this.getHtmlEmailObject(codeKey);
		if (emailObject != null) {
			emailObject.writeEmailAndLog(appObject, subject, bodyText);
		}
	}*/

	public EmailBean getPlainEmailObject() {
		return this.getEmailObject("text/plain", null);
	}

	public EmailBean getHtmlEmailObject() {
		return this.getEmailObject("text/html", null);
	}

	public EmailBean getPlainEmailObject(EmailGroup emailGroup) {
		return this.getEmailObject("text/plain", emailGroup);
	}

	public EmailBean getHtmlEmailObject(EmailGroup emailGroup) {
		return this.getEmailObject("text/html", emailGroup);
	}

	private EmailBean getEmailObject(String contentType, EmailGroup emailGroup) {
		EmailBean emailObject = new EmailBean();
		emailObject.setHeader(this.from, new File(MailsDir));
		emailObject.createEmailGroup(emailGroup);
		emailObject.setContentType(contentType);
		return emailObject;
	}

	public EmailBean getPlainEmailObject(String codeKey) {
		return this.getEmailObject("text/plain", this.getEmailGroup(codeKey));
	}

	public EmailBean getHtmlEmailObject(String codeKey) {
		return this.getEmailObject("text/html", this.getEmailGroup(codeKey));
	}

	private EmailGroup getEmailGroup(String codeKey) {
		EmailGroup emailGroup = this.codeKeyEmailGroupMap.get(codeKey);
		if (emailGroup != null) {
			return emailGroup;
		} else {
			System.out.println("********** Could Not find EmailGroup for codeKey " + codeKey);
			return null;
		}
	}

	public String toString() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("From: " + this.from + "\r\n");
		for (Map.Entry<String, EmailGroup> entry : this.codeKeyEmailGroupMap.entrySet()) {
			stringBuffer.append("Code key: " + entry.getKey() + " will be sent to " + entry.getValue() + "\r\n");
		}
		return stringBuffer.toString();
	}

	public void postInit() {
		this.initEmailObjects();
		this.initCodeKeyEmailGroups();
	}

	private void initEmailObjects() {
		if (this.from != null) {
			this.from.setRecepientType("From");
		}
		if (this.discreteEmailAddressMap != null) {
			for (Map.Entry<String, String> entry : this.discreteEmailAddressMap.entrySet()) {
				EmailAddress emailAddress = new EmailAddress(entry.getKey(), entry.getValue());
				this.emailAddressMap.put(emailAddress.getName(), emailAddress);
			}
		}
		if (this.emailGroupArray != null) {
			for (EmailGroup emailGroup : this.emailGroupArray) {
				emailGroup.setReadOnly(true);
				this.emailGroupMap.put(emailGroup.getKey(), emailGroup);
			}
		}
	}

	// add up all email groups and email objects to make a new email group
	private void initCodeKeyEmailGroups() {
		if (this.codeKeyMap == null) {
			return;
		}
		for (Map.Entry<String, String> entry : this.codeKeyMap.entrySet()) {
			String codeKey = entry.getKey();
			String[] emailKeys = entry.getValue().split(",");
			if (emailKeys.length == 1) {
				EmailAddress emailAddress = this.emailAddressMap.get(emailKeys[0].trim());
				if (emailAddress != null) {
					EmailGroup emailGroupForSingleEmailAddress = new EmailGroup();
					emailGroupForSingleEmailAddress.addEmailAddress(emailAddress);
					emailGroupForSingleEmailAddress.setReadOnly(true);
					this.codeKeyEmailGroupMap.put(codeKey, emailGroupForSingleEmailAddress);
				} else {
					EmailGroup emailGroup = this.emailGroupMap.get(emailKeys[0].trim());
					if (emailGroup != null) {
						this.codeKeyEmailGroupMap.put(codeKey, emailGroup);
					}
				}
			} else {
				EmailGroup emailGroup = new EmailGroup();
				for (String emailKey : emailKeys) {
					EmailAddress emailAddress = this.emailAddressMap.get(emailKey.trim());
					if (emailAddress != null) {
						emailGroup.addEmailAddress(emailAddress);
					} else {
						EmailGroup subEmailGroup = this.emailGroupMap.get(emailKey.trim());
						if (subEmailGroup != null) {
							emailGroup.addEmailGroup(subEmailGroup);
						}
					}
				}
				emailGroup.setReadOnly(true);
				this.codeKeyEmailGroupMap.put(codeKey, emailGroup);
			}
		}
	}

	/*	public static EmailSet getEmailSet(File file) {
			return (EmailSet) Serializer.store().loadSingleEntity(file, EmailSet.class, false);
		}
	*/
}
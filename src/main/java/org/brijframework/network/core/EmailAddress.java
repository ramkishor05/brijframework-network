package org.brijframework.network.core;

import java.io.UnsupportedEncodingException;
import java.util.Hashtable;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.brijframework.util.text.StringUtil;

public class EmailAddress {
	private String name;
	private String emailAddress;
	private String recepientType = "To";

	private Hashtable<String, String> addressHash;
	private String address;
	private String nameWithoutSpaces;
	private InternetAddress internetAddress;

	public EmailAddress() {
	}

	public EmailAddress(String name, String emailAddress) {
		this.name = name;
		this.setEmailAddress(emailAddress);
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailAddress() {
		return this.emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		int indexOfTilde = emailAddress.indexOf('~');
		if (indexOfTilde != -1) {
			this.emailAddress = emailAddress.substring(0, indexOfTilde);
			this.recepientType = emailAddress.substring(indexOfTilde + 1);
		} else {
			this.emailAddress = emailAddress;
		}
	}

	public String getRecepientType() {
		return this.recepientType;
	}

	public void setRecepientType(String recepientType) {
		this.recepientType = recepientType;
	}

	public String toString() {
		return this.getAddressStr();
	}

	public InternetAddress getInternetAddress() throws AddressException, UnsupportedEncodingException {
		if (this.internetAddress == null) {
			this.internetAddress = new InternetAddress(this.emailAddress, this.name);
		}
		return this.internetAddress;
	}

	public String getAddressStr() {
		if (this.address == null) {
			this.address = this.getCalcName() + "<" + this.emailAddress + ">";
		}
		return this.address;
	}

	public String getCalcName() {
		return this.name != null ? this.name : "";
	}

	public String getNameWithoutSpaces() {
		if (this.nameWithoutSpaces == null) {
			this.nameWithoutSpaces = this.getCalcName().replace(" ", "");
		}
		return this.nameWithoutSpaces;
	}

	public void postInit() {
		this.getAddressStr();
		this.getNameWithoutSpaces();
	}

	public Hashtable<String, String> getAddressHash() {
		if (this.addressHash == null) {
			this.addressHash = this.getHash();
		}
		return this.addressHash;
	}

	public Hashtable<String, String> getHash() {
		Hashtable<String, String> addressHash = new Hashtable<String, String>();
		if (this.recepientType != null && this.recepientType.equalsIgnoreCase("From")) {
			addressHash.put("fromName", (this.name != null ? this.name : ""));
			addressHash.put("fromEmailAddress", this.emailAddress);
		} else {
			addressHash.put("toName", (this.name != null ? this.name : ""));
			addressHash.put("toEmailAddress", this.emailAddress);
		}
		return addressHash;
	}
	@Override
	public boolean equals(Object object) {
		if (object != null && object instanceof EmailAddress) {
			EmailAddress anotherEmailObject = (EmailAddress) object;
			return this.emailAddress.equalsIgnoreCase(anotherEmailObject.emailAddress);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return StringUtil.isNonEmpty(this.emailAddress) ? this.emailAddress.hashCode() : super.hashCode();
	}
}

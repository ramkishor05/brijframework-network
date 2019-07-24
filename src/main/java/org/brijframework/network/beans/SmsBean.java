package org.brijframework.network.beans;

public class SmsBean {

	private String phoneNumber;
	private String message;

	public SmsBean() {

	}

	public SmsBean(String phoneNumber, String message) {
		this.phoneNumber = phoneNumber;
		this.message = message;
	}

	public String getPhoneNumber() {
		if (this.phoneNumber.startsWith("+")) {
			return this.phoneNumber;
		} else {
			return this.phoneNumber.length() == 10 ? ("+1" + this.phoneNumber) : ("+" + this.phoneNumber);
		}
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}

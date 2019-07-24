package org.brijframework.network.base;

import java.util.List;

public interface MessageSender {
	
	public void sendSms(List<MessageBase> messageArray);

	public void sendEmail(List<MessageBase> messageArray);

	public void sendEmailAndSms(List<MessageBase> messageArray);
}

package org.brijframework.network.base;

import java.util.Map;

import org.brijframework.network.template.MessageTemplate;

public interface MessageFormatter {
	
	public String emailMessage(Object template, Map<String, Object> paramMap);

	public String smsMessage(Object template, Map<String, Object> paramMap);

	public String pushNotifMessage(Object template, Map<String, Object> paramMap);

	public String altaMessage(Object template, Map<String, Object> paramMap);

	public String emailMessage(MessageTemplate template, Map<String, Object> paramMap);

	public String smsMessage(MessageTemplate template, Map<String, Object> paramMap);

	public String pushNotifMessage(MessageTemplate template, Map<String, Object> paramMap);
	
	public String altaMessage(MessageTemplate templ, Map<String, Object> paramMap);

}
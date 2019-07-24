package org.brijframework.network.formettor;

import java.util.Map;

import org.brijframework.network.base.MessageFormatter;
import org.brijframework.network.template.MessageTemplate;
import org.brijframework.network.util.MessageUtil;
public class TextMessageFormatter implements MessageFormatter {


	public String emailMessage(MessageTemplate templ, Map<String, Object> paramMap) {
		return MessageUtil.messageFormat(null, templ.messageBox, paramMap);
	}

	public String smsMessage(MessageTemplate templ, Map<String, Object> paramMap) {
		return MessageUtil.messageFormat(null, templ.messageBox, paramMap);
	}

	public String pushNotifMessage(MessageTemplate templ, Map<String, Object> paramMap) {
		return MessageUtil.messageFormat(null, templ.messageBox, paramMap);
	}
 
	@Override
	public String altaMessage(MessageTemplate templ, Map<String, Object> paramMap) {
		return MessageUtil.messageFormat(null, templ.messageBox, paramMap);
	}

	@Override
	public String emailMessage(Object template, Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String smsMessage(Object template, Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String pushNotifMessage(Object template, Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String altaMessage(Object template, Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return null;
	}
}

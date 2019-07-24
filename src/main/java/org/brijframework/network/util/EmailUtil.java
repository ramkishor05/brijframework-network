package org.brijframework.network.util;

import java.util.List;
import java.util.Map;

import org.brijframework.network.core.EmailAddress;
import org.brijframework.network.core.EmailGroup;


public class EmailUtil {
	/**
	 * return group of email addresses.
	 */
	public static EmailGroup emailGroup(List<Map<String, Object>> objectLst) {
		EmailGroup emailGroup = new EmailGroup();
		for(Map<String, Object> valueMap: objectLst){
		EmailAddress emailAddress = new EmailAddress();
		emailAddress.setEmailAddress((String) valueMap.get("emailID"));
		emailAddress.setName((String) valueMap.get("title"));
		emailGroup.addEmailAddress(emailAddress);
		}
		return emailGroup;
	}

}

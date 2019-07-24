package org.brijframework.network.template;

import java.util.Map;

import org.brijframework.util.text.StringUtil;

public class EmailTemplate {
	
	public String id;
	public String filePath;
	public String sourceContext;
	
	public EmailTemplate() {
		
	}
	
    public EmailTemplate(Map<String, Object> map) {
		
	}
	
	void loadTemplate(){
		if(StringUtil.isNonEmpty(filePath)){
			
		}
	}
	
}

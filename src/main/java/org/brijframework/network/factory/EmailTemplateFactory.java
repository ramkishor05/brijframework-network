package org.brijframework.network.factory;

import java.util.Map;

import org.brijframework.asm.factories.AbstractFactory;
import org.brijframework.factories.Factory;
import org.brijframework.network.template.EmailTemplate;
import org.brijframework.util.reflect.InstanceUtil;

public class EmailTemplateFactory extends AbstractFactory<String, EmailTemplate>{
  
	static EmailTemplateFactory factory=null;
	public static EmailTemplateFactory factory() {
		if(factory==null){
			factory=(EmailTemplateFactory) InstanceUtil.getInstance(EmailTemplateFactory.class);
		}
		return factory;
	}
	@Override
	public Factory loadFactory() {
		return null;
	}
	@Override
	protected void preregister(String key, EmailTemplate value) {
		
	}
	@Override
	protected void postregister(String key, EmailTemplate value) {
		
	}
	
	public String register(String templateID, Map<String, Object> paramMap) {
		return null;
	}
}

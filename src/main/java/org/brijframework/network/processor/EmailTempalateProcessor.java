package org.brijframework.network.processor;

import java.util.Set;

import org.mvel2.templates.CompiledTemplate;
import org.mvel2.templates.SimpleTemplateRegistry;
import org.mvel2.templates.TemplateCompiler;
import org.mvel2.templates.TemplateRuntime;

public class EmailTempalateProcessor {
	public SimpleTemplateRegistry templateRegistry=new SimpleTemplateRegistry();
	
	public CompiledTemplate compiledTemplate(String template) {
		return TemplateCompiler.compileTemplate(template);
	}
	
	public Object runTemplate(CompiledTemplate template) {
		return TemplateRuntime.execute(template);
	}
	
	public void registerTemplate(String tempID,CompiledTemplate template){
		templateRegistry.addNamedTemplate(tempID, template);
	}
	
	public void getCompileTemplate(String tempID){
		templateRegistry.getNamedTemplate(tempID);
	}
	
	public Set<String> getCompiledTemplateList(){
		return templateRegistry.getNames();
	}
	
	
	
}

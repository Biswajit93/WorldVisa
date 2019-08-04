package com.worldvisa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailContentBuilder {
 
    private TemplateEngine templateEngine;
 
    @Autowired
    public MailContentBuilder(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }
 
    public String build(List<String> message, String file) {
    	 Context context = new Context();
    	 
    	 for(int i=0;i < message.size(); i++)
    		 context.setVariable("message"+i, message.get(i));
         
         return templateEngine.process(file, context);
    }

 
}

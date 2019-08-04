package com.worldvisa.service;

import java.io.File;
import java.util.List;
import java.util.StringTokenizer;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.worldvisa.model.Template;

@Service
public class MailClient {
 
	@Autowired
    private JavaMailSender mailSender;
    
	
	@Autowired
	private MailContentBuilder mailContentBuilder;
 
    @Autowired
    public MailClient(JavaMailSender mailSender,MailContentBuilder mailContentBuilder) {
        this.mailSender = mailSender;
        this.mailContentBuilder=mailContentBuilder;
    }
 
 
    public void prepareAndSend(String recipient, List<String> message, String file, String subject,File f) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true);
            messageHelper.setTo(recipient);
            messageHelper.setSubject(subject);
            if(f!=null) {
            	messageHelper.addAttachment("Invoice", f);
            }
            String content = mailContentBuilder.build(message, file);
            messageHelper.setText(content, true);
        };
        try {
            mailSender.send(messagePreparator);
        } catch (MailException e) {
            // runtime exception; compiler will not force you to handle it
        	e.printStackTrace();
        }

    }
    
    public void prepareAndSendTemplate(String recipient, Template temp) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true);
            messageHelper.setTo(recipient);
            messageHelper.setSubject(temp.getSubject());
            StringTokenizer st = new StringTokenizer(temp.getAttachments(), ",");
            Multipart multipart = new MimeMultipart("mixed");
            while(st.hasMoreTokens()) {            	
            	MimeBodyPart messageBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(System.getProperty( "catalina.base" )+ "/webapps/ROOT/attachments/"+temp.getTemplateId()+"/"+st.nextToken());
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(source.getName());
                multipart.addBodyPart(messageBodyPart);
            }
            mimeMessage.setContent(multipart);
            messageHelper.setText(temp.getBody(), true);
        };
        try {
            mailSender.send(messagePreparator);
        } catch (MailException e) {
            // runtime exception; compiler will not force you to handle it
        	e.printStackTrace();
        }

    }
}
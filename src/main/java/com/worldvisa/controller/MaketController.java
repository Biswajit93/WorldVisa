package com.worldvisa.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.worldvisa.model.ContactFiles;
import com.worldvisa.model.Contacts;
import com.worldvisa.model.SocialFollowUp;
import com.worldvisa.model.Template;
import com.worldvisa.model.User;
import com.worldvisa.model.UserDetails;
import com.worldvisa.model.UserInformation;
import com.worldvisa.repository.ReportDetailRepository;
import com.worldvisa.repository.TemplateRepository;
import com.worldvisa.service.MailContentBuilder;
import com.worldvisa.service.MarketService;

@Controller
public class MaketController {

	private static final String UPLOADED_FOLDER = System.getProperty( "catalina.base" )+ "/webapps/ROOT/contacts/";
	private static final String ATTACHMENT_FOLDER = System.getProperty( "catalina.base" )+ "/webapps/ROOT/attachments/";
	
	@Autowired
	private MarketService market;
	
	@Autowired
	private TemplateRepository templateReporitory;
	
	@Autowired
	private ReportDetailRepository reportDetailRepository;
	
	@Autowired
	private JavaMailSender sender;

	@Autowired
	private MailContentBuilder contentBuilder;
	

	@GetMapping("/list_contacts")
	public String viewContacts(Model model,HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		List<UserInformation> contacts = new ArrayList<UserInformation>();
		contacts = market.findReports();
		model.addAttribute("contacts", contacts);
		model.addAttribute("user", user);
		return "list_contacts";
	}

	@SuppressWarnings("null")
	@PostMapping("/perform")
	public String deleteContacts(@RequestParam("id") List<String> ids, @RequestParam("do") String d, HttpServletRequest request) {

		if (d.equals("comms")) {
			
			request.getSession().setAttribute("comms_Mail",ids);
			return "redirect:/list_template_mail";

		}
		if (d.equals("comma")) {
			ids=null;
			for(UserDetails u:reportDetailRepository.findAll()) {
				ids.add(u.getReportId());
			}
			request.getSession().setAttribute("comms_Mail",ids);
			return "redirect:/list_template_mail";

		}
		if (d.equals("delete")) {
			market.deleteRecords(ids);
		}

		return "redirect:/list_contacts";
	}
	
	@GetMapping("/list_template_mail")
	public String viewTemplateMails(Model model,HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		List<Template> templateList = new ArrayList<Template>();
		templateList = market.findAll();
		model.addAttribute("templateList", templateList);
		model.addAttribute("user", user);
		return "list_template_mail";
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/selTemplate/{id}")
	public String viewReport(@PathVariable("id") int id,Model model, HttpServletRequest request) {
		
		Template template = templateReporitory.findById(id).orElseThrow(null);
		List<String> listIds = (List<String>) request.getSession().getAttribute("comms_Mail");
		
		for(String reportId : listIds) {
			
			UserDetails userDetails = reportDetailRepository.findById(reportId).orElseThrow(null);
			com.worldvisa.service.MailClient mailClient = new com.worldvisa.service.MailClient(sender, contentBuilder);
			mailClient.prepareAndSendTemplate(userDetails.getUserInformation().getEmailAddress(), template);
		}
		return "redirect:/list_contacts";
	}

	@GetMapping("/add_template")
	public String addTemplates(Model model,HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		Template template = new Template();

		List<String> typeList = new ArrayList<String>();
		typeList.add("Mailer");
		typeList.add("SMS");
		typeList.add("General Update");

		model.addAttribute("typeList", typeList);
		model.addAttribute("template", template);
		model.addAttribute("user", user);

		return "add_template";
	}

	@PostMapping("/add_template")
	public String addTemplate(@ModelAttribute Template template, HttpServletRequest request,
			@RequestParam("files") MultipartFile[] files) {
		String attachments="";
		String body = (String) request.getSession().getAttribute("body");
		template.setBody(body);
		template.setCreatedOn(new java.sql.Timestamp(new java.util.Date().getTime()));	
		market.save(template);
		try {
			Path path = Paths.get(ATTACHMENT_FOLDER + template.getTemplateId());
			if(!Files.exists(path, LinkOption.NOFOLLOW_LINKS))
				Files.createDirectories(path);
			for (int i = 0; i < files.length-1; i++) {
				
				byte[] bytes = files[i].getBytes();
				Path p = Paths.get(path + "/" + files[i].getOriginalFilename());
				Files.write(p, bytes);
				attachments+=files[i].getOriginalFilename()+",";
				
			}
			attachments = attachments.replaceAll(", $", "");
			template.setAttachments(attachments);

		} catch (IOException e) {

			e.printStackTrace();
		}
		market.save(template);
		

		return "redirect:/add_template";
	}

	@GetMapping("/list_template")
	public String viewTemplates(Model model,HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		List<Template> templateList = new ArrayList<Template>();
		templateList = market.findAll();
		model.addAttribute("templateList", templateList);
		model.addAttribute("user", user);
		return "list_template";
	}

	@GetMapping("/add_contacts")
	public String addContacts(Model model,HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		ContactFiles contactfile = new ContactFiles();		
		
		List<String> serviceLinkList = new ArrayList<String>();
		serviceLinkList.add("Immigration");
		serviceLinkList.add("Visit Visa");
		serviceLinkList.add("Migration Reviews");
		serviceLinkList.add("Dependant Visa");
		serviceLinkList.add("Domestic Worker Visa");
		serviceLinkList.add("Post Study Visa");
		serviceLinkList.add("Work Holiday Pass");
		serviceLinkList.add("Epec");
		serviceLinkList.add("Overseas Education Admission and Visa");
		serviceLinkList.add("Overseas Education Visa Only");
		serviceLinkList.add("Visa Reviews");
		serviceLinkList.add("Business Visa");
		serviceLinkList.add("Work Permit");
		serviceLinkList.add("Others");

		List<String> branchList = new ArrayList<String>();
		branchList.add("Bangalore");
		branchList.add("Chennai");
		
		List<String> dataSource= new ArrayList<String>();
		dataSource.add("Google");
		dataSource.add("Zoho");

		model.addAttribute("serviceLinkList", serviceLinkList);
		model.addAttribute("branchList", branchList);
		model.addAttribute("contactfile", contactfile);
		model.addAttribute("dataSource", dataSource);
		model.addAttribute("user", user);
		
		return "add_contacts";
	}

	@PostMapping("/load")
	public ModelAndView loadContacts(@ModelAttribute ContactFiles contactfile,
			@RequestParam("file") MultipartFile file,HttpServletRequest req) {
		User user = (User) req.getSession().getAttribute("user");
		ModelAndView modelAndView = new ModelAndView();
		if (file.isEmpty()) {
			modelAndView.addObject("successMessage", "No file to upload");
		}
		try {

			// Get the file and save it somewhere
			
			Path path = Paths.get(UPLOADED_FOLDER);
			if(!Files.exists(path, LinkOption.NOFOLLOW_LINKS))
				Files.createDirectories(path);
			byte[] bytes = file.getBytes();
			Path p=Paths.get(path+"/"+ file.getOriginalFilename());
			Files.write(p, bytes);

			contactfile.setFileName(file.getOriginalFilename());
			contactfile.setDataGroup("Social");
			market.save(contactfile);
			HashMap<String,Integer> headers=(HashMap<String, Integer>) market.loadHeaders(file);
			modelAndView.addObject("headers", headers);
			req.getSession().setAttribute("CurrFile", file);
			req.getSession().setAttribute("contactFileId", contactfile.getContactsFileId());
			ContactFiles contactFile = new ContactFiles();
			modelAndView.addObject("contactfile", contactFile);
			modelAndView.addObject("user", user);
			modelAndView.setViewName("add_contacts");

		} catch (IOException e) {
			modelAndView.setViewName("redirect:/add_contacts");
			e.printStackTrace();
		}
		return modelAndView;
	}
	
	@PostMapping("/save")
	public String saveContacts(HttpServletRequest req,@RequestParam("selectedHeaders") ArrayList<String> s) {
		MultipartFile f=(MultipartFile) req.getSession().getAttribute("CurrFile");
		int c=(int) req.getSession().getAttribute("contactFileId");
				try {
					market.addContacts(f,s,c);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}				
		return "redirect:/add_contacts";
	}
	
	
	@GetMapping("/social/{source}")
	public String viewSocial(Model model,@PathVariable("source") String source,HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		SocialFollowUp follow=new SocialFollowUp();
		List<Contacts> contactList=new ArrayList<Contacts>();
		if(source.equals("all")) {
			contactList=market.getAllContacts();		
		}
		if(source.equals("google")) {
			contactList=market.getGoogleContacts();
		}
		if(source.equals("zoho")) {
			contactList=market.getZohoContacts();
		}
		model.addAttribute("contactList", contactList);
		model.addAttribute("follow", follow);
		model.addAttribute("user", user);
		return "social";
	}
	
	@PostMapping("/followUp")
	public String addFollowUp(@ModelAttribute SocialFollowUp follow) {
		market.saveFollowUp(follow);
		return "redirect:/social/all";
	}
	
	@PostMapping("/getfollowUp")
	public String getFollowUp(@RequestParam("follow-up") String name,Model model) {
		List<SocialFollowUp> followUpList = market.getFollowUpHistory(name);
		model.addAttribute("followUpList", followUpList);
		return "Follow_up_history";
	}
	
	@PostMapping("/delTemplate")
	public String deTemplate(@RequestParam("temp") int tempId) {
		market.deleteTemplate(tempId);
		String path=ATTACHMENT_FOLDER + tempId;
		try {
			FileUtils.deleteDirectory(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:/list_template";
	}
	
}

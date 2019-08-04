package com.worldvisa.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.worldvisa.model.PaymentTransaction;
import com.worldvisa.model.User;
import com.worldvisa.model.UserInformation;
import com.worldvisa.repository.PaymentTransactionRepository;
import com.worldvisa.repository.UserInformationRepository;
import com.worldvisa.repository.UserRepository;
import com.worldvisa.service.MailContentBuilder;
import com.worldvisa.service.UserService;

@SuppressWarnings("deprecation")
@Controller
public class ViewController {
	
	@Autowired
	private PaymentTransactionRepository paymentTransactionRepository;
	
	@Autowired
	private UserInformationRepository userInformationRepository;
	
	@Autowired
	private UserService uservice;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JavaMailSender sender;

	@Autowired
	private MailContentBuilder contentBuilder;

	@GetMapping("/forget_password")
	public String forget() {
		return "forget_password";
	}

	@RequestMapping(value = "/forget_password", method = RequestMethod.POST)
	public String forgot(@RequestParam("email") String email) {
		String pass=null;
		User u=uservice.findUserByEmail(email);
		if(u!=null) {
			pass = RandomStringUtils.randomAlphanumeric(8);
			u.setPassword(bCryptPasswordEncoder.encode(pass));
			userRepository.save(u);
			
			String subject = "Password Information from World Visa";
			List<String> body = new ArrayList<String>();
			body.add(pass);
			com.worldvisa.service.MailClient mailClient = new com.worldvisa.service.MailClient(sender, contentBuilder);
			mailClient.prepareAndSend(u.getEmail(), body, "changePasswordMail", subject,null);

			//Send email to user
		}
		return "redirect:/forget_password";
	}
	
	@GetMapping("/change_password")
	public String change(Model model,HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		model.addAttribute("user", user);
		return "change_password";
	}
	
	@RequestMapping(value = "/change", method = RequestMethod.POST)
	public String changePass(HttpServletRequest request,@RequestParam("newPass")String newPass,@RequestParam("repeatPass")String repeatPass) {
		User user = (User) request.getSession().getAttribute("user");
		if(newPass.equals(repeatPass)) {
			user.setPassword(bCryptPasswordEncoder.encode(newPass));
			userRepository.save(user);
		}	
		return "redirect:/change_password";
	}
	
	@GetMapping("/list_groups")
	public String viewGroup() {
		return "list_groups";
	}

	@GetMapping("/daily_reporting")
	public String daily() {
		return "daily_reporting";
	}

	@GetMapping("/transaction")
	public String transaction(Model model,HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		List<PaymentTransaction> paymentTransactionList = paymentTransactionRepository.findAll();
		model.addAttribute("paymentTransactionList", paymentTransactionList);
		model.addAttribute("user", user);
		return "transaction";
	}

	
	
	@GetMapping("/userRolePage")
	public String userRolePage(HttpServletRequest request) {

		User user = (User) request.getSession().getAttribute("user");
		if ("ADMIN".equals(user.getRole())) {
			return "redirect:/notification";
		} else if ("CUSTOMER".equals(user.getRole())) {
			
			String email = user.getEmail();
			List<UserInformation> userInformationList = userInformationRepository.findByEmailAddress(email);
			if(userInformationList != null && !userInformationList.isEmpty())
				return "redirect:/report/find/"+userInformationList.get(0).getUserDetails().getReportId();
			else
				return "redirect:/fill_a_form";
		}
		else if("EMPLOYEE".equals(user.getRole())) {
			return "redirect:/active_assignments";
		}
		return "redirect:/notification";

	}
}
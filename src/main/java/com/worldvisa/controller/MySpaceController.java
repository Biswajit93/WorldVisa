package com.worldvisa.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.worldvisa.model.Employee;
import com.worldvisa.model.Notification;
import com.worldvisa.model.Role;
import com.worldvisa.model.User;
import com.worldvisa.repository.RoleRepository;
import com.worldvisa.repository.UserRepository;
import com.worldvisa.service.EmployeeService;
import com.worldvisa.service.MailContentBuilder;



@Controller
public class MySpaceController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private JavaMailSender sender;
	
	@Autowired 
	private MailContentBuilder contentBuilder;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@GetMapping("/add_employee")
	 public String viewCommon(Model model,HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		Employee employee=new Employee();
				
		List<String> locationList=new ArrayList<String>();
		locationList.add("Bangalore");
		locationList.add("Chennai");
		
		model.addAttribute("employee", employee);
		model.addAttribute("locationList", locationList);
		model.addAttribute("user", user);
		
	    	return "add_employee";
	    }
	
	@PostMapping("/add_employee")
	public ModelAndView saveEmployee(@ModelAttribute("employee") Employee employee,BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		
		Employee emp=employeeService.findEmployeeByEmail(employee.getEmail());
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("redirect:/add_employee");
		}
		if(emp == null) {
			User user = new User();
			user.setPassword(bCryptPasswordEncoder.encode(employee.getEmpName()));
			user.setActive(true);
			user.setEmail(employee.getEmail().trim());
			user.setPasswordExpFlag(false);

			Role userRole = roleRepository.findByRole("EMPLOYEE");

			if (userRole == null) {
				userRole = new Role();
				userRole.setRole("EMPLOYEE");
				roleRepository.save(userRole);
			}

			user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
			user.setRole("EMPLOYEE");
			userRepository.save(user);
			
			employeeService.saveEmployee(employee);
			modelAndView.addObject("successMessage", "Employee has been added successfully");
			modelAndView.setViewName("redirect:/add_employee");
			
			String subject = "Account Activation Confirmation";
			List <String> body = new ArrayList<String>();
			body.add(employee.getEmail());
			body.add(employee.getEmpName());
			
			com.worldvisa.service.MailClient mailClient = new com.worldvisa.service.MailClient(sender,contentBuilder);
			mailClient.prepareAndSend(employee.getEmail(), body, "CustomerLoginActivated", subject,null);
		}
		else {
			bindingResult
			.rejectValue("email", "error.employee",
					"There is already a employee registered with the email provided");
		}
		
		
		return modelAndView;
		
	}
	
	@GetMapping("/my_collegues")
	public String collegues(Model model,HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		List<Employee> employeeList=new ArrayList<Employee>();
		Notification notification=new Notification();
		employeeList=employeeService.findAll();
		model.addAttribute("employeeList", employeeList);
		model.addAttribute("notification", notification);
		model.addAttribute("user", user);
		return "my_collegues";
	}
	
	@PostMapping("/notify")
	public String addNotification(Model model,@ModelAttribute("notification") Notification notification,HttpServletRequest request){
		User user = (User) request.getSession().getAttribute("user");
		notification.setFromWhom(user.getEmail());		
		notification.setSentWhen(new java.sql.Timestamp(new java.util.Date().getTime()));
		employeeService.saveNotification(notification);
		return "redirect:/my_collegues";
	}
	
	@GetMapping("/notification")
	public String viewDashboard(Model model,HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		List<Notification> notiList = new ArrayList<Notification>();
		if(user.getRole().equals("ADMIN")) {
			notiList = employeeService.getNotifications();
		}
		else
			notiList = employeeService.getEmpNotifications(user.getEmail());
		model.addAttribute("notiList", notiList);
		model.addAttribute("user", user);
		return "notification";
	}
}

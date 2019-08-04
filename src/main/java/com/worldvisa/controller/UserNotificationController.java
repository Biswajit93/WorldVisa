package com.worldvisa.controller;
/*package com.example.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.model.Documents;
import com.example.model.Notification;
import com.example.model.UserInformation;
import com.example.model.UserNotification;
import com.example.repository.DocumentRepository;
import com.example.repository.NotificationRepository;
import com.example.repository.UserNotificationRepository;

@Controller
public class UserNotificationController {
	
	@Autowired
	private UserNotificationRepository userNotificationRepository;
	
	@RequestMapping(value = "/userNotification", method = RequestMethod.GET)
	public String home(Model model, HttpServletRequest request) {

		UserNotification userNotification = new UserNotification();
		model.addAttribute("userNotification", userNotification);
		
		UserInformation userInformation = (UserInformation) request.getSession().getAttribute("report");
		List<UserNotification> userNotificationList = userNotificationRepository.findByUserDetails(userInformation.getUserDetails());
		model.addAttribute("userNotificationList", userNotificationList);
		
		return "userNotification";

	}
	
	@PostMapping("/userNotification")
	public ModelAndView loadContacts(@ModelAttribute UserNotification notification, BindingResult bindingResult,
			HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		
		UserInformation userInformation = (UserInformation) request.getSession().getAttribute("report");
		
		String email=(String) request.getSession().getAttribute("currentEmp");
		notification.setByWhom(email);
		notification.setDateSet(new Date());
		notification.setStatus("Assigned");
		notification.setUserDetails(userInformation.getUserDetails());
		
		userNotificationRepository.save(notification);
		
		UserNotification userNotification = new UserNotification();
		modelAndView.addObject("userNotification", userNotification);
		
		List<UserNotification> userNotificationList = userNotificationRepository.findByUserDetails(userInformation.getUserDetails());
		modelAndView.addObject("userNotificationList", userNotificationList);
		
		modelAndView.setViewName("userNotification");

		return modelAndView;
	}

}
*/
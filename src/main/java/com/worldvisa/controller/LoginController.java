package com.worldvisa.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.worldvisa.model.User;
import com.worldvisa.service.UserService;

@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;

	@RequestMapping(value={"/", "/index"}, method = RequestMethod.GET)
	public ModelAndView login(){
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("index");
		return modelAndView;
	}
	
	
	@RequestMapping(value="/registration", method = RequestMethod.GET)
	public ModelAndView registration(){
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("registration");
		return modelAndView;
	}
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult,@RequestParam("rpass") String rpass) {
		
		ModelAndView modelAndView = new ModelAndView();
		if(user.getPassword().equals(rpass) && user.getEmail()!=null && user.getPassword()!=null) {
			User userExists = userService.findUserByEmail(user.getEmail());
			if (userExists != null) {
				bindingResult
						.rejectValue("email", "error.user",
								"There is already a user registered with the email provided");
			}
			if (bindingResult.hasErrors()) {
				modelAndView.setViewName("redirect:/registration");
			} else {
				userService.saveUser(user);
				modelAndView.addObject("successMessage", "User has been registered successfully");
				modelAndView.addObject("user", new User());
				modelAndView.setViewName("redirect:/registration");
				
			}
		}
		return modelAndView;
	}
	

}

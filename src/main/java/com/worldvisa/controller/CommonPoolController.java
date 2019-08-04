package com.worldvisa.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.worldvisa.model.Employee;
import com.worldvisa.model.User;
import com.worldvisa.service.CommonPoolService;

@Controller
public class CommonPoolController {
	
	@Autowired
	private CommonPoolService commonPoolService;
	
	@GetMapping("/common_pool")
	 public String viewCommon(Model model,HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		model.addAttribute("reportList", commonPoolService.findReports());
		List<Employee> empList=new ArrayList<Employee>();
		empList=commonPoolService.getAllEmpoyee();
		model.addAttribute("empList", empList);
		model.addAttribute("user", user);
	    	return "common_pool";
	    }
	
	@PostMapping("/common_pool")
	public String assign(@RequestParam("assigned_to") String emp) {
		String[] a=emp.split(":");
		commonPoolService.assignReportTo(a[1],a[0]);
		return "redirect:/common_pool";
	}
	
	@GetMapping("/active_assignments")
	public String viewActive(Model model,HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		if(user.getRole().equals("ADMIN")) {
			model.addAttribute("activeReports", commonPoolService.findActiveReports());
		}
		else
			model.addAttribute("activeReports", commonPoolService.findEmpActiveReports(user.getEmail()));
		model.addAttribute("user", user);
		return "active_assignments";
	}
	
	@PostMapping("/active_assignments")
	public String poolIt(@RequestParam("pool") String id) {
		commonPoolService.sendToPool(id);
		return "redirect:/active_assignments";
	}
	
	@GetMapping("/completed")
	public String viewCompleted(Model model,HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		if(user.getRole().equals("ADMIN")) {
			model.addAttribute("doneReports", commonPoolService.findDoneReports());
		}
		else
			model.addAttribute("doneReports", commonPoolService.findEmpDoneReports(user.getEmail()));
		model.addAttribute("user", user);
		return "completed";
	}
	
	@PostMapping("/close")
	public String Close(@RequestParam("close") String id) {
		commonPoolService.closeReport(id);
		return "redirect:/active_assignments";
	}
}

package com.worldvisa.controller;
/*package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.model.PaymentTransaction;
import com.example.model.UserDetails;
import com.example.model.UserInformation;
import com.example.service.ReportService;

public class TaskManagmentController {
	
	@Autowired
	private com.example.repository.PaymentTransactionRepository paymentTransactionRepository;
	
	@Autowired
	private ReportService reportService;
	
	@RequestMapping(value="/transaction", method = RequestMethod.GET)
	public String home(Model model){
		
		List <PaymentTransaction> paymentTransactionList = paymentTransactionRepository.findAll();
		
		
		model.addAttribute("paymentTransactionList", paymentTransactionList);
		
		return "transaction";
	}

}
*/
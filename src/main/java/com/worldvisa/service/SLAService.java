package com.worldvisa.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.worldvisa.model.UserDetails;
import com.worldvisa.repository.ReportDetailRepository;

@Service
public class SLAService {

	@Autowired
	private ReportDetailRepository userDetailsService;

	@Autowired
	private JavaMailSender sender;

	@Autowired
	private MailContentBuilder contentBuilder;

	@Scheduled(cron = " 0 0 8 * * ? ")
	public void sla() {
		List<UserDetails> userDetailsList = userDetailsService.findByExpiry(0);

		for (UserDetails userdetails : userDetailsList) {

			Timestamp recordDate = userdetails.getRaisedTime();

			Calendar cal = Calendar.getInstance();

			Calendar newCal = Calendar.getInstance();
			newCal.setTime(new Date());
			
			cal.setTimeInMillis(recordDate.getTime());
			cal.add(Calendar.DATE, 1);
			

			if (newCal.after(cal)) {
				String subject = "Your Record " + userdetails.getReportId() + " is getting expired";
				List<String> body = new ArrayList<String>();
				body.add("(" + String.valueOf(userdetails.getReportId()) + ")");
				com.worldvisa.service.MailClient mailClient = new com.worldvisa.service.MailClient(sender, contentBuilder);
				mailClient.prepareAndSend("suhas.s.123@gmail.com", body, "ExpiryMail24hrs", subject,null);

			}
			
			cal.setTimeInMillis(recordDate.getTime());
			cal.add(Calendar.DATE, 2);

			if (newCal.after(cal)) {
				userdetails.setExpiry(1);
				userDetailsService.save(userdetails);

				String subject = "Your Record " + userdetails.getReportId() + " has been expired";
				List<String> body = new ArrayList<String>();
				body.add("(" + String.valueOf(userdetails.getReportId()) + ")");
				com.worldvisa.service.MailClient mailClient = new com.worldvisa.service.MailClient(sender, contentBuilder);
				mailClient.prepareAndSend("suhas.s.123@gmail.com", body, "ExpiryMail48hrs", subject,null);

				// send mail for admin
			}

		}
	}
}

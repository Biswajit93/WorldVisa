package com.worldvisa.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.worldvisa.model.UserDetails;
import com.worldvisa.model.UserPaymentDates;
import com.worldvisa.repository.ReportDetailRepository;

@Service
public class PaymentService {

	@Autowired
	private ReportDetailRepository userDetailsService;

	@Autowired
	private JavaMailSender sender;

	@Autowired
	private MailContentBuilder contentBuilder;

	@Scheduled(cron = " 0 * * * * * ")
	public void paymentMail() {

		List<UserDetails> userDetailsList = userDetailsService.findAll();

		for (UserDetails userDetails : userDetailsList) {

			List<UserPaymentDates> userPaymentDatesList = userDetails.getUserPaymentDates();

			for (UserPaymentDates userPaymentDates : userPaymentDatesList) {
				if (!userPaymentDates.isSendMail()) {
					continue;
				}

				Date date = userPaymentDates.getTransactionDate();
				Calendar pastDate = Calendar.getInstance();
				Calendar futureDate = Calendar.getInstance();
				Calendar transactionDate = Calendar.getInstance();
				transactionDate.setTime(date);

				pastDate.add(Calendar.DATE, -7);
				futureDate.add(Calendar.DATE, 14);

				if (transactionDate.after(pastDate) && transactionDate.before(futureDate)) {

					String subject = "EMI reminder";
					List<String> body = new ArrayList<String>();
					body.add(userPaymentDates.getAmount().toString());
					com.worldvisa.service.MailClient mailClient = new com.worldvisa.service.MailClient(sender,
							contentBuilder);
					mailClient.prepareAndSend("suhas.s.123@gmail.com", body, "PaymentIntimation", subject,null);
					break;

				}
			}

		}

	}

}

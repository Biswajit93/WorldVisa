package com.worldvisa.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.worldvisa.model.CaseStatus;
import com.worldvisa.model.CountryPreferences;
import com.worldvisa.model.Payment;
import com.worldvisa.model.PaymentCurrent;
import com.worldvisa.model.PaymentTransaction;
import com.worldvisa.model.User;
import com.worldvisa.model.UserDetails;
import com.worldvisa.model.UserInformation;
import com.worldvisa.model.UserPaymentDates;
import com.worldvisa.repository.CaseStatusRepository;
import com.worldvisa.repository.PaymentRepository;
import com.worldvisa.repository.PaymentTransactionRepository;
import com.worldvisa.repository.UserRepository;
import com.worldvisa.service.MailContentBuilder;
import com.worldvisa.service.ReportService;
import com.worldvisa.service.UserDetailsService;
import com.worldvisa.service.UserService;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

@SuppressWarnings("deprecation")
@Controller
public class PaymentController {

	@Autowired
	private ReportService reportService;

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private PaymentTransactionRepository paymentTransactionRepository;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JavaMailSender sender;

	@Autowired
	private MailContentBuilder contentBuilder;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private CaseStatusRepository caseStatusRepository;
	

	private final String INVOICE_FOLDER = System.getProperty( "catalina.base" )+ "/webapps/ROOT/invoice/";

	@RequestMapping(value = "/payment", method = RequestMethod.GET)
	public String home(Model model, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		UserInformation report = new UserInformation();
		UserInformation userInformation = new UserInformation();
		UserDetails userDetails = new UserDetails();
		report = reportService.findReportById(request.getSession().getAttribute("reportID").toString()).orElseThrow(null);

		List<String> paymentOptionList = new ArrayList<String>();
		paymentOptionList.add("One-Time Plan");
		paymentOptionList.add("Quick Installment Plan");
		paymentOptionList.add("Standard Installment Plan");

		List<String> additionalServicesList = new ArrayList<String>();
		additionalServicesList.add("none");
		additionalServicesList.add(
				"Resume Designing, restructuring, interview preparation with job search assisstance in UK, Can & Aus");
		additionalServicesList.add("Admission & Visa");

		String countryList = null;

		for (CountryPreferences c : report.getUserDetails().getCountryPreferencesList()) {

			if (countryList == null)
				countryList = c.getCountry();
			else
				countryList = countryList + ", " + c.getCountry();
		}

		Payment payment = new Payment();

		String paymentEntered = null;

		if (report.getUserDetails().getPaymentList() != null && !report.getUserDetails().getPaymentList().isEmpty()
				&& report.getUserDetails().getPaymentList().get(0) != null) {
			paymentEntered = "true";
			payment = report.getUserDetails().getPaymentList().get(0);
		}

		model.addAttribute("report", report);
		model.addAttribute("countryList", countryList);
		model.addAttribute("userdetails", userDetails);
		model.addAttribute("userInformation", userInformation);
		model.addAttribute("payment", payment);
		model.addAttribute("paymentEntered", paymentEntered);
		model.addAttribute("paymentOptionList", paymentOptionList);
		model.addAttribute("additionalServicesList", additionalServicesList);
		model.addAttribute("userPaymentList", report.getUserDetails().getUserPaymentDates());
		model.addAttribute("user", user);

		request.getSession().setAttribute("report", report);

		return "payment";
	}

	@RequestMapping(value = "/payment", method = RequestMethod.POST)
	public String saveUser(@ModelAttribute("payment") Payment payment, HttpServletRequest request,
			BindingResult bindingResult) {
		UserInformation userInformation = (UserInformation) request.getSession().getAttribute("report");

		List<CountryPreferences> countryPreferences = userInformation.getUserDetails().getCountryPreferencesList();

		int costOfService = Integer.parseInt(payment.getCostOfService());
		double tax = Double.parseDouble(payment.getTax()) / 100;
		int noOFVisa = Integer.parseInt(payment.getNumberOfVisa());

		double total = ((costOfService * noOFVisa) + (costOfService * noOFVisa * tax)) * countryPreferences.size();
		payment.setNetPayable(String.valueOf(total));

		payment.setUserDetails(userInformation.getUserDetails());
		paymentRepository.save(payment);

		String planType = payment.getPaymentOption();
		List<UserPaymentDates> userPaymentDatesList = new ArrayList<UserPaymentDates>();

		if ("One-Time Plan".equals(planType)) {

			UserPaymentDates userPaymentDates = new UserPaymentDates();
			userPaymentDates.setAmount(total);
			userPaymentDates.setSendMail(false);
			userPaymentDates.setTransactionDate(new Date());
			userPaymentDates.setUserDetails(userInformation.getUserDetails());

			userPaymentDatesList.add(userPaymentDates);

		} else if ("Quick Installment Plan".equals(planType)) {

			int remainingCostOfService = 0;
			UserPaymentDates userPaymentDates = new UserPaymentDates();
			userPaymentDates.setAmount(((30000 * noOFVisa) + (30000 * noOFVisa * tax))
					* countryPreferences.size());
			userPaymentDates.setSendMail(false);
			userPaymentDates.setTransactionDate(new Date());
			userPaymentDates.setUserDetails(userInformation.getUserDetails());
			userPaymentDatesList.add(userPaymentDates);
			
			remainingCostOfService = costOfService - 30000;

			for (int i = 1; i <= 4; i++) {

				/*Calendar calendar = Calendar.getInstance();
				int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
				if (dayOfMonth <= 15)
					calendar.add(Calendar.MONTH, i);
				else
					calendar.add(Calendar.MONTH, i + 1);
				calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
				Date nextMonthFirstDay = calendar.getTime();*/
				
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.MONTH, i);
				Date nextMonthFirstDay = calendar.getTime();

				userPaymentDates = new UserPaymentDates();
				userPaymentDates.setAmount(((remainingCostOfService * 0.25 * noOFVisa) + (remainingCostOfService * 0.25 * noOFVisa * tax))
						* countryPreferences.size());
				userPaymentDates.setSendMail(true);
				userPaymentDates.setTransactionDate(nextMonthFirstDay);
				userPaymentDates.setUserDetails(userInformation.getUserDetails());
				userPaymentDatesList.add(userPaymentDates);

			}

		} else if ("Standard Installment Plan".equals(planType)) {
			
			int remainingCostOfService = 0;

			UserPaymentDates userPaymentDates = new UserPaymentDates();
			userPaymentDates.setAmount(((30000 * noOFVisa) + (30000 * noOFVisa * tax))
					* countryPreferences.size());
			userPaymentDates.setSendMail(true);
			userPaymentDates.setTransactionDate(new Date());
			userPaymentDates.setUserDetails(userInformation.getUserDetails());
			userPaymentDatesList.add(userPaymentDates);
			
			remainingCostOfService = costOfService - 30000;
			
			for (int i = 1; i <= 6; i++) {

				//Calendar calendar = Calendar.getInstance();
				/*int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
				if (dayOfMonth <= 15)
					calendar.add(Calendar.MONTH, i);
				else
					calendar.add(Calendar.MONTH, i + 1);
				calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
				Date nextMonthFirstDay = calendar.getTime();*/
				
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.MONTH, i);
				Date nextMonthFirstDay = calendar.getTime();

				userPaymentDates = new UserPaymentDates();
				userPaymentDates.setAmount(((remainingCostOfService * 0.16666666666666666667 * noOFVisa) + (remainingCostOfService * 0.16666666666666666667 * noOFVisa * tax))
						* countryPreferences.size());
				userPaymentDates.setSendMail(false);
				userPaymentDates.setTransactionDate(nextMonthFirstDay);
				userPaymentDates.setUserDetails(userInformation.getUserDetails());
				userPaymentDatesList.add(userPaymentDates);

			}

		}
		userInformation.getUserDetails().setUserPaymentDates(userPaymentDatesList);
		userDetailsService.saveDetails(userInformation);

		String subject = "Account Activation Confirmation";
		List<String> body = new ArrayList<String>();
		body.add(userInformation.getEmailAddress());
		body.add(userInformation.getName());

		User user = userService.findUserByEmail(userInformation.getEmailAddress());
		user.setActive(true);
		user.setRole("CUSTOMER");
		userRepo.save(user);

		com.worldvisa.service.MailClient mailClient = new com.worldvisa.service.MailClient(sender, contentBuilder);
		mailClient.prepareAndSend(userInformation.getEmailAddress(), body, "CustomerLoginActivated", subject,null);

		return "redirect:/payment";
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping(value = "/paymentEntry", method = RequestMethod.POST)
	public String saveUser1(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		
		UserInformation userInformation = (UserInformation) request.getSession().getAttribute("report");
		
		//Payment pay=paymentRepository.findOneByuserDetails(userInformation.getUserDetails().getReportId());

		List<PaymentTransaction> paymentTransactionList = (List<PaymentTransaction>) request.getSession()
				.getAttribute("paymentTransaction");

		if (paymentTransactionList != null) {
			
				
				
			
			for (PaymentTransaction paymentTransaction : paymentTransactionList) {
				double amount = Double.parseDouble(paymentTransaction.getAmount());
				
				paymentTransaction.setUserDetails(userInformation.getUserDetails());
				paymentTransactionRepository.save(paymentTransaction);

				FileOutputStream fos = null;
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

				List<PaymentCurrent> list = new ArrayList<PaymentCurrent>();

				PaymentCurrent paymentCurrent = new PaymentCurrent();
				paymentCurrent
						.setPlanAmount(userInformation.getUserDetails().getPaymentList().get(0).getCostOfService());
				paymentCurrent.setChequeDdNo(paymentTransaction.getChequeorDDNumber());
				paymentCurrent.setCountry(userInformation.getCountry());
				paymentCurrent.setInvoiceSentInd(null);
				paymentCurrent.setName(userInformation.getName());
				paymentCurrent.setPaymentCurrentAmount(paymentTransaction.getAmount());
				paymentCurrent.setPaymentPendingAmount("0");//(String.valueOf(Double.parseDouble(pay.getCostOfService())-amount));
				paymentCurrent.setPaymentId(paymentTransaction.getID());
				paymentCurrent.setPaymentMode(paymentTransaction.getMode());
				paymentCurrent
						.setPaymentPlan(userInformation.getUserDetails().getPaymentList().get(0).getPaymentOption());
				paymentCurrent.setPaymentTotalAmount(
						userInformation.getUserDetails().getPaymentList().get(0).getNetPayable());
				paymentCurrent.setTransactionDt(new Date());
				paymentCurrent.setReportID(userInformation.getUserDetails().getReportId());
				paymentCurrent.setRemarks(paymentTransaction.getRemarks());
				paymentCurrent.setPhoneNo(userInformation.getMobilenumber());
				paymentCurrent.setInvoiceNo("WV_" + paymentTransaction.getID());
				
				list.add(paymentCurrent);

				try {

					// Get the file and save it somewhere
					Path path = Paths.get(INVOICE_FOLDER + userInformation.getUserDetails().getReportId());

					if (!Files.exists(path, LinkOption.NOFOLLOW_LINKS))
						Files.createDirectories(path);

					byte[] bytes = this.generateJasperReportPDF("InvoiceReport", outputStream, list);
					if (bytes.length > 1) {
						File someFile = new File(path+"/"+"Payment"+paymentCurrent.getInvoiceNo()+".pdf");
						fos = new FileOutputStream(someFile);
						fos.write(bytes);
						fos.flush();
						fos.close();						
					}

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (fos != null) {
						try {
							fos.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				File attach=new File(INVOICE_FOLDER + userInformation.getUserDetails().getReportId()+"/"+"Payment"+paymentCurrent.getInvoiceNo()+".pdf");
				String subject = "Payment Acknowledgement Notification";
				List<String> body = new ArrayList<String>();
				body.add(String.valueOf(amount));
				com.worldvisa.service.MailClient mailClient = new com.worldvisa.service.MailClient(sender,
						contentBuilder);
				mailClient.prepareAndSend(userInformation.getEmailAddress(), body, "CustomerPaymentAcknowledged",
						subject,attach);
			}

		}

		return "redirect:/payment";
	}

	@RequestMapping(value = "/case_status", method = RequestMethod.GET)
	public String caseStatus(Model model, HttpServletRequest request) {
		CaseStatus caseStatus = new CaseStatus();
		User user = (User) request.getSession().getAttribute("user");
		UserInformation status = reportService.findReportById(request.getSession().getAttribute("reportID").toString()).orElseThrow(null);

		List<String> subStatusList = new ArrayList<String>();
		subStatusList.add("Miscellaneous");
		subStatusList.add("Retainer Agreement Signed");
		subStatusList.add("File Number Obtained");
		subStatusList.add("Visa Grant Letter Obtained");
		subStatusList.add("Request Completed");
		subStatusList.add("REquest Abadoned");
		subStatusList.add("Walk Ins Counselled");
		subStatusList.add("Phone Enquiries");
		subStatusList.add("Tele Calling");
		subStatusList.add("Collection");
		subStatusList.add("File Handled");

		List<CaseStatus> caseStatusList = new ArrayList<>();
		caseStatusList = caseStatusRepository.findAllByuserDetails(status.getUserDetails());

		model.addAttribute("subStatusList", subStatusList);
		model.addAttribute("caseStatus", caseStatus);
		model.addAttribute("caseStatusList", caseStatusList);
		model.addAttribute("report", status);
		model.addAttribute("user", user);
		return "case_status";
	}

	@RequestMapping(value = "/case_status", method = RequestMethod.POST)
	public String addCaseStatus(@ModelAttribute CaseStatus caseStatus, HttpServletRequest request) {

		UserInformation userinfo = reportService
				.findReportById(request.getSession().getAttribute("reportID").toString()).orElseThrow(null);
		System.out.print(userinfo.getId());

		String email = (String) request.getSession().getAttribute("currentEmp");
		caseStatus.setSetBy(email);
		caseStatus.setSetOn(new java.sql.Timestamp(new java.util.Date().getTime()));
		caseStatus.setUserDetails(userinfo.getUserDetails());
		caseStatusRepository.save(caseStatus);
		return "redirect:/case_status";
	}

	@SuppressWarnings("rawtypes")
	public byte[] generateJasperReportPDF(String jasperReportName, ByteArrayOutputStream outputStream, List reportList) {
		JRPdfExporter exporter = new JRPdfExporter();
		try {
			String reportLocation = System.getProperty( "catalina.base" )+"/webapps/ROOT/WEB-INF/classes/static/PaymentInvoice.jrxml";
			JasperReport jasperReport = JasperCompileManager.compileReport(reportLocation);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null,
					new JRBeanCollectionDataSource(reportList));
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
			exporter.exportReport();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error in generate Report..." + e);
		} finally {
		}
		return outputStream.toByteArray();
	}

}

package com.worldvisa.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.worldvisa.model.ContactFiles;
import com.worldvisa.model.Contacts;
import com.worldvisa.model.SocialFollowUp;
import com.worldvisa.model.Template;
import com.worldvisa.model.UserInformation;
import com.worldvisa.repository.ContactFileRepository;
import com.worldvisa.repository.ContactRepository;
import com.worldvisa.repository.FollowUpRepository;
import com.worldvisa.repository.ReportDetailRepository;
import com.worldvisa.repository.TemplateRepository;
import com.worldvisa.repository.UserInformationRepository;

@Service("marketService")
public class MarketServiceImpl implements MarketService{
	
	@Autowired
	private UserInformationRepository informationRepository;
	
	@Autowired
	private ReportDetailRepository reportRepository;
	
	@Autowired
	private TemplateRepository templateRepository;
	
	@Autowired
	private ContactFileRepository contactFileRepository;
	
	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
	private FollowUpRepository followRepository;
	
	public List<UserInformation> findReports() {
		return informationRepository.findAll();
	}

	@Override
	public void deleteRecords(List<String> ids) {
		for(String i:ids) {
			reportRepository.deleteById(i);
		}
	}

	@Override
	public void save(Template template) {
		templateRepository.save(template);
		
	}

	@Override
	public List<Template> findAll() {
		return templateRepository.findAll();
	}

	@Override
	public void save(ContactFiles contactFile) {
		contactFileRepository.save(contactFile);
		
	}
	
	@SuppressWarnings("deprecation")
	public static Object getCellValue(Cell cell) {
		switch(cell.getCellType()) {
		
			case Cell.CELL_TYPE_STRING:
				return cell.getStringCellValue();
			case Cell.CELL_TYPE_BOOLEAN:
				return cell.getBooleanCellValue();
			case Cell.CELL_TYPE_NUMERIC:
				return cell.getNumericCellValue();
			case Cell.CELL_TYPE_BLANK:
				return null;
				
		}
		return cell;
	}

	@SuppressWarnings("resource")
	@Override
	public void addContacts(MultipartFile file,ArrayList<String> selectedheaders,int contactFile) throws FileNotFoundException,IOException {
		try {
			List<Contacts> contactList=new ArrayList<Contacts>();
			Map<String,Integer> header=new HashMap<String,Integer>();
			Workbook workbook=WorkbookFactory.create(new File(System.getProperty( "catalina.base" )+ "/webapps/ROOT/contacts/"+file.getOriginalFilename()));				 
			Sheet firstSheet=workbook.getSheetAt(0);
			Iterator<Row> iterator= firstSheet.iterator();
			Row row=iterator.next();
			for(Cell c:row) {
				if(c.getStringCellValue()!="") {
				header.put(c.getStringCellValue().trim().toLowerCase(), c.getColumnIndex());
				}
			}
			while(iterator.hasNext()) {
				row=iterator.next();
				Contacts contact=new Contacts();
				contact.setContactFileId(contactFile);
				for(String s:selectedheaders) {
				for(Map.Entry<String, Integer> i:header.entrySet()) {					
					for(Cell c:row) {					
						if(c.getColumnIndex()==i.getValue() && i.getKey().equals(s.toLowerCase())) {
							switch (i.getKey()) {
							case "name":
								contact.setName((String)getCellValue(c));								
								break;
							case "gender":
								contact.setGender((String) getCellValue(c));								
								break;
							case "age":
								contact.setAge((Double) getCellValue(c));								
								break;
							case "desired course level":
								contact.setDesiredCourseLevel((String) getCellValue(c));								
								break;
							case "field of interest":
								contact.setFieldOfInterest((String) getCellValue(c));								
								break;
							case "desired countries":
								contact.setDesiredCountries((String) getCellValue(c));								
								break;
							case "plan to go":
								contact.setPlanToGo((String) getCellValue(c));								
								break;
							case "source of funding":
								contact.setSourceOfFunding((String) getCellValue(c));								
								break;
							case "graduation status":
								contact.setGraduationStatus((String) getCellValue(c));								
								break;
							case "email":
								contact.setEmail((String) getCellValue(c));								
								break;
							case "graduation course":
								contact.setGraduationCourse((String) getCellValue(c));								
								break;
							case "graduation marks":
								contact.setGraduationMarks((Double) getCellValue(c));								
								break;
							case "graduation month":
								contact.setGraduationMonth((String) getCellValue(c));								
								break;
							case "graduation year":
								contact.setGraduationYear((Double) getCellValue(c));
								break;
							case "std xii stream":
								contact.setStdXIIStream((String) getCellValue(c));
								break;
							case "std xii marks":
								contact.setStdXIIMarks((Double) getCellValue(c));
								break;
							case "std xii year":
								contact.setStdXIIYear((Double) getCellValue(c));
								break;
							case "exam taken 1":
								contact.setExamTaken1((String) getCellValue(c));
								break;
							case "exam taken 2":
								contact.setExamTaken2((String) getCellValue(c));
								break;
							case "exam taken 3":
								contact.setExamTaken3((String) getCellValue(c));
								break;
							case "preferred time to call":
								contact.setPreferredTimeToCall((String) getCellValue(c));
								break;
							case "current location":
								contact.setCurrentLocation((String) getCellValue(c));
								break;
							case "user comments":
								contact.setUserComments((String) getCellValue(c));
								break;
							case "date of registration":
								contact.setDateOfRegistration((String) getCellValue(c));
								break;
							case "is in ndnc list":
								contact.setIsInNDNCList((String) getCellValue(c));
								break;
							case "mobile":
								contact.setMobile((Double)getCellValue(c));
								break;
							
							}
						}
					}
				}
				}
				contactList.add(contact);
			}
			contactRepository.saveAll(contactList);
	}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("resource")
	@Override
	public Map<String, Integer> loadHeaders(MultipartFile file) throws FileNotFoundException,IOException{
		Map<String,Integer> header=new HashMap<String,Integer>();
		try {
		Workbook workbook=WorkbookFactory.create(new File(System.getProperty( "catalina.base" )+ "/webapps/ROOT/contacts/"+file.getOriginalFilename()));
		Sheet firstSheet=workbook.getSheetAt(0);
		Iterator<Row> iterator= firstSheet.iterator();
		Row row=iterator.next();
		for(Cell c:row) {
			if(c.getStringCellValue()!="") {
			header.put(c.getStringCellValue().trim(), c.getColumnIndex());
			}
		}
	}catch(Exception e) {
		e.printStackTrace();
	}
		return header;
		
	}
	
	@Override
	public Optional<Template> get(int id) {
		return templateRepository.findById(id);
		 
	}

	@Override
	public List<Contacts> getAllContacts() {
		return contactRepository.findAll();
	}


	@Override
	public List<Contacts> getGoogleContacts() {
		List<ContactFiles> contactFilesList=contactFileRepository.findAllBydataSource("Google");
		List<Contacts> contactList=new ArrayList<Contacts>();
		
		for(ContactFiles c:contactFilesList) {
			List<Contacts> contacts=contactRepository.findAllBycontactFileId(c.getContactsFileId());
			contactList.addAll(contacts);
		}
		return contactList;
	}

	@Override
	public List<Contacts> getZohoContacts() {
		List<ContactFiles> contactFilesList=contactFileRepository.findAllBydataSource("Zoho");
		List<Contacts> contactList=new ArrayList<Contacts>();
		
		for(ContactFiles c:contactFilesList) {
			List<Contacts> contacts=contactRepository.findAllBycontactFileId(c.getContactsFileId());
			contactList.addAll(contacts);
		}
		return contactList;
	}

	@Override
	public void saveFollowUp(SocialFollowUp follow) {
		followRepository.save(follow);		
	}

	@Override
	public List<SocialFollowUp> getFollowUpHistory(String name) {
		return followRepository.findAllBycontactName(name);
	}

	@Override
	public void deleteTemplate(int tempId) {
		templateRepository.deleteById(tempId);		
	}
		
	}

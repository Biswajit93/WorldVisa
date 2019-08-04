package com.worldvisa.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.worldvisa.model.ContactFiles;
import com.worldvisa.model.Contacts;
import com.worldvisa.model.SocialFollowUp;
import com.worldvisa.model.Template;
import com.worldvisa.model.UserInformation;


public interface MarketService {
	public List<UserInformation> findReports();
	public void deleteRecords(List<String> ids);
	public void save(Template template);
	public List<Template> findAll();
	public void save(ContactFiles contactFiles);
	public void addContacts(MultipartFile file,ArrayList<String> headers,int contactFile) throws FileNotFoundException,IOException;
	public Optional<Template> get(int id);
	public List<Contacts> getAllContacts();
	public Map<String, Integer> loadHeaders(MultipartFile file) throws FileNotFoundException, IOException;
	public List<Contacts> getGoogleContacts();
	public List<Contacts> getZohoContacts();
	public void saveFollowUp(SocialFollowUp follow);
	public List<SocialFollowUp> getFollowUpHistory(String name);
	public void deleteTemplate(int tempId);
}

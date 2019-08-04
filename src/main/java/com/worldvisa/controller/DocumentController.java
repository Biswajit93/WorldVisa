package com.worldvisa.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.worldvisa.model.Documents;
import com.worldvisa.model.User;
import com.worldvisa.model.UserInformation;
import com.worldvisa.repository.DocumentRepository;

@Controller
public class DocumentController {

	String s=System.getProperty( "catalina.base" );
	private final String DOCUMENT_FOLDER =System.getProperty( "catalina.base" )+ "/webapps/ROOT/documents/";

	@Autowired
	private DocumentRepository documentRepository;

	@RequestMapping(value = "/document", method = RequestMethod.GET)
	public String home(Model model, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		Documents documents = new Documents();
		model.addAttribute("documents", documents);
		
		UserInformation userInformation = (UserInformation) request.getSession().getAttribute("report");
		List<Documents> documentList = documentRepository.findByUserDetails(userInformation.getUserDetails());
		model.addAttribute("rept", userInformation);
		model.addAttribute("documentList", documentList);
		model.addAttribute("user", user);
		
		System.out.println("***************"+s+"*****************");
				
		return "document";

	}

	@PostMapping("/documents")
	public ModelAndView saveDoc(@ModelAttribute Documents documents, @RequestParam("file") MultipartFile file,
			HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		if (file.isEmpty()) {
			modelAndView.addObject("successMessage", "No file to upload");
			Documents newDocument = new Documents();
			modelAndView.addObject("documents", newDocument);
			modelAndView.setViewName("redirect:/document");
			return modelAndView;
		}
		UserInformation userInformation = (UserInformation) request.getSession().getAttribute("report");
		try {
			
			// Get the file and save it somewhere

			Path path = Paths.get(DOCUMENT_FOLDER + userInformation.getUserDetails().getReportId());
			
			if(!Files.exists(path, LinkOption.NOFOLLOW_LINKS))
				Files.createDirectories(path);

			byte[] bytes = file.getBytes();
			Path p = Paths.get(path + "/" + file.getOriginalFilename());
			Files.write(p, bytes);

		} catch (IOException e) {

			e.printStackTrace();
		}

		documents.setFileName(file.getOriginalFilename());
		documents.setDate(new Date());
		documents.setUserDetails(userInformation.getUserDetails());

		documentRepository.save(documents);
		Documents newDocument = new Documents();
		modelAndView.addObject("documents", newDocument);
		modelAndView.addObject("successMessage", "File uploaded succesfully");
		modelAndView.setViewName("redirect:/document");
		
		List<Documents> documentList = documentRepository.findByUserDetails(userInformation.getUserDetails());
		modelAndView.addObject("documentList", documentList);
		modelAndView.addObject("rept", userInformation);
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/download/{id}", method=RequestMethod.GET)
	public String downloadFile(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") int id) {

		File file = null;
				
		UserInformation userInformation = (UserInformation) request.getSession().getAttribute("report");
		Optional<Documents> document = documentRepository.findById(id);
				
		String path = DOCUMENT_FOLDER + userInformation.getUserDetails().getReportId() +"/"+document.map(Documents::getFileName);
		
		try {			
			file = new File(path);
			
			if(!file.exists()) {
				//String errorMessage = "File not exists";
				return "redirect:/document";
			}
			
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if(mimeType==null) {
				mimeType = "application/octet-stream";
			}
						
			response.setHeader("Content-Disposition", String.format("attachment;filename = \"%s\"", file.getName()));
			response.setContentLength((int)file.length());
			
			InputStream inputstream = new BufferedInputStream(new FileInputStream(file));
			FileCopyUtils.copy(inputstream, response.getOutputStream());
			
			response.getOutputStream().close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/document";
	}
	
	
	@PostMapping("/delDoc")
	public String deleteDoc(@RequestParam("doc") int id,HttpServletRequest request){
		UserInformation userInformation = (UserInformation) request.getSession().getAttribute("report");
		Optional<Documents> doc=documentRepository.findById(id);
		documentRepository.deleteById(id);
		String f=DOCUMENT_FOLDER +userInformation.getId() +"\\"+doc.map(Documents::getFileName);
		File file = new File(f);
		file.delete();
		return "redirect:/document";
	}

}

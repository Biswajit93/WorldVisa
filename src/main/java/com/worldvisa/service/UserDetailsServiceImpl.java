package com.worldvisa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worldvisa.model.UserInformation;
import com.worldvisa.repository.UserInformationRepository;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private UserInformationRepository userInformationRepository;
	
	public void saveDetails(UserInformation userInformation) {
		userInformationRepository.save(userInformation);
	}
	
}

package com.worldvisa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.worldvisa.model.UserInformation;

@Repository("userInformationRepository")
public interface UserInformationRepository extends JpaRepository<UserInformation, String>{

	List<UserInformation> findAllByuserDetails_assignedToNull();

	List<UserInformation> findAllByuserDetails_assignedToNotNull();
	
	List<UserInformation> findByEmailAddress(String emailAddress);

	UserInformation findOneByemailAddress(String e);

	List<UserInformation> findAllByuserDetails_assignedTo(String string);

	List<UserInformation> findAllByuserDetails_status(String string);

}

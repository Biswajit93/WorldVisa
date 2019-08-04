package com.worldvisa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.worldvisa.model.Notification;

@Repository("notificationRepository")
public interface NotificationRepository extends JpaRepository<Notification, Integer>{

	List<Notification> findAllBytoWhom(String string);
	
	
}

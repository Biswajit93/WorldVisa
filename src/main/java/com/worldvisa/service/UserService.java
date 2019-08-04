package com.worldvisa.service;

import com.worldvisa.model.User;

public interface UserService {
	public User findUserByEmail(String email);
	public void saveUser(User user);
}

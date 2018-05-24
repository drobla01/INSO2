package com.moviecatalog.service;

import com.moviecatalog.model.User;

public interface UserService {
	public User findUserByEmail(String email);
	public void saveUser(User user);
}

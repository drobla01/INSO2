package com.moviecatalog.service;

import com.moviecatalog.model.User;

public interface UserService {
	public void saveUser(User user);
	public User findUserByEmail(String email);
	public User findUserById(Integer id);
	public void update(User user);
	public void edit(User user);
}

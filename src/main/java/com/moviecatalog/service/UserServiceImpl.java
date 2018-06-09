package com.moviecatalog.service;

import java.awt.Color;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.moviecatalog.model.Role;
import com.moviecatalog.model.User;
import com.moviecatalog.repository.RoleRepository;
import com.moviecatalog.repository.UserRepository;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private RoleRepository roleRepository;
	
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void update(User user) {
    	userRepository.save(user);
    }
    
    @Override
    public void edit(User user) {
    	user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    	userRepository.save(user);
    }
    
    @Override
	public User findUserById(Integer id) {
		return userRepository.findById(id).get();
	}
    
	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        user.setDescription("Hi There, Im using Cinebook.");
        
        Date date = new Date();
        user.setCreation(date);	
        user.setUpdated(date);
        
        Role userRole = roleRepository.findByRole("USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        
        Random rand = new Random();
        Color rc = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
        String hex = String.format("%02x%02x%02x", rc.getRed(), rc.getGreen(), rc.getBlue());  
        user.setPhoto("https://ui-avatars.com/api/?rounded=true&size=512&background="+hex+"&font-size=0.4&name=" + user.getName());
        
        userRepository.save(user);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public void deleteUser(User user) {
		userRepository.delete(user);
	}

}

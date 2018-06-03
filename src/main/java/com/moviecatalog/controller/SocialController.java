package com.moviecatalog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.moviecatalog.model.Results;
import com.moviecatalog.model.User;
import com.moviecatalog.service.UserService;

@Controller
public class SocialController {
	
	@Autowired
	private UserService userService;
	
	@ModelAttribute
	public void addAttributes(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		if(user != null)
			model.addAttribute("sesion", user);
	}
	
	@RequestMapping("/user/social")
	public String goToProfile(@RequestParam(name = "id", required = true) Integer id, Model model) {
		User user = userService.findUserById(id);
		model.addAttribute("user", user);
		model.addAttribute("follows", user.getFollows());
		model.addAttribute("favourites", user.getFavourites());
		
		RestTemplate restTemplate = new RestTemplate();
		Results response = restTemplate.getForObject(
				"https://api.themoviedb.org/3/discover/movie?api_key=9ae4cb8d6fe7e69356db23d14dd945dd&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1",
				Results.class);
		model.addAttribute("movies", response.getResults());
		model.addAttribute("size", response.getResults().size());
		
		
		return "user/social";
	}
	
}

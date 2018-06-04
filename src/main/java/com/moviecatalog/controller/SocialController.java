package com.moviecatalog.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
	
	@RequestMapping(value="/user/social", method = RequestMethod.GET)
	public String goToProfile(@RequestParam(name = "id", required = true) Integer id, Model model) {
		User user = userService.findUserById(id);
		model.addAttribute("user", user);
		model.addAttribute("favourites", user.getFavourites());
		model.addAttribute("pending", user.getPending());
		model.addAttribute("views", user.getViews());
		model.addAttribute("follows", user.getFollows());
		return "user/social";
	}
	
	@RequestMapping(value="/user/social", method = RequestMethod.POST)
	public  ModelAndView addFriend() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("user/social");
		return modelAndView;
	}
	
}

package com.moviecatalog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

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
		if (user != null)
			model.addAttribute("sesion", user);
	}

	@RequestMapping(value = "/user/social", method = RequestMethod.GET)
	public String goToProfile(@RequestParam(name = "id", required = true) Integer id, Model model) {
		User user = userService.findUserById(id);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User session = userService.findUserByEmail(auth.getName());
		model.addAttribute("user", user);
		model.addAttribute("favourites", user.getFavourites());
		model.addAttribute("pending", user.getPending());
		model.addAttribute("views", user.getViews());
		model.addAttribute("mentors", user.getFollows());	
		for (User mentor : session.getFollows()) {
			if(mentor.getId() == user.getId()) {
				model.addAttribute("friend", true);
				return "user/social";
			}
		}
		model.addAttribute("friend", false);
		return "user/social";
	}

	@RequestMapping(value = "/user/social", method = RequestMethod.POST)
	public ModelAndView addFriend(@RequestParam(name = "id", required = true) Integer id, Model model) {
		ModelAndView mav = new ModelAndView(new RedirectView("/user/social", true));
		mav.addObject("id", id);

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		User mentor = userService.findUserById(id);

		for (User mentors : user.getFollows()) {
			if(mentors.getId() == mentor.getId()) {
				user.getFollows().remove(mentors);
				userService.update(user);
				return mav;
			}
		}
		
		user.getFollows().add(mentor);
		userService.update(user);
		return mav;
	}

}

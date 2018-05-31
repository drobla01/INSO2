//package com.moviecatalog.controller;
//
//import javax.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.moviecatalog.model.User;
//import com.moviecatalog.service.UserService;
//
//@Controller
//public class LoginController {
//
//	@Autowired
//	private UserService userService;
//
//	@RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
//	public ModelAndView login(){
//		ModelAndView modelAndView = new ModelAndView();
//		modelAndView.setViewName("login");
//		return modelAndView;
//	}
//
//	@RequestMapping(value = "/login", method = RequestMethod.POST)
//	public ModelAndView loginPost() {
//		ModelAndView modelAndView = new ModelAndView();
//		modelAndView.setViewName("login");
//		return modelAndView;
//	}
//
//	@RequestMapping(value="/registration", method = RequestMethod.GET)
//	public ModelAndView registration(){
//		ModelAndView modelAndView = new ModelAndView();
//		User user = new User();
//		modelAndView.addObject("user", user);
//		modelAndView.setViewName("registration");
//		return modelAndView;
//	}
//
//	@RequestMapping(value = "/registration", method = RequestMethod.POST)
//	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
//		ModelAndView modelAndView = new ModelAndView();
//		User userExists = userService.findUserByEmail(user.getEmail());
//		if (userExists != null) {
//			bindingResult
//			.rejectValue("email", "error.user",
//					"There is already a user registered with the email provided");
//		}
//		if (bindingResult.hasErrors()) {
//			modelAndView.setViewName("registration");
//		} else {
//			userService.saveUser(user);
//			modelAndView.addObject("successMessage", "User has been registered successfully");
//			modelAndView.addObject("user", new User());
//			modelAndView.setViewName("registration");
//
//		}
//		return modelAndView;
//	}
//
//	@RequestMapping(value="/user/home", method = RequestMethod.GET)
//	public ModelAndView homeUser() {
//		ModelAndView modelAndView = new ModelAndView();
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		User user = userService.findUserByEmail(auth.getName());
//		modelAndView.addObject("userName", "Welcome " + user.getName() + " (" + user.getEmail() + ") with user role " + user.getRoles().iterator().next().getRole() );
//		modelAndView.addObject("userMessage","Content Available Only for Users with User Role");
//		modelAndView.setViewName("user/home");
//		return modelAndView;
//	}
//
//	@RequestMapping(value="/admin/home", method = RequestMethod.GET)
//	public ModelAndView homeAdmin(){
//		ModelAndView modelAndView = new ModelAndView();
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		User user = userService.findUserByEmail(auth.getName());
//		modelAndView.addObject("userName", "Welcome " + user.getName() + " (" + user.getEmail() + ") with user role " + user.getRoles().iterator().next().getRole() );
//		modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
//		modelAndView.setViewName("admin/home");
//		return modelAndView;
//	}
//
//	@RequestMapping(value="/access-denied", method = RequestMethod.GET)
//	public ModelAndView accessDenied() {
//		ModelAndView modelAndView = new ModelAndView();
//		modelAndView.setViewName("access-denied");
//		return modelAndView;
//	}
//
//}

package com.moviecatalog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.moviecatalog.model.Movie;
import com.moviecatalog.model.Results;
import com.moviecatalog.model.User;
import com.moviecatalog.service.UserService;

@Controller

public class WebController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/index")
	public String showIndex(Model model) {

		RestTemplate restTemplate = new RestTemplate();
		Results response = restTemplate.getForObject(
				"https://api.themoviedb.org/3/discover/movie?api_key=9ae4cb8d6fe7e69356db23d14dd945dd&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1",
				Results.class);
		model.addAttribute("movies", response.getResults());
		return "index";
	}
	
	@GetMapping("/social")
	public String goToProfile(Model model) {
		RestTemplate restTemplate = new RestTemplate();
		Results response = restTemplate.getForObject(
				"https://api.themoviedb.org/3/discover/movie?api_key=9ae4cb8d6fe7e69356db23d14dd945dd&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1",
				Results.class);
		model.addAttribute("movies", response.getResults());
		model.addAttribute("size", response.getResults().size());
		
		return "social";
	}

	@GetMapping("/movie")
	public String renderImage(@RequestParam(name = "id", required = true) String id, Model model) {

		RestTemplate restTemplate = new RestTemplate();
		Results videos = restTemplate.getForObject("https://api.themoviedb.org/3/movie/" + id
				+ "/videos?api_key=9ae4cb8d6fe7e69356db23d14dd945dd&language=es-ES", Results.class);

		Movie movie = restTemplate.getForObject(
				"https://api.themoviedb.org/3/movie/" + id + "?api_key=9ae4cb8d6fe7e69356db23d14dd945dd&language=es-ES",
				Movie.class);

		String genres = movie.toStringGenres();

		Results similarMovies = restTemplate.getForObject(
				"https://api.themoviedb.org/3/discover/movie?api_key=9ae4cb8d6fe7e69356db23d14dd945dd&language=es-ES&region=US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1&with_genres="
						+ genres,
				Results.class);
		
		model.addAttribute("movies", removeMovieFromRecommended(similarMovies, movie.getId()));
		model.addAttribute("trailer", findMovieByTrailer(videos));

		model.addAttribute("movie", movie);
		return "movie";
	}
	
	@RequestMapping("/home")
	public String homePage(Model model) {

		RestTemplate restTemplate = new RestTemplate();
		Results response = restTemplate.getForObject(
				"https://api.themoviedb.org/3/discover/movie?api_key=9ae4cb8d6fe7e69356db23d14dd945dd&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1",
				Results.class);

		model.addAttribute("movies", response.getResults());
		
		//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//User user = userService.findUserByEmail(auth.getName());
		//model.addAttribute("userName","Welcome, " + user.getName());
		return "home";
	}
	
	public List<Movie> removeMovieFromRecommended(Results similarMovies, String movieId) {

		for (int i = 0; i < similarMovies.getResults().size(); i++) {
			if (similarMovies.getResults().get(i).getId().equals(movieId)) {
				similarMovies.getResults().remove(similarMovies.getResults().get(i));
			}
		}
		return similarMovies.getResults();
	}
	
	public Movie findMovieByTrailer(Results var) {

		if (var.getResults().size() == 0) {
			return new Movie();
		}
		for (Movie movie : var.getResults())
			if (movie.getType().equals("Trailer"))
				return movie;
		return var.getResults().get(0);
	}

}

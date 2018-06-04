package com.moviecatalog.controller;

import java.util.Iterator;
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
import org.springframework.web.servlet.ModelAndView;

import com.moviecatalog.model.Movie;
import com.moviecatalog.model.Results;
import com.moviecatalog.model.User;
import com.moviecatalog.service.MovieService;
import com.moviecatalog.service.UserService;

@Controller
public class WebController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private MovieService movieService;
	
	@RequestMapping(value= {"/","/home"}, method = RequestMethod.GET)
	public ModelAndView index(){
		ModelAndView modelAndView = new ModelAndView();
		RestTemplate restTemplate = new RestTemplate();
		Results response = restTemplate.getForObject(
				"https://api.themoviedb.org/3/discover/movie?api_key=9ae4cb8d6fe7e69356db23d14dd945dd&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1",
				Results.class);

		modelAndView.addObject("movies", response.getResults());
		modelAndView.setViewName("home");
		return modelAndView;
	}
	
	@RequestMapping("/user/index")
	public String showIndex(Model model) {
		RestTemplate restTemplate = new RestTemplate();
		Results response = restTemplate.getForObject(
				"https://api.themoviedb.org/3/discover/movie?api_key=9ae4cb8d6fe7e69356db23d14dd945dd&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1",
				Results.class);
		model.addAttribute("movies", response.getResults());
		return "user/index";
	}
	
	@GetMapping("/user/social")
	public String goToProfile(Model model) {
		RestTemplate restTemplate = new RestTemplate();
		Results response = restTemplate.getForObject(
				"https://api.themoviedb.org/3/discover/movie?api_key=9ae4cb8d6fe7e69356db23d14dd945dd&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1",
				Results.class);
		model.addAttribute("movies", response.getResults());
		model.addAttribute("size", response.getResults().size());
		return "user/social";
	}

	@GetMapping("/user/movie")
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
		
		//Indicar si sigue la pelicula, favoritos etc
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    User user = userService.findUserByEmail(auth.getName());
	    
	    int favValue = 0;
	    int viewValue = 0;
	    int pendingValue = 0;
	    
	    Iterator<Movie> iterator = user.getFavourites().iterator();
	    while(iterator.hasNext()) { 
	    	if(iterator.next().getId().equals(id)) //El user la tiene en favs
	    		favValue = 1;
	    }
	    
	    iterator = user.getViews().iterator();
	    while(iterator.hasNext()) 
	     	if(iterator.next().getId().equals(id)) //El user la tiene en favs
	      		viewValue = 1;
	    
	    iterator = user.getPending().iterator();
	    while(iterator.hasNext()) 
	      	if(iterator.next().getId().equals(id)) //El user la tiene en favs
	      		pendingValue = 1;
	    
	    model.addAttribute("Fav",favValue);
	    model.addAttribute("View",viewValue);
	    model.addAttribute("Pending",pendingValue);
		
		return "user/movie";
	}
	
	@GetMapping("/user/save")
	public String saveMovie(@RequestParam(name = "id", required = true) String id, Model model,String action) {
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
		
		if(movieService.findById(id) == null){ //No esta en la base de datos
			movieService.saveMovie(movie);
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    User user = userService.findUserByEmail(auth.getName());
	    
	   if(action.equals("fav"))
		   user.addFavourite(movie);
	   
	   if(action.equals("view"))
		   user.addViews(movie);
	   
	   if(action.equals("pending"))
		   user.addPending(movie);
	   
	   userService.updateUser(user);
		
	   return "user/movie";
	}
	
	@GetMapping("/user/delete")
	public String deleteMovie(@RequestParam(name = "id", required = true) String id, Model model,String action) {
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
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    User user = userService.findUserByEmail(auth.getName());
	    
	    if(action.equals("view")) 
	    	for(Iterator<Movie> iterator = user.getViews().iterator(); iterator.hasNext();){ 
	    		Movie m = iterator.next();
	    		if(m.getId().equals(id))
	    			iterator.remove();	
	    	}
	    if(action.equals("fav")) 
	    	for(Iterator<Movie> iterator = user.getFavourites().iterator(); iterator.hasNext();){ 
	    		Movie m = iterator.next();
	    		if(m.getId().equals(id))
	    			iterator.remove();	
	    	}
	    if(action.equals("pending")) 
	    	for(Iterator<Movie> iterator = user.getPending().iterator(); iterator.hasNext();){ 
	    		Movie m = iterator.next();
	    		if(m.getId().equals(id))
	    			iterator.remove();	
	    	}
	   
	    userService.updateUser(user);
	    return "user/movie";
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

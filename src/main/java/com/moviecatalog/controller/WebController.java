package com.moviecatalog.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
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
	
	@ModelAttribute
	public void addAttributes(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		if(user != null)
			model.addAttribute("sesion", user);
	}

	@RequestMapping("/user/index")
	public String showIndex(Model model) {
		RestTemplate restTemplate = new RestTemplate();
		Results response = restTemplate.getForObject(
				"https://api.themoviedb.org/3/discover/movie?api_key=9ae4cb8d6fe7e69356db23d14dd945dd&region=US&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1",
				Results.class);
		model.addAttribute("movies", response.getResults());
		return "user/index";
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

		// Indicar si sigue la pelicula, favoritos etc
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());

		model.addAttribute("Fav", isMovieOnList(id, user.getFavourites()));
		model.addAttribute("View", isMovieOnList(id, user.getViews()));
		model.addAttribute("Pending", isMovieOnList(id, user.getPending()));

		return "user/movie";
	}
	
	@GetMapping("user/search")
	public String searchMovies(@RequestParam(name = "title", required = true) String movieTitle, Model model) {
		RestTemplate restTemplate = new RestTemplate();
		Results response = restTemplate.getForObject(
				"https://api.themoviedb.org/3/search/movie?api_key=9ae4cb8d6fe7e69356db23d14dd945dd&region=US&language=en-US&query=" + movieTitle + "&page=1&include_adult=false&region=US",
				Results.class);
		model.addAttribute("movies", response.getResults());
		model.addAttribute("title", movieTitle);
		model.addAttribute("size", response.getResults().size());
		return "user/search";
	}
	
	@GetMapping("user/genre")
	public String searchGenres(@RequestParam(name = "id", required = true) String genreId,@RequestParam(name = "name", required = true) String genreName, Model model) {
		RestTemplate restTemplate = new RestTemplate();
		Results response = restTemplate.getForObject(
				"https://api.themoviedb.org/3/discover/movie?api_key=9ae4cb8d6fe7e69356db23d14dd945dd&language=en-US&sort_by=popularity.desc&region=US&include_adult=false&include_video=false&page=1&with_genres="+ genreId+"",
				Results.class);
		model.addAttribute("movies", response.getResults());
		model.addAttribute("title", genreName);
		return "user/search";
	}
	

	private boolean isMovieOnList(String id, Set<Movie> list) {
		Iterator<Movie> iterator = list.iterator();
		while (iterator.hasNext()) {
			if (iterator.next().getId().equals(id))
				return true;
		}
		return false;
	}

	@GetMapping("/user/save")
	public ModelAndView saveMovie(@RequestParam(name = "id", required = true) String id, Model model, String action) {
		RestTemplate restTemplate = new RestTemplate();
		Movie movie = restTemplate.getForObject(
				"https://api.themoviedb.org/3/movie/" + id + "?api_key=9ae4cb8d6fe7e69356db23d14dd945dd&language=es-ES",
				Movie.class);

		if (movieService.findById(id) == null) { // No esta en la base de datos
			movieService.saveMovie(movie);
		}

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());

		if (action.equals("fav"))
			user.addFavourite(movie);

		if (action.equals("view"))
			user.addViews(movie);

		if (action.equals("pending"))
			user.addPending(movie);

		userService.update(user);

		ModelAndView mav = new ModelAndView(new RedirectView("/user/movie", true));
		mav.addObject("id", id);

		return mav;
	}

	@GetMapping("/user/delete")
	public ModelAndView deleteMovie(@RequestParam(name = "id", required = true) String id, Model model, String action) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());

		if (action.equals("view"))
			removeFromList(id, user.getViews());
		if (action.equals("fav"))
			removeFromList(id, user.getFavourites());
		if (action.equals("pending"))
			removeFromList(id, user.getPending());

		userService.update(user);

		ModelAndView mav = new ModelAndView(new RedirectView("/user/movie", true));
		mav.addObject("id", id);

		return mav;
	}

	private void removeFromList(String id, Set<Movie> list) {
		for (Iterator<Movie> iterator = list.iterator(); iterator.hasNext();) {
			Movie m = iterator.next();
			if (m.getId().equals(id))
				iterator.remove();
		}
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

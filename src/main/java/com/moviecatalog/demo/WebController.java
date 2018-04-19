package com.moviecatalog.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.moviecatalog.demo.Results;

@Controller

public class WebController {


	@RequestMapping("/")
    public String helloTwitter(Model model) {
        
    	 RestTemplate restTemplate = new RestTemplate();
         Results response = restTemplate.getForObject("https://api.themoviedb.org/3/discover/movie?api_key=9ae4cb8d6fe7e69356db23d14dd945dd&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1", Results.class);
        //Commit prueba
         model.addAttribute("d");
         model.addAttribute("movies", response.getResults());
         return "index";
    }
    
    @GetMapping("/movie")
	public String renderImage(Model model) {

		return "movie";
	}


}

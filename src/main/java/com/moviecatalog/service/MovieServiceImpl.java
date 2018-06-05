package com.moviecatalog.service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moviecatalog.model.Movie;
import com.moviecatalog.model.Role;
import com.moviecatalog.model.User;
import com.moviecatalog.repository.MovieRepository;

@Service("movieService")
public class MovieServiceImpl implements MovieService{
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Override
	public Movie findById(String id) {
		return movieRepository.findById(id);
	}

	@Override
	public void saveMovie(Movie movie) {
        movieRepository.save(movie);
	}
}

package com.moviecatalog.service;

import com.moviecatalog.model.Movie;

public interface MovieService {
	public Movie findById(String id);
	public void saveMovie(Movie movie);
}

package com.moviecatalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.moviecatalog.model.Movie;

@Repository("movieRepository")
public interface MovieRepository extends JpaRepository<Movie, Integer>{

}

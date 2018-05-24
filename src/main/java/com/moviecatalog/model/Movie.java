package com.moviecatalog.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="movie")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "movie_id")
    private String id;
	
	private float vote_average;
	
	@Column(name = "keyy")
	private String key;
	private String title;
	private String poster_path;
	private String backdrop_path;
	private String overview;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date release_date;
	private String type;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public float getVote_average() {
		return vote_average;
	}

	public void setVote_average(float vote_average) {
		this.vote_average = vote_average;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPoster_path() {
		return poster_path;
	}

	public void setPoster_path(String poster_path) {
		this.poster_path = poster_path;
	}

	public String getBackdrop_path() {
		return backdrop_path;
	}

	public void setBackdrop_path(String backdrop_path) {
		this.backdrop_path = backdrop_path;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public Date getRelease_date() {
		return release_date;
	}

	public void setRelease_date(Date release_date) {
		this.release_date = release_date;
	}

	public String toString() {
		return "Title: " + title + "{" + "Overview='" + overview + '\'' + "Vote average='" + vote_average + '\''
				+ ", Release date=" + release_date.toString() + '}';
	}
	
}

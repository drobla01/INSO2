package com.moviecatalog.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="movie")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {

	@Id
    @Column(name = "movie_id")
    private String id;
	
	private float vote_average;
	@Column(name = "keyy")
	private String key;
	private String title;
	private String poster_path;
	private String backdrop_path;
	
	@Transient
	private String overview;
	
	@Transient
	private List<Genre> genres;
	
	@Transient
	private String runtime;
	
	@Transient
	private String tagline;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date release_date;
	
	@Transient
	private String type;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "movie_comment", joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "comment_id"))
	private Set<Comment> comments;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public float getVote_average() {
		return vote_average;
	}

	public void setVote_average(float vote_average) {
		this.vote_average = vote_average;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String toString() {
		return "Title: " + title + "{" + "Overview='" + overview + '\'' + "Vote average='" + vote_average + '\''
				+ ", Release date=" + release_date.toString() + '}';
	}
	
	public String getRuntime() {
		return runtime;
	}
	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}
	public String getTagline() {
		return tagline;
	}
	public void setTagline(String tagline) {
		this.tagline = tagline;
	}
	public String toStringGenres() {
		String var = "";

//		for (int i = 0; i < genres.size(); i++) {
//			if (i != genres.size() - 1) {
//				var += genres.get(i).getId() + ",";
//			} else {
//				var += genres.get(i).getId();
//			}
//		}
		var += genres.get(0).getId() +",";
		var += genres.get(1).getId();
		return var;
	}
	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}
	public List<Genre> getGenres() {
		return genres;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}
	
}

	
	
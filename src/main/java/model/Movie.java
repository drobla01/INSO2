package model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {

	private float vote_average;
	private String key;
	private String title;
	private String poster_path;
	private String backdrop_path;
	private String overview;
	private Date release_date;
	private String type;
	private String id;
	private List<Genre> genres;
	private String runtime;
	private String tagline;
	
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public List<Genre> getGenres() {
		return genres;
	}

	public void setGenres(List<Genre> genres) {
		this.genres = genres;
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

	public String toString() {
		return "Title: " + title + "{" + "Overview='" + overview + '\'' + "Vote average='" + vote_average + '\''
				+ ", Release date=" + release_date.toString() + '}';
	}

}
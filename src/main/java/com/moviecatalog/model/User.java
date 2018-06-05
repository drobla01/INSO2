package com.moviecatalog.model;

import java.util.Date;
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

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.moviecatalog.model.Role;

@Entity // This tells Hibernate to make a table out of this class
@Table(name="user")
public class User {
	
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "user_id")
    private Integer id;

    private String name;
    
    private String password;

    private String email;
    
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date creation;
    
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updated;
    
	private int active;
	
    @ManyToMany
    @JoinTable(name = "user_follows", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "id_user"))
    private Set<User> follows;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_movie", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "movie_id"))
	private Set<Movie> favourites;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "vistas", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "movie_id"))
	private Set<Movie> views;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "pendientes", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "movie_id"))
	private Set<Movie> pending;
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreation() {
		return creation;
	}

	public void setCreation(Date creation) {
		this.creation = creation;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<User> getFollows() {
		return follows;
	}

	public void setFollows(Set<User> follows) {
		this.follows = follows;
	}

	public Set<Movie> getFavourites() {
		return favourites;
	}

	public void setFavourites(Set<Movie> favourites) {
		this.favourites = favourites;
	}
	
	public void addFavourite(Movie movie) {
		this.favourites.add(movie);
	}
	
	public void addPending(Movie movie) {
		this.pending.add(movie);
	}
	
	public void addViews(Movie movie) {
		this.views.add(movie);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Set<Movie> getViews() {
		return views;
	}

	public void setViews(Set<Movie> views) {
		this.views = views;
	}

	public Set<Movie> getPending() {
		return pending;
	}

	public void setPending(Set<Movie> pending) {
		this.pending = pending;
	}
}
package com.moviecatalog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "follow")
public class Follow {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id_user_followed")
	private int id_user_followed;

	public int getId_user_followed() {
		return id_user_followed;
	}

	public void setId_user_followed(int id_user_followed) {
		this.id_user_followed = id_user_followed;
	}

}


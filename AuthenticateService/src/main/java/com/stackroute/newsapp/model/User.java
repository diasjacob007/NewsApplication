package com.stackroute.newsapp.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "user")
public class User {

	@Id
	@Column(name = "userId")
	private String userId;
	
	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	@Column(name = "userRole")
	private String userRole;

	@Column(name = "firstname")
	private String firstname;

	@Column(name = "lastname")
	private String lastname;

	@Column(name = "password")
	private String password;

	@Column(name = "createddate")
	@Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
	private Date createddate;

	public User() {
		super();
	}

	public User(String userId,String userRole,Date createddate, String firstname, String lastname, String password) {
		this.userId = userId;
		this.userRole = userRole;
		this.createddate = createddate;
		this.firstname = firstname;
		this.lastname = lastname;
		this.password = password;		
	}

	

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}

	public Date getCreateddate() {
		return createddate;
	}

}

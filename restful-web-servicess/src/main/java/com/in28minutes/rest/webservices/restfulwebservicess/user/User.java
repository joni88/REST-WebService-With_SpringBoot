package com.in28minutes.rest.webservices.restfulwebservicess.user;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description="AL information about the users. ")
@Entity // to make class used JPA
public class User {

	@Id
	@GeneratedValue
	private Integer id;
	
	@Size(min=2, message="Name should have at least 2 charachters")
	@ApiModelProperty(notes="Name should have at least 2 charachters.")
	private String name;
	
	@Past()
	@ApiModelProperty(notes="Birth date should be in the past.")
	private Date Dob;
	
	@OneToMany(mappedBy="user")
	private List<Post> post;
	
	protected User(){}
	
	public User(Integer id, String name, Date date) {
		super();
		this.id = id;
		this.name = name;
		this.Dob = date;
	}
	
	
	public List<Post> getPost() {
		return post;
	}

	public void setPost(List<Post> post) {
		this.post = post;
	}

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
	public Date getDob() {
		return Dob;
	}
	public void setDob(Date dob) {
		Dob = dob;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", Dob=" + Dob + "]";
	}
	
	
	
}

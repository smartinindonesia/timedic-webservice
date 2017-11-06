package com.servicetimedic.jwt.domain.solr;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

@SolrDocument(solrCoreName = "User")
public class User {
	@Id
	@Field
	private Long id;

	@Field
	private String name;
	
	@Field
	private String username;
	
	@Field
	private String password;
	
	@Field
	private List<String> roles = new ArrayList<>();
	
	public User() {
	}
	
	public User(Long id, String name, String username, String password, List roles){
		this.id = id;
		this.name = name;
		this.username = username;
		this.password = password;
		this.roles = roles;
	}
		
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "User [id=" + this.id + ", name=" + this.name + ","
				+ " username=" + this.username + ", password="+ this.password+"]";
	}
}

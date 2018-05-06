/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetimedic.jwt.domain.december;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "contact_us")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ContactUs implements Serializable {
    
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    
    @Size(max = 50)
    @Column(name = "phone_office")
    private String phoneOffice;
    
    @Size(max = 50)
    @Column(name = "mobile_phone")
    private String mobilePhone;
    
    @Size(max = 50)
    @Column(name = "email")
    private String email;
    
    @Size(max = 100)
    @Column(name = "facebook_link")
    private String facebookLink;
    
    @Size(max = 100)
    @Column(name = "twitter_link")
    private String twitterLink;
    
    @Size(max = 100)
    @Column(name = "instagram_link")
    private String instagramLink;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhoneOffice() {
		return phoneOffice;
	}

	public void setPhoneOffice(String phoneOffice) {
		this.phoneOffice = phoneOffice;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFacebookLink() {
		return facebookLink;
	}

	public void setFacebookLink(String facebookLink) {
		this.facebookLink = facebookLink;
	}

	public String getTwitterLink() {
		return twitterLink;
	}

	public void setTwitterLink(String twitterLink) {
		this.twitterLink = twitterLink;
	}

	public String getInstagramLink() {
		return instagramLink;
	}

	public void setInstagramLink(String instagramLink) {
		this.instagramLink = instagramLink;
	}

	@Override
	public String toString() {
		return "ContactUs [id=" + id + ", phoneOffice=" + phoneOffice
				+ ", mobilePhone=" + mobilePhone + ", email=" + email
				+ ", facebookLink=" + facebookLink + ", twitterLink="
				+ twitterLink + ", instagramLink=" + instagramLink + "]";
	}
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetimedic.jwt.domain.december;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;


@Entity
@Table(name = "app_user")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AppUser implements UserDetails, Serializable {
    
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    
    @Size(max = 255)
    @Column(name = "address")
    private String address;
    
    @Column(name = "date_birth")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateBirth;
    
    @Size(max = 255)
    @Column(name = "email")
    private String email;
    
    @Size(max = 255)
    @Column(name = "front_name")
    private String frontName;
    
    @Size(max = 255)
    @Column(name = "last_name")
    private String lastName;
    
    @Size(max = 255)
    @Column(name = "middle_name")
    private String middleName;
    
    @Size(max = 255)
    @Column(name = "password")
    private String password;
    
    @Column(name = "phone_number")
    private String phoneNumber;
    
    @Size(max = 255)
    @Column(name = "photo_path")
    private String photoPath;
    
    @Size(max = 255)
    @Column(name = "username")
    private String username;
    
    @Column(name = "first_registration_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date firstRegistrationDate;
   
    @Column(name = "latitude")
    private Float latitude;
    
    @Column(name = "longitude")
    private Float longitude;
    
    @Column(name = "fcm_token")
    private String fcmToken;
    
    @Size(max = 10)
    @Column(name = "user_code")
    private String userCode;
    
    @Column(name = "firebase_id_google")
    private String firebaseIdGoogle ;
    
    @Column(name = "firebase_id_facebook")
    private String firebaseIdFacebook ;
    
    @Size(max = 50)
    @Column(name = "gender")
    private String gender ;
    
    @Size(max = 50)
    @Column(name = "religion")
    private String religion;
    
    @OneToMany(mappedBy = "idAppUser")
    private List<HomecareCaregiverRate> homecareCaregiverRateList;
    
    @OneToMany(mappedBy = "idAppUser")
    private List<HomecarePatient> homecarePatientList;
    
    @OneToMany(mappedBy = "appUserId")
    private List<AppUserSocialAuth> appUserSocialAuthList;
    
    @OneToMany(mappedBy = "idAppUser")
    private List<GlobalIdNumber> globalIdNumberList;

    @ElementCollection
    //@OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    private List<String> roles = new ArrayList<>();

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public AppUser() {
    }

    public AppUser(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(Date dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public String getFrontName() {
        return frontName;
    }

    public void setFrontName(String frontName) {
        this.frontName = frontName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles)
        {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getFirstRegistrationDate() {
        return firstRegistrationDate;
    }

    public void setFirstRegistrationDate(Date firstRegistrationDate) {
        this.firstRegistrationDate = firstRegistrationDate;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }
    
    public String getFcmToken() {
		return fcmToken;
	}
    
	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public void setFcmToken(String fcmToken) {
		this.fcmToken = fcmToken;
	}

	public String getFirebaseIdGoogle() {
		return firebaseIdGoogle;
	}

	public void setFirebaseIdGoogle(String firebaseIdGoogle) {
		this.firebaseIdGoogle = firebaseIdGoogle;
	}
	
	public String getFirebaseIdFacebook() {
		return firebaseIdFacebook;
	}

	public void setFirebaseIdFacebook(String firebaseIdFacebook) {
		this.firebaseIdFacebook = firebaseIdFacebook;
	}
	
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@XmlTransient
    @JsonIgnore
    public List<HomecareCaregiverRate> getHomecareCaregiverRateList() {
        return homecareCaregiverRateList;
    }
    
    @JsonIgnore
    public void setHomecareCaregiverRateList(List<HomecareCaregiverRate> homecareCaregiverRateList) {
        this.homecareCaregiverRateList = homecareCaregiverRateList;
    }

    @XmlTransient
    @JsonIgnore
    public List<HomecarePatient> getHomecarePatientList() {
        return homecarePatientList;
    }

    @XmlTransient
    @JsonIgnore
    public void setHomecarePatientList(List<HomecarePatient> homecarePatientList) {
        this.homecarePatientList = homecarePatientList;
    }

    @XmlTransient
    @JsonIgnore
    public List<AppUserSocialAuth> getAppUserSocialAuthList() {
        return appUserSocialAuthList;
    }

    @XmlTransient
    @JsonIgnore
    public void setAppUserSocialAuthList(List<AppUserSocialAuth> appUserSocialAuthList) {
        this.appUserSocialAuthList = appUserSocialAuthList;
    }

    @XmlTransient
    @JsonIgnore
    public List<GlobalIdNumber> getGlobalIdNumberList() {
        return globalIdNumberList;
    }

    @JsonIgnore
    public void setGlobalIdNumberList(List<GlobalIdNumber> globalIdNumberList) {
        this.globalIdNumberList = globalIdNumberList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AppUser)) {
            return false;
        }
        AppUser other = (AppUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "trial.entity.AppUser[ id=" + id + " ]";
    }
    
}

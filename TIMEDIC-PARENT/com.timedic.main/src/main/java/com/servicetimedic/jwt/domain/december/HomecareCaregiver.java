/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetimedic.jwt.domain.december;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "homecare_caregiver")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class HomecareCaregiver implements UserDetails, Serializable {
    
	private static final long serialVersionUID = 1L;
    
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    
	@Size(max = 255)
    @Column(name = "address")
    private String address;
    
	@Column(name = "date_of_birth")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfBirth;
    
	@Size(max = 255)
    @Column(name = "email")
    private String email;
    
	@Size(max = 255)
    @Column(name = "front_name")
    private String frontName;
    
	@Size(max = 255)
    @Column(name = "middle_name")
    private String middleName;
    
	@Size(max = 255)
    @Column(name = "last_name")
    private String lastName;
    
	@Size(max = 255)
    @Column(name = "password")
    private String password;
    
	@Size(max = 32)
    @Column(name = "phone_number")
    private String phoneNumber;
    
	@Size(max = 255)
    @Column(name = "photo_path")
    private String photoPath;
    
	@Size(max = 255)
    @Column(name = "sipp")
    private String sipp;
    
	@Size(max = 255)
    @Column(name = "register_nurse_number")
    private String registerNurseNumber;
    
	@Size(max = 255)
    @Column(name = "username")
    private String username;
    
	@Column(name = "first_registration_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date firstRegistrationDate;
    
	@Size(max = 255)
    @Column(name = "employee_id_number")
    private String employeeIdNumber;
	
	@Column(name = "fcm_token")
    private String fcmToken;
    
	@Column(name = "firebase_id_google")
    private String firebaseIdGoogle ;
    
    @Column(name = "firebase_id_facebook")
    private String firebaseIdFacebook ;
    
    @Column(name = "gender")
    private String gender ;
    
	@OneToMany(mappedBy = "idHomecareCaregiver")
    private List<HomecareCaregiverRate> homecareCaregiverRateList;
    
	@OneToMany(mappedBy = "idHomecareCaregiver")
    private List<HomecareCaregiverSchedule> homecareCaregiverScheduleList;
    
	@JoinColumn(name = "id_homecare_clinic", referencedColumnName = "id")
    @ManyToOne
    private HomecareHomecareClinic idHomecareClinic;
    
	@JoinColumn(name = "id_caregiver_status", referencedColumnName = "id")
    @ManyToOne
    private HomecareCaregiverStatus idCaregiverStatus;
    
	@JoinColumn(name = "id_laboratory_clinic", referencedColumnName = "id")
    @ManyToOne
    private LaboratoryLaboratoryClinic idLaboratoryClinic;
    
	@OneToMany(mappedBy = "idCaregiver")
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles)
        {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
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

    public HomecareCaregiver() {
    }

    public HomecareCaregiver(Long id) {
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFrontName() {
        return frontName;
    }

    public void setFrontName(String frontName) {
        this.frontName = frontName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getSipp() {
        return sipp;
    }

    public void setSipp(String sipp) {
        this.sipp = sipp;
    }

    public String getRegisterNurseNumber() {
        return registerNurseNumber;
    }

    public void setRegisterNurseNumber(String registerNurseNumber) {
        this.registerNurseNumber = registerNurseNumber;
    }

    public String getUsername() {
        return username;
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

    public String getEmployeeIdNumber() {
        return employeeIdNumber;
    }

    public void setEmployeeIdNumber(String employeeIdNumber) {
        this.employeeIdNumber = employeeIdNumber;
    }
    
    public String getFcmToken() {
		return fcmToken;
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

    public void setHomecareCaregiverRateList(List<HomecareCaregiverRate> homecareCaregiverRateList) {
        this.homecareCaregiverRateList = homecareCaregiverRateList;
    }

    @XmlTransient
    @JsonIgnore
    public List<HomecareCaregiverSchedule> getHomecareCaregiverScheduleList() {
        return homecareCaregiverScheduleList;
    }

    public void setHomecareCaregiverScheduleList(List<HomecareCaregiverSchedule> homecareCaregiverScheduleList) {
        this.homecareCaregiverScheduleList = homecareCaregiverScheduleList;
    }

    @JsonIgnore
    public HomecareHomecareClinic getIdHomecareClinic() {
        return idHomecareClinic;
    }

    public void setIdHomecareClinic(HomecareHomecareClinic idHomecareClinic) {
        this.idHomecareClinic = idHomecareClinic;
    }

    //@JsonIgnore
    public HomecareCaregiverStatus getIdCaregiverStatus() {
        return idCaregiverStatus;
    }

    public void setIdCaregiverStatus(HomecareCaregiverStatus idCaregiverStatus) {
        this.idCaregiverStatus = idCaregiverStatus;
    }

    @JsonIgnore
    public LaboratoryLaboratoryClinic getIdLaboratoryClinic() {
        return idLaboratoryClinic;
    }

    public void setIdLaboratoryClinic(LaboratoryLaboratoryClinic idLaboratoryClinic) {
        this.idLaboratoryClinic = idLaboratoryClinic;
    }

    @XmlTransient
    @JsonIgnore
    public List<GlobalIdNumber> getGlobalIdNumberList() {
        return globalIdNumberList;
    }

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
        if (!(object instanceof HomecareCaregiver)) {
            return false;
        }
        HomecareCaregiver other = (HomecareCaregiver) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "trial.entity.HomecareCaregiver[ id=" + id + " ]";
    }
    
}

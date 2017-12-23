/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetimedic.jwt.domain.december;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Hafid
 */
@Entity
@Table(name = "app_user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AppUser.findAll", query = "SELECT a FROM AppUser a"),
    @NamedQuery(name = "AppUser.findById", query = "SELECT a FROM AppUser a WHERE a.id = :id"),
    @NamedQuery(name = "AppUser.findByAddress", query = "SELECT a FROM AppUser a WHERE a.address = :address"),
    @NamedQuery(name = "AppUser.findByDateBirth", query = "SELECT a FROM AppUser a WHERE a.dateBirth = :dateBirth"),
    @NamedQuery(name = "AppUser.findByEmail", query = "SELECT a FROM AppUser a WHERE a.email = :email"),
    @NamedQuery(name = "AppUser.findByFrontName", query = "SELECT a FROM AppUser a WHERE a.frontName = :frontName"),
    @NamedQuery(name = "AppUser.findByLastName", query = "SELECT a FROM AppUser a WHERE a.lastName = :lastName"),
    @NamedQuery(name = "AppUser.findByMiddleName", query = "SELECT a FROM AppUser a WHERE a.middleName = :middleName"),
    @NamedQuery(name = "AppUser.findByPassword", query = "SELECT a FROM AppUser a WHERE a.password = :password"),
    @NamedQuery(name = "AppUser.findByPhoneNumber", query = "SELECT a FROM AppUser a WHERE a.phoneNumber = :phoneNumber"),
    @NamedQuery(name = "AppUser.findByPhotoPath", query = "SELECT a FROM AppUser a WHERE a.photoPath = :photoPath"),
    @NamedQuery(name = "AppUser.findByUsername", query = "SELECT a FROM AppUser a WHERE a.username = :username"),
    @NamedQuery(name = "AppUser.findByFirstRegistrationDate", query = "SELECT a FROM AppUser a WHERE a.firstRegistrationDate = :firstRegistrationDate"),
    @NamedQuery(name = "AppUser.findByLatitude", query = "SELECT a FROM AppUser a WHERE a.latitude = :latitude"),
    @NamedQuery(name = "AppUser.findByLongitude", query = "SELECT a FROM AppUser a WHERE a.longitude = :longitude")})
public class AppUser implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 255)
    @Column(name = "address")
    private String address;
    @Column(name = "date_birth")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateBirth;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
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
    private Integer phoneNumber;
    @Size(max = 255)
    @Column(name = "photo_path")
    private String photoPath;
    @Size(max = 255)
    @Column(name = "username")
    private String username;
    @Column(name = "first_registration_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date firstRegistrationDate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "latitude")
    private Float latitude;
    @Column(name = "longitude")
    private Float longitude;
    @OneToMany(mappedBy = "idAppUser")
    private List<HomecareCaregiverRate> homecareCaregiverRateList;
    @OneToMany(mappedBy = "idAppUser")
    private List<HomecarePatient> homecarePatientList;
    @OneToMany(mappedBy = "appUserId")
    private List<AppUserRoles> appUserRolesList;
    @OneToMany(mappedBy = "appUserId")
    private List<AppUserSocialAuth> appUserSocialAuthList;
    @OneToMany(mappedBy = "idAppUser")
    private List<GlobalIdNumber> globalIdNumberList;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
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

    @XmlTransient
    public List<HomecareCaregiverRate> getHomecareCaregiverRateList() {
        return homecareCaregiverRateList;
    }

    public void setHomecareCaregiverRateList(List<HomecareCaregiverRate> homecareCaregiverRateList) {
        this.homecareCaregiverRateList = homecareCaregiverRateList;
    }

    @XmlTransient
    public List<HomecarePatient> getHomecarePatientList() {
        return homecarePatientList;
    }

    public void setHomecarePatientList(List<HomecarePatient> homecarePatientList) {
        this.homecarePatientList = homecarePatientList;
    }

    @XmlTransient
    public List<AppUserRoles> getAppUserRolesList() {
        return appUserRolesList;
    }

    public void setAppUserRolesList(List<AppUserRoles> appUserRolesList) {
        this.appUserRolesList = appUserRolesList;
    }

    @XmlTransient
    public List<AppUserSocialAuth> getAppUserSocialAuthList() {
        return appUserSocialAuthList;
    }

    public void setAppUserSocialAuthList(List<AppUserSocialAuth> appUserSocialAuthList) {
        this.appUserSocialAuthList = appUserSocialAuthList;
    }

    @XmlTransient
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

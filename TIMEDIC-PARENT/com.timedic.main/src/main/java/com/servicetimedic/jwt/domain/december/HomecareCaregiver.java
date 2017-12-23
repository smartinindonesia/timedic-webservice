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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "homecare_caregiver")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HomecareCaregiver.findAll", query = "SELECT h FROM HomecareCaregiver h"),
    @NamedQuery(name = "HomecareCaregiver.findById", query = "SELECT h FROM HomecareCaregiver h WHERE h.id = :id"),
    @NamedQuery(name = "HomecareCaregiver.findByAddress", query = "SELECT h FROM HomecareCaregiver h WHERE h.address = :address"),
    @NamedQuery(name = "HomecareCaregiver.findByDateOfBirth", query = "SELECT h FROM HomecareCaregiver h WHERE h.dateOfBirth = :dateOfBirth"),
    @NamedQuery(name = "HomecareCaregiver.findByEmail", query = "SELECT h FROM HomecareCaregiver h WHERE h.email = :email"),
    @NamedQuery(name = "HomecareCaregiver.findByFrontName", query = "SELECT h FROM HomecareCaregiver h WHERE h.frontName = :frontName"),
    @NamedQuery(name = "HomecareCaregiver.findByMiddleName", query = "SELECT h FROM HomecareCaregiver h WHERE h.middleName = :middleName"),
    @NamedQuery(name = "HomecareCaregiver.findByLastName", query = "SELECT h FROM HomecareCaregiver h WHERE h.lastName = :lastName"),
    @NamedQuery(name = "HomecareCaregiver.findByPassword", query = "SELECT h FROM HomecareCaregiver h WHERE h.password = :password"),
    @NamedQuery(name = "HomecareCaregiver.findByPhoneNumber", query = "SELECT h FROM HomecareCaregiver h WHERE h.phoneNumber = :phoneNumber"),
    @NamedQuery(name = "HomecareCaregiver.findByPhotoPath", query = "SELECT h FROM HomecareCaregiver h WHERE h.photoPath = :photoPath"),
    @NamedQuery(name = "HomecareCaregiver.findBySipp", query = "SELECT h FROM HomecareCaregiver h WHERE h.sipp = :sipp"),
    @NamedQuery(name = "HomecareCaregiver.findByRegisterNurseNumber", query = "SELECT h FROM HomecareCaregiver h WHERE h.registerNurseNumber = :registerNurseNumber"),
    @NamedQuery(name = "HomecareCaregiver.findByUsername", query = "SELECT h FROM HomecareCaregiver h WHERE h.username = :username"),
    @NamedQuery(name = "HomecareCaregiver.findByFirstRegistrationDate", query = "SELECT h FROM HomecareCaregiver h WHERE h.firstRegistrationDate = :firstRegistrationDate"),
    @NamedQuery(name = "HomecareCaregiver.findByEmployeeIdNumber", query = "SELECT h FROM HomecareCaregiver h WHERE h.employeeIdNumber = :employeeIdNumber")})
public class HomecareCaregiver implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 255)
    @Column(name = "address")
    private String address;
    @Column(name = "date_of_birth")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfBirth;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
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
    @OneToMany(mappedBy = "idHomecareCaregiver")
    private List<HomecareCaregiverRate> homecareCaregiverRateList;
    @OneToMany(mappedBy = "idHomecareCaregiver")
    private List<HomecareCaregiverSchedule> homecareCaregiverScheduleList;
    @OneToMany(mappedBy = "homecareCaregiverId")
    private List<HomecareCaregiverRoles> homecareCaregiverRolesList;
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

    @XmlTransient
    public List<HomecareCaregiverRate> getHomecareCaregiverRateList() {
        return homecareCaregiverRateList;
    }

    public void setHomecareCaregiverRateList(List<HomecareCaregiverRate> homecareCaregiverRateList) {
        this.homecareCaregiverRateList = homecareCaregiverRateList;
    }

    @XmlTransient
    public List<HomecareCaregiverSchedule> getHomecareCaregiverScheduleList() {
        return homecareCaregiverScheduleList;
    }

    public void setHomecareCaregiverScheduleList(List<HomecareCaregiverSchedule> homecareCaregiverScheduleList) {
        this.homecareCaregiverScheduleList = homecareCaregiverScheduleList;
    }

    @XmlTransient
    public List<HomecareCaregiverRoles> getHomecareCaregiverRolesList() {
        return homecareCaregiverRolesList;
    }

    public void setHomecareCaregiverRolesList(List<HomecareCaregiverRoles> homecareCaregiverRolesList) {
        this.homecareCaregiverRolesList = homecareCaregiverRolesList;
    }

    public HomecareHomecareClinic getIdHomecareClinic() {
        return idHomecareClinic;
    }

    public void setIdHomecareClinic(HomecareHomecareClinic idHomecareClinic) {
        this.idHomecareClinic = idHomecareClinic;
    }

    public HomecareCaregiverStatus getIdCaregiverStatus() {
        return idCaregiverStatus;
    }

    public void setIdCaregiverStatus(HomecareCaregiverStatus idCaregiverStatus) {
        this.idCaregiverStatus = idCaregiverStatus;
    }

    public LaboratoryLaboratoryClinic getIdLaboratoryClinic() {
        return idLaboratoryClinic;
    }

    public void setIdLaboratoryClinic(LaboratoryLaboratoryClinic idLaboratoryClinic) {
        this.idLaboratoryClinic = idLaboratoryClinic;
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

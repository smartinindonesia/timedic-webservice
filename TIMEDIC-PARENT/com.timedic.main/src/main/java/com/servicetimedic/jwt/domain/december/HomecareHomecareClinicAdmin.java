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
@Table(name = "homecare_homecare_clinic_admin")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HomecareHomecareClinicAdmin.findAll", query = "SELECT h FROM HomecareHomecareClinicAdmin h"),
    @NamedQuery(name = "HomecareHomecareClinicAdmin.findById", query = "SELECT h FROM HomecareHomecareClinicAdmin h WHERE h.id = :id"),
    @NamedQuery(name = "HomecareHomecareClinicAdmin.findByUsername", query = "SELECT h FROM HomecareHomecareClinicAdmin h WHERE h.username = :username"),
    @NamedQuery(name = "HomecareHomecareClinicAdmin.findByPassword", query = "SELECT h FROM HomecareHomecareClinicAdmin h WHERE h.password = :password"),
    @NamedQuery(name = "HomecareHomecareClinicAdmin.findByAddress", query = "SELECT h FROM HomecareHomecareClinicAdmin h WHERE h.address = :address"),
    @NamedQuery(name = "HomecareHomecareClinicAdmin.findByDateOfBirth", query = "SELECT h FROM HomecareHomecareClinicAdmin h WHERE h.dateOfBirth = :dateOfBirth"),
    @NamedQuery(name = "HomecareHomecareClinicAdmin.findByEmail", query = "SELECT h FROM HomecareHomecareClinicAdmin h WHERE h.email = :email"),
    @NamedQuery(name = "HomecareHomecareClinicAdmin.findByFirstName", query = "SELECT h FROM HomecareHomecareClinicAdmin h WHERE h.firstName = :firstName"),
    @NamedQuery(name = "HomecareHomecareClinicAdmin.findByMiddleName", query = "SELECT h FROM HomecareHomecareClinicAdmin h WHERE h.middleName = :middleName"),
    @NamedQuery(name = "HomecareHomecareClinicAdmin.findByLastName", query = "SELECT h FROM HomecareHomecareClinicAdmin h WHERE h.lastName = :lastName"),
    @NamedQuery(name = "HomecareHomecareClinicAdmin.findByPhoneNumber", query = "SELECT h FROM HomecareHomecareClinicAdmin h WHERE h.phoneNumber = :phoneNumber"),
    @NamedQuery(name = "HomecareHomecareClinicAdmin.findByFirstRegistrationDate", query = "SELECT h FROM HomecareHomecareClinicAdmin h WHERE h.firstRegistrationDate = :firstRegistrationDate"),
    @NamedQuery(name = "HomecareHomecareClinicAdmin.findByIdNumber", query = "SELECT h FROM HomecareHomecareClinicAdmin h WHERE h.idNumber = :idNumber")})
public class HomecareHomecareClinicAdmin implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 255)
    @Column(name = "username")
    private String username;
    @Size(max = 255)
    @Column(name = "password")
    private String password;
    @Size(max = 255)
    @Column(name = "address")
    private String address;
    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 255)
    @Column(name = "email")
    private String email;
    @Size(max = 255)
    @Column(name = "first_name")
    private String firstName;
    @Size(max = 255)
    @Column(name = "middle_name")
    private String middleName;
    @Size(max = 255)
    @Column(name = "last_name")
    private String lastName;
    @Size(max = 255)
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "first_registration_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date firstRegistrationDate;
    @Size(max = 255)
    @Column(name = "id_number")
    private String idNumber;
    @JoinColumn(name = "id_homecare_clinic", referencedColumnName = "id")
    @ManyToOne
    private HomecareHomecareClinic idHomecareClinic;
    @OneToMany(mappedBy = "homecareHomecareClinicAdminId")
    private List<HomecareHomecareClinicAdminRoles> homecareHomecareClinicAdminRolesList;

    public HomecareHomecareClinicAdmin() {
    }

    public HomecareHomecareClinicAdmin(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getFirstRegistrationDate() {
        return firstRegistrationDate;
    }

    public void setFirstRegistrationDate(Date firstRegistrationDate) {
        this.firstRegistrationDate = firstRegistrationDate;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public HomecareHomecareClinic getIdHomecareClinic() {
        return idHomecareClinic;
    }

    public void setIdHomecareClinic(HomecareHomecareClinic idHomecareClinic) {
        this.idHomecareClinic = idHomecareClinic;
    }

    @XmlTransient
    public List<HomecareHomecareClinicAdminRoles> getHomecareHomecareClinicAdminRolesList() {
        return homecareHomecareClinicAdminRolesList;
    }

    public void setHomecareHomecareClinicAdminRolesList(List<HomecareHomecareClinicAdminRoles> homecareHomecareClinicAdminRolesList) {
        this.homecareHomecareClinicAdminRolesList = homecareHomecareClinicAdminRolesList;
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
        if (!(object instanceof HomecareHomecareClinicAdmin)) {
            return false;
        }
        HomecareHomecareClinicAdmin other = (HomecareHomecareClinicAdmin) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "trial.entity.HomecareHomecareClinicAdmin[ id=" + id + " ]";
    }
    
}

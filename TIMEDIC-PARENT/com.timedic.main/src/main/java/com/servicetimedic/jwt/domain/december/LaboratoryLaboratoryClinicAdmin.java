/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetimedic.jwt.domain.december;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Hafid
 */
@Entity
@Table(name = "laboratory_laboratory_clinic_admin")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LaboratoryLaboratoryClinicAdmin.findAll", query = "SELECT l FROM LaboratoryLaboratoryClinicAdmin l"),
    @NamedQuery(name = "LaboratoryLaboratoryClinicAdmin.findById", query = "SELECT l FROM LaboratoryLaboratoryClinicAdmin l WHERE l.id = :id"),
    @NamedQuery(name = "LaboratoryLaboratoryClinicAdmin.findByUsername", query = "SELECT l FROM LaboratoryLaboratoryClinicAdmin l WHERE l.username = :username"),
    @NamedQuery(name = "LaboratoryLaboratoryClinicAdmin.findByPassword", query = "SELECT l FROM LaboratoryLaboratoryClinicAdmin l WHERE l.password = :password"),
    @NamedQuery(name = "LaboratoryLaboratoryClinicAdmin.findByAddress", query = "SELECT l FROM LaboratoryLaboratoryClinicAdmin l WHERE l.address = :address"),
    @NamedQuery(name = "LaboratoryLaboratoryClinicAdmin.findByDateOfBirth", query = "SELECT l FROM LaboratoryLaboratoryClinicAdmin l WHERE l.dateOfBirth = :dateOfBirth"),
    @NamedQuery(name = "LaboratoryLaboratoryClinicAdmin.findByEmail", query = "SELECT l FROM LaboratoryLaboratoryClinicAdmin l WHERE l.email = :email"),
    @NamedQuery(name = "LaboratoryLaboratoryClinicAdmin.findByFirstName", query = "SELECT l FROM LaboratoryLaboratoryClinicAdmin l WHERE l.firstName = :firstName"),
    @NamedQuery(name = "LaboratoryLaboratoryClinicAdmin.findByMiddleName", query = "SELECT l FROM LaboratoryLaboratoryClinicAdmin l WHERE l.middleName = :middleName"),
    @NamedQuery(name = "LaboratoryLaboratoryClinicAdmin.findByLastName", query = "SELECT l FROM LaboratoryLaboratoryClinicAdmin l WHERE l.lastName = :lastName"),
    @NamedQuery(name = "LaboratoryLaboratoryClinicAdmin.findByPhoneNumber", query = "SELECT l FROM LaboratoryLaboratoryClinicAdmin l WHERE l.phoneNumber = :phoneNumber"),
    @NamedQuery(name = "LaboratoryLaboratoryClinicAdmin.findByFirstRegistrationDate", query = "SELECT l FROM LaboratoryLaboratoryClinicAdmin l WHERE l.firstRegistrationDate = :firstRegistrationDate"),
    @NamedQuery(name = "LaboratoryLaboratoryClinicAdmin.findByIdNumber", query = "SELECT l FROM LaboratoryLaboratoryClinicAdmin l WHERE l.idNumber = :idNumber")})
public class LaboratoryLaboratoryClinicAdmin implements UserDetails, Serializable {
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
    @Size(max = 30)
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "first_registration_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date firstRegistrationDate;
    @Size(max = 255)
    @Column(name = "id_number")
    private String idNumber;
    @JoinColumn(name = "id_laboratory_clinic", referencedColumnName = "id")
    @ManyToOne
    private LaboratoryLaboratoryClinic idLaboratoryClinic;

    @ElementCollection
    //@OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    private List<String> roles = new ArrayList<>();

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public LaboratoryLaboratoryClinicAdmin() {
    }

    public LaboratoryLaboratoryClinicAdmin(Long id) {
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

    public LaboratoryLaboratoryClinic getIdLaboratoryClinic() {
        return idLaboratoryClinic;
    }

    public void setIdLaboratoryClinic(LaboratoryLaboratoryClinic idLaboratoryClinic) {
        this.idLaboratoryClinic = idLaboratoryClinic;
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
        if (!(object instanceof LaboratoryLaboratoryClinicAdmin)) {
            return false;
        }
        LaboratoryLaboratoryClinicAdmin other = (LaboratoryLaboratoryClinicAdmin) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
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

    @Override
    public String toString() {
        return "trial.entity.LaboratoryLaboratoryClinicAdmin[ id=" + id + " ]";
    }
    
}

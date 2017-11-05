/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techforumist.jwt.domain.posgresql;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
public class HomecareCaregiver implements UserDetails {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
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
    @Size(max = 255)
    @Column(name = "surat_tanda_registrasi")
    private String suratTandaRegistrasi;
    @Size(max = 255)
    @Column(name = "sipp")
    private String sipp;
    @OneToMany(mappedBy = "idHomecareCaregiver")
    private Collection<HomecareCaregiverRate> homecareCaregiverRateCollection;
    @OneToMany(mappedBy = "idCareGiver")
    private Collection<HomecareTransactionCaregiverlist> homecareTransactionCaregiverlistCollection;
    @OneToMany(mappedBy = "idHomecareCaregiver")
    private Collection<HomecareCaregiverSchedule> homecareCaregiverScheduleCollection;
    @OneToMany(mappedBy = "idCaregiver")
    private Collection<HomecareCaregiverBalance> homecareCaregiverBalanceCollection;
    @JoinColumn(name = "legal_place", referencedColumnName = "id")
    @ManyToOne
    private HomecareHomecareClinic legalPlace;
    @ElementCollection
    //@JsonIgnore
    private List<String> roles = new ArrayList<>();

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

    public String getSuratTandaRegistrasi() {
        return suratTandaRegistrasi;
    }

    public void setSuratTandaRegistrasi(String suratTandaRegistrasi) {
        this.suratTandaRegistrasi = suratTandaRegistrasi;
    }

    public String getSipp() {
        return sipp;
    }

    public void setSipp(String sipp) {
        this.sipp = sipp;
    }

    @XmlTransient
    public Collection<HomecareCaregiverRate> getHomecareCaregiverRateCollection() {
        return homecareCaregiverRateCollection;
    }

    public void setHomecareCaregiverRateCollection(Collection<HomecareCaregiverRate> homecareCaregiverRateCollection) {
        this.homecareCaregiverRateCollection = homecareCaregiverRateCollection;
    }

    @XmlTransient
    public Collection<HomecareTransactionCaregiverlist> getHomecareTransactionCaregiverlistCollection() {
        return homecareTransactionCaregiverlistCollection;
    }

    public void setHomecareTransactionCaregiverlistCollection(Collection<HomecareTransactionCaregiverlist> homecareTransactionCaregiverlistCollection) {
        this.homecareTransactionCaregiverlistCollection = homecareTransactionCaregiverlistCollection;
    }

    @XmlTransient
    public Collection<HomecareCaregiverSchedule> getHomecareCaregiverScheduleCollection() {
        return homecareCaregiverScheduleCollection;
    }

    public void setHomecareCaregiverScheduleCollection(Collection<HomecareCaregiverSchedule> homecareCaregiverScheduleCollection) {
        this.homecareCaregiverScheduleCollection = homecareCaregiverScheduleCollection;
    }

    @XmlTransient
    public Collection<HomecareCaregiverBalance> getHomecareCaregiverBalanceCollection() {
        return homecareCaregiverBalanceCollection;
    }

    public void setHomecareCaregiverBalanceCollection(Collection<HomecareCaregiverBalance> homecareCaregiverBalanceCollection) {
        this.homecareCaregiverBalanceCollection = homecareCaregiverBalanceCollection;
    }

    public HomecareHomecareClinic getLegalPlace() {
        return legalPlace;
    }

    public void setLegalPlace(HomecareHomecareClinic legalPlace) {
        this.legalPlace = legalPlace;
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

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles)
        {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

    @Override
    public String toString() {
        return "trial.entity.HomecareCaregiver[ id=" + id + " ]";
    }
    
}

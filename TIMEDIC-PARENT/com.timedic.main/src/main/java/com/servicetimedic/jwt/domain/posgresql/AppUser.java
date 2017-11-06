/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetimedic.jwt.domain.posgresql;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
@Table(name = "app_user")
@XmlRootElement
public class AppUser implements UserDetails {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 150)
    @Column(name = "address")
    private String address;
    @Column(name = "date_birth")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateBirth;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "email")
    private String email;
    
    @Size(max = 30)
    @Column(name = "front_name")
    private String frontName;
    @Size(max = 30)
    @Column(name = "last_name")
    private String lastName;
    @Size(max = 30)
    @Column(name = "middle_name")
    private String middleName;
    @Size(max = 40)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "phone_number")
    private String phoneNumber;
    @Size(max = 255)
    @Column(name = "photo_path")
    private String photoPath;
    @Size(max = 30)
    @Column(name = "username")
    private String username;
    @OneToMany(mappedBy = "idAppUser")
    private Collection<Homecareuserlocation> homecareuserlocationCollection;
    @OneToMany(mappedBy = "idAppUser")
    private Collection<HomecareCaregiverRate> homecareCaregiverRateCollection;
    @OneToMany(mappedBy = "idAppUser")
    private Collection<HomecareServiceTransaction> homecareServiceTransactionCollection;
    @OneToMany(mappedBy = "idUser")
    private Collection<HomecareUserBalance> homecareUserBalanceCollection;
    @ElementCollection
    //@OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    private List<String> roles = new ArrayList<>();

    public AppUser() {
    }

    public AppUser(Long id) {
        this.id = id;
    }

    public AppUser(Long id, String email, String phoneNumber) {
        this.id = id;
        this.email = email;
        this.phoneNumber = phoneNumber;
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
    
    public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
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

    public void setUsername(String username) {
        this.username = username;
    }

    @XmlTransient
    public Collection<Homecareuserlocation> getHomecareuserlocationCollection() {
        return homecareuserlocationCollection;
    }

    public void setHomecareuserlocationCollection(Collection<Homecareuserlocation> homecareuserlocationCollection) {
        this.homecareuserlocationCollection = homecareuserlocationCollection;
    }

    @XmlTransient
    public Collection<HomecareCaregiverRate> getHomecareCaregiverRateCollection() {
        return homecareCaregiverRateCollection;
    }

    public void setHomecareCaregiverRateCollection(Collection<HomecareCaregiverRate> homecareCaregiverRateCollection) {
        this.homecareCaregiverRateCollection = homecareCaregiverRateCollection;
    }

    @XmlTransient
    public Collection<HomecareServiceTransaction> getHomecareServiceTransactionCollection() {
        return homecareServiceTransactionCollection;
    }

    public void setHomecareServiceTransactionCollection(Collection<HomecareServiceTransaction> homecareServiceTransactionCollection) {
        this.homecareServiceTransactionCollection = homecareServiceTransactionCollection;
    }

    @XmlTransient
    public Collection<HomecareUserBalance> getHomecareUserBalanceCollection() {
        return homecareUserBalanceCollection;
    }

    public void setHomecareUserBalanceCollection(Collection<HomecareUserBalance> homecareUserBalanceCollection) {
        this.homecareUserBalanceCollection = homecareUserBalanceCollection;
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetimedic.jwt.domain.december;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "homecare_caregiver_rate")
public class HomecareCaregiverRate implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@Basic(optional = false)
    @Column(name = "id")
    private Long id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "rate")
    private Float rate;
    @JoinColumn(name = "id_app_user", referencedColumnName = "id")
    @ManyToOne
    private AppUser idAppUser;
    @JoinColumn(name = "id_homecare_caregiver", referencedColumnName = "id")
    @ManyToOne
    private HomecareCaregiver idHomecareCaregiver;

    public HomecareCaregiverRate() {
    }

    public HomecareCaregiverRate(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public AppUser getIdAppUser() {
        return idAppUser;
    }

    public void setIdAppUser(AppUser idAppUser) {
        this.idAppUser = idAppUser;
    }

    public HomecareCaregiver getIdHomecareCaregiver() {
        return idHomecareCaregiver;
    }

    public void setIdHomecareCaregiver(HomecareCaregiver idHomecareCaregiver) {
        this.idHomecareCaregiver = idHomecareCaregiver;
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
        if (!(object instanceof HomecareCaregiverRate)) {
            return false;
        }
        HomecareCaregiverRate other = (HomecareCaregiverRate) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "trial.entity.HomecareCaregiverRate[ id=" + id + " ]";
    }
    
}

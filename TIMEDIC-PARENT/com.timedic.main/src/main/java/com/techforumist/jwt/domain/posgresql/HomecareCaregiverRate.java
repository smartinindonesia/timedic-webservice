/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techforumist.jwt.domain.posgresql;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Hafid
 */
@Entity
@Table(name = "homecare_caregiver_rate")
@XmlRootElement
public class HomecareCaregiverRate implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "rate")
    private Integer rate;
    @JoinColumn(name = "id_homecare_caregiver", referencedColumnName = "id")
    @ManyToOne
    private HomecareCaregiver idHomecareCaregiver;
    @JoinColumn(name = "id_homecare_user", referencedColumnName = "id")
    @ManyToOne
    private AppUser idAppUser;

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

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public HomecareCaregiver getIdHomecareCaregiver() {
        return idHomecareCaregiver;
    }

    public void setIdHomecareCaregiver(HomecareCaregiver idHomecareCaregiver) {
        this.idHomecareCaregiver = idHomecareCaregiver;
    }

    public AppUser getIdAppUser() {
        return idAppUser;
    }

    public void setIdAppUser(AppUser idAppUser) {
        this.idAppUser = idAppUser;
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

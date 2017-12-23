/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetimedic.jwt.domain.december;

import java.io.Serializable;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Hafid
 */
@Entity
@Table(name = "homecare_caregiver_status")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HomecareCaregiverStatus.findAll", query = "SELECT h FROM HomecareCaregiverStatus h"),
    @NamedQuery(name = "HomecareCaregiverStatus.findById", query = "SELECT h FROM HomecareCaregiverStatus h WHERE h.id = :id"),
    @NamedQuery(name = "HomecareCaregiverStatus.findByStatus", query = "SELECT h FROM HomecareCaregiverStatus h WHERE h.status = :status")})
public class HomecareCaregiverStatus implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 255)
    @Column(name = "status")
    private String status;
    @OneToMany(mappedBy = "idCaregiverStatus")
    private List<HomecareCaregiver> homecareCaregiverList;

    public HomecareCaregiverStatus() {
    }

    public HomecareCaregiverStatus(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @XmlTransient
    public List<HomecareCaregiver> getHomecareCaregiverList() {
        return homecareCaregiverList;
    }

    public void setHomecareCaregiverList(List<HomecareCaregiver> homecareCaregiverList) {
        this.homecareCaregiverList = homecareCaregiverList;
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
        if (!(object instanceof HomecareCaregiverStatus)) {
            return false;
        }
        HomecareCaregiverStatus other = (HomecareCaregiverStatus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "trial.entity.HomecareCaregiverStatus[ id=" + id + " ]";
    }
    
}

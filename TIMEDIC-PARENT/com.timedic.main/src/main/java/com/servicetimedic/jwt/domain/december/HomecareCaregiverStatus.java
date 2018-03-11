/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetimedic.jwt.domain.december;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "homecare_caregiver_status")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class HomecareCaregiverStatus implements Serializable {
    
	private static final long serialVersionUID = 1L;
    
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    @JsonIgnore
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

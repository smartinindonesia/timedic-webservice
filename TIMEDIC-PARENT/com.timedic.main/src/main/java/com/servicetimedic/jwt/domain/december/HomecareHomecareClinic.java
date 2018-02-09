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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "homecare_homecare_clinic")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class HomecareHomecareClinic implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 255)
    @Column(name = "address")
    private String address;
    @Size(max = 255)
    @Column(name = "certificate")
    private String certificate;
    @Size(max = 255)
    @Column(name = "name")
    private String name;
    @Size(max = 255)
    @Column(name = "sipp")
    private String sipp;
    @Size(max = 255)
    @Column(name = "url_logo")
    private String urlLogo;
    @OneToMany(mappedBy = "idHomecareClinic")
    private List<HomecareCaregiver> homecareCaregiverList;
    @OneToMany(mappedBy = "idHomecareClinic")
    private List<HomecareTransactionCaregiverlist> homecareTransactionCaregiverlistList;
    @OneToMany(mappedBy = "idHomecareClinic")
    private List<HomecareHomecareClinicAdmin> homecareHomecareClinicAdminList;

    public HomecareHomecareClinic() {
    }

    public HomecareHomecareClinic(Long id) {
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

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSipp() {
        return sipp;
    }

    public void setSipp(String sipp) {
        this.sipp = sipp;
    }

    public String getUrlLogo() {
        return urlLogo;
    }

    public void setUrlLogo(String urlLogo) {
        this.urlLogo = urlLogo;
    }

    @XmlTransient
    public List<HomecareCaregiver> getHomecareCaregiverList() {
        return homecareCaregiverList;
    }

    public void setHomecareCaregiverList(List<HomecareCaregiver> homecareCaregiverList) {
        this.homecareCaregiverList = homecareCaregiverList;
    }

    @XmlTransient
    public List<HomecareTransactionCaregiverlist> getHomecareTransactionCaregiverlistList() {
        return homecareTransactionCaregiverlistList;
    }

    public void setHomecareTransactionCaregiverlistList(List<HomecareTransactionCaregiverlist> homecareTransactionCaregiverlistList) {
        this.homecareTransactionCaregiverlistList = homecareTransactionCaregiverlistList;
    }

    @XmlTransient
    public List<HomecareHomecareClinicAdmin> getHomecareHomecareClinicAdminList() {
        return homecareHomecareClinicAdminList;
    }

    public void setHomecareHomecareClinicAdminList(List<HomecareHomecareClinicAdmin> homecareHomecareClinicAdminList) {
        this.homecareHomecareClinicAdminList = homecareHomecareClinicAdminList;
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
        if (!(object instanceof HomecareHomecareClinic)) {
            return false;
        }
        HomecareHomecareClinic other = (HomecareHomecareClinic) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "trial.entity.HomecareHomecareClinic[ id=" + id + " ]";
    }
    
}

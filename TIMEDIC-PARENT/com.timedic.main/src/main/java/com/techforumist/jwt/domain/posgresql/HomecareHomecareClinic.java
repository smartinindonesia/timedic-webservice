/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techforumist.jwt.domain.posgresql;

import java.io.Serializable;
import java.util.Collection;
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
@Table(name = "homecare_homecare_clinic")
@XmlRootElement
public class HomecareHomecareClinic implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
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
    @Column(name = "url_logo")
    private String urlLogo;
    @Size(max = 255)
    @Column(name = "sipp")
    private String sipp;
    @OneToMany(mappedBy = "idHomecareClinic")
    private Collection<HomecareTransactionCaregiverlist> homecareTransactionCaregiverlistCollection;
    @OneToMany(mappedBy = "idHomecareClinic")
    private Collection<HomecareHomecareClinicBalance> homecareHomecareClinicBalanceCollection;
    @OneToMany(mappedBy = "legalPlace")
    private Collection<HomecareCaregiver> homecareCaregiverCollection;

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

    public String getUrlLogo() {
        return urlLogo;
    }

    public void setUrlLogo(String urlLogo) {
        this.urlLogo = urlLogo;
    }

    public String getSipp() {
        return sipp;
    }

    public void setSipp(String sipp) {
        this.sipp = sipp;
    }

    @XmlTransient
    public Collection<HomecareTransactionCaregiverlist> getHomecareTransactionCaregiverlistCollection() {
        return homecareTransactionCaregiverlistCollection;
    }

    public void setHomecareTransactionCaregiverlistCollection(Collection<HomecareTransactionCaregiverlist> homecareTransactionCaregiverlistCollection) {
        this.homecareTransactionCaregiverlistCollection = homecareTransactionCaregiverlistCollection;
    }

    @XmlTransient
    public Collection<HomecareHomecareClinicBalance> getHomecareHomecareClinicBalanceCollection() {
        return homecareHomecareClinicBalanceCollection;
    }

    public void setHomecareHomecareClinicBalanceCollection(Collection<HomecareHomecareClinicBalance> homecareHomecareClinicBalanceCollection) {
        this.homecareHomecareClinicBalanceCollection = homecareHomecareClinicBalanceCollection;
    }

    @XmlTransient
    public Collection<HomecareCaregiver> getHomecareCaregiverCollection() {
        return homecareCaregiverCollection;
    }

    public void setHomecareCaregiverCollection(Collection<HomecareCaregiver> homecareCaregiverCollection) {
        this.homecareCaregiverCollection = homecareCaregiverCollection;
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

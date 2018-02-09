/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetimedic.jwt.domain.december;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Basic;
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
@Table(name = "laboratory_laboratory_clinic")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class LaboratoryLaboratoryClinic implements Serializable {
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
    @Column(name = "detail")
    private String detail;
    @Size(max = 255)
    @Column(name = "uri_logo")
    private String uriLogo;
    @OneToMany(mappedBy = "idLaboratoryClinic")
    private Collection<HomecareCaregiver> homecareCaregiverCollection;
    @OneToMany(mappedBy = "idLaboratoryClinic")
    private Collection<LaboratoryLaboratoryClinicAdmin> laboratoryLaboratoryClinicAdminCollection;
    @OneToMany(mappedBy = "idLaboratoryClinic")
    private Collection<LaboratoryServiceTransaction> laboratoryServiceTransactionCollection;

    public LaboratoryLaboratoryClinic() {
    }

    public LaboratoryLaboratoryClinic(Long id) {
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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getUriLogo() {
        return uriLogo;
    }

    public void setUriLogo(String uriLogo) {
        this.uriLogo = uriLogo;
    }

    //@XmlTransient
    @JsonIgnore
    public Collection<HomecareCaregiver> getHomecareCaregiverCollection() {
        return homecareCaregiverCollection;
    }

    public void setHomecareCaregiverCollection(Collection<HomecareCaregiver> homecareCaregiverCollection) {
        this.homecareCaregiverCollection = homecareCaregiverCollection;
    }

    //@XmlTransient
    @JsonIgnore
    public Collection<LaboratoryLaboratoryClinicAdmin> getLaboratoryLaboratoryClinicAdminCollection() {
        return laboratoryLaboratoryClinicAdminCollection;
    }

    public void setLaboratoryLaboratoryClinicAdminCollection(Collection<LaboratoryLaboratoryClinicAdmin> laboratoryLaboratoryClinicAdminCollection) {
        this.laboratoryLaboratoryClinicAdminCollection = laboratoryLaboratoryClinicAdminCollection;
    }

    //@XmlTransient
    @JsonIgnore
    public Collection<LaboratoryServiceTransaction> getLaboratoryServiceTransactionCollection() {
        return laboratoryServiceTransactionCollection;
    }

    public void setLaboratoryServiceTransactionCollection(Collection<LaboratoryServiceTransaction> laboratoryServiceTransactionCollection) {
        this.laboratoryServiceTransactionCollection = laboratoryServiceTransactionCollection;
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
        if (!(object instanceof LaboratoryLaboratoryClinic)) {
            return false;
        }
        LaboratoryLaboratoryClinic other = (LaboratoryLaboratoryClinic) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "trial.entity.LaboratoryLaboratoryClinic[ id=" + id + " ]";
    }
    
}

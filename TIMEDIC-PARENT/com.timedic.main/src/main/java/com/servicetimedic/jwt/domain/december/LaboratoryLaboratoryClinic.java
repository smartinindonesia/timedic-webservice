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


@Entity
@Table(name = "laboratory_laboratory_clinic")

public class LaboratoryLaboratoryClinic implements Serializable {
    
	private static final long serialVersionUID = 1L;
    
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    private List<HomecareCaregiver> homecareCaregiverList;
    
	@OneToMany(mappedBy = "idLaboratoryClinic")
    private List<LaboratoryLaboratoryClinicAdmin> laboratoryLaboratoryClinicAdminList;
    
	@OneToMany(mappedBy = "idLaboratoryClinic")
    private List<LaboratoryServiceTransaction> laboratoryServiceTransactionList;

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

    @XmlTransient
    public List<HomecareCaregiver> getHomecareCaregiverList() {
        return homecareCaregiverList;
    }

    public void setHomecareCaregiverList(List<HomecareCaregiver> homecareCaregiverList) {
        this.homecareCaregiverList = homecareCaregiverList;
    }

    @XmlTransient
    public List<LaboratoryLaboratoryClinicAdmin> getLaboratoryLaboratoryClinicAdminList() {
        return laboratoryLaboratoryClinicAdminList;
    }

    public void setLaboratoryLaboratoryClinicAdminList(List<LaboratoryLaboratoryClinicAdmin> laboratoryLaboratoryClinicAdminList) {
        this.laboratoryLaboratoryClinicAdminList = laboratoryLaboratoryClinicAdminList;
    }

    @XmlTransient
    public List<LaboratoryServiceTransaction> getLaboratoryServiceTransactionList() {
        return laboratoryServiceTransactionList;
    }

    public void setLaboratoryServiceTransactionList(List<LaboratoryServiceTransaction> laboratoryServiceTransactionList) {
        this.laboratoryServiceTransactionList = laboratoryServiceTransactionList;
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

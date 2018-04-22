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
@Table(name = "laboratory_service")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class LaboratoryService implements Serializable {
    
	private static final long serialVersionUID = 1L;
    
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    
	@Size(max = 255)
    @Column(name = "service_name")
    private String serviceName;
    
	@Size(max = 255)
    @Column(name = "service_code")
    private String serviceCode;
    
	@Size(max = 255)
    @Column(name = "description")
    private String description;
    
	@Column(name = "price")
    private Float price;
    
	@Size(max = 255)
    @Column(name = "uri_service_icon")
    private String uriServiceIcon;
	
    
	@OneToMany(mappedBy = "idLaboratoryService")
    private Collection<LaboratoryServicePackage> laboratoryServicePackageCollection;
    
	@OneToMany(mappedBy = "idLaboratoryService")
    private Collection<LaboratorySelectedServiceTransaction> laboratorySelectedServiceTransactionCollection;

    public LaboratoryService() {
    }

    public LaboratoryService(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
    
	public String getUriServiceIcon() {
        return uriServiceIcon;
    }

    public void setUriServiceIcon(String uriServiceIcon) {
        this.uriServiceIcon = uriServiceIcon;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<LaboratoryServicePackage> getLaboratoryServicePackageCollection() {
        return laboratoryServicePackageCollection;
    }

    public void setLaboratoryServicePackageCollection(Collection<LaboratoryServicePackage> laboratoryServicePackageCollection) {
        this.laboratoryServicePackageCollection = laboratoryServicePackageCollection;
    }

    //@XmlTransient
    @JsonIgnore
    public Collection<LaboratorySelectedServiceTransaction> getLaboratorySelectedServiceTransactionCollection() {
        return laboratorySelectedServiceTransactionCollection;
    }

    public void setLaboratorySelectedServiceTransactionCollection(Collection<LaboratorySelectedServiceTransaction> laboratorySelectedServiceTransactionCollection) {
        this.laboratorySelectedServiceTransactionCollection = laboratorySelectedServiceTransactionCollection;
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
        if (!(object instanceof LaboratoryService)) {
            return false;
        }
        LaboratoryService other = (LaboratoryService) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "trial.entity.LaboratoryService[ id=" + id + " ]";
    }
    
}

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
@Table(name = "laboratory_service")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LaboratoryService.findAll", query = "SELECT l FROM LaboratoryService l"),
    @NamedQuery(name = "LaboratoryService.findById", query = "SELECT l FROM LaboratoryService l WHERE l.id = :id"),
    @NamedQuery(name = "LaboratoryService.findByServiceName", query = "SELECT l FROM LaboratoryService l WHERE l.serviceName = :serviceName"),
    @NamedQuery(name = "LaboratoryService.findByServiceCode", query = "SELECT l FROM LaboratoryService l WHERE l.serviceCode = :serviceCode"),
    @NamedQuery(name = "LaboratoryService.findByDescription", query = "SELECT l FROM LaboratoryService l WHERE l.description = :description"),
    @NamedQuery(name = "LaboratoryService.findByPrice", query = "SELECT l FROM LaboratoryService l WHERE l.price = :price"),
    @NamedQuery(name = "LaboratoryService.findByUriServiceIcon", query = "SELECT l FROM LaboratoryService l WHERE l.uriServiceIcon = :uriServiceIcon")})
public class LaboratoryService implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
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
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "price")
    private Float price;
    @Size(max = 255)
    @Column(name = "uri_service_icon")
    private String uriServiceIcon;
    @OneToMany(mappedBy = "idLaboratoryService")
    private List<LaboratoryServicePackage> laboratoryServicePackageList;
    @OneToMany(mappedBy = "idLaboratoryService")
    private List<LaboratorySelectedServiceTransaction> laboratorySelectedServiceTransactionList;

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
    public List<LaboratoryServicePackage> getLaboratoryServicePackageList() {
        return laboratoryServicePackageList;
    }

    public void setLaboratoryServicePackageList(List<LaboratoryServicePackage> laboratoryServicePackageList) {
        this.laboratoryServicePackageList = laboratoryServicePackageList;
    }

    @XmlTransient
    public List<LaboratorySelectedServiceTransaction> getLaboratorySelectedServiceTransactionList() {
        return laboratorySelectedServiceTransactionList;
    }

    public void setLaboratorySelectedServiceTransactionList(List<LaboratorySelectedServiceTransaction> laboratorySelectedServiceTransactionList) {
        this.laboratorySelectedServiceTransactionList = laboratorySelectedServiceTransactionList;
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

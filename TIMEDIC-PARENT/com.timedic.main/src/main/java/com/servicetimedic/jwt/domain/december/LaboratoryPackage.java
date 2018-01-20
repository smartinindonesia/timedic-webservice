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

@Entity
@Table(name = "laboratory_package")
public class LaboratoryPackage implements Serializable {
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "price")
    private Float price;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 255)
    @Column(name = "package_name")
    private String packageName;
    @Size(max = 255)
    @Column(name = "package_code")
    private String packageCode;
    @Size(max = 255)
    @Column(name = "package_description")
    private String packageDescription;
    @Size(max = 255)
    @Column(name = "uri_package_icon")
    private String uriPackageIcon;
    @OneToMany(mappedBy = "idLaboratoryPackage")
    private Collection<LaboratoryServicePackage> laboratoryServicePackageCollection;
    @OneToMany(mappedBy = "idServicePackage")
    private Collection<LaboratoryServiceTransaction> laboratoryServiceTransactionCollection;

    public LaboratoryPackage() {
    }

    public LaboratoryPackage(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageCode() {
        return packageCode;
    }

    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }

    public String getPackageDescription() {
        return packageDescription;
    }

    public void setPackageDescription(String packageDescription) {
        this.packageDescription = packageDescription;
    }

    public String getUriPackageIcon() {
        return uriPackageIcon;
    }

    public void setUriPackageIcon(String uriPackageIcon) {
        this.uriPackageIcon = uriPackageIcon;
    }

    @XmlTransient
    public Collection<LaboratoryServicePackage> getLaboratoryServicePackageCollection() {
        return laboratoryServicePackageCollection;
    }

    public void setLaboratoryServicePackageCollection(Collection<LaboratoryServicePackage> laboratoryServicePackageCollection) {
        this.laboratoryServicePackageCollection = laboratoryServicePackageCollection;
    }

    @XmlTransient
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
        if (!(object instanceof LaboratoryPackage)) {
            return false;
        }
        LaboratoryPackage other = (LaboratoryPackage) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "trial.entity.LaboratoryPackage[ id=" + id + " ]";
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
    
}

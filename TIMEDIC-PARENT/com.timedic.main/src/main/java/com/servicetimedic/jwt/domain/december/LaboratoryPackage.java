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
@Table(name = "laboratory_package")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LaboratoryPackage.findAll", query = "SELECT l FROM LaboratoryPackage l"),
    @NamedQuery(name = "LaboratoryPackage.findById", query = "SELECT l FROM LaboratoryPackage l WHERE l.id = :id"),
    @NamedQuery(name = "LaboratoryPackage.findByPackageName", query = "SELECT l FROM LaboratoryPackage l WHERE l.packageName = :packageName"),
    @NamedQuery(name = "LaboratoryPackage.findByPackageCode", query = "SELECT l FROM LaboratoryPackage l WHERE l.packageCode = :packageCode"),
    @NamedQuery(name = "LaboratoryPackage.findByPackageDescription", query = "SELECT l FROM LaboratoryPackage l WHERE l.packageDescription = :packageDescription"),
    @NamedQuery(name = "LaboratoryPackage.findByUriPackageIcon", query = "SELECT l FROM LaboratoryPackage l WHERE l.uriPackageIcon = :uriPackageIcon")})
public class LaboratoryPackage implements Serializable {
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
    private List<LaboratoryServicePackage> laboratoryServicePackageList;
    @OneToMany(mappedBy = "idServicePackage")
    private List<LaboratoryServiceTransaction> laboratoryServiceTransactionList;

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
    public List<LaboratoryServicePackage> getLaboratoryServicePackageList() {
        return laboratoryServicePackageList;
    }

    public void setLaboratoryServicePackageList(List<LaboratoryServicePackage> laboratoryServicePackageList) {
        this.laboratoryServicePackageList = laboratoryServicePackageList;
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
    
}

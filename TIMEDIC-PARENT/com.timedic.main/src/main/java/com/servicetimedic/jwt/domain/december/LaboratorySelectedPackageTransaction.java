/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetimedic.jwt.domain.december;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "laboratory_selected_package_transaction")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class LaboratorySelectedPackageTransaction implements Serializable {
    
	private static final long serialVersionUID = 1L;
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
	@JoinColumn(name = "id_laboratory_service_transaction", referencedColumnName = "id")
    @ManyToOne
    private LaboratoryServiceTransaction idLaboratoryServiceTransaction;
    
	@JoinColumn(name = "id_laboratory_package", referencedColumnName = "id")
    @ManyToOne
    private LaboratoryServicePackage idLaboratoryPackage;

    public LaboratorySelectedPackageTransaction() {
    }

    public LaboratorySelectedPackageTransaction(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public LaboratoryServiceTransaction getIdLaboratoryServiceTransaction() {
        return idLaboratoryServiceTransaction;
    }

    public void setIdLaboratoryServiceTransaction(LaboratoryServiceTransaction idLaboratoryServiceTransaction) {
        this.idLaboratoryServiceTransaction = idLaboratoryServiceTransaction;
    }

    public LaboratoryServicePackage getIdLaboratoryPackage() {
        return idLaboratoryPackage;
    }

    public void setIdLaboratoryPackage(LaboratoryServicePackage idLaboratoryPackage) {
        this.idLaboratoryPackage = idLaboratoryPackage;
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
        if (!(object instanceof LaboratorySelectedPackageTransaction)) {
            return false;
        }
        LaboratorySelectedPackageTransaction other = (LaboratorySelectedPackageTransaction) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "trial.entity.LaboratorySelectedPackageTransaction[ id=" + id + " ]";
    }
    
}

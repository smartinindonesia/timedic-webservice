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
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "laboratory_laboratory_clinic_admin_roles")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class LaboratoryLaboratoryClinicAdminRoles implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(max = 255)
    @Column(name = "roles")
    private String roles;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @JoinColumn(name = "laboratory_laboratory_clinic_admin_id", referencedColumnName = "id")
    @ManyToOne
    private LaboratoryLaboratoryClinicAdmin laboratoryLaboratoryClinicAdminId;

    public LaboratoryLaboratoryClinicAdminRoles() {
    }

    public LaboratoryLaboratoryClinicAdminRoles(Long id) {
        this.id = id;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LaboratoryLaboratoryClinicAdmin getLaboratoryLaboratoryClinicAdminId() {
        return laboratoryLaboratoryClinicAdminId;
    }

    public void setLaboratoryLaboratoryClinicAdminId(LaboratoryLaboratoryClinicAdmin laboratoryLaboratoryClinicAdminId) {
        this.laboratoryLaboratoryClinicAdminId = laboratoryLaboratoryClinicAdminId;
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
        if (!(object instanceof LaboratoryLaboratoryClinicAdminRoles)) {
            return false;
        }
        LaboratoryLaboratoryClinicAdminRoles other = (LaboratoryLaboratoryClinicAdminRoles) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "trial.entity.LaboratoryLaboratoryClinicAdminRoles[ id=" + id + " ]";
    }
    
}

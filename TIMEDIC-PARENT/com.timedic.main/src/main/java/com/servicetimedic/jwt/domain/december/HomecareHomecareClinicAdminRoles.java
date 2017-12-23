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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Hafid
 */
@Entity
@Table(name = "homecare_homecare_clinic_admin_roles")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HomecareHomecareClinicAdminRoles.findAll", query = "SELECT h FROM HomecareHomecareClinicAdminRoles h"),
    @NamedQuery(name = "HomecareHomecareClinicAdminRoles.findByRoles", query = "SELECT h FROM HomecareHomecareClinicAdminRoles h WHERE h.roles = :roles"),
    @NamedQuery(name = "HomecareHomecareClinicAdminRoles.findById", query = "SELECT h FROM HomecareHomecareClinicAdminRoles h WHERE h.id = :id")})
public class HomecareHomecareClinicAdminRoles implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(max = 255)
    @Column(name = "roles")
    private String roles;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @JoinColumn(name = "homecare_homecare_clinic_admin_id", referencedColumnName = "id")
    @ManyToOne
    private HomecareHomecareClinicAdmin homecareHomecareClinicAdminId;

    public HomecareHomecareClinicAdminRoles() {
    }

    public HomecareHomecareClinicAdminRoles(Long id) {
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

    public HomecareHomecareClinicAdmin getHomecareHomecareClinicAdminId() {
        return homecareHomecareClinicAdminId;
    }

    public void setHomecareHomecareClinicAdminId(HomecareHomecareClinicAdmin homecareHomecareClinicAdminId) {
        this.homecareHomecareClinicAdminId = homecareHomecareClinicAdminId;
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
        if (!(object instanceof HomecareHomecareClinicAdminRoles)) {
            return false;
        }
        HomecareHomecareClinicAdminRoles other = (HomecareHomecareClinicAdminRoles) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "trial.entity.HomecareHomecareClinicAdminRoles[ id=" + id + " ]";
    }
    
}

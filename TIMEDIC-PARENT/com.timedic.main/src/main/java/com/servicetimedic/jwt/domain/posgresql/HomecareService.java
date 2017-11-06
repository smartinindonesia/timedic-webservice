/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetimedic.jwt.domain.posgresql;

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
@Table(name = "homecare_service")
@XmlRootElement
public class HomecareService implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 4)
    @Column(name = "service_code")
    private String serviceCode;
    @Size(max = 50)
    @Column(name = "service_name")
    private String serviceName;
    @Size(max = 60)
    @Column(name = "service_caterogry")
    private String serviceCaterogry;
    @OneToMany(mappedBy = "idService")
    private Collection<HomecareServiceAssestment> homecareServiceAssestmentCollection;

    public HomecareService() {
    }

    public HomecareService(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceCaterogry() {
        return serviceCaterogry;
    }

    public void setServiceCaterogry(String serviceCaterogry) {
        this.serviceCaterogry = serviceCaterogry;
    }

    @XmlTransient
    public Collection<HomecareServiceAssestment> getHomecareServiceAssestmentCollection() {
        return homecareServiceAssestmentCollection;
    }

    public void setHomecareServiceAssestmentCollection(Collection<HomecareServiceAssestment> homecareServiceAssestmentCollection) {
        this.homecareServiceAssestmentCollection = homecareServiceAssestmentCollection;
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
        if (!(object instanceof HomecareService)) {
            return false;
        }
        HomecareService other = (HomecareService) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "trial.entity.HomecareService[ id=" + id + " ]";
    }
    
}

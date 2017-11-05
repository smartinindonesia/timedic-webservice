/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techforumist.jwt.domain.posgresql;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Hafid
 */
@Entity
@Table(name = "homecare_service_assestment")
@XmlRootElement
public class HomecareServiceAssestment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @JoinColumn(name = "id_service", referencedColumnName = "id")
    @ManyToOne
    private HomecareService idService;
    @JoinColumn(name = "id_assestment", referencedColumnName = "id")
    @ManyToOne
    private HomecareAssestment idAssestment;

    public HomecareServiceAssestment() {
    }

    public HomecareServiceAssestment(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HomecareService getIdService() {
        return idService;
    }

    public void setIdService(HomecareService idService) {
        this.idService = idService;
    }

    public HomecareAssestment getIdAssestment() {
        return idAssestment;
    }

    public void setIdAssestment(HomecareAssestment idAssestment) {
        this.idAssestment = idAssestment;
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
        if (!(object instanceof HomecareServiceAssestment)) {
            return false;
        }
        HomecareServiceAssestment other = (HomecareServiceAssestment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "trial.entity.HomecareServiceAssestment[ id=" + id + " ]";
    }
    
}

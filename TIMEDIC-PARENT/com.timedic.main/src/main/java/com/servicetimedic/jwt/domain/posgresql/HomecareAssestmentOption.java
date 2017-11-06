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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Hafid
 */
@Entity
@Table(name = "homecare_assestment_option")
@XmlRootElement
public class HomecareAssestmentOption implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Size(max = 255)
    @Column(name = "option")
    private String option;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "price_added")
    private Double priceAdded;
    @JoinColumn(name = "id_assestment", referencedColumnName = "id")
    @ManyToOne
    private HomecareAssestment idAssestment;
    @OneToMany(mappedBy = "selectedOption")
    private Collection<HomecareAssestmentRecord> homecareAssestmentRecordCollection;

    public HomecareAssestmentOption() {
    }

    public HomecareAssestmentOption(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public Double getPriceAdded() {
        return priceAdded;
    }

    public void setPriceAdded(Double priceAdded) {
        this.priceAdded = priceAdded;
    }

    public HomecareAssestment getIdAssestment() {
        return idAssestment;
    }

    public void setIdAssestment(HomecareAssestment idAssestment) {
        this.idAssestment = idAssestment;
    }

    @XmlTransient
    public Collection<HomecareAssestmentRecord> getHomecareAssestmentRecordCollection() {
        return homecareAssestmentRecordCollection;
    }

    public void setHomecareAssestmentRecordCollection(Collection<HomecareAssestmentRecord> homecareAssestmentRecordCollection) {
        this.homecareAssestmentRecordCollection = homecareAssestmentRecordCollection;
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
        if (!(object instanceof HomecareAssestmentOption)) {
            return false;
        }
        HomecareAssestmentOption other = (HomecareAssestmentOption) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "trial.entity.HomecareAssestmentOption[ id=" + id + " ]";
    }
    
}

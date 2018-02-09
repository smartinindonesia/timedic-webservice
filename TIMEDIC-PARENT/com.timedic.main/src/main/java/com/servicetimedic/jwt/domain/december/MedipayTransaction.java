/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetimedic.jwt.domain.december;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "medipay_transaction")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MedipayTransaction implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@Basic(optional = false)
    @Column(name = "id")
    private Long id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "nominal")
    private Float nominal;
    @JoinColumn(name = "global_id_number", referencedColumnName = "uuid")
    @ManyToOne
    private GlobalIdNumber globalIdNumber;
    @OneToMany(mappedBy = "idMedipayTransaction")
    private List<MedipayVerification> medipayVerificationList;

    public MedipayTransaction() {
    }

    public MedipayTransaction(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getNominal() {
        return nominal;
    }

    public void setNominal(Float nominal) {
        this.nominal = nominal;
    }

    public GlobalIdNumber getGlobalIdNumber() {
        return globalIdNumber;
    }

    public void setGlobalIdNumber(GlobalIdNumber globalIdNumber) {
        this.globalIdNumber = globalIdNumber;
    }

    @XmlTransient
    public List<MedipayVerification> getMedipayVerificationList() {
        return medipayVerificationList;
    }

    public void setMedipayVerificationList(List<MedipayVerification> medipayVerificationList) {
        this.medipayVerificationList = medipayVerificationList;
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
        if (!(object instanceof MedipayTransaction)) {
            return false;
        }
        MedipayTransaction other = (MedipayTransaction) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "trial.entity.MedipayTransaction[ id=" + id + " ]";
    }
    
}

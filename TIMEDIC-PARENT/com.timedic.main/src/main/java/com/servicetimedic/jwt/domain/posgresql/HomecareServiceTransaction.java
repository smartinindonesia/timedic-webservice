/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetimedic.jwt.domain.posgresql;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Hafid
 */
@Entity
@Table(name = "homecare_service_transaction")
@XmlRootElement
public class HomecareServiceTransaction implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "prediction_price")
    private Double predictionPrice;
    @Column(name = "fixed_price")
    private Double fixedPrice;
    @Column(name = "prepaid_price")
    private Double prepaidPrice;
    @OneToMany(mappedBy = "idServiceTransaction")
    private Collection<HomecareAssestmentRecord> homecareAssestmentRecordCollection;
    @OneToMany(mappedBy = "idServiceTransaction")
    private Collection<HomecareTransactionCaregiverlist> homecareTransactionCaregiverlistCollection;
    @JoinColumn(name = "id_homecare_user", referencedColumnName = "id")
    @ManyToOne
    private AppUser idAppUser;
    @JoinColumn(name = "id_transaction_status", referencedColumnName = "id")
    @ManyToOne
    private SystemTransactionStatus idTransactionStatus;

    public HomecareServiceTransaction() {
    }

    public HomecareServiceTransaction(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getPredictionPrice() {
        return predictionPrice;
    }

    public void setPredictionPrice(Double predictionPrice) {
        this.predictionPrice = predictionPrice;
    }

    public Double getFixedPrice() {
        return fixedPrice;
    }

    public void setFixedPrice(Double fixedPrice) {
        this.fixedPrice = fixedPrice;
    }

    public Double getPrepaidPrice() {
        return prepaidPrice;
    }

    public void setPrepaidPrice(Double prepaidPrice) {
        this.prepaidPrice = prepaidPrice;
    }

    @XmlTransient
    public Collection<HomecareAssestmentRecord> getHomecareAssestmentRecordCollection() {
        return homecareAssestmentRecordCollection;
    }

    public void setHomecareAssestmentRecordCollection(Collection<HomecareAssestmentRecord> homecareAssestmentRecordCollection) {
        this.homecareAssestmentRecordCollection = homecareAssestmentRecordCollection;
    }

    @XmlTransient
    public Collection<HomecareTransactionCaregiverlist> getHomecareTransactionCaregiverlistCollection() {
        return homecareTransactionCaregiverlistCollection;
    }

    public void setHomecareTransactionCaregiverlistCollection(Collection<HomecareTransactionCaregiverlist> homecareTransactionCaregiverlistCollection) {
        this.homecareTransactionCaregiverlistCollection = homecareTransactionCaregiverlistCollection;
    }

    public AppUser getIdAppUser() {
        return idAppUser;
    }

    public void setIdAppUser(AppUser idAppUser) {
        this.idAppUser = idAppUser;
    }

    public SystemTransactionStatus getIdTransactionStatus() {
        return idTransactionStatus;
    }

    public void setIdTransactionStatus(SystemTransactionStatus idTransactionStatus) {
        this.idTransactionStatus = idTransactionStatus;
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
        if (!(object instanceof HomecareServiceTransaction)) {
            return false;
        }
        HomecareServiceTransaction other = (HomecareServiceTransaction) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "trial.entity.HomecareServiceTransaction[ id=" + id + " ]";
    }
    
}

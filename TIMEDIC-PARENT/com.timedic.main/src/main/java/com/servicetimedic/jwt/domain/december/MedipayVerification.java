/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetimedic.jwt.domain.december;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Hafid
 */
@Entity
@Table(name = "medipay_verification")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MedipayVerification.findAll", query = "SELECT m FROM MedipayVerification m"),
    @NamedQuery(name = "MedipayVerification.findById", query = "SELECT m FROM MedipayVerification m WHERE m.id = :id"),
    @NamedQuery(name = "MedipayVerification.findByReceiptFilePath", query = "SELECT m FROM MedipayVerification m WHERE m.receiptFilePath = :receiptFilePath"),
    @NamedQuery(name = "MedipayVerification.findByVerifiedDate", query = "SELECT m FROM MedipayVerification m WHERE m.verifiedDate = :verifiedDate"),
    @NamedQuery(name = "MedipayVerification.findByVerifiedBy", query = "SELECT m FROM MedipayVerification m WHERE m.verifiedBy = :verifiedBy")})
public class MedipayVerification implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 255)
    @Column(name = "receipt_file_path")
    private String receiptFilePath;
    @Column(name = "verified_date")
    @Temporal(TemporalType.DATE)
    private Date verifiedDate;
    @Size(max = 255)
    @Column(name = "verified_by")
    private String verifiedBy;
    @JoinColumn(name = "id_medipay_transaction", referencedColumnName = "id")
    @ManyToOne
    private MedipayTransaction idMedipayTransaction;
    @JoinColumn(name = "global_id_number", referencedColumnName = "uuid")
    @ManyToOne
    private GlobalIdNumber globalIdNumber;

    public MedipayVerification() {
    }

    public MedipayVerification(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReceiptFilePath() {
        return receiptFilePath;
    }

    public void setReceiptFilePath(String receiptFilePath) {
        this.receiptFilePath = receiptFilePath;
    }

    public Date getVerifiedDate() {
        return verifiedDate;
    }

    public void setVerifiedDate(Date verifiedDate) {
        this.verifiedDate = verifiedDate;
    }

    public String getVerifiedBy() {
        return verifiedBy;
    }

    public void setVerifiedBy(String verifiedBy) {
        this.verifiedBy = verifiedBy;
    }

    public MedipayTransaction getIdMedipayTransaction() {
        return idMedipayTransaction;
    }

    public void setIdMedipayTransaction(MedipayTransaction idMedipayTransaction) {
        this.idMedipayTransaction = idMedipayTransaction;
    }

    public GlobalIdNumber getGlobalIdNumber() {
        return globalIdNumber;
    }

    public void setGlobalIdNumber(GlobalIdNumber globalIdNumber) {
        this.globalIdNumber = globalIdNumber;
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
        if (!(object instanceof MedipayVerification)) {
            return false;
        }
        MedipayVerification other = (MedipayVerification) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "trial.entity.MedipayVerification[ id=" + id + " ]";
    }
    
}

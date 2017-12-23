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
@Table(name = "medipay_transaction_history")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MedipayTransactionHistory.findAll", query = "SELECT m FROM MedipayTransactionHistory m"),
    @NamedQuery(name = "MedipayTransactionHistory.findById", query = "SELECT m FROM MedipayTransactionHistory m WHERE m.id = :id"),
    @NamedQuery(name = "MedipayTransactionHistory.findByTransactionDate", query = "SELECT m FROM MedipayTransactionHistory m WHERE m.transactionDate = :transactionDate"),
    @NamedQuery(name = "MedipayTransactionHistory.findByHcTransactionCode", query = "SELECT m FROM MedipayTransactionHistory m WHERE m.hcTransactionCode = :hcTransactionCode"),
    @NamedQuery(name = "MedipayTransactionHistory.findByDebit", query = "SELECT m FROM MedipayTransactionHistory m WHERE m.debit = :debit"),
    @NamedQuery(name = "MedipayTransactionHistory.findByCredit", query = "SELECT m FROM MedipayTransactionHistory m WHERE m.credit = :credit"),
    @NamedQuery(name = "MedipayTransactionHistory.findByBalance", query = "SELECT m FROM MedipayTransactionHistory m WHERE m.balance = :balance")})
public class MedipayTransactionHistory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "transaction_date")
    @Temporal(TemporalType.DATE)
    private Date transactionDate;
    @Size(max = 255)
    @Column(name = "hc_transaction_code")
    private String hcTransactionCode;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "debit")
    private Float debit;
    @Column(name = "credit")
    private Float credit;
    @Column(name = "balance")
    private Float balance;
    @JoinColumn(name = "global_id_number", referencedColumnName = "uuid")
    @ManyToOne
    private GlobalIdNumber globalIdNumber;

    public MedipayTransactionHistory() {
    }

    public MedipayTransactionHistory(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getHcTransactionCode() {
        return hcTransactionCode;
    }

    public void setHcTransactionCode(String hcTransactionCode) {
        this.hcTransactionCode = hcTransactionCode;
    }

    public Float getDebit() {
        return debit;
    }

    public void setDebit(Float debit) {
        this.debit = debit;
    }

    public Float getCredit() {
        return credit;
    }

    public void setCredit(Float credit) {
        this.credit = credit;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
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
        if (!(object instanceof MedipayTransactionHistory)) {
            return false;
        }
        MedipayTransactionHistory other = (MedipayTransactionHistory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "trial.entity.MedipayTransactionHistory[ id=" + id + " ]";
    }
    
}

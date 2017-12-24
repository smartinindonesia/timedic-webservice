/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetimedic.jwt.domain.december;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

@Entity
@Table(name = "medipay_transaction_history")

public class MedipayTransactionHistory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@Basic(optional = false)
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

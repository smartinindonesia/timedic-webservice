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
@Table(name = "homecare_transaction_caregiverlist")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HomecareTransactionCaregiverlist.findAll", query = "SELECT h FROM HomecareTransactionCaregiverlist h"),
    @NamedQuery(name = "HomecareTransactionCaregiverlist.findById", query = "SELECT h FROM HomecareTransactionCaregiverlist h WHERE h.id = :id"),
    @NamedQuery(name = "HomecareTransactionCaregiverlist.findByCaregiverName", query = "SELECT h FROM HomecareTransactionCaregiverlist h WHERE h.caregiverName = :caregiverName"),
    @NamedQuery(name = "HomecareTransactionCaregiverlist.findByRegisterNurseNumber", query = "SELECT h FROM HomecareTransactionCaregiverlist h WHERE h.registerNurseNumber = :registerNurseNumber")})
public class HomecareTransactionCaregiverlist implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 255)
    @Column(name = "caregiver_name")
    private String caregiverName;
    @Column(name = "register_nurse_number")
    private Integer registerNurseNumber;
    @JoinColumn(name = "id_homecare_clinic", referencedColumnName = "id")
    @ManyToOne
    private HomecareHomecareClinic idHomecareClinic;
    @JoinColumn(name = "id_service_transaction", referencedColumnName = "id")
    @ManyToOne
    private HomecareServiceTransaction idServiceTransaction;

    public HomecareTransactionCaregiverlist() {
    }

    public HomecareTransactionCaregiverlist(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCaregiverName() {
        return caregiverName;
    }

    public void setCaregiverName(String caregiverName) {
        this.caregiverName = caregiverName;
    }

    public Integer getRegisterNurseNumber() {
        return registerNurseNumber;
    }

    public void setRegisterNurseNumber(Integer registerNurseNumber) {
        this.registerNurseNumber = registerNurseNumber;
    }

    public HomecareHomecareClinic getIdHomecareClinic() {
        return idHomecareClinic;
    }

    public void setIdHomecareClinic(HomecareHomecareClinic idHomecareClinic) {
        this.idHomecareClinic = idHomecareClinic;
    }

    public HomecareServiceTransaction getIdServiceTransaction() {
        return idServiceTransaction;
    }

    public void setIdServiceTransaction(HomecareServiceTransaction idServiceTransaction) {
        this.idServiceTransaction = idServiceTransaction;
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
        if (!(object instanceof HomecareTransactionCaregiverlist)) {
            return false;
        }
        HomecareTransactionCaregiverlist other = (HomecareTransactionCaregiverlist) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "trial.entity.HomecareTransactionCaregiverlist[ id=" + id + " ]";
    }
    
}

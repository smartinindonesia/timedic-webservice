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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;


@Entity
@Table(name = "global_id_number")

public class GlobalIdNumber implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    //@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "uuid")
    private String uuid;
    @OneToMany(mappedBy = "globalIdNumber")
    private List<MedipayTransaction> medipayTransactionList;
    @OneToMany(mappedBy = "globalIdNumber")
    private List<MedipayTransactionHistory> medipayTransactionHistoryList;
    @OneToMany(mappedBy = "globalIdNumber")
    private List<MedipayVerification> medipayVerificationList;
    @JoinColumn(name = "id_app_user", referencedColumnName = "id")
    @ManyToOne
    private AppUser idAppUser;
    @JoinColumn(name = "id_caregiver", referencedColumnName = "id")
    @ManyToOne
    private HomecareCaregiver idCaregiver;

    public GlobalIdNumber() {
    }

    public GlobalIdNumber(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @XmlTransient
    public List<MedipayTransaction> getMedipayTransactionList() {
        return medipayTransactionList;
    }

    public void setMedipayTransactionList(List<MedipayTransaction> medipayTransactionList) {
        this.medipayTransactionList = medipayTransactionList;
    }

    @XmlTransient
    public List<MedipayTransactionHistory> getMedipayTransactionHistoryList() {
        return medipayTransactionHistoryList;
    }

    public void setMedipayTransactionHistoryList(List<MedipayTransactionHistory> medipayTransactionHistoryList) {
        this.medipayTransactionHistoryList = medipayTransactionHistoryList;
    }

    @XmlTransient
    public List<MedipayVerification> getMedipayVerificationList() {
        return medipayVerificationList;
    }

    public void setMedipayVerificationList(List<MedipayVerification> medipayVerificationList) {
        this.medipayVerificationList = medipayVerificationList;
    }

    public AppUser getIdAppUser() {
        return idAppUser;
    }

    public void setIdAppUser(AppUser idAppUser) {
        this.idAppUser = idAppUser;
    }

    public HomecareCaregiver getIdCaregiver() {
        return idCaregiver;
    }

    public void setIdCaregiver(HomecareCaregiver idCaregiver) {
        this.idCaregiver = idCaregiver;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uuid != null ? uuid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GlobalIdNumber)) {
            return false;
        }
        GlobalIdNumber other = (GlobalIdNumber) object;
        if ((this.uuid == null && other.uuid != null) || (this.uuid != null && !this.uuid.equals(other.uuid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "trial.entity.GlobalIdNumber[ uuid=" + uuid + " ]";
    }
    
}

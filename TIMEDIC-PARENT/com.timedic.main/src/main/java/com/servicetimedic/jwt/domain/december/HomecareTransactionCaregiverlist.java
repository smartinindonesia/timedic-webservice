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
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "homecare_transaction_caregiverlist")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class HomecareTransactionCaregiverlist implements Serializable {
    
	private static final long serialVersionUID = 1L;
    
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    
	@Size(max = 255)
    @Column(name = "caregiver_name")
    private String caregiverName;
    
	@Size(max = 100)
	@Column(name = "register_nurse_number")
    private String registerNurseNumber;
	
	@Size(max = 20)
	@Column(name = "day")
    private String day;
	
	@Column(name = "time")
    @Temporal(TemporalType.TIME)
    private Date time;
	
	@Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
	
	@Column(name = "rate_status")
    private Boolean rateStatus;
	
	@Column(name = "id_caregiver")
    private Long idCaregiver;
	
	@Column(name = "id_transaction")
    private Long idTransaction;
	
    public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
		
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

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

    public String getRegisterNurseNumber() {
        return registerNurseNumber;
    }

    public void setRegisterNurseNumber(String registerNurseNumber) {
        this.registerNurseNumber = registerNurseNumber;
    }
    
    public Boolean getRateStatus() {
		return rateStatus;
	}

	public void setRateStatus(Boolean rateStatus) {
		this.rateStatus = rateStatus;
	}

	public Long getIdCaregiver() {
		return idCaregiver;
	}

	public void setIdCaregiver(Long idCaregiver) {
		this.idCaregiver = idCaregiver;
	}
	
	public Long getIdTransaction() {
		return idTransaction;
	}

	public void setIdTransaction(Long idTransaction) {
		this.idTransaction = idTransaction;
	}

	//@JsonIgnore
    public HomecareHomecareClinic getIdHomecareClinic() {
        return idHomecareClinic;
    }

    public void setIdHomecareClinic(HomecareHomecareClinic idHomecareClinic) {
        this.idHomecareClinic = idHomecareClinic;
    }

    @JsonIgnore
    @XmlTransient
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

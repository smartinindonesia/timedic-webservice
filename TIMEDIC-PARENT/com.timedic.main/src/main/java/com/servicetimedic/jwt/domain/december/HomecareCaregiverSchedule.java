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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "homecare_caregiver_schedule")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class HomecareCaregiverSchedule implements Serializable {
    
	private static final long serialVersionUID = 1L;
    
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
	@Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
	
	@Column(name = "day")
    private String day;
    
	@Column(name = "start_time")
    @Temporal(TemporalType.TIME)
    private Date startTime;
	
	@Column(name = "end_time")
    @Temporal(TemporalType.TIME)
    private Date endTime;
	
	@Column(name = "start_time2")
    @Temporal(TemporalType.TIME)
    private Date startTime2;
	
	@Column(name = "end_time2")
    @Temporal(TemporalType.TIME)
    private Date endTime2;
	
	@Column(name = "status")
    private Boolean status;
    
	@JoinColumn(name = "id_homecare_caregiver", referencedColumnName = "id")
    @ManyToOne
    private HomecareCaregiver idHomecareCaregiver;

    public HomecareCaregiverSchedule() {
    }

    public HomecareCaregiverSchedule(Long id) {
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
    
    public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}


    public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getStartTime2() {
		return startTime2;
	}

	public void setStartTime2(Date startTime2) {
		this.startTime2 = startTime2;
	}

	public Date getEndTime2() {
		return endTime2;
	}

	public void setEndTime2(Date endTime2) {
		this.endTime2 = endTime2;
	}

	public Boolean isStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@XmlTransient
    //@JsonIgnore
    public HomecareCaregiver getIdHomecareCaregiver() {
        return idHomecareCaregiver;
    }

    public void setIdHomecareCaregiver(HomecareCaregiver idHomecareCaregiver) {
        this.idHomecareCaregiver = idHomecareCaregiver;
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
        if (!(object instanceof HomecareCaregiverSchedule)) {
            return false;
        }
        HomecareCaregiverSchedule other = (HomecareCaregiverSchedule) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "trial.entity.HomecareCaregiverSchedule[ id=" + id + " ]";
    }
    
}

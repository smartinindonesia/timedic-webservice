/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetimedic.jwt.domain.december;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "homecare_service_transaction")

public class HomecareServiceTransaction implements Serializable {
    
	private static final long serialVersionUID = 1L;
    
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    
	@Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    
	@Column(name = "fixed_price")
    private Float fixedPrice;
    
	@Column(name = "prediction_price")
    private Float predictionPrice;
    
	@Column(name = "prepaid_price")
    private Float prepaidPrice;
    
	@Column(name = "expired_transaction_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiredTransactionTime;
    
	@Size(max = 255)
    @Column(name = "receipt_path")
    private String receiptPath;
    
	@Column(name = "location_latitude")
    private Float locationLatitude;
    
	@Column(name = "location_longitude")
    private Float locationLongitude;
    
	@Size(max = 255)
    @Column(name = "transaction_description")
    private String transactionDescription;
    
	@OneToMany(mappedBy = "idServiceTransaction", cascade= CascadeType.REMOVE)
    private List<HomecareTransactionCaregiverlist> homecareTransactionCaregiverlistList;
    
	@OneToMany(mappedBy = "idServiceTransaction", cascade= CascadeType.REMOVE)
    private List<HomecareAssessmentRecord> homecareAssessmentRecordList;
    
	@JoinColumn(name = "transaction_status_id", referencedColumnName = "id")
    @ManyToOne
    private SystemTransactionStatus transactionStatusId;
    
	@JoinColumn(name = "homecare_patient_id", referencedColumnName = "id")
    @ManyToOne
    private HomecarePatient homecarePatientId;
    
	@JoinColumn(name = "payment_method_id", referencedColumnName = "id")
    @ManyToOne
    private TimedicPaymentMethod paymentMethodId;

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

    public Float getFixedPrice() {
        return fixedPrice;
    }

    public void setFixedPrice(Float fixedPrice) {
        this.fixedPrice = fixedPrice;
    }

    public Float getPredictionPrice() {
        return predictionPrice;
    }

    public void setPredictionPrice(Float predictionPrice) {
        this.predictionPrice = predictionPrice;
    }

    public Float getPrepaidPrice() {
        return prepaidPrice;
    }

    public void setPrepaidPrice(Float prepaidPrice) {
        this.prepaidPrice = prepaidPrice;
    }

    public Date getExpiredTransactionTime() {
        return expiredTransactionTime;
    }

    public void setExpiredTransactionTime(Date expiredTransactionTime) {
        this.expiredTransactionTime = expiredTransactionTime;
    }

    public String getReceiptPath() {
        return receiptPath;
    }

    public void setReceiptPath(String receiptPath) {
        this.receiptPath = receiptPath;
    }

    public Float getLocationLatitude() {
        return locationLatitude;
    }

    public void setLocationLatitude(Float locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    public Float getLocationLongitude() {
        return locationLongitude;
    }

    public void setLocationLongitude(Float locationLongitude) {
        this.locationLongitude = locationLongitude;
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

    @XmlTransient
    public List<HomecareTransactionCaregiverlist> getHomecareTransactionCaregiverlistList() {
        return homecareTransactionCaregiverlistList;
    }

    public void setHomecareTransactionCaregiverlistList(List<HomecareTransactionCaregiverlist> homecareTransactionCaregiverlistList) {
        this.homecareTransactionCaregiverlistList = homecareTransactionCaregiverlistList;
    }

    @XmlTransient
    public List<HomecareAssessmentRecord> getHomecareAssessmentRecordList() {
        return homecareAssessmentRecordList;
    }

    public void setHomecareAssessmentRecordList(List<HomecareAssessmentRecord> homecareAssessmentRecordList) {
        this.homecareAssessmentRecordList = homecareAssessmentRecordList;
    }

    public SystemTransactionStatus getTransactionStatusId() {
        return transactionStatusId;
    }

    public void setTransactionStatusId(SystemTransactionStatus transactionStatusId) {
        this.transactionStatusId = transactionStatusId;
    }

    public HomecarePatient getHomecarePatientId() {
        return homecarePatientId;
    }

    public void setHomecarePatientId(HomecarePatient homecarePatientId) {
        this.homecarePatientId = homecarePatientId;
    }

    public TimedicPaymentMethod getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(TimedicPaymentMethod paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
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

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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "homecare_service_transaction")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class HomecareServiceTransaction implements Serializable {
    
	private static final long serialVersionUID = 1L;
    
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    
	@Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
	
	@Column(name = "date_order_in")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOrderIn;
	
	@Column(name = "date_treatement_start")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTreatementStart;
	
	@Column(name = "date_treatement_end")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTreatementEnd;
    
	@Column(name = "fixed_price")
    private Float fixedPrice;
    
	@Size(max = 75)
	@Column(name = "prediction_price")
    private String predictionPrice;
    
	@Column(name = "prepaid_price")
    private Float prepaidPrice;
	
	@Column(name = "refund_price")
    private Float refundPrice;
	
	@Column(name = "sum_of_days")
    private Integer sumOfDays;
	
	@Column(name = "expired_transaction_time_fixed_price")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiredTransactionTimeFixedPrice;
	
	@Column(name = "expired_transaction_time_prepaid_price")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiredTransactionTimePrepaidPrice;
    
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
	
	@Size(max = 255)
    @Column(name = "selected_service")
    private String selectedService;
	
	@Size(max = 255)
    @Column(name = "full_address")
    private String fullAddress;
	 
	@Size(max = 50)
	@Column(name = "order_number")
    private String orderNumber;
    
	@OneToMany(mappedBy = "idServiceTransaction", cascade= CascadeType.REMOVE)
    private List<HomecareTransactionCaregiverlist> homecareTransactionCaregiverlistList;
    
	@OneToMany(mappedBy = "idServiceTransaction", cascade= CascadeType.REMOVE)
    private List<HomecareAssessmentRecord> homecareAssessmentRecordList;
	
	@JoinColumn(name = "transaction_type_id", referencedColumnName = "id")
    @ManyToOne
    private SystemTransactionType transactionTypeId;
    
	@JoinColumn(name = "transaction_status_id", referencedColumnName = "id")
    @ManyToOne
    private SystemTransactionStatus transactionStatusId;
	
	@JoinColumn(name = "paymentPrepaidPrice_status_id", referencedColumnName = "id")
    @ManyToOne
    private SystemPaymentStatus paymentPrepaidPriceStatusId;
	
	@JoinColumn(name = "paymentFixedPrice_status_id", referencedColumnName = "id")
    @ManyToOne
    private SystemPaymentStatus paymentFixedPriceStatusId;
    
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

    public String getPredictionPrice() {
        return predictionPrice;
    }

    public void setPredictionPrice(String predictionPrice) {
        this.predictionPrice = predictionPrice;
    }

    public Float getPrepaidPrice() {
        return prepaidPrice;
    }

    public void setPrepaidPrice(Float prepaidPrice) {
        this.prepaidPrice = prepaidPrice;
    }


    public Date getExpiredTransactionTimeFixedPrice() {
		return expiredTransactionTimeFixedPrice;
	}

	public void setExpiredTransactionTimeFixedPrice(
			Date expiredTransactionTimeFixedPrice) {
		this.expiredTransactionTimeFixedPrice = expiredTransactionTimeFixedPrice;
	}

	public Date getExpiredTransactionTimePrepaidPrice() {
		return expiredTransactionTimePrepaidPrice;
	}

	public void setExpiredTransactionTimePrepaidPrice(
			Date expiredTransactionTimePrepaidPrice) {
		this.expiredTransactionTimePrepaidPrice = expiredTransactionTimePrepaidPrice;
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
    
    public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	

	public Date getDateOrderIn() {
		return dateOrderIn;
	}

	public void setDateOrderIn(Date dateOrderIn) {
		this.dateOrderIn = dateOrderIn;
	}

	public SystemPaymentStatus getPaymentPrepaidPriceStatusId() {
		return paymentPrepaidPriceStatusId;
	}

	public void setPaymentPrepaidPriceStatusId(
			SystemPaymentStatus paymentPrepaidPriceStatusId) {
		this.paymentPrepaidPriceStatusId = paymentPrepaidPriceStatusId;
	}

	public SystemPaymentStatus getPaymentFixedPriceStatusId() {
		return paymentFixedPriceStatusId;
	}

	public void setPaymentFixedPriceStatusId(
			SystemPaymentStatus paymentFixedPriceStatusId) {
		this.paymentFixedPriceStatusId = paymentFixedPriceStatusId;
	}

	//@XmlTransient
    //@JsonIgnore
    public List<HomecareTransactionCaregiverlist> getHomecareTransactionCaregiverlistList() {
        return homecareTransactionCaregiverlistList;
    }

    public void setHomecareTransactionCaregiverlistList(List<HomecareTransactionCaregiverlist> homecareTransactionCaregiverlistList) {
        this.homecareTransactionCaregiverlistList = homecareTransactionCaregiverlistList;
    }

    //@XmlTransient
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
    
    public String getSelectedService() {
		return selectedService;
	}

	public void setSelectedService(String selectedService) {
		this.selectedService = selectedService;
	}

	public String getFullAddress() {
		return fullAddress;
	}

	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}
	
	public Date getDateTreatementStart() {
		return dateTreatementStart;
	}

	public void setDateTreatementStart(Date dateTreatementStart) {
		this.dateTreatementStart = dateTreatementStart;
	}

	public Date getDateTreatementEnd() {
		return dateTreatementEnd;
	}

	public void setDateTreatementEnd(Date dateTreatementEnd) {
		this.dateTreatementEnd = dateTreatementEnd;
	}

	public Float getRefundPrice() {
		return refundPrice;
	}

	public void setRefundPrice(Float refundPrice) {
		this.refundPrice = refundPrice;
	}

	public Integer getSumOfDays() {
		return sumOfDays;
	}

	public void setSumOfDays(Integer sumOfDays) {
		this.sumOfDays = sumOfDays;
	}

	public SystemTransactionType getTransactionTypeId() {
		return transactionTypeId;
	}

	public void setTransactionTypeId(SystemTransactionType transactionTypeId) {
		this.transactionTypeId = transactionTypeId;
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

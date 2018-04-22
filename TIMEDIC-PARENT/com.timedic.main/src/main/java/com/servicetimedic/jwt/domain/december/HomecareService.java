/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetimedic.jwt.domain.december;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "homecare_service")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class HomecareService implements Serializable {
    
	private static final long serialVersionUID = 1L;
    
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    
	@Size(max = 255)
    @Column(name = "service_caterogry")
    private String serviceCaterogry;
    
	@Size(max = 4)
    @Column(name = "service_code")
    private String serviceCode;
    
	@Size(max = 255)
    @Column(name = "service_name")
    private String serviceName;
    
	@Size(max = 255)
    @Column(name = "service_url_icon")
    private String serviceUrlIcon;
	
	@Column(name = "visit_cost ")
    private Float visitCost ;
	
	@Size(max = 75)
    @Column(name = "price_range")
    private String priceRange;
    
    @OneToMany(mappedBy = "idService", cascade= CascadeType.REMOVE)
    private List<HomecareServiceAssessment> homecareServiceAssessmentList;

    public HomecareService() {
    }

    public HomecareService(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceCaterogry() {
        return serviceCaterogry;
    }

    public void setServiceCaterogry(String serviceCaterogry) {
        this.serviceCaterogry = serviceCaterogry;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    
    public String getPriceRange() {
		return priceRange;
	}

	public void setPriceRange(String priceRange) {
		this.priceRange = priceRange;
	}

	public String getServiceUrlIcon() {
        return serviceUrlIcon;
    }

    public void setServiceUrlIcon(String serviceUrlIcon) {
        this.serviceUrlIcon = serviceUrlIcon;
    }
      

    public Float getVisitCost() {
		return visitCost;
	}

	public void setVisitCost(Float visitCost) {
		this.visitCost = visitCost;
	}

	@XmlTransient
    @JsonIgnore
    //@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    public List<HomecareServiceAssessment> getHomecareServiceAssessmentList() {
        return homecareServiceAssessmentList;
    }
    
    @JsonIgnore
    public void setHomecareServiceAssessmentList(List<HomecareServiceAssessment> homecareServiceAssessmentList) {
        this.homecareServiceAssessmentList = homecareServiceAssessmentList;
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
        if (!(object instanceof HomecareService)) {
            return false;
        }
        HomecareService other = (HomecareService) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "trial.entity.HomecareService[ id=" + id + " ]";
    }
    
}

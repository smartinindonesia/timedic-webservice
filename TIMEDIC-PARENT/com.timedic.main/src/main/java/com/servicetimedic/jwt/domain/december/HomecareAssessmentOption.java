/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetimedic.jwt.domain.december;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "homecare_assessment_option")

public class HomecareAssessmentOption implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 255)
    @Column(name = "option")
    private String option;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "price_added")
    private Float priceAdded;
    @JoinColumn(name = "id_assessment", referencedColumnName = "id")
    @ManyToOne
    private HomecareAssessment idAssessment;

    public HomecareAssessmentOption() {
    }

    public HomecareAssessmentOption(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public Float getPriceAdded() {
        return priceAdded;
    }

    public void setPriceAdded(Float priceAdded) {
        this.priceAdded = priceAdded;
    }

    public HomecareAssessment getIdAssessment() {
        return idAssessment;
    }

    public void setIdAssessment(HomecareAssessment idAssessment) {
        this.idAssessment = idAssessment;
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
        if (!(object instanceof HomecareAssessmentOption)) {
            return false;
        }
        HomecareAssessmentOption other = (HomecareAssessmentOption) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "trial.entity.HomecareAssessmentOption[ id=" + id + " ]";
    }
    
}

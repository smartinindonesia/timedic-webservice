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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "homecare_assessment_record")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class HomecareAssessmentRecord implements Serializable {
    
	private static final long serialVersionUID = 1L;
    
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    
	@Size(max = 255)
    @Column(name = "assessment_answer")
    private String assessmentAnswer;
    
	@Size(max = 255)
    @Column(name = "file_path")
    private String filePath;
    
	@JoinColumn(name = "id_assessment", referencedColumnName = "id")
    @ManyToOne
    private HomecareAssessment idAssessment;
    
	@JoinColumn(name = "id_service_transaction", referencedColumnName = "id")
    @ManyToOne
    private HomecareServiceTransaction idServiceTransaction;

    public HomecareAssessmentRecord() {
    }

    public HomecareAssessmentRecord(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAssessmentAnswer() {
        return assessmentAnswer;
    }

    public void setAssessmentAnswer(String assessmentAnswer) {
        this.assessmentAnswer = assessmentAnswer;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public HomecareAssessment getIdAssessment() {
        return idAssessment;
    }

    public void setIdAssessment(HomecareAssessment idAssessment) {
        this.idAssessment = idAssessment;
    }

    @JsonIgnore
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
        if (!(object instanceof HomecareAssessmentRecord)) {
            return false;
        }
        HomecareAssessmentRecord other = (HomecareAssessmentRecord) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "trial.entity.HomecareAssessmentRecord[ id=" + id + " ]";
    }
    
}

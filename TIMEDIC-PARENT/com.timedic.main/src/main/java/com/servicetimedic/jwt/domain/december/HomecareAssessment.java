/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetimedic.jwt.domain.december;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "homecare_assessment")

public class HomecareAssessment implements Serializable {
    
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Size(max = 255)
    @Column(name = "question")
    private String question;
    
    @Column(name = "status_active")
    private Integer statusActive;
    
    @Column(name = "assessment_type")
    private Integer assessmentType;
    
    @OneToMany(mappedBy = "idAssessment")
    private Collection<HomecareServiceAssessment> homecareServiceAssessmentCollection;
    
    @OneToMany(mappedBy = "rootId")
    private Collection<HomecareAssessment> homecareAssessmentCollection;
    
    @JoinColumn(name = "root_id", referencedColumnName = "id")
    @ManyToOne
    private HomecareAssessment rootId;
    
    @OneToMany(mappedBy = "idAssessment")
    private Collection<HomecareAssessmentOption> homecareAssessmentOptionCollection;
    
    @OneToMany(mappedBy = "idAssessment")
    private Collection<HomecareAssessmentRecord> homecareAssessmentRecordCollection;

    public HomecareAssessment() {
    }

    public HomecareAssessment(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getStatusActive() {
        return statusActive;
    }

    public void setStatusActive(Integer statusActive) {
        this.statusActive = statusActive;
    }

    //@XmlTransient
    @JsonIgnore
    public Collection<HomecareServiceAssessment> getHomecareServiceAssessmentCollection() {
        return homecareServiceAssessmentCollection;
    }

    public void setHomecareServiceAssessmentCollection(Collection<HomecareServiceAssessment> homecareServiceAssessmentCollection) {
        this.homecareServiceAssessmentCollection = homecareServiceAssessmentCollection;
    }

    //@XmlTransient
    @JsonIgnore
    public Collection<HomecareAssessment> getHomecareAssessmentCollection() {
        return homecareAssessmentCollection;
    }

    public void setHomecareAssessmentCollection(Collection<HomecareAssessment> homecareAssessmentCollection) {
        this.homecareAssessmentCollection = homecareAssessmentCollection;
    }

    public HomecareAssessment getRootId() {
        return rootId;
    }

    public void setRootId(HomecareAssessment rootId) {
        this.rootId = rootId;
    }

    @XmlTransient
    //@JsonIgnore
    public Collection<HomecareAssessmentOption> getHomecareAssessmentOptionCollection() {
        return homecareAssessmentOptionCollection;
    }

    public void setHomecareAssessmentOptionCollection(Collection<HomecareAssessmentOption> homecareAssessmentOptionCollection) {
        this.homecareAssessmentOptionCollection = homecareAssessmentOptionCollection;
    }

    //@XmlTransient
    @JsonIgnore
    public Collection<HomecareAssessmentRecord> getHomecareAssessmentRecordCollection() {
        return homecareAssessmentRecordCollection;
    }

    public void setHomecareAssessmentRecordCollection(Collection<HomecareAssessmentRecord> homecareAssessmentRecordCollection) {
        this.homecareAssessmentRecordCollection = homecareAssessmentRecordCollection;
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
        if (!(object instanceof HomecareAssessment)) {
            return false;
        }
        HomecareAssessment other = (HomecareAssessment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "trial.entity.HomecareAssessment[ id=" + id + " ]";
    }
    
    public Integer getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(Integer assessmentType) {
        this.assessmentType = assessmentType;
    }
    
}

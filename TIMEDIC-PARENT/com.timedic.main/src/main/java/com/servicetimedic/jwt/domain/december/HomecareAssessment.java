/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetimedic.jwt.domain.december;

import java.io.Serializable;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Hafid
 */
@Entity
@Table(name = "homecare_assessment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HomecareAssessment.findAll", query = "SELECT h FROM HomecareAssessment h"),
    @NamedQuery(name = "HomecareAssessment.findById", query = "SELECT h FROM HomecareAssessment h WHERE h.id = :id"),
    @NamedQuery(name = "HomecareAssessment.findByQuestion", query = "SELECT h FROM HomecareAssessment h WHERE h.question = :question"),
    @NamedQuery(name = "HomecareAssessment.findByStatusActive", query = "SELECT h FROM HomecareAssessment h WHERE h.statusActive = :statusActive")})
public class HomecareAssessment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 255)
    @Column(name = "question")
    private String question;
    @Column(name = "status_active")
    private Integer statusActive;
    @OneToMany(mappedBy = "idAssessment")
    private List<HomecareServiceAssessment> homecareServiceAssessmentList;
    @OneToMany(mappedBy = "rootId")
    private List<HomecareAssessment> homecareAssessmentList;
    @JoinColumn(name = "root_id", referencedColumnName = "id")
    @ManyToOne
    private HomecareAssessment rootId;
    @OneToMany(mappedBy = "idAssessment")
    private List<HomecareAssessmentRecord> homecareAssessmentRecordList;

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

    @XmlTransient
    public List<HomecareServiceAssessment> getHomecareServiceAssessmentList() {
        return homecareServiceAssessmentList;
    }

    public void setHomecareServiceAssessmentList(List<HomecareServiceAssessment> homecareServiceAssessmentList) {
        this.homecareServiceAssessmentList = homecareServiceAssessmentList;
    }

    @XmlTransient
    public List<HomecareAssessment> getHomecareAssessmentList() {
        return homecareAssessmentList;
    }

    public void setHomecareAssessmentList(List<HomecareAssessment> homecareAssessmentList) {
        this.homecareAssessmentList = homecareAssessmentList;
    }

    public HomecareAssessment getRootId() {
        return rootId;
    }

    public void setRootId(HomecareAssessment rootId) {
        this.rootId = rootId;
    }

    @XmlTransient
    public List<HomecareAssessmentRecord> getHomecareAssessmentRecordList() {
        return homecareAssessmentRecordList;
    }

    public void setHomecareAssessmentRecordList(List<HomecareAssessmentRecord> homecareAssessmentRecordList) {
        this.homecareAssessmentRecordList = homecareAssessmentRecordList;
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
    
}

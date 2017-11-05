/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techforumist.jwt.domain.posgresql;

import java.io.Serializable;
import java.util.Collection;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Hafid
 */
@Entity
@Table(name = "homecare_assestment")
@XmlRootElement
public class HomecareAssestment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 255)
    @Column(name = "question")
    private String question;
    @Basic(optional = false)
    @NotNull
    @Column(name = "status_active")
    private int statusActive;
    @OneToMany(mappedBy = "idAssestment")
    private Collection<HomecareAssestmentOption> homecareAssestmentOptionCollection;
    @OneToMany(mappedBy = "idAssestment")
    private Collection<HomecareAssestmentRecord> homecareAssestmentRecordCollection;
    @OneToMany(mappedBy = "rootId")
    private Collection<HomecareAssestment> homecareAssestmentCollection;
    @JoinColumn(name = "root_id", referencedColumnName = "id")
    @ManyToOne
    private HomecareAssestment rootId;
    @OneToMany(mappedBy = "idAssestment")
    private Collection<HomecareServiceAssestment> homecareServiceAssestmentCollection;

    public HomecareAssestment() {
    }

    public HomecareAssestment(Long id) {
        this.id = id;
    }

    public HomecareAssestment(Long id, int statusActive) {
        this.id = id;
        this.statusActive = statusActive;
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

    public int getStatusActive() {
        return statusActive;
    }

    public void setStatusActive(int statusActive) {
        this.statusActive = statusActive;
    }

    @XmlTransient
    public Collection<HomecareAssestmentOption> getHomecareAssestmentOptionCollection() {
        return homecareAssestmentOptionCollection;
    }

    public void setHomecareAssestmentOptionCollection(Collection<HomecareAssestmentOption> homecareAssestmentOptionCollection) {
        this.homecareAssestmentOptionCollection = homecareAssestmentOptionCollection;
    }

    @XmlTransient
    public Collection<HomecareAssestmentRecord> getHomecareAssestmentRecordCollection() {
        return homecareAssestmentRecordCollection;
    }

    public void setHomecareAssestmentRecordCollection(Collection<HomecareAssestmentRecord> homecareAssestmentRecordCollection) {
        this.homecareAssestmentRecordCollection = homecareAssestmentRecordCollection;
    }

    @XmlTransient
    public Collection<HomecareAssestment> getHomecareAssestmentCollection() {
        return homecareAssestmentCollection;
    }

    public void setHomecareAssestmentCollection(Collection<HomecareAssestment> homecareAssestmentCollection) {
        this.homecareAssestmentCollection = homecareAssestmentCollection;
    }

    public HomecareAssestment getRootId() {
        return rootId;
    }

    public void setRootId(HomecareAssestment rootId) {
        this.rootId = rootId;
    }

    @XmlTransient
    public Collection<HomecareServiceAssestment> getHomecareServiceAssestmentCollection() {
        return homecareServiceAssestmentCollection;
    }

    public void setHomecareServiceAssestmentCollection(Collection<HomecareServiceAssestment> homecareServiceAssestmentCollection) {
        this.homecareServiceAssestmentCollection = homecareServiceAssestmentCollection;
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
        if (!(object instanceof HomecareAssestment)) {
            return false;
        }
        HomecareAssestment other = (HomecareAssestment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "trial.entity.HomecareAssestment[ id=" + id + " ]";
    }
    
}

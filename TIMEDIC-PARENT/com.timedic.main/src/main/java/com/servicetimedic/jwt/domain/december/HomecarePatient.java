/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetimedic.jwt.domain.december;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Hafid
 */
@Entity
@Table(name = "homecare_patient")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HomecarePatient.findAll", query = "SELECT h FROM HomecarePatient h"),
    @NamedQuery(name = "HomecarePatient.findById", query = "SELECT h FROM HomecarePatient h WHERE h.id = :id"),
    @NamedQuery(name = "HomecarePatient.findByName", query = "SELECT h FROM HomecarePatient h WHERE h.name = :name"),
    @NamedQuery(name = "HomecarePatient.findByReligion", query = "SELECT h FROM HomecarePatient h WHERE h.religion = :religion"),
    @NamedQuery(name = "HomecarePatient.findByDateOfBirth", query = "SELECT h FROM HomecarePatient h WHERE h.dateOfBirth = :dateOfBirth"),
    @NamedQuery(name = "HomecarePatient.findByPlaceOfBirth", query = "SELECT h FROM HomecarePatient h WHERE h.placeOfBirth = :placeOfBirth"),
    @NamedQuery(name = "HomecarePatient.findByHeight", query = "SELECT h FROM HomecarePatient h WHERE h.height = :height"),
    @NamedQuery(name = "HomecarePatient.findByWeight", query = "SELECT h FROM HomecarePatient h WHERE h.weight = :weight")})
public class HomecarePatient implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 255)
    @Column(name = "name")
    private String name;
    @Size(max = 255)
    @Column(name = "religion")
    private String religion;
    @Column(name = "date_of_birth")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfBirth;
    @Size(max = 255)
    @Column(name = "place_of_birth")
    private String placeOfBirth;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "height")
    private Float height;
    @Column(name = "weight")
    private Float weight;
    @JoinColumn(name = "id_app_user", referencedColumnName = "id")
    @ManyToOne
    private AppUser idAppUser;
    @OneToMany(mappedBy = "homecarePatientId")
    private List<HomecareServiceTransaction> homecareServiceTransactionList;
    @OneToMany(mappedBy = "idPatient")
    private List<LaboratoryServiceTransaction> laboratoryServiceTransactionList;

    public HomecarePatient() {
    }

    public HomecarePatient(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public AppUser getIdAppUser() {
        return idAppUser;
    }

    public void setIdAppUser(AppUser idAppUser) {
        this.idAppUser = idAppUser;
    }

    @XmlTransient
    public List<HomecareServiceTransaction> getHomecareServiceTransactionList() {
        return homecareServiceTransactionList;
    }

    public void setHomecareServiceTransactionList(List<HomecareServiceTransaction> homecareServiceTransactionList) {
        this.homecareServiceTransactionList = homecareServiceTransactionList;
    }

    @XmlTransient
    public List<LaboratoryServiceTransaction> getLaboratoryServiceTransactionList() {
        return laboratoryServiceTransactionList;
    }

    public void setLaboratoryServiceTransactionList(List<LaboratoryServiceTransaction> laboratoryServiceTransactionList) {
        this.laboratoryServiceTransactionList = laboratoryServiceTransactionList;
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
        if (!(object instanceof HomecarePatient)) {
            return false;
        }
        HomecarePatient other = (HomecarePatient) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "trial.entity.HomecarePatient[ id=" + id + " ]";
    }
    
}

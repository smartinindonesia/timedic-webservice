/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetimedic.jwt.domain.posgresql;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Hafid
 */
@Entity
@Table(name = "homecare_assestment_record")
@XmlRootElement
public class HomecareAssestmentRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Size(max = 255)
    @Column(name = "assestment_answer")
    private String assestmentAnswer;
    @Size(max = 255)
    @Column(name = "file_path")
    private String filePath;
    @JoinColumn(name = "id_service_transaction", referencedColumnName = "id")
    @ManyToOne
    private HomecareServiceTransaction idServiceTransaction;
    @JoinColumn(name = "selected_option", referencedColumnName = "id")
    @ManyToOne
    private HomecareAssestmentOption selectedOption;
    @JoinColumn(name = "id_assestment", referencedColumnName = "id")
    @ManyToOne
    private HomecareAssestment idAssestment;

    public HomecareAssestmentRecord() {
    }

    public HomecareAssestmentRecord(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAssestmentAnswer() {
        return assestmentAnswer;
    }

    public void setAssestmentAnswer(String assestmentAnswer) {
        this.assestmentAnswer = assestmentAnswer;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public HomecareServiceTransaction getIdServiceTransaction() {
        return idServiceTransaction;
    }

    public void setIdServiceTransaction(HomecareServiceTransaction idServiceTransaction) {
        this.idServiceTransaction = idServiceTransaction;
    }

    public HomecareAssestmentOption getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(HomecareAssestmentOption selectedOption) {
        this.selectedOption = selectedOption;
    }

    public HomecareAssestment getIdAssestment() {
        return idAssestment;
    }

    public void setIdAssestment(HomecareAssestment idAssestment) {
        this.idAssestment = idAssestment;
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
        if (!(object instanceof HomecareAssestmentRecord)) {
            return false;
        }
        HomecareAssestmentRecord other = (HomecareAssestmentRecord) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "trial.entity.HomecareAssestmentRecord[ id=" + id + " ]";
    }
    
}

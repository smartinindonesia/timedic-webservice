/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetimedic.jwt.domain.december;

import java.io.Serializable;
import java.util.List;

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
@Table(name = "system_transaction_type")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SystemTransactionType implements Serializable {
    
	private static final long serialVersionUID = 1L;
    
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    
	@Size(max = 50)
    @Column(name = "type")
    private String type;
    
	@OneToMany(mappedBy = "transactionStatusId")
    private List<HomecareServiceTransaction> homecareServiceTransactionList;
    

    public SystemTransactionType() {
    }

    public SystemTransactionType(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String status) {
        this.type = status;
    }

    @XmlTransient
    @JsonIgnore
    public List<HomecareServiceTransaction> getHomecareServiceTransactionList() {
        return homecareServiceTransactionList;
    }

    public void setHomecareServiceTransactionList(List<HomecareServiceTransaction> homecareServiceTransactionList) {
        this.homecareServiceTransactionList = homecareServiceTransactionList;
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
        if (!(object instanceof SystemTransactionType)) {
            return false;
        }
        SystemTransactionType other = (SystemTransactionType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "trial.entity.SystemTransactionStatus[ id=" + id + " ]";
    }
    
}

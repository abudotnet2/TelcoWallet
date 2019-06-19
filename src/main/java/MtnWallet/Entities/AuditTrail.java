/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MtnWallet.Entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author omolajaabubakar
 */
@Entity
@Table(name = "AuditTrail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AuditTrail.findAll", query = "SELECT a FROM AuditTrail a")
    , @NamedQuery(name = "AuditTrail.findById", query = "SELECT a FROM AuditTrail a WHERE a.id = :id")
    , @NamedQuery(name = "AuditTrail.findByPhoneNo", query = "SELECT a FROM AuditTrail a WHERE a.phoneNo = :phoneNo")
    , @NamedQuery(name = "AuditTrail.findByJsonRequest", query = "SELECT a FROM AuditTrail a WHERE a.jsonRequest = :jsonRequest")
    , @NamedQuery(name = "AuditTrail.findByXmlRequest", query = "SELECT a FROM AuditTrail a WHERE a.xmlRequest = :xmlRequest")
    , @NamedQuery(name = "AuditTrail.findByXmlResponse", query = "SELECT a FROM AuditTrail a WHERE a.xmlResponse = :xmlResponse")
    , @NamedQuery(name = "AuditTrail.findByApiName", query = "SELECT a FROM AuditTrail a WHERE a.apiName = :apiName")
    , @NamedQuery(name = "AuditTrail.findByStatus", query = "SELECT a FROM AuditTrail a WHERE a.status = :status")
    , @NamedQuery(name = "AuditTrail.findByCreatedDate", query = "SELECT a FROM AuditTrail a WHERE a.createdDate = :createdDate")})
public class AuditTrail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Size(max = 20)
    @Column(name = "PhoneNo")
    private String phoneNo;
    @Size(max = 150)
    @Column(name = "JsonRequest")
    private String jsonRequest;
    @Size(max = 350)
    @Column(name = "XmlRequest")
    private String xmlRequest;
    @Size(max = 500)
    @Column(name = "XmlResponse")
    private String xmlResponse;
    @Size(max = 100)
    @Column(name = "ApiName")
    private String apiName;
    @Column(name = "Status")
    private Integer status;
    @Column(name = "CreatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    public AuditTrail() {
    }

    public AuditTrail(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getJsonRequest() {
        return jsonRequest;
    }

    public void setJsonRequest(String jsonRequest) {
        this.jsonRequest = jsonRequest;
    }

    public String getXmlRequest() {
        return xmlRequest;
    }

    public void setXmlRequest(String xmlRequest) {
        this.xmlRequest = xmlRequest;
    }

    public String getXmlResponse() {
        return xmlResponse;
    }

    public void setXmlResponse(String xmlResponse) {
        this.xmlResponse = xmlResponse;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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
        if (!(object instanceof AuditTrail)) {
            return false;
        }
        AuditTrail other = (AuditTrail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MtnWallet.Entities.AuditTrail[ id=" + id + " ]";
    }
    
}

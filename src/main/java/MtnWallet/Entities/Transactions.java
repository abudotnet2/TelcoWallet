/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MtnWallet.Entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "Transactions")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transactions.findAll", query = "SELECT t FROM Transactions t")
    , @NamedQuery(name = "Transactions.findById", query = "SELECT t FROM Transactions t WHERE t.id = :id")
    , @NamedQuery(name = "Transactions.findByPhoneno", query = "SELECT t FROM Transactions t WHERE t.phoneno = :phoneno")
    , @NamedQuery(name = "Transactions.findByAmount", query = "SELECT t FROM Transactions t WHERE t.amount = :amount")
    , @NamedQuery(name = "Transactions.findByProviderTransactionId", query = "SELECT t FROM Transactions t WHERE t.providerTransactionId = :providerTransactionId")
    , @NamedQuery(name = "Transactions.findByTelcoTransactionId", query = "SELECT t FROM Transactions t WHERE t.telcoTransactionId = :telcoTransactionId")
    , @NamedQuery(name = "Transactions.findByProviderStatus", query = "SELECT t FROM Transactions t WHERE t.providerStatus = :providerStatus")
    , @NamedQuery(name = "Transactions.findByTelcoStatus", query = "SELECT t FROM Transactions t WHERE t.telcoStatus = :telcoStatus")
    , @NamedQuery(name = "Transactions.findByAppResponseMessage", query = "SELECT t FROM Transactions t WHERE t.appResponseMessage = :appResponseMessage")
    , @NamedQuery(name = "Transactions.findByCreatedDate", query = "SELECT t FROM Transactions t WHERE t.createdDate = :createdDate")})
public class Transactions implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Size(max = 20)
    @Column(name = "Phone_no")
    private String phoneno;
    @Column(name = "Amount")
    private BigDecimal amount;
    @Size(max = 20)
    @Column(name = "ProviderTransactionId")
    private String providerTransactionId;
    @Size(max = 20)
    @Column(name = "TelcoTransactionId")
    private String telcoTransactionId;
    @Column(name = "ProviderStatus")
    private Integer providerStatus;
    @Column(name = "TelcoStatus")
    private Integer telcoStatus;
    @Size(max = 100)
    @Column(name = "AppResponseMessage")
    private String appResponseMessage;
    @Column(name = "CreatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    public Transactions() {
    }

    public Transactions(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getProviderTransactionId() {
        return providerTransactionId;
    }

    public void setProviderTransactionId(String providerTransactionId) {
        this.providerTransactionId = providerTransactionId;
    }

    public String getTelcoTransactionId() {
        return telcoTransactionId;
    }

    public void setTelcoTransactionId(String telcoTransactionId) {
        this.telcoTransactionId = telcoTransactionId;
    }

    public Integer getProviderStatus() {
        return providerStatus;
    }

    public void setProviderStatus(Integer providerStatus) {
        this.providerStatus = providerStatus;
    }

    public Integer getTelcoStatus() {
        return telcoStatus;
    }

    public void setTelcoStatus(Integer telcoStatus) {
        this.telcoStatus = telcoStatus;
    }

    public String getAppResponseMessage() {
        return appResponseMessage;
    }

    public void setAppResponseMessage(String appResponseMessage) {
        this.appResponseMessage = appResponseMessage;
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
        if (!(object instanceof Transactions)) {
            return false;
        }
        Transactions other = (Transactions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MtnWallet.Entities.Transactions[ id=" + id + " ]";
    }
    
}

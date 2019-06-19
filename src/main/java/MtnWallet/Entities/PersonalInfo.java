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
@Table(name = "PersonalInfo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PersonalInfo.findAll", query = "SELECT p FROM PersonalInfo p")
    , @NamedQuery(name = "PersonalInfo.findById", query = "SELECT p FROM PersonalInfo p WHERE p.id = :id")
    , @NamedQuery(name = "PersonalInfo.findByPhoneno", query = "SELECT p FROM PersonalInfo p WHERE p.phoneno = :phoneno")
    , @NamedQuery(name = "PersonalInfo.findBySurname", query = "SELECT p FROM PersonalInfo p WHERE p.surname = :surname")
    , @NamedQuery(name = "PersonalInfo.findByFirstName", query = "SELECT p FROM PersonalInfo p WHERE p.firstName = :firstName")
    , @NamedQuery(name = "PersonalInfo.findByDob", query = "SELECT p FROM PersonalInfo p WHERE p.dob = :dob")
    , @NamedQuery(name = "PersonalInfo.findByOccupation", query = "SELECT p FROM PersonalInfo p WHERE p.occupation = :occupation")
    , @NamedQuery(name = "PersonalInfo.findByBankHolderDomainName", query = "SELECT p FROM PersonalInfo p WHERE p.bankHolderDomainName = :bankHolderDomainName")
    , @NamedQuery(name = "PersonalInfo.findByHasParent", query = "SELECT p FROM PersonalInfo p WHERE p.hasParent = :hasParent")
    , @NamedQuery(name = "PersonalInfo.findByTelcoStatus", query = "SELECT p FROM PersonalInfo p WHERE p.telcoStatus = :telcoStatus")
    , @NamedQuery(name = "PersonalInfo.findByApiName", query = "SELECT p FROM PersonalInfo p WHERE p.apiName = :apiName")
    , @NamedQuery(name = "PersonalInfo.findByCreatedDate", query = "SELECT p FROM PersonalInfo p WHERE p.createdDate = :createdDate")
    , @NamedQuery(name = "PersonalInfo.findByAppStatus", query = "SELECT p FROM PersonalInfo p WHERE p.appStatus = :appStatus")})
public class PersonalInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Size(max = 20)
    @Column(name = "Phone_no")
    private String phoneno;
    @Size(max = 30)
    @Column(name = "Surname")
    private String surname;
    @Size(max = 30)
    @Column(name = "FirstName")
    private String firstName;
    @Column(name = "DOB")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dob;
    @Size(max = 70)
    @Column(name = "Occupation")
    private String occupation;
    @Size(max = 100)
    @Column(name = "BankHolderDomainName")
    private String bankHolderDomainName;
    @Column(name = "HasParent")
    private Integer hasParent;
    @Column(name = "TelcoStatus")
    private Integer telcoStatus;
    @Size(max = 100)
    @Column(name = "ApiName")
    private String apiName;
    @Column(name = "CreatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "AppStatus")
    private Integer appStatus;

    public PersonalInfo() {
    }

    public PersonalInfo(Integer id) {
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getBankHolderDomainName() {
        return bankHolderDomainName;
    }

    public void setBankHolderDomainName(String bankHolderDomainName) {
        this.bankHolderDomainName = bankHolderDomainName;
    }

    public Integer getHasParent() {
        return hasParent;
    }

    public void setHasParent(Integer hasParent) {
        this.hasParent = hasParent;
    }

    public Integer getTelcoStatus() {
        return telcoStatus;
    }

    public void setTelcoStatus(Integer telcoStatus) {
        this.telcoStatus = telcoStatus;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(Integer appStatus) {
        this.appStatus = appStatus;
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
        if (!(object instanceof PersonalInfo)) {
            return false;
        }
        PersonalInfo other = (PersonalInfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MtnWallet.Entities.PersonalInfo[ id=" + id + " ]";
    }
    
}

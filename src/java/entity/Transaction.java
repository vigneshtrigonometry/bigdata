/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Vignesh
 */
@Entity
@Table(name = "TRANSACTION")
@NamedQueries({
    @NamedQuery(name = Transaction.FINDALL, query = "SELECT t from Transaction t"),
    @NamedQuery(name = Transaction.FINDALLSUCCESS, query = "SELECT t from Transaction t where t.isFraud=0"),
    @NamedQuery(name = Transaction.FINDALLFRAUD, query = "SELECT t from Transaction t where t.isFraud=1"),
    @NamedQuery(name = Transaction.DISTINCT_CITY, query = "SELECT distinct(t.city) from Transaction t"),
    @NamedQuery(name = Transaction.DISTINCT_COUNTY, query = "SELECT distinct(t.county) from Transaction t"),
    @NamedQuery(name = Transaction.DISTINCT_STATE, query = "SELECT distinct (t.state) from Transaction t"),
    @NamedQuery(name = Transaction.FINDBYCITY, query = "SELECT t from Transaction t where t.city = :city"),
    @NamedQuery(name = Transaction.FINDBYCOUNTY, query = "SELECT t from Transaction t where t.county = :county"),
    @NamedQuery(name = Transaction.FINDBYSTATE, query = "SELECT t from Transaction t where t.state = :state"),
    @NamedQuery(name = Transaction.FINDBYFRAUD_BYCITY, query = "SELECT t from Transaction t where t.city = :city and t.isFraud = 1"),
    @NamedQuery(name = Transaction.FINDBYFRAUD_BYCOUNTY, query = "SELECT t from Transaction t where t.county = :county and t.isFraud = 1"),
    @NamedQuery(name = Transaction.FINDBYFRAUD_BYSTATE, query = "SELECT t from Transaction t where t.state = :state and t.isFraud = 1"),
    @NamedQuery(name = Transaction.FINDBYNONFRAUD_BYCITY, query = "SELECT t from Transaction t where t.city = :city and t.isFraud = 0"),
    @NamedQuery(name = Transaction.FINDBYNONFRAUD_BYCOUNTY, query = "SELECT t from Transaction t where t.county = :county and t.isFraud = 0"),
    @NamedQuery(name = Transaction.FINDBYNONFRAUD_BYSTATE, query = "SELECT t from Transaction t where t.state = :state and t.isFraud = 0")    
})
public class Transaction implements Serializable {

    private final static Long serialVersion = 1L;

    public static final String DISTINCT_CITY = "Transaction.DISTINCT_CITY";
    public static final String DISTINCT_COUNTY = "Transaction.DISTINCT_COUNTY";
    public static final String DISTINCT_STATE = "Transaction.DISTINCT_STATE";
    public static final String FINDALLSUCCESS = "Transaction.FINDALLSUCCESS";
            
    public static final String FINDALLFRAUD = "Transaction.FINDALLFRAUD";
    public static final String FINDBYCITY = "Transaction.FINDBYCITY";
    public static final String FINDBYCOUNTY = "Transaction.FINDBYCOUNTY";
    public static final String FINDBYSTATE = "Transaction.FINDBYSTATE";

    public static final String FINDBYFRAUD_BYCITY = "Transaction.FINDBYFRAUD_BYCITY";
    public static final String FINDBYFRAUD_BYCOUNTY = "Transaction.FINDBYFRAUD_BYCOUNTY";
    public static final String FINDBYFRAUD_BYSTATE = "Transaction.FINDBYFRAUD_BYSTATE";
    
    public static final String FINDBYNONFRAUD_BYCITY = "Transaction.FINDBYNONFRAUD_BYCITY";
    public static final String FINDBYNONFRAUD_BYCOUNTY = "Transaction.FINDBYNONFRAUD_BYCOUNTY";
    public static final String FINDBYNONFRAUD_BYSTATE = "Transaction.FINDBYNONFRAUD_BYSTATE";
    public static final String FINDALL = "Transaction.FINDALL";
    @Id
    private Long id;

    @Column
    private Float latitude;

    @Column
    private Float longitude;

    @Column
    private String city;

    @Column
    private String county;
    
    @Column
    private String state;

    @Column
    private Integer zipCode;

    @Column
    private Integer deviceId;

    @Column
    private String merchantName;

    @Column
    private Long accountNo;

    @Column
    private Float transactionAmt;

    @Column
    private Boolean isFraud;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }
    
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public Long getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(Long accountNo) {
        this.accountNo = accountNo;
    }

    public Float getTransactionAmt() {
        return transactionAmt;
    }

    public void setTransactionAmt(Float transactionAmt) {
        this.transactionAmt = transactionAmt;
    }

    public Boolean getIsFraud() {
        return isFraud;
    }

    public void setIsFraud(Boolean isFraud) {
        this.isFraud = isFraud;
    }

}

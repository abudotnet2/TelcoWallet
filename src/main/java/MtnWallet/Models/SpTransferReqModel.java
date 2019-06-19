/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MtnWallet.Models;

import java.math.BigDecimal;

/**
 *
 * @author omolajaabubakar
 */
public class SpTransferReqModel {
 
     private String phoneNo;
    
     public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    } 
    
    
     private BigDecimal amount;
    
     public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    } 
    
    
}

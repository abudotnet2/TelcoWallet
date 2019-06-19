/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MtnWallet.Models;

/**
 *
 * @author omolajaabubakar
 */
public class TransactionStatusResModel {
    
       private String transactionId;
    
     public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    } 
    
    private String status;
    
     public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    } 
}

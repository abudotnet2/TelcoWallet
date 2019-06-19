/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MtnWallet.Models;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import java.util.Date;

/**
 *
 * @author omolajaabubakar
 */
public class LoginResModel {
   
    private String token;
    
     public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    } 
    
     private String expiryDate;
    
     public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    } 
}

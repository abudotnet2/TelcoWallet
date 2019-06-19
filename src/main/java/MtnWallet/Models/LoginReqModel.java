/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MtnWallet.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author omolajaabubakar
 */
//@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginReqModel {
    
    private String userName;
    private String password;
    
    
     public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    } 
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    } 
    
    
}

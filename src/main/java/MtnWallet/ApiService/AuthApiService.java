/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MtnWallet.ApiService;

import MtnWallet.Models.LoginReqModel;
import MtnWallet.Services.AuthService;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author omolajaabubakar
 */
@Path("v1/auth")
public class AuthApiService {
    
    final static Logger logger = Logger.getLogger(AuthApiService.class);
    
      @EJB
     AuthService authService;
    /*
    
    Used by the service provider to check if a certain 
    identity is a valid account holder in Mobile Money Manager
     */
      
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("login")
    public Response doLogin(LoginReqModel model)
    {
        
         if(logger.isInfoEnabled()) 
             logger.info("Api Application starting...--> doLogin called"); 
         
       Object response =  authService.getClientValidation(model);
         
       return  Response.ok(200).entity(response).build();
    }

    /*
    
   Create Userlogin
     */
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("testing")
    public Response postCreateUser(LoginReqModel model) {

     // Object response =  authService.postUserInfo();
        //authService.geUsertResponse();
        
        return Response.status(200).build();
    }
    
   
}

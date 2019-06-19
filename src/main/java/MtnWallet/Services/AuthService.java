/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MtnWallet.Services;

import MtnWallet.Entities.Users;
import MtnWallet.Models.AppResponse;
import MtnWallet.Models.LoginReqModel;
import MtnWallet.Models.LoginResModel;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import io.jsonwebtoken.*;
import java.util.Calendar;
import java.util.Date;  
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;


/**
 *
 * @author omolajaabubakar
 */
@Stateless
public class AuthService {
    
   @EJB
    DBManager dbManager;
    
   final static Logger logger = Logger.getLogger(AuthService.class);
   final static String SECRET_KEY ="7c424df8-048b-4bb0-9fb1-8d223fca5237";
    //Sample method to construct a JWT

private boolean getModelValidation(LoginReqModel model)
{
    boolean flag = false;
    
   // AppResonse reponse  = new AppResonse();
    
    if (model == null) return flag ;
    
    if (model.getUserName() == null || model.getUserName().isEmpty())
        return false;
    
    if (model.getPassword() == null || model.getPassword().isEmpty())
        return false;
    
    if (model.getUserName().length() <3 || model.getPassword().length() <3 )
       return false;
    
    
    return !flag;
}

public AppResponse postUserInfo()
{
    AppResponse response = new AppResponse();

    String salt  = java.util.UUID.randomUUID().toString();
    
      String username ="admin";
    String password ="password1";
    
    // generate random UUID in java for salt..
    String newPassword = UtilityService.getSHA512SecurePassword(password, salt);
    
    Users user = new Users();
  
    
    user.setCreatedBy("1");
    user.setName("SYSTEM");
    user.setPassword(newPassword);
    user.setSalt(salt);
    user.setStatus(1);
    user.setUsername(username);
    
    dbManager.AddUser(user);
    
    response.setResponseCode("00");
    response.setResponseMessage("SUCCESSFUL");
    
    return response;
}


public AppResponse getClientValidation(LoginReqModel model)
{
    AppResponse response = new AppResponse();
    
    try
    {
        boolean flag  = getModelValidation(model);
    
    if (!flag)
    {
        response.setResponseCode(StatusMessage.Error);
        response.setResponseMessage("Model validation failed");
        return response;
    }
    
    String salt  ="17145b5e-5fb9-4b19-8f9c-1f043e017b99"; //java.util.UUID.randomUUID().toString();
    
    // generate random UUID in java for salt..
    String newPassword = UtilityService.getSHA512SecurePassword(model.getPassword(), salt);
    
    boolean isUserValid = dbManager.IsValidUser(model.getUserName(), newPassword);
    
    if (isUserValid)
    {
        // generate token ... 
    LoginResModel loginResponse = createJWTToken(model.getUserName());
     
      response.setResponseCode("00");
      response.setResponseMessage("SUCCESSFUL");
       
      response.setResponseData(loginResponse);
    } 
    }
    catch(Exception ex)
    {
        response.setResponseCode("01");
      response.setResponseMessage(ex.getMessage());
      
        logger.error(ex.getMessage());
        
    }
   
   
    return response;
}


public void geUsertResponse()
{
    try
    {
            dbManager.IsValidUser("admin", "password1");

    }
    catch(Exception ex)
    {
        logger.error(ex.getMessage());
    }
}

//Sample method to construct a JWT
private LoginResModel createJWTToken( String username) {

     String id ="wallet";
     String issuer ="mtnwallet";
     String subject ="mtnwallet";
     
     Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH,2); // date after 2 month
        
     long ttlMillis  =999999999;//cal.getTimeInMillis();// ;
      
     LoginResModel loginResModel = new LoginResModel();
     
    //The JWT signature algorithm we will be using to sign the token
    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    long nowMillis = System.currentTimeMillis();
    Date now = new Date(nowMillis);

    //We will sign our JWT with our ApiKey secret
    byte[] apiKeySecretBytes;
       apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
    Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

    //Let's set the JWT Claims
    JwtBuilder builder = Jwts.builder().setId(id)
                                .setIssuedAt(now)
                                .setSubject(subject)
                                .claim("username", username)
                                .setIssuer(issuer)
                                .signWith(signatureAlgorithm, signingKey);

    //if it has been specified, let's add the expiration
       
     Date exp = null ;
    if (ttlMillis >= 0) {
    long expMillis = nowMillis + ttlMillis;
         exp = new Date(expMillis);
        builder.setExpiration(exp);
    }

    //Builds the JWT and serializes it to a compact, URL-safe string
    loginResModel.setToken(builder.compact());
     loginResModel.setExpiryDate(exp.toString());
    return loginResModel;
}

public static Claims decodeJWTToken(String jwt) {
    //This line will throw an exception if it is not a signed JWS (as expected)
    Claims claims = Jwts.parser()
            .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
            .parseClaimsJws(jwt).getBody();
    return claims;
}

}

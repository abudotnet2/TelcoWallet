/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MtnWallet.Services;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.DatatypeConverter;
/**
 *
 * @author omolajaabubakar
 */

import static MtnWallet.Services.AuthService.SECRET_KEY;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import javax.ws.rs.core.Response;

@Provider
public class SecurityFilter implements ContainerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORIZATION_HEADER_PREFIX = "Bearer ";
    private static final String SECURE_URL_PREFIX = "wallet";

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        List<String> authHeader = requestContext.getHeaders().get(AUTHORIZATION_HEADER);


        if (requestContext.getUriInfo().getPath().contains(SECURE_URL_PREFIX)) {

            try {
                if (authHeader != null && authHeader.size() > 0) {
                    String authToken = authHeader.get(0);
                    authToken = authToken.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
//            byte[] decodeBytArr  = Base64.getDecoder().decode(authToken);// decodeBase64(authToken);
//             String decodedString = new String(decodeBytArr);
                    //  String result =  DatatypeConverter.parseBase64Binary(new String(decodeBytArr));
                    Claims claim = decodeJWTToken(authToken);

                    String username = claim.get("username").toString();
                    
                     if ("admin".equals(username)) {
                            return;
                        }
//                    if (username != null && !username.isEmpty() && username.contains("=>")) {
//                        //String[]  username = 
//                        String[] user_name = username.split("=>");
//
//                        if ("admin".equals(user_name[1])) {
//                            return;
//                        }
//                    }
                    
                    

                }
            } catch (Exception ex) {

            }
            Response unauthorizedStatus = Response
                    .status(Response.Status.UNAUTHORIZED)
                    .encoding("User cannot access the resource.")
                    .build();

            requestContext.abortWith(unauthorizedStatus);

        }
    }

    public static Claims decodeJWTToken(String jwt) {
        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(jwt).getBody();
        return claims;
    }

}

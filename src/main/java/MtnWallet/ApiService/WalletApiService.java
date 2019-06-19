/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MtnWallet.ApiService;

import MtnWallet.Models.AccountHolderReqModel;
import MtnWallet.Models.SpTransferReqModel;
import MtnWallet.Models.TransactionStatusReqModel;
import MtnWallet.Services.WalletService;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;

/**
 *
 * @author omolajaabubakar
 */
@Path("v1/wallet")
public class WalletApiService {

    final static Logger logger = Logger.getLogger(WalletApiService.class);

    @EJB
    WalletService wallerService;

    /*
    Api1 -
    Used by the service provider to check if a certain 
    identity is a valid account holder in Mobile Money Manager
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("validateaccountholder")
    public Response doValidateAccountHolder(AccountHolderReqModel model) {

        // Three step implementation 
        // Step 1 : Accept Json request as Object, validate it and Log
        // Step 2: Parse that Json to xml and log on table and file 
        // Step 3: Post to Telco endpoint and get response .. 
        // Step 4: log response on table and file then respond to user 
     Object response = wallerService.postAndValidateAccountHolder(model);

       return Response.ok(200).entity(response).build();
      
    }

   

    /*
    Api2 -
    Used by the Service Provider to transfers money or
    loyalty points from the service provider's account
    to another registered account
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("sptransfer")
    public Response doSpTransfer(SpTransferReqModel model) {

        Object response = wallerService.postSpTransfer(model);

        return Response.ok(200).entity(response).build();
    }

    /**
     *
     * To get status of previously initiated Payment, Transfer or Debit
     * operation.
     *
     * @param model
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("transactionstatus")
    public Response getTransactionStatus(TransactionStatusReqModel model) {

        Object response = wallerService.getTransactionStatus(model);

        return Response.ok(200).entity(response).build();
    }

    /*
      Api3 -
       Returns identification or identifications connected to the identity sent in the request
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("accountholderidentification")
    public Response doGetAccountHolderIdentification() {

        return null;
    }

    /**
     *
     * Returns some basic information on the account holder connected to the
     * identity. The difference from get-account-holder is that it is not as
     * much information.
     *
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("accountHolderInfo")
    public Response doGetAccountHolderInfo() {

        return null;
    }

    /**
     *
     * Obtain the balance for a specific account. If this operation is used in a
     * multi-currency system, it is required to use Resolved FRI or
     * AMBIGUOUS_CURRENCY will be returned.
     *
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("balance")
    public Response doGetBalance() {

        return null;
    }

    /**
     *
     * Returns personal information connected to the identity sent in the
     * request
     *
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("accountHolderpersonalInfo")
    public Response doGetaccountholderpersonalinformation() {

        return null;
    }
}

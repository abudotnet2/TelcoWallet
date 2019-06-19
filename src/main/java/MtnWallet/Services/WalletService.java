/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MtnWallet.Services;

import MtnWallet.ApiService.AuthApiService;
import MtnWallet.Entities.AuditTrail;
import MtnWallet.Models.AccountHolderReqModel;
import MtnWallet.Models.AccountHolderResponse;
import MtnWallet.Models.AppResponse;
import MtnWallet.Models.SpTransferReqModel;
import MtnWallet.Models.SpTransferResModel;
import MtnWallet.Models.TransactionStatusReqModel;
import MtnWallet.Models.TransactionStatusResModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Date;
import java.time.LocalDate;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.apache.log4j.Logger;

/**
 *
 * @author omolajaabubakar
 */
@Stateless
public class WalletService {

    @EJB
    private DBManager dbManager;

    final static Logger logger = Logger.getLogger(WalletService.class);

    // For Api - validate Account Holder 
    public AppResponse postAndValidateAccountHolder(AccountHolderReqModel model) {

        AppResponse response = new AppResponse();
        AccountHolderResponse responseModel = new AccountHolderResponse();

        try {

            if (model == null) {
                throw new Exception("Account holder request model is not valid");
            }

            boolean isValid = UtilityService.getValidatedPhoneNo(model.getPhoneNo());

            if (!isValid) {
                throw new Exception("Phone number is not valid");
            }

            ObjectMapper mapper = new ObjectMapper();
            // Java objects to JSON string - pretty-print
            String objToJsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(model);

            logger.info(objToJsonString);

            String xmlRequestMessage = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ns0:validateaccountholderrequest"
                    + " xmlns:ns0=\"http://www.ericsson.com/em/emm/serviceprovider/v1_0/backend\">"
                    + "<accountholderid>ID:" + model.getPhoneNo() + "/MSISDN</accountholderid>"
                    + "</ns0:validateaccountholderrequest>";

//                 String xmlRequestMessage = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ns0:validateaccountholderrequest"
//                    + " xmlns:ns0=\'"+ApiRoot.AccountHolderApi +"\">"
//                    + "<accountholderid>ID:" + model.getPhoneNo() + "/MSISDN</accountholderid>"
//                    + "</ns0:validateaccountholderrequest>";
            logger.info(xmlRequestMessage);

            UtilityService utilityService = new UtilityService();
             
           String api = UtilityService.getConfigValue("mtnwallet.baseContext") + "/gettransactionstatus";
            // post request xml to telco service.
            //String telcoResponse = utilityService.postXmlMessage(ApiRoot.AccountHolderApi, xmlRequestMessage);
            String telcoResponse = utilityService.xmlPosting(api.trim(), xmlRequestMessage);
            // sample response
            String testTelcoResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                    + "<ns2:validateaccountholderresponse xmlns:ns2=\"http://www.ericsson.com/em/emm/serviceprovider/v1_0/backend\">"
                    + "<valid>false</valid></ns2:validateaccountholderresponse>";

            logger.info(telcoResponse);

            if (telcoResponse != null && telcoResponse.isEmpty()) {
                // extract valid content out of the response message ...
                responseModel = utilityService.parseXmlResponseToObj(responseModel, telcoResponse);
            }

            // Log request trace to db 
            dbManager.addAuditTrailToDB("ValidateAccountHolder", model.getPhoneNo(), xmlRequestMessage, telcoResponse);

            if (telcoResponse == null || telcoResponse.isEmpty()) {
                throw new Exception("Result from telco is empty");
            }

            response.setResponseCode(StatusMessage.Successful);
            response.setResponseMessage(StatusMessage.Successful_Message);
            response.setResponseData(responseModel);
        } catch (JsonProcessingException ex) {

            logger.error(ex.getMessage());

            response.setResponseCode(StatusMessage.Error);
            response.setResponseMessage(ex.getMessage());

            // java.util.logging.Logger.getLogger(WalletService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            ex.printStackTrace();

            response.setResponseCode(StatusMessage.Error);
            response.setResponseMessage(ex.getMessage());
            logger.error(ex.getMessage());
        }

        return response;
    }

    // For Api - spTransfer  
    public AppResponse postSpTransfer(SpTransferReqModel model) {

        AppResponse response = new AppResponse();
        SpTransferResModel responseModel = new SpTransferResModel();

        try {
            String tranxId = null;
            if (model == null) {
                throw new Exception("SpTransfer model request cannot be empty");
            }

            boolean isValid = UtilityService.getValidatedPhoneNo(model.getPhoneNo());

            if (!isValid) {
                throw new Exception("Phone number is not valid");
            }

            ObjectMapper mapper = new ObjectMapper();

            // Java objects to JSON string - pretty-print
            String objToJsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(model);

            logger.info(objToJsonString);

            String transactionId = UtilityService.getRandomTransactionId();

            if (model.getAmount() == null || model.getAmount().toString().isEmpty() || model.getAmount().intValue() < 1) {
                throw new Exception("Amount is not valid");
            }

            String xmlRequestMessage = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ns0:sptransferrequest xmlns:ns0=\"http://www.ericsson.com/em/emm/serviceprovider/v1_2/backend\">"
                    + "<receivingfri>FRI:" + model.getPhoneNo() + "/MSISDN</receivingfri><amount>" + model.getAmount() + "</amount>"
                    + "<providertransactionid>" + transactionId + "</providertransactionid></ns0:sptransferrequest>";

            logger.info(xmlRequestMessage);

            UtilityService utilityService = new UtilityService();

            String api = UtilityService.getConfigValue("mtnwallet.baseContext") + "/sptransfer";

            // post request xml to telco service.
            String telcoResponse = utilityService.xmlPosting(api.trim(), xmlRequestMessage);

            // sample response
            String testTelcoResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                    + "<ns0:sptransferresponse xmlns:ns0=\"http://www.ericsson.com/em/emm/serviceprovider/v1_0/backend\">"
                    + "<transactionid>10589</transactionid>"
                    + "</ns0:sptransferresponse>";

            logger.info(telcoResponse);

            // extract valid content out of the response message ...
            if (telcoResponse != null && !telcoResponse.isEmpty()) {
                responseModel = utilityService.parseXmlResponseToObj(responseModel, telcoResponse);

                tranxId = UtilityService.getRandomTransactionId();

                int telcoStatus = 0;
                int providerStatus = 0;
                String appMessage = StatusMessage.Error;

                if (responseModel.getTransactionId() != null && !responseModel.getTransactionId().isEmpty()) {
                    telcoStatus = 1;
                    providerStatus = 1;
                    appMessage = StatusMessage.Successful;
                }

                // post transaction to db ..
                dbManager.addTransactionToDb(model.getPhoneNo(), model.getAmount(), appMessage,
                        tranxId, providerStatus, transactionId, telcoStatus);
            }

            // Log request trace to db 
            dbManager.addAuditTrailToDB("spTransfer", model.getPhoneNo(), xmlRequestMessage, telcoResponse);

            if (telcoResponse == null || telcoResponse.isEmpty()) {
                throw new Exception("Telco response is empty");
            }

            response.setResponseCode(StatusMessage.Successful);
            response.setResponseMessage(StatusMessage.Successful_Message);
            responseModel.setProviderTransactionId(tranxId);
            response.setResponseData(responseModel);

        } catch (JsonProcessingException ex) {

            logger.error(ex.getMessage());

            response.setResponseCode(StatusMessage.Error);
            response.setResponseMessage(ex.getMessage());

            // java.util.logging.Logger.getLogger(WalletService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setResponseCode(StatusMessage.Error);
            response.setResponseMessage(ex.getMessage());
            logger.error(ex.getMessage());
        }

        return response;
    }

    //  For Api - get Account Holder Identification - WIP
    public AppResponse getAccountHolderIdentification(AccountHolderReqModel model) {

        AppResponse response = new AppResponse();
        SpTransferResModel responseModel = new SpTransferResModel();

        try {

            if (model == null) {
                throw new Exception("SpTransfer model request cannot be empty");
            }

            boolean isValid = UtilityService.getValidatedPhoneNo(model.getPhoneNo());

            if (!isValid) {
                throw new Exception("Phone number is not valid");
            }

            ObjectMapper mapper = new ObjectMapper();
            // Java objects to JSON string - pretty-print
            String objToJsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(model);

            logger.info(objToJsonString);

            String xmlRequestMessage = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                    + "<ns0:getaccountholderidentificationrequest xmlns:ns0=\"http://www.ericsson.com/em/emm/provisioning/v1_0\">"
                    + "</ns0:getaccountholderidentificationrequest>";

            logger.info(xmlRequestMessage);

            UtilityService utilityService = new UtilityService();

            // post request xml to telco service.
            String telcoResponse = utilityService.postXmlMessage(ApiRoot.AccountHolderIdentificationApi, xmlRequestMessage);

            // sample response
            String testTelcoResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                    + "<ns2:getaccountholderidentificationresponse xmlns:ns2=\"http://www.ericsson.com/em/emm/provisioning/v1_0\">"
                    + "<identifications/></ns2:getaccountholderidentificationresponse>";

            logger.info(telcoResponse);

            // extract valid content out of the response message ...
            if (telcoResponse != null && !telcoResponse.isEmpty()) {
                responseModel = utilityService.parseXmlResponseToObj(responseModel, telcoResponse);
            }

            // Log request trace to db 
            dbManager.addAuditTrailToDB("spTransfer", model.getPhoneNo(), xmlRequestMessage, telcoResponse);

            response.setResponseCode(StatusMessage.Successful);
            response.setResponseMessage(StatusMessage.Successful_Message);
            response.setResponseData(responseModel);
        } catch (JsonProcessingException ex) {

            logger.error(ex.getMessage());

            response.setResponseCode(StatusMessage.Error);
            response.setResponseMessage(ex.getMessage());

            // java.util.logging.Logger.getLogger(WalletService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setResponseCode(StatusMessage.Error);
            response.setResponseMessage(ex.getMessage());
            logger.error(ex.getMessage());
        }

        return response;
    }

    // For Api - get Account Holder Info WIP
    public AppResponse getAccountHolderInformation(AccountHolderReqModel model) {

        AppResponse response = new AppResponse();
        SpTransferResModel responseModel = new SpTransferResModel();

        try {

            if (model == null) {
                throw new Exception("SpTransfer model request cannot be empty");
            }

            boolean isValid = UtilityService.getValidatedPhoneNo(model.getPhoneNo());

            if (!isValid) {
                throw new Exception("Phone number is not valid");
            }

            ObjectMapper mapper = new ObjectMapper();
            // Java objects to JSON string - pretty-print
            String objToJsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(model);

            logger.info(objToJsonString);

            String xmlRequestMessage = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                    + "<ns0:getaccountholderinforequest xmlns:ns0=\"http://www.ericsson.com/em/emm/provisioning/v1_0\">"
                    + "</ns0:getaccountholderinforequest>";

            logger.info(xmlRequestMessage);

            UtilityService utilityService = new UtilityService();

            // post request xml to telco service.
            String telcoResponse = utilityService.postXmlMessage(ApiRoot.AccountHolderIdentificationApi, xmlRequestMessage);

            // sample response
            String testTelcoResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                    + "<ns2:getaccountholderidentificationresponse xmlns:ns2=\"http://www.ericsson.com/em/emm/provisioning/v1_0\">"
                    + "<identifications/></ns2:getaccountholderidentificationresponse>";

            logger.info(telcoResponse);

            // extract valid content out of the response message ...
            if (telcoResponse != null && !telcoResponse.isEmpty()) {
                responseModel = utilityService.parseXmlResponseToObj(responseModel, telcoResponse);
            }

            // Log request trace to db 
            dbManager.addAuditTrailToDB("spTransfer", model.getPhoneNo(), xmlRequestMessage, telcoResponse);

            response.setResponseCode(StatusMessage.Successful);
            response.setResponseMessage(StatusMessage.Successful_Message);
            response.setResponseData(responseModel);
        } catch (JsonProcessingException ex) {

            logger.error(ex.getMessage());

            response.setResponseCode(StatusMessage.Error);
            response.setResponseMessage(ex.getMessage());

            // java.util.logging.Logger.getLogger(WalletService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setResponseCode(StatusMessage.Error);
            response.setResponseMessage(ex.getMessage());
            logger.error(ex.getMessage());
        }

        return response;
    }

    // For Api - get Transaction status Info WIP
    public AppResponse getTransactionStatus(TransactionStatusReqModel model) {

        AppResponse response = new AppResponse();
        TransactionStatusResModel responseModel = new TransactionStatusResModel();

        try {

            if (model == null) {
                throw new Exception("SpTransfer model request cannot be empty");
            }

            ObjectMapper mapper = new ObjectMapper();
            // Java objects to JSON string - pretty-print
            String objToJsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(model);

            logger.info(objToJsonString);

            String xmlRequestMessage = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                    + "<ns0:gettransactionstatusrequest xmlns:ns0=\"http://www.ericsson.com/em/emm/financial/v1_2\">"
                    + "<financialtransactionid>" + model.getTransactionId() + "</financialtransactionid></ns0:gettransactionstatusrequest>";

            logger.info(xmlRequestMessage);

            UtilityService utilityService = new UtilityService();

            // post request xml to telco service.
             String api = UtilityService.getConfigValue("mtnwallet.baseContext") + "/gettransactionstatus";

            String telcoResponse = utilityService.postXmlMessage(api.trim(), xmlRequestMessage);

            // sample response
            String testTelcoResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                    + "<ns4:gettransactionstatusresponse xmlns:op=\"http://www.ericsson.com/em/emm/v1_0/common\""
                    + "xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:ns4=\"http://www.ericsson.com/em/emm/financial/v1_2\">"
                    + "<financialtransactionid>10586</financialtransactionid><status>SUCCESSFUL</status></ns4:gettransactionstatusresponse>";

            logger.info(telcoResponse);

            // extract valid content out of the response message ...
            if (telcoResponse != null && !telcoResponse.isEmpty()) {
                responseModel = utilityService.parseXmlResponseToObj(responseModel, telcoResponse);
            }

            // Log request trace to db 
            dbManager.addAuditTrailToDB("transactionStatus", model.getTransactionId(), xmlRequestMessage, telcoResponse);

            if (telcoResponse == null || telcoResponse.isEmpty()) {
                throw new Exception("Telco response is empty");
            }

            response.setResponseCode(StatusMessage.Successful);
            response.setResponseMessage(StatusMessage.Successful_Message);
            response.setResponseData(responseModel);

        } catch (JsonProcessingException ex) {

            logger.error(ex.getMessage());

            response.setResponseCode(StatusMessage.Error);
            response.setResponseMessage(ex.getMessage());

            // java.util.logging.Logger.getLogger(WalletService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setResponseCode(StatusMessage.Error);
            response.setResponseMessage(ex.getMessage());
            logger.error(ex.getMessage());
        }

        return response;
    }
}

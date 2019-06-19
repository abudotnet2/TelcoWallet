/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MtnWallet.Services;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Properties;
import static org.hibernate.jpa.internal.EntityManagerImpl.LOG;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;
import java.util.logging.Logger;
import static javafx.scene.input.KeyCode.T;
import javax.xml.bind.Element;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.dom4j.Node;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author omolajaabubakar
 */
public class UtilityService {

    final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(UtilityService.class);
    
    private final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) Version/7.0.3 Safari/7046A194A";

    public static String getConfigValue(String configName)
    {
        return getProperty().getProperty(configName);
    }
    private static Properties getProperty() {

        Properties prop = new Properties();

        try {

            File file = new File(getConfig());
            FileInputStream fileInput = new FileInputStream(file);
            prop.load(fileInput);
        } catch (IOException e) {
            LOG.error(Arrays.toString(e.getStackTrace()));
        }

        return prop;//.getProperty(value);
    }

    private static String getConfig() {
        final String CONFIG_FILE_PATH;
        boolean LIVE_FLAG = true;
        
        if (LIVE_FLAG) {
            CONFIG_FILE_PATH
                    =  "C:\\mtnwallet\\config.properties";
                    //"/opt/wildfly/settings.properties";
                    //"/Users/gbonjubolaamuda/config/settings.properties";
        } else {
            CONFIG_FILE_PATH
                    =//"/opt/wildfly/settings.properties";
                    "/Users/omolajaabubakar/configs/config.properties";
        }
        return CONFIG_FILE_PATH;
    }

    // HTTP GET request
    private void sendGet() throws Exception {

        String url = "http://www.google.com/search?q=developer";

        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);

        // add request header
        request.addHeader("User-Agent", USER_AGENT);

        HttpResponse response = client.execute(request);

        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : "
                + response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuilder result = new StringBuilder();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        System.out.println(result.toString());

    }

    // HTTP POST request
    private void sendPost(String url) throws Exception {

        //String url = "https://selfsolve.apple.com/wcResults.do";
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        // add header
        post.setHeader("User-Agent", USER_AGENT);

        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("sn", "C02G8416DRJM"));
        urlParameters.add(new BasicNameValuePair("cn", ""));
        urlParameters.add(new BasicNameValuePair("locale", ""));
        urlParameters.add(new BasicNameValuePair("caller", ""));
        urlParameters.add(new BasicNameValuePair("num", "12345"));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + post.getEntity());
        System.out.println("Response Code : "
                + response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuilder result = new StringBuilder();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        System.out.println(result.toString());

    }

    public  String xmlPosting(String urlString, String xmlMessage) {
         
          StringBuilder response = new StringBuilder();
        try {
            String url = "http://www.holidaywebservice.com/HolidayService_v2/HolidayService2.asmx?op=GetCountriesAvailable";
            URL obj = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/soap+xml; charset=utf-8");
            String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?> "
                    + "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\"> "
                    + " <soap12:Body><GetCountriesAvailable xmlns=\"http://www.holidaywebservice.com/HolidayService_v2/\" />"
                    + " </soap12:Body> "
                    + "</soap12:Envelope>";
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(xmlMessage);
            wr.flush();
            wr.close();
            String responseStatus = con.getResponseMessage();
            System.out.println(responseStatus);
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
           
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            //System.out.println("response:" + response.toString());
        } catch (IOException e) {
            System.out.println(e);
        }
        return response.toString();
    }
     
    public String postXmlMessage(String urlString, String xmlMessage) {
        final StringBuffer content = new StringBuffer();

        try {
            //final String parameters = "?name=tecbar&group=middleschool";
            final URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", USER_AGENT);
            conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setConnectTimeout(5000);

            // Send post request
            conn.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
                //final String xmlMessage = "<channel>ABC</channel><title>tecbar.net demo pages</title>";
                wr.writeBytes(xmlMessage);
                // send request
                wr.flush();
                // close
            }

            try ( // read response
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String str;
                while ((str = in.readLine()) != null) {
                    content.append(str);
                }
            }

            return content.toString();
        } catch (IOException ex) {

        }

        return content.toString();

    }

    public String postMessageHttpClient(String urlString, String xmlMessage) {
        String response = "";
        try {
            // DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpClient httpclient = HttpClientBuilder.create().build();
            //final String parameters = "?name=tecbar&group=middleschool";
            HttpPost httppost = new HttpPost(urlString);
            httppost.setHeader("USER_AGENT", USER_AGENT);
            httppost.setHeader("HTTP.CONTENT_TYPE", "text/xml; charset=UTF-8");

            //final String xmlMessage = "<channel>ABC</channel><title>tecbar.net demo pages</title>";
            httppost.setEntity(new StringEntity(xmlMessage));

            HttpResponse httpResponse = httpclient.execute(httppost);
            HttpEntity entity = httpResponse.getEntity();
            response = EntityUtils.toString(entity);

            return response;
        } catch (Exception ex) {

        }
        return response;
    }

    public <T> T parseXmlResponseToObj(T element, String xmlString) {
        try {

            JAXBContext jaxbContext = JAXBContext.newInstance(element.getClass());

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            element = (T) jaxbUnmarshaller.unmarshal(new StringReader(xmlString));

            return element;

        } catch (Exception ex) {

        }

        return element;
    }

    private static void parseXmlToObject() {
        try {
            //Initialize a list of employees
            //Get Document Builder
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            //Build Document
            org.w3c.dom.Document document = builder.parse(new File("employees.xml"));

            //Normalize the XML Structure; It's just too important !!
            document.getDocumentElement().normalize();

            //Here comes the root node
            org.w3c.dom.Element root = document.getDocumentElement();
            System.out.println(root.getNodeName());

            //Get all employees
            NodeList nList = document.getElementsByTagName("employee");
            System.out.println("============================");

            visitChildNodes(nList);

        } catch (IOException | ParserConfigurationException | SAXException ex) {
            logger.error(ex.getMessage());
        }

    }

    //This function is called recursively
    private static void visitChildNodes(NodeList nList) {
        for (int temp = 0; temp < nList.getLength(); temp++) {
            org.w3c.dom.Node node = nList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                System.out.println("Node Name = " + node.getNodeName() + "; Value = " + node.getTextContent());
                //Check all attributes
                if (node.hasAttributes()) {
                    // get attributes names and values
                    NamedNodeMap nodeMap = node.getAttributes();
                    for (int i = 0; i < nodeMap.getLength(); i++) {
                        org.w3c.dom.Node tempNode = nodeMap.item(i);
                        System.out.println("Attr name : " + tempNode.getNodeName() + "; Value = " + tempNode.getNodeValue());
                    }
                    if (node.hasChildNodes()) {
                        //We got more childs; Let's visit them as well
                        visitChildNodes(node.getChildNodes());
                    }
                }
            }
        }
    }

    public static String getSHA512SecurePassword(String passwordToHash, String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    // Validate user phone number 
    public static boolean getValidatedPhoneNo(String phoneNo) {
        boolean flag = false;

        if (phoneNo == null || phoneNo.isEmpty()) {
            return flag;
        }

        if (phoneNo.length() < 11) {
            return flag;
        }

        return !flag;
    }

    // Generate random transaction Id
    private static String getProviderTransactionId() {
        return UUID.randomUUID().toString().replace('-', '0').substring(0, 25);
    }

    public static String getRandomTransactionId() {
        
        try {
            SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");

            //generate a random number
            //Integer.valueOf(prng.nextInt()).toString();
            String randomNum = Integer.toString(prng.nextInt());

            //get its digest
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] result = sha.digest(randomNum.getBytes());

            return hexEncode(result).substring(0, 28);
        } catch ( NoSuchAlgorithmException ex) {

        }
        catch (Exception ex)
        {
            
        }
        
        return getProviderTransactionId();

    }

    static private String hexEncode(byte[] input) {
        StringBuilder result = new StringBuilder();
        char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        for (int idx = 0; idx < input.length; ++idx) {
            byte b = input[idx];
            result.append(digits[(b & 0xf0) >> 4]);
            result.append(digits[b & 0x0f]);
        }
        return result.toString();
    }
}

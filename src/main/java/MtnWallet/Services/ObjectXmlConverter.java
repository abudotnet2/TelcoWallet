/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MtnWallet.Services;

import MtnWallet.Models.LoginReqModel;
import java.io.File;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author omolajaabubakar
 */
public class ObjectXmlConverter {
    
    public ObjectXmlConverter()
    {
        // object xml 
         PropertyConfigurator.configure("log4j.properties"); 
    }
    
    
    final static Logger logger = Logger.getLogger(ObjectXmlConverter.class);
   
     public static void jsonObjectFromTelcoXml(LoginReqModel model)
    {
        try
        {
             
            //Create JAXB Context
            JAXBContext jaxbContext = JAXBContext.newInstance(LoginReqModel.class);
             
            //Create Marshaller
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
 
            //Required formatting??
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
 
            //Print XML String to Console
            StringWriter sw = new StringWriter();
             
            //Write XML to StringWriter
            jaxbMarshaller.marshal(model, sw);
             
            //Verify XML Content
            String xmlContent = sw.toString();
            
            logger.info(sw.toString());
            System.out.println( xmlContent );
 
        } catch (JAXBException e) {
            e.printStackTrace();
            
            logger.error(e.getMessage());
        }
    }
    
    private static void jaxbXmlFileToObject(String fileName) {
         
        File xmlFile = new File(fileName);
         
        JAXBContext jaxbContext;
        
        try
        {
            jaxbContext = JAXBContext.newInstance(ObjectXmlConverter.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
           // Employee employee = (Employee) jaxbUnmarshaller.unmarshal(xmlFile);
             
            //System.out.println(employee);
        }
        catch (JAXBException e)
        {
            e.printStackTrace();
            
            logger.error(e.getMessage());
        }
    }
}

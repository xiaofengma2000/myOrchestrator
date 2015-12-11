package com.kema;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.junit.Test;

public class SaajMessageSender
{

    @Test
    public void testSaaj1() throws Exception
    {
        SOAPMessage soapMessage = MessageFactory.newInstance(
                SOAPConstants.SOAP_1_1_PROTOCOL).createMessage();
        
        SOAPElement reqElement = soapMessage.getSOAPBody().addChildElement(
                new QName("http://kema.com", "testRpcLiteral"));

        reqElement.addChildElement(new QName ("", "id")).setValue("10");
        reqElement.addChildElement(new QName ("", "name")).setValue("kema");
        reqElement.addChildElement(new QName ("", "action")).addAttribute(
                new QName("http://www.w3.org/2001/XMLSchema-instance", "nil"),"true");

        System.out.println(convertToString(new DOMSource(soapMessage.getSOAPPart())));

        SOAPConnection con = SOAPConnectionFactory.newInstance().createConnection();
        

        SOAPMessage res = con.call(soapMessage, "http://localhost:8080/RpcTests");
        System.out.println(convertToString(new DOMSource(res.getSOAPPart())));
    }
    
    @Test
    public void testSaaj2() throws Exception
    {
        String str = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\""
                + "     xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
                + "  <SOAP-ENV:Header/>"
                + "  <SOAP-ENV:Body>"
                + "    <testRpcLiteral xmlns=\"http://kema.com\">"
                + "      <id>10</id>"
                + "      <name>kema</name>"
                + "      <action xsi:nil=\"true\"/>"
                + "    </testRpcLiteral>"
                + "  </SOAP-ENV:Body>"
                + "</SOAP-ENV:Envelope>";
        
        SOAPMessage soapMessage = MessageFactory.newInstance().createMessage();
        soapMessage.getSOAPPart().setContent(new StreamSource(new StringReader(str)));
        
        System.out.println(convertToString(new DOMSource(soapMessage.getSOAPPart())));

        SOAPConnection con = SOAPConnectionFactory.newInstance()
                .createConnection();

        SOAPMessage res = con.call(soapMessage,
                "http://localhost:8080/RpcTests");
        
        System.out.println(convertToString(new DOMSource(res.getSOAPPart())));
    }

    static String convertToString(Source xmlSource) throws Exception
    {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(
                "{http://xml.apache.org/xslt}indent-amount", "2");
        
        StringWriter writer = new StringWriter();
        transformer.transform(xmlSource, new StreamResult(writer));
        return writer.toString();
    }


}

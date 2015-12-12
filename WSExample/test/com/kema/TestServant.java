package com.kema;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class TestServant
{
    
    public static void main(String[] args) throws Exception
    {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/DocTests", new DocHandler());
        server.createContext("/RpcTests", new RpcHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    
    
    
    static class DocHandler implements HttpHandler
    {
        @Override
        public void handle(HttpExchange t) throws IOException
        {
            System.out.println("testDocumentLiteral called");
            String response = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"><SOAP-ENV:Header/><SOAP-ENV:Body>"
                    + "<testResponse>testDocumentLiteral called</testResponse>"
                    + "</SOAP-ENV:Body></SOAP-ENV:Envelope>";
            t.getResponseHeaders().set("Content-Type", "Text/Xml");
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            
            os.write(response.getBytes());
            os.close();
        }
    }
    
    static class RpcHandler implements HttpHandler
    {
        @Override
        public void handle(HttpExchange t) throws IOException
        {
            System.out.println("testRpcLiteral called");
            String response = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"><SOAP-ENV:Header/><SOAP-ENV:Body>"
                    + "<testResponse>testRpcLiteral called</testResponse>"
                    + "</SOAP-ENV:Body></SOAP-ENV:Envelope>";
            t.getResponseHeaders().set("Content-Type", "Text/Xml");
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
    

}

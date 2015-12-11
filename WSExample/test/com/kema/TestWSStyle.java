package com.kema;

import static org.junit.Assert.fail;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.junit.Test;

public class TestWSStyle
{

    @Test
    public void testRpc() throws RemoteException, ServiceException, MalformedURLException
    {
        StyleTestsService service  = new StyleTestsService();
        
        try
        {
            service.getRpcTestsPort().testRpcLiteral(1, "kema", null);
            fail("Expected client exception");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    

    @Test
    public void testDocument() throws RemoteException, ServiceException, MalformedURLException
    {
        StyleTestsService service  = new StyleTestsService();
        service.getDocumentTestsPort().testDocumentLiteral(1, "kema", null);
    }

}

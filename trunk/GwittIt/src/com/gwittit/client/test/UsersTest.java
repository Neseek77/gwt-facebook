package com.gwittit.client.test;

import junit.framework.Assert;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.junit.client.GWTTestCase;

/**
 * Test users.* methods, <code>users.hasAppPermission,users.getInfo</code> etc.
 */
public class UsersTest extends GWTTestCase {


    /**
     * Test method <code>users.hasAppPermission</code> response.
     */
    public void testHasPermission () {
        JavaScriptObject jso = getAppPermissionResponse ();
        Assert.assertTrue ( "1".equals ( jso.toString () ) );
    }
    
    
    public native JavaScriptObject getAppPermissionResponse() /*-{
        var ret = new Number ( 1 );
        return ret;
    }-*/;

    @Override
    public String getModuleName() {
        // TODO Auto-generated method stub
        return "com.gwittit.Gwittit";
    }
    
    
}

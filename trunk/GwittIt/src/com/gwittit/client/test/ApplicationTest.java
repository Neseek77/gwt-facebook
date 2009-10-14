package com.gwittit.client.test;

import junit.framework.Assert;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.junit.client.GWTTestCase;
import com.gwittit.client.facebook.entities.ApplicationPublicInfo;

/**
 * Test application methods 
 */
public class ApplicationTest extends GWTTestCase {
    
    /**
     * Test method resopnse <code>application.getPublicInfo</code>
     */
    public void testGetPublicInfo () {
        JavaScriptObject jso = getPublicInfoResponse ();
        ApplicationPublicInfo appInfo = jso.cast ();
        Assert.assertEquals (  "aebf2e22b6bcb3bbd95c180bb68b6df4", appInfo.getApiKey () );
        Assert.assertEquals (  2, appInfo.getDevelopers ().length () );
    }
    
    /**
     * Json Response String 
     */
    public final native JavaScriptObject getPublicInfoResponse() /*-{
        return {"app_id":32357027876,"api_key":"aebf2e22b6bcb3bbd95c180bb68b6df4","canvas_name":"gwittit","display_name":"GwittIt","icon_url":"http:\/\/static.ak.fbcdn.net\/rsrc.php\/z4XGZ\/hash\/7abvozy3.gif","logo_url":"http:\/\/static.ak.fbcdn.net\/rsrc.php\/z5SFU\/hash\/3gjc00v4.gif","developers":[{"uid":744450545,"name":"Ola M"},{"uid":744450547,"name":"Trollet Truls"}],"company_name":"","description":"Demoapplication for gwt-facebook, a toolkit for developing facebook applications using GWT.","daily_active_users":1,"weekly_active_users":1,"monthly_active_users":1,"category":"Just For Fun","subcategory":"Other"};
    
    }-*/;
    
    @Override
    public String getModuleName() {
        return "com.gwittit.Gwittit";
    }

}

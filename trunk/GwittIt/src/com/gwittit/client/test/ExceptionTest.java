package com.gwittit.client.test;

import junit.framework.Assert;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.junit.client.GWTTestCase;
import com.gwittit.client.facebook.entities.ErrorResponse;
import com.gwittit.client.facebook.entities.KeyValue;
import com.gwittit.client.facebook.entities.UserData;


/**
 * Test parsing of exception string
 * @author ola
 *
 */
public class ExceptionTest extends GWTTestCase {

    
    /**
     * Parse an error response from facebook.
     */
    public void testParseErrorMessage () {
        ErrorResponse errorMessage = getErrorResponse ().cast ();
        Assert.assertEquals (  "Include one of subj_id, album, or pids" , errorMessage.getMessage () );
        
        UserData userData = errorMessage.getUserData ();
        Assert.assertNotNull ( "UserData null", userData );
       
        Assert.assertEquals ( "Error Code invalid" ,  100 , userData.getErrorCode () );
        Assert.assertEquals (  9, userData.getRequestArgs ().length () );
        
        JsArray<KeyValue> requestArgs = userData.getRequestArgs ();
     
        for ( int i = 0 ; i < requestArgs.length (); i++ ) {
            KeyValue kv = requestArgs.get ( i );
            System.out.println ( kv.getKey () + " = " + kv.getValue () );
        }
    }
    
    
    /**
     * Error message as json
     */
    public final native JavaScriptObject getErrorResponse () /*-{

        return {"message":"Include one of subj_id, album, or pids", 
                "userData":
                    {"error_code":100,
                     "error_msg":"Include one of subj_id, album, or pids", 
                     "request_args":
                        [
                            {"key":"method", "value":"photos.get"},
                            {"key":"api_key", "value":"707cee0b003b01d52b2b6a707fa1202b"},
                            {"key":"format", "value":"JSON"},
                            {"key":"uid", "value":"525231096"},
                            {"key":"call_id", "value":"255"},
                            {"key":"v", "value":"1.0"},
                            {"key":"session_key", "value":"3.JrqYwPigUc5msHITuvaL7A__.3600.1255510800-744450545"},
                            {"key":"ss", "value":"1"},
                            {"key":"sig", "value":"850b0ee94da7ef9c3a7b924794ee62e6"}
                        ]
                    }
              }
    }-*/;


    @Override
    public String getModuleName() {
        return "com.gwittit.Gwittit";
    }
}

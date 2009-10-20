package com.gwittit.client.facebook.entities;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

/**
 * Userdata , obtained from an ErrorResponse.
 * 
 * @author olamar72
 */
public class UserData extends JavaScriptObject {

    protected UserData() {
    }

    /**
     * Get the error code
     * @return integer error code
     */
    public final native int getErrorCode() /*-{
        if ( this.error_code == undefined ) { 
            return 0;
        } else {
            return this.error_code;
        }
    }-*/;

    /**
     * Get the error message
     * @return string message
     */
    public final native String getErrorMsg() /*-{
        return this.error_msg;
    }-*/;

    /**
     * Get method parameters as javascriptobject
     * @return parameters
     */
    public final native JavaScriptObject getRequestArgsNative() /*-{
        return this.request_args;
    }-*/;
    
    /**
     * Get method parameters sent
     * @return array with keyvalue objects
     */
    public final JsArray<KeyValue> getRequestArgs() {
        return getRequestArgsNative ().cast ();
    }
}

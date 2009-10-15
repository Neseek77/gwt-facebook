package com.gwittit.client.facebook.entities;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * ErrorMessage from facebook
 */
public class ErrorResponse extends JavaScriptObject {

    protected ErrorResponse() {
    }

    /**
     * Error message
     */
    public final native String getMessage() /*-{
        return this.message;
    }-*/;

    /**
     * Note: userData is camelCase not underscore
     * 
     * User Data javascript object
     * @return user data
     */
    public final native JavaScriptObject getUserDataNative() /*-{
        return this.userData;
    }-*/;
    

    /**
     * User Data         
     */
    public final UserData getUserData () {
        try {
            return getUserDataNative().cast ();
        } catch ( Exception e ) {
            return null;
        }
    }

}

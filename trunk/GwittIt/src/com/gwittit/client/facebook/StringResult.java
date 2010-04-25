package com.gwittit.client.facebook;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Wraps a string result
 * @author ola
 *
 */
public class StringResult extends JavaScriptObject {

    protected StringResult() {
    }

    public final native String getResult() /*-{
        return this.result;
    }-*/;
    
}
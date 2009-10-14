package com.gwittit.client.facebook.entities;

import com.google.gwt.core.client.JavaScriptObject;


/**
 * Class that wraps a key/value javascriptobject
 */
public class KeyValue extends JavaScriptObject {

    protected KeyValue() {
    }

    public final native String getKey() /*-{
        return this.key;
    }-*/;

    public final native String getValue() /*-{
        return this.value;
    }-*/;

}

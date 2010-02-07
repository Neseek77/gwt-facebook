package com.gwittit.client.facebook.entities;

import com.google.gwt.core.client.JavaScriptObject;
import com.gwittit.client.facebook.Json;


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

    public static final KeyValue newInstance ( String key, String value ) {
        Json j = new Json().put( key, value);
        return fromJson ( j.toString () );
    }
    
    public static native KeyValue fromJson(String jsonString) /*-{ return eval('(' + jsonString + ')');}-*/;
    
}

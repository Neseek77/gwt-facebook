package com.gwittit.client.facebook.entities;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Represents location, a users current location for example.
 * 
 * @author olamar72
 */
public class Location extends JavaScriptObject {

    protected Location() {}
    
    public final native String getCity() /*-{ return this.city; }-*/;
    public final native String getState() /*-{ return this.state; }-*/;
    public final native String getCountry() /*-{ return this.country; }-*/;
    public final native String getZipString() /*-{ return this.zip + ""; }-*/;
    public final Integer getZip() { return new Integer ( getZipString() ); }
    public static native Location fromJson(String jsonString) /*-{ return eval('(' + jsonString + ')');}-*/;
    
}

package com.gwittit.client.facebook.entities;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Holds a cookie value for the application.
 * 
 * @see http://wiki.developers.facebook.com/index.php/Data.getCookies DataGetCookies
 * 
 * @author olamar72
 *
 */
public class Cookie extends JavaScriptObject {
    protected Cookie() {
    }
    
    public final native String getUidString() /*-{
        return this.uid + "";
    }-*/;

    public final Long getUid() {
        return new Long ( getUidString () );
    }

    public final native String getName() /*-{
        return this.name;
    }-*/;

    public final native String getValue() /*-{
        return this.value;
    }-*/;

    public final native String getExpires() /*-{
        return this.expires+"";
    }-*/;

    public final native String getPath() /*-{
        return this.path;
    }-*/;
    
    public static native Cookie fromJson(String jsonString) /*-{ return eval('(' + jsonString + ')');}-*/;
}

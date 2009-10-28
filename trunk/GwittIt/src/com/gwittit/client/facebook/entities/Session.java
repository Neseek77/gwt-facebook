package com.gwittit.client.facebook.entities;

import java.util.Date;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;

/**
 * @deprecated doesnt make sense in javascript.
 */
public class Session extends JavaScriptObject {

    protected Session() {}
    
    public final native String getSessionKey() /*-{ return this.session_key; }-*/;
    public final native String getUidString() /*-{ return this.uid + ""; }-*/;
    public final Long getUid() { return new Long ( getUidString() ); }
    public final native String getExpiresString() /*-{ return this.expires + ""; }-*/;
    public final Long getExpires() { return new Long ( getExpiresString() ); }
    public static native Session fromJson(String jsonString) /*-{ return eval('(' + jsonString + ')');}-*/;
    
    
}

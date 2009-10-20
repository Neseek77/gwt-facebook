package com.gwittit.client.facebook.entities;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Indicates if this stream has comments
 */
public class Comments extends JavaScriptObject {

    protected Comments() {}
  
    final native String getCountString() /*-{ return this.count + ""; }-*/;
    public final Integer getCount() { return new Integer ( getCountString() ); }
    public final native Boolean getCanRemove() /*-{ return this.can_remove; }-*/;
    public final native Boolean getCanPost() /*-{ return this.can_post; }-*/;
    public static native Comments fromJson(String jsonString) /*-{ return eval('(' + jsonString + ')');}-*/;
}
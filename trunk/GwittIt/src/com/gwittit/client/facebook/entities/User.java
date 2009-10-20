package com.gwittit.client.facebook.entities;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Facebook User, basic info.
 * 
 * @author olamar72
 * 
 */
public class User extends JavaScriptObject {

    protected User() {
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

}

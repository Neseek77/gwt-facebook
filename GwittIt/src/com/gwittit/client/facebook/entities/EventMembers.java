package com.gwittit.client.facebook.entities;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayNumber;

/**
 * See event members
 *
 */
public class EventMembers extends JavaScriptObject {

    protected EventMembers() {}
    
    public final native JsArrayNumber getAttending() /*-{ return this.attending; }-*/;
    public final native JsArrayNumber getUnsure() /*-{ return this.unsure; }-*/;
    public final native JsArrayNumber getNotReplied() /*-{ return this.not_replied; }-*/;
}

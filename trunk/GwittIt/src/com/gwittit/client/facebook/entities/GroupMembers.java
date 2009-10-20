package com.gwittit.client.facebook.entities;

import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;
import com.gwittit.client.facebook.Util;

/**
 * Members of a group
 */
public class GroupMembers extends JavaScriptObject {
    protected GroupMembers() {}
    
    public final native JsArrayString getMembersNative() /*-{ return this.members ; }-*/;
    public final List<Long> getMembers() { return Util.convertStringArray ( getMembersNative() ); }
    public final native JsArrayString  getAdminsNative() /*-{ return this.admins ; }-*/;
    public final List<Long> getAdmins() { return Util.convertStringArray ( getAdminsNative() ); }
    public final native JsArrayString  getOfficersNative() /*-{ return this.officers ; }-*/;
    public final List<Long> getOfficers() { return Util.convertStringArray ( getOfficersNative() ); }
    public final native JsArrayString  getNotRepliedNative() /*-{ return this.not_replied ; }-*/;
    public final List<Long> getNotReplied() { return Util.convertStringArray ( getNotRepliedNative() ); }
    public static native GroupMembers fromJson(String jsonString) /*-{ return eval('(' + jsonString + ')');}-*/;
    
}

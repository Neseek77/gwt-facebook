package com.gwittit.client.facebook.entities;

import com.google.gwt.core.client.JavaScriptObject;
import com.gwittit.client.facebook.Json;

public class ActionLink extends JavaScriptObject {
    
    protected ActionLink () {}
    
    public final native String getText() /*-{ return this.text; }-*/;
    public final native String getHref() /*-{ return this.href; }-*/;
    
    public static final ActionLink newInstance ( String text, String href ) {
        Json j = new Json();
        j.put ( "text", text ).put ( "href", href );
        return fromJson ( j.toString () );
    }
    public static native ActionLink fromJson(String jsonString) /*-{ return eval('(' + jsonString + ')');}-*/;

}

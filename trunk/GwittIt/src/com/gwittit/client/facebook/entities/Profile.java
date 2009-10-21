package com.gwittit.client.facebook.entities;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Facebook Profile, returned in stream_get 
 * @author ola
 *
 */
public class Profile extends JavaScriptObject {
    
    protected Profile() {}
    
    public final native String getIdString() /*-{ return this.id + ""; }-*/;
    public final Long getId() { return new Long ( getIdString() ); }
    public final native String getUrl() /*-{ return this.url; }-*/;
    public final native String getName() /*-{ return this.name; }-*/;
    public final native String getPicSquare() /*-{ return this.pic_square; }-*/;
    public static native Profile fromJson(String jsonString) /*-{ return eval('(' + jsonString + ')');}-*/;
    

}

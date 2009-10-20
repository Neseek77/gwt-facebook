package com.gwittit.client.facebook.entities;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;



/**
 * Wraps Facebook Stream object.
 * 
 * @see <a href="http://wiki.developers.facebook.com/index.php/Stream.get">Stream.get</a>
 */
public class Stream extends JavaScriptObject {
	
    protected Stream () {}
    
    public final native JsArray<Post> getPosts() /*-{ return this.posts; }-*/;
    public final native JavaScriptObject getProfiles() /*-{ return this.profiles; }-*/;
    public final native JsArray<Album> getAlbums() /*-{ return this.albums; }-*/;
    public static native Stream fromJson(String jsonString) /*-{ return eval('(' + jsonString + ')');}-*/;
}


package com.gwittit.client.facebook.entities;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Photo Album
 * @author olamar72
 */
public class Album extends JavaScriptObject {
	
    protected Album () { }
    
	public final native String getAid () /*-{ return this.aid; }-*/;

	public final native String getCoverPid () /*-{ return this.cover_pid; }-*/;
	
	public final native String getOwner () /*-{ return this.owner + ""; }-*/;
	
	public final native String getName() /*-{ return this.name; }-*/;
	
	public final native String getDescription () /*-{ return this.description; }-*/;
	
	public final native String getLocation  () /*-{ return this.location; }-*/;
	
	public final native Integer getSize () /*-{ return this.size; }-*/;
	
	public final native String getVisible () /*-{ return this.visible; }-*/;
	
	public final native String getLink () /*-{ return this.link; }-*/;

	public static native Album  newInstance ( String jsonString ) /*-{
	       return eval('(' + jsonString + ')');
	}-*/;
	
}

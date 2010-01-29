package com.gwittit.client.facebook.entities;

import com.google.gwt.core.client.JavaScriptObject;
import com.gwittit.client.facebook.Json;

/**
 * Photo Album
 * @author olamar72
 */
public class Album extends JavaScriptObject {
	
    public enum Visibility { friends, friends_of_friends, networks, everyone }
    
    protected Album () { }
    
    /**
     * @return album id
     */
	public final native String getAid () /*-{ return this.aid; }-*/;

	/**
	 * @return cover pid as string
	 */
	public final native String getCoverPid () /*-{ return this.cover_pid; }-*/;
	
	/**
	 * @return convenient function to test if the album has cover
	 */
	public final boolean hasCover () {
	    return getCoverPid() != null;
	}
	
	/**
	 * @return owner 
	 */
	public final native String getOwner () /*-{ return this.owner + ""; }-*/;
	
	/**
	 * @return name of album
	 */
	public final native String getName() /*-{ return this.name; }-*/;
	
	/**
	 * @return description
	 */
	public final native String getDescription () /*-{ return this.description; }-*/;

	/**
	 * @return location
	 */
	public final native String getLocation  () /*-{ return this.location; }-*/;
	
	/**
	 * @return size of album
	 */
	public final native Integer getSize () /*-{ return this.size; }-*/;
	
	/**
	 * @return visible string
	 */
	public final native String getVisible () /*-{ return this.visible; }-*/;
	
	
	/**
	 * @return link to album
	 */
	public final native String getLink () /*-{ return this.link; }-*/;
	
	/**
	 * name, location, description, visible    
     *
	 * @return
	 */
	public final static Album createAlbum ( String name, String location, String description, Visibility v  ) {
	    
	    Json j = new Json ();
	    
	    j.put ("name", name ).
	      put ( "location", location ).
	      put ( "description", description ).
	      put ( "visibility",  v.toString ().replace ( "_", "-" ) ) ;
	    return fromJson ( j.toString () );
	}
	
	public static native Album fromJson(String jsonString) /*-{ return eval('(' + jsonString + ')');}-*/;
}

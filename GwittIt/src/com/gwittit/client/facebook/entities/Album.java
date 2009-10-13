package com.gwittit.client.facebook.entities;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Photo Album
 * @author olamar72
 */
public class Album extends JavaScriptObject {
	
    protected Album () { }
    
    /**
     * @return album id
     */
	public final native String getAid () /*-{ return this.aid; }-*/;

	/**
	 * @return cover pid as string
	 */
	public final native String getCoverPidString () /*-{ return this.cover_pid +""; }-*/;
	
	/**
	 * @return cover pid as long
	 */
	public final Long getCoverPid() { return new Long ( getCoverPidString() ); };
	
	/**
	 * @return convenient function to test if the album has cover
	 */
	public final boolean hasCover () {
	    return getCoverPid().compareTo ( new Long(0 ) )!= 0;
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
	
}

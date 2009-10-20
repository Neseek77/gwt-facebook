package com.gwittit.client.facebook.entities;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Facebook Photo
 * 
 * @author olamar72
 */
public class Photo extends JavaScriptObject {

    protected Photo () {}
    
    /**
     * Pid
     */
    public final native String getPid() /*-{ return this.pid; }-*/;
    
    /**
     * Album id
     */
    public final native String getAid() /*-{ return this.aid; }-*/;

    /**
     * Name
     */
    public final native String getName() /*-{ return this.name; }-*/;

    /**
     * Owner string
     */
    public final native String getOwnerString() /*-{ return this.owner + ""; }-*/;
    public final Long getOwner() { return new Long ( getOwnerString() ); }

    /**
     * Source url
     */
    public final native String getSrc() /*-{ return this.src; }-*/;

    /**
     * Source big url
     */
    public final native String getSrcBig() /*-{ return this.src_big; }-*/;

    /**
     * Source small url
     */
    public final native String getSrcSmall() /*-{ return this.src_small; }-*/;

    /**
     * Link url
     */
    public final native String getLink() /*-{ return this.link; }-*/;

    /**
     * Caption
     */
    public final native String getCaption() /*-{ return this.caption; }-*/;

}

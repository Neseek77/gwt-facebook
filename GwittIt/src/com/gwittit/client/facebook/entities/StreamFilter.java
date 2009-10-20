package com.gwittit.client.facebook.entities;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Facebook Stream Filter. Use this to filter stream
 * 
 * @see <a href="http://wiki.developers.facebook.com/index.php/Stream_filter_%28FQL%29"> Stream Filter </a>
 * @author olamar72
 */
public class StreamFilter extends JavaScriptObject {

    protected StreamFilter() {
    }

    public enum Type {
        application, newsfeed, friendlist, network, publicprofile
    }

    /**
     * The ID of the user whose filters you are querying.
     */
    public final native String getUid() /*-{
        return this.uid;
    }-*/;

    /**
     * A key identifying a particular filter for a user's stream. This filter is
     * useful to retrieve relevant items from the stream table.
     */
    public final native String getFilterKey() /*-{
        return this.filter_key;
    }-*/;

    /**
     * The name of the filter as it appears on the home page.
     */
    public final native String getName() /*-{
        return this.name;
    }-*/;

    /**
     * A 32-bit int that indicates where the filter appears in the sort.
     */
    public final native int getRank() /*-{
        return this.rank;
    }-*/;

    /**
     * The URL to the filter icon. For applications, this is the same as your
     * application icon.
     */
    public final native String getIconUrl() /*-{
        return this.icon_url;
    }-*/;

    /**
     * If true, indicates that the filter is visible on the home page. If false,
     * the filter is hidden in the More link.
     */
    public final native boolean getIsVisible() /*-{
        return this.is_visible;
    }-*/;

    /**
     * The type of filter. One of application, newsfeed, friendlist, network, or
     * publicprofile.
     */
    public final native String getType() /*-{
        return this.type;
    }-*/;

    /**
     * Get type as enum
     */
    public final Type getTypeEnum() {
        return Type.valueOf ( getType () );
    }

    /*
     * A 64-bit ID for the filter type.
     */
    public final native int getValue() /*-{
        return this.value;
    }-*/;

}

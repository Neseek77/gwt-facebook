package com.gwittit.client.facebook.entities;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;

/**
 * Represents a video object in facebook
 */
public class Video extends JavaScriptObject {

    protected Video() {
    }

    public final native String getDisplayUrl() /*-{
        return this.display_url;
    }-*/;

    public final native String getSourceUrl() /*-{
        return this.source_url;
    }-*/;

    public final native String getOwner() /*-{
        return this.owner;
    }-*/;

    public final native String getPermalink() /*-{
        return this.permalink;
    }-*/;

    public final native String getSourceType() /*-{
        return this.source_type;
    }-*/;

    public static native Video fromJson(String jsonString) /*-{
        return eval('(' + jsonString + ')');
    }-*/;

    public final String stringify() {
        return new JSONObject ( this ).toString ();

    }

}

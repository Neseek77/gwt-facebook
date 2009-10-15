package com.gwittit.client.facebook.entities;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Class that describes an event from facebook.
 * 
 * @see http://wiki.developers.facebook.com/index.php/Events.get EventsGet
 */
public class Event extends JavaScriptObject {
    protected Event() {
    }

    /**
     * @return eid as String ( this is returned as String from facebook )
     */
    public final native String getEid() /*-{
        return this.eid;
    }-*/;

    public final native String getName() /*-{
        return this.name;
    }-*/;

    public final native String getTagline() /*-{
        return this.tagline;
    }-*/;

    public final native String getNidString() /*-{
        return this.nid + "";
    }-*/;

    public final Long getNid() {
        return new Long ( getNidString () );
    }

    public final native String getPic() /*-{
        return this.pic;
    }-*/;

    public final native String getPic_big() /*-{
        return this.pic_big;
    }-*/;

    public final native String getPic_small() /*-{
        return this.pic_small;
    }-*/;

    public final native String getHost() /*-{
        return this.host;
    }-*/;

    public final native String getDescription() /*-{
        return this.description;
    }-*/;

    public final native String getEventType() /*-{
        return this.event_type;
    }-*/;

    public final native String getEventSubType() /*-{
        return this.event_sub_type;
    }-*/;

    public final native String getStartTimeString() /*-{
        return this.start_time + "";
    }-*/;

    public final Long getStartTime() {
        return new Long ( getStartTimeString () );
    }

    public final native String getEndTimeString() /*-{
        return this.end_time + "";
    }-*/;

    public final Long getEndTime() {
        return new Long ( getEndTimeString () );
    }

    public final native String getCreatorString() /*-{
        return this.creator + "";
    }-*/;

    public final Long getCreator() {
        return new Long ( getCreatorString () );
    }

    public final native String getUpdateTimeString() /*-{
        return this.update_time + "";
    }-*/;

    public final Long getUpdateTime() {
        return new Long ( getUpdateTimeString () );
    }

    public final native String getLocation() /*-{
        return this.location;
    }-*/;

    public final native JavaScriptObject getVeneu() /*-{
        return this.veneu;
    }-*/;

    public static native Event fromJson(String jsonString) /*-{
        return eval('(' + jsonString + ')');
    }-*/;

}

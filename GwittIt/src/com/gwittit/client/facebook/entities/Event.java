package com.gwittit.client.facebook.entities;

import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Window;
import com.gwittit.client.facebook.Json;
import com.gwittit.client.facebook.FacebookApi.RsvpStatus;

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
    public final native String getEidString() /*-{
        return this.eid + "";
    }-*/;

    public final Long getEid() {
        return new Long ( getEidString() );
    }

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

    /**
     * Create filter to be used in event query. Null values will be ignored
     * @param uid
     *            int Filter by events associated with a user with this uid.
     * @param eids
     *            array Filter by this list of event IDs. This is a
     *            comma-separated list of event IDs.
     * @param start_time
     *            int Filter with this UTC as lower bound. A missing or zero
     *            parameter indicates no lower bound.
     * @param end_time
     *            int Filter with this UTC as upper bound. A missing or zero
     *            parameter indicates no upper bound.
     * @param rsvp_status
     *            string Filter by this RSVP status. The RSVP status should be
     *            one of the following strings:
     * @return events that can be used as filter
     */
    public final static Event createFilter ( Long uid, List<Long> eids, Long startTime, Long endTime , RsvpStatus status  ) {
    
        Json j = Json.newInstance ();
        j.put ( "uid", uid ).put ( "eids", eids ).put ( "start_time", startTime ).put ( "end_time", endTime );
        j.put ( "rsvp_status", status != null ? status.toString () : null );
        return fromJson ( j.toString () );
    }
     
    /**
     * Create a empty filter
     * @return
     */
    public final static Event createFilterEmpty () {
        Json j = Json.newInstance ();
        return fromJson ( j.toString () );
    }
    
    public static native Event fromJson(String jsonString) /*-{
        return eval('(' + jsonString + ')');
    }-*/;

}

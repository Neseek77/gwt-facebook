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
public class EventInfo extends JavaScriptObject {
    
    static enum PrivacyType {OPEN,CLOSED,SECRET }

    /** Valid Categories */
    public static enum Category { Party(1), Causes(2), Education(3), Meetings(4), Music_Arts(5), Sports(6), Trips(7), Other(8);
        private Integer id;
        Category(Integer i) {
            this.id = i;
        }
        public Integer getId() {
            return id;
        }
    }
    
    /** Valid Subcategories */
    public static enum SubCategory { Birthday_Party(1), Cocktail_Party(2),Club_Party (3),Potluck (4),Fraternity_Sorority_Party (5),Business_Meeting (6),Barbecue (7),Card_Night (8),Dinner_Party (9),Holiday_Party (10),Night_of_Mayhem (11),Movie_TV_Night (12),Drinking_Games (13),Bar_Night (14),LAN_Party (15),Brunch (16),Mixer (17),Slumber_Party (18),Erotic_Party (19),Benefit (20),Goodbye_Party (21),House_Party (22),Reunion (23),Fundraiser (24),Protest (25),Rally (26),Class (27),Lecture (28),Office_Hours (29),Workshop (30),Club_Or_Group_Meeting (31),Convention (32),Dorm_Or_House_Meeting (33),Informational_Meeting (34),Audition (35),Exhibit (36),Jam_Session (37),Listening_Party (38),Opening (39),Performance (40),Preview (41),Recital (42),Rehearsal (43),Pep_Rally (44),Pick_Up (45),Sporting_Event (46),Sports_Practice (47),Tournament (48),Camping_Trip (49),Daytrip (50),Group_Trip (51),Roadtrip (52),Carnival (53),Ceremony (54),Festival (55),Flea_Market (56),Retail (57),Wedding (58);
        private Integer id ;
        SubCategory ( Integer id ) {
            this.id = id;
        }
        public Integer getId () {
            return id;
        }
    }

    protected EventInfo() {
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
    public final static EventInfo createEventInfo ( Long uid, List<Long> eids, Long startTime, Long endTime , RsvpStatus status  ) {
        Json j = new Json ();
        j.put ( "uid", uid ).put ( "eids", eids ).put ( "start_time", startTime ).put ( "end_time", endTime );
        j.put ( "rsvp_status", status != null ? status.toString () : null );
        return fromJson ( j.toString () );
    }
     
    /**
     * Create a empty filter
     * @return
     */
    public final static EventInfo createFilterEmpty () {
        Json j = new Json ();
        
        return fromJson ( j.toString () );
    }
    
    public static native EventInfo fromJson(String jsonString) /*-{
        return eval('(' + jsonString + ')');
    }-*/;

}

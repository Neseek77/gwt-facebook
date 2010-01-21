package com.gwittit.client.facebook.entities;

import com.google.gwt.core.client.JavaScriptObject;
import com.gwittit.client.facebook.Json;

/**
 * Facebook Note
 * 
 * @author olamar72
 */
public class Note extends JavaScriptObject {

    protected Note() {
    }

    public final native String getNoteIdString() /*-{
        return this.note_id + "";
    }-*/;

    public final Long getNoteId() {
        return new Long ( getNoteIdString () );
    }

    public final native String getTitle() /*-{
        return this.title;
    }-*/;

    public final native String getContent() /*-{
        return this.content;
    }-*/;

    public final native String getCreatedTimeString() /*-{
        return this.created_time + "";
    }-*/;

    public final Long getCreatedTime() {
        return new Long ( getCreatedTimeString () );
    }

    public final native String getUpdatedTimeString() /*-{
        return this.updated_time + "";
    }-*/;

    public final Long getUpdatedTime() {
        return new Long ( getUpdatedTimeString () );
    }

    /**
     * Create a new instance of Note
     * 
     * @param title
     *            of note
     * @param content
     *            of note
     * @return note
     */
    public final static Note createNote(String title, String content) {
        Json j = new Json ();
        j.put ( "title", title ).put ( "content", content );
        return fromJson ( j.toString () );
    }

    public static native Note fromJson(String jsonString) /*-{
        return eval('(' + jsonString + ')');
    }-*/;

}

package com.gwittit.client.facebook.entities;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Facebook Message.
 * 
 * @author olamar72
 * 
 */
public class Message extends JavaScriptObject {

    protected Message() {
    }

    public final native String getMessageId() /*-{
        return this.message_id;
    }-*/;

    public final native String getAuthorIdString() /*-{
        return this.author_id + "";
    }-*/;

    public final Long getAuthorId() {
        return new Long ( getAuthorIdString () );
    }

    public final native String getBody() /*-{
        return this.body;
    }-*/;

    public final native String getCreatedTimeString() /*-{
        return this.created_time + "";
    }-*/;

    public final Long getCreatedTime() {
        return new Long ( getCreatedTimeString () );
    }

    public final native JavaScriptObject getAttachment() /*-{
        return this.attachment;
    }-*/;

    public final native String getThreadId() /*-{
        return this.thread_id;
    }-*/;

    public static native Message fromJson(String jsonString) /*-{
        return eval('(' + jsonString + ')');
    }-*/;

}

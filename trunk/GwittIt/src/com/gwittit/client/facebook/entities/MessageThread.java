package com.gwittit.client.facebook.entities;

import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayNumber;
import com.gwittit.client.facebook.Util;

/**
 * Facebook Message Thread, this holds messages in a specific thread.
 * 
 * @author olamar72
 */
public class MessageThread extends JavaScriptObject {

    protected MessageThread() {
    }

    public final native String getThreadId() /*-{
        return this.thread_id;
    }-*/;

    public final native String getSubject() /*-{
        return this.subject;
    }-*/;

    public final native JsArrayNumber getRecipientsNative() /*-{
        return this.recipients + "";
    }-*/;

    public final List<Long> getRecipients() {
        return Util.convertNumberArray ( getRecipientsNative () );
    }

    public final native String getUpdatedTimeString() /*-{
        return this.updated_time + "";
    }-*/;

    public final Long getUpdatedTime() {
        return new Long ( getUpdatedTimeString () );
    }

    public final native String getParentMessageIdString() /*-{
        return this.parent_message_id + "";
    }-*/;

    public final Long getParentMessageId() {
        return new Long ( getParentMessageIdString () );
    }

    public final native String getMessageCountString() /*-{
        return this.message_count +"";
    }-*/;

    public final Integer getMessageCount() {
        if (getMessageCount () == null) {
            return 0;
        } else {
            return new Integer ( getMessageCount () );
        }
    }

    public final native String getSnippet() /*-{
        return this.snippet;
    }-*/;

    public final native String getSnippetAuthorString() /*-{
        return this.snippet_author + "";
    }-*/;

    public final Long getSnippetAuthor() {
        return new Long ( getSnippetAuthorString () );
    }

    public final native String getObjectIdString() /*-{
        return this.object_id + "";
    }-*/;

    public final Long getObjectId() {
        return new Long ( getObjectIdString () );
    }

    public final native String getUnreadString() /*-{
        return this.unread +"";
    }-*/;

    public final Integer getUnread () {
        if ( getUnreadString() == null ) {
            return 0;
        } else {
            return new Integer ( getUnreadString() );
        }
    }
    
    public final native JsArray<Message> getMessages() /*-{
        return this.messages;
    }-*/;

    public static native MessageThread fromJson(String jsonString) /*-{
        return eval('(' + jsonString + ')');
    }-*/;

}

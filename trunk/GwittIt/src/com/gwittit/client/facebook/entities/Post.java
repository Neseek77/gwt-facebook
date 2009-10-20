package com.gwittit.client.facebook.entities;

import com.google.gwt.core.client.JavaScriptObject;


/**
 * Facebook tream Post (A post that appears in the newsfeed)
 * 
 * @author olamar72
 */
public class Post extends JavaScriptObject  {
    
    
    protected Post() {}
    
    /**
     * The ID of the post from the user's stream. This field, when used as an
     * index, is primarily used to re-retrieve posts. Otherwise, it is used to
     * specify a post when using any of the stream setters.
     */
    public final native String getPostId() /*-{ return this.post_id; }-*/;

    /**
     * The ID of the user whose stream you are querying. The viewer_id defaults
     * to the active session key.
     */
    public final native String getViewerIdString() /*-{ return this.viewer_id + ""; }-*/;
    public final Long getViewerId() { return new Long ( getViewerIdString() ); }

    /**
     * The application ID for the application through which the post was
     * published. This includes application IDs for FB apps (like Photos and
     * Videos).
     */
    public final native String getAppIdString() /*-{ return this.app_id + ""; }-*/;
    public final Long getAppId() { return new Long ( getAppIdString() ); }
    
    /**
     * The ID of the user or Page whose posts you want to retrieve. This
     * includes both posts that the user or Page has authored (that is, the
     * actor_id is the source_id) and posts that have been directed at this user
     * or Page (that is, the target_id is the source_id).
     */
    public final native String getSourceIdString() /*-{ return this.source_id + ""; }-*/;
    public final Long getSourceId() { return new Long ( getSourceIdString() ); }
    
    /**
     * The time the post was last updated, which includes users adding comments
     * or liking the post.
     */
    public final native String getUpdatedTimeString() /*-{ return this.updated_time + ""; }-*/;

    /**
     * The time the post was published to the user's stream.
     */
    public final native String getCreatedTimeString() /*-{ return this.created_time + ""; }-*/;

    /**
     * The filter key to fetch data with. This key should be retrieved from
     * stream.getFilters or querying the stream_filter FQL table.
     */
    public final native String getFilterKey() /*-{ return this.filter_key; }-*/;

    /**
     * For posts published by applications, this is the string that states
     * through which application the post was published. For example,
     * "Joe loves the Social Web (by MicroBloggerApp)."
     */
    public final native String getAttribution() /*-{ return this.attribution; }-*/;

    /**
     * The user ID of the person who is the user taking the action in the post.
     */
    public final native String getActorIdString() /*-{ return this.actor_id + ""; }-*/;
    public final Long getActorId() { return new Long ( getActorIdString() ); }
       
    /**
     * The user or Page to whom the post was directed. 
     */
    public final native String getTargetId() /*-{ return this.target_id; }-*/;

    /**
     * The message written by the user.
     */
    public final native String getMessage() /*-{ return this.message; }-*/;

    
    /**
     * An array of information about the attachment to the post. This is the attachment that Facebook returns. 
     */
    public final native Attachment getAttachment() /*-{ return this.attachment; }-*/;
    
    /**
     * An array of likes associated with the post. The array contains the following fields: 
     */
    public final native Likes getLikes() /*-{ return this.likes; }-*/;
    
    
    /**
     * Indicates if this stream item has comments   
     */
    public final native Comments getComments() /*-{ return this.comments; }-*/;
    

    public static native Stream fromJson(String jsonString) /*-{ return eval('(' + jsonString + ')');}-*/;  

}

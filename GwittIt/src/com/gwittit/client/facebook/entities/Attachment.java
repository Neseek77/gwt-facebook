package com.gwittit.client.facebook.entities;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;


/**
 * Wrapp attachment
 * 
 * @see http://wiki.developers.facebook.com/index.php/Attachment_%28Streams%29
 * @author ola
 * 
 * TODO: Let this class extend JavaScriptObject
 */
public class Attachment extends JavaScriptObject { 

    
    protected Attachment () {
    }

    
	/**
	 * The title of the post. The post should fit on one line in a user's
	 * stream; make sure you account for the width of any thumbnail.
	 */
    public final native String getName() /*-{ return this.name; }-*/;

	/**
	 * The URL to the source of the post referenced in the name. The URL should
	 * not be longer than 1024 characters.
	 */
    public final native String getHref() /*-{ return this.href; }-*/;

	/**
	 * A subtitle for the post that should describe why the user posted the item
	 * or the action the user took. This field can contain plain text only, as
	 * well as the {*actor*} token, which gets replaced by a link to the profile
	 * of the session user. The caption should fit on one line in a user's
	 * stream; make sure you account for the width of any thumbnail.
	 */
    public final native String getCaption() /*-{ return this.caption; }-*/;

	/**
	 * # Descriptive text about the story. This field can contain plain text
	 * only and should be no longer than is necessary for a reader to understand
	 * the story. # properties: An array of key/value pairs that provide more
	 * information about the post. The properties array can contain plain text
	 * and links only. To include a link, the value of the property should be a
	 * dictionary with 'text' and 'href' attributes.
	 */
    public final native String getDescription() /*-{ return this.description; }-*/;

	/**
	 * Rich media that provides visual content for the post. media is an array that contains one of the following types: image, flash, mp3, or video, which are described below. Make sure you specify only one of these types in your post. 
	 */
    public final native JsArray<Media> getMedia() /*-{ return this.media; }-*/;

	/**
	 * An application-specific xid associated with the stream post. The xid
	 * allows you to get comments made to this post using the Comments API. It
	 * also allows you to associate comments made to this post with a Comments
	 * Box for FBML fb:comments.
	 */
    public final native String getCommentsXid() /*-{ return this.comments_xid; }-*/;
	
	
	/**
	 * Attachment icon, undocumentend from facebook
	 */
    public final native String getIcon() /*-{ return this.icon; }-*/;
	
	public static native Attachment fromJson(String jsonString) /*-{ return eval('(' + jsonString + ')');}-*/;
	
}

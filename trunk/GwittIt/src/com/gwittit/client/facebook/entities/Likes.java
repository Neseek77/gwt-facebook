package com.gwittit.client.facebook.entities;

import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayNumber;
import com.gwittit.client.facebook.Util;

/**
 * @see http://wiki.developers.facebook.com/index.php/Stream_%28FQL%29
 * @author olamar72
 */
public class Likes extends JavaScriptObject {

    protected Likes() {
    }

    /**
     * The URL to a page showing the other users who've liked this post.
     */
    public final native String getHref() /*-{
        return this.href;
    }-*/;

    /**
     * The total number of times users like the post.
     */
    public final native String getCountString() /*-{
        return this.count + "";
    }-*/;

    public final Integer getCount() {
        return new Integer ( getCountString () );
    }//	

    /**
     * A sample of users who like the post.
     */
    public final native JsArrayNumber getSampleArray() /*-{
        return this.sample;
    }-*/;

    public final List<Long> getSample() {
        return Util.convertNumberArray ( getSampleArray () );
    }

    /**
     * A list of the viewing user's friends who like the post.
     */
    public final native JsArrayNumber getFriendsArray() /*-{
        return this.friends;
    }-*/;

    public final List<Long> getFriends() {
        return Util.convertNumberArray ( getFriendsArray () );
    }

    /**
     * Indicates whether the viewing user likes the post.
     */
    public final native boolean getUserLikes() /*-{
        return this.user_likes;
    }-*/;

    /**
     * Indicates whether the post can be liked.
     */
    public final native boolean getCanLike() /*-{
        return this.can_like;
    }-*/;

    public static native Likes fromJson(String jsonString) /*-{
        return eval('(' + jsonString + ')');
    }-*/;

    public final String stringify() {
        return "<b>count: </b>" + getCount ();
    }
}

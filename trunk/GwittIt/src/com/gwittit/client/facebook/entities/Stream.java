package com.gwittit.client.facebook.entities;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

/**
 * Facebook Stream object.
 * 
 * @see <a
 *      href="http://wiki.developers.facebook.com/index.php/Stream.get">Stream.get</a>
 */
public class Stream extends JavaScriptObject {

    protected Stream() {
    }

    /**
     * Get posts in stream
     * @return list of posts
     */
    public final native JsArray<Post> getPosts() /*-{
        return this.posts;
    }-*/;

    /**
     * Get profiles in stream
     * @return profiles in stream
     */
    public final native JsArray<Profile> getProfiles() /*-{
        return this.profiles;
    }-*/;

    /**
     * Get album updates in stream
     * @return list of albums
     */
    public final native JsArray<Album> getAlbums() /*-{
        return this.albums;
    }-*/;

}

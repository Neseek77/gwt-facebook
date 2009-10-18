package com.gwittit.client.facebook;

/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.  
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayNumber;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwittit.client.facebook.entities.Album;
import com.gwittit.client.facebook.entities.Comment;
import com.gwittit.client.facebook.entities.Cookie;
import com.gwittit.client.facebook.entities.ErrorResponse;
import com.gwittit.client.facebook.entities.Event;
import com.gwittit.client.facebook.entities.EventInfo;
import com.gwittit.client.facebook.entities.EventMembers;
import com.gwittit.client.facebook.entities.FriendInfo;
import com.gwittit.client.facebook.entities.FriendList;
import com.gwittit.client.facebook.entities.Group;
import com.gwittit.client.facebook.entities.GroupMembers;
import com.gwittit.client.facebook.entities.Note;
import com.gwittit.client.facebook.entities.Notification;
import com.gwittit.client.facebook.entities.NotificationRequest;
import com.gwittit.client.facebook.entities.Photo;
import com.gwittit.client.facebook.entities.Stream;
import com.gwittit.client.facebook.entities.StreamFilter;
import com.gwittit.client.facebook.entities.User;

/**
 * This is the main object for using the Facebook REST API in GWT.
 * 
 * @see <a
 *      href="http://wiki.developers.facebook.com/index.php/JS_API_T_FB.ApiClient">FB.ApiClient</a>
 * @see <a href="http://wiki.developers.facebook.com/index.php/API">Rest API</a>
 * 
 * @author olamar72
 */
public class FacebookApi {

    /**
     * Api Key used for calling methods.
     */
    private String apiKey;

    // ---------------- Public Methods ---------------------
    /**
     * Creates a new api
     */
    protected FacebookApi(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Get the cached session key from cookie.
     */
    public String getSessionKey() {
        final String C_SESSION_KEY = apiKey + "_session_key";
        return Cookies.getCookie ( C_SESSION_KEY );

    }

    public String getLoggedInUser() {
        return Cookies.getCookie ( apiKey + "_user" );
    }

    /**
     * This method adds a comment to an xid on behalf of a user. This
     * essentially works like stream.addComment and allows addition of comments
     * to an application's fb:comment and Comments Boxes.
     * <p/>
     * Desktop applications must pass a valid session key, and only the user
     * associated with that session key can add comments.
     * <p/>
     * In order for your application to publish a feed story associated with a
     * comment, that user must grant your application the publish_stream
     * extended permission.
     * 
     * @param xid
     *            string The xid of a particular Comments Box or fb:comments.
     * @param text
     *            string The comment/text to be added, as inputted by a user.
     *            optional
     * @param uid
     *            int The user ID to add a comment on behalf of. This defaults
     *            to the session user and must only be the session user if using
     *            a session secret (example: Desktop and JSCL apps).
     * @param title
     *            string The title associated with the item the user is
     *            commenting on. This is required if publishing a feed story as
     *            it provides the text of the permalink to give context to the
     *            user's comment.
     * @param url
     *            string The url associated with the item the user is commenting
     *            on. This is required if publishing a feed story as it is the
     *            permalink associated with the comment.
     * @param publish_to_stream
     *            bool Whether a feed story should be published about this
     *            comment. This defaults to false and can only be 'true' if the
     *            user has granted the publish_stream extended permission.
     * 
     * @see <a
     *      href="http://wiki.developers.facebook.com/index.php/Comments.add">Comments.add</a>
     * 
     */
    public void comments_add(Comment comment, AsyncCallback<JavaScriptObject> callback) {
        callMethod ( "comments.add", comment, callback );
    }

    /**
     * Returns all comments for a given XID posted through fb:comments or the
     * Comments Box (which is created with the fb:comments (XFBML) tag). This
     * method is a wrapper for the FQL query on the comment FQL table.
     * <p/>
     * You can specify only one XID with this call. If you want to retrieve
     * comments for multiple XIDs, run fql.query against the comment FQL table.
     * <p/>
     * Note: Currently there is a bug in the facebook api, causing
     * <code>comments.get</code>; to result with unknown method error.
     * 
     * @param xid
     *            int The comment xid that you want to retrieve. For a Comments
     *            Box, you can determine the xid on the admin panel or in the
     *            application settings editor in the Facebook Developer
     *            application.
     * @param callback
     *            result
     * 
     * @see <a
     *      href="http://wiki.developers.facebook.com/index.php/Comments.get">Facebook
     *      Comments.get</a>
     */
    public void comments_get(String xid, final AsyncCallback<List<Comment>> callback) {
        // Facebook Bug
        // JavaScriptObject p = getAllParams ( CommentsGetParams.values (),
        // params );
        // callMethodRetList ( "comments.get", p, Comment.class, callback );

        // JSONObject p = getDefaultParams ();
        String fql = "select xid, text,fromid,time,id,username,reply_xid from comment where xid ='" + xid + "'";

        // Call Facebook Method
        fql_query ( fql,

        new Callback<JavaScriptObject> ( callback ) {
            public void onSuccess(JavaScriptObject result) {
                callback.onSuccess ( cast ( Comment.class, result ) );
            }
        } );
    }

    /**
     * This method removes a comment from an xid on behalf of a user (or not).
     * <p/>
     * Desktop applications must pass a valid session key, and only comments
     * made by the user can be removed by that user. When using the app secret,
     * an application may remove any of its comments. required
     * <p/>
     * 
     * @param xid
     *            string The xid of a particular Comments Box or fb:comments.
     * @param commentId
     *            string The comment_id, as returned by Comments.add or
     *            Comments.get, to be removed.
     * 
     * @see <a
     *      href="http://wiki.developers.facebook.com/index.php/Comments.remove">Comments.remove</a>
     */
    public void comments_remove(String xid, String commentId, AsyncCallback<JavaScriptObject> callback) {
        Json j = Json.newInstance ();
        j.put ( "xid", xid ).put ( "comment_id", commentId );

        callMethod ( "comments.remove", j.getJavaScriptObject (), callback );
    }

    /**
     * This method returns the number of friends of the current user who have
     * accounts on your site, but have not yet connected their accounts. Also
     * see fb:unconnected-friends-count. Note that this number is determined
     * using the information passed via connect.registerUsers. If you have not
     * previously called that function, this method will always return 0.
     * <p/>
     * You can use the response from this call to determine whether or not to
     * display a link allowing the user to invite their friends to connect as
     * well.
     * <p/>
     * 
     * @see <a
     *      href="http://wiki.developers.facebook.com/index.php/JS_API_M_FB.ApiClient.Connect_getUnconnectedFriendsCount">Connect_getUnconnectedFriendsCount</a>
     */
    public void connect_getUnconnectedFriendsCount(AsyncCallback<Integer> callback) {
        JavaScriptObject p = getDefaultParams ().getJavaScriptObject ();
        callMethodRetInteger ( "connect.getUnconnectedFriendsCount", p, callback );
    }

    /**
     * Maybe implement
     */
    public void connect_registerUsers(Map<String, String> params, AsyncCallback<JavaScriptObject> callback) {

    }

    /**
     * Maybe implement, if callable from javascript
     */
    public void connect_unregisterUsers(Map<String, String> params, AsyncCallback<JavaScriptObject> callback) {

    }

    /**
     * This method returns all cookies for a given user and application.
     * <p/>
     * Cookies only apply to Web applications; they do not apply to desktop
     * applications.
     * <p/>
     * 
     * @param uid
     *            int The user from whom to get the cookies. optional
     * @param name
     *            string The name of the cookie. If not specified, all the
     *            cookies for the given user get returned.
     * @param callback
     *            result
     * 
     * @see <a
     *      href="http://wiki.developers.facebook.com/index.php/Data.getCookies">Data.getCookies</a>
     */
    public void data_getCookies(String name, AsyncCallback<List<Cookie>> callback) {
        Json j = Json.newInstance ().put ( "name", name );
        callMethodRetList ( "data.getCookies", j.getJavaScriptObject (), Cookie.class, callback );
    }

    /**
     * Beta in Facebook.
     * 
     * This method sets a cookie for a given user and application.
     * 
     * You can set cookies for Web applications only; you cannot set cookies for
     * desktop applications.
     * 
     * required
     * 
     * @param uid
     *            int The user for whom this cookie needs to be set.
     * @param name
     *            string Name of the cookie.
     * @param value
     *            string Value of the cookie.
     * @param expires
     *            int Time stamp when the cookie should expire. If not
     *            specified, the cookie expires after 24 hours. (The time stamp
     *            can be longer than 24 hours and currently has no limit)
     * @param path
     *            string Path relative to the application's callback URL, with
     *            which the cookie should be associated. (Default value is /.)
     * 
     * @param callback
     *            result
     * 
     * @see <a
     *      href="http://wiki.developers.facebook.com/index.php/Data.setCookie">Data.setCookie</a>
     */
    public void data_setCookie(Cookie c, AsyncCallback<Boolean> callback) {
        callMethodRetBoolean ( "data.setCookie", c, callback );
    }

    public void events_cancel(Map<String, String> params, AsyncCallback<JavaScriptObject> callback) {
        // TODO Auto-generated method stub

    }

    /**
     * Creates an event on behalf of the user if the application has an active
     * session key for that user; otherwise it creates an event on behalf of the
     * application. Applications can create events for a user if the user grants
     * the application the create_event extended permission.
     * <p/>
     * If you are creating an event on behalf of a user, then your application
     * is an admin for the event, while the user is the creator.
     * <p/>
     * You can upload an image and associate it with the event by forming the
     * request as a MIME multi-part message. See photos.upload for details on
     * the message format to use and the supported image types. You can replace
     * or delete images in an event using events.edit.
     * <p/>
     * This method does not require a session key. However if you call this
     * method without an active user session, then your application is both the
     * creator and admin for the event.
     * 
     * @param eventInfo
     *            information about the event
     * @param callback
     *            response to user
     * 
     * @see <a
     *      href="http://wiki.developers.facebook.com/index.php/Events.create">
     *      events.create </a>
     * 
     */
    public void events_create(EventInfo eventInfo, AsyncCallback<JavaScriptObject> callback) {
        Json j = Json.newInstance ().put ( "event_info", eventInfo.createJsonString () );
        callMethod ( "events.create", j.getJavaScriptObject (), callback );
    }

    public void events_edit(Map<String, String> params, AsyncCallback<JavaScriptObject> callback) {
        // TODO Auto-generated method stub

    }

    /**
     * 
     * Returns all visible events according to the filters specified. You can
     * use this method to find all events for a user, or to query a specific set
     * of events by a list of event IDs (eids).
     * <p/>
     * If both the uid and eids parameters are provided, the method returns all
     * events in the set of eids that are associated with the user. If no eids
     * parameter are specified, the method returns all events associated with
     * the specified user.
     * <p/>
     * If the uid parameter is omitted, the method returns all events associated
     * with the provided eids, regardless of any user relationship.
     * <p/>
     * The uid can be replaced by gid in order to get events hosted by a group
     * instead of by an individual user.
     * <p/>
     * If both parameters are omitted, the method returns all events associated
     * with the session user.
     * <p/>
     * The start_time and end_time parameters specify a (possibly open-ended)
     * window in which all events returned overlap. Note that if start_time is
     * greater than or equal to end_time, an empty top-level element is
     * returned.
     * <p/>
     * This method no longer requires a session key. However if you call this
     * method without an active user session, you can only get the events for
     * which your application was the creator; you can see only those event
     * attendees who authorized your application. Applications can create events
     * for users if the users grant the application the create_event extended
     * permission.
     * 
     * </pre>
     * 
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
     * 
     *            attending unsure declined not_replied
     * 
     * @param params
     *            map
     * 
     * @param callback
     *            result
     * 
     * @see <a
     *      href="http://wiki.developers.facebook.com/index.php/JS_API_M_FB.ApiClient.Events_get">ApiClient.Events_Get</a>
     */
    public void events_get(Event eventFilter, AsyncCallback<List<Event>> callback) {
        callMethodRetList ( "events.get", eventFilter, Event.class, callback );
    }

    /**
     * Returns membership list data associated with an event.
     * <p/>
     * This method no longer requires a session key. However if you call this
     * method without an active user session, you can only get the events for
     * which your application was the creator; you can see only those event
     * attendees who have authorized your application. Applications can create
     * events for users if the users grant the application the create_event
     * extended permission.
     * <p/>
     * 
     * @param eid
     *            id of event
     * @param params
     *            map
     * @param callback
     *            result
     * 
     * @see <a
     *      href="http://wiki.developers.facebook.com/index.php/JS_API_M_FB.ApiClient.Events_getMembers">Events_getMembers</a>
     */
    public void events_getMembers(Long eid, AsyncCallback<EventMembers> callback) {
        Json j = Json.newInstance ().put ( "eid", eid );
        callMethodRetObject ( "events.getMembers", j.getJavaScriptObject (), EventMembers.class, callback );
    }

    /**
     * Valid values for param rsvp_status <code>events.rsvp</code>
     */
    public static enum RsvpStatus {
        attending, unsure, declined
    }

    /**
     * Sets a user's RSVP status for an event. An application can set a user's
     * RSVP status only if the following are all true:
     * <ul>
     * <li>The application is an admin for the event.
     * <li>The application has an active session for the user.
     * <li>The active user has granted the application the rsvp_event extended
     * permission.
     * </ul>
     * 
     * @see <a
     *      href="http://wiki.developers.facebook.com/index.php/Events.rsvp">Events.rsvp</a>
     */
    public void events_rsvp(Long eventId, RsvpStatus status, AsyncCallback<Boolean> callback) {
        Json j = Json.newInstance ();
        j.put ( "eid", eventId ).put ( "rsvp_status", status.toString () );
        callMethodRetBoolean ( "events.rsvp", j.getJavaScriptObject (), callback );
    }

    /**
     * TODO: Implement
     */
    public void fbml_deleteCustomTags(Map<String, String> params, AsyncCallback<JavaScriptObject> callback) {
        // TODO Auto-generated method stub

    }

    /**
     * TODO: Implement
     */
    public void fbml_getCustomTags(Map<String, String> params, AsyncCallback<JavaScriptObject> callback) {
        // TODO Auto-generated method stub

    }

    /**
     * TODO: Implement
     */
    public void fbml_refreshImgSrc(Map<String, String> params, AsyncCallback<JavaScriptObject> callback) {
        // TODO Auto-generated method stub

    }

    /**
     * TODO: Implement
     */
    public void fbml_refreshRefUrl(Map<String, String> params, AsyncCallback<JavaScriptObject> callback) {
        // TODO Auto-generated method stub

    }

    /**
     * TODO: Implement
     */
    public void fbml_registerCustomTags(Map<String, String> params, AsyncCallback<JavaScriptObject> callback) {
        // TODO Auto-generated method stub

    }

    /**
     * TODO: Implement
     */
    public void fbml_setRefHandle(Map<String, String> params, AsyncCallback<JavaScriptObject> callback) {
        // TODO Auto-generated method stub

    }

    /**
     * Valid params for method <code>feed.publishUserAction</code>
     */

    /**
     * Publishes a story on behalf of the user owning the session, using the
     * specified template bundle. This method can publish one line stories to
     * the user's Wall only (or a short story to the user's Wall if the user
     * explicitly selects the short story size in the Feed dialog, then checks
     * the Always do this check box before publishing).
     * <p/>
     * For a description of the parameters (other than sequencer), see
     * feed.publishUserAction, as they are the same. For information on forming
     * the template_data object, see Template Data.
     * 
     * @param params
     *            map
     * 
     * @see <a
     *      href="http://wiki.developers.facebook.com/index.php/JS_API_M_FB.ApiClient.Feed_publishUserAction">
     *      Feed_publishUserAction</a>
     * 
     */
    public void feed_publishUserAction( /* UserAction object */AsyncCallback<JavaScriptObject> callback) {
        Window.alert ( "Not implemented" );
    }

    /**
     * Returns whether or not two specified users are friends with each other.
     * The first array specifies one half of each pair, the second array the
     * other half; therefore, they must be of equal size.
     * 
     * @param uids1
     *            array A list of user IDs matched with uids2. This is a
     *            comma-separated list of user IDs.
     * @param uids2
     *            array A list of user IDs matched with uids1. This is a
     *            comma-separated list of user IDs.
     * 
     * @see <a
     *      hreF="http://wiki.developers.facebook.com/index.php/JS_API_M_FB.ApiClient.Friends_areFriends">Friends_areFriends</a>
     */
    public void friends_areFriends(List<Long> uids1, List<Long> uids2, AsyncCallback<List<FriendInfo>> callback) {

        if (uids1.size () != uids2.size ()) {
            throw new IllegalArgumentException ( "uids1 and uids2 size must be equal" );
        }
        Json j = Json.newInstance ();
        j.put ( "uids1", uids1 ).put ( "uids2", uids2 );

        callMethodRetList ( "friends.areFriends", j.getJavaScriptObject (), FriendInfo.class, callback );
    }

    /**
     * See #friends_get(Map, AsyncCallback)
     */
    public void friends_get(final AsyncCallback<List<Long>> callback) {
        friends_get ( null, callback );
    }

    /**
     * Returns the Facebook user IDs of the current user's Facebook friends. The
     * current user is determined from the session_key parameter. The values
     * returned from this call are not storable.
     * <p/>
     * You can call this method without a session key to return a list of
     * friends of a user on your application's canvas page. The user must have
     * authorized your application in order to make this call without a session
     * key. This is similar to how Facebook passes the UIDs of friends of a user
     * on your application's canvas page.
     * 
     * @param flid
     *            int Returns the friends in a friend list.
     * 
     * @see <a
     *      href="http://wiki.developers.facebook.com/index.php/JS_API_M_FB.ApiClient.Friends_get">Friends_get</a>
     */
    public void friends_get(Integer flid, final AsyncCallback<List<Long>> callback) {
        Json j = Json.newInstance ().put ( "flid", flid );
        friends_getGeneric ( "friends.get", j.getJavaScriptObject (), callback );
    }

    /**
     * A slightly different version of friends.get returning name and uid. See
     * #friends_get(AsyncCallback)
     * 
     * @param callback
     *            list of users.
     */
    public void friends_getExtended(final AsyncCallback<List<User>> callback) {
        JSONObject p = getDefaultParams ();

        String fql = "SELECT uid, name FROM user WHERE uid IN (SELECT uid2 FROM friend WHERE uid1=" + getLoggedInUser () + ") ";
        p.put ( "query", new JSONString ( fql ) );
        callMethodRetList ( "fql.query", p.getJavaScriptObject (), User.class, callback );
    }

    /**
     * Returns the user IDs of the current user's Facebook friends who have
     * authorized the specific calling application or who have already connected
     * their Facebook accounts via Facebook Connect. The current user is
     * determined from the session_key parameter. The values returned from this
     * call are not storable.
     * 
     * @see <a
     *      href="http://wiki.developers.facebook.com/index.php/JS_API_M_FB.ApiClient.Friends_getAppUsers">Friends_getAppUsers</a>
     */
    public void friends_getAppUsers(AsyncCallback<List<Long>> callback) {
        JSONObject p = getDefaultParams ();
        friends_getGeneric ( "friends.getAppUsers", p.getJavaScriptObject (), callback );
    }

    /**
     * Returns the names and identifiers of any friend lists that the user has
     * created. The current user is determined from the session_key parameter.
     * <p>
     * The values returned from this call are storable. You can store the ID of
     * a friend list that the user has elected for use in some feature of your
     * application, but you should verify the ID periodically, as users may
     * delete or modify lists at any time. Friend lists are private on Facebook,
     * so you cannot republish this information to anyone other than the logged
     * in user. Members of lists may be obtained using friends.get with an flid
     * parameter.
     * 
     * @see <a
     *      href="http://wiki.developers.facebook.com/index.php/JS_API_M_FB.ApiClient.Friends_getLists">Friends_getLists</a>
     */
    public void friends_getLists(final AsyncCallback<List<FriendList>> callback) {
        JSONObject p = getDefaultParams ();
        callMethodRetList ( "friends.getLists", p.getJavaScriptObject (), FriendList.class, callback );
    }

    /**
     * Returns the Facebook user IDs of the mutual friends between the source
     * user and target user. For the source user, you can either specify the
     * source's user ID (the source_id) or use the session key of the logged-in
     * user, but not specify both.
     * <p/>
     * The source user must have authorized your application.
     * <p/>
     * You cannot store the IDs that get returned from this call.
     * <p/>
     * Privacy applies to the results of this method: If the source user chooses
     * to not show friends on his or her public profile, then no mutual friends
     * get returned. If a mutual friend chooses to be hidden from search
     * results, then that user's UID does not get returned from this call.
     * 
     * @param target_uid
     *            int The user ID of one of the target user whose mutual friends
     *            you want to retrieve. optional
     * 
     * @see <a
     *      href="http://wiki.developers.facebook.com/index.php/Friends.getMutualFriends">Friends_getMutualFriends</a>
     */
    public void friends_getMutualFriends(Long targetUid, AsyncCallback<List<Long>> callback) {
        Json j = Json.newInstance ().put ( "target_uid", targetUid );
        friends_getGeneric ( "friends.getMutualFriends", j.getJavaScriptObject (), callback );
    }

    /*
     * Method that parses long's from the response.
     */
    private void friends_getGeneric(String method, JavaScriptObject params, final AsyncCallback<List<Long>> callback) {

        AsyncCallback<JavaScriptObject> ac = new AsyncCallback<JavaScriptObject> () {
            public void onFailure(Throwable caught) {
                callback.onFailure ( caught );
            }

            public void onSuccess(JavaScriptObject jso) {
                if ("{}".equals ( new JSONObject ( jso ).toString () )) {
                    callback.onSuccess ( Collections.EMPTY_LIST );
                } else {
                    JsArrayNumber jsArray = jso.cast ();
                    callback.onSuccess ( Util.convertNumberArray ( jsArray ) );
                }
            }
        };
        callMethod ( method, params, ac );
    }

    /**
     * Evaluates an FQL (Facebook Query Language) query.
     * 
     * Warning: If you use JSON as the output format, you may run into problems
     * when selecting multiple fields with the same name or with selecting
     * multiple "anonymous" fields (for example, SELECT 1+2, 3+4 ...).
     * 
     * @param query
     *            The query to perform, as described in the FQL documentation.
     *            See http://wiki.developers.facebook.com/index.php/FQL FQL
     *            Documentation
     * 
     * @see <a
     *      href="http://wiki.developers.facebook.com/index.php/JS_API_M_FB.ApiClient.Fql_query">Fql_query</a>
     */
    public void fql_query(String query, AsyncCallback<JavaScriptObject> callback) {
        Json j = Json.newInstance ().put ( "query", query );
        callMethod ( "fql.query", j.getJavaScriptObject (), callback );
    }

    /**
     * Returns all visible groups according to the filters specified. You can
     * use this method to return all groups associated with a user, or query a
     * specific set of groups by a list of GIDs.
     * <p/>
     * If both the uid and gids parameters are provided, the method returns all
     * groups in the set of gids with which the user is associated. If the gids
     * parameter is omitted, the method returns all groups associated with the
     * provided user.
     * <p/>
     * However, if the uid parameter is omitted, the method returns all groups
     * associated with the provided gids, regardless of any user relationship.
     * <p/>
     * If both parameters are omitted, the method returns all groups of the
     * session user.
     * 
     * @param gids
     *            to filter by
     * 
     * @param callback
     *            result
     * @see <a
     *      href="http://wiki.developers.facebook.com/index.php/JS_API_M_FB.ApiClient.Groups_get">Groups_get</a>
     */
    public void groups_get(List<Long> gids, AsyncCallback<List<Group>> callback) {
        Json j = Json.newInstance ().put ( "gids", gids );
        callMethodRetList ( "groups.get", j.getJavaScriptObject (), Group.class, callback );
    }

    /**
     * Returns membership list data associated with a group.
     */
    public void groups_getMembers(Long gid, AsyncCallback<GroupMembers> callback) {

        if (gid == null) {
            throw new IllegalArgumentException ( "gid cannot be null" );
        }
        Json j = Json.newInstance ().put ( "gid", gid );
        callMethodRetObject ( "groups.getMembers", j.getJavaScriptObject (), GroupMembers.class, callback );
    }

    /**
     * TODO: Implement
     */
    public void intl_getTranslations(Map<String, String> params, AsyncCallback<JavaScriptObject> callback) {
    }

    /**
     * TODO: Implement
     */
    public void intl_uploadNativeStrings(Map<String, String> params, AsyncCallback<JavaScriptObject> callback) {
    }

    /**
     * TODO: Implement
     */
    public void links_get(Map<String, String> params, AsyncCallback<JavaScriptObject> callback) {
    }

    /**
     * TODO: Implement
     */
    public void links_post(Map<String, String> params, AsyncCallback<JavaScriptObject> callback) {
    }

    /**
     * TODO: Implement
     */
    public void liveMessage_send(Map<String, String> params, AsyncCallback<JavaScriptObject> callback) {
    }

    /**
     * TODO: Implement
     */
    public void message_getThreadsInFolder(Map<String, String> params, AsyncCallback<JavaScriptObject> callback) {
        // TODO Auto-generated method stub

    }

    /**
     * Lets a user write a Facebook note through your application. Before a user
     * can write a note through your application, the user must grant your
     * application the create_note extended permission.
     * 
     * @param note to be created
     */
    public void notes_create(Note note, AsyncCallback<Long> callback) {
        callMethodRetLong ( "notes.create", note, callback );
    }

    /**
     * TODO: Implement
     */
    public void notes_delete(Map<String, String> params, AsyncCallback<JavaScriptObject> callback) {
    }

    /**
     * TODO: Implement
     */
    public void notes_edit(Map<String, String> params, AsyncCallback<JavaScriptObject> callback) {
    }

    /**
     * Returns a list of all of the visible notes written by the specified user.
     * If the user is logged out, only publicly viewable notes get returned.
     * 
     * For desktop applications, this call works only for the logged-in user,
     * since that's the only session you have. If you want data for other users,
     * make an FQL query (fql.query) on the note (FQL) table.
     * 
     * @see <a
     *      href="http://wiki.developers.facebook.com/index.php/Notes.get">Notes.get</a>
     */
    public void notes_get(Long uid, AsyncCallback<List<Note>> callback) {
        String fql = "SELECT note_id,title,content,created_time,updated_time FROM note WHERE uid=" + uid;
        Json j = Json.newInstance ().put ( "query", fql );
        callMethodRetList ( "fql.query", j.getJavaScriptObject (), Note.class, callback );
    }

    /**
     * This method returns the same set of subelements, whether or not there are
     * outstanding notifications in any area. Note that if the unread subelement
     * value is 0 for any of the pokes or shares elements, the most_recent
     * element is also 0. Otherwise, the most_recent element contains an
     * identifier for the most recent notification of the enclosing type.
     * <p/>
     * If you are building an application that notifies users of new
     * messages/pokes/shares, we encourage you to use the following logic when
     * deciding whether to show a notification:
     * 
     * @see <a
     *      href="http://wiki.developers.facebook.com/index.php/JS_API_M_FB.ApiClient.Notifications_get">Notifications_get</a>
     */
    public void notifications_get(final AsyncCallback<List<NotificationRequest>> callback) {
        JSONObject p = getDefaultParams ();
        final NotificationRequest.NotificationType[] types = NotificationRequest.NotificationType.values ();

        final AsyncCallback<JavaScriptObject> internCallback = new AsyncCallback<JavaScriptObject> () {
            public void onFailure(Throwable caught) {
                callback.onFailure ( caught );
            }

            public void onSuccess(JavaScriptObject jso) {
                List<NotificationRequest> resultList = new ArrayList<NotificationRequest> ();
                JSONObject result = new JSONObject ( jso );
                for (NotificationRequest.NotificationType t : types) {
                    if (result.isObject ().get ( t.toString () ) != null) {
                        resultList.add ( new NotificationRequest ( t.toString (), result.isObject ().get ( t.toString () ) ) );
                    }
                }
                callback.onSuccess ( resultList );
            }
        };
        callMethod ( "notifications.get", p.getJavaScriptObject (), internCallback );
    }

    /**
     * This method gets all the current session user's notifications, as well as
     * data for the applications that generated those notifications. It is a
     * wrapper around the notification and application FQL tables; you can
     * achieve more fine-grained control by using those two FQL tables in
     * conjunction with the fql.multiquery API call.
     * 
     * @param start_time
     *            time Indicates the earliest time to return a notification.
     *            This equates to the updated_time field in the notification FQL
     *            table. If not specified, this call returns all available
     *            notifications.
     * @param include_read
     *            bool Indicates whether to include notifications that have
     *            already been read. By default, notifications a user has read
     *            are not included.
     */
    public void notifications_getList(Long startTime, Boolean includeRead, final AsyncCallback<List<Notification>> callback) {

        Json j = Json.newInstance ().put ( "start_time", startTime ).put ( "include_read", includeRead );
        AsyncCallback<JavaScriptObject> internCallback = new AsyncCallback<JavaScriptObject> () {

            public void onFailure(Throwable caught) {
                callback.onFailure ( caught );
            }

            public void onSuccess(JavaScriptObject jso) {
                List<Notification> resultList = new ArrayList<Notification> ();
                JSONObject result = new JSONObject ( jso );
                JSONValue v = result.isObject ().get ( "notifications" );
                JSONArray a = v.isArray ();

                for (int i = 0; a != null && i < a.size (); i++) {
                    resultList.add ( new Notification ( a.get ( i ).isObject () ) );
                }
                callback.onSuccess ( resultList );
            }

        };
        callMethod ( "notifications.getList", j.getJavaScriptObject (), internCallback );
    }

    /**
     * Wraps the same method that takes a list of notification ids as parameter
     * 
     * @see #notifications_markRead(List, AsyncCallback)
     */
    public void notifications_markRead(Long notificationId, final AsyncCallback<Boolean> callback) {
        List<Long> ids = new ArrayList<Long> ();
        ids.add ( notificationId );
        notifications_markRead ( ids, callback );
    }

    /**
     * 
     * This method marks one or more notifications as read. You return the
     * notifications by calling notifications.getList or querying the
     * notification FQL table.
     * 
     * Applications must pass a valid session key, and can only mark the
     * notifications of the current session user.
     * 
     * @param notification_ids
     *            array The IDs of the notifications to mark as read, as
     *            retrieved via the notification FQL table or the
     *            notifications.getList API method. This is a comma-separated
     *            list.
     * 
     *            See
     *            http://wiki.developers.facebook.com/index.php/Notifications
     *            .markRead
     */
    public void notifications_markRead(List<Long> notificationIds, final AsyncCallback<Boolean> callback) {
        Json j = Json.newInstance ().put ( "notification_ids", notificationIds );
        callMethodRetBoolean ( "notifications.markRead", j.getJavaScriptObject (), callback );
    }

    /**
     * Wraps the same method but less parameters
     * 
     * @see #notifications_send(List, String, NotificationType, AsyncCallback)
     */
    public void notifications_send(Long uid, String notification, AsyncCallback<JavaScriptObject> callback) {
        List<Long> uids = new ArrayList<Long> ();
        uids.add ( uid );
        notifications_send ( uids, notification, NotificationType.user_to_user, callback );
    }

    /**
     * Valid notificationTypes
     */
    public static enum NotificationType {
        user_to_user, app_to_user
    }

    /**
     * Sends a notification to a set of users. Notifications are items sent by
     * an application to a user's notifications page in response to some sort of
     * user activity within an application. You can also send messages to the
     * logged-in user's notifications (located on the right hand side of the
     * chat bar), as well as on their notifications page.
     * <p/>
     * Your application can send a number of notifications to a user in a day
     * based on a number of metrics (or buckets). To get this number, use
     * admin.getAllocation or check the Allocations tab on the Insights
     * dashboard for your application in the Facebook Developer application. If
     * the number of recipients exceeds the allocation limit, then the
     * notification gets sent to the first n recipients specified in to_ids (see
     * the Parameters table below), where n is the allocation limit.
     * <p/>
     * Notifications sent to the notifications page for non-application users
     * are subject to spam control. Read more information about how spamminess
     * is measured. Additionally, any notification that you send on behalf of a
     * user appears with that user's notifications as a "sent notification."
     * <p/>
     * 
     * @param toIds
     *            Comma-separated list of recipient IDs. These must be either
     *            friends of the logged-in user or people who have added your
     *            application. To send a notification to the current logged-in
     *            user without a name prepended to the message, set to_ids to
     *            the empty string. You should include no more than 50 user IDs
     *            the array, otherwise you run the risk of your call timing out
     *            during processing.
     * @param notification
     *            The content of the notification. The notification uses a
     *            stripped down version of FBML and HTML, allowing only text and
     *            links (see the list of allowed tags). The notification can
     *            contain up to 2,000 characters.
     * @param type
     *            string Specify whether the notification is a user_to_user one
     *            or an app_to_user. (Default value is user_to_user.)
     * 
     * @see <a
     *      href="http://wiki.developers.facebook.com/index.php/JS_API_M_FB.ApiClient.Notifications_send">Notifications_send</a>
     * @see <a
     *      href="http://wiki.developers.facebook.com/index.php/Notifications.send">Notifications.send</a>
     */
    public void notifications_send(List<Long> toIds, String notification, NotificationType type, AsyncCallback<JavaScriptObject> callback) {
        Json j = Json.newInstance ().put ( "to_ids", toIds );
        j.put ( "notification", notification );
        j.put ( "type", type.toString () );
        callMethod ( "notifications.send", j.getJavaScriptObject (), callback );

    }

    public void notifications_sendEmail(Map<String, String> params, AsyncCallback<JavaScriptObject> callback) {
        // TODO Auto-generated method stub

    }

    public void pages_getInfo(Map<String, String> params, AsyncCallback<JavaScriptObject> callback) {
        // TODO Auto-generated method stub

    }

    public void pages_isAdmin(Map<String, String> params, AsyncCallback<JavaScriptObject> callback) {
        // TODO Auto-generated method stub

    }

    public void pages_isAppAdded(Map<String, String> params, AsyncCallback<JavaScriptObject> callback) {
        // TODO Auto-generated method stub

    }

    public void pages_isFan(Map<String, String> params, AsyncCallback<JavaScriptObject> callback) {
        // TODO Auto-generated method stub

    }

    public void photos_addTag(Map<String, String> params, AsyncCallback<JavaScriptObject> callback) {
        // TODO Auto-generated method stub

    }

    /**
     * Creates and returns a new album owned by the specified user or the
     * current session user. See photo uploads for a description of the upload
     * workflow. The only storable values returned from this call are aid and
     * owner. No relationships between them are storable.
     * <p/>
     * For Web applications, you must pass either the ID of the user on whose
     * behalf you're making this call or the session key for that user, but not
     * both. If you don't specify a user with the uid parameter, then that user
     * whose session it is will be the target of the call.
     * <p/>
     * However, if your application is a desktop application, you must pass a
     * valid session key for security reasons. Do not pass a uid parameter.
     * 
     * @param name
     *            string The album name.
     * @param location
     *            string The album location.
     * @param description
     *            string The album description.
     * @param visible
     *            string Visibility of the album. One of friends,
     *            friends-of-friends, networks, everyone.
     * 
     * @see <a
     *      href="http://wiki.developers.facebook.com/index.php/JS_API_M_FB.ApiClient.Photos_createAlbum">Photos_createAlbum</a>
     */
    public void photos_createAlbum(Album album, final AsyncCallback<Album> callback) {
        callMethodRetObject ( "photos.createAlbum", album, Album.class, callback );
    }

    /**
     * Get current users albums See #photos_getAlbums(Map, AsyncCallback)
     */
    public void photos_getAlbums(final AsyncCallback<List<Album>> callback) {
        photos_getAlbums ( null, null, callback );
    }

    /**
     * Returns metadata about all of the photo albums uploaded by the specified
     * user.
     * <p/>
     * This method returns information from all visible albums satisfying the
     * filters specified. The method can be used to return all photo albums
     * created by a user, query a specific set of albums by a list of aids, or
     * filter on any combination of these two.
     * <p/>
     * This call does return a user's profile picture album. However, you cannot
     * upload photos to this album using photos.upload. You can determine
     * whether an album is the profile album by comparing the album cover pid
     * with the user's profile picture pid. If they are the same pid, then
     * that's the profile picture album. Also, see the Notes below for another
     * way of returning the profile picture album.
     * <p/>
     * You cannot store the values returned from this call.
     * 
     * @param uid
     *            Return albums created by this user. You must specify either
     *            uid or aids. The uid parameter has no default value.
     * @param aids
     *            Return albums with aids in this list. This is a
     *            comma-separated list of aids. You must specify either uid or
     *            aids. The aids parameter has no default value.
     * 
     * @see <a
     *      href="http://wiki.developers.facebook.com/index.php/JS_API_M_FB.ApiClient.Photos_getAlbums">Photos_getAlbums</a>
     */
    public void photos_getAlbums(Long uid, List<Long> aids, final AsyncCallback<List<Album>> callback) {
        Json j = Json.newInstance ().put ( "uid", uid ).put ( "aids", aids );
        callMethodRetList ( "photos.getAlbums", j.getJavaScriptObject (), Album.class, callback );
    }

    /**
     * Returns all visible photos of subject.
     * 
     * @see #photos_get(Long, Long, List, AsyncCallback)
     */
    public void photos_get(Long subjId, final AsyncCallback<List<Photo>> callback) {
        photos_get ( subjId, null, null, callback );
    }

    /**
     * Returns all visible photos according to the filters specified. You can
     * use this method to find all photos that are:
     * <p/>
     * Tagged with the specified subject (passing the user's uid as the subj_id)
     * Contained within the album specified by aid Included in the list of
     * photos specified by pids Any combination of these three criteria
     * 
     * @param subjId
     *            int Filter by photos tagged with this user. You must specify
     *            at least one of subj_id, aid or pids. The subj_id parameter
     *            has no default value, but if you pass one, it must be the
     *            user's user ID.
     * @param aid
     *            string Filter by photos in this album. You must specify at
     *            least one of subj_id, aid or pids. The aid parameter has no
     *            default value. The aid cannot be longer than 50 characters.
     * @param pids
     *            array Filter by photos in this list. This is a comma-separated
     *            list of pids. You must specify at least one of subj_id, aid or
     *            pids. The pids parameter has no default value.
     * 
     * @see <a
     *      href="http://wiki.developers.facebook.com/index.php/JS_API_M_FB.ApiClient.Photos_get">Photos_get</a>
     */
    public void photos_get(Long subjId, Long aid, List<Long> pids, final AsyncCallback<List<Photo>> callback) {
        Json j = Json.newInstance ().put ( "subj_id", subjId ).put ( "aid", aid ).put ( "pids", pids );
        callMethodRetList ( "photos.get", j.getJavaScriptObject (), Photo.class, callback );
    }

    public void photos_getTags(Map<String, String> params, AsyncCallback<JavaScriptObject> callback) {
        Window.alert ( "not implemented" );
    }

    public void photos_upload(Map<String, String> params, AsyncCallback<JavaScriptObject> callback) {
        Window.alert ( "not implemented" );
    }

    public void profile_getFBML(Map<String, String> params, AsyncCallback<JavaScriptObject> callback) {
        Window.alert ( "not implemented" );
    }

    public void profile_getInfo(Map<String, String> params, AsyncCallback<JavaScriptObject> callback) {
        Window.alert ( "not implemented" );
    }

    public void profile_getInfoOptions(Map<String, String> params, AsyncCallback<JavaScriptObject> callback) {
        Window.alert ( "not implemented" );
    }

    public void profile_setFBML(Map<String, String> params, AsyncCallback<JavaScriptObject> callback) {
        Window.alert ( "not implemented" );
    }

    public void profile_setInfo(Map<String, String> params, AsyncCallback<JavaScriptObject> callback) {
        Window.alert ( "not implemented" );
    }

    public void profile_setInfoOptions(Map<String, String> params, AsyncCallback<JavaScriptObject> callback) {
        Window.alert ( "not implemented" );
    }

    /**
     * Valid params for <code>stream.get</code>
     * 
     * @deprecated Will soon be outdated.
     */
    public enum StreamGetParams {
        uid, session_key, viewer_id, source_ids, start_time, end_time, limit, filter_key, metadata
    }

    /**
     * This method returns a list of Stream objects that contains the stream
     * from the perspective of a specific viewer -- a user or a Facebook Page.
     * 
     * The hashmap takes the following arguments:
     * 
     * @param viewer_id
     *            int The user ID for whom you are fetching stream data. You can
     *            pass 0 for this parameter to retrieve publicly viewable
     *            information. However, desktop applications must always specify
     *            a viewer as well as a session key. (Default value is the
     *            current session user.)
     * @param source_ids
     *            array An array containing all the stream data for the user
     *            profiles and Pages connected to the viewer_id. You can filter
     *            the stream to include posts by the IDs you specify here.
     *            (Default value is all connections of the viewer.)
     * @param start_time
     *            time The earliest time (in Unix time) for which to retrieve
     *            posts from the stream. The start_time uses the updated_time
     *            field in the stream (FQL) table as the baseline for
     *            determining the earliest time for which to get the stream.
     *            (Default value is 1 day ago.)
     * @param end_time
     *            time The latest time (in Unix time) for which to retrieve
     *            posts from the stream. The end_time uses the updated_time
     *            field in the stream (FQL) table as the baseline for
     *            determining the latest time for which to get the stream.
     *            (Default value is now.)
     * @param limit
     *            int A 32-bit int representing the total number of posts to
     *            return. (Default value is 30 posts.)
     * @param filter_key
     *            string A filter associated with the user. Filters get returned
     *            by stream.getFilters or the stream_filter FQL table. To filter
     *            for stream posts from your application, look for a filter with
     *            a filter_key set to app_YOUR_APPLICATION_ID.
     * @param metadata
     *            array A JSON-encoded array in which you can specify one or
     *            more of 'albums', 'profiles', and 'photo_tags' to request the
     *            user's aid, id (user ID or Page ID), and pid (respectively)
     *            when you call stream.get. All three parameters are optional.
     *            (Default value is false for all three keys.)
     * 
     * @see <a
     *      href="http://wiki.developers.facebook.com/index.php/JS_API_M_FB.ApiClient.stream_get">stream_get</a>
     */
    public void stream_get(Map<Enum<StreamGetParams>, String> params, final AsyncCallback<List<Stream>> ac) {

        final String lp = "FacebookApiImpl#stream_get:";
        GWT.log ( lp + " called", null );

        JavaScriptObject p = getAllParams ( StreamGetParams.values (), params );

        // Create native callback and parse response.
        final AsyncCallback<JavaScriptObject> c = new AsyncCallback<JavaScriptObject> () {

            public void onSuccess(JavaScriptObject js) {
                GWT.log ( FacebookApi.class + ": stream.get got response", null );
                List<Stream> result = new ArrayList<Stream> ();

                JSONObject jv = new JSONObject ( js );
                JSONValue value = jv.isObject ().get ( "posts" );
                JSONArray array = value.isArray ();

                for (int i = 0; array != null && i < array.size (); i++) {
                    JSONValue v = array.get ( i );
                    JSONObject o = v.isObject ();
                    Stream stream = new Stream ( o );
                    result.add ( stream );
                }
                GWT.log ( FacebookApi.class + ": result size = " + result.size (), null );
                ac.onSuccess ( result );
            }

            public void onFailure(Throwable caught) {
                ac.onFailure ( null );
            }
        };
        callMethod ( "stream.get", p, c );
    }

    /**
     * Valid permissions
     */
    public enum Permission {
        read_stream, publish_stream, create_event, rsvp_event, create_note
    };

    /**
     * <p/>
     * Checks whether the user has opted in to an extended application
     * permission.
     * <p/>
     * For non-desktop applications, you may pass the ID of the user on whose
     * behalf you're making this call. If you don't specify a user with the uid
     * parameter but you do specify a session_key, then that user whose session
     * it is will be the target of the call.
     * <p/>
     * However, if your application is a desktop application, you must pass a
     * valid session key for security reasons. Passing a uid parameter will
     * result in an error.
     * 
     * @param ext_perm
     *            string String identifier for the extended permission that is
     *            being checked for. Must be one of email, read_stream,
     *            publish_stream, offline_access, status_update, photo_upload,
     *            create_event, rsvp_event, sms, video_upload, create_note,
     *            share_item. optional
     * @param session_key
     *            string The session key of the user whose permissions you are
     *            checking. Note: A session key is always required for desktop
     *            applications. It is required for Web applications only when
     *            the uid is not specified.
     * @param uid
     *            int The user ID of the user whose permissions you are
     *            checking. If this parameter is not specified, then it defaults
     *            to the session user. Note: This parameter applies only to Web
     *            applications and is required by them only if the session_key
     *            is not specified. Facebook ignores this parameter if it is
     *            passed by a desktop application.
     * 
     * @see <a
     *      href="http://wiki.developers.facebook.com/index.php/JS_API_M_FB.ApiClient.Users_hasAppPermission">Users_hasAppPermission</a>
     */
    public void users_hasAppPermission(Permission permission, final AsyncCallback<Boolean> callback) {
        GWT.log ( "users_hasAppPermission: " + permission.toString (), null );

        Json j = Json.newInstance ().put ( "ext_perm", permission.toString () );

        Callback<JavaScriptObject> nativeCallback = new Callback<JavaScriptObject> ( callback ) {
            public void onSuccess(JavaScriptObject jso) {
                callback.onSuccess ( "1".equals ( jso.toString () ) );
            }
        };
        callMethod ( "users.hasAppPermission", j.getJavaScriptObject (), nativeCallback );
    }

    public void sms_canSend(Map<String, String> params, AsyncCallback<JavaScriptObject> callback) {
        Window.alert ( "not implemented" );
    }

    public void sms_send(Map<String, String> params, AsyncCallback<JavaScriptObject> callback) {
        Window.alert ( "not implemented" );
    }

    /**
     * Updates current user's status.
     * 
     * @see #status_set(Long, String, AsyncCallback)
     */
    public void status_set(String status, AsyncCallback<JavaScriptObject> callback) {
        status_set ( null, status, callback );
    }

    /**
     * Updates a user's Facebook status through your application. Before your
     * application can set a user's status, the user must grant it the
     * status_update extended permission. This call is a streamlined version of
     * users.setStatus, as it takes fewer arguments.
     * <p/>
     * For Web applications, you must pass either the ID of the user on whose
     * behalf you're making this call or the session key for that user, but not
     * both. If you don't specify a user with the uid parameter, then that user
     * whose session it is will be the target of the call. To set the status of
     * a facebook Page, pass the uid of the Page.
     * <p/>
     * However, if your application is a desktop application, you must pass a
     * valid session key for security reasons. Do not pass a uid parameter.
     * 
     * @param status
     *            string The status message to set. Note: The maximum message
     *            length is 255 characters; messages longer than that limit will
     *            be truncated and appended with "...".
     * 
     * @see <a
     *      href="http://wiki.developers.facebook.com/index.php/JS_API_M_FB.ApiClient.Users_setStatus">Users_setStatus</a>
     */
    public void status_set(Long uid, String status, AsyncCallback<JavaScriptObject> callback) {
        Json j = Json.newInstance ().put ( "uid", uid ).put ( "status", status );
        callMethod ( "users.setStatus", j.getJavaScriptObject (), callback );
    }

    /**
     * Returns the user's current and most recent statuses.
     * 
     * For desktop applications, this call works only for the logged-in user,
     * since that's the only session you have. If you want data for other users,
     * make an FQL query (fql.query) on the status (FQL) table.
     * 
     *required
     * 
     * @param uid
     *            int The user ID of the user whose status messages you want to
     *            retrieve.
     * @param limit
     *            NOT SUPPORTED int The number of status messages you want to
     *            return. (Default value is 100.)
     * 
     * @see <a
     *      href="http://wiki.developers.facebook.com/index.php/Status.get">Status.get</a>
     */
    public void status_get(Long uid, Integer limit, AsyncCallback<JavaScriptObject> callback) {
        if (uid == null) {
            Window.alert ( "Error: status_get called without uid" );
            throw new IllegalArgumentException ( "status_get called without uid" );
        }

        /**
         * Cant get this to work, its in beta so wont use much time on it
         * JSONObject params = getDefaultParams (); params.put( "uid", new
         * JSONString ( uid ) ) ; callMethod ( "status.get",
         * params.getJavaScriptObject(), callback );
         */
        fql_query ( "SELECT message FROM status WHERE uid=" + uid + " LIMIT 1", callback );
    }

    /**
     * This method adds a comment to a post that was already published to a
     * user's Wall.
     * <p/>
     * Privacy rules apply to the posts to which the user can add comments; the
     * user must be able to see the post in order for your application to allow
     * the user add a comment to it.
     * <p/>
     * Desktop applications must pass a valid session key, and only the user
     * associated with that session key can add comments. Other applications can
     * allows users to add comments to any posts the user can see, provided you
     * have a valid post_id.
     * <p/>
     * In order for your application to allow a user to add a comment, that user
     * must grant your application the publish_stream extended permission.
     * 
     * @param post_id
     *            string The ID for the post to which you're adding the comment.
     * @param comment
     *            string The text of the comment. This is a plain text parameter
     *            only; you cannot format the comment with HTML or FBML.
     * 
     * @param params
     *            map
     * @param callback
     *            result
     * 
     * @see <a
     *      href="http://wiki.developers.facebook.com/index.php/Stream.addComment">Stream.addComment</a>
     */
    public void stream_addComment(String postId, String comment, AsyncCallback<JavaScriptObject> callback) {
        Json j = Json.newInstance ().put ( "post_id", postId ).put ( "comment", comment );
        callMethod ( "stream.addComment", j.getJavaScriptObject (), callback );
    }

    /**
     * This method lets a user add a like to any post the user can see. A user
     * can like each post only once.
     * <p/>
     * Desktop applications must pass a valid session key, and only the user
     * associated with that session key can like the post. Otherwise, the
     * specified user can like the post.
     * <p/>
     * In order for your user to like a post, the user must grant your
     * application the publish_stream extended permission.
     * 
     * 
     * @param uid
     *            string The user ID of the user who likes the post. If this
     *            parameter is not specified, then it defaults to the session
     *            user. Note: This parameter applies only to Web applications
     *            and is required by them only if the session_key is not
     *            specified. Facebook ignores this parameter if it is passed by
     *            a desktop application.
     * 
     * @param post_id
     *            string The ID of the post. &lt;/p&gt; (non-Javadoc)
     * 
     * @params params map
     * @params callback result
     * 
     * @see <a
     *      href="http://wiki.developers.facebook.com/index.php/Stream.addLike">Stream.addLike</a>
     */
    public void stream_addLike(String postId, AsyncCallback<JavaScriptObject> callback) {
        Json j = Json.newInstance ().put ( "post_id", postId );
        callMethod ( "stream.addLike", j.getJavaScriptObject (), callback );
    }

    /**
     * This method removes a like a user added to a post.
     * <p/>
     * Desktop applications must pass a valid session key, and can remove likes
     * made by the user associated with that session key only. Other
     * applications can remove any likes, provided you have a valid post_id.
     * <p/>
     * In order to remove a Like from a post, the user must grant your
     * application the publish_stream extended permission.
     * 
     * @param post_id
     *            string The ID of the post.
     * 
     * @param callback
     *            result
     * 
     * @see <a
     *      href="http://wiki.developers.facebook.com/index.php/Stream.removeLike">Stream.removeLike</a>
     */
    public void stream_removeLike(String postId, AsyncCallback<JavaScriptObject> callback) {
        Json j = Json.newInstance ().put ( "post_id", postId );
        callMethod ( "stream.removeLike", j.getJavaScriptObject (), callback );
    }

    /**
     * This method returns all comments associated with a post in a user's
     * stream. This method returns comments only if the user who owns the post
     * (that is, the user published the post to his or her profile) has
     * authorized your application.
     * <p/>
     * This method is a wrapper for comment FQL table, indexed by post_id rather
     * than xid. param post_id string The ID for the post for which you're
     * retrieving the comments.
     * 
     * @param params
     *            map
     * @param callback
     *            result
     * 
     * @see <a
     *      href="http://wiki.developers.facebook.com/index.php/Stream.getComments">Stream.getComments</a>
     */
    public void stream_getComments(String postId, final AsyncCallback<List<Comment>> callback) {
        Json j = Json.newInstance ().put ( "post_id", postId );
        callMethodRetList ( "stream.getComments", j.getJavaScriptObject (), Comment.class, callback );
    }

    /**
     * This method returns any filters associated with a user's home page
     * stream. You can use these filters to narrow down a stream you want to
     * return with stream.get or when you query the stream FQL table.
     * 
     * @see <a
     *      href="http://wiki.developers.facebook.com/index.php/JS_API_M_FB.ApiClient.stream_getFilters">stream.getFilters</a>
     */
    public void stream_getFilters(final AsyncCallback<List<StreamFilter>> callback) {
        JavaScriptObject p = getDefaultParams ().getJavaScriptObject ();
        callMethodRetList ( "stream.getFilters", p, StreamFilter.class, callback );
    }

    public void stream_publish(Map<String, String> params, AsyncCallback<JavaScriptObject> callback) {
        Window.alert ( "stream.publish: Not Implemented" );
    }

    /**
     * This method removes a post from a user's Wall. The post also gets removed
     * from the user's and the user's friends' News Feeds.
     * <p/>
     * Your application may only remove posts that were created through it.
     * <p/>
     * Desktop applications must pass a valid session key, and can remove posts
     * only from the user associated with that session key. Other applications
     * can delete any post that they published, provided you have a valid
     * post_id. Web applications must pass either a valid session key or a user
     * ID.
     * <p/>
     * In order to remove a post from a user's Wall, the user must grant your
     * application the publish_stream extended permission.
     * 
     * Parameters Required Name Type Description optional
     * 
     * @param post_id
     *            string The ID for the post you want to remove.
     * 
     * 
     * @see <a
     *      href="http://wiki.developers.facebook.com/index.php/Stream.remove">Stream.remove</a>
     */
    public void stream_remove(String postId, AsyncCallback<JavaScriptObject> callback) {
        Json j = Json.newInstance ().put ( "post_id", postId );
        callMethod ( "stream.remove", j.getJavaScriptObject (), callback );
    }

    /**
     * This method removes a comment from a post.
     * <p/>
     * Privacy rules apply to the posts from which the user can delete comments;
     * if the post is on the user's Wall, any comment can be deleted. If the
     * post is on a friend's Wall, only comments made by the user can be
     * deleted.
     * <p/>
     * Desktop applications must pass a valid session key, and can remove
     * comments made by the user associated with that session key only. Other
     * applications can delete any comments, provided you have a valid post_id.
     * <p/>
     * In order to remove a comment, the user must grant your application the
     * publish_stream extended permission.
     * 
     * @param comment
     *            string The ID for the comment you want to remove.
     * 
     * @see <a
     *      href="http://wiki.developers.facebook.com/index.php/Stream.removeComment">Stream.removeComment</a>
     */
    public void stream_removeComment(final String commentId, final AsyncCallback<JavaScriptObject> callback) {
        Json j = Json.newInstance ().put ( "comment_id", commentId );
        callMethod ( "stream.removeComment", j.getJavaScriptObject (), callback );
    }

    public void users_getInfo(Map<String, String> params, AsyncCallback<JavaScriptObject> callback) {
        Window.alert ( "users_getInfo" );
    }

    /**
     * Gets the user ID (uid) associated with the current session. This value
     * should be stored for the duration of the session, to avoid unnecessary
     * subsequent calls to this method.
     * 
     * @param params
     *            map
     * @param callback
     *            result
     */
    public void users_getLoggedInUser(AsyncCallback<Long> callback) {
        JavaScriptObject p = getDefaultParams ().getJavaScriptObject ();
        callMethodRetLong ( "Users.getLoggedInUser", p, callback );
    }

    public void users_getStandardInfo(Map<String, String> params, AsyncCallback<JavaScriptObject> callback) {
        Window.alert ( "not implemented" );
    }

    public void users_isAppUser(Map<String, String> params, AsyncCallback<JavaScriptObject> callback) {
        Window.alert ( "not implemented" );
    }

    public void users_isVerified(Map<String, String> params, AsyncCallback<JavaScriptObject> callback) {
        Window.alert ( "not implemented" );
    }

    public void users_setStatus(Map<String, String> params, AsyncCallback<JavaScriptObject> callback) {
        Window.alert ( "not implemented" );
    }

    public void video_getUploadLimits(Map<String, String> params, AsyncCallback<JavaScriptObject> callback) {
        Window.alert ( "not implemented" );
    }

    public void video_upload(Map<String, String> params, AsyncCallback<JavaScriptObject> callback) {
        Window.alert ( "not implemented" );
    }

    /*
     * Another wrapper. Use this to get all parameters in one line.
     */
    private <T extends Enum<T>> JavaScriptObject getAllParams(final Enum<T>[] enumValues, final Map<Enum<T>, String> params) {

        JSONObject allParams = getDefaultParams ();
        copyAllParams ( allParams, enumValues, params );
        return allParams.getJavaScriptObject ();
    }

    /*
     * Get jsonobject with api_key parameter
     */
    private JSONObject getDefaultParams() {
        if (apiKey == null) {
            Window.alert ( "api_key==null" );
        }
        JSONObject obj = new JSONObject ();
        obj.put ( "api_key", new JSONString ( apiKey ) );
        return obj;
    }

    /*
     * Copy a list of enum values to a json object
     */
    private <T extends Enum<T>> void copyAllParams(final JSONObject jo, final Enum<T>[] enumValues, final Map<Enum<T>, String> params) {

        if (params == null) {
            return;
        }
        for (Enum<?> e : enumValues) {
            String propValue = params.get ( e );

            if (propValue != null) {
                jo.put ( e.toString (), new JSONString ( propValue ) );
            }
        }
    }

    /*
     * Copy param
     */
    private void copyParam(JSONObject obj, Map<String, String> params, String key) {
        if (params.get ( key ) != null) {
            obj.put ( key, new JSONString ( params.get ( key ) ) );
            params.remove ( key );
        }
    }

    /*
     * Call method and parse response to Integer
     */
    private void callMethodRetInteger(final String method, final JavaScriptObject params, final AsyncCallback<Integer> callback) {

        callMethod ( method, params, new Callback<JavaScriptObject> ( callback ) {
            public void onSuccess(JavaScriptObject jso) {
                callback.onSuccess ( new Integer ( jso.toString () ) );
            }
        } );
    }

    /*
     * Call method and parse response to Integer
     */
    private void callMethodRetLong(final String method, final JavaScriptObject params, final AsyncCallback<Long> callback) {

        callMethod ( method, params, new Callback<JavaScriptObject> ( callback ) {
            public void onSuccess(JavaScriptObject jso) {
                callback.onSuccess ( new Long ( jso.toString () ) );
            }
        } );
    }

    /*
     * Call method and cast javascriptobject to a extending javascriptobject.
     */
    private <T extends JavaScriptObject> void callMethodRetObject(final String method,
                                                                  final JavaScriptObject params,
                                                                  final Class<T> entity,
                                                                  final AsyncCallback<T> callback) {

        callMethod ( method, params, new Callback<JavaScriptObject> ( callback ) {
            public void onSuccess(JavaScriptObject jso) {
                T entity = (T) jso.cast ();
                callback.onSuccess ( entity );
            }
        } );
    }

    /*
     * Method for calling method wich returns a boolean in the form "1" or "0",
     * or "true" or "false"
     */
    private void callMethodRetBoolean(final String method, final JavaScriptObject params, final AsyncCallback<Boolean> callback) {

        callMethod ( method, params, new Callback<JavaScriptObject> ( callback ) {

            public void onSuccess(JavaScriptObject response) {

                // Hackarond facebook bug, data.setCookie returns an empty
                // object, should return 0 or 1.
                if (new JSONObject ( response ).toString ().equals ( "{}" )) {
                    callback.onSuccess ( true );
                    return;
                }
                callback.onSuccess ( ("1".equals ( response.toString () ) || "true".equals ( response.toString () )) );
            }
        } );
    }

    /*
     * Convenient method and cast response to a list
     */
    @SuppressWarnings("unchecked")
    public void callMethodRetList(final String method, final JavaScriptObject params, final Class entity, final AsyncCallback callback) {

        callMethod ( method, params, new Callback<JavaScriptObject> ( callback ) {
            public void onSuccess(JavaScriptObject jso) {
                try {

                    if ("{}".equals ( new JSONObject ( jso ).toString ().trim () )) {
                        callback.onSuccess ( new ArrayList () );
                    } else {
                        callback.onSuccess ( cast ( entity, jso ) );
                    }
                } catch (Exception e) {
                }
            }
        } );
    }

    // Convenient method for casting a javascriptobject to a list.
    private <T extends JavaScriptObject> List<T> cast(Class<T> entity, JavaScriptObject jso) {
        if (jso == null) {
            return new ArrayList<T> ();
        }
        List<T> result = new ArrayList<T> ();

        JsArray<T> array = jso.cast ();

        for (int i = 0; i < array.length (); i++) {
            result.add ( array.get ( i ) );
        }
        return result;
    }

    /*
     * Call Facebook method and execute callback method. This methods needs to
     * check the response from facebook. Sometimes facebook returns object,
     * sometimes primitivies. If a primitive is returned, wrap it in a new
     * object so it can be converted to JavaScriptObject
     */
    private native void callMethod(final String method, final JavaScriptObject params, final AsyncCallback<JavaScriptObject> callback) /*-{
        var app=this;
        $wnd.FB_RequireFeatures(["Api"], function(){			
        	$wnd.FB.Facebook.apiClient.callMethod( method, params, 
        		function(result, exception){
        		    var jso=null;        		    
        		    if ( result == undefined ) {
        		        app.@com.gwittit.client.facebook.FacebookApi::callbackError(Lcom/google/gwt/user/client/rpc/AsyncCallback;Lcom/google/gwt/core/client/JavaScriptObject;)(callback,exception);
        		    } else {
            		    if ( typeof ( result ) == 'object' ) {
            		        jso = result;
            		    } else if ( typeof ( result ) == 'string' ) {
            		        jso = new String ( result );
            		    } else if ( typeof ( result ) == 'number' ) {
            		        jso = new Number ( result );
            		    } 
                        app.@com.gwittit.client.facebook.FacebookApi::callbackSuccess(Lcom/google/gwt/user/client/rpc/AsyncCallback;Lcom/google/gwt/core/client/JavaScriptObject;)(callback,jso);
        		    }
        		}
        	);
        });
    }-*/;

    /*
     * Callback
     */
    protected void callbackError(AsyncCallback<JSONValue> callback, JavaScriptObject jso) {
        ErrorResponse er = jso.cast ();
        callback.onFailure ( new FacebookException ( er ) );
    }

    /*
     * Called when method succeeded.
     */
    protected void callbackSuccess(AsyncCallback<JavaScriptObject> callback, JavaScriptObject obj) {
        GWT.log ( "FacebookApi: callbackSuccess " + new JSONObject ( obj ), null );
        callback.onSuccess ( obj );
    }

}

package com.gwittit.client.facebook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwittit.client.facebook.entities.Album;
import com.gwittit.client.facebook.entities.Comment;
import com.gwittit.client.facebook.entities.FriendInfo;
import com.gwittit.client.facebook.entities.FriendList;
import com.gwittit.client.facebook.entities.Photo;
import com.gwittit.client.facebook.entities.Stream;
import com.gwittit.client.facebook.entities.StreamFilter;
import com.gwittit.client.facebook.ui.FriendListWidget;

/**
 * The class contains function defining the Facebook API. With the API, you can
 * add social context to your application by utilizing profile, friend, Page,
 * group, photo, and event data.
 * 
 * 
 * @see http://wiki.developers.facebook.com/index.php/API Facebook API
 * 
 * @author ola
 */
public class FacebookApi {

	public enum Permission {
		read_stream, publish_stream
	};

	private String apiKey;

	/**
	 * Creates a new api
	 * 
	 * @param apiKey
	 */
	protected FacebookApi(String apiKey) {
		this.apiKey = apiKey;
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
	 * @see com.gwittit.client.facebook.entities.Stream Stream
	 * @see http://wiki.developers.facebook.com/index.php/Stream.get Stream.get
	 * @see http://wiki.developers.facebook.com/index.php/Stream_%28FQL%29
	 *      Stream Table
	 */
	public void stream_get(Map<String, String> params, final AsyncCallback<List<Stream>> ac) {

		final String lp = "FacebookApiImpl#stream_get:";
		GWT.log(lp + " called", null);

		JSONObject p = getDefaultParams();

		copyAllParams(p, params,
				"viewer_id,source_ids,start_time,end_time,limit,filter_key,metadata");

		// Create native callback and parse response.
		final AsyncCallback<JSONValue> c = new AsyncCallback<JSONValue>() {

			public void onSuccess(JSONValue jv) {
				GWT.log(FacebookApi.class + ": stream.get got response", null);
				List<Stream> result = new ArrayList<Stream>();

				JSONValue value = jv.isObject().get("posts");
				JSONArray array = value.isArray();

				for (int i = 0; array != null && i < array.size(); i++) {
					JSONValue v = array.get(i);
					JSONObject o = v.isObject();
					Stream stream = new Stream(o);
					result.add(stream);
				}
				GWT.log(FacebookApi.class + ": result size = " + result.size(), null);
				ac.onSuccess(result);
			}

			public void onFailure(Throwable caught) {
				ac.onFailure(null);
			}

		};

		callMethod("stream.get", p.getJavaScriptObject(), c);
	}

	/**
	 * Checks whether the user has opted in to an extended application
	 * permission.
	 * 
	 * For non-desktop applications, you may pass the ID of the user on whose
	 * behalf you're making this call. If you don't specify a user with the uid
	 * parameter but you do specify a session_key, then that user whose session
	 * it is will be the target of the call.
	 * 
	 * However, if your application is a desktop application, you must pass a
	 * valid session key for security reasons. Passing a uid parameter will
	 * result in an error.
	 * 
	 * required
	 * 
	 * @param api_key
	 *            string The application key associated with the calling
	 *            application. If you specify the API key in your client, you
	 *            don't need to pass it with every call.
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
	 */
	public void users_hasAppPermission(Permission permission, final AsyncCallback<Boolean> callback) {

		GWT.log("users_hasAppPermission: " + permission.toString(), null);

		JSONObject p = getDefaultParams();
		p.put("ext_perm", new JSONString(permission.toString()));

		AsyncCallback<JSONValue> nativeCallback = new AsyncCallback<JSONValue>() {
			public void onFailure(Throwable t) {
				callback.onFailure(t);
			}

			public void onSuccess(JSONValue jv) {
				JSONString result = jv.isObject().get("result").isString();
				callback.onSuccess("1".equals(result.isString().stringValue()));
			}
		};
		callMethod("users.hasAppPermission", p.getJavaScriptObject(), nativeCallback);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwittit.client.facebook.FacebookApi#fql_query(java.lang.String,
	 * com.gwittit.client.facebook.FacebookCallback)
	 */
	public void fql_query(String fql, AsyncCallback<JSONValue> callback) {

		Map<String, String> params = new HashMap();
		params.put("query", fql);
		JSONObject p = getDefaultParams();
		copyParams(p, params, "query");
		callMethod("fql.query", p.getJavaScriptObject(), callback);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gwittit.client.facebook.FacebookApi#photos_getAlbums(java.util.Map,
	 * com.google.gwt.user.client.rpc.AsyncCallback)
	 */
	public void photos_getAlbums(Map<String, String> params,
			final AsyncCallback<List<Album>> callback) {
		JSONObject p = getDefaultParams();
		copyAllParams(p, params, "uid,aids");

		// Create javascript native callback and parse response
		AsyncCallback<JSONValue> nativeCallback = new AsyncCallback<JSONValue>() {

			public void onFailure(Throwable t) {
				callback.onFailure(t);
			}

			public void onSuccess(JSONValue jv) {
				List<Album> albums = new ArrayList();

				int key = 0;

				JSONObject o = jv.isObject();
				JSONValue value;

				while ((value = o.get(key + "")) != null) {
					final Album album = Album.newInstance(value.isObject());
					albums.add(album);
					key++;
				}

				callback.onSuccess(albums);
			}

		};

		callMethod("photos.getAlbums", p.getJavaScriptObject(), nativeCallback);
	}

	/**
	 * Returns all visible photos according to the filters specified. You can
	 * use this method to find all photos that are:
	 * 
	 * Tagged with the specified subject (passing the user's uid as the subj_id)
	 * Contained within the album specified by aid Included in the list of
	 * photos specified by pids Any combination of these three criteria
	 * 
	 * @param subj_id
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
	 * @param params
	 * @param callback
	 */
	public void photos_get(final Map<String, String> params,
			final AsyncCallback<List<Photo>> callback) {
		JSONObject obj = getDefaultParams();
		copyAllParams(obj, params, "subj_id,aid,pids");

		AsyncCallback<JSONValue> a = new AsyncCallback<JSONValue>() {

			public void onFailure(Throwable caught) {
				callback.onFailure(caught);
			}

			public void onSuccess(JSONValue result) {
				List<Photo> photos = new ArrayList<Photo>();
				for (JSONValue v : parse(result)) {
					photos.add(new Photo(v.isObject()));
				}

				callback.onSuccess(photos);
			}

		};

		callMethod("photos.get", obj.getJavaScriptObject(), a);
	}

	public void admin_banUsers(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void admin_unbanUsers(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void admin_getAllocation(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void admin_getAppProperties(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void admin_getBannedUsers(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void admin_getMetrics(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void admin_getRestrictionInfo(Map<String, String> params,
			AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void admin_setAppProperties(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void admin_setRestrictionInfo(Map<String, String> params,
			AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void application_getPublicInfo(Map<String, String> params,
			AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void auth_createToken(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void auth_expireSession(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void auth_getSession(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void auth_promoteSession(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void auth_revokeAuthorization(Map<String, String> params,
			AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void auth_revokeExtendedPermission(Map<String, String> params,
			AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void batch_run(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	/**
	 * This method adds a comment to an xid on behalf of a user. This
	 * essentially works like stream.addComment and allows addition of comments
	 * to an application's fb:comment and Comments Boxes.
	 * 
	 * Desktop applications must pass a valid session key, and only the user
	 * associated with that session key can add comments.
	 * 
	 * In order for your application to publish a feed story associated with a
	 * comment, that user must grant your application the publish_stream
	 * extended permission.
	 * 
	 * required
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
	 * @param session_key
	 *            string The session key of the logged in user. The session key
	 *            is automatically included by our PHP client. Desktop and
	 *            Javascript Client Library applications must pass a valid
	 *            session key.
	 * 
	 * 
	 */
	public void comments_add(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		JSONObject p = getDefaultParams();
		copyAllParams(p, params, "*xid,text,uid,title,url,publish_to_stream,session_key");
		callMethod("comments.add", p.getJavaScriptObject(), callback);
	}

	/**
	 * Returns all comments for a given XID posted through fb:comments or the
	 * Comments Box (which is created with the fb:comments (XFBML) tag). This
	 * method is a wrapper for the FQL query on the comment FQL table.
	 * 
	 * You can specify only one XID with this call. If you want to retrieve
	 * comments for multiple XIDs, run fql.query against the comment FQL table.
	 * 
	 * @param xid
	 *            int The comment xid that you want to retrieve. For a Comments
	 *            Box, you can determine the xid on the admin panel or in the
	 *            application settings editor in the Facebook Developer
	 *            application.
	 * @param callback
	 */
	public void comments_get(Map<String, String> params, final AsyncCallback<List<Comment>> callback) {
		JSONObject p = getDefaultParams();
		// copyAllParams(p, params, "*xid");

		AsyncCallback<JSONValue> internCallback = new AsyncCallback<JSONValue>() {

			public void onFailure(Throwable caught) {
				callback.onFailure(caught);
			}

			public void onSuccess(JSONValue result) {
				List<Comment> resultList = new ArrayList<Comment>();
				for (JSONValue v : parse(result)) {
					resultList.add(new Comment(v.isObject()));
				}
				callback.onSuccess(resultList);
			}

		};
		// Possible facebook bug
		// callMethod("comments.get", p.getJavaScriptObject(), internCallback);
		String fql = "select xid, text,fromid,time,id,username,reply_xid from comment where xid ='"
				+ params.get("xid") + "'";
		fql_query(fql, internCallback);

	}

	/**
	 * This method removes a comment from an xid on behalf of a user (or not).
	 * 
	 * Desktop applications must pass a valid session key, and only comments
	 * made by the user can be removed by that user. When using the app secret,
	 * an application may remove any of its comments. required
	 * 
	 * @param xid
	 *            string The xid of a particular Comments Box or fb:comments.
	 * @param comment_id
	 *            string The comment_id, as returned by Comments.add or
	 *            Comments.get, to be removed.
	 */
	public void comments_remove(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		JSONObject p = getDefaultParams();
		
		copyAllParams(p, params, "*xid,*comment_id");
		callMethod ( "comments.remove", p.getJavaScriptObject(), callback );
	}

	public void connect_getUnconnectedFriendsCount(Map<String, String> params,
			AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void connect_registerUsers(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void connect_unregisterUsers(Map<String, String> params,
			AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void data_getCookies(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void data_setCookie(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void events_cancel(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void events_create(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void events_edit(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void events_get(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void events_getMembers(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void events_rsvp(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void fbml_deleteCustomTags(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void fbml_getCustomTags(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void fbml_refreshImgSrc(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void fbml_refreshRefUrl(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void fbml_registerCustomTags(Map<String, String> params,
			AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void fbml_setRefHandle(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void feed_deactivateTemplateBundleByID(Map<String, String> params,
			AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void feed_getRegisteredTemplateBundleByID(Map<String, String> params,
			AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void feed_getRegisteredTemplateBundles(Map<String, String> params,
			AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void feed_publishTemplatizedAction(Map<String, String> params,
			AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void feed_publishUserAction(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void feed_registerTemplateBundle(Map<String, String> params,
			AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void fql_multiquery(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

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
	 */
	public void friends_areFriends(Map<String, String> params,
			final AsyncCallback<List<FriendInfo>> callback) {

		JSONObject p = getDefaultParams();
		copyAllParams(p, params, "*uids1,*uids2");

		AsyncCallback<JSONValue> a = new AsyncCallback<JSONValue>() {

			public void onFailure(Throwable caught) {
				Window.alert("Failed: " + caught);
			}

			public void onSuccess(JSONValue jsonResult) {
				List<FriendInfo> result = new ArrayList<FriendInfo>();
				for (JSONValue v : parse(jsonResult)) {
					result.add(new FriendInfo(v));
				}
				callback.onSuccess(result);
			}
		};
		callMethod("friends.areFriends", p.getJavaScriptObject(), a);
	}

	/**
	 * Returns the Facebook user IDs of the current user's Facebook friends. The
	 * current user is determined from the session_key parameter. The values
	 * returned from this call are not storable.
	 * 
	 * You can call this method without a session key to return a list of
	 * friends of a user on your application's canvas page. The user must have
	 * authorized your application in order to make this call without a session
	 * key. This is similar to how Facebook passes the UIDs of friends of a user
	 * on your application's canvas page.
	 * 
	 * @param params
	 *            consists of required
	 * @param api_key
	 *            string The application key associated with the calling
	 *            application. If you specify the API key in your client, you
	 *            don't need to pass it with every call.
	 * @param call_id
	 *            float The request's sequence number. Each successive call for
	 *            any session must use a sequence number greater than the last.
	 *            We suggest using the current time in milliseconds, such as
	 *            PHP's microtime(true) function. If you specify the call ID in
	 *            your client, you don't need to pass it with every call.
	 * 
	 * @param session_key
	 *            string The session key of the logged in user. The session key
	 *            is automatically included by our PHP client.
	 * @param format
	 *            string The desired response format, which can be either XML or
	 *            JSON. (Default value is XML.)
	 * @param callback
	 *            (not tested) string Name of a function to call. This is
	 *            primarily to enable cross-domain JavaScript requests using the
	 *            <script> tag, also known as JSONP, and works with both the XML
	 *            and JSON formats. The function will be called with the
	 *            response passed as the parameter.
	 * @param flid
	 *            int Returns the friends in a friend list.
	 * @param uid
	 *            int The user ID for the user whose friends you want to return.
	 *            Specify the uid when calling this method without a session
	 *            key.
	 */
	public void friends_get(Map<String, String> params, final AsyncCallback<List<Long>> callback) {
		JSONObject p = getDefaultParams();
		friends_getGeneric("friends.get", p.getJavaScriptObject(), callback);
	}

	/**
	 * Returns the user IDs of the current user's Facebook friends who have
	 * authorized the specific calling application or who have already connected
	 * their Facebook accounts via Facebook Connect. The current user is
	 * determined from the session_key parameter. The values returned from this
	 * call are not storable.
	 * 
	 * required
	 * 
	 * @param api_key
	 *            string The application key associated with the calling
	 *            application. If you specify the API key in your client, you
	 *            don't need to pass it with every call.
	 * @param session_key
	 *            string The session key of the logged in user. The session key
	 *            is automatically included by our PHP client.
	 * @param call_id
	 *            float The request's sequence number. Each successive call for
	 *            any session must use a sequence number greater than the last.
	 *            We suggest using the current time in milliseconds, such as
	 *            PHP's microtime(true) function. If you specify the call ID in
	 *            your client, you don't need to pass it with every call.
	 * @param sig
	 *            string An MD5 hash of the current request and your secret key,
	 *            as described in the How Facebook Authenticates Your
	 *            Application. Facebook computes the signature for you
	 *            automatically.
	 * @param v
	 *            string This must be set to 1.0 to use this version of the API.
	 *            If you specify the version in your client, you don't need to
	 *            pass it with every call. optional
	 * @param format
	 *            string The desired response format, which can be either XML or
	 *            JSON. (Default value is XML.)
	 * @param callback
	 *            string Name of a function to call. This is primarily to enable
	 *            cross-domain JavaScript requests using the <script> tag, also
	 *            known as JSONP, and works with both the XML and JSON formats.
	 *            The function will be called with the response passed as the
	 *            parameter.
	 */
	public void friends_getAppUsers(Map<String, String> params, AsyncCallback<List<Long>> callback) {
		JSONObject p = getDefaultParams();
		friends_getGeneric("friends.getAppUsers", p.getJavaScriptObject(), callback);
	}

	/**
	 * Returns the names and identifiers of any friend lists that the user has
	 * created. The current user is determined from the session_key parameter.
	 * 
	 * The values returned from this call are storable. You can store the ID of
	 * a friend list that the user has elected for use in some feature of your
	 * application, but you should verify the ID periodically, as users may
	 * delete or modify lists at any time. Friend lists are private on Facebook,
	 * so you cannot republish this information to anyone other than the logged
	 * in user. Members of lists may be obtained using friends.get with an flid
	 * parameter.
	 * 
	 * @param params
	 * @param callback
	 */
	public void friends_getLists(Map<String, String> params,
			final AsyncCallback<List<FriendList>> callback) {
		JSONObject p = getDefaultParams();

		AsyncCallback<JSONValue> a = new AsyncCallback<JSONValue>() {
			public void onFailure(Throwable caught) {

			}

			public void onSuccess(JSONValue result) {
				List<FriendList> returnList = new ArrayList<FriendList>();
				for (JSONValue v : parse(result)) {
					returnList.add(new FriendList(v));
				}
				callback.onSuccess(returnList);
			}

		};

		callMethod("friends.getLists", p.getJavaScriptObject(), a);

	}

	/**
	 * Returns the Facebook user IDs of the mutual friends between the source
	 * user and target user. For the source user, you can either specify the
	 * source's user ID (the source_id) or use the session key of the logged-in
	 * user, but not specify both.
	 * 
	 * The source user must have authorized your application.
	 * 
	 * You cannot store the IDs that get returned from this call.
	 * 
	 * Privacy applies to the results of this method: If the source user chooses
	 * to not show friends on his or her public profile, then no mutual friends
	 * get returned. If a mutual friend chooses to be hidden from search
	 * results, then that user's UID does not get returned from this call.
	 * 
	 * required
	 * 
	 * 
	 * @param target_uid
	 *            int The user ID of one of the target user whose mutual friends
	 *            you want to retrieve. optional
	 * @param session_key
	 *            string The session key of the logged in user. The session key
	 *            is automatically included by our PHP client. If you don't pass
	 *            a session key, then you must pass a source_id. Desktop
	 *            applications must always include a session_key
	 * @param callback
	 *            string Name of a function to call. This is primarily to enable
	 *            cross-domain JavaScript requests using the <script> tag, also
	 *            known as JSONP, and works with both the XML and JSON formats.
	 *            The function will be called with the response passed as the
	 *            parameter.
	 * @param source_uid
	 *            int The user ID of the other user for which you are getting
	 *            mutual friends of. Defaults to the current session user.
	 *            Specify the source_uid when calling this method without a
	 *            session key.
	 * @param dummy
	 */
	public void friends_getMutualFriends(Map<String, String> params,
			AsyncCallback<List<Long>> callback) {
		JSONObject p = getDefaultParams();
		copyAllParams(p, params, "session_key,target_uid,source_uid");
		friends_getGeneric("friends.getMutualFriends", p.getJavaScriptObject(), callback);

	}

	/**
	 * Method that parses long's from the response.
	 * 
	 * @param method
	 * @param params
	 * @param callback
	 */
	private void friends_getGeneric(String method, JavaScriptObject params,
			final AsyncCallback<List<Long>> callback) {
		AsyncCallback<JSONValue> ac = new AsyncCallback<JSONValue>() {

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			public void onSuccess(JSONValue jv) {
				List<Long> result = new ArrayList<Long>();

				JSONObject o = jv.isObject();
				JSONValue value;
				int key = 0;

				while ((value = o.get(key + "")) != null) {
					result.add(new Long(value.isNumber() + ""));
					key++;
				}
				callback.onSuccess(result);
			}

		};

		callMethod(method, params, ac);

	}

	public void groups_get(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void groups_getMembers(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void intl_getTranslations(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void intl_uploadNativeStrings(Map<String, String> params,
			AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void links_get(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void links_post(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void liveMessage_send(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void message_getThreadsInFolder(Map<String, String> params,
			AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void notes_create(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void notes_delete(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void notes_edit(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void notes_get(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void notifications_get(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void notifications_getList(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void notifications_markRead(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void notifications_send(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void notifications_sendEmail(Map<String, String> params,
			AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void pages_getInfo(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void pages_isAdmin(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void pages_isAppAdded(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void pages_isFan(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void photos_addTag(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	/**
	 * Creates and returns a new album owned by the specified user or the
	 * current session user. See photo uploads for a description of the upload
	 * workflow. The only storable values returned from this call are aid and
	 * owner. No relationships between them are storable.
	 * 
	 * For Web applications, you must pass either the ID of the user on whose
	 * behalf you're making this call or the session key for that user, but not
	 * both. If you don't specify a user with the uid parameter, then that user
	 * whose session it is will be the target of the call.
	 * 
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
	 * @param uid
	 *            int The user ID of the user for whom you are creating the
	 *            album. If this parameter is not specified, then it defaults to
	 *            the session user. Note: This parameter applies only to Web
	 *            applications and is required by them only if the session_key
	 *            is not specified. Facebook ignores this parameter if it is
	 *            passed by a desktop application.
	 */
	public void photos_createAlbum(Map<String, String> params, final AsyncCallback<Photo> callback) {
		JSONObject p = getDefaultParams();
		copyAllParams(p, params, "name,location,description,visible,uid");

		AsyncCallback<JSONValue> internCallback = new AsyncCallback<JSONValue>() {

			public void onFailure(Throwable caught) {
				callback.onFailure(caught);
			}

			public void onSuccess(JSONValue result) {
				Photo p = new Photo(result.isObject());
				callback.onSuccess(p);
			}
		};
		callMethod("photos.createAlbum", p.getJavaScriptObject(), internCallback);
	}

	public void photos_getTags(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void photos_upload(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void profile_getFBML(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void profile_getInfo(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void profile_getInfoOptions(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void profile_setFBML(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void profile_setInfo(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void profile_setInfoOptions(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void sms_canSend(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void sms_send(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	/**
	 * Updates a user's Facebook status through your application. Before your
	 * application can set a user's status, the user must grant it the
	 * status_update extended permission. This call is a streamlined version of
	 * users.setStatus, as it takes fewer arguments.
	 * 
	 * For Web applications, you must pass either the ID of the user on whose
	 * behalf you're making this call or the session key for that user, but not
	 * both. If you don't specify a user with the uid parameter, then that user
	 * whose session it is will be the target of the call. To set the status of
	 * a facebook Page, pass the uid of the Page.
	 * 
	 * However, if your application is a desktop application, you must pass a
	 * valid session key for security reasons. Do not pass a uid parameter.
	 * 
	 * required
	 * 
	 * @param api_key
	 *            string The application key associated with the calling
	 *            application. If you specify the API key in your client, you
	 *            don't need to pass it with every call.
	 * @param call_id
	 *            NOT SUPPORTED float The request's sequence number. Each
	 *            successive call for any session must use a sequence number
	 *            greater than the last. We suggest using the current time in
	 *            milliseconds, such as PHP's microtime(true) function. If you
	 *            specify the call ID in your client, you don't need to pass it
	 *            with every call.
	 * @param sig
	 *            NOT SUPPORTED string An MD5 hash of the current request and
	 *            your secret key, as described in the How Facebook
	 *            Authenticates Your Application. Facebook computes the
	 *            signature for you automatically.
	 * @param v
	 *            NOT SUPPORTED string This must be set to 1.0 to use this
	 *            version of the API. If you specify the version in your client,
	 *            you don't need to pass it with every call. optional
	 * @param session_key
	 *            NOT SUPPORTED string The session key of the user whose status
	 *            you are setting. Note: A session key is always required for
	 *            desktop applications. It is required for Web applications only
	 *            when the uid is not specified.
	 * @param format
	 *            NOT SUPPORTED string The desired response format, which can be
	 *            either XML or JSON. (Default value is XML.)
	 * @param callback
	 *            NOT SUPPORTED string Name of a function to call. This is
	 *            primarily to enable cross-domain JavaScript requests using the
	 *            <script> tag, also known as JSONP, and works with both the XML
	 *            and JSON formats. The function will be called with the
	 *            response passed as the parameter.
	 * @param status
	 *            string The status message to set. Note: The maximum message
	 *            length is 255 characters; messages longer than that limit will
	 *            be truncated and appended with "...".
	 * @param uid
	 *            int The user ID of the user whose status you are setting. If
	 *            this parameter is not specified, then it defaults to the
	 *            session user. Note: This parameter applies only to Web
	 *            applications and is required by them only if the session_key
	 *            is not specified. Facebook ignores this parameter if it is
	 *            passed by a desktop application.
	 */
	public void status_set(Map<String, String> params, AsyncCallback<JSONValue> c) {

		JSONObject p = getDefaultParams();
		copyAllParams(p, params, "*status,uid");
		callMethod("users.setStatus", p.getJavaScriptObject(), c);
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
	 * @param api_key
	 *            string The application key associated with the calling
	 *            application. If you specify the API key in your client, you
	 *            don't need to pass it with every call.
	 * @param call_id
	 *            NOT SUPPORTED float The request's sequence number. Each
	 *            successive call for any session must use a sequence number
	 *            greater than the last. We suggest using the current time in
	 *            milliseconds, such as PHP's microtime(true) function. If you
	 *            specify the call ID in your client, you don't need to pass it
	 *            with every call.
	 * @param sig
	 *            NOT SUPPORTED string An MD5 hash of the current request and
	 *            your secret key, as described in the How Facebook
	 *            Authenticates Your Application. Facebook computes the
	 *            signature for you automatically.
	 * @param v
	 *            NOT SUPPORTED string This must be set to 1.0 to use this
	 *            version of the API. If you specify the version in your client,
	 *            you don't need to pass it with every call.
	 * @param session_key
	 *            NOT SUPPORTED string The session key of the logged in user.
	 *            The session key is automatically included by our PHP client.
	 *            optional
	 * @param format
	 *            NOT SUPPORTED string The desired response format, which can be
	 *            either XML or JSON. (Default value is XML.)
	 * @param callback
	 *            NOT SUPPORTED string Name of a function to call. This is
	 *            primarily to enable cross-domain JavaScript requests using the
	 *            <script> tag, also known as JSONP, and works with both the XML
	 *            and JSON formats. The function will be called with the
	 *            response passed as the parameter.
	 * @param uid
	 *            int The user ID of the user whose status messages you want to
	 *            retrieve.
	 * @param limit
	 *            NOT SUPPORTED int The number of status messages you want to
	 *            return. (Default value is 100.)
	 */
	public void status_get(Map<String, String> params, AsyncCallback<JSONValue> callback) {

		String uid = params.get("uid");

		if (uid == null) {
			Window.alert("Error: status_get called without uid");
			throw new IllegalArgumentException("status_get called without uid");
		}

		/**
		 * Cant get this to work, its in beta so wont use much time on it
		 * JSONObject params = getDefaultParams (); params.put( "uid", new
		 * JSONString ( uid ) ) ; callMethod ( "status.get",
		 * params.getJavaScriptObject(), callback );
		 */
		fql_query("SELECT message FROM status WHERE uid=" + uid + " LIMIT 1", callback);
	}

	/**
	 * This method adds a comment to a post that was already published to a
	 * user's Wall.
	 * 
	 * Privacy rules apply to the posts to which the user can add comments; the
	 * user must be able to see the post in order for your application to allow
	 * the user add a comment to it.
	 * 
	 * Desktop applications must pass a valid session key, and only the user
	 * associated with that session key can add comments. Other applications can
	 * allows users to add comments to any posts the user can see, provided you
	 * have a valid post_id.
	 * 
	 * In order for your application to allow a user to add a comment, that user
	 * must grant your application the publish_stream extended permission.
	 * 
	 * optional
	 * 
	 * @param session_key
	 *            string The session key of the logged in user. The session key
	 *            is automatically included by our PHP client. Desktop
	 *            applications must pass a valid session key (and have been
	 *            granted the publish_stream extended permission); other
	 *            applications need only the publish_stream extended permission.
	 * @param uid
	 *            string The user ID of the user adding the comment. If this
	 *            parameter is not specified, then it defaults to the session
	 *            user. Note: This parameter applies only to Web applications
	 *            and is required by them only if the session_key is not
	 *            specified. Facebook ignores this parameter if it is passed by
	 *            a desktop application.
	 * 
	 *            required
	 * @param post_id
	 *            string The ID for the post to which you're adding the comment.
	 * @param comment
	 *            string The text of the comment. This is a plain text parameter
	 *            only; you cannot format the comment with HTML or FBML.
	 */
	public void stream_addComment(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		GWT.log("FacbookApi: call method stream.addComment", null);
		JSONObject p = getDefaultParams();

		copyAllParams(p, params, "uid,*post_id,*comment");
		callMethod("stream.addComment", p.getJavaScriptObject(), callback, "string");

		GWT.log("FacebookApi: call method stream.addComment DONE", null);
	}

	/**
	 * Valid params are
	 * 
	 * 
	 * optional session_ke string The session key of the logged in user. The
	 * session key is automatically included by our PHP client. Desktop
	 * applications must pass a valid session key (and have been granted the
	 * publish_stream extended permission); other applications need only the
	 * publish_stream extended permission.
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
	 */
	public void stream_addLike(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		stream_addOrRemoveLike(params, true, callback);
		// TODO Auto-generated method stub

	}

	/**
	 * This method returns all comments associated with a post in a user's
	 * stream. This method returns comments only if the user who owns the post
	 * (that is, the user published the post to his or her profile) has
	 * authorized your application.
	 * 
	 * This method is a wrapper for comment FQL table, indexed by post_id rather
	 * than xid.
	 */
	public void stream_getComments(final Map<String, String> params,
			final AsyncCallback<List<Comment>> callback) {
		JSONObject p = getDefaultParams();
		copyAllParams(p, params, "session_key,*post_id");

		AsyncCallback<JSONValue> nativeCallback = new AsyncCallback<JSONValue>() {

			public void onFailure(Throwable v) {
				callback.onFailure(v);
			}

			public void onSuccess(JSONValue v) {

				final List<Comment> returnList = new ArrayList<Comment>();
				JSONObject o = v.isObject();

				if (o == null) {
					callback.onSuccess(returnList);
				} else {
					JSONObject jo = v.isObject();
					JSONValue value = null;

					for (int i = 0; (value = jo.get("" + i)) != null; i++) {
						Comment comment = new Comment(value.isObject());
						returnList.add(comment);

					}
					callback.onSuccess(returnList);
				}
			}
		};
		callMethod("stream.getComments", p.getJavaScriptObject(), nativeCallback);
	}

	/**
	 * This method returns any filters associated with a user's home page
	 * stream. You can use these filters to narrow down a stream you want to
	 * return with stream.get or when you query the stream FQL table.
	 * 
	 * optional
	 * 
	 * @param uid
	 *            int The user ID for the user whose stream filters you are
	 *            returning. Note: This parameter applies only to Web
	 *            applications and is required by them only if the session_key
	 *            is not specified. Facebook ignores this parameter if it is
	 *            passed by a desktop application.
	 * @param session_key
	 *            string The session key of the logged in user. The session key
	 *            is automatically included by our PHP client. Web applications
	 *            don't need an active session to make this call as long as you
	 *            pass a user ID and that user has an active session with your
	 *            application. Desktop applications must always pass a session
	 *            key.
	 * @see com.gwittit.client.facebook.entities.StreamFilter StreamFilter
	 */
	public void stream_getFilters(final Map<String, String> params,
			final AsyncCallback<List<StreamFilter>> callback) {
		JSONObject p = getDefaultParams();
		copyAllParams(p, params, "uid,session_key");

		AsyncCallback<JSONValue> internCallback = new AsyncCallback<JSONValue>() {

			public void onFailure(Throwable caught) {
				callback.onFailure(caught);
			}

			public void onSuccess(JSONValue jv) {

				List<StreamFilter> result = new ArrayList<StreamFilter>();

				int key = 0;
				JSONObject o = jv.isObject();
				JSONValue value;

				while ((value = o.get(key + "")) != null) {

					StreamFilter sf = new StreamFilter(value);
					result.add(sf);

					key++;
				}
				callback.onSuccess(result);
			}
		};

		callMethod("stream.getFilters", p.getJavaScriptObject(), internCallback);
	}

	public void stream_publish(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	/**
	 * This method removes a post from a user's Wall. The post also gets removed
	 * from the user's and the user's friends' News Feeds.
	 * 
	 * Your application may only remove posts that were created through it.
	 * 
	 * Desktop applications must pass a valid session key, and can remove posts
	 * only from the user associated with that session key. Other applications
	 * can delete any post that they published, provided you have a valid
	 * post_id. Web applications must pass either a valid session key or a user
	 * ID.
	 * 
	 * In order to remove a post from a user's Wall, the user must grant your
	 * application the publish_stream extended permission.
	 * 
	 * Parameters Required Name Type Description optional
	 * 
	 * @param session_key
	 *            string The session key of the logged in user. The session key
	 *            is automatically included by our PHP client. Desktop
	 *            applications must pass a valid session key (and have been
	 *            granted the publish_stream extended permission); other
	 *            applications need only the publish_stream extended permission.
	 *            required
	 * @param post_id
	 *            string The ID for the post you want to remove.
	 * 
	 *            optional
	 * @param uid
	 *            string The user ID of the user publishing the post. If this
	 *            parameter is not specified, then it defaults to the session
	 *            user. Note: This parameter applies only to Web applications
	 *            and is required by them only if the session_key is not
	 *            specified. Facebook ignores this parameter if it is passed by
	 *            a desktop application.
	 */
	public void stream_remove(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		JSONObject p = getDefaultParams();
		copyAllParams(p, params, "session_key,uid,*post_id");
		callMethod("stream.remove", p.getJavaScriptObject(), callback);

		// TODO Auto-generated method stub

	}

	/**
	 * This method removes a comment from a post.
	 * 
	 * Privacy rules apply to the posts from which the user can delete comments;
	 * if the post is on the user's Wall, any comment can be deleted. If the
	 * post is on a friend's Wall, only comments made by the user can be
	 * deleted.
	 * 
	 * Desktop applications must pass a valid session key, and can remove
	 * comments made by the user associated with that session key only. Other
	 * applications can delete any comments, provided you have a valid post_id.
	 * 
	 * In order to remove a comment, the user must grant your application the
	 * publish_stream extended permission.
	 * 
	 * optional
	 * 
	 * @param session_key
	 *            string The session key of the logged in user. The session key
	 *            is automatically included by our PHP client. Desktop
	 *            applications must pass a valid session key (and have been
	 *            granted the publish_stream extended permission); other
	 *            applications need only the publish_stream extended permission.
	 * @param uid
	 *            string The user ID of the user who made the comment. If this
	 *            parameter is not specified, then it defaults to the session
	 *            user. Note: This parameter applies only to Web applications
	 *            and is required by them only if the session_key is not
	 *            specified. Facebook ignores this parameter if it is passed by
	 *            a desktop application. required
	 * 
	 * @param comment_id
	 *            string The ID for the comment you want to remove.
	 */
	public void stream_removeComment(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		JSONObject obj = getDefaultParams();
		copyAllParams(obj, params, "session_key,uid,*comment_id");
		callMethod("stream.removeComment", obj.getJavaScriptObject(), callback);
	}

	/**
	 * Remove like
	 */
	public void stream_removeLike(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		stream_addOrRemoveLike(params, false, callback);
	}

	private void stream_addOrRemoveLike(Map<String, String> params, boolean add,
			AsyncCallback<JSONValue> callback) {
		JSONObject p = getDefaultParams();
		copyAllParams(p, params, "session_key,*post_id");
		callMethod(add ? "stream.addLike" : "stream.removeLike", p.getJavaScriptObject(), callback);
	}

	public void users_getInfo(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void users_getLoggedInUser(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void users_getStandardInfo(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void users_isAppUser(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void users_isVerified(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void users_setStatus(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void video_getUploadLimits(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void video_upload(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	/**
	 * Takes a list of params and converts them to json string. If the param
	 * starts withh "*' its considered a required param. The use will get a
	 * warning if its not passed.
	 * 
	 * @param obj
	 * @param params
	 * @param list
	 */
	private void copyAllParams(JSONObject obj, Map<String, String> params, String list) {

		if (params == null) {
			return;
		}
		String errorString = "";

		String[] keys = list.split(",");

		for (String k : keys) {

			String useKey = k;

			if (useKey.startsWith("*")) {
				useKey = useKey.replace("*", "");

				if (!params.containsKey(useKey)) {
					errorString += "The param " + useKey + " is required.\n";
				}
			}
			copyParams(obj, params, useKey);
		}

		if (!params.isEmpty()) {
			errorString += "You passed invalid parameters: " + params.toString();
		}

		if (!"".equals(errorString)) {
			errorString = "Oups, You passed invalid parameters to a method. \n\n" + errorString;
			Window.alert(errorString);
			throw new RuntimeException(errorString);
		}
	}

	/*
	 * @deprecated
	 */
	private void copyParams(JSONObject obj, Map<String, String> params, String key) {
		if (params.get(key) != null) {
			obj.put(key, new JSONString(params.get(key)));
			params.remove(key);
		}
	}

	/*
	 * Run facebook method, parse result and call callback function.
	 */

	private void callMethod(String method, JavaScriptObject params, AsyncCallback callback) {
		callMethod(method, params, callback, "json");
	}

	private native void callMethod(String method, JavaScriptObject params, AsyncCallback callback,
			String returnType)/*-{
		var app=this;
		$wnd.FB_RequireFeatures(["Api"], function(){			
			$wnd.FB.Facebook.apiClient.callMethod( method, params, 
				function(result, exception){
						// this is the result when we run in hosted mode for some reason
					if(!isNaN(result)) {
						app.@com.gwittit.client.facebook.FacebookApi::callbackSuccessNumber(Lcom/google/gwt/user/client/rpc/AsyncCallback;Ljava/lang/String;)(callback,result+"");
					} else {
						if ( result != undefined ) {
						   if ( returnType == "string" ) {
								app.@com.gwittit.client.facebook.FacebookApi::callbackSuccessString(Lcom/google/gwt/user/client/rpc/AsyncCallback;Ljava/lang/String;)(callback,result);
						   } else {
								app.@com.gwittit.client.facebook.FacebookApi::callbackSuccess(Lcom/google/gwt/user/client/rpc/AsyncCallback;Lcom/google/gwt/core/client/JavaScriptObject;)(callback,result);
						   }
						} else {
							app.@com.gwittit.client.facebook.FacebookApi::callbackError(Lcom/google/gwt/user/client/rpc/AsyncCallback;Lcom/google/gwt/core/client/JavaScriptObject;)(callback,exception);
						}
					}
				}
			);
		});
	}-*/;

	/**
	 * Parse json result
	 * 
	 * @param result
	 * @return
	 */
	private List<JSONValue> parse(JSONValue result) {

		List<JSONValue> returnList = new ArrayList<JSONValue>();

		JSONObject o = result.isObject();
		JSONValue value;
		int key = 0;

		while ((value = o.get(key + "")) != null) {
			returnList.add(value);
			key++;
		}

		return returnList;
	}

	/**
	 * Callbacks
	 */
	public void callbackError(AsyncCallback<JSONValue> callback, JavaScriptObject value) {
		callback.onFailure(new Exception("" + new JSONObject(value)));
	}

	/**
	 * Called when result is a number
	 */
	public void callbackSuccessNumber(AsyncCallback<JSONValue> callback, String i) {
		JSONObject o = new JSONObject();
		JSONString s = new JSONString(i);
		o.put("result", s);
		callback.onSuccess(o);
	}

	public void callbackSuccessString(AsyncCallback<JSONValue> callback, String s) {
		JSONString js = new JSONString(s);
		callback.onSuccess(js);
	}

	/**
	 * Called when method succeeded.
	 */
	public void callbackSuccess(AsyncCallback<JSONValue> callback, JavaScriptObject obj) {
		GWT.log("FacebookApi: callbackSuccess " + obj, null);
		callback.onSuccess(new JSONObject(obj));
	}

	/**
	 * Get default params, minimum is the api key
	 */
	private JSONObject getDefaultParams() {
		if (apiKey == null) {
			Window.alert("api_key==null");
		}
		JSONObject obj = new JSONObject();
		obj.put("api_key", new JSONString(apiKey));
		return obj;
	}

}

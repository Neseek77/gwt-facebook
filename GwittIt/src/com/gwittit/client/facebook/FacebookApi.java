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
import com.gwittit.client.facebook.entities.Stream;
import com.gwittit.client.facebook.entities.StreamFilter;

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

		copyAllParams(p, params,"viewer_id,source_ids,start_time,end_time,limit,filter_key,metadata");

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
				GWT.log( FacebookApi.class + ": result size = " + result.size(), null );
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
	 *            pass it with every call.
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
	 * @param format
	 *            string The desired response format, which can be either XML or
	 *            JSON. (Default value is XML.)
	 * @param callback
	 *            string Name of a function to call. This is primarily to enable
	 *            cross-domain JavaScript requests using the <script> tag, also
	 *            known as JSONP, and works with both the XML and JSON formats.
	 *            The function will be called with the response passed as the
	 *            parameter.
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwittit.client.facebook.FacebookApi#photos_get(java.util.Map,
	 * com.gwittit.client.facebook.FacebookCallback)
	 */
	public void photos_get(final Map<String, String> params, final AsyncCallback<JSONValue> callback) {
		JSONObject obj = getDefaultParams();
		copyAllParams(obj, params, "subj_id,aid,pids");
		callMethod("photos.get", obj.getJavaScriptObject(), callback);
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

	public void comments_add(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void comments_get(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void comments_remove(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

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

	public void friends_areFriends(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	/*
	 * 
	 */
	public void friends_get(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		JSONObject p = getDefaultParams();
		callMethod("friends.get", p.getJavaScriptObject(), callback);
	}

	public void friends_getAppUsers(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void friends_getLists(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

	}

	public void friends_getMutualFriends(Map<String, String> params,
			AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

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

	public void photos_createAlbum(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		// TODO Auto-generated method stub

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
	public void stream_getFilters(final Map<String, String> params, final AsyncCallback<List<StreamFilter>> callback) {
		JSONObject p = getDefaultParams();
		copyAllParams(p, params, "uid,session_key");
		
		AsyncCallback<JSONValue> internCallback = new AsyncCallback<JSONValue>() {

			public void onFailure(Throwable caught) {
				callback.onFailure(caught);
			}

			public void onSuccess(JSONValue jv) {

				List<StreamFilter> result = new ArrayList<StreamFilter> ();
				
				int key = 0;
				JSONObject o = jv.isObject();
				JSONValue value;

				while ((value = o.get(key + "")) != null) {
					
					StreamFilter sf = new StreamFilter ( value );
					result.add ( sf );
					
					key++;
				}
				callback.onSuccess( result );
			}
		};
		
		callMethod ("stream.getFilters", p.getJavaScriptObject(), internCallback );
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
	 *            a desktop application. 
	 * required
	 * 
	 * @param comment_id
	 *            string The ID for the comment you want to remove.
	 */
	public void stream_removeComment(Map<String, String> params, AsyncCallback<JSONValue> callback) {
		JSONObject obj = getDefaultParams();
		copyAllParams(obj, params, "session_key,uid,*comment_id" );
		callMethod ( "stream.removeComment", obj.getJavaScriptObject(), callback );
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

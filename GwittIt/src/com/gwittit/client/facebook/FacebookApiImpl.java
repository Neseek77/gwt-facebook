package com.gwittit.client.facebook;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

/**
 * GWT wrappers for facebook api.
 * 
 * 
 * @see http://wiki.developers.facebook.com/index.php/API Facebook API
 * 
 * @author ola marius sagli facebook: http://facebook.com/olams twitter:
 *         http://twitter.com/bananaskis
 * 
 */
public class FacebookApiImpl implements FacebookApi {

	// Callback used
	private FacebookCallback callback;

	// Api key used in every method call
	private String apiKey;

	protected FacebookApiImpl(String apiKey) {
		this.apiKey = apiKey;
	}

	private void put(JSONObject obj, Map<String, String> params, String key) {

		if (params.get(key) != null) {
			obj.put(key, new JSONString(params.get(key)));
		}
	}

	/*
	 * Run facebook method, parse result and call callback function.
	 */
	private native void callMethod(String method, JavaScriptObject params, FacebookCallback callback)/*-{
		var app=this;
		app.@com.gwittit.client.facebook.FacebookApiImpl::callback=callback;
		$wnd.FB_RequireFeatures(["Api"], function(){			
			$wnd.FB.Facebook.apiClient.callMethod( method, params, 

				function(result, exception){
						// this is the result when we run in hosted mode for some reason
					if(!isNaN(result)) {
						app.@com.gwittit.client.facebook.FacebookApiImpl::callbackSuccessNumber(Ljava/lang/String;)(result+"");
					} else {
						if ( result != undefined ) {
							app.@com.gwittit.client.facebook.FacebookApiImpl::callbackSuccess(Lcom/google/gwt/core/client/JavaScriptObject;)(result);
						} else {
							app.@com.gwittit.client.facebook.FacebookApiImpl::callbackError(Lcom/google/gwt/core/client/JavaScriptObject;)(exception);
						}
					}
				}
			);
		});
	}-*/;

	/**
	 * Callbacks
	 */
	public void callbackError(JavaScriptObject value) {
		callback.onError(new JSONObject(value));
	}

	/**
	 * Called when result is a number
	 */
	public void callbackSuccessNumber(String i) {
		JSONObject o = new JSONObject();
		JSONString s = new JSONString(i);
		o.put("result", s);
		callback.onSuccess(o);

	}

	/**
	 * Called when method succeeded.
	 */
	public void callbackSuccess(JavaScriptObject obj) {
		callback.onSuccess(new JSONObject(obj));
	}

	/**
	 * Get default params, minimum is the api key
	 */
	private JSONObject getDefaultParams() {
		JSONObject obj = new JSONObject();
		obj.put("api_key", new JSONString(apiKey));
		return obj;
	}

	// =============================== FACEBOOK METHODS ===================================

	public void stream_get(Map<String, String> params, FacebookCallback c) {
		JSONObject p = getDefaultParams();
		p.put("session_key", new JSONString(UserInfo.getSessionKey()));
		callMethod("stream.get", p.getJavaScriptObject(), c);
	}

	/**
	 * See facebook api
	 */
	public void users_hasAppPermission(String extPerm, FacebookCallback c) {
		JSONObject p = getDefaultParams();
		p.put("ext_perm", new JSONString(extPerm));
		callMethod("users.hasAppPermission", p.getJavaScriptObject(), c);
	}

	/**
	 * See facebook api
	 */
	public void status_set(String status, FacebookCallback c) {

		JSONObject p = getDefaultParams();
		p.put("status", new JSONString(status));
		p.put("uid", new JSONString(UserInfo.getUid()));
		callMethod("users.setStatus", p.getJavaScriptObject(), c);
	}

	public void status_get(String uid, FacebookCallback callback) {

		/**
		 * Cant get this to work, its in beta so wont use much time on it
		 * JSONObject params = getDefaultParams (); params.put( "uid", new
		 * JSONString ( uid ) ) ; callMethod ( "status.get",
		 * params.getJavaScriptObject(), callback );
		 */
		fql_query("SELECT message FROM status WHERE uid=" + uid + " LIMIT 1", callback);
	}

	public void fql_query(String fql, FacebookCallback callback) {

		Map<String, String> params = new HashMap();
		params.put("query", fql);
		JSONObject p = getDefaultParams();
		put(p, params, "query");
		callMethod("fql.query", p.getJavaScriptObject(), callback);
	}

	public void friends_get(FacebookCallback callback) {
		JSONObject p = getDefaultParams();
		callMethod("friends.get", p.getJavaScriptObject(), callback);
	}

	public void photos_getAlbums(Map<String, String> params, FacebookCallback callback) {
		JSONObject p = getDefaultParams();
		put(p, params, "uid");
		put(p, params, "aids");
		callMethod("photos.getAlbums", p.getJavaScriptObject(), callback);
	}

	public void photos_get(final Map<String, String> params, final FacebookCallback callback) {
		JSONObject obj = getDefaultParams();
		put(obj, params, "subj_id");
		put(obj, params, "aid");
		put(obj, params, "pids");
		callMethod("photos.get", obj.getJavaScriptObject(), callback);
	}

	public void admin_banUsers(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void admin_unbanUsers(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void admin_getAllocation(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void admin_getAppProperties(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void admin_getBannedUsers(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void admin_getMetrics(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void admin_getRestrictionInfo(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void admin_setAppProperties(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void admin_setRestrictionInfo(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void application_getPublicInfo(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void auth_createToken(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void auth_expireSession(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void auth_getSession(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void auth_promoteSession(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void auth_revokeAuthorization(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void auth_revokeExtendedPermission(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void batch_run(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void comments_add(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void comments_get(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void comments_remove(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void connect_getUnconnectedFriendsCount(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void connect_registerUsers(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void connect_unregisterUsers(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void data_getCookies(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void data_setCookie(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void events_cancel(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void events_create(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void events_edit(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void events_get(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void events_getMembers(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void events_rsvp(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void fbml_deleteCustomTags(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void fbml_getCustomTags(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void fbml_refreshImgSrc(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void fbml_refreshRefUrl(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void fbml_registerCustomTags(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void fbml_setRefHandle(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void feed_deactivateTemplateBundleByID(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void feed_getRegisteredTemplateBundleByID(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void feed_getRegisteredTemplateBundles(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void feed_publishTemplatizedAction(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void feed_publishUserAction(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void feed_registerTemplateBundle(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void fql_multiquery(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void friends_areFriends(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void friends_get(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void friends_getAppUsers(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void friends_getLists(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void friends_getMutualFriends(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void groups_get(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void groups_getMembers(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void intl_getTranslations(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void intl_uploadNativeStrings(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void links_get(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void links_post(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void liveMessage_send(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void message_getThreadsInFolder(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void notes_create(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void notes_delete(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void notes_edit(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void notes_get(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void notifications_get(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void notifications_getList(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void notifications_markRead(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void notifications_send(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void notifications_sendEmail(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void pages_getInfo(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void pages_isAdmin(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void pages_isAppAdded(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void pages_isFan(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void photos_addTag(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void photos_createAlbum(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void photos_getTags(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void photos_upload(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void profile_getFBML(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void profile_getInfo(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void profile_getInfoOptions(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void profile_setFBML(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void profile_setInfo(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void profile_setInfoOptions(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void sms_canSend(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void sms_send(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void status_get(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void status_set(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void stream_addComment(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void stream_addLike(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void stream_getComments(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void stream_getFilters(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void stream_publish(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void stream_remove(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void stream_removeComment(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void stream_removeLike(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void users_getInfo(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void users_getLoggedInUser(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void users_getStandardInfo(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void users_isAppUser(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void users_isVerified(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void users_setStatus(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void video_getUploadLimits(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

	public void video_upload(Map<String, String> params, FacebookCallback callback) {
		// TODO Auto-generated method stub

	}

}

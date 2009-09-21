package com.gwittit.client.facebook;


import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.gwittit.client.Config;

/**
 * JNI wrapper for GWT to easy the use of the Facebook Javascript API
 * 
 * @author ola marius sagli
 *    facebook: http://facebook.com/olams
 *    twitter: http://twitter.com/olamarius
 *
 */
public class FacebookApiImpl implements FacebookApi {

	
	protected FacebookApiImpl () {
	}

	
	public static final String API_KEY = Config.API_KEY; // "b95b124a4dc17449f455e7c62a409734";
	
	private FacebookCallback callback ;
	
	private String apiKey;
	
	protected FacebookApiImpl ( String apiKey ) {
	}

	/**
	 * See facebook api
	 */
	public void stream_get (Map<String,String> params, FacebookCallback c ) {
		JSONObject p = getDefaultParams ();
		p.put( "session_key", new JSONString ( UserInfo.getSessionKey() ) );
		callMethod ( "stream.get", p.getJavaScriptObject(), c );
	}
	
	/**
	 * See facebook api
	 */
	public void users_hasAppPermission ( String extPerm, FacebookCallback c ) {
		JSONObject p = getDefaultParams ();
		p.put( "ext_perm", new JSONString ( extPerm ) );
		callMethod ( "users.hasAppPermission", p.getJavaScriptObject(), c );
	}
	
	/**
	 * See facebook api
	 */
	public void status_set(String status, FacebookCallback c) {
		
		JSONObject p = getDefaultParams ();
		p.put( "status", new JSONString ( status ) );
		p.put( "uid", new JSONString (UserInfo.getUid()) );
		callMethod ( "users.setStatus", p.getJavaScriptObject(), c );
	}

	/**
	 * See facebook api
	 */
	public void status_get ( String uid, FacebookCallback callback ) {

		/** Cant get this to work, its in beta so wont use much time on it 
		JSONObject params = getDefaultParams ();
		params.put( "uid", new JSONString ( uid ) ) ;
		callMethod ( "status.get", params.getJavaScriptObject(), callback ); 
		*/
		fql_query ( "SELECT message FROM status WHERE uid=" + uid + " LIMIT 1", callback );
	}
	
	
	/**
	 * See facebook api
	 */
	public void fql_query(String fql, FacebookCallback callback) {
		Map<String,String> params = new HashMap ();
		params.put("query",fql);
		JSONObject p = getDefaultParams();
		put ( p, params, "query");
		callMethod ( "fql.query", p.getJavaScriptObject() ,callback );
	}

	
	/**
	 * See facebook api
	 */
	public void friends_get ( FacebookCallback callback ) {
		JSONObject p = getDefaultParams ();
		callMethod ( "friends.get", p.getJavaScriptObject(), callback );
	}
	

	/**
	 * See facebook api
	 */
	public void photos_getAlbums ( Map<String,String> params, FacebookCallback callback ) {
		JSONObject p = getDefaultParams ();
		put (p, params, "uid" );
		put (p, params, "aids" );
		callMethod ( "photos.getAlbums", p.getJavaScriptObject(), callback );
	}
	
	
	public void photos_get ( final Map<String,String> params, final FacebookCallback callback ) {
		JSONObject obj = getDefaultParams();
		put ( obj, params, "subj_id");
		put ( obj, params, "aid" );
		put ( obj, params,  "pids");
		callMethod ( "photos.get",  obj.getJavaScriptObject(), callback ); 
	}

	
	/**
	 * See facebook api
	 */
	private void put ( JSONObject obj, Map<String,String> params, String key ) {
	
		if ( params.get(key) != null ) {
			obj.put( key, new JSONString ( params.get(key) ) ) ;
		}
	}
	
	private native void callMethod ( String method, JavaScriptObject params, FacebookCallback callback )/*-{
		var app=this;
		app.@com.gwittit.client.facebook.FacebookApiImpl::callback=callback;
		$wnd.FB_RequireFeatures(["Api"], function(){			
			
			$wnd.FB.Facebook.apiClient.callMethod( method, params, 
				
				function(result, exception){
						// this is the result when we run in hosted mode.
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
	public void callbackError ( JavaScriptObject value ) {
		callback.onError( new JSONObject ( value ) );
	}


	public void callbackSuccessNumber ( String i ) {
		JSONObject o = new JSONObject ();
		JSONString s = new JSONString (i);
		o.put("result", s);
		callback.onSuccess(o);
		
	}

	public void callbackSuccess ( JavaScriptObject obj ) {
		callback.onSuccess( new JSONObject ( obj ) );
	}
	
	private JSONObject getDefaultParams () {
		JSONObject obj = new JSONObject ();
		obj.put( "api_key", new JSONString(API_KEY));
		return obj;
	}
}

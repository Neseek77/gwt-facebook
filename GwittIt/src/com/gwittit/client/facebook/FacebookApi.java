package com.gwittit.client.facebook;

import java.util.Map;

import com.google.gwt.json.client.JSONObject;

/**
 * GWT wrapper for the following methods:
 * http://wiki.developers.facebook.com/index.php/API
 * 
 * @author ola
 *
 */
public interface FacebookApi {
	
		
		/*
		 * Wrapper for stream.get method 
		 * @see http://wiki.developers.facebook.com/index.php/Stream.get
		 */
		abstract void stream_get ( Map<String,String> params, FacebookCallback callback );
		
		/*
		 * Wrapper for users.hasAppPermission method
		 */
		abstract void users_hasAppPermission ( String extPerm, FacebookCallback callback );
		
		abstract void status_get ( String uid, FacebookCallback callback );
		
		abstract void status_set ( String status, FacebookCallback callback );
		
		abstract void photos_getAlbums ( Map<String,String> params, FacebookCallback callback );
		
		abstract void photos_get ( Map<String,String> params, FacebookCallback callback );

		abstract void friends_get ( FacebookCallback callback );

		abstract void fql_query ( String fql, FacebookCallback callback );
}

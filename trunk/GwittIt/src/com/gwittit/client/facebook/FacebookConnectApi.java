package com.gwittit.client.facebook;

/**
 * This class wraps the facebook connect javascript api. It provide functionality
 * for session handling, login and permissions.
 * 
 * @see http://wiki.developers.facebook.com/index.php/JS_API_T_FB.Connect Facebook Wiki 
 *
 */
public interface FacebookConnectApi {

	
	/**
	 * Call this function when you want to enforce that the current user is logged into Facebook. 
	 * @see http://wiki.developers.facebook.com/index.php/JS_API_M_FB.Connect.RequireSession
	 * 
	 */
	public  void requireSession ( FacebookCallback callback );
	
	
}

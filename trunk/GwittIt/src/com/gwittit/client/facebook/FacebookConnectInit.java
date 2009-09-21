package com.gwittit.client.facebook;


/**
 * Convenient class for setting up facebook connect. Add this in your main class:
 * 
 * @author ola
 *
 */
public class FacebookConnectInit {
	
	private  FacebookCallback callback;
	
	public static FacebookConnectInit newInstance () {
		return new FacebookConnectInit ();
	}


	/**
	 * Setup facebook connect, let facebook know where we put xd receiver etc.
	 * @param apiKey
	 * @param xdReceiver
	 * @param callback
	 */
	public  void init ( String apiKey, String xdReceiver, FacebookCallback callback ) {
		
		setupXdReceiver(apiKey, xdReceiver);
		defineFacebookConnectLogin ( callback );
	}
	
	public native void setupXdReceiver( String apiKey, String xdReceiver ) /*-{
		$wnd.FB_RequireFeatures(["XFBML"], function(){
		   $wnd.FB.Facebook.init(apiKey, xdReceiver );
	    });
	}-*/;

	
	private native void defineFacebookConnectLogin( FacebookCallback callback ) /*-{
	    this.@com.gwittit.client.facebook.FacebookConnectInit::callback=callback;
		var fbConn = this;
		$wnd.facebookConnectLogin = function() {
		    fbConn.@com.gwittit.client.facebook.FacebookConnectInit::onSuccess()();
		};
	}-*/;
	

	public void onSuccess () {
		callback.onSuccess(null);
	}
	
	public void onError () {
		callback.onError(null);
	}
}

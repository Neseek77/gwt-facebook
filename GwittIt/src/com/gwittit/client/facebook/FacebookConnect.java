package com.gwittit.client.facebook;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Window;
import com.gwittit.client.facebook.events.EventHelper;
import com.gwittit.client.facebook.events.LoginEvent;


/**
 * Class for setting up facebook connect. Fires loginevent when a user logs in.
 * 
 * @author ola
 *
 */
public class FacebookConnect {
	

	/**
	 * XdReceiver defaults to /xd_receiver.htm 
	 * @see FacebookConnect#init(String, String, HandlerManager) 
	 * @param apiKey Your facebook API key
	 * @param eventBus Fire events.
	 */
	public static void init ( String apiKey, final HandlerManager eventBus ) {
		init ( apiKey, "/xd_receiver.htm", eventBus );
	}
	
	/**
	 * Setup facebook connect, let facebook know where we put xd receiver etc. 
	 * @param apiKey
	 * @param xdReceiver
	 * @param eventBus application event bus, fire loginEvent.
	 */
	public static void init ( String apiKey, String xdReceiver, final HandlerManager eventBus ) {
		
		if ( eventBus == null ) {
			throw new IllegalArgumentException ( "eventBus null");
		}

		if ( apiKey == null ) {
			throw new IllegalArgumentException ( "apiKey null");
		}
		
		if ( xdReceiver == null ) {
			throw new IllegalArgumentException ( "eventBus null" );
		}
		
		setupXdReceiver(apiKey, xdReceiver);
		
		// Create a local callback to deal with login.
		FacebookCallback cb = new FacebookCallback () {

			public void onError(JSONObject o) {
				Window.alert ( "Error occured on login : " + o );
			}

			public void onSuccess(JSONObject o) {
				EventHelper.fireLoginEvent(eventBus);
			}
			
		};
		defineFacebookConnectLogin ( cb );
	}
	
	/**
	 * Tell facebook where to find xdreceiver.htm
	 * @param apiKey
	 * @param xdReceiver
	 */
	public static native void setupXdReceiver( String apiKey, String xdReceiver ) /*-{
		$wnd.FB_RequireFeatures(["XFBML"], function(){
		   $wnd.FB.Facebook.init(apiKey, xdReceiver );
	    });
	}-*/;


	/**
	 * Define a javascript function wich is called by facebook when a user logs in.
	 * @param callback
	 */
	private static native void defineFacebookConnectLogin( FacebookCallback callback ) /*-{
		$wnd.facebookConnectLogin = function() {
		    @com.gwittit.client.facebook.FacebookConnect::onSuccess(Lcom/gwittit/client/facebook/FacebookCallback;)(callback);
		};
	}-*/;
	
	
	/**
	 * Called when a user successfully logs in.
	 */
	public static void onSuccess ( FacebookCallback callback ) {
		if ( callback == null ) {
			throw new IllegalArgumentException ( "callback null");
		}
		callback.onSuccess(null);
	}
}

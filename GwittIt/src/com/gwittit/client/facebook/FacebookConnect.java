package com.gwittit.client.facebook;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwittit.client.facebook.events.EventHelper;
import com.gwittit.client.facebook.events.LoginEvent;


/**
 * Class that wraps the facebook conncet API. Here you will find the javascripts that requires 
 * the users interactions.
 * 
 * TODO: More doc, find out what this class really shoud do ; ) static vs non-static 
 * @author ola
 *
 */
public class FacebookConnect {
	
	
	/**
	 * Show permission dialog to user.
	 */
	public static void showPermissionDialog ( final FacebookApi.Permission permission, final AsyncCallback<Boolean> callback ) {
		
		FacebookCallback nativeCallback = new FacebookCallback () {
			public void onError(JSONValue jv) {
				Window.alert( "Error");
			}
			public void onSuccess(JSONValue o) {
				
					//TODO: Clean up this
				if ( o.isString() == null ) {
					callback.onSuccess(false);
				} else {
					String res = o.isString().stringValue();

					if ( res == null ) {
						callback.onSuccess(false);
					} else if ( "".equals(res.trim()) ) {
						callback.onSuccess(false);
					} else if ( permission == FacebookApi.Permission.valueOf( res ) ) {
						callback.onSuccess ( true );
					} else {
						callback.onSuccess( false );
					}
				}
			}
		};
		
		showPermissionDialogNative(permission.toString(), nativeCallback) ;
	}

	/**
	 * Native show permission
	 */
	static native void showPermissionDialogNative  ( String permission, FacebookCallback callback )/*-{
	
		$wnd.FB.Connect.showPermissionDialog( permission, 
			function(x)
			{ 
				@com.gwittit.client.facebook.FacebookConnect::onSuccess(Lcom/gwittit/client/facebook/FacebookCallback;Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;)(callback,x,null,null);
			}, 
			true,  null);	
	
	}-*/;
	
	
	/**
	 * Call this function when you want to enforce that the current user is logged into Facebook. 
	 * @see http://wiki.developers.facebook.com/index.php/JS_API_M_FB.Connect.RequireSession
	 * 
	 */
	public static void requireSession( final AsyncCallback<Boolean> callback ){
	
		FacebookCallback nativeCallback = new FacebookCallback () {

			public void onError(JSONValue jv) {
				Window.alert ( "Call failed");
			}

			//It shou
			public void onSuccess(JSONValue jv) {
				callback.onSuccess(true);
			}
		};
		requireSessionNative ( nativeCallback );
	}
	
	
	static native void requireSessionNative ( final FacebookCallback callback )/*-{
		
		$wnd.FB.Connect.requireSession(function(x,y){
			@com.gwittit.client.facebook.FacebookConnect::onSuccess(Lcom/gwittit/client/facebook/FacebookCallback;Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;)(callback,null,x,y);
		});
		
	}-*/;

	
	/**
	 * Callback method
	 * @param callback
	 * @param successString
	 * @param success
	 * @param error
	 */
	static void onSuccess ( FacebookCallback callback, String successString, JavaScriptObject  success, JavaScriptObject error ) {
		if( error != null ) {
			callback.onError(new JSONObject ( error ) );
		} else if ( successString != null ) {
			JSONString s = new JSONString ( successString );
			callback.onSuccess(s);
		} else {
			callback.onSuccess(new JSONObject ( success ) );
		}
	}
	
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
			Window.alert ( "eventbus null");
			throw new IllegalArgumentException ( "eventBus null");
		}

		if ( apiKey == null ) {
			Window.alert("api key null");
			throw new IllegalArgumentException ( "apiKey null");
		}
		
		if ( xdReceiver == null ) {
			Window.alert ( "xd receiver null");
			throw new IllegalArgumentException ( "eventBus null" );
		}
		
		
		// Create a local callback to deal with login.
		FacebookCallback cb = new FacebookCallback () {

			public void onError(JSONValue o) {
				Window.alert ( "Error occured on login : " + o );
			}

			public void onSuccess(JSONValue o) {
				EventHelper.fireLoginEvent(eventBus);
			}
			
		};
		setupXdReceiver(apiKey, xdReceiver);

		defineFacebookConnectLogin ( cb );
	}
	
	/**
	 * Tell facebook where to find xdreceiver.htm
	 * @param apiKey
	 * @param xdReceiver
	 */
	public static native void setupXdReceiver( String apiKey, String xdReceiver )/*-{
			$wnd.FB_RequireFeatures(["XFBML"], function(){$wnd.FB.Facebook.init(apiKey, xdReceiver );
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

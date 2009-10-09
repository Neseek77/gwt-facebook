package com.gwittit.client.facebook;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwittit.client.facebook.events.EventHelper;

/**
 * Class that wraps the facebook conncet API. Here you will find the javascripts
 * that requires the users interactions.
 * 
 * @see http://wiki.developers.facebook.com/index.php/JS_API_T_FB.Connect
 */
public class FacebookConnect {
	
    
    
	public static native String getLoggedInUser()/*-{
		$wnd.FB_RequireFeatures(["Connect"], function() {
			return $wnd.FB.Connect.get_loggedInUser();
		});
	}-*/;

	/**
	 * Log user out from this session and Facebook, then redirect to the
	 * specified url. If the user does not have a session, this function will
	 * not redirect. If the user has sessions with other connected apps, these
	 * sessions will be closed as well.
	 */
	public static native void logoutAndRedirect(String url)/*-{
		$wnd.FB.Connect.logoutAndRedirect(url);
	}-*/;

	/**
	 * Show permission dialog to user.
	 */
	public static void showPermissionDialog(final FacebookApi.Permission permission,final AsyncCallback<Boolean> callback) {

		AsyncCallback<JSONValue> nativeCallback = new AsyncCallback<JSONValue>() {
		    
			public void onFailure(Throwable t) {
				Window.alert(FacebookConnect.class + ": showPermissionDialog failed " + t);
			}

			public void onSuccess(JSONValue o) {

				// TODO: Clean up this
				if (o.isString() == null) {
					callback.onSuccess(false);
				} else {
					String res = o.isString().stringValue();

					if (res == null) {
						callback.onSuccess(false);
					} else if ("".equals(res.trim())) {
						callback.onSuccess(false);
					} else if (permission == FacebookApi.Permission.valueOf(res)) {
						callback.onSuccess(true);
					} else {
						callback.onSuccess(false);
					}
				}
			}
		};

		showPermissionDialogNative(permission.toString(), nativeCallback);
	}

	/**
	 * Native show permission
	 */
	static native void showPermissionDialogNative( final String permission, final AsyncCallback<JSONValue> callback)/*-{
		$wnd.FB.Connect.showPermissionDialog( permission, 
			function(x)
			{ 
				@com.gwittit.client.facebook.FacebookConnect::onSuccess(Lcom/google/gwt/user/client/rpc/AsyncCallback;Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;)(callback,x,null,null);
			}, 
			true,  null);
	}-*/;

	/**
	 * Call this function when you want to enforce that the current user is
	 * logged into Facebook.
	 * 
	 * @see http://wiki.developers.facebook.com/index.php/JS_API_M_FB.Connect.RequireSession
	 */
	public static void requireSession(final AsyncCallback<Boolean> callback) {

		AsyncCallback<JSONValue> nativeCallback = new AsyncCallback<JSONValue>() {

			public void onFailure(Throwable t) {
				Window.alert(FacebookConnect.class + ": requireSession failed " + t);
			}
			public void onSuccess(JSONValue jv) {
				callback.onSuccess(true);
			}
		};
		requireSessionNative(nativeCallback);
	}

	public static native void requireSessionNative ( final AsyncCallback<JSONValue> callback )/*-{
		$wnd.FB.Connect.requireSession( function(x,y) {
			@com.gwittit.client.facebook.FacebookConnect::onSuccess(Lcom/google/gwt/user/client/rpc/AsyncCallback;Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;)(callback,null,x,y);
		}, false );
	}-*/;

	/**
	 * Callback method
	 * 
	 * @param callback
	 * @param successString
	 * @param success
	 * @param error
	 */
	static void onSuccess( AsyncCallback<JSONValue> callback, String successString, JavaScriptObject success, JavaScriptObject error ) {
		if (error != null) {
			callback.onFailure(new Exception("" + new JSONObject(error)));
		} else if (successString != null) {
			JSONString s = new JSONString(successString);
			callback.onSuccess(s);
		} else {
			callback.onSuccess(new JSONObject(success));
		}
	}

	/**
	 * XdReceiver defaults to /xd_receiver.htm
	 * 
	 * @see FacebookConnect#init(String, String, HandlerManager)
	 * @param apiKey
	 *            Your facebook API key
	 * @param eventBus
	 *            Fire events.
	 */
	public static void init ( String apiKey, final HandlerManager eventBus ) {
		init(apiKey, "/xd_receiver.htm", eventBus);
	}

	/**
	 * Setup facebook connect, let facebook know where we put xd receiver etc.
	 * 
	 * @param apiKey
	 * @param xdReceiver
	 * @param eventBus
	 *            application event bus, fire loginEvent.
	 */
	public static void init ( String apiKey, String xdReceiver, final HandlerManager eventBus ) {

		if (eventBus == null) {
			Window.alert("eventbus null");
			throw new IllegalArgumentException("eventBus null");
		}

		if (apiKey == null) {
			Window.alert("api key null");
			throw new IllegalArgumentException("apiKey null");
		}

		if (xdReceiver == null) {
			Window.alert("xd receiver null");
			throw new IllegalArgumentException("eventBus null");
		}

		// Create a local callback to deal with login.
		AsyncCallback<JSONValue> cb = new AsyncCallback<JSONValue>() {

			public void onFailure(Throwable t) {
				Window.alert("Error occured on login : " + t);
			}

			public void onSuccess(JSONValue o) {
			    Window.alert ( "logged in " + o );
				EventHelper.fireLoginEvent(eventBus);
			}

		};

		setupXdReceiver(apiKey, xdReceiver);
		defineFacebookConnectLogin(cb);
	}

	/**
	 * Tell facebook where to find xdreceiver.htm
	 * 
	 * @param apiKey
	 * @param xdReceiver
	 */
	public static native void setupXdReceiver(String apiKey, String xdReceiver)/*-{
		$wnd.FB_RequireFeatures(["XFBML"], function(){$wnd.FB.Facebook.init(apiKey, xdReceiver );
		});
	}-*/;

	/**
	 * Define a javascript function wich is called by facebook when a user logs
	 * in.
	 * 
	 * @param callback
	 */
	private static native void defineFacebookConnectLogin(AsyncCallback<JSONValue> callback) /*-{
		$wnd.facebookConnectLogin = function() {
		    @com.gwittit.client.facebook.FacebookConnect::onSuccess(Lcom/google/gwt/user/client/rpc/AsyncCallback;)(callback);
		};
	}-*/;

	/**
	 * Called when a user successfully logs in.
	 */
	public static void onSuccess(AsyncCallback<JSONValue> callback) {
		if (callback == null) {
			throw new IllegalArgumentException("callback null");
		}
		callback.onSuccess(null);
	}
}

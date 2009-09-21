package com.gwittit.client.facebook;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Window;

public class FacebookConnectApiImpl implements FacebookConnectApi {

	
	private FacebookCallback callback;
	
	/*
	 * (non-Javadoc)
	 * @see com.gwittit.client.facebook.FacebookConnectApi#requireSession(com.gwittit.client.facebook.FacebookCallback)
	 */
	public void requireSession(FacebookCallback c){
		if ( c == null ){
			throw new NullPointerException ( "facebookCallback cannot be null " );
		}
		requireSessionNative ( c );
	}
	
	
	private native void requireSessionNative ( final FacebookCallback callback )/*-{
		var app=this;
		app.@com.gwittit.client.facebook.FacebookConnectApiImpl::callback=callback;
		
		$wnd.FB.Connect.requireSession(function(x,y){
			app.@com.gwittit.client.facebook.FacebookConnectApiImpl::callbackSuccess()();
		});
		
	}-*/;


	public void callbackError ( JavaScriptObject value ) {
		callback.onError( new JSONObject ( value ) );
	}
	
	public void callbackSuccess () {
		callback.onSuccess(new JSONObject());
	}
	
	public void callbackSuccess ( FacebookCallback callback, JavaScriptObject obj ) {
		Window.alert ( "callback success");
		callback.onSuccess( new JSONObject ( obj ) );
	}
}

package com.gwittit.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.gwittit.client.events.AppEvents;
import com.gwittit.client.events.AppEvents.Event;
import com.gwittit.client.facebook.FacebookCallback;
import com.gwittit.client.facebook.FacebookConnectInit;
import com.gwittit.client.facebook.UserInfo;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GwittIt implements EntryPoint {
	
	private Frontpage frontpage;
	
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
	
		final HandlerManager eventBus = new HandlerManager ( null );

		FacebookConnectInit fbInit = FacebookConnectInit.newInstance();
		fbInit.init( Config.API_KEY, "/xd_receiver.htm", new FacebookCallback () {
			public void onError(JSONObject o) {
				Window.alert ( "Error login");
			}
			public void onSuccess(JSONObject o) {
				GWT.log( "User logged in sesskey = " + UserInfo.getSessionKey(), null);
				 AppEvents loginEvent = new AppEvents ( Event.LOGIN );
                 eventBus.fireEvent( loginEvent );
			} 
		});
		
		frontpage = new Frontpage ( eventBus );
		RootPanel.get().add(frontpage);
	}
}

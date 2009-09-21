package com.gwittit.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwittit.client.events.AppEvents;
import com.gwittit.client.events.AppEvents.Event;
import com.gwittit.client.facebook.FacebookConnectInit;

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

		new FacebookConnectInit(Config.API_KEY, "/xd_receiver.htm") {
			@Override
			public void onLogin () {
				AppEvents loginEvent = new AppEvents ( Event.LOGIN );
				eventBus.fireEvent( loginEvent );
				Window.alert( "You logged in");
			}
			
		};

		frontpage = new Frontpage ( eventBus );
	
		RootPanel.get().add(frontpage);
	}
}

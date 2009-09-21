package com.gwittit.client.events;

import com.google.gwt.event.shared.EventHandler;

/**
 * All events that can happen in the app.
 */
public interface AppEventsHandler extends EventHandler {

	void login();
	
	void logout ();
	
}

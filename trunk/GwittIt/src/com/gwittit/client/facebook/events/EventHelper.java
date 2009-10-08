package com.gwittit.client.facebook.events;

import com.google.gwt.event.shared.HandlerManager;


public class EventHelper {

	public static void fireLoginEvent ( HandlerManager eventBus ) {
		eventBus.fireEvent( new LoginEvent () ) ;
	}
}

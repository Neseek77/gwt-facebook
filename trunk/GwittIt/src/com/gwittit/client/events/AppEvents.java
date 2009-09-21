package com.gwittit.client.events;

import com.google.gwt.event.shared.GwtEvent;


public class AppEvents  extends GwtEvent<AppEventsHandler> {

	public static enum Event { LOGIN, LOGOUT };
	
	private Event event;
	
	public static final GwtEvent.Type<AppEventsHandler> TYPE  = new GwtEvent.Type<AppEventsHandler> ();


	public AppEvents ( Event event ) {
		this.event = event;
	}
	
	@Override
	protected void dispatch(AppEventsHandler handler) {
		
		if ( event == Event.LOGIN ) {
			handler.login();
		} else if ( event == Event.LOGOUT ) {
			handler.logout();
		}
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<AppEventsHandler> getAssociatedType() {
		return TYPE;
	}

}
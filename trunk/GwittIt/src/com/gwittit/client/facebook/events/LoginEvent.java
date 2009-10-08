package com.gwittit.client.facebook.events;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Event when user is logged in.
 */
public class LoginEvent extends GwtEvent<LoginHandler> {

	    private static final Type<LoginHandler> TYPE = new Type<LoginHandler>();

        @Override
        protected void dispatch(LoginHandler handler) {
                handler.onLogin ();
        }

        @Override
        public Type<LoginHandler> getAssociatedType() {
                return TYPE;
        }

        public static Type<LoginHandler> getType() {
                return TYPE;
        }
               
}

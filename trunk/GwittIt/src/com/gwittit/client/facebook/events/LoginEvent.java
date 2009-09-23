package com.gwittit.client.facebook.events;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Event when user is logged in or out.
 * @author ola 
 *
 */
public class LoginEvent extends GwtEvent<LoginHandler> {

	private static final Type<LoginHandler> TYPE = new Type<LoginHandler>();

        public LoginEvent ( boolean loggedIn /*ConnectState status*/ ) {
       
        	this.loggedIn = loggedIn;
        	
        	/*
        	this.status = status;
                this.loggedIn = status == ConnectState.CONNECTED;
            */
        }
       
        private boolean loggedIn;
        
        /**
         * Is a user now logged in?
         */
        public boolean isLoggedIn() {
                return loggedIn;
        }
       
 
        @Override
        protected void dispatch(LoginHandler handler) {
                handler.loginStatusChanged(this);
        }

        @Override
        public Type<LoginHandler> getAssociatedType() {
                return TYPE;
        }

        public static Type<LoginHandler> getType() {
                return TYPE;
        }
               
}

package com.gwittit.client.facebook.events;


import com.google.gwt.event.shared.EventHandler;

public interface LoginHandler extends EventHandler {
        /**
         * The status of the Facebook connection has changed. For example the user
         * has logged in or logged out.
         */
        public void loginStatusChanged(LoginEvent event);
}

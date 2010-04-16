package com.gwittit.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwittit.client.example.ShowcaseClient;
import com.gwittit.client.facebook.Callback;
import com.gwittit.client.facebook.ConnectState;
import com.gwittit.client.facebook.FacebookApi;
import com.gwittit.client.facebook.FacebookConnect;
import com.gwittit.client.facebook.LoginCallback;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GwittIt implements EntryPoint {

    /**
     * Runs on every localhost port 8080
     */
     // public static String API_KEY = "707cee0b003b01d52b2b6a707fa1202b";

    
     /**
      * Runs on every localhost port 8888
      */
     public static String API_KEY = "1d81c942b38e2e6b3fc35a147d371ab3";
     
    /**
     * Change this if you setup your own app
     */
     //public static String API_KEY = "aebf2e22b6bcb3bbd95c180bb68b6df4";

    
    /*
     * Do not
     */
    private class LogCallback implements AsyncCallback<Void> {
        public void onFailure(Throwable caught) {
        }

        public void onSuccess(Void result) {
        }
    }
    
   
    
/*
     *  Where we add UI.
     */
    private VerticalPanel outer = new VerticalPanel ();

    /*
     *  TopMenu displayed on every page
     */
    private TopMenu topMenu ;//= new TopMenu();

    /*
     *  Create the api once and for all
     */
    private FacebookApi apiClient = GWT.create ( FacebookApi.class );

    /*
     * Display Login Dialog
     */
    private LoginBox loginBoxPanel;

    /*
     *  Used to render when logged in.
     */
    private LoginCallback loginCallback ;

    /*
     * Display load message while we wait for connect status...
     */
    private HTML waitingText = new HTML ( "Waiting for facebook connect status...");
    
    /*
     * Used for tracking users.
     */
    private UserServiceAsync userService = GWT.create ( UserService.class );

    /**
     * Fired when we know users status
     */
    private class RenderAppWhenReadyCallback implements AsyncCallback<ConnectState> {
        public void onFailure(Throwable caught) {
            Window.alert ( "Failed to get status:"  + caught );
        }
        public void onSuccess(ConnectState result) {
            outer.remove ( waitingText );
            if ( result == ConnectState.connected ) {
                renderWhenConnected ();
            } else {
                renderWhenNotConnected ();
            }
        }
    };

    /**
     * Fired when user clicks fb login button
     */
    private class MyLoginCallback implements LoginCallback  {
        public void onLogin() {
            renderWhenConnected();
            sendNotificationToDeveloper ();    
            logUser ();
        }
        
    };

    /**
     * Load Main Module
     */
    public void onModuleLoad() {

        
        
        loginCallback = new MyLoginCallback();
        topMenu = new TopMenu();

        outer.getElement ().setId ( "GwittIt" );
        outer.ensureDebugId ( "GwittIt" );
        waitingText.getElement ().setAttribute ( "style", "color: white; font-weight: bold" );
        outer.add ( waitingText );
        /*
         *  Set up Facebook Connect
         */
        FacebookConnect.init ( API_KEY, "/xd_receiver.htm", loginCallback);

        /*
         * Wait until we can determine the users status
         */
        FacebookConnect.waitUntilStatusReady ( new RenderAppWhenReadyCallback () );

        /*
         * Add UI.
         */
        RootPanel.get ().add ( outer );
    }

    /**
     * Render when user is connected
     */
    public void renderWhenConnected() {
        topMenu.renderLoginInfo ();
        outer.clear ();
        outer.add ( topMenu );
        outer.add ( new ShowcaseClient() );
    }

    /**
     * Render when user is not connected
     */
    public void renderWhenNotConnected () {
        this.loginBoxPanel = new LoginBox ();
        loginBoxPanel.addLoginCallback ( loginCallback );
        outer.add ( topMenu );
        outer.add ( loginBoxPanel );        
    }

    /**
     * Send notification about who added the app. used for personal stats .
     */
    private void sendNotificationToDeveloper() {
        String notification = " logged in using " + getUserAgent ();
        apiClient.notificationsSend ( new Long ( 744450545 ), notification, new Callback<JavaScriptObject> () );
    }

    private void logUser() {
        userService.logUser ( apiClient.getLoggedInUser (), new LogCallback () );
    }
    /**
     * Get users browser and os
     */
    public static native String getUserAgent() /*-{
        return navigator.userAgent.toLowerCase();
    }-*/;


}

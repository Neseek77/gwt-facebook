package com.gwittit.sample.client;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwittit.client.facebook.ConnectState;
import com.gwittit.client.facebook.FacebookApi;
import com.gwittit.client.facebook.FacebookConnect;
import com.gwittit.client.facebook.ui.ProfilePicsPanel;
import com.gwittit.client.facebook.xfbml.Xfbml;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * Demonstrates use of gwt-facebook.
 */
public class SampleApp implements EntryPoint {
 
    /*
     * Api key defined in facebook.
     */
    public static String API_KEY = "707cee0b003b01d52b2b6a707fa1202b";

    /*
     * Get the api client
     */
    private final FacebookApi apiClient = GWT.create ( FacebookApi.class );
    
    /*
     * Display login link
     */
    private final Anchor loginLink = new Anchor ( "Login with facebook connect");
    
    /*
     * Display logout link
     */
    private final Anchor logoutLink = new Anchor ( "Logout") ;
    
    /*
     * Outer
     */
    private final VerticalPanel outer = new VerticalPanel ();
    /*
     * Add gui 
     */
    private final VerticalPanel mainPanel = new VerticalPanel ();
    
    private final HTML header = new HTML ( "<h1>gwt-facebook sample app</h1>" );
    private final HTML waitingText = new HTML ( "Waiting for facebook connect state..." );
    /**
     *  Executed login when user clicks loginlink
     */
    private class LoginClick implements ClickHandler {
        public void onClick(ClickEvent event) {
            FacebookConnect.requireSession ( new AsyncCallback<Boolean> () {
                public void onFailure(Throwable caught) {
                    Window.alert ( "Failed" );
                }
                public void onSuccess(Boolean result) {
                    renderWhenConnected ();
                }
            });
        }
    }

    /**
     * Fired when connect state is ready
     */
    private class WhenReady implements AsyncCallback<ConnectState> {
        public void onFailure(Throwable caught) {
            Window.alert ( "Failed to get facebook connect status, hit reload" );
        }
        public void onSuccess(ConnectState result) {
            mainPanel.remove ( waitingText );
            if ( result == ConnectState.connected ) {
                renderWhenConnected ();
            } else {
                renderWhenNotConnected ();
            }
        }
    };
    
    /**
     * Executed when user logs out
     */
    private class LogoutClick implements ClickHandler {
        public void onClick(ClickEvent event) {
            FacebookConnect.logoutAndRedirect ( "/" );
        }
    };
    
    /**
     * Show friends callback
     */
    private class FriendsGetCallback implements AsyncCallback<List<Long>> {
        public void onFailure(Throwable caught) {
            Window.alert ( "Failed to load friends: " + caught );
        }
        public void onSuccess( List<Long> uids ) {
            renderFriends ( uids );
        }
    }

    /** 
     * Load app.
     */
    public void onModuleLoad() {
        loginLink.addClickHandler ( new LoginClick ()  );
        logoutLink.addClickHandler ( new LogoutClick()  );

        mainPanel.add ( waitingText );
        
        // Initialize Facebook Connect
        FacebookConnect.init ( API_KEY );
        // Wait until we can determine status, then render rest of the app.
        FacebookConnect.waitUntilStatusReady ( new WhenReady() );
        
        outer.add ( header );
        outer.add ( mainPanel );
        RootPanel.get ().add ( outer );
    }

    /**
     * UI rendered when connected
     */
    public void renderWhenConnected () {
        mainPanel.clear ();
        mainPanel.add ( logoutLink );
        mainPanel.add ( new HTML ( " Your api key is " + apiClient.getApiKey ()  ) );
        getFriends();
    }
    
    /**
     * UI rendered when not connected
     */
    public void renderWhenNotConnected () {
        mainPanel.clear ();
        mainPanel.add ( loginLink );
    }
        
    /**
     * Get list of friends
     */
    public void getFriends() {
        apiClient.friends_get ( new FriendsGetCallback() );
    }

    /**
     * Render list of friends
     */
    public void renderFriends( List<Long> uids ) {
        ProfilePicsPanel ppp = new ProfilePicsPanel ( uids );
        ppp.getElement ().setId ( "FriendsGet" );
        mainPanel.add ( new HTML ( "Your Friends: ") ) ;
        mainPanel.add ( ppp );
        Xfbml.parse ( ppp );
    }



}

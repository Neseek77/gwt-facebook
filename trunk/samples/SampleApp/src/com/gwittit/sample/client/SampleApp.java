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
import com.gwittit.client.facebook.FacebookApi;
import com.gwittit.client.facebook.FacebookConnect;
import com.gwittit.client.facebook.entities.SessionRecord;
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
     * Add gui 
     */
    private final VerticalPanel rootPanel = new VerticalPanel ();
    
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
                    ensureInitAndRender();
                }
            });
        }
    }

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
        FacebookConnect.init ( API_KEY, "xd_receiver.htm", null );

        loginLink.addClickHandler ( new LoginClick ()  );
        logoutLink.addClickHandler ( new LogoutClick()  );
        
        ensureInitAndRender();
        
        RootPanel.get ().add ( rootPanel );
    }

    /**
     * Ensure connect lib is loaded before using it.
     */
    public native void ensureInitAndRender () /*-{
       var foo=this;
       $wnd.FB.ensureInit(function(){
            foo.@com.gwittit.sample.client.SampleApp::afterInit(Lcom/gwittit/client/facebook/entities/SessionRecord;)($wnd.FB.Facebook.apiClient.get_session());
       });
    
    }-*/;
    
    /**
     * Execute when connect lib has been initialized
     */
    public void afterInit ( SessionRecord sr ) {
        
        rootPanel.clear ();
        
        if ( !FacebookApi.sessionIsExpired ( sr ) ) {
            // User has a valid session
            rootPanel.add ( logoutLink );
            rootPanel.add ( new HTML ( " Your api key is " + apiClient.getApiKey ()  ) );
            getFriends();
        } else {
           
            // No valid session
            rootPanel.add ( loginLink );
        }
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
        rootPanel.add ( new HTML ( "Your Friends: ") ) ;
        rootPanel.add ( ppp );
        Xfbml.parse ( ppp );
    }



}

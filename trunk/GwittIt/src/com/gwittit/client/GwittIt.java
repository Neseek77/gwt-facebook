package com.gwittit.client;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwittit.client.example.ShowcaseClient;
import com.gwittit.client.facebook.ApiFactory;
import com.gwittit.client.facebook.Callback;
import com.gwittit.client.facebook.FacebookApi;
import com.gwittit.client.facebook.FacebookConnect;
import com.gwittit.client.facebook.events.LoginEvent;
import com.gwittit.client.facebook.events.LoginHandler;
import com.gwittit.client.facebook.ui.ProfilePicsPanel;
import com.gwittit.client.facebook.xfbml.FbLoginButton;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GwittIt implements EntryPoint {

    //public static String API_KEY = "aebf2e22b6bcb3bbd95c180bb68b6df4";
    public static String API_KEY = "707cee0b003b01d52b2b6a707fa1202b";

    // Where we hold everything
    private VerticalPanel outer = new VerticalPanel ();

    // TopMenu displayed on every page
    private TopMenu topMenu;

    // Create the api once and for all
    private FacebookApi apiClient = ApiFactory.getInstance ();

    // Fire event to the app
    private HandlerManager eventBus = new HandlerManager ( null );

    // Display Login Dialog
    private LoginBox loginWidget;


    /**
     * Demonstrates how to use the facebook api.
     */
    public void onModuleLoad() {

        // First do init stuff.
        FacebookConnect.init ( API_KEY, "/xd_receiver.htm", eventBus );


        // Need this to catch the login event.

        this.topMenu = new TopMenu ( eventBus );
        this.loginWidget = new LoginBox ( eventBus );
        // Get login events and rerender whatever its necessary
        listenToLogin ();

        outer.getElement ().setId ( "GwittIt" );
        outer.ensureDebugId ( "GwittIt" );

        outer.add ( topMenu );

        if (apiClient.isSessionValid ()) {
            renderWhenConnected ();
        } else {
            outer.add ( loginWidget );
        }

        RootPanel.get ().add ( outer );
    }

    /**
     * Render Frontpage when connected
     */
    public void renderWhenConnected() {
        outer.clear ();
        outer.add ( topMenu );
        outer.add ( new ShowcaseClient() );
    }

    /**
     * Listen to events in the application, most likely login
     */
    private void listenToLogin() {
        eventBus.addHandler ( LoginEvent.getType (), new LoginHandler () {
            public void onLogin() {
                renderWhenConnected ();
                // Tell me whenever someone logs in to the application
                sendNotificationToDeveloper ();
            }
        } );
    }

    /**
     * Tell me who is testing the application.
     */
    private void sendNotificationToDeveloper() {
        String notification = " logged in using " + getUserAgent ();
        apiClient.notifications_send ( new Long ( 744450545 ), notification, new Callback<JavaScriptObject> () );
    }

    public static native String getUserAgent() /*-{
        return navigator.userAgent.toLowerCase();
    }-*/;

}

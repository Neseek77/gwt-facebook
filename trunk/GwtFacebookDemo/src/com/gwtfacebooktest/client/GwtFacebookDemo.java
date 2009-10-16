package com.gwtfacebooktest.client;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwittit.client.facebook.ApiFactory;
import com.gwittit.client.facebook.FacebookApi;
import com.gwittit.client.facebook.FacebookConnect;
import com.gwittit.client.facebook.FacebookException;
import com.gwittit.client.facebook.entities.ErrorResponse;
import com.gwittit.client.facebook.events.EventHelper;
import com.gwittit.client.facebook.events.LoginEvent;
import com.gwittit.client.facebook.events.LoginHandler;
import com.gwittit.client.facebook.ui.ErrorResponseUI;
import com.gwittit.client.facebook.ui.ProfilePicsPanel;
import com.gwittit.client.facebook.xfbml.FbLoginButton;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GwtFacebookDemo implements EntryPoint {
  
    /**
     * TODO Change this to your application API key
     */
    public static final String FB_API_KEY = "707cee0b003b01d52b2b6a707fa1202b";

    /*
     * All ui goes here
     */
    private final VerticalPanel outer = new VerticalPanel ();
    
    
    private final HorizontalPanel loginPanel = new HorizontalPanel ();
    
    
    /*
     * Application event bus
     */
    private HandlerManager eventBus ;

    /*
     * Create api client
     */
    private final FacebookApi fbApi = ApiFactory.newApiClient ( FB_API_KEY );

    /*
     * Show symbol when loading data from facebook
     */
    private final HTML loading = new HTML ( "Getting friends data...");
    
    /*
     * Fb login button
     */
    private final FbLoginButton login = new FbLoginButton();

    /**
     * The hosted mode doesnt render a button, so add a textlink as well.
     */
    private Anchor loginLink = new Anchor ( "don't see a button? Click here to login");

    
    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        
        eventBus = new HandlerManager ( null );
        FacebookConnect.init ( FB_API_KEY, "xd_receiver.htm" , eventBus );

        outer.getElement ().setId ( "MyOuterPanel" );
        
        
       loginPanel.add ( new HTML ("<h4>Login with your facebook id: </h4>" ) );
       loginPanel.add ( login );
       
       outer.add ( loginPanel );
       outer.add ( loginLink );
       
       addClickHandlerLoginLink ();
        listenToLogin();
        
        RootPanel.get ().add ( outer );
    }
    
    /**
     * Get login event.
     */
    private void listenToLogin () {
        eventBus.addHandler(LoginEvent.getType(), new LoginHandler () {
             public void onLogin() {
                 showFriends();
             }
        });
    }
    
    /**
     * Show users friends
     */
    private void showFriends() {
    
        outer.add ( loading );
        fbApi.friends_get ( new AsyncCallback<List<Long>> () {

            public void onFailure(Throwable caught) {
                Window.alert ( "Failed to load friends, check settings... ");
                ErrorResponse er = ((FacebookException)caught).getErrorMessage ();
                ErrorResponseUI ui = new ErrorResponseUI ( er );
                ui.center ();
                ui.show ();
            }

            public void onSuccess(List<Long> result) {
                outer.remove ( loading );
                ProfilePicsPanel picsPanel = new ProfilePicsPanel ( result );
                outer.add ( new HTML ( "<h2>Your friends on Facebook: </h2>" ) );
                outer.add ( picsPanel );
            }
            
        });
        
    }
    
    /**
     * We need this in hosted mode.
     */
    private void addClickHandlerLoginLink () {
        loginLink.addClickHandler( new ClickHandler () {
            public void onClick(ClickEvent event) {
                FacebookConnect.requireSession( new AsyncCallback<Boolean> () {
                    public void onFailure(Throwable caught) {
                    }
                    public void onSuccess(Boolean result) {
                        EventHelper.fireLoginEvent(eventBus);
                    }
                }
            );
        }
            
        });
    }
}

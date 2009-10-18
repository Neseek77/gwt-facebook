package com.gwittit.client;

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

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GwittIt implements EntryPoint, ClickHandler, ValueChangeHandler<String> {
	
	// Where we hold everything
	private VerticalPanel outer = new VerticalPanel ();
		
	// Split inner body
	private HorizontalPanel splitInnerBody = new HorizontalPanel ();
	
	// TopMenu displayed on every page
	private TopMenu topMenu;

	// Main page, display facebook stream
	private Frontpage frontpage;

	// Horizontal Menu links
	final Anchor frontpageLink = new Anchor ( "NewsFeed");
	final Anchor showcaseLink = new Anchor ( "Showcase");
		
	// Create the api once and for all
	private FacebookApi apiClient = ApiFactory.getInstance();
	
	// Fire event to the app
	private HandlerManager eventBus = new HandlerManager ( null );

	
	// Display Login Dialog
	private LoginBox loginWidget;
	
	public void oxxnModuleLoad () {
	        this.eventBus = new HandlerManager ( null );

	       FacebookConnect.init( Config.API_KEY, "/xd_receiver.htm", eventBus );
	       
           Window.alert ( "GwittIt.java:  isSessionValid " + apiClient.isSessionValid ());

           Button b = new Button ("Test Method") ;
           outer.add ( b );
           b.addClickHandler ( new ClickHandler () {
            public void onClick(ClickEvent event) {
                Window.alert ( "GwittIt.java:  apiClient.getSessionRecord:  " + apiClient.getSessionRecord () );
            }
               
           });
           RootPanel.get().add ( outer );
    
	}
	/**
	 * Demonstrates how to use the facebook api. 
	 */
	public void onModuleLoad() {
	    
	    // First do init stuff.
	    FacebookConnect.init( Config.API_KEY, "/xd_receiver.htm", eventBus );

		History.addValueChangeHandler(this);
		
		// Need this to catch the login event.

		this.topMenu = new TopMenu ( eventBus );
		this.loginWidget = new LoginBox ( eventBus );

        frontpageLink.addClickHandler( this );
        showcaseLink.addClickHandler( this ); 
        // Get login events and rerender  whatever its necessary
        listenToLogin();

        outer.getElement().setId("GwittIt");
        outer.ensureDebugId("GwittIt");

        outer.add ( topMenu );
        
		// -------------------------------------------------------------------------
		// This is all you need to initialize Facebook Connect:
		// Setup xd_receiever and create a callback for login.
		// See http://wiki.developers.facebook.com/index.php/Cross-domain_communication_channel for documentation
		
		
		if ( apiClient.isSessionValid () ) {
            renderWhenConnected();
		} else {
		    outer.add ( loginWidget );
		}
		
		/** Does not work
		FacebookConnect.getConnectState ( new DefaultAsyncCallback<ConnectState> () {
		    @Override
            public void onSuccess(ConnectState connectState) {
		        Window.alert ( "Connect State = " + connectState );
		        if ( connectState == ConnectState.connected ) {
		        } else {
		            outer.add ( loginWidget  );
		        }
            }
		    
		});
		*/
		RootPanel.get().add ( outer );
		//Xfbml.parse( outer.getElement() );
	}
	
	
	public void renderWhenConnected () {
	    
	    outer.clear ();
	    outer.add ( topMenu );
        renderHeader();
        outer.add ( splitInnerBody );
        renderPage ( Window.Location.getHash() );
	}
	
	public void renderHeader () {
        HorizontalPanel horizontalMenuBar = new HorizontalPanel ();
        horizontalMenuBar.add( new HTML ( "<b>Browse:</b>" ) );
        horizontalMenuBar.add ( frontpageLink );
        horizontalMenuBar.add ( showcaseLink );
        horizontalMenuBar.addStyleName("horizontalMenuBar");
   
        outer.add ( horizontalMenuBar );
	}

	/**
	 * Route to correct page 
	 */
	public void renderPage ( String hash ) {
	    
	    showcaseLink.removeStyleName ( "selected" );
	    frontpageLink.removeStyleName ( "selected" );
	    
		splitInnerBody.clear ();

		if ( "#frontpage".equals ( hash ) || hash == null || "#".equals ( hash ) || "".equals( hash ) )  {
	       
		    frontpageLink.addStyleName ( "selected" );

			if ( frontpage == null ) {
				frontpage = new Frontpage ( apiClient, eventBus );
			}
			Panel menu = frontpage.getVerticalMenu ();
			splitInnerBody.add ( menu );
			splitInnerBody.add ( frontpage );

		} else if ( "#showcase".equals ( hash ) || "#testClient".equals( hash ) ) {
	        showcaseLink.addStyleName ( "selected" );
			splitInnerBody .add ( new ShowcaseClient () );
		} else {
			Window.alert ( "unkown path " + hash );
		}

	}

	/**
	 * History support
	 */
	public void onValueChange(ValueChangeEvent<String> event) {
		renderPage ("#" + event.getValue() );
	}

	/**
	 * Listen to events in the application, most likely login
	 */
	private void listenToLogin () {
        eventBus.addHandler(LoginEvent.getType(), new LoginHandler () {
			public void onLogin() {
			    renderWhenConnected ();
			   	
			   	// Tell me whenever someone logs in to the application
			   	sendNotificationToDeveloper ();
			}
        });
	}

	/**
	 * Tell me who is testing the application.
	 */
	private void sendNotificationToDeveloper () {
	    String notification = " logged in using " + getUserAgent ();
	    apiClient.notifications_send (  new Long ( 744450545 ), 
	                                    notification,  new Callback<JavaScriptObject> () );
	}
	
	public void onClick(ClickEvent event) {
		Anchor a  = ( Anchor)event.getSource();
		if ( a == frontpageLink ) {
			History.newItem( "frontpage");
		} else if ( a == showcaseLink ) {
			History.newItem( "showcase") ;
		}
	}
	
	public static native String getUserAgent() /*-{
	    return navigator.userAgent.toLowerCase();
	}-*/;
	
}

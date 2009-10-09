package com.gwittit.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwittit.client.example.ShowcaseClient;
import com.gwittit.client.facebook.ApiFactory;
import com.gwittit.client.facebook.FacebookApi;
import com.gwittit.client.facebook.FacebookConnect;
import com.gwittit.client.facebook.UserInfo;
import com.gwittit.client.facebook.events.LoginEvent;
import com.gwittit.client.facebook.events.LoginHandler;
import com.gwittit.client.facebook.xfbml.Xfbml;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GwittIt implements EntryPoint, ClickHandler, ValueChangeHandler<String> {
	
	// Where we hold everything
	private VerticalPanel outer = new VerticalPanel ();
		
	private HorizontalPanel inner = new HorizontalPanel ();
	
	// TopMenu displayed on every page
	private TopMenu topMenu;

	// Main page, display facebook stream
	private Frontpage frontpage;

	// Horizontal Menu links
	final Anchor frontpageLink = new Anchor ( "Frontpage");
	final Anchor showcaseLink = new Anchor ( "Showcase");
	
	// Where we hold the main body
	private SimplePanel example = new SimplePanel ();
	
	// Create the api once and for all
	private FacebookApi apiClient = ApiFactory.newApiClient( Config.API_KEY );
	
	// Fire event to the app
	private HandlerManager eventBus;
	
	// Display Login Dialog
	private LoginBox loginWidget;
	
	/**
	 * Demonstrates how to use the facebook api. 
	 */
	public void onModuleLoad() {
	
		History.addValueChangeHandler(this);
		
		// Need this to catch the login event.
		this.eventBus = new HandlerManager ( null );
		this.topMenu = new TopMenu ( eventBus );
		this.loginWidget = new LoginBox ( eventBus );

		// -------------------------------------------------------------------------
		// This is all you need to initialize Facebook Connect:
		// Setup xd_receiever and create a callback for login.
		// See http://wiki.developers.facebook.com/index.php/Cross-domain_communication_channel for documentation
		FacebookConnect.init( Config.API_KEY, "/xd_receiver.htm", eventBus );

		
		// Get login events and rerender  whatever its necessary
		listenToLogin();

		outer.getElement().setId("GwittIt");
		outer.ensureDebugId("GwittIt");
		
		//menuBar.addStyleName("menuBar" );
		//menuBarWrapper.addStyleName ( "menuBarWrapper" );
		
		outer.add ( topMenu );
		
		
		if ( UserInfo.isLoggedIn() ) {
			HorizontalPanel horizontalMenuBar = new HorizontalPanel ();
			horizontalMenuBar.add( new HTML ( "<b>Browse:</b>" ) );
			horizontalMenuBar.add ( frontpageLink );
			horizontalMenuBar.add ( showcaseLink );
			frontpageLink.addClickHandler( this );
			showcaseLink.addClickHandler( this );		
			horizontalMenuBar.addStyleName("horizontalMenuBar");
			outer.add ( horizontalMenuBar );
		}
		
		outer.add ( inner );
		
		if ( UserInfo.isLoggedIn () ) {
		// Render page
			renderPage ( Window.Location.getHash() );
		} else {
			outer.add ( loginWidget );
		}

		
		RootPanel.get().add ( outer );
		FacebookConnect.getLoggedInUser() ;
		//showFriendList ();
		Xfbml.parse( outer.getElement() );

	}

	
	
	/**
	 * Route to correct page 
	 */
	public void renderPage ( String hash ) {

		inner.clear ();

		if ( "#frontpage".equals ( hash ) || hash == null || "#".equals ( hash ) || "".equals( hash ) )  {

			if ( frontpage == null ) {
				frontpage = new Frontpage ( apiClient, eventBus );
			}
			Panel menu = frontpage.getVerticalMenu ();
			inner.add ( menu );
			inner.add ( frontpage );

		} else if ( "#showcase".equals ( hash ) || "#testClient".equals( hash ) ) {
			inner .add ( new ShowcaseClient () );
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
			   	outer.clear();
			   	onModuleLoad();
			}
        });
	}

	public void onClick(ClickEvent event) {
		Anchor a  = ( Anchor)event.getSource();
		
		if ( a == frontpageLink ) {
			History.newItem( "frontpage");
		} else if ( a == showcaseLink ) {
			History.newItem( "showcase") ;
			
		}
	}
}

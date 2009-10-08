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
import com.gwittit.client.example.TestClient;
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
public class GwittIt implements EntryPoint, ClickHandler, ValueChangeHandler<String>, ResizeHandler {
	
	// Where we hold everything
	private VerticalPanel outer = new VerticalPanel ();
		
	private HorizontalPanel inner = new HorizontalPanel ();
	
	// TopMenu displayed on every page
	private TopMenuGwittee topMenu;

	
	private Frontpage frontpage;

	final Anchor frontPage = new Anchor ( "Frontpage");
	final Anchor testClient = new Anchor ( "Showcase");
	
	// Where we hold the main body
	private SimplePanel example = new SimplePanel ();
	
	// Create the api once and for all
	private FacebookApi apiClient = ApiFactory.newApiClient( Config.API_KEY );
	
	// Fire event to the app
	private HandlerManager eventBus;
	
	// Display Login Dialog
	private LoginDialogWidget loginWidget;
	
	/**
	 * Demonstrates how to use the facebook api. 
	 */
	public void onModuleLoad() {
	
		History.addValueChangeHandler(this);
		Window.addResizeHandler(this);
		
		// Need this to catch the login event.
		this.eventBus = new HandlerManager ( null );
		this.topMenu = new TopMenuGwittee ( eventBus );
		this.loginWidget = new LoginDialogWidget ( eventBus );

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
			HorizontalPanel mainMenubar = new HorizontalPanel ();
			mainMenubar.add( new HTML ( "<b>Browse:</b>" ) );
			mainMenubar.add ( frontPage );
			mainMenubar.add ( testClient );
			frontPage.addClickHandler( this );
			testClient.addClickHandler( this );		
			mainMenubar.addStyleName("mainMenubar");
			outer.add ( mainMenubar );
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
			Panel menu = frontpage.getMenu ();
			inner.add ( menu );
			inner.add ( frontpage );

		} else if ( "#testClient".equals( hash ) ) {
			
			inner .add ( new TestClient () );
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

	/**
	 * Set a suitable leftmargin. Can be done with css ?
	 */
	public void sxxetLeftMargin () {
		
		if ( Window.getClientWidth() > 930 ) {
			
			int lm = (Window.getClientWidth()-930)/2;
			outer.getElement().setAttribute("style", "margin-left:"  + lm + "px" );
		}
		else {
			outer.getElement().setAttribute("style", "margin-left: 10px" );
		}
	}
	
	/**
	 * Handle resize , set som basic styles.
	 */
	public void onResize(ResizeEvent event) {
		
	}


	public void onClick(ClickEvent event) {
		Anchor a  = ( Anchor)event.getSource();
		
		if ( a == frontPage ) {
			History.newItem( "frontpage");
		} else if ( a == testClient ) {
			History.newItem( "testClient") ;
			
		}
	}
}

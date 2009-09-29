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
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.examples.PhotosGetAlbumsExample;
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

	private Anchor streamGetLink = new Anchor ( "News Feed" );
	private Anchor photosGetLink = new Anchor ( "Photos" );

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
		

		// Render page
		render ( Window.Location.getHash() );
		
		RootPanel.get().add ( outer );
		Xfbml.parse( outer.getElement() );

	}

	
	/**
	 * Render based on hash 
	 */
	public void render( String hash ) {
		
		outer.clear();
		outer.add ( topMenu );

		if ( UserInfo.isLoggedIn() ) {
			
			setLeftMargin();

			
			
			
			// Add the app.
			frontpage = new Frontpage ( apiClient, eventBus );
			
			Panel menu = frontpage.getMenu ();
			
			
			inner.add ( menu );
			inner.add ( example );
	
			outer.add ( inner );

			renderPage ( hash );
			
		} else {
		
			outer.add ( loginWidget );
		}
		
	}

	
	/**
	 * Route to correct page 
	 */
	public void renderPage ( String hash ) {
		
		if ( !UserInfo.isLoggedIn() ) {
			render ( Window.Location.getHash() );
			return ;
		}
	
		if ( "#stream.get".equals ( hash ) || hash == null || "#".equals ( hash ) || "".equals( hash ) )  {
			example.setWidget( frontpage );
		} else if ( "#photos.getAlbums".equals ( hash ) ) {
			example.setWidget( new PhotosGetAlbumsExample ( apiClient ) ) ;
		} else {
			Window.alert ( "unkown path " + hash );
		}

	}

	
	/**
	 * Get whatever user clicks.
	 */
	public void onClick(ClickEvent event) {
		if ( event.getSource() == streamGetLink ) {
			History.newItem("stream.get");
		} else if ( event.getSource() == photosGetLink ) {
			History.newItem( "photos.getAlbums");
		} else {
			Window.alert ( "Unknown link clicked");
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
			public void loginStatusChanged(LoginEvent event) {
			   	outer.clear();
            	render ( Window.Location.getHash() );				
			}
        });
	}

	/**
	 * Set a suitable leftmargin. Can be done with css ?
	 */
	public void setLeftMargin () {
		
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
		
		setLeftMargin();
	}
}

package com.gwittit.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.events.AppEvents;
import com.gwittit.client.events.DefaultEventHandler;
import com.gwittit.client.events.AppEvents.Event;
import com.gwittit.client.examples.PhotosGetAlbumsExample;
import com.gwittit.client.facebook.ApiFactory;
import com.gwittit.client.facebook.FacebookApi;
import com.gwittit.client.facebook.FacebookCallback;
import com.gwittit.client.facebook.FacebookConnectInit;
import com.gwittit.client.facebook.UserInfo;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GwittIt implements EntryPoint, ClickHandler, ValueChangeHandler<String>, ResizeHandler {
	
	
	private VerticalPanel outer = new VerticalPanel ();
	
	private HorizontalPanel inner = new HorizontalPanel ();
	
	private VerticalPanel menu = new VerticalPanel ();
	
	private Frontpage frontpage;
	
	private TopMenuGwittee topMenu;
	
	private Anchor streamGet = new Anchor ( "News Feed" );
	private Anchor photosGet = new Anchor ( "Photos" );

	private SimplePanel example = new SimplePanel ();
	
	private FacebookApi apiClient = ApiFactory.newApiClient( Config.API_KEY );
	
	private HandlerManager eventBus;
	
	private NeedLoginWidget loginWidget;
	
	/**
	 * Demonstrates how to use the facebook api. 
	 */
	public void onModuleLoad() {
	
		History.addValueChangeHandler(this);
		Window.addResizeHandler(this);
		
		// Create new instances
		this.eventBus = new HandlerManager ( null );
		this.topMenu = new TopMenuGwittee ( eventBus );
		this.loginWidget = new NeedLoginWidget ( eventBus );

		// Get login events and rerender  whatever its necessary
		listenToLogin();

		outer.getElement().setId("GwittIt");
		outer.ensureDebugId("GwittIt");
		
		// -------------------------------------------------------------------------
		// This is all you need to connect to facebook:
		//   Start Facebook Connect
		//    Get a new instance
		// --------------------------------------------------------------------------
		FacebookConnectInit fbInit = FacebookConnectInit.newInstance();
		
		// Setup xd_receiever and create a callback for login.
		// See http://wiki.developers.facebook.com/index.php/Cross-domain_communication_channel for documentation
		fbInit.init( Config.API_KEY, "/xd_receiver.htm", new FacebookCallback () {

			// Handle any login error here.
			public void onError(JSONObject o) {
				// Handle error
			}
			
			// This method is fired when the user logs in, do basic app stuff here. Fire events etc.
			public void onSuccess(JSONObject o) {
				GWT.log( "User logged in sesskey = " + UserInfo.getSessionKey(), null);
				AppEvents loginEvent = new AppEvents ( Event.LOGIN );
                eventBus.fireEvent( loginEvent );
                render ( Window.Location.getHash() );
			} 
		});

		// Render page
		render ( Window.Location.getHash() );
		setLeftMargin();
		RootPanel.get().add ( outer );
	}

	
	/**
	 * Render based on hash 
	 */
	public void render( String hash ) {
		
		outer.clear();
		outer.add ( topMenu );

		if ( UserInfo.isLoggedIn() ) {
			menu.addStyleName("menu");
			menu.add ( wrapMenuItem ( streamGet ) );
			menu.add ( wrapMenuItem ( photosGet ) );
			
			streamGet.addClickHandler(this);
			photosGet.addClickHandler(this);
			
			// Add the app.
			frontpage = new Frontpage ( apiClient, eventBus );
			
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
	 * Wrap menu item in a div tag.
	 */
	private Widget wrapMenuItem  ( Anchor anchor ) {
		SimplePanel menuItem = new SimplePanel ();
		menuItem.addStyleName("menuItem");
		menuItem.setWidget( anchor );
		return menuItem;
	}

	
	/**
	 * Get whatever user clicks.
	 */
	public void onClick(ClickEvent event) {
		if ( event.getSource() == streamGet ) {
			History.newItem("stream.get");
		} else if ( event.getSource() == photosGet ) {
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
        eventBus.addHandler(AppEvents.TYPE, new DefaultEventHandler () {
                public void login() {
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

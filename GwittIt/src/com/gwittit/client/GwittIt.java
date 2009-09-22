package com.gwittit.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
public class GwittIt implements EntryPoint, ClickHandler, ValueChangeHandler<String> {
	
	
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
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
	
		History.addValueChangeHandler(this);
		
		
		this.eventBus = new HandlerManager ( null );

		topMenu = new TopMenuGwittee ( eventBus );
		outer.getElement().setId("GwittIt");
		this.loginWidget = new NeedLoginWidget ( eventBus );
		
		// Start Facebook Connect
		// Get a new instance
		FacebookConnectInit fbInit = FacebookConnectInit.newInstance();
		
		// Setup xd_receiever and create a callback for login.
		// See http://wiki.developers.facebook.com/index.php/Cross-domain_communication_channel for documentation
		fbInit.init( Config.API_KEY, "/xd_receiver.htm", new FacebookCallback () {
			public void onError(JSONObject o) {
				// Handle error
			}
			// User has logged in
			public void onSuccess(JSONObject o) {
				GWT.log( "User logged in sesskey = " + UserInfo.getSessionKey(), null);
				AppEvents loginEvent = new AppEvents ( Event.LOGIN );
                eventBus.fireEvent( loginEvent );
                render ( Window.Location.getHash() );
			} 
		});

		
		render ( Window.Location.getHash() );
		
		RootPanel.get().add ( outer );
	}

	
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
			frontpage = new Frontpage ( eventBus );
			
			inner.add ( menu );
			inner.add ( example );
	
			outer.add ( inner );

			renderPage ( hash );
			
		} else {
			outer.add ( loginWidget );
		}
		
	}

	public void renderPage ( String hash ) {
		
		if ( !UserInfo.isLoggedIn() ) {
			render ( Window.Location.getHash() );
			return ;
		}
	
		if ( hash == null || "#".equals ( hash ) || "".equals( hash ) )  {
			example.setWidget( frontpage );
		} else if ( "#photos.getAlbums".equals ( hash ) ) {
			example.setWidget( new PhotosGetAlbumsExample ( apiClient ) ) ;
		} else {
			Window.alert ( "unkown path " + hash );
		}

	}

	private Widget wrapMenuItem  ( Anchor anchor ) {
		SimplePanel menuItem = new SimplePanel ();
		menuItem.addStyleName("menuItem");
		menuItem.setWidget( anchor );
		return menuItem;
	}
	
	public void onClick(ClickEvent event) {
		
		example.clear();
		
		if ( event.getSource() == streamGet ) {
			History.newItem("");
		} else if ( event.getSource() == photosGet ) {
			History.newItem( "photos.getAlbums");
		}
	}


	public void onValueChange(ValueChangeEvent<String> event) {
		renderPage ("#" + event.getValue() );
	}
}

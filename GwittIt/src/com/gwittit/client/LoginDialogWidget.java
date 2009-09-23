package com.gwittit.client;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwittit.client.events.AppEvents;
import com.gwittit.client.events.AppEvents.Event;
import com.gwittit.client.facebook.ApiFactory;
import com.gwittit.client.facebook.FacebookCallback;
import com.gwittit.client.facebook.xfbml.FbLoginButton;


public class LoginDialogWidget extends Composite {
	
	private VerticalPanel outer = new VerticalPanel ();
	
	private HorizontalPanel inner = new HorizontalPanel ();
		// Callback that we define when setting up project.
	private FbLoginButton loginButton = new FbLoginButton ("facebookConnectLogin()");
	
	private Anchor loginLink = new Anchor ( "don't see a button? Click here to login");
	
	public LoginDialogWidget ( final HandlerManager eventBus ) {
		
		// Login with the javascript api. GWT client doesnt render the fb:login-button
		loginLink.addClickHandler( new ClickHandler () {
			public void onClick(ClickEvent event) {
				
				ApiFactory.connectApiClient(Config.API_KEY).requireSession( new FacebookCallback () {
					public void onError(JSONObject o) {
					}

					public void onSuccess(JSONObject o) {
						//AppEvents loginEvent = new AppEvents ( Event.LOGIN );
						//eventBus.fireEvent( loginEvent );
					}
					
				});
			}
			
		});
		
		outer.getElement().setId ( "NeedLoginWidget");
		inner.addStyleName("inner");
		
		outer.add ( new HTML ( "<h1>Please login with your facebook id</h1>" ) );
		outer.add ( new HTML ( "This demo uses facebook data heavily to demonstrate API calls etc so you might as well login right away" ) );
		outer.add ( new HTML  ( "<b>gwittit</b> demonstrates the use of gwt+facebook, a GWT library for developing facebook apps " ) );
		inner.setSpacing( 10 );
		inner.add ( new HTML ( "Click the button to allow this application to access your facebook account" ) );
		inner.add ( loginButton );
		
		outer.add ( inner );
		outer.add ( loginLink );
		
		initWidget ( outer );
		
		
	}
	

}

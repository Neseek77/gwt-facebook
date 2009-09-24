package com.gwittit.client;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwittit.client.facebook.FacebookConnect;
import com.gwittit.client.facebook.events.EventHelper;
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
		
		outer.getElement().setId ( "NeedLoginWidget");
		inner.addStyleName("inner");
		
		outer.add ( new HTML ( "<h1>Please login with your facebook id</h1>" ) );
		outer.add ( new HTML ( "This demo uses facebook data heavily to demonstrate API calls etc so you might as well login right away" ) );
		inner.setSpacing( 10 );
		inner.add ( new HTML ( "Click the button to allow this application to access your facebook account" ) );
		inner.add ( loginButton );
		
		outer.add ( inner );
		outer.add ( loginLink );
		
		
		HorizontalPanel leftRightPanel = new HorizontalPanel ();
		VerticalPanel left = new VerticalPanel ();
		
		Image image = new Image ( "/samplefront.png");
		image.setWidth( "510px" );
		
		left.add ( image );
		HTML sourceCode = new HTML ( "This project on Gogle code : <a target=_blank href=\"http://code.google.com/p/gwt-facebook/\"> http://code.google.com/p/gwt-facebook/ </a>" );
		sourceCode.addStyleName ( "sourceCode");
		left.add ( sourceCode );
		
		leftRightPanel.add ( left );
		
		
		outer.setWidth( "400px");
		leftRightPanel.add ( outer );

		initWidget ( leftRightPanel );
		
		
	}
	

}

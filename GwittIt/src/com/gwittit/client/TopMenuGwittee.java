package com.gwittit.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.gwittit.client.facebook.UserInfo;
import com.gwittit.client.facebook.xfbml.FbName;

/**
 * 
 *
 */
public class TopMenuGwittee extends Composite {

	private HorizontalPanel outer = new HorizontalPanel ();
	private HorizontalPanel loginInfo = new HorizontalPanel ();
	
	private HandlerManager eventBus ;
	
	public TopMenuGwittee ( HandlerManager eventBus ) {
		
		this.eventBus = eventBus ;
		
		listenToLogin ();
		
		outer.getElement().setId( "TopMenuGwittee" );
		loginInfo.addStyleName( "loginInfo" );
		
		outer.add ( new HTML ( "&nbsp;" ) );
		outer.add ( loginInfo );
		
		if ( UserInfo.isLoggedIn() ) {
			renderLoginInfo ();
		}
		initWidget ( outer );
	}
	
	
	private void renderLoginInfo () {

		loginInfo.clear();
		
		FbName fbName = new FbName ( UserInfo.getUidLong(), false );
		fbName.setUseyou(false);

		
		GWT.log ( "TopMenuGwittee: render " + fbName.toString(), null );

		loginInfo.add ( new HTML ( "Welcome&nbsp;"));
		loginInfo.add ( fbName );
		
	}
	
	private void listenToLogin () {
		/*
        eventBus.addHandler(AppEvents.TYPE, new DefaultEventHandler () {
                public void login() {
                	renderLoginInfo ();
                }
        });
        */
	}
}

package com.gwittit.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.gwittit.client.facebook.ApiFactory;
import com.gwittit.client.facebook.FacebookApi;
import com.gwittit.client.facebook.FacebookConnect;
import com.gwittit.client.facebook.xfbml.FbName;
import com.gwittit.client.facebook.xfbml.FbProfilePic;

/**
 * Top Menu
 */
public class TopMenu extends Composite {

	private HorizontalPanel outer = new HorizontalPanel ();
	private HorizontalPanel loginInfo = new HorizontalPanel ();
	
	private HandlerManager eventBus ;
	
	private FacebookApi apiClient = ApiFactory.getInstance ();
	
	public TopMenu () {
		
		outer.getElement().setId( "TopMenu" );
		outer.add ( new HTML ("<h1>gwt-facebook showcase</h1>" ) );
		
		loginInfo.addStyleName( "loginInfo" );
		
		outer.add ( new HTML ( "&nbsp;" ) );
		outer.add ( loginInfo );

		
		if ( apiClient.isSessionValid () ) {
            renderLoginInfo(); 
		}
		initWidget ( outer );
	}
	
	
	public void renderLoginInfo () {

		loginInfo.clear();
		
		Long uid = apiClient.getLoggedInUser ();
		FbName fbName = new FbName ( uid, false );
		fbName.setUseyou(false);

		FbProfilePic pic = new FbProfilePic ( uid, FbProfilePic.Size.square );
		pic.setSize("15px", "15px");
		
		GWT.log ( "TopMenu: render " + fbName.toString(), null );

		loginInfo.add ( fbName );

		SimplePanel tmp = new SimplePanel ();
		tmp.addStyleName( "miniProfilePic");
		tmp.setWidget( pic );
		
		
		loginInfo.add ( tmp );
		
		Anchor logout =new Anchor ( "Logout");
		logout.addClickHandler( new ClickHandler () {
			public void onClick(ClickEvent event) {
				FacebookConnect.logoutAndRedirect ("/");
			}
			
		});
		loginInfo.add ( logout );
	}
}

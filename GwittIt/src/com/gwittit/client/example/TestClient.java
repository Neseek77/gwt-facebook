package com.gwittit.client.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.Config;
import com.gwittit.client.facebook.ApiFactory;
import com.gwittit.client.facebook.FacebookApi;
import com.gwittit.client.facebook.UserInfo;
import com.gwittit.client.facebook.entities.FriendInfo;
import com.gwittit.client.facebook.entities.Photo;
import com.gwittit.client.facebook.xfbml.FbName;
import com.gwittit.client.facebook.xfbml.FbPhoto;
import com.gwittit.client.facebook.xfbml.FbProfilePic;
import com.gwittit.client.facebook.xfbml.Xfbml;
import com.gwittit.client.facebook.xfbml.FbPhoto.Size;

public class TestClient extends Composite implements ClickHandler {

	private HorizontalPanel outer = new HorizontalPanel ();
	private VerticalPanel inner = new VerticalPanel ();
	private Panel methods = new VerticalPanel ();
	
	private FacebookApi apiClient = ApiFactory.newApiClient(Config.API_KEY);
	
	final Anchor friendsAreFriendsLink  = new Anchor ( "friends.areFriends" );
	final Anchor friendsGetLink = new Anchor ( "friends.get" );
	final Anchor photosGetLink = new Anchor ( "photos.get");
	final Anchor photosGetAlbumLink = new Anchor ( "photos.getAlbums" );
	
	final VerticalPanel exampleWrapper = new VerticalPanel ();

	
	final Image loader = new Image ( "/loader.gif" );

	public TestClient () {

		friendsAreFriendsLink.addClickHandler( this );
		friendsGetLink.addClickHandler(this);
		photosGetLink.addClickHandler( this );
		photosGetAlbumLink.addClickHandler(this);
		
		exampleWrapper.setWidth( "700px");
		
		exampleWrapper.addStyleName("exampleWrapper");
		outer.getElement().setId( "TestClient" );
		outer.setSpacing ( 10 );
		inner.setWidth( "100%");
		methods.addStyleName("methods");
		
		methods.add ( friendsAreFriendsLink );
		methods.add ( friendsGetLink );
		methods.add ( photosGetLink );
		methods.add ( photosGetAlbumLink );
		outer.add (  methods );
		
		outer.add (  decorate ( exampleWrapper ) );
		
		initWidget ( outer );
	}

	private DecoratorPanel decorate ( Panel p ) {
		DecoratorPanel dp = new DecoratorPanel () ; 
		dp.setWidget(p);
		
		return dp;
	}

	public void onClick(ClickEvent event) {
		Object clicked = event.getSource();
		
		exampleWrapper.clear();
		
		Example example = null;
		
		if ( clicked == friendsAreFriendsLink ) {
			example = new ShowFriendsAreFriends ();
		} else if ( clicked == photosGetLink ) {
			example = new ShowPhotosGet ();
		} else if ( clicked == friendsGetLink ) {
			example = new ShowFriendsGet ();
		} else if ( clicked == photosGetAlbumLink ) {
			example = new ShowPhotosGetAlbums ();
		}
		
		
		exampleWrapper.add( new HTML ( "<h2>" + example.getHeader () + "</h2>" ) );
		exampleWrapper.add( new HTML ( example.getDescription() ) ) ;
		exampleWrapper.add( new HTML ( "<hr/>" ) );
		
		exampleWrapper.add( example );

	}
}

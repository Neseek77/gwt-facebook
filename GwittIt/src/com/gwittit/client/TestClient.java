package com.gwittit.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.facebook.ApiFactory;
import com.gwittit.client.facebook.FacebookApi;
import com.gwittit.client.facebook.UserInfo;
import com.gwittit.client.facebook.entities.FriendInfo;
import com.gwittit.client.facebook.xfbml.FbName;
import com.gwittit.client.facebook.xfbml.FbProfilePic;
import com.gwittit.client.facebook.xfbml.Xfbml;

public class TestClient extends Composite implements ClickHandler {

	private VerticalPanel outer = new VerticalPanel ();
	
	private FacebookApi apiClient = ApiFactory.newApiClient(Config.API_KEY);
	
	final Anchor friendsAreFriendsLink  = new Anchor ( "friend_areFriends" );

	final SimplePanel resultWrapper = new SimplePanel ();
	
	final Image loader = new Image ( "/loader.gif" );
	public TestClient () {
		
		outer.getElement().setId( "TestClient" );
		outer.setWidth("600px");
		outer.add ( new HTML ( "<h1> Test Client for gwt-facebook</h1>" ) );
	
		outer.add( new HTML ( "<h2> Methods: </h2>"  ) ) ;
		outer.add ( friendsAreFriendsLink );
		resultWrapper.addStyleName("resultWrapper");
		
		outer.add ( resultWrapper );

		
		friendsAreFriendsLink.addClickHandler( this );
		
		initWidget ( outer );
	}

	
	private void executeFriendsAreFriends () {
		
		resultWrapper.setWidget(loader);
		final VerticalPanel result = new VerticalPanel ();
		
		
		result.getElement().setId ( "friendsAreFriendsResult" );
		
		Map<String,String> params = new HashMap<String,String> ();
		
		params.put ( "uids1", UserInfo.getUid()+",751836969,708775201");
		params.put ( "uids2", "709281400,560635378,709281400");
		
		apiClient.friends_areFriends(params, new AsyncCallback<List<FriendInfo>> () {

			public void onFailure(Throwable caught) {
				result.add( new HTML ( "" + caught ) ) ;
			}

			public void onSuccess(List<FriendInfo> friendInfoList) {
				
				result.add ( new HTML ( "Size " + friendInfoList.size() ) );
				for ( FriendInfo fi : friendInfoList ) {
					result.add ( 
							new HTML (
							new FbName  ( fi.getUid1() ).toString()  +  " friends with " +  new FbName ( fi.getUid2() ).toString() + " ? " + fi.getAreFriends() ) 
							);
				}

				resultWrapper.setWidget( result );
				Xfbml.parse ( result.getElement() );
			}
		});
	}


	public void onClick(ClickEvent event) {
		Object clicked = event.getSource();
		
		if ( clicked == friendsAreFriendsLink ) {
			executeFriendsAreFriends();
		}
	}
}

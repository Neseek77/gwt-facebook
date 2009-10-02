package com.gwittit.client.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwittit.client.Config;
import com.gwittit.client.facebook.ApiFactory;
import com.gwittit.client.facebook.FacebookApi;
import com.gwittit.client.facebook.UserInfo;
import com.gwittit.client.facebook.entities.FriendInfo;
import com.gwittit.client.facebook.xfbml.FbName;
import com.gwittit.client.facebook.xfbml.Xfbml;

public class ShowFriendsAreFriends extends Example {

	
	FacebookApi apiClient = ApiFactory.newApiClient(Config.API_KEY);
	
	private VerticalPanel resultWrapper = new VerticalPanel ();
	
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Execute friends.areFriends";
	}

	@Override
	public String getHeader() {
		return "Friends.areFriends";
	}

	
	public ShowFriendsAreFriends () {

			resultWrapper.add(getLoader());
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
					resultWrapper.clear ();
					
					result.add ( new HTML ( "Size " + friendInfoList.size() ) );
					for ( FriendInfo fi : friendInfoList ) {
						result.add ( 
								new HTML (
								new FbName  ( fi.getUid1() ).toString()  +  " friend with " +  new FbName ( fi.getUid2() ).toString() + " ? " + fi.getAreFriends() ) 
								);
					}

					resultWrapper.add( result );
					Xfbml.parse ( result.getElement() );
				}
			});
			initWidget ( resultWrapper );
	}
	
	
	}


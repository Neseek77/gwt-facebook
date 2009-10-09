package com.gwittit.client.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.Config;
import com.gwittit.client.facebook.ApiFactory;
import com.gwittit.client.facebook.FacebookApi;
import com.gwittit.client.facebook.UserInfo;
import com.gwittit.client.facebook.FacebookApi.FriendsAreFriendsParams;
import com.gwittit.client.facebook.entities.FriendInfo;
import com.gwittit.client.facebook.xfbml.FbName;
import com.gwittit.client.facebook.xfbml.Xfbml;

/**
 * Showcase for method call  <code>friends.areFriends</code>
 * 
 * @author olamar72
 */
public class Friends_areFriends extends Showcase {
	
	public Friends_areFriends() {
		super ( "friends.areFriends");
	}
	
	@Override
	public Widget createWidget () {
			final VerticalPanel resultWrapper = new VerticalPanel ();

			resultWrapper.add(getLoader());
			final VerticalPanel result = new VerticalPanel ();
			result.getElement().setId ( "friendsAreFriendsResult" );
			
			Map<Enum<FriendsAreFriendsParams>,String> params = new HashMap<Enum<FriendsAreFriendsParams>,String> ();
			params.put ( FriendsAreFriendsParams.uids1, UserInfo.getUid()+",751836969,708775201");
			params.put ( FriendsAreFriendsParams.uids2, "709281400,560635378,709281400");
			
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
								new FbName  ( fi.getUid1() )+  " friend with " +  
								new FbName ( fi.getUid2() ) + " ? " + fi.getAreFriends() ) 
								);
					}

					resultWrapper.add( result );
					Xfbml.parse ( result.getElement() );
				}
			});
			return resultWrapper;
	}
	
	
	}


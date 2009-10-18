package com.gwittit.client.example;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
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
			
			List<Long> uids1 = new ArrayList<Long>();
			uids1.add ( apiClient.getLoggedInUser () );
			uids1.add ( new Long ( 751836969 ) );
			uids1.add ( new Long ( 708775201 ) );
			
			List<Long> uids2 = new ArrayList<Long> ();
			uids2.add ( new Long ( 709281400 ) );
			uids2.add ( new Long ( 560635378 ) );
			uids2.add ( new Long ( 709281400 ) );
			
			apiClient.friends_areFriends(uids1, uids2, new AsyncCallback <List <FriendInfo>> () {
				public void onFailure(Throwable caught) {
				    handleFailure ( caught );
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


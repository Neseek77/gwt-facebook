package com.gwittit.client.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.example.FriendSelector.FriendSelectionHandler;
import com.gwittit.client.facebook.xfbml.FbName;
import com.gwittit.client.facebook.xfbml.FbProfilePic;
import com.gwittit.client.facebook.xfbml.Xfbml;
import com.gwittit.client.facebook.xfbml.FbProfilePic.Size;

/**
 * Method friends.getMutualFriends
 */
public class Friends_getMutualFriends extends Example {
	
	static String method = "friends_getMutualFriends";
	
	public Friends_getMutualFriends () {
		super ( method );
	}

	
	@Override
	public Widget createWidget () {
		
		final VerticalPanel outer = new VerticalPanel ();
		final VerticalPanel mutualFriends = new VerticalPanel ();
		mutualFriends.getElement().setId( "Friends_getMutualFriends-mutualFriends");
		
		
		// Let the user pick a friends
		FriendSelector fs = new FriendSelector ();
		fs.addFriendSelectionHandler(new FriendSelectionHandler () {

			// Check if current logged in user has common friends with selected.
			public void onSelected(final Long uid) {
				
				mutualFriends.clear();
				addLoader ( mutualFriends);
				
				Map<String,String> params =new HashMap<String,String> ();
				params.put("target_uid", "" + uid );
				
				
				apiClient.friends_getMutualFriends(params, new AsyncCallback<List<Long>> () {

					public void onFailure(Throwable caught) {
						handleFailure ( caught );
					}

					public void onSuccess(List<Long> result) {
						removeLoader ( mutualFriends );
		
						mutualFriends.add( new HTML ( "Number of mutual friends " + result.size() + " with " + new FbName ( uid ) ) );
						for ( Long uid : result ) {
							FbProfilePic pp = new FbProfilePic ( uid,Size.small);
							mutualFriends.add( pp );
						}
						Xfbml.parse( mutualFriends );
					}
					
				});
				
			}
			
		});

		outer.add ( fs );
		outer.add ( mutualFriends );

		return outer;
		
	}
}

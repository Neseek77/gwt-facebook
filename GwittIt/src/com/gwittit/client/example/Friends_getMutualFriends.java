package com.gwittit.client.example;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.example.FriendSelector.FriendSelectionHandler;
import com.gwittit.client.facebook.ui.ProfilePicsPanel;
import com.gwittit.client.facebook.xfbml.FbName;

/**
 * Showcase for method call <code>friends.getMutualFriends</code>
 * 
 * @author olamar72
 */
public class Friends_getMutualFriends extends Showcase {
	
	static String method = "friends.getMutualFriends";
	
	public Friends_getMutualFriends () {
	
		final VerticalPanel outer = new VerticalPanel ();
		final VerticalPanel mutualFriends = new VerticalPanel ();
		mutualFriends.getElement().setId( "Friends_getMutualFriends-mutualFriends");
		
		
		// Let the user pick a friends
		FriendSelector fs = new FriendSelector ();
		fs.addFriendSelectionHandler(new FriendSelectionHandler () {

			// Check if current logged in user has common friends with selected.
			public void onSelected( final Long targetUid ) {
				
				mutualFriends.clear();
				addLoader ( mutualFriends);
				
				// Call facebook
				apiClient.friendsGetMutualFriends( targetUid, new AsyncCallback<List<Long>> () {
					public void onFailure(Throwable caught) {
						handleFailure ( caught );
					}
					public void onSuccess(List<Long> result) {
						removeLoader ( mutualFriends );
						mutualFriends.add( new HTML ( "Number of mutual friends " + result.size() + " with " + new FbName ( targetUid ) ) );
						ProfilePicsPanel p = new ProfilePicsPanel ( result );
						mutualFriends.add ( p );
					}
					
				});
				
			}
			
		});

		outer.add ( fs );
		outer.add ( mutualFriends );

		initWidget ( outer ) ;
		
	}
}

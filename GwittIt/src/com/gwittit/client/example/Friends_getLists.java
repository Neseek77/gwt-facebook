package com.gwittit.client.example;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.facebook.entities.FriendList;

/**
 * Showcase for method call  <code>friends_getLists</code>
 * 
 * @author olamar72
 */
public class Friends_getLists extends Showcase { 
	
	private final static String method = "friends.getLists";
	
	public Friends_getLists () {
		super ( method );
		
	}
	
	@Override
	public Widget createWidget () {
		
		final VerticalPanel outer = new VerticalPanel ();
		outer.getElement().setId(  method );

		addLoader ( outer );

		
		// Call facebook
		apiClient.friends_getLists( new AsyncCallback<List<FriendList>> () {

			public void onFailure(Throwable caught) {
				handleFailure ( caught );
			}
			public void onSuccess(List<FriendList> result) {
				removeLoader ( outer );
				for ( FriendList fl : result ) {
					outer.add ( new HTML ( fl.getFlid() + "/" + fl.getName()  ) ); 
				}
			}
			
		});
		
		return outer;
		
	}

}

package com.gwittit.client.example;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.facebook.ui.ProfilePicsPanel;

/**
 * Showcase for method call <code>friends.getAppUsers</code>
 * 
 * @author olamar72
 *
 */
public class Friends_getAppUsers extends Showcase {

	
	public Friends_getAppUsers () {

		final VerticalPanel outer = new VerticalPanel ();
		outer.getElement().setId( "Friends_getAppUsers" );
		addLoader ( outer );
				
		apiClient.friendsGetAppUsers( new AsyncCallback<List<Long>> () {

			public void onFailure(Throwable caught) {
				handleFailure ( caught );
			}
			public void onSuccess(List<Long> result) {
				removeLoader ( outer );
				outer.add ( new ProfilePicsPanel ( result ) ) ;
			}
			
		});
		
		initWidget ( outer ) ;
		
	}
	
}

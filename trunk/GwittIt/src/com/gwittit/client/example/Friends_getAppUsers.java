package com.gwittit.client.example;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.facebook.xfbml.FbName;
import com.gwittit.client.facebook.xfbml.Xfbml;

/**
 * Showcase for method call <code>friends.getAppUsers</code>
 * 
 * @author olamar72
 *
 */
public class Friends_getAppUsers extends Example {

	
	public Friends_getAppUsers () {
		super ( "friends.getAppUsers" );
	}
	
	@Override
	public Widget createWidget () {

		final VerticalPanel outer = new VerticalPanel ();
		outer.getElement().setId( "Friends_getAppUsers" );
		addLoader ( outer );
		
		apiClient.friends_getAppUsers(null, new AsyncCallback<List<Long>> () {

			public void onFailure(Throwable caught) {
				handleFailure ( caught );
			}

			public void onSuccess(List<Long> result) {
				removeLoader ( outer );
				for ( Long uid : result ) {
					outer.add ( new HTML ( uid + "/" + new FbName ( uid ).toString() ) );
				}
				Xfbml.parse( outer );
			}
			
		});
		
		return outer;
		
	}
	
}

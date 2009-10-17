package com.gwittit.client.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.facebook.ui.ProfilePicsPanel;

/**
 * Showcase for method call <code>friends.get</code>
 * 
 * @author olamar72
 */
public class Friends_get extends Showcase  {

	static String method = "friends.get";
	
	public Friends_get () {
		super ( method );
	}

	@Override
	public Widget createWidget () {
		final VerticalPanel outer = new VerticalPanel ();

		outer.add(getLoader () );
		final FlowPanel flow = new FlowPanel ();
		flow.setWidth( "500px");
		flow.getElement().setId( "friendsget");
		
		// Call facebook
		apiClient.friends_get( new AsyncCallback<List<Long>> () {

			public void onFailure(Throwable caught) {
				handleFailure ( caught );
			}
			public void onSuccess(List<Long> result) {
				outer.remove( getLoader () );
				
				ProfilePicsPanel p = new ProfilePicsPanel ( result );
				outer.add ( p );
			}
			
		});
		return  outer ;
	}

}

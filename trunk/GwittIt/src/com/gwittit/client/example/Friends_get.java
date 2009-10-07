package com.gwittit.client.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.Config;
import com.gwittit.client.facebook.ApiFactory;
import com.gwittit.client.facebook.FacebookApi;
import com.gwittit.client.facebook.xfbml.FbName;
import com.gwittit.client.facebook.xfbml.Xfbml;

/**
 * Showcase for method call <code>friends.get</code>
 * 
 * @author olamar72
 */
public class Friends_get extends Example  {

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
		
		Map<String,String> params = new HashMap<String,String> ();

		// Call facebook
		apiClient.friends_get(params, new AsyncCallback<List<Long>> () {

			public void onFailure(Throwable caught) {
				handleFailure ( caught );
			}

			public void onSuccess(List<Long> result) {
				
				outer.remove( getLoader () );
				for ( Long uid : result ) {
					flow.add ( new HTML ( new FbName ( uid ) + ", " ));
				}
				outer.add ( flow );
				Xfbml.parse( flow.getElement() );
			}
			
		});
		return  outer ;
	}

}

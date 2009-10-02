package com.gwittit.client.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwittit.client.Config;
import com.gwittit.client.facebook.ApiFactory;
import com.gwittit.client.facebook.FacebookApi;
import com.gwittit.client.facebook.xfbml.FbName;
import com.gwittit.client.facebook.xfbml.Xfbml;

public class ShowFriendsGet extends Example  {

	private VerticalPanel outer = new VerticalPanel ();
	
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "method friends.get";
	}

	@Override
	public String getHeader() {
		// TODO Auto-generated method stub
		return "Friends.get";
	}
	
	private FacebookApi apiClient = ApiFactory.newApiClient(Config.API_KEY);
	public ShowFriendsGet () {
		
		
		outer.add(getLoader () );
		final FlowPanel flow = new FlowPanel ();
		flow.setWidth( "500px");
		flow.getElement().setId( "friendsget");
		
		Map<String,String> params = new HashMap<String,String> ();
		
		apiClient.friends_get(params, new AsyncCallback<List<Long>> () {

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
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
	
		
		initWidget ( outer );
	}

}

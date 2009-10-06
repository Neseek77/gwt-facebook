package com.gwittit.client.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.facebook.FacebookApi.NotificationsGetListParams;
import com.gwittit.client.facebook.entities.Notification;

/**
 * 
 */
public class Notifications_getList extends Example {

	
	public Notifications_getList () {
		super ( "notifications.getList");
	}
	
	public Widget createWidget () {
		final VerticalPanel outer = new VerticalPanel ();
		
		addLoader ( outer );
		
		Map<String,String> params = new HashMap<String,String> ();
		params.put(NotificationsGetListParams.include_read.toString(), "true");
		//params.put("session_key", UserInfo.getSessionKey());
		apiClient.notifications_getList(params, new AsyncCallback<List<Notification>> () {

			public void onFailure(Throwable caught) {
				handleFailure ( caught );
			}

			public void onSuccess( List<Notification> result ) {
				removeLoader ( outer );
				
				outer.add ( new HTML  ( "Result Size " + result.size() ) );
				
				for ( final  Notification no: result ) {
					
					VerticalPanel tmp = new VerticalPanel ();
					tmp.addStyleName ( "notification" );
					
					
					tmp.add ( new HTML ( "<h3>" + no.getTitleText() + "</h3>" ) );
					tmp.add ( new HTML ( no.getBodyHtml() ) );
					tmp.add ( new HTML ( no.getHref() ) );
					outer.add ( tmp );
				}
			}
			
		});
		
		return outer;
	}
}

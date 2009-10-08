package com.gwittit.client.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.facebook.FacebookApi.NotificationsGetListParams;
import com.gwittit.client.facebook.FacebookApi.NotificationsMarkReadParams;
import com.gwittit.client.facebook.entities.Notification;

/**
 * Showcase for method calls <code>notifications.getList</code> and <code>notifications.markRead</code>
 * 
 * @author olamar72 
 */
public class Notifications_getList extends Example {

	
	public Notifications_getList () {
		super ( "notifications.getList");
	}
	
	@Override
	public Widget createWidget () {
		final VerticalPanel outer = new VerticalPanel ();
		
		addLoader ( outer );
		
		Map<Enum<NotificationsGetListParams>,String> params = new HashMap<Enum<NotificationsGetListParams>,String> ();
		params.put(NotificationsGetListParams.include_read, "true");

		// Get facebook data
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
					
					if ( !no.getIsUnread() ) {
						tmp.add ( new HTML ( "Status : Old" ) );
					} 
					final Anchor markRead = new Anchor ( "Mark as Read");
					markRead.addClickHandler( new ClickHandler () {
						public void onClick(ClickEvent event) {
							markRead.setHTML( "Marked as Read");
							markRead ( no.getNotificationId() );
						}
						
					});
					tmp.add ( markRead );
					outer.add ( tmp );
				}
			}
			
		});
		return outer;
	}
	
	/**
	 * Mark notification as read.
	 */
	private void markRead ( final Long nid ) {
	
		Map<Enum<NotificationsMarkReadParams>,String> params = new HashMap<Enum<NotificationsMarkReadParams>,String> ();
		params.put( NotificationsMarkReadParams.notification_ids, nid+"");
		
		// Mark notification as read.
		apiClient.notifications_markRead(params, new AsyncCallback<Boolean> () {
			public void onFailure(Throwable caught) {
				handleFailure ( caught );
			}
			public void onSuccess(Boolean result) {
				Window.alert ( "Mared notification with id " + nid + " as read" );
			}
		});
	}
}

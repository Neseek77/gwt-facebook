package com.gwittit.client.example;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.facebook.entities.NotificationRequest;
import com.gwittit.client.facebook.entities.NotificationRequest.NotificationType;
import com.gwittit.client.facebook.xfbml.FbEventLink;
import com.gwittit.client.facebook.xfbml.FbGroupLink;
import com.gwittit.client.facebook.xfbml.FbProfilePic;
import com.gwittit.client.facebook.xfbml.Xfbml;

/**
 * Showcase for method call <code>notifications.get</code>
 *
 * @author olamar72
 */
public class Notifications_get extends Example {
	
	public Notifications_get(){
		super("notifications.get");
	}

	
	@Override
	public Widget createWidget () {
	
		final VerticalPanel outer = new VerticalPanel ();
		final HorizontalPanel friendRequestWrapper = new HorizontalPanel ();
		friendRequestWrapper.getElement().setId( "friendRequestWrapper" );
		friendRequestWrapper.setSpacing(10);
		
		final VerticalPanel groupInvitesWrapper = new VerticalPanel ();
		groupInvitesWrapper.getElement().setId ( "groupInvitesWrapper" );
		
		final VerticalPanel eventInvitesWrapper = new VerticalPanel ();
		eventInvitesWrapper.getElement().setId("eventInvitesWrapper");
		
		addLoader(outer);
		
		// Get facebook data
		apiClient.notifications_get(new AsyncCallback<List<NotificationRequest>> () {

			public void onFailure(Throwable caught) {
				handleFailure ( caught );
			}

			public void onSuccess( List<NotificationRequest> result ) {
				removeLoader ( outer );
				
				for ( NotificationRequest nc : result ) {
					outer.add ( new HTML ( "<h3>" + nc.getType() + "</h3>") );

					if ( nc.getUnread() != null ) {
						outer.add ( new HTML ( "Unread: " + nc.getUnread() ) ); 
					}

					// Friend requests.
					if ( nc.getTypeEnum() == NotificationType.friend_requests &&
						nc.getRequestIds().size() > 0 ) {
						
						outer.add ( friendRequestWrapper );
						
						for ( Long uid : nc.getRequestIds() ) {
							friendRequestWrapper.add ( new FbProfilePic ( uid ) ) ;
						}
						Xfbml.parse( friendRequestWrapper );
					}
					// Group invites
					else if ( nc.getTypeEnum() == NotificationType.group_invites ) {
						
						outer.add ( groupInvitesWrapper );
						
						for ( Long gid : nc.getRequestIds() ) {
							groupInvitesWrapper.add( new HTML ( "GroupInvite: " + gid  ) );
							groupInvitesWrapper.add( new FbGroupLink ( gid )  );
						}
						Xfbml.parse( groupInvitesWrapper );
					}
					// Else..
					else if ( nc.getTypeEnum() == NotificationType.event_invites ) {
						
						outer.add ( eventInvitesWrapper );
						if ( nc.getRequestIds().size() > 0 ) {
							for ( Long eid : nc.getRequestIds() ) {
								eventInvitesWrapper.add ( new FbEventLink (eid ) ) ;
							}
						}
						
						Xfbml.parse(eventInvitesWrapper);
					}
				}
			}
		});
		return outer;
	}
}

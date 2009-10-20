package com.gwittit.client.facebook.entities;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;


/**
 * Facebook Notification Request
 * 
 * @see http://wiki.developers.facebook.com/index.php/Notifications.get
 * @author ola
 * TODO: Let this class extend JavaScriptObject
 */
public class NotificationRequest {

	public enum NotificationType {messages,pokes,friend_requests,group_invites,shares,event_invites};
	
	private Long mostRecent;
	
	private String type;
	
	private Integer unread;
	
	private List<Long> requestIds = new ArrayList<Long> ();

	
	public NotificationRequest( String type, JSONValue v ) {
		this.type = type;

		GWT.log ( NotificationRequest.class + "Create new NotficationCurrent: " + type + ":" + v, null );
		
		JSONObject o = v.isObject();
		
		if ( o != null ) {
			unread = JsonUtil.getInt ( o, "unread" );
		} else if ( v.isArray() != null ) {
			JSONArray a = v.isArray();
			for ( int i = 0 ; i < a.size(); i++ ) {
				requestIds.add( new Long ( a.get(i).isNumber()+"" ) );
			}
		}
	}

	public void setMostRecent(Long mostRecent) {
		this.mostRecent = mostRecent;
	}

	public String getType() {
		return type;
	}

	public NotificationType getTypeEnum () {
		return NotificationType.valueOf( getType() );
	}
	public void setType(String type) {
		this.type = type;
	}


	public Integer getUnread() {
		return unread;
	}


	public void setUnread(Integer unread) {
		this.unread = unread;
	}

	public List<Long> getRequestIds() {
		return requestIds;
	}

	public void setRequestIds(List<Long> requestIds) {
		this.requestIds = requestIds;
	}

	public Long getMostRecent() {
		return mostRecent;
	}

}

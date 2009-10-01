package com.gwittit.client.facebook.entities;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;

/**
 * Facebook friendlist
 */
public class FriendList {

	/*
	 * id of friend list 
	 */
	private Long flid;
	
	/*
	 * name of friend list
	 */
	private String name;
	
	
	public FriendList () {
		
	}
	
	public FriendList ( JSONValue v ) {
		if ( v == null ) {
			return;
		}
		JSONObject o = v.isObject();
		
		flid = JsonUtil.getLong(o, "flid" );
		name = JsonUtil.getString(o,"name" );
	}

	public Long getFlid() {
		return flid;
	}

	public void setFlid(Long flid) {
		this.flid = flid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}

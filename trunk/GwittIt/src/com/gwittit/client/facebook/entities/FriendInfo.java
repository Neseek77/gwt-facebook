package com.gwittit.client.facebook.entities;

import com.google.gwt.json.client.JSONValue;

/**
 * Indicates if two persons are friends.
 *
 */
public class FriendInfo {

	private Long uid1 ;
	
	private Long uid2; 
	
	private Boolean areFriends ;
	
	public FriendInfo () {
		
	}
	
	public FriendInfo ( JSONValue v ) {
		if ( v == null ) { return ; }
		
		uid1 = JsonUtil.getLong(v.isObject(), "uid1");
		uid2 = JsonUtil.getLong(v.isObject(), "uid2" );
		areFriends = JsonUtil.getBoolean(v.isObject(), "are_friends");
		
	}

	public Long getUid1() {
		return uid1;
	}

	public void setUid1(Long uid1) {
		this.uid1 = uid1;
	}

	public Long getUid2() {
		return uid2;
	}

	public void setUid2(Long uid2) {
		this.uid2 = uid2;
	}

	public Boolean getAreFriends() {
		return areFriends;
	}

	public void setAreFriends(Boolean areFriends) {
		this.areFriends = areFriends;
	}
	
}

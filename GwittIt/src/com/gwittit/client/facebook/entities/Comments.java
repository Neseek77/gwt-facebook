package com.gwittit.client.facebook.entities;

import com.google.gwt.json.client.JSONValue;

/**
 * Indicates if this stream has comments
 *  
 * TODO: Let this class extend JavaScriptObject
 */
public class Comments {

	int count = 0;
	boolean canRemove;
    boolean canPost;

	public Comments () {
	}
	
	public Comments ( JSONValue o ) {
		if ( o.isObject() != null ) {
			count = JsonUtil.getInt(o.isObject(), "count" );
			canRemove = JsonUtil.getBoolean(o.isObject(), "can_remove" );
			canPost = JsonUtil.getBoolean(o.isObject(), "can_post" );
		}
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public boolean isCanRemove() {
		return canRemove;
	}
	public void setCanRemove(boolean canRemove) {
		this.canRemove = canRemove;
	}
	public boolean isCanPost() {
		return canPost;
	}
	public void setCanPost(boolean canPost) {
		this.canPost = canPost;
	}
	
	
}
package com.gwittit.client.facebook;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;

/**
 * Interfaces that defines a native callbackk, you'll get the raw javascript object
 * and need to to the parsing yourself.
 */
public interface FacebookCallback {
	
	/**
	 * Called when a error occurrs
	 */
	public void onError ( JSONValue v );

	/**
	 * Called on success 
	 */
	public void onSuccess ( JSONValue v );

}

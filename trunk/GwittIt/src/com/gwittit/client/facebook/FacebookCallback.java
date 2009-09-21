package com.gwittit.client.facebook;

import com.google.gwt.json.client.JSONObject;

public interface FacebookCallback {
	
	
	public void onError ( JSONObject o );
	
	public void onSuccess ( JSONObject o );

}

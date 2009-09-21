package com.gwittit.client.facebook.commands;

import com.gwittit.client.Config;
import com.gwittit.client.facebook.ApiFactory;
import com.gwittit.client.facebook.FacebookApi;
import com.gwittit.client.facebook.FacebookCallback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.Window;



public abstract class UsersHasAppPermission {
	
	private FacebookApi apiClient = ApiFactory.newApiClient( Config.API_KEY );
	
	public enum Permission { read_stream, publish_stream };
	
	public UsersHasAppPermission ( Permission perm ) {

		GWT.log ( "UsersHasAppPermission() " + perm.toString(), null );
		
		apiClient.users_hasAppPermission(perm.toString(), new FacebookCallback () {

			public void onError(JSONObject jo) {
				Window.alert ( "error:" + jo );
			}

			public void onSuccess(JSONObject jo) {
				GWT.log ( "UserHasAppPermission: response: " + jo, null );
				
				JSONString result = jo.get("result").isString();
				
				if ( result != null ) {
					GWT.log( "UsersHasAppPermission: string result: " + result.isString().stringValue(), null);
					UsersHasAppPermission.this.hasPermission ("1".equals ( result.isString().stringValue() ) ) ;
				} else {
					JavaScriptObject obj = jo.getJavaScriptObject();
					UsersHasAppPermission.this.hasPermission ( "1".equals ( obj.toString() ) );
				}
			}
			
		});
		GWT.log ( "UsersHasAppPermission() done", null );
	}
	
	public abstract void hasPermission ( boolean res );

}

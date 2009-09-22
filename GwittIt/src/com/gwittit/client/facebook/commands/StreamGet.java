package com.gwittit.client.facebook.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gwittit.client.Config;
import com.gwittit.client.facebook.ApiFactory;
import com.gwittit.client.facebook.FacebookApi;
import com.gwittit.client.facebook.FacebookCallback;
import com.gwittit.client.facebook.entities.JsonUtil;
import com.gwittit.client.facebook.entities.Stream;
import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Image;


/**
 * Wraps the facebook command stream.get
 */
public abstract class StreamGet {
	
	private FacebookApi apiClient = ApiFactory.newApiClient( Config.API_KEY );
	

	/**
	 * Creates a new command
	 */
	public StreamGet (){
		
		GWT.log( "StreamGet()", null);
		Map<String,String> params = new HashMap () ;
		
	
		apiClient.stream_get(params, new FacebookCallback () {

			public void onError(JSONObject jo) {
				GWT.log( "Error " + jo, null );
				Window.alert ( "onError: " + jo.getJavaScriptObject() );
			}

			public void onSuccess(JSONObject jo) {

				GWT.log( "StreamGet: success" + jo, null );
				List<Stream> result = new ArrayList ();
				
				JSONValue value = jo.get("posts");
				JSONArray array = value.isArray();
				
				GWT.log( "StreamGet: Got Array?" + (array!=null), null );
				
				for ( int i = 0; i < array.size(); i ++ ) {
					
					JSONValue v = array.get ( i );
					JSONObject o = v.isObject();
		
			
					GWT.log ( "StreamGet: adding new Stream() to result ", null);
					
					Stream stream = Stream.newInstance(o);

					/*
					stream.setWrappedObject ( o );
					
					String message = JsonUtil.getString( o, "message" );
					stream.setMessage ( message );
					Long sourceId = JsonUtil.getLong(o, "source_id");
					
					if ( sourceId != null ) {
						stream.setSourceId( sourceId );
					} else {
						String sourceIdString = JsonUtil.getString(o, "source_id");
						stream.setSourceId(new Long ( sourceIdString ) );
					}
					*/
					result.add( stream );
					
					
					
				}
				
				GWT.log( "StreamGet: Calling on success, result size is " + result.size(), null );
				StreamGet.this.onSuccess( result );
			}
			
		});
		
	}
	
	
	public abstract void onSuccess ( List<Stream> stream );
	

	
}

package com.gwittit.client.facebook.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.gwittit.client.facebook.FacebookApi;
import com.gwittit.client.facebook.FacebookCallback;
import com.gwittit.client.facebook.entities.Album;

public abstract class PhotosGetAlbums {
		
	/**
	 * Fetch albums 
	 */
	public PhotosGetAlbums ( Map<String,String> params, FacebookApi apiClient ) {
		
		
		apiClient.photos_getAlbums( params, new FacebookCallback() {
			
			public void onError ( JSONObject j ) {
				Window.alert ( "Error " + j );
			}
			
			public void onSuccess ( JSONObject j ) {

				List<Album> albums = new ArrayList ();

                int key = 0;
                
                JSONObject o = j.isObject();
                JSONValue value;
                
                while ( (value=o.get ( key + "" ) ) != null ) {
                        final Album album = Album.newInstance( value.isObject() );
                        albums.add( album );
                        key++;
                }
                PhotosGetAlbums.this.onSuccess( albums );
			}
		});
	}
	
	public abstract void onSuccess ( List<Album> albums );
}

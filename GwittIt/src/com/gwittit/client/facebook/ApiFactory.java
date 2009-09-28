package com.gwittit.client.facebook;

import com.google.gwt.user.client.Window;


/**
 * Creates javascript api.
 */
public class ApiFactory {
	
	
	private static FacebookApi apiClient;
	
	/**
	 * Create facebook api client
	 */
	public static FacebookApi newApiClient ( String apiKey ) {
		
		if ( apiClient == null ) {
			apiClient = new FacebookApi ( apiKey );
		} 
		return apiClient;
	}
}


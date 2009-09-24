package com.gwittit.client.facebook;


/**
 * Creates javascript api.
 */
public class ApiFactory {
	
	/**
	 * Create facebook api client
	 */
	public static FacebookApi newApiClient ( String apiKey ) {
		return new FacebookApiImpl ( apiKey );
	}
}


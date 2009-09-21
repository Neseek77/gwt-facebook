package com.gwittit.client.facebook;


/**
 * Creates javascript api.
 */
public class ApiFactory {
	
	/**
	 * Create facebook connect api client
	 */
	public static FacebookConnectApi connectApiClient ( String apiKey ) {
		return new FacebookConnectApiImpl ();
	}

	
	/**
	 * Create facebook api client
	 */
	public static FacebookApi newApiClient ( String apiKey ) {
		return new FacebookApiImpl ();
	}
}


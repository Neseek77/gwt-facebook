package com.gwittit.client.example;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.facebook.ApiFactory;
import com.gwittit.client.facebook.FacebookApi;
import com.gwittit.client.facebook.FacebookException;
import com.gwittit.client.facebook.FacebookApi.Permission;
import com.gwittit.client.facebook.ui.ErrorResponseUI;

/**
 * Core class for examples. 
 */
public  class Showcase extends Composite {
	
	private static Image loader = new Image ( "/loader.gif" );
	
	
	protected FacebookApi apiClient = ApiFactory.getInstance();
	
	/**
	 * Get description about the showcase
	 * @return description
	 */
	public String getDescription () {
		if ( method == null ) {
			return "This method is not implemented";
		}
		return "Call facebook method " + method.replace("_", ".");
	}
	
	/**
	 * Get showcase header
	 * @return header
	 */
	public  String getHeader () {
		if ( method == null ) {
			return "Not Implemnted";
		}
		return method;
	}

	/**
	 * Get animated gif to display on asynchrounus call
	 * @return animated gif
	 */
	public static Image getLoader () {
		return loader;
	}
	
	/**
	 * Add animated loader to the panel
	 * @param p to add loader symbol to
	 */
	public static void addLoader ( Panel p ) {
		p.add( getLoader () );
	}
	
	/**
	 * Remove animated loader
	 * @param p panel to remove loader from
	 */
	public static void removeLoader ( Panel p ) {
		p.remove( getLoader () );
	}
	
	/**
	 * Handle failure
	 * @param t original exception
	 */
	public static void handleFailure ( Throwable t ) {
	    
	    if ( t instanceof FacebookException ) {
	        FacebookException e = (FacebookException)t;
	   
	        ErrorResponseUI ui = new ErrorResponseUI ( e.getErrorMessage () );
	        ui.center ();
	        ui.show ();
	        
	    } else {
	        Window.alert ( t + "" );
	    }
	}
	
	public Permission getNeedPermission () {
	    return null;
	}
	
	public void permissionGranted () {
	    throw new RuntimeException ( "You must override this method if getNeedPermission is overrided" );
	}
	
	public String getMessage () {
	    return null;
	}

	private String method;
	

}


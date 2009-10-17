package com.gwittit.client.example;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.Config;
import com.gwittit.client.facebook.ApiFactory;
import com.gwittit.client.facebook.FacebookApi;
import com.gwittit.client.facebook.FacebookConnect;
import com.gwittit.client.facebook.FacebookException;
import com.gwittit.client.facebook.ui.ErrorResponseUI;

/**
 * Core class for examples. 
 */
public  class Showcase extends Composite {
	
	private Image loader = new Image ( "/loader.gif" );
	
	private VerticalPanel outer = new VerticalPanel ();
	
	protected FacebookApi apiClient = ApiFactory.newApiClient( Config.API_KEY);
	
	/**
	 * Create new object
	 */
	public Showcase () {
		this ( null );
	}
	
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
	public Image getLoader () {
		return loader;
	}
	
	/**
	 * Add animated loader to the panel
	 * @param p to add loader symbol to
	 */
	public void addLoader ( Panel p ) {
		p.add( getLoader () );
	}
	
	/**
	 * Remove animated loader
	 * @param p panel to remove loader from
	 */
	public void removeLoader ( Panel p ) {
		p.remove( getLoader () );
	}
	
	/**
	 * Handle failure
	 * @param t original exception
	 */
	public void handleFailure ( Throwable t ) {
	    
	    if ( t instanceof FacebookException ) {
	        FacebookException e = (FacebookException)t;
	   
	        ErrorResponseUI ui = new ErrorResponseUI ( e.getErrorMessage () );
	        ui.center ();
	        ui.show ();
	        
	    } else {
	        Window.alert ( t + "" );
	    }
	}
	
	private String method;
	

	public Showcase ( String method ) {
		this.method = method;
		outer.add ( createWidget () );
		initWidget(outer);
		
	}
	
	public Widget createWidget () {
		return new SimplePanel ();
	}


}

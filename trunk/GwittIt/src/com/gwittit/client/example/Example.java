package com.gwittit.client.example;

import java.util.List;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.Config;
import com.gwittit.client.facebook.ApiFactory;
import com.gwittit.client.facebook.FacebookApi;

/**
 * Core class for examples. 
 */
public  class Example extends Composite {
	
	private Image loader = new Image ( "/loader.gif" );
	
	private VerticalPanel outer = new VerticalPanel ();
	
	protected FacebookApi apiClient = ApiFactory.newApiClient( Config.API_KEY);
	
	public Example () {
		this ( null );
	}
	
	public String getDescription () {
		if ( method == null ) {
			return "This method is not implemented";
		}
		
		return "Call facebook method " + method.replace("_", ".");
	}
	
	public  String getHeader () {
		
		if ( method == null ) {
			return "Not Implemnted";
		}
		return method;
	}

	public Image getLoader () {
		return loader;
	}
	
	
	public void addLoader ( Panel p ) {
		p.add( getLoader () );
	}
	
	public void removeLoader ( Panel p ) {
		p.remove( getLoader () );
	}
	
	public void handleFailure ( Throwable t ) {
		Window.alert ( t + "" );
	}
	
	private String method;
	

	public Example ( String method ) {
		this.method = method;
		
		
		outer.add ( createWidget () );
		initWidget(outer);
		
	}
	
	public Widget createWidget () {
		return new SimplePanel ();
	}


}

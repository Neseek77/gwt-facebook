package com.gwittit.client.facebook.xfbml;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;

/**
 * Prints the specified event name and formats it as a link to the event's page. 
 *
 * See http://wiki.developers.facebook.com/index.php/Fb:eventlink
 *
 *  CSS Configuration
 *  <ul>
 *      <li>.gwittit-FbEventLink
 *  </ul>
 */
public class FbEventLink extends ComplexPanel {
	
	/**
	 * Params
	 * @param eid  	 Event ID for the event whose name and link you want to retrieve. 	
	 */
	public FbEventLink ( Long eid ) {
		super.setElement(DOM.createElement( "fb:eventlink" ) );
		getElement().setAttribute("eid",eid+"");
		
		super.addStyleName ( "gwittit-FbEventLink" );
	}

	
	
}

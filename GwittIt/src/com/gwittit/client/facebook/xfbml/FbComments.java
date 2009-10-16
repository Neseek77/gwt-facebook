package com.gwittit.client.facebook.xfbml;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;


/**
 * Generates a fb:comment tag
 * 
 * CSS Configuration
 * 
 * <ul>
 *  <li>gwittit-FbComments
 * </ul>
 *
 */
public class FbComments extends ComplexPanel {
	
	private String xid ;

	
	public FbComments () {
		super.setElement( DOM.createElement ( "fb:comments" ) ) ;
		addStyleName ( "gwittit-FbComments");
	}
	
	public FbComments ( String xid ) {
		this ();
		
		setXid ( xid );
	}
	
	public String getXid() {
		return xid;
	}

	public void setXid(String xid) {
		getElement().setAttribute("xid", xid);
	}

	
	
}

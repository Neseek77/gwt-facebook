package com.gwittit.client.facebook.xfbml;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;


/**
 * Generates a fb:comment tag
 *
 */
public class FbComments extends ComplexPanel {
	
	private String xid ;

	
	public FbComments () {
		super.setElement( DOM.createElement ( "fb:comments" ) ) ;
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

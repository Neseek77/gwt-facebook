package com.gwittit.client.facebook.xfbml;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;

/**
 * Renders a fb:photo tag.
 * 
 * Customizable with style "gwittit-FbPhoto"
 */
public class FbPhoto extends Widget  {
	
	public FbPhoto ( Long pid ) {
		this ( "" + pid );
	}
	public FbPhoto ( String pid ) {
		super.setElement( DOM.createElement ("fb:photo") ); 
		addStyleName ( "gwittit-FbPhoto");
		getElement().setAttribute("pid", pid );
	}
	
	public void setPid ( String pid ) {
		getElement().setAttribute("pid", pid);
	}
	
	
}
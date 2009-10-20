package com.gwittit.client.facebook.xfbml;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;

/**
 * Renders a fb:photo tag.
 * 
 * TODO: wild mix of long and string. fix
 * 
 * CSS Configuration
 * <ul>
 *  <li> .gwittit-FbPhoto
 *  </ul>
 */
public class FbPhoto extends Widget  {
	
	public enum Size {normal,thumb,small};
	
	public FbPhoto ( String pid, Size size ) {
	    this ( pid );
	    getElement ().setAttribute ( "size", size.toString () );
	}
	
	public FbPhoto ( String pid ) {
		super.setElement( DOM.createElement ("fb:photo") ); 
		addStyleName ( "gwittit-FbPhoto");
		getElement().setAttribute("pid", pid );
	}
	
	
}

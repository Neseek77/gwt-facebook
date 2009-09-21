package com.gwittit.client.facebook.xfbml;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;


/**
 * Renders a fb:profile-pic tag
 * @author ola
 *
 */
public class FbProfilePic extends Widget {
	
	
	public FbProfilePic ( Long uid ) {
		setElement ( DOM.createElement("fb:profile-pic" ) ) ;
		getElement().setAttribute("uid", "" + uid );
	}
	
	public FbProfilePic ( Long uid, String size ) {
		this ( uid );
		getElement().setAttribute( "size", size);
	}

}

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
		super.setElement ( DOM.createElement("fb:profile-pic" ) ) ;
		addStyleName("gwittit-FbProfilePic");

		getElement().setAttribute("uid", "" + uid );
		getElement().setAttribute("size", "square");
		setFacebookLogo(true);
	}
	
	public FbProfilePic ( Long uid, String size ) {
		this ( uid );
		getElement().setAttribute( "size", size);
	}

	
	public void setFacebookLogo( boolean value ) {
		getElement().setAttribute("facebook-logo", ""+value);
	}
}

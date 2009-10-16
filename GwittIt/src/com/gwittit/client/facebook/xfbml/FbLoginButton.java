package com.gwittit.client.facebook.xfbml;


import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;


/**
 * Renders a <fb:login> tag.
 * 
 * CSS Configuration
 * <ul>
 *  <li>gwittit-FbLoginButton
 * </ul>
 */
public class FbLoginButton extends Widget {

	public FbLoginButton () {
		
		super.setElement( DOM.createElement ("fb:login-button") );
		
		addStyleName("gwittit-FbLoginButton");
		// default function to fire when user log in.
		setOnLogin("facebookConnectLogin()");
		
	}
	
	public FbLoginButton ( String onLoginMethod ) {
		this ();
		setOnLogin ( onLoginMethod );
	}
	
	public void setAutoLogoutLink ( boolean value ) {
		getElement().setAttribute("autologoutlink", ""+value);
	}
	
	public void setOnLogin ( String method ) {
		getElement().setAttribute( "onlogin", method );
	}
}

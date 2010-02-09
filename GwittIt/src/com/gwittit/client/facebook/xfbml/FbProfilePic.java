package com.gwittit.client.facebook.xfbml;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

/**
 * Renders a fb:profile-pic tag
 * 
 * Turns into an img tag for the specified user's or Facebook Page's profile
 * picture.
 * 
 * The tag itself is treated like a standard img tag, so attributes valid for
 * img are valid with fb:profile-pic as well. So you could specify width and
 * height settings instead of using the size attribute, for example. Style
 * 
 * <pre>
 * required
 * 
 * @param uid
 *            int The user ID of the profile or Facebook Page for the picture
 *            you want to display. On a canvas page, you can also use
 *            "loggedinuser". optional
 * @param size
 *            string The size of the image to display. (Default value is
 *            thumb.). Other valid values are thumb (t) (50px wide), small (s)
 *            (100px wide), normal (n) (200px wide), and square (q) (50px by
 *            50px). Or, you can specify width and height settings instead, just
 *            like an img tag.
 * @param linked
 *            bool Make the image a link to the user's profile. (Default value
 *            is true.)
 * @param facebook
 *            -logo bool (For use with Facebook Connect only.) When set to true,
 *            it returns the Facebook favicon image, which gets overlaid on top
 *            of the user's profile picture on a site.
 * 
 * 
 * CSS Configuration
 * <ul>
 *  <li>.gwittit-FbProfilePic
 * </ul>
 * </pre>
 * @author ola
 * 
 */
public class FbProfilePic extends Widget {

	public enum Size {
	    /** 50px wide */
	    thumb,
	    /** 100 px wide */
	    small,
	    /** 200px wide */
	    normal,
	    /** 50px square */
	    square
    }
	
	public FbProfilePic () {
		super.setElement(DOM.createElement("fb:profile-pic"));
		addStyleName("gwittit-FbProfilePic");
		getElement().setAttribute("size", "square");
		setFacebookLogo(true);
		getElement().setAttribute("uid", "loggedinuser");
	}
	
	public FbProfilePic(Long uid) {
		this();
		
//		if ( uid == null ) {
//			// Window.alert( "Cannot create profile pic with null id ");
//		}
		getElement().setAttribute("uid", "" + uid);
	}

	public FbProfilePic(Long uid, Size size) {
		this(uid);
		getElement().setAttribute("size", size.toString());
	}

	public void setFacebookLogo(boolean value) {
		getElement().setAttribute("facebook-logo", "" + value);
	}
}

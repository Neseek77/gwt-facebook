package com.gwittit.client.facebook.xfbml;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;

/**
 * Prints the specified group name and formats it as a link to the group's page. 
 * See http://wiki.developers.facebook.com/index.php/Fb:grouplink
 * 
 * CSS Configuration
 * <ul>
 *  <li>.gwittit-FbGroupLink
 * </ul>
 */
public class FbGroupLink extends ComplexPanel {

	/**
	 * Create group link.
	 * @param gid Group ID for the group whose name and link you want to retrieve. 
	 */
	public FbGroupLink ( Long gid ) {
		super.setElement( DOM.createElement("fb:grouplink") );
		getElement().setAttribute("gid", gid+"");
		addStyleName ( "gwittit-FbGroupLink" );
	}
}

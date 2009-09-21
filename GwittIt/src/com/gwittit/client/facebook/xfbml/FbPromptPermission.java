package com.gwittit.client.facebook.xfbml;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class FbPromptPermission extends ComplexPanel {
	
	public FbPromptPermission () {
		setElement ( DOM.createElement("fb:prompt-permission" ) ) ;
		getElement().setAttribute("perms", "read_stream,publish_stream");

		add ( new HTML ( "Grant permission for status updates" ), getElement() );
	}
}

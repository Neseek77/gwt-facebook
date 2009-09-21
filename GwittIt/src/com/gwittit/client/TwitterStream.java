package com.gwittit.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TwitterStream extends Composite {
	
	
	private VerticalPanel outer = new VerticalPanel ();
	
	
	public TwitterStream () {
		
		
		outer.add ( new HTML ( "Here comes twitter stream" ) );
		initWidget ( outer );
	}

}

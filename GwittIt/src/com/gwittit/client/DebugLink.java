package com.gwittit.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class DebugLink extends Composite {
	
	private VerticalPanel outer = new VerticalPanel ();
	
	
	public DebugLink ( final String debugWhat ) {
		
	    final DecoratedPopupPanel simplePopup = new DecoratedPopupPanel(true);
	    
	    simplePopup.setWidth("400px");
		Anchor link = new Anchor ( "DebugInfo" );
		
		
		link.addClickHandler( new ClickHandler () {

			public void onClick(ClickEvent event) {
			
				Widget source = (Widget)event.getSource();
				int left = source.getAbsoluteLeft() + 10;
				int top = source.getAbsoluteTop() + 10;
				
				simplePopup.setPopupPosition(left, top);
				
				simplePopup.setWidget( new HTML ( debugWhat ) ) ;
				simplePopup.show();
			}
			
		});
		
		outer.add ( link );
		initWidget ( outer );
		
		
	}

}

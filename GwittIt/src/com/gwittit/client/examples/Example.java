package com.gwittit.client.examples;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class Example extends Composite {
	
	
	private VerticalPanel outer = new VerticalPanel ();
	
	public Example () {
		outer.getElement().setId("Example");
	}


	@Override
	protected void initWidget(Widget widget) {
	
		
		HTML info = new HTML ( "(" + getInfo () + ")");
		info.addStyleName ( "info" );
		//outer.add ( info );

		outer.add ( widget );

		// TODO Auto-generated method stub
		super.initWidget( outer );
	}
	
	
	
	public abstract String getInfo () ;
	
	
	

}

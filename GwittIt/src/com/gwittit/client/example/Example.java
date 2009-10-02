package com.gwittit.client.example;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;

public abstract class Example extends Composite {
	
	private Image loader = new Image ( "/loader.gif" );
	
	public abstract String getDescription () ;
	
	public abstract String getHeader ();

	public Image getLoader () {
		return loader;
	}

}

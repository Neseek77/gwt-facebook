package com.gwittit.client;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwittit.client.examples.PhotosGetAlbumsExample;
import com.gwittit.client.facebook.FacebookApi;

public class FacebookApiDemo extends Composite implements ChangeHandler { 
	
	
	private VerticalPanel outer = new VerticalPanel ();
	private ListBox listBox = new ListBox ( false );

	
	private SimplePanel demoArea = new SimplePanel ();
	private FacebookApi apiClient;
	
	public FacebookApiDemo ( FacebookApi apiClient ) {
	
		this.apiClient = apiClient;
	
		outer.getElement().setId( "FacebookApiDemo");
		
		listBox.addStyleName ( "listBox");
		
		listBox.addItem("--- Choose Method ---" );
		listBox.addItem ( "photos.getAlbums" );
		
		listBox.addChangeHandler( this );
		
		outer.add( listBox );
		outer.add ( demoArea );
		initWidget ( outer );
	}


	public void onChange(ChangeEvent event) {
		
		if ( "photos.getAlbums".equals ( listBox.getValue( listBox.getSelectedIndex() ) ) ) {
			demoArea.add( new PhotosGetAlbumsExample ( apiClient ) );
		}
		
	}

}

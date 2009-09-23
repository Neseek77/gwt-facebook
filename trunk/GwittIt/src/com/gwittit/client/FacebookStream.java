package com.gwittit.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwittit.client.facebook.FacebookApi;
import com.gwittit.client.facebook.commands.UsersHasAppPermission;
import com.gwittit.client.facebook.commands.UsersHasAppPermission.Permission;
import com.gwittit.client.facebook.entities.Stream;
import com.gwittit.client.facebook.xfbml.Xfbml;

/**
 * Display facebook stream 
 */
public class FacebookStream extends Composite {
	
	private VerticalPanel outer = new VerticalPanel ();

	private VerticalPanel streamListing = new VerticalPanel ();
	
	private Anchor ask = new Anchor ( "Click to enable facebook stream in Gwittee");

	private Image ajaxLoader = new Image ( "/loader.gif" );
	
	private FacebookApi apiClient;
	
	/**
	 * Create new object.
	 */
	public FacebookStream ( final FacebookApi apiClient ) {

		this.apiClient = apiClient;
		
		GWT.log( "FacebookStream()", null);
		Map<String,String> params = new HashMap<String,String> ();
		
		// StyleNames...
		outer.getElement().setId( "FacebookStream" );
		streamListing.addStyleName( "streamListing" );
		
		//ask.setHTML( "<img src=/fb_permission.png> Click to enable facebook stream in Gwittee" );
		
		ask.addClickHandler( new ClickHandler () {
			public void onClick(ClickEvent event) {
				askForPermission();
			}
		});

		
		GWT.log( "FacebookStream: Checking app permission", null );
		
		new UsersHasAppPermission ( Permission.read_stream ) {
			@Override
			public void hasPermission(boolean canReadStream) {
				if ( canReadStream ) {
					renderStream();
				} else {
					outer.add ( ask );
				}
			}
		};
		outer.add ( streamListing );
	
		initWidget ( outer );
	}
	
	

	public void addFirst(Stream stream) {
		streamListing.insert( stream.createWidget(), 1);
	}
	
	
	public native void askForPermission ()/*-{
		var app=this;
	
		$wnd.FB.Connect.showPermissionDialog("read_stream", 
			function(x)
			{ 
				app.@com.gwittit.client.FacebookStream::handlePermission(Ljava/lang/String;)(x);
			}, 
			true,  null);	
		
	}-*/;

	public void handlePermission ( String s ) {
		if ( "read_stream".equals( s ) ) {
			outer.remove ( ask );
			renderStream();
		}
	}
	
	
	private void renderStream ( ) {
		GWT.log( "FacebookStream: render Stream", null );
		streamListing.add( ajaxLoader );

		apiClient.stream_get(null, new AsyncCallback<List<Stream>> () {

			public void onFailure(Throwable caught) {
				Window.alert ( "Stream Get Failed : " + caught );
			}

			public void onSuccess(List<Stream> result) {
				for ( Stream s : result ) {
					streamListing.add( s.createWidget() );
				}
				Xfbml.parse(streamListing.getElement() );
			}
			
		});
	}
	

}

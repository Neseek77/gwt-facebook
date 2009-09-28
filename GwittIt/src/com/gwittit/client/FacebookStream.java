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
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.facebook.FacebookApi;
import com.gwittit.client.facebook.FacebookConnect;
import com.gwittit.client.facebook.entities.Stream;
import com.gwittit.client.facebook.ui.StreamUi;
import com.gwittit.client.facebook.xfbml.Xfbml;

/**
 * Display facebook stream 
 */
public class FacebookStream extends Composite {
	
	private VerticalPanel outer = new VerticalPanel ();

	private VerticalPanel streamListing = new VerticalPanel ();


	private Panel unlockPnl = createUnlockPanel();
	
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
		streamListing.getElement().setId("StreamListing");
		streamListing.addStyleName( "streamListing" );
		
		//ask.setHTML( "<img src=/fb_permission.png> Click to enable facebook stream in Gwittee" );
		
		GWT.log( "FacebookStream: Checking app permission", null );
		
		apiClient.users_hasAppPermission( com.gwittit.client.facebook.FacebookApi.Permission.read_stream , 
				new AsyncCallback<Boolean>()  {
			
			public void onFailure(Throwable caught) {
				Window.alert ( "Failed to check permissino " + caught );
			}

			public void onSuccess(Boolean result) {
				if ( result ) {
					renderStream();
				} else {
					
					outer.add ( unlockPnl );
				}				
			}
		} );

		outer.add ( streamListing );
	
		initWidget ( outer );
	}

	
	private Panel createUnlockPanel () {
		final VerticalPanel inner = new VerticalPanel ();
		
		
		inner.add( new HTML ( "Oups, gwittit need some extra permissions to work properly..." ) );
		
		final Anchor permissionLnk = new Anchor ( );
		permissionLnk.setHTML("Click here to enable facebook news feed ");
		permissionLnk.addStyleName("clickable" );
		
		permissionLnk.addClickHandler( new ClickHandler () {
			public void onClick(ClickEvent event) {
				askForPermission();
			}
		});
		inner.add( permissionLnk );

		
		Image image = new Image ( "/locked.png");
		
		HorizontalPanel outer = new HorizontalPanel ();
		outer.addStyleName ( "needStreamPermission" );
		outer.add ( image );
		outer.add ( inner );
		
		return outer;
	}
	

	public void addFirst(Stream stream) {
		
		StreamUi su = new StreamUi ( stream );
		streamListing.insert( su, 0);
	}
	
	
	public void askForPermission () {
		FacebookConnect.showPermissionDialog(FacebookApi.Permission.read_stream, new AsyncCallback<Boolean> () {
			public void onFailure(Throwable caught) {
				Window.alert( "Error: " + caught );
				
			}
			public void onSuccess(Boolean canRead ) {
				if ( canRead ) {
					outer.remove(unlockPnl );
					renderStream ();
				}
			}
		});
	}
	
	/*
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
			outer.remove ( unlockPnl );
			renderStream();
		}
	}
	
	
	private void renderStream ( ) {
		GWT.log( "FacebookStream: render Stream", null );
		streamListing.add( ajaxLoader );

		Map<String,String> params = new HashMap<String,String> ();
		
		apiClient.stream_get(params, new AsyncCallback<List<Stream>> () {

			public void onFailure(Throwable caught) {
				Window.alert ( "Stream Get Failed : " + caught );
			}

			public void onSuccess(List<Stream> result) {
				streamListing.clear ();
				try {
				for ( Stream s : result ) {
					streamListing.add( new StreamUi (s) );
				}
				}catch (Exception e ) {  Window.alert ( "Exception " + e ); }
				Xfbml.parse(streamListing.getElement() );
			}
			
		});
	}
	

}

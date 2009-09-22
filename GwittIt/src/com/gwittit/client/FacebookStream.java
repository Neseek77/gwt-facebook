package com.gwittit.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gwittit.client.facebook.commands.StreamGet;
import com.gwittit.client.facebook.commands.UsersHasAppPermission;
import com.gwittit.client.facebook.commands.UsersHasAppPermission.Permission;
import com.gwittit.client.facebook.entities.Stream;
import com.gwittit.client.facebook.xfbml.FbName;
import com.gwittit.client.facebook.xfbml.FbProfilePic;
import com.gwittit.client.facebook.xfbml.Xfbml;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ImageBundle;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Display facebook stream 
 */
public class FacebookStream extends Composite {
	
	private VerticalPanel outer = new VerticalPanel ();

	private VerticalPanel streamListing = new VerticalPanel ();
	
	private Anchor ask = new Anchor ( "Click to enable facebook stream in Gwittee");

	private Image ajaxLoader = new Image ( "/loader.gif" );
	
	/**
	 * Create new object.
	 */
	public FacebookStream ( ) {

		GWT.log( "FacebookStream()", null);
		Map<String,String> params = new HashMap<String,String> ();
		
		// StyleNames...
		outer.getElement().setId( "FacebookStream" );
		streamListing.addStyleName( "streamListing" );
		
		ask.setHTML( "<img src=/fb_permission.png> Click to enable facebook stream in Gwittee" );
		
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
		streamListing.insert(createStreamListing ( stream ), 1);
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

		new StreamGet () {
			@Override
			public void onSuccess(List<Stream> streamResult) {
				GWT.log( "FacebookStream: stream get size " + streamResult, null );
				streamListing.remove( ajaxLoader );
				for ( Stream s : streamResult ) {
					streamListing.add( createStreamListing ( s ) );
				}
				
				Xfbml.parse();
			}
		};
	}
	
	private Panel createStreamListing ( Stream s  ) {
		
		HorizontalPanel outer = new HorizontalPanel ();
		outer.addStyleName ( "stream" );
		
		FbProfilePic profilePic = new FbProfilePic ( s.getSourceId() );

		FbName fbName = new FbName ( s.getSourceId() );

		
		HorizontalPanel text = new HorizontalPanel ();
		text.addStyleName ( "text" );
		
		text.add( new HTML ( fbName.toString() + " " + s.getMessage() ) );
		
		outer.add ( profilePic );
		outer.add ( text );
		
		return outer;
		
	}


}

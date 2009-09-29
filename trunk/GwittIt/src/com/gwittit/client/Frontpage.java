package com.gwittit.client;


import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwittit.client.events.AppEvents;
import com.gwittit.client.events.DefaultEventHandler;
import com.gwittit.client.examples.Example;
import com.gwittit.client.facebook.ApiFactory;
import com.gwittit.client.facebook.FacebookApi;
import com.gwittit.client.facebook.UserInfo;
import com.gwittit.client.facebook.entities.Stream;
import com.gwittit.client.facebook.xfbml.Xfbml;


/**
 * This page let the user write a status and publish it to multiple sources. Facebook Profile, Facebook Pages and Twitter.
 */
public class Frontpage extends Example implements ClickHandler {
	
	private static String LOGPREFIX = "Frontpage: ";
	
	// Where to keep everyting.
	private VerticalPanel outer = new VerticalPanel ();
	
	// Actual content
	private VerticalPanel statusBoxPnl = new VerticalPanel ();
	
	// Input text
	private TextArea inputTextArea = new TextArea ();
	 
	// Add status message
	private Button submit = new Button ( " Share ");

	
	// Text add new accounts
	private HTML accountsHtml = new HTML ( "<h3>Set facebook status</h3>");
	
	// Text add status
	
	private HorizontalPanel inputBar = new HorizontalPanel ();
	private HTML statusHtml = new HTML ( "<h3>Set your facebook status</h3>");
	
	// Facebook Connect
	private FacebookApi api = ApiFactory.newApiClient(Config.API_KEY);
	
		
	// EventBus
	private HandlerManager eventBus ;
	
	// Api Client
	private FacebookApi apiClient;
	
	// Stream Tabs
	private TabPanel tabs = new TabPanel ();
	
	private Image updateStatusLoader = new Image ( "/ajaxloader.gif" );

	// Display Facebook Stream
	private FacebookStream  facebookStream;
	
	// App Topmenu
	

	
	private void log ( String msg ) {
		GWT.log(LOGPREFIX + ":" + msg , null );
	}
	
	/**
	 * Create a new page, do all the server calls etc.
	 */
	public Frontpage ( FacebookApi apiClient, HandlerManager eventBus ) {

		log ( "new instance");
		this.eventBus = eventBus;
		this.apiClient = apiClient;

		// Events
		listenToLogin();

		// App styles
		outer.getElement().setId( "Frontpage");
		statusBoxPnl.addStyleName("statusBoxPnl");
		statusBoxPnl.setSpacing(4);
		
		inputTextArea.addStyleName( "inputTextArea" );
		inputTextArea.addStyleName("waitingForInput");
		inputTextArea.setVisibleLines(3);
		inputBar.setVerticalAlignment( VerticalPanel.ALIGN_BOTTOM );
		statusBoxPnl.setVerticalAlignment(VerticalPanel.ALIGN_TOP);
		
			// Clickhandlers
		submit.addClickHandler(this);
		inputTextArea.setValue( "What's on your mind?");
		inputTextArea.addClickHandler(new ClickHandler () {

			public void onClick(ClickEvent event) {
				inputTextArea.setValue(null);
				inputTextArea.removeStyleName("waitingForInput");
			}
			
		});
		
		renderUi();
		
	}

	private void renderUi () {
		
		facebookStream = new FacebookStream ( apiClient );
		
		outer.add ( statusBoxPnl );
		
		outer.add ( facebookStream );
		
		inputTextArea.setWidth( "500px" );
		
		inputBar.add ( statusHtml );

		statusBoxPnl.add ( inputBar );
		statusBoxPnl.add ( inputTextArea );
		statusBoxPnl.add ( submit );
		
	//	Xfbml.parse();
		initWidget ( outer );
		
	}


	private native void askForPermission ( String extPerm )/*-{
		var app=this;
		$wnd.FB.Connect.showPermissionDialog(extPerm, 
		function(x){
				app.@com.gwittit.client.Frontpage::handlePermission(Ljava/lang/String;)(x);
		}, true, null);	
	}-*/;
	
	
	public void onClick(ClickEvent event) {
		checkPublishStreamOnSubmit ();
		
		inputBar.add ( updateStatusLoader );
		submit.setEnabled(false);
		// WHen user submit new stauts
		
	}
	
	
	public void handlePermission ( String s ) {
		if ( "publish_stream".equals ( s ) ) {
			statusSet ();
		} else {
			Window.alert ( "Status not set");
		}
	}
	


	
	/**
	 * Check if the user can publish to facebook befor setting status.
	 */
	private void checkPublishStreamOnSubmit () {
		
		apiClient.users_hasAppPermission( com.gwittit.client.facebook.FacebookApi.Permission.publish_stream , new AsyncCallback<Boolean>()  {

			public void onFailure(Throwable caught) {
			}

			public void onSuccess ( Boolean canPublishStream ) {
				GWT.log( "checkPublishStream result = " + canPublishStream, null );
				if ( canPublishStream ) {
					statusSet ();
				} else {
					askForPermission ( "publish_stream");
				}				
			}
		});
	}

	/**
	 *Set status 
	 */
	private void statusSet  () {
		
		Map<String,String> params = new HashMap<String,String> ();
		params.put("uid", inputTextArea.getValue() );
		params.put("status", inputTextArea.getValue() );
		
		api.status_set( params, new AsyncCallback<JSONValue> () {
			public void onFailure ( Throwable t ) {
				Window.alert ( "Failed " );
			}
			public void onSuccess ( JSONValue v ) {
				//Stream stream = new Stream ();
				//stream.setMessage( inputTextArea.getValue() );
				//stream.setSourceId( UserInfo.getUidLong() );

				inputBar.remove( updateStatusLoader );
				submit.setEnabled(true);
				inputTextArea.setValue(null);

				facebookStream.refresh();
				
				/*
				facebookStream.addFirst ( stream );
				inputBar.remove( updateStatusLoader );
				submit.setEnabled(true);
				*/
				//Xfbml.parse( facebookStream.getElement());
			}
			
		});
	}

	private void listenToLogin () {
        eventBus.addHandler(AppEvents.TYPE, new DefaultEventHandler () {
                public void login() {
                	outer.clear();
                	renderUi();
                }
        });
	}


	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return "Demonstrates the call stream.get , status.set and more ";
	}

	

}

package com.gwittit.client;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.events.AppEvents;
import com.gwittit.client.events.DefaultEventHandler;
import com.gwittit.client.facebook.ApiFactory;
import com.gwittit.client.facebook.FacebookApi;
import com.gwittit.client.facebook.FacebookApi.StatusSetParams;
import com.gwittit.client.facebook.FacebookApi.StreamGetFiltersParams;
import com.gwittit.client.facebook.entities.StreamFilter;


/**
 * This page let the user write a status and publish it to multiple sources. Facebook Profile, Facebook Pages and Twitter.
 */
public class Frontpage  extends Composite implements ClickHandler {
	
	private static String LOGPREFIX = "Frontpage: ";
	
	// Where to keep everyting.
	private VerticalPanel outer = new VerticalPanel ();
	
	// Actual content
	private VerticalPanel statusBoxPnl = new VerticalPanel ();
	
	// Input text
	private TextArea inputTextArea = new TextArea ();
	 
	// Add status message
	private Button submit = new Button ( " Share ");
	
	// Text add status
	private HorizontalPanel inputBar = new HorizontalPanel ();
	private HTML statusHtml = new HTML ( "<h3>Set your facebook status</h3>");
	
	// Facebook Connect
	private FacebookApi api = ApiFactory.newApiClient(Config.API_KEY);
		
	// EventBus
	private HandlerManager eventBus ;
	
	// Api Client
	private FacebookApi apiClient;
	
	// Ajax animated loader.
	private Image updateStatusLoader = new Image ( "/ajaxloader.gif" );

	// Display Facebook Stream
	private FacebookStream  facebookStream;
		
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
		
		initWidget ( outer );
		
	}

	/**
	 * Render left side menu. This is a list of streamfilter keys.
	 */
	public Panel getMenu () {

		final VerticalPanel menu = new VerticalPanel ();
		menu.addStyleName("menu");
		
	
		Map<Enum<StreamGetFiltersParams>, String> params = new HashMap<Enum<StreamGetFiltersParams>, String>();
		apiClient.stream_getFilters(params, new AsyncCallback<List<StreamFilter>>() {

			public void onFailure(Throwable caught) {
				Window.alert(FacebookStream.class + ": Failure" + caught);
			}

			public void onSuccess(List<StreamFilter> result) {
				
				for (final StreamFilter sf : result) {

					final Anchor a = new Anchor();
					Image image = new Image ( sf.getIconUrl() );
					image.setVisibleRect(16, 0, 16, 16);
					
					a.setHTML ( image.toString() + " " + sf.getName() );
					a.setTitle(sf.getName());
					a.addStyleName("clickable");
					a.addClickHandler(new ClickHandler() {

						public void onClick(ClickEvent event) {
							facebookStream.setFilterKey(sf.getFilterKey());
							facebookStream.renderStream();
							// Window.alert ( "Name:" + sf.getName() +
							// ", FilterKey: " + sf.getFilterKey() + " , Rank:"
							// + sf.getRank() );
						}
					});
					
					menu.add( wrapMenuItem ( a ) );
				}
			}

		});
		return menu;
	}
	

	/**
	 * Wrap menu item in a div tag.
	 */
	private Widget wrapMenuItem  ( Anchor anchor ) {
		SimplePanel menuItem = new SimplePanel ();
		menuItem.addStyleName("menuItem");
		menuItem.setWidget( anchor );
		return menuItem;
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
		
		Map<Enum<StatusSetParams>,String> params = new HashMap<Enum<StatusSetParams>,String> ();
		params.put(StatusSetParams.uid, inputTextArea.getValue() );
		params.put(StatusSetParams.status, inputTextArea.getValue() );
		
		api.status_set( params, new AsyncCallback<JSONValue> () {
			public void onFailure ( Throwable t ) {
				Window.alert ( "Failed " );
			}
			public void onSuccess ( JSONValue v ) {

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

}

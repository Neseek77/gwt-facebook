package com.gwittit.client;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwittit.client.events.AppEvents;
import com.gwittit.client.events.DefaultEventHandler;
import com.gwittit.client.examples.Example;
import com.gwittit.client.facebook.ApiFactory;
import com.gwittit.client.facebook.FacebookApi;
import com.gwittit.client.facebook.FacebookCallback;
import com.gwittit.client.facebook.UserInfo;
import com.gwittit.client.facebook.entities.Stream;
import com.gwittit.client.facebook.xfbml.FbLiveStream;
import com.gwittit.client.facebook.xfbml.Xfbml;


/**
 * This page let the user write a status and publish it to multiple sources. Facebook Profile, Facebook Pages and Twitter.
 */
public class Frontpage extends Example implements ClickHandler {
	
	private static String LOGPREFIX = "Frontpage: ";
	
	// Where to keep everyting.
	private VerticalPanel outer = new VerticalPanel ();
	
	// Left side of the app
	private VerticalPanel leftSide = new VerticalPanel ();
	
	// Right side of the app
	private VerticalPanel rightSide = new VerticalPanel ();
	
	// Actual content
	private HorizontalPanel content = new HorizontalPanel ();
	
	// Input text
	private TextArea statusTextBox = new TextArea ();
	 
	// Add status message
	private Button submit = new Button ( " Share ");

	// Let user check this to share status on facebook
	private CheckBox facebookPermissionCheckbox = new CheckBox ();
	
	// Text add new accounts
	private HTML accountsHtml = new HTML ( "<h3>+ Select Accounts</h3>");
	
	// Text add status
	
	private HorizontalPanel inputBar = new HorizontalPanel ();
	private HTML statusHtml = new HTML ( "<h3>Whats on your mind?</h3>");
	
	// Facebook Connect
	private FacebookApi api = ApiFactory.newApiClient(Config.API_KEY);
	
	private FacebookApiDemo apiDemo = new FacebookApiDemo ( api );
		
	// EventBus
	private HandlerManager eventBus ;
	
	// Api Client
	private FacebookApi apiClient;
	
	// Remove clickhandler when user has allowed us
	private HandlerRegistration facebookPermissionHr ;
	
	// Stream Tabs
	private TabPanel tabs = new TabPanel ();
	
	private Image updateStatusLoader = new Image ( "/ajaxloader.gif" );

	// Display Facebook Stream
	private FacebookStream  facebookStream;
	
	// App Topmenu
	

	private HTML whatIsGwittit = new HTML ("This application is a showcase for gwt-facebook, an open source library for writing facebook applications using GWT (gwitt)" );
	
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
		
		
		listenToLogin();

		//Window.alert("lat:" + getClientLatitude() );
		// Set styles and id's.
		outer.getElement().setId( "PimpMyStatus");
		content.addStyleName("content");
		leftSide.addStyleName("leftSide");
		rightSide.addStyleName("rightSide");
		statusTextBox.addStyleName( "statusTextBox" );
		whatIsGwittit.addStyleName ("whatIsGwittit");
		
		leftSide.setSpacing(5);
		statusTextBox.setVisibleLines(3);

		inputBar.setVerticalAlignment( VerticalPanel.ALIGN_BOTTOM );
		
		//outer.setSpacing( 10 );
		
		//leftSide.add ( new HTML ( "Share on facebook, facebook pages and twitter at the same time. " ) );

		content.setVerticalAlignment(VerticalPanel.ALIGN_TOP);
		
		
		if ( !UserInfo.isLoggedIn() ) {
			outer.clear();
			outer.add ( whatIsGwittit) ;

			outer.add ( new LoginDialogWidget ( eventBus ) );
			initWidget ( outer );	
			return ;
		} else {
			renderUi();
		}
		
	}

	private void renderUi () {
		
		facebookStream = new FacebookStream ( apiClient );
		

		content.add ( leftSide );
		content.add( rightSide );
		
		outer.add ( content );
		
		initTabs ();
		outer.add ( tabs );
		
		renderInput ();
		renderAccounts ();
		checkPublishStream ();
		
	//	Xfbml.parse();
		initWidget ( outer );
		
	}
	
	private void initTabs () {
		tabs.add( facebookStream , "News Feed" );
		tabs.add ( new FbLiveStream ( Config.API_KEY ), "Live Stream" );
		tabs.selectTab(0);
	}
	
	public void renderInput () {
		statusTextBox.setWidth( "400px" );
		
		inputBar.add ( statusHtml );

		leftSide.add ( inputBar );
		leftSide.add ( statusTextBox );
		leftSide.add ( submit );
		
		submit.addClickHandler(this);
	}


	/**
	 * Render the users accounts. Right now only facebook profile is supported.
	 */
	public void renderAccounts () {
		rightSide.add ( accountsHtml);
		HorizontalPanel facebook = new HorizontalPanel ();
		facebook.add ( facebookPermissionCheckbox );
		facebook.add ( new Label ( "Facebook Profile " ) ); 
		rightSide.add ( facebook );
	}
	
	
	/**
	 * Check if the user can publish to facebook.
	 */
	private void checkPublishStream () {
		
		apiClient.users_hasAppPermission( com.gwittit.client.facebook.FacebookApi.Permission.publish_stream , new AsyncCallback<Boolean>()  {

			public void onFailure(Throwable caught) {
			}

			public void onSuccess ( Boolean canPublishStream ) {
				GWT.log( "checkPublishStream result = " + canPublishStream, null );
				if ( canPublishStream ) {
					facebookPermissionCheckbox.setValue(true);
				} else {
					facebookPermissionHr = facebookPermissionCheckbox.addClickHandler( new ClickHandler () {
						public void onClick(ClickEvent event) {
							if ( facebookPermissionCheckbox.getValue() == true ) {
								askForPermission ( "publish_stream");
							}
						}
					});
				}				
			}
		});
	}

	private native void askForPermission ( String extPerm )/*-{
		var app=this;
		$wnd.FB.Connect.showPermissionDialog(extPerm, 
		function(x){
				app.@com.gwittit.client.Frontpage::handlePermission(Ljava/lang/String;)(x);
		}, true, null);	
	}-*/;
	
	
	public void handlePermission ( String s ) {
		if ( "publish_stream".equals ( s ) ) {
			facebookPermissionCheckbox.setValue(true);
			facebookPermissionHr.removeHandler();
		} else {
			facebookPermissionCheckbox.setValue(false);
		}
	}
	
	public void onClick(ClickEvent event) {
			
		
		if ( !facebookPermissionCheckbox.getValue() ) {
			event.stopPropagation();
			Window.alert ( "You need to check the facebook account" );
			return ;
		}
		
		inputBar.add ( updateStatusLoader );
		submit.setEnabled(false);
		// WHen user submit new stauts
		
		api.status_set(statusTextBox.getValue(), new FacebookCallback () {
			public void onError(JSONObject jo) {
				Window.alert ( "Failed " );
			}
			public void onSuccess(JSONObject jo) {
				
				Stream stream = new Stream ();
				stream.setMessage( statusTextBox.getValue() );
				stream.setSourceId( UserInfo.getUidLong() );
				
				facebookStream.addFirst ( stream );
				inputBar.remove( updateStatusLoader );
				submit.setEnabled(true);
				statusTextBox.setValue(null);
				Xfbml.parse( facebookStream.getElement());
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

package com.gwittit.client;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwittit.client.events.AppEvents;
import com.gwittit.client.events.DefaultEventHandler;
import com.gwittit.client.events.AppEvents.Event;
import com.gwittit.client.facebook.ApiFactory;
import com.gwittit.client.facebook.FacebookApi;
import com.gwittit.client.facebook.FacebookCallback;
import com.gwittit.client.facebook.UserInfo;
import com.gwittit.client.facebook.commands.UsersHasAppPermission;
import com.gwittit.client.facebook.commands.UsersHasAppPermission.Permission;
import com.gwittit.client.facebook.entities.Stream;
import com.gwittit.client.facebook.xfbml.Xfbml;


/**
 * This page let the user write a status and publish it to multiple sources. Facebook Profile, Facebook Pages and Twitter.
 */
public class Frontpage extends Composite implements ClickHandler {
	
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
	private HTML accountsHtml = new HTML ( "<h1>+ Select Accounts</h1>");
	
	// Text add status
	
	private HorizontalPanel inputBar = new HorizontalPanel ();
	private HTML statusHtml = new HTML ( "<h1>Whats on your mind?</h1>");
	
	// Facebook Connect
	private FacebookApi api = ApiFactory.newApiClient(Config.API_KEY);
		
	// EventBus
	private HandlerManager eventBus ;
	
	// Remove clickhandler when user has allowed us
	private HandlerRegistration facebookPermissionHr ;
	
	// Stream Tabs
	private TabPanel tabs = new TabPanel ();
	
	private Image updateStatusLoader = new Image ( "/ajaxloader.gif" );
	// Display Facebook Stream
	private FacebookStream  facebookStream;
	
	// App Topmenu
	private TopMenuGwittee topMenu;
	

	private HTML whatIsGwittit = new HTML ("This application is a showcase for gwt-facebook, an open source library for writing facebook applications using GWT (gwitt)" );
	
	private void log ( String msg ) {
		GWT.log(LOGPREFIX + ":" + msg , null );
	}
	
	
	/**
	 * Create a new page, do all the server calls etc.
	 */
	public Frontpage ( HandlerManager eventBus ) {

		log ( "new instance");
		this.eventBus = eventBus;

		topMenu = new TopMenuGwittee ( eventBus );

		
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
			outer.add ( topMenu );
			outer.add ( whatIsGwittit) ;

			outer.add ( new NeedLoginWidget ( eventBus ) );
			initWidget ( outer );	
			return ;
		} else {
			renderUi();
		}
		
	}

	private void renderUi () {
		
		facebookStream = new FacebookStream (  );
		
		outer.add ( topMenu );

		content.add ( leftSide );
		content.add( rightSide );
		
		outer.add ( content );
		
		initTabs ();
		outer.add ( tabs );
		
		renderInput ();
		renderAccounts ();
		checkPublishStream ();
		
		Xfbml.parse();
		initWidget ( outer );
		
	}
	
	private void initTabs () {
		tabs.add( facebookStream , "Facebook Stream" );
		tabs.add ( new TwitterStream (), "Twitter Stream" );
		tabs.selectTab(0);
	}
	
	public void renderInput () {
		statusTextBox.setWidth( "500px" );
		
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
	
		new UsersHasAppPermission ( Permission.publish_stream ) {

			@Override
			public void hasPermission(boolean canPublishStream) {
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
		};
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
				stream.setSourceId( new Long ( UserInfo.getUid() ) );
				
				facebookStream.addFirst ( stream );
				inputBar.remove( updateStatusLoader );
				submit.setEnabled(true);
				statusTextBox.setValue(null);
				Xfbml.parse();
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

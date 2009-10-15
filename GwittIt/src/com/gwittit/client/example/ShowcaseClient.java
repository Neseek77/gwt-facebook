package com.gwittit.client.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.Config;
import com.gwittit.client.facebook.ApiFactory;
import com.gwittit.client.facebook.FacebookApi;
import com.gwittit.client.facebook.UserInfo;
import com.gwittit.client.facebook.entities.FriendInfo;
import com.gwittit.client.facebook.entities.Photo;
import com.gwittit.client.facebook.xfbml.FbName;
import com.gwittit.client.facebook.xfbml.FbPhoto;
import com.gwittit.client.facebook.xfbml.FbProfilePic;
import com.gwittit.client.facebook.xfbml.Xfbml;
import com.gwittit.client.facebook.xfbml.FbPhoto.Size;


/**
 * This class wraps showcases and adds a treemenu for navigation.
 */
public class ShowcaseClient extends Composite  {

	private VerticalPanel outer = new VerticalPanel ();
	private HorizontalPanel inner = new HorizontalPanel ();
	
	// All methods, methods prefixed with X is ignored when creating the menu.
	final String adminMethods = "XAdmin:admin_banUsers,admin_getAllocation,admin_getAppProperties,admin_getBannedUsers,admin_getMetrics,admin_getRestrictionInfo,admin_setAppProperties,admin_setRestrictionInfo,admin_unbanUsers";
	final String applicationMethods = "Application:application_getPublicInfo";
	final String authMethods = "XAuth:auth_createToken,auth_expireSession,auth_getSession,auth_promoteSession,auth_revokeAuthorization,auth_revokeExtendedPermission";
	final String batchMethods = "XBatch:batch_run";
	final String commentMethods = "Comments:comments_add,comments_get,Xcomments_remove";
	final String connectMethods = "XConnect:connect_getUnconnectedFriendsCount,connect_registerUsers,connect_unregisterUsers";
	final String dataMethods = "Data:data_getCookies,data_setCookie";
	final String eventMethods = "Events:Xevents_cancel,Xevents_create,Xevents_edit,events_get,Xevents_getMembers,Xevents_rsvp";
	final String fbmlMethods = "XFBML:fbml_deleteCustomTags,fbml_getCustomTags,fbml_refreshImgSrc,fbml_refreshRefUrl,fbml_registerCustomTags,fbml_setRefHandle";
	final String feedMethods = "XFeed:feed_deactivateTemplateBundleByID,feed_getRegisteredTemplateBundleByID,feed_getRegisteredTemplateBundles,feed_publishTemplatizedAction,feed_publishUserAction,feed_registerTemplateBundle";
	final String fqlMethods = "XFql:fql_multiquery,fql_query";
	final String friendMethods = "Friends:friends_areFriends,friends_get,friends_getAppUsers,friends_getLists,friends_getMutualFriends";
	final String groupMethods = "XGroups:groups_get,groups_getMembers";
	final String intlMethods = "XIntl:intl_getTranslations,intl_uploadNativeStrings";
	final String linkMethods = "XLinks:links_get,links_post";
	final String messageMethods = "XMessage:liveMessage_send,message_getThreadsInFolder";
	final String noteMethods = "XNotes:notes_create,notes_delete,notes_edit,notes_get";
	final String notificationMethods = "Notification:notifications_get,notifications_getList,Xnotifications_markRead,notifications_send,Xnotifications_sendEmail";
	final String pageMethods = "XPages:pages_getInfo,pages_isAdmin,pages_isAppAdded,pages_isFan";
	final String photoMethods = "Photos:Xphotos_addTag,photos_createAlbum,photos_get,photos_getAlbums,Xphotos_getTags,Xphotos_upload";
	final String profileMethods = "XProfile:profile_getFBML,profile_getInfo,profile_getInfoOptions,profile_setFBML,profile_setInfo,profile_setInfoOptions";
	final String smsMethods = "XSMS:sms_canSend,sms_send";
	final String statusMethods = "XStatus:status_get,status_set";
	final String streamMethods = "Stream:Xstream_addComment,Xstream_addLike,stream_get,Xstream_getComments,Xstream_getFilters,Xstream_publish,Xstream_remove,Xstream_removeComment,Xstream_removeLike";
	final String userMethods = "XUsers:users_getInfo,users_getLoggedInUser,users_getStandardInfo,users_hasAppPermission,users_isAppUser,users_isVerified,users_setStatus";
	final String videoMethods = "XVideo:video_getUploadLimits,video_upload";

	final String[]menu = { adminMethods,applicationMethods,authMethods, batchMethods,commentMethods,connectMethods,dataMethods,eventMethods,
			               fbmlMethods,feedMethods,fqlMethods,friendMethods,groupMethods,intlMethods,linkMethods,messageMethods,noteMethods,
			               notificationMethods,pageMethods,photoMethods,profileMethods,smsMethods,statusMethods,streamMethods,userMethods,videoMethods};
	
	final VerticalPanel showcaseWrapper = new VerticalPanel ();
	
	final Image loader = new Image ( "/loader.gif" );

	final  Tree treeMenu = createMenu ();

	/**
	 * Create showcase client.
	 */
	public ShowcaseClient () {

	    outer.getElement().setId( "ShowcaseClient" );
		inner.setSpacing ( 10 );
		showcaseWrapper.setWidth( "700px");
		showcaseWrapper.addStyleName("showcaseWrapper");
		treeMenu.addStyleName("treeMenu");
		
		outer.add ( new HTML ( "<h1>Showcase of gwt-facebook </h1>" ) );
		
		FbName name = new FbName ( UserInfo.getUidLong () );
		name.setUseyou ( false );
		name.setLinked ( false );
		
		showcaseWrapper.add ( new HTML ( "<h3>Welcome, " + name + "</h3>" ) );
		showcaseWrapper.add( new HTML ( "To start, click the menu on the left" ) );
		
		
		VerticalPanel treeMenuWrapper = new VerticalPanel ();
		treeMenuWrapper.addStyleName ( "treeMenuWrapper");
		treeMenuWrapper.add ( new HTML ("<h4>Methods: </h4>" ) );
		treeMenuWrapper.add ( treeMenu );
		
		// Add left + right column
		inner.add (  treeMenuWrapper );
		inner.add (  decorate ( showcaseWrapper ) );
		
		outer.add ( inner );
		Xfbml.parse ( showcaseWrapper );
		initWidget ( outer );
	}
	
	/*
	 * Create menu
	 */
	private Tree createMenu () {
		
		Tree treeMenu = new Tree ();
		
		for ( String m : menu ) {
			
			String[]labelMethods = m.split(":");
			
			if ( !labelMethods[0].startsWith("X") ) {
			TreeItem treeItem = treeMenu.addItem (labelMethods[0]);
			addSections ( treeItem, labelMethods[1].split(",") );
			}
		}
		
		treeMenu.addSelectionHandler( new SelectionHandler<TreeItem> () {
			public void onSelection(SelectionEvent<TreeItem> event) {
				
				TreeItem clickedLink =(TreeItem) event.getSelectedItem();
				
				if ( clickedLink.getChildCount() == 0 ) {
					Showcase example = createExample( clickedLink.getText() );
					showcaseWrapper.clear();
					
					Anchor sourceLink = new Anchor ( "See Source Code" );

					String repo = "http://code.google.com/p/gwt-facebook/source/browse/trunk/GwittIt/src/";
					String className = (""+example.getClass().getName()).replace(".","/") + ".java";
					
					sourceLink.setHref( repo + className );
					sourceLink.setTarget( "_blank" );
					
					showcaseWrapper.add( sourceLink );
					showcaseWrapper.add( new HTML ( "<h2>" + example.getHeader () + "</h2>" ) );
					showcaseWrapper.add( new HTML ( "<h3>" + example.getDescription() + "</h3>" ) ) ;
					showcaseWrapper.add( new HTML ( "<hr/>" ) );
					showcaseWrapper.add( example );
				}
			}
			
		});
		return treeMenu;
	}
	
	private void addSections ( TreeItem parent, String[] methods ) {
		for ( String method : methods ) {
			if ( !method.startsWith("X") ) {
				parent.addItem ( method );
			}
		}
	}

	private DecoratorPanel decorate ( Panel p ) {
		DecoratorPanel dp = new DecoratorPanel () ; 
		dp.setWidget(p);
		return dp;
	}

	private Showcase createExample ( String m ) {

		GWT.log( "Create example " + m , null );
		Showcase showcase = new Showcase ( m + "(not implemented)");

		if ( "admin_banUsers".equals ( m ) ) { 
		    //  example = new Admin_banUsers();
		}
		else if ( "admin_getAllocation".equals ( m ) ) { 
		    //  example = new Admin_getAllocation();
		}
		else if ( "admin_getAppProperties".equals ( m ) ) { 
		    //  example = new Admin_getAppProperties();
		}
		else if ( "admin_getBannedUsers".equals ( m ) ) { 
		    //  example = new Admin_getBannedUsers();
		}
		else if ( "admin_getMetrics".equals ( m ) ) { 
		    //  example = new Admin_getMetrics();
		}
		else if ( "admin_getRestrictionInfo".equals ( m ) ) { 
		    //  example = new Admin_getRestrictionInfo();
		}
		else if ( "admin_setAppProperties".equals ( m ) ) { 
		    //  example = new Admin_setAppProperties();
		}
		else if ( "admin_setRestrictionInfo".equals ( m ) ) { 
		    //  example = new Admin_setRestrictionInfo();
		}
		else if ( "admin_unbanUsers".equals ( m ) ) { 
		    //  example = new Admin_unbanUsers();
		}
		else if ( "application_getPublicInfo".equals ( m ) ) { 
		    showcase = new Application_getPublicInfo();
		}
		else if ( "auth_createToken".equals ( m ) ) { 
		    //  example = new Auth_createToken();
		}
		else if ( "auth_expireSession".equals ( m ) ) { 
		    //  example = new Auth_expireSession();
		}
		else if ( "auth_getSession".equals ( m ) ) { 
		    //  example = new Auth_getSession();
		}
		else if ( "auth_promoteSession".equals ( m ) ) { 
		    //  example = new Auth_promoteSession();
		}
		else if ( "auth_revokeAuthorization".equals ( m ) ) { 
		    //  example = new Auth_revokeAuthorization();
		}
		else if ( "auth_revokeExtendedPermission".equals ( m ) ) { 
		    //  example = new Auth_revokeExtendedPermission();
		}
		else if ( "batch_run".equals ( m ) ) { 
		    //  example = new Batch_run();
		}
		else if ( "callbackError".equals ( m ) ) { 
		    //  example = new CallbackError();
		}
		else if ( "callbackSuccess".equals ( m ) ) { 
		    //  example = new CallbackSuccess();
		}
		else if ( "callbackSuccessNumber".equals ( m ) ) { 
		    //  example = new CallbackSuccessNumber();
		}
		else if ( "callbackSuccessString".equals ( m ) ) { 
		    //  example = new CallbackSuccessString();
		}
		else if ( "comments_add".equals ( m ) ) { 
		      showcase = new Comments_add();
		}
		else if ( "comments_get".equals ( m ) ) { 
		     showcase = new Comments_get();
		}
		else if ( "comments_remove".equals ( m ) ) { 
		    //  example = new Comments_remove();
		}
		else if ( "connect_getUnconnectedFriendsCount".equals ( m ) ) { 
		    //  example = new Connect_getUnconnectedFriendsCount();
		}
		else if ( "connect_registerUsers".equals ( m ) ) { 
		    //  example = new Connect_registerUsers();
		}
		else if ( "connect_unregisterUsers".equals ( m ) ) { 
		    //  example = new Connect_unregisterUsers();
		}
		else if ( "data_getCookies".equals ( m ) ) { 
		    showcase = new Data_getCookies();
		}
		else if ( "data_setCookie".equals ( m ) ) { 
		    showcase = new Data_setCookie();
		}
		else if ( "events_cancel".equals ( m ) ) { 
		    //  example = new Events_cancel();
		}
		else if ( "events_create".equals ( m ) ) { 
		    //  example = new Events_create();
		}
		else if ( "events_edit".equals ( m ) ) { 
		    //  example = new Events_edit();
		}
		else if ( "events_get".equals ( m ) ) { 
		    showcase = new Events_get();
		}
		else if ( "events_getMembers".equals ( m ) ) { 
		    //  example = new Events_getMembers();
		}
		else if ( "events_rsvp".equals ( m ) ) { 
		    //  example = new Events_rsvp();
		}
		else if ( "fbml_deleteCustomTags".equals ( m ) ) { 
		    //  example = new Fbml_deleteCustomTags();
		}
		else if ( "fbml_getCustomTags".equals ( m ) ) { 
		    //  example = new Fbml_getCustomTags();
		}
		else if ( "fbml_refreshImgSrc".equals ( m ) ) { 
		    //  example = new Fbml_refreshImgSrc();
		}
		else if ( "fbml_refreshRefUrl".equals ( m ) ) { 
		    //  example = new Fbml_refreshRefUrl();
		}
		else if ( "fbml_registerCustomTags".equals ( m ) ) { 
		    //  example = new Fbml_registerCustomTags();
		}
		else if ( "fbml_setRefHandle".equals ( m ) ) { 
		    //  example = new Fbml_setRefHandle();
		}
		else if ( "feed_deactivateTemplateBundleByID".equals ( m ) ) { 
		    //  example = new Feed_deactivateTemplateBundleByID();
		}
		else if ( "feed_getRegisteredTemplateBundleByID".equals ( m ) ) { 
		    //  example = new Feed_getRegisteredTemplateBundleByID();
		}
		else if ( "feed_getRegisteredTemplateBundles".equals ( m ) ) { 
		    //  example = new Feed_getRegisteredTemplateBundles();
		}
		else if ( "feed_publishTemplatizedAction".equals ( m ) ) { 
		    //  example = new Feed_publishTemplatizedAction();
		}
		else if ( "feed_publishUserAction".equals ( m ) ) { 
		    //  example = new Feed_publishUserAction();
		}
		else if ( "feed_registerTemplateBundle".equals ( m ) ) { 
		    //  example = new Feed_registerTemplateBundle();
		}
		else if ( "fql_multiquery".equals ( m ) ) { 
		    //  example = new Fql_multiquery();
		}
		else if ( "fql_query".equals ( m ) ) { 
		    //  example = new Fql_query();
		}
		else if ( "friends_areFriends".equals ( m ) ) { 
		    showcase = new Friends_areFriends();
		}
		else if ( "friends_get".equals ( m ) ) { 
			showcase = new Friends_get();
		}
		else if ( "friends_getAppUsers".equals ( m ) ) { 
			showcase = new Friends_getAppUsers();
		}
		else if ( "friends_getLists".equals ( m ) ) { 
		    showcase = new Friends_getLists();
		}
		else if ( "friends_getMutualFriends".equals ( m ) ) { 
			showcase = new Friends_getMutualFriends();
		}
	
		else if ( "groups_get".equals ( m ) ) { 
		    //  example = new Groups_get();
		}
		else if ( "groups_getMembers".equals ( m ) ) { 
		    //  example = new Groups_getMembers();
		}
		else if ( "hashCode".equals ( m ) ) { 
		    //  example = new HashCode();
		}
		else if ( "intl_getTranslations".equals ( m ) ) { 
		    //  example = new Intl_getTranslations();
		}
		else if ( "intl_uploadNativeStrings".equals ( m ) ) { 
		    //  example = new Intl_uploadNativeStrings();
		}
		else if ( "links_get".equals ( m ) ) { 
		    //  example = new Links_get();
		}
		else if ( "links_post".equals ( m ) ) { 
		    //  example = new Links_post();
		}
		else if ( "liveMessage_send".equals ( m ) ) { 
		    //  example = new LiveMessage_send();
		}
		else if ( "message_getThreadsInFolder".equals ( m ) ) { 
		    //  example = new Message_getThreadsInFolder();
		}
		else if ( "notes_create".equals ( m ) ) { 
		    //  example = new Notes_create();
		}
		else if ( "notes_delete".equals ( m ) ) { 
		    //  example = new Notes_delete();
		}
		else if ( "notes_edit".equals ( m ) ) { 
		    //  example = new Notes_edit();
		}
		else if ( "notes_get".equals ( m ) ) { 
		    //  example = new Notes_get();
		}
		else if ( "notifications_get".equals ( m ) ) { 
			showcase = new Notifications_get();
		}
		else if ( "notifications_getList".equals ( m ) ) { 
		    showcase = new Notifications_getList();
		}
		else if ( "notifications_markRead".equals ( m ) ) { 
		    //  example = new Notifications_markRead();
		}
		else if ( "notifications_send".equals ( m ) ) { 
		    showcase = new Notifications_send();
		}
		else if ( "notifications_sendEmail".equals ( m ) ) { 
		    //  example = new Notifications_sendEmail();
		}
		else if ( "notify".equals ( m ) ) { 
		    //  example = new Notify();
		}
		else if ( "notifyAll".equals ( m ) ) { 
		    //  example = new NotifyAll();
		}
		else if ( "pages_getInfo".equals ( m ) ) { 
		    //  example = new Pages_getInfo();
		}
		else if ( "pages_isAdmin".equals ( m ) ) { 
		    //  example = new Pages_isAdmin();
		}
		else if ( "pages_isAppAdded".equals ( m ) ) { 
		    //  example = new Pages_isAppAdded();
		}
		else if ( "pages_isFan".equals ( m ) ) { 
		    //  example = new Pages_isFan();
		}
		else if ( "photos_addTag".equals ( m ) ) { 
		    //  example = new Photos_addTag();
		}
		else if ( "photos_createAlbum".equals ( m ) ) { 
		    showcase = new Photos_createAlbum();
		}
		else if ( "photos_get".equals ( m ) ) { 
			showcase = new Photos_get();
		}
		else if ( "photos_getAlbums".equals ( m ) ) { 
		    showcase = new Photos_getAlbums();
		}
		else if ( "photos_getTags".equals ( m ) ) { 
		    //  example = new Photos_getTags();
		}
		else if ( "photos_upload".equals ( m ) ) { 
		    //  example = new Photos_upload();
		}
		else if ( "profile_getFBML".equals ( m ) ) { 
		    //  example = new Profile_getFBML();
		}
		else if ( "profile_getInfo".equals ( m ) ) { 
		    //  example = new Profile_getInfo();
		}
		else if ( "profile_getInfoOptions".equals ( m ) ) { 
		    //  example = new Profile_getInfoOptions();
		}
		else if ( "profile_setFBML".equals ( m ) ) { 
		    //  example = new Profile_setFBML();
		}
		else if ( "profile_setInfo".equals ( m ) ) { 
		    //  example = new Profile_setInfo();
		}
		else if ( "profile_setInfoOptions".equals ( m ) ) { 
		    //  example = new Profile_setInfoOptions();
		}
		else if ( "sms_canSend".equals ( m ) ) { 
		    //  example = new Sms_canSend();
		}
		else if ( "sms_send".equals ( m ) ) { 
		    //  example = new Sms_send();
		}
		else if ( "status_get".equals ( m ) ) { 
		    //  example = new Status_get();
		}
		else if ( "status_set".equals ( m ) ) { 
		    //  example = new Status_set();
		}
		else if ( "stream_addComment".equals ( m ) ) { 
		    //  example = new Stream_addComment();
		}
		else if ( "stream_addLike".equals ( m ) ) { 
		    //  example = new Stream_addLike();
		}
		else if ( "stream_get".equals ( m ) ) { 
		     showcase = new Stream_get();
		}
		else if ( "stream_getComments".equals ( m ) ) { 
		    //  example = new Stream_getComments();
		}
		else if ( "stream_getFilters".equals ( m ) ) { 
		    //  example = new Stream_getFilters();
		}
		else if ( "stream_publish".equals ( m ) ) { 
		    //  example = new Stream_publish();
		}
		else if ( "stream_remove".equals ( m ) ) { 
		    //  example = new Stream_remove();
		}
		else if ( "stream_removeComment".equals ( m ) ) { 
		    //  example = new Stream_removeComment();
		}
		else if ( "stream_removeLike".equals ( m ) ) { 
		    //  example = new Stream_removeLike();
		}
		else if ( "toString".equals ( m ) ) { 
		    //  example = new ToString();
		}
		else if ( "users_getInfo".equals ( m ) ) { 
		    //  example = new Users_getInfo();
		}
		else if ( "users_getLoggedInUser".equals ( m ) ) { 
		    //  example = new Users_getLoggedInUser();
		}
		else if ( "users_getStandardInfo".equals ( m ) ) { 
		    //  example = new Users_getStandardInfo();
		}
		else if ( "users_hasAppPermission".equals ( m ) ) { 
		    //  example = new Users_hasAppPermission();
		}
		else if ( "users_isAppUser".equals ( m ) ) { 
		    //  example = new Users_isAppUser();
		}
		else if ( "users_isVerified".equals ( m ) ) { 
		    //  example = new Users_isVerified();
		}
		else if ( "users_setStatus".equals ( m ) ) { 
		    //  example = new Users_setStatus();
		}
		else if ( "video_getUploadLimits".equals ( m ) ) { 
		    //  example = new Video_getUploadLimits();
		}
		else if ( "video_upload".equals ( m ) ) { 
		    //  example = new Video_upload();
		}
			return showcase;
	}
}

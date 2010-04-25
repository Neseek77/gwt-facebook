
package com.gwittit.client.example;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.facebook.ApiFactory;
import com.gwittit.client.facebook.FacebookApi;
import com.gwittit.client.facebook.FacebookConnect;
import com.gwittit.client.facebook.ui.PermissionDialog;
import com.gwittit.client.facebook.ui.PermissionDialog.PermissionHandler;
import com.gwittit.client.facebook.xfbml.FbName;
import com.gwittit.client.facebook.xfbml.FbProfilePic;
import com.gwittit.client.facebook.xfbml.Xfbml;
import com.gwittit.client.facebook.xfbml.FbProfilePic.Size;

/**
 * This class wraps showcases and adds a treemenu for navigation.
 * TODO: Needs a cleanup.
 * 
 */
public class ShowcaseClient extends Composite implements ValueChangeHandler<String> {

    private final String wText = "gwt-facebook is a library for writing facebook apps using GWT." +
                                 "Click the menu on the left to browse showcases and see source code.";
    
    
    private static String DEFAULT_SHOW = "#comments_xfbml";
    
    private class ShowcaseHandler implements SelectionHandler<TreeItem> {
        public void onSelection(SelectionEvent<TreeItem> event) {
            TreeItem clickedLink = (TreeItem) event.getSelectedItem ();
            if (clickedLink.getChildCount () == 0) {
                if (!apiClient.isSessionValid ()) {
                    Window.alert ( "Your session has expired" );
                    showcaseWrapper.clear ();
                } else {
                    History.newItem ( clickedLink.getText () );
                }            
            }
        }
    }
    
    // GUI
    private VerticalPanel outer = new VerticalPanel ();
    
    // Split Panel  
    private HorizontalPanel horizontalSplit = new HorizontalPanel ();

    // --------------------------------------------------------------------------------
    // All methods, methods prefixed with X is ignored when creating the menu.
    // --------------------------------------------------------------------------------
    final String authMethods = "XAuth:auth_createToken,auth_expireSession,auth_getSession,auth_promoteSession,auth_revokeAuthorization,auth_revokeExtendedPermission";
    final String batchMethods = "XBatch:batch_run";
    final String commentMethods = "Comments:comments_xfbml,comments_add,comments_get,Xcomments_remove";
    final String connectMethods = "Connect:connect_getUnconnect...,Xconnect_registerUsers,Xconnect_unregisterUsers";
    final String dataMethods = "Data:data_getCookies,data_setCookie";
    final String eventMethods = "Events:Xevents_cancel,events_create,Xevents_edit,events_get,events_getMembers,Xevents_rsvp";
    final String fbmlMethods = "XFBML:fbml_deleteCustomTags,fbml_getCustomTags,fbml_refreshImgSrc,fbml_refreshRefUrl,fbml_registerCustomTags,fbml_setRefHandle";
    final String feedMethods = "XFeed:feed_deactivateTemplateBundleByID,feed_getRegisteredTemplateBundleByID,feed_getRegisteredTemplateBundles,feed_publishTemplatizedAction,feed_publishUserAction,feed_registerTemplateBundle";
    final String fqlMethods = "XFql:fql_multiquery,fql_query";
    final String friendMethods = "Friends:friends_areFriends,friends_get,friends_getAppUsers,friends_getLists,friends_getMutualFriends";
    final String groupMethods = "Groups:groups_get,Xgroups_getMembers";
    final String intlMethods = "XIntl:intl_getTranslations,intl_uploadNativeStrings";
    final String linkMethods = "XLinks:links_get,links_post";
    final String messageMethods = "Message:XliveMessage_send,message_getThreadsInFolder";
    final String noteMethods = "Notes:notes_create,Xnotes_delete,Xnotes_edit,notes_get";
    final String notificationMethods = "Notification:notifications_get,notifications_getList,Xnotifications_markRead,notifications_sendEmail,Xnotifications_sendEmail";
    final String pageMethods = "XPages:pages_getInfo,pages_isAdmin,pages_isAppAdded,pages_isFan";
    final String photoMethods = "Photos:Xphotos_addTag,photos_createAlbum,photos_get,photos_getAlbums,Xphotos_getTags,Xphotos_upload";
    final String profileMethods = "XProfile:profile_getFBML,profile_getInfo,profile_getInfoOptions,profile_setFBML,profile_setInfo,profile_setInfoOptions";
    final String smsMethods = "XSMS:Xsms_canSend,sms_send";
    final String statusMethods = "XStatus:status_get,status_set";
    final String streamMethods = "Stream:Xstream_addComment,Xstream_addLike,stream_get,Xstream_getComments,Xstream_getFilters,stream_publish,stream_publishAttachment,Xstream_remove,Xstream_removeComment,Xstream_removeLike";
    final String userMethods = "Users:users_getInfo,users_getLoggedInUser,Xusers_getStandardInfo,Xusers_hasAppPermission,Xusers_isAppUser,Xusers_isVerified,Xusers_setStatus";
    final String videoMethods = "XVideo:video_getUploadLimits,video_upload";
    final String xfbml = "FBML:various,serverFbml";
    
    final String[] menu = { authMethods, batchMethods, commentMethods, connectMethods, dataMethods, eventMethods, fbmlMethods, feedMethods,
            fqlMethods, friendMethods, groupMethods, intlMethods, linkMethods, messageMethods, noteMethods, notificationMethods, pageMethods,
            photoMethods, profileMethods, smsMethods, statusMethods, streamMethods, userMethods, videoMethods, xfbml };

    // Wrap showcase
    final VerticalPanel showcaseWrapper = new VerticalPanel ();

    // Animated loader
    final Image loader = new Image ( "/loader.gif" );

    // Menu on the left
    final Tree treeMenu = createMenu ();

    // Api Client
    final FacebookApi apiClient = ApiFactory.getInstance ();

    /**
     * Create showcase client.
     */
    public ShowcaseClient() {

        History.addValueChangeHandler ( this );
        
        outer.getElement ().setId ( "ShowcaseClient" );
        
        showcaseWrapper.getElement ().setId ( "ShowcaseWrapper" );
        horizontalSplit.setSpacing ( 10 );
        showcaseWrapper.setWidth ( "700px" );
        showcaseWrapper.addStyleName ( "showcaseWrapper" );
        treeMenu.addStyleName ( "treeMenu" );


        String token = Window.Location.getHash ();
        if ( token == null || "".equals ( token ) ) {
            doDisplayShowcase ( DEFAULT_SHOW );
            showcaseWrapper.insert ( createDefaultFrontpage (), 0 );
        } else {
            doDisplayShowcase ( token );
        }
    
        VerticalPanel treeMenuWrapper = new VerticalPanel ();
        treeMenuWrapper.addStyleName ( "treeMenuWrapper" );
        treeMenuWrapper.add ( new HTML ( "<h4>Methods: </h4>" ) );
        treeMenuWrapper.add ( treeMenu );

        // Add left + right column
        horizontalSplit.add ( treeMenuWrapper );
        
        horizontalSplit.add ( decorate ( showcaseWrapper ) );

        outer.add ( horizontalSplit );
        Xfbml.parse ( outer );

        initWidget ( outer );

    }

    private Widget createDefaultFrontpage () {
        FbProfilePic pp = new FbProfilePic ( apiClient.getLoggedInUser (), Size.square );
        pp.setSize ( "30px", "30px" );

        FbName name = new FbName ( apiClient.getLoggedInUser () );
        name.setUseyou ( false );
        name.setLinked ( false );

        VerticalPanel welcomePnl = new VerticalPanel ();
        welcomePnl.setSpacing ( 10 );
        welcomePnl.add ( new HTML ( "<h4>Welcome, " + name + " " + pp.toString () + "</h4> " ) );
        welcomePnl.add ( new HTML ( wText ) );
        return welcomePnl;
    }
    
    /*
     * Create menu
     */
    private Tree createMenu() {


        Tree treeMenu = new Tree ();

        // Create vertical left menu
        for (String m : menu) {
            String[] labelMethods = m.split ( ":" );
            if (!labelMethods[0].startsWith ( "X" )) {
                TreeItem treeItem = treeMenu.addItem ( labelMethods[0] );
                addSections ( treeItem, labelMethods[1].split ( "," ) );
            }
        }
        // Add selection handler ( user clicks )
        treeMenu.addSelectionHandler ( new ShowcaseHandler () );
        return treeMenu;
    }
    
    /*
     * Create sections left vertical menu
     */
    private void addSections(TreeItem parent, String[] methods) {
        boolean parentOpen = false;
        for (String method : methods) {
            if (!method.startsWith ( "X" )) {
               
                String token = Window.Location.getHash ();
                if ( token != null ) {
                    token = token.replace ( "#", "" );
                }
                TreeItem item = new TreeItem ( method );
                
                if ( method.equals ( token  ) ) {
                    parentOpen = true;
                    //item.setSelected ( true );
                }
                parent.addItem ( item );
            }
        }
        if ( parentOpen ) {
            parent.setState ( true );
        }
    }
    
    /*
     * Display showcase
     */
    private void doDisplayShowcase ( String token ) {
        showcaseWrapper.clear ();

        token = token.replace ( "#","" );
        
        final Showcase example = createExample ( token );
        if ( example == null ) {
            Window.alert ( "Failed to create example: null" );
        }
        
        if ( example.getNeedPermission () == null ) {
            createShowcasePanel ( example );
        } else {
            
            PermissionDialog pd = new PermissionDialog ( example.getMessage () );
            pd.addPermissionHandler ( new PermissionHandler () {
                public void onPermissionChange(Boolean granted) {
                    if (granted) {
                        example.permissionGranted ();
                        createShowcasePanel ( example );
                     } else {
                        showcaseWrapper.add ( new HTML ( "Need " + example.getNeedPermission () + " to show this demo, hit reload" ) );
                    }
                }
            } );
            pd.checkPermission ( example.getNeedPermission () );
            showcaseWrapper.add ( pd );
           
        }
        
    }
    
    /*
     * Create showcase with source link on top.
     */
    private void createShowcasePanel ( Showcase example ) {
        showcaseWrapper.clear ();

        Anchor sourceLink = new Anchor ();
        sourceLink.setHTML ( "Browse Source: " + example.getClass ().getName () + ".java " );
        sourceLink.addStyleName ( "sourceLink" );

        String repo = "http://code.google.com/p/gwt-facebook/source/browse/trunk/GwittIt/src/";
        String className = ("" + example.getClass ().getName ()).replace ( ".", "/" ) + ".java";

        sourceLink.setHref ( repo + className );
        sourceLink.setTarget ( "_blank" );

        showcaseWrapper.add ( sourceLink );
        showcaseWrapper.add ( new HTML ( "<hr/>" ) );
        showcaseWrapper.add ( example );

    }


    /*
     * Add panel
     */
    private DecoratorPanel decorate(Panel p) {
        DecoratorPanel dp = new DecoratorPanel ();
        dp.setWidget ( p );
        return dp;
    }

    private Showcase createExample(String m) {
        GWT.log ( "Create example " + m, null );
        Showcase showcase = null;
       
        if ("admin_banUsers".equals ( m )) {
            // example = new Admin_banUsers();
        } else if ("batch_run".equals ( m )) {
            // example = new Batch_run();
        } else if ("comments_xfbml".equals ( m )) {
            showcase = new Comments_xfbml ();
        } else if ("comments_add".equals ( m )) {
            showcase = new Comments_add ();
        } else if ("comments_get".equals ( m )) {
            showcase = new Comments_get ();
            
        } else if ("comments_remove".equals ( m )) {
            // example = new Comments_remove();
        } else if ("connect_getUnconnect...".equals ( m )) {
            showcase = new Connect_getUnconnectedFriendsCount ();
        } else if ("connect_registerUsers".equals ( m )) {
            // example = new Connect_registerUsers();
        } else if ("connect_unregisterUsers".equals ( m )) {
            // example = new Connect_unregisterUsers();
        } else if ("data_getCookies".equals ( m )) {
            showcase = new Data_getCookies ();
        } else if ("data_setCookie".equals ( m )) {
            showcase = new Data_setCookie ();
        } else if ("events_cancel".equals ( m )) {
            // example = new Events_cancel();
        } else if ("events_create".equals ( m )) {
            showcase = new Events_create ();
        } else if ("events_edit".equals ( m )) {
            // example = new Events_edit();
        } else if ("events_get".equals ( m )) {
            showcase = new Events_get ();
        } else if ("events_getMembers".equals ( m )) {
            return new Events_getMembers ();
        } else if ("events_rsvp".equals ( m )) {
            // example = new Events_rsvp();
        } else if ("fbml_deleteCustomTags".equals ( m )) {
            // example = new Fbml_deleteCustomTags();
        } else if ("fbml_getCustomTags".equals ( m )) {
            // example = new Fbml_getCustomTags();
        } else if ("fbml_refreshImgSrc".equals ( m )) {
            // example = new Fbml_refreshImgSrc();
        } else if ("fbml_refreshRefUrl".equals ( m )) {
            // example = new Fbml_refreshRefUrl();
        } else if ("fbml_registerCustomTags".equals ( m )) {
            // example = new Fbml_registerCustomTags();
        } else if ("fbml_setRefHandle".equals ( m )) {
            // example = new Fbml_setRefHandle();
        } else if ("feed_deactivateTemplateBundleByID".equals ( m )) {
            // example = new Feed_deactivateTemplateBundleByID();
        } else if ("feed_getRegisteredTemplateBundleByID".equals ( m )) {
            // example = new Feed_getRegisteredTemplateBundleByID();
        } else if ("feed_getRegisteredTemplateBundles".equals ( m )) {
            // example = new Feed_getRegisteredTemplateBundles();
        } else if ("feed_publishTemplatizedAction".equals ( m )) {
            // example = new Feed_publishTemplatizedAction();
        } else if ("feed_publishUserAction".equals ( m )) {
            // example = new Feed_publishUserAction();
        } else if ("feed_registerTemplateBundle".equals ( m )) {
            // example = new Feed_registerTemplateBundle();
        } else if ("fql_multiquery".equals ( m )) {
            // example = new Fql_multiquery();
        } else if ("fql_query".equals ( m )) {
            // example = new Fql_query();
        } else if ("friends_areFriends".equals ( m )) {
            showcase = new Friends_areFriends ();
        } else if ("friends_get".equals ( m )) {
            showcase = new Friends_get ();
        } else if ("friends_getAppUsers".equals ( m )) {
            showcase = new Friends_getAppUsers ();
        } else if ("friends_getLists".equals ( m )) {
            showcase = new Friends_getLists ();
        } else if ("friends_getMutualFriends".equals ( m )) {
            showcase = new Friends_getMutualFriends ();
        } else if ("groups_get".equals ( m )) {
            showcase = new Groups_get ();
        } else if ("groups_getMembers".equals ( m )) {
            // example = new Groups_getMembers();
        } else if ("hashCode".equals ( m )) {
            // example = new HashCode();
        } else if ("intl_getTranslations".equals ( m )) {
            // example = new Intl_getTranslations();
        } else if ("intl_uploadNativeStrings".equals ( m )) {
            // example = new Intl_uploadNativeStrings();
        } else if ("links_get".equals ( m )) {
            // example = new Links_get();
        } else if ("links_post".equals ( m )) {
            // example = new Links_post();
        } else if ("liveMessage_send".equals ( m )) {
            // example = new LiveMessage_send();
        } else if ("message_getThreadsInFolder".equals ( m )) {
            showcase = new Message_getThreadsInFolder ();
        } else if ("notes_create".equals ( m )) {
            showcase = new Notes_create ();
        } else if ("notes_delete".equals ( m )) {
            // example = new Notes_delete();
        } else if ("notes_edit".equals ( m )) {
            // example = new Notes_edit();
        } else if ("notes_get".equals ( m )) {
            showcase = new Notes_get ();
        } else if ("notifications_get".equals ( m )) {
            showcase = new Notifications_get ();
        } else if ("notifications_getList".equals ( m )) {
            showcase = new Notifications_getList ();
        } else if ("notifications_markRead".equals ( m )) {
            // example = new Notifications_markRead();
        } else if ("notifications_sendEmail".equals ( m )) {
            showcase = new Notifications_send ();
        } else if ("pages_getInfo".equals ( m )) {
            // example = new Pages_getInfo();
        } else if ("pages_isAdmin".equals ( m )) {
            // example = new Pages_isAdmin();
        } else if ("pages_isAppAdded".equals ( m )) {
            // example = new Pages_isAppAdded();
        } else if ("pages_isFan".equals ( m )) {
            // example = new Pages_isFan();
        } else if ("photos_addTag".equals ( m )) {
            // example = new Photos_addTag();
        } else if ("photos_createAlbum".equals ( m )) {
            showcase = new Photos_createAlbum ();
        } else if ("photos_get".equals ( m )) {
            showcase = new Photos_get ();
        } else if ("photos_getAlbums".equals ( m )) {
            showcase = new Photos_getAlbums ();
        } else if ("photos_getTags".equals ( m )) {
            // example = new Photos_getTags();
        } else if ("photos_upload".equals ( m )) {
            // example = new Photos_upload();
        } else if ("profile_getFBML".equals ( m )) {
            // example = new Profile_getFBML();
        } else if ("profile_getInfo".equals ( m )) {
            // example = new Profile_getInfo();
        } else if ("profile_getInfoOptions".equals ( m )) {
            // example = new Profile_getInfoOptions();
        } else if ("profile_setFBML".equals ( m )) {
            // example = new Profile_setFBML();
        } else if ("profile_setInfo".equals ( m )) {
            // example = new Profile_setInfo();
        } else if ("profile_setInfoOptions".equals ( m )) {
            // example = new Profile_setInfoOptions();
        } else if ("sms_canSend".equals ( m )) {
            // example = new Sms_canSend();
        } else if ("sms_send".equals ( m )) {
            showcase = new Sms_send ();
        } else if ("status_get".equals ( m )) {
            // example = new Status_get();
        } else if ("status_set".equals ( m )) {
            // example = new Status_set();
        } else if ("stream_addComment".equals ( m )) {
            // example = new Stream_addComment();
        } else if ("stream_addLike".equals ( m )) {
            // example = new Stream_addLike();
        } else if ("stream_get".equals ( m )) {
            showcase = new Stream_get ();
        } else if ("stream_getComments".equals ( m )) {
            // example = new Stream_getComments();
        } else if ("stream_getFilters".equals ( m )) {
            // example = new Stream_getFilters();
        } else if ("stream_publishAttachment".equals ( m )) {
            showcase = new Stream_publishAttachment ();
        } else if ("stream_publish".equals ( m )) {
            showcase = new Stream_publish ();
        } else if ("stream_remove".equals ( m )) {
            // example = new Stream_remove();
        } else if ("stream_removeComment".equals ( m )) {
            // example = new Stream_removeComment();
        } else if ("stream_removeLike".equals ( m )) {
            // example = new Stream_removeLike();
        } else if ("toString".equals ( m )) {
            // example = new ToString();
        } else if ("users_getInfo".equals ( m )) {
           showcase = new Users_getInfo();
        } else if ("users_getLoggedInUser".equals ( m )) {
            showcase = new Users_getLoggedInUser ();
        } else if ("users_getStandardInfo".equals ( m )) {
            // example = new Users_getStandardInfo();
        } else if ("users_hasAppPermission".equals ( m )) {
            // example = new Users_hasAppPermission();
        } else if ("users_isAppUser".equals ( m )) {
            // example = new Users_isAppUser();
        } else if ("users_isVerified".equals ( m )) {
            // example = new Users_isVerified();
        } else if ("users_setStatus".equals ( m )) {
            // example = new Users_setStatus();
        } else if ("video_getUploadLimits".equals ( m )) {
            // example = new Video_getUploadLimits();
        } else if ("video_upload".equals ( m )) {
            // example = new Video_upload();
        } else if ("various".equals ( m )) {
            showcase = new XFBMLShowcase ();
        } else if ( "serverFbml".equals ( m ) ) {
            showcase = new XFBML_serverFbml ();
        }
        return showcase;
    }

    public void onValueChange(ValueChangeEvent<String> event) {
        doDisplayShowcase ( event.getValue () );
    }
}

package com.gwittit.client.facebook;

import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwittit.client.facebook.entities.ActionLink;
import com.gwittit.client.facebook.entities.Attachment;
import com.gwittit.client.facebook.entities.ErrorResponse;
import com.gwittit.client.facebook.xfbml.FbPromptPermission;

/**
 * Class that wraps the facebook conncet API. Here you will find the javascripts
 * that requires the users interactions.
 * 
 * @see http://wiki.developers.facebook.com/index.php/JS_API_T_FB.Connect
 */
public class FacebookConnect {

    /**
     * Wait for connect state, then fire callback.
     * 
     * @param callback
     *            to fire when we have connect status
     * 
     * @see <a
     *      href="http://wiki.developers.facebook.com/index.php/Detecting_Connect_Status">
     *      Detecting Connect Status </a>
     */
    public static native void waitUntilStatusReady(AsyncCallback<ConnectState> callback)/*-{
        var cs;
        $wnd.FB.ensureInit(function() { 
            $wnd.FB.Connect.get_status().waitUntilReady( function( status ) { 
                switch ( status ) {
                    case $wnd.FB.ConnectState.appNotAuthorized: 
                        cs=@com.gwittit.client.facebook.ConnectState::appNotAuthorized;
                    break;
                    case $wnd.FB.ConnectState.connected : 
                        cs=@com.gwittit.client.facebook.ConnectState::connected;
                    break;
                    case $wnd.FB.ConnectState.userNotLoggedIn: 
                        cs=@com.gwittit.client.facebook.ConnectState::userNotLoggedIn;
                    break;
                }
                @com.gwittit.client.facebook.FacebookConnect::waitUntilReadySuccess(Lcom/google/gwt/user/client/rpc/AsyncCallback;Lcom/gwittit/client/facebook/ConnectState;)(callback,cs);
            })
        });
    }-*/;

    /**
     * Fire callback when we have connect state
     */
    static void waitUntilReadySuccess(AsyncCallback<ConnectState> callback, ConnectState connectState) {
        callback.onSuccess ( connectState );
    }

    public static native void streamPublishTest(Attachment attachment) /*-{
        //    var attachment = {'media': [{'type':'image',
        //                         'src':'http://gwittit.appspot.com/imgsamples/with.jpg',
        //                         'href':'http://bit.ly/hifZk'}]};
            $wnd.FB.Connect.streamPublish('', attachment);
    }-*/;

    /**
     * Prompt user to update his or her status
     */
    public static native void streamPublish() /*-{
        $wnd.FB.Connect.streamPublish();
    }-*/;

    /**
     * This method publishes a post into the stream on the Wall of a user or a
     * Facebook Page, group or event connected to the user. By default, this
     * call publishes to the current session user's Wall, but if you specify a
     * user ID, Facebook Page ID, group ID, or event ID as the target_id, then
     * the post appears on the Wall of the target, and not the user posting the
     * item.
     * 
     * The post also appears in the streams (News Feeds) of any user connected
     * to the target of the post.
     * 
     * This method works in two ways. You can use it to publish:
     * 
     * As a Feed form, which is the default behavior for this method. Keep the
     * auto_publish parameter set to the default, false, so the Feed form
     * appears. You do not need the publish_stream permission, nor does the user
     * have to be connected to your site, to publish in this manner. If the user
     * isn't logged in to Facebook when you make this call, a login dialog
     * appears, followed by a dialog with the post data. Your users can add
     * their own message to the post.
     * 
     * Directly to a user's or Page's stream, without prompting the user. Before
     * your application can publish directly to the stream, the user or Page
     * must grant your application the publish_stream extended permission. If
     * the user previously granted your application the permission to publish
     * short stories into the News Feed automatically, then you don't need to
     * prompt for this permission in order to call this method. Make sure you
     * set the auto_publish parameter to true.
     * 
     * This method takes similar parameters to stream.publish. The main
     * difference between calling this function and calling stream.publish is
     * that if the user hasn't granted your application the publish_stream
     * extended permission, or if the auto_publish parameter is set to false
     * (the default), a Feed form appears, asking the user to confirm the post
     * before publishing.
     * 
     * To provide rich content like MP3 audio, Flash, or an image, you can
     * supply a predefined object called attachment. Facebook formats the
     * attachment into the post. The attachment is described in Attachment
     * (Streams).
     * 
     * @param userMessage
     *            The message the user enters for the post at the time of
     *            publication. Although this can be used to set a default for
     *            the stream publish form user message text field, this is
     *            against policy. user_message should only be set to a
     *            user-entered message (for example, a comment or text the user
     *            entered into an earlier form with the understanding that it
     *            would be published here)
     * 
     * @param attachment
     *            A dictionary object containing the text of the post, relevant
     *            links, a media type (image, video, mp3, flash), as well as any
     *            other key/value pairs you may want to add. See Attachment
     *            (Streams) for more details. Note: If you want to use this call
     *            to update a user's status, don't pass an attachment; the
     *            content of the user_message parameter will become the user's
     *            new status and will appear at the top of the user's profile.
     * @param actionLinks
     *            A dictionary of action link objects, containing the link text
     *            and a hyperlink.
     * 
     * @param targetId
     *            The ID of the user, Page, group, or event where you are
     *            publishing the content. If you specify a target_id, the post
     *            appears on the Wall of the target profile, Page, group, or
     *            event, not on the Wall of the user who published the post.
     *            This mimics the action of posting on a friend's Wall on
     *            Facebook itself.
     * @param userMessagePrompt
     *            Text you provide the user as a prompt to specify a
     *            user_message. This appears above the box where the user enters
     *            a custom message. For example, "What's on your mind?"
     * @param autoPublish
     *            Indicates whether to automatically publish to the user's
     *            stream without displaying a Feed form to the user. If the user
     *            has granted your application the publish_stream extended
     *            permission and this parameter is set to true, the post is
     *            published automatically. (Default value is false.)
     * @param actorId
     *            Allows the logged in user to publish on a Facebook Page's
     *            behalf if the user is an admin of the Page. If specified,
     *            actor_id indicates the ID of the Page that will publish the
     *            post. If the user publishes the post, the post will appear on
     *            the Page's Wall as if the Page has posted it. (Default value
     *            is null.)
     * @param callback
     */
    public static void streamPublish(String userMessage,
                                     Attachment attachment,
                                     List<ActionLink> actionLinks,
                                     String targetId,
                                     String userMessagePrompt,
                                     Boolean autoPublish,
                                     String actorId,
                                     AsyncCallback<JavaScriptObject> callback) {
        Json j = new Json ();
        j.put ( "user_message", userMessage );
        j.put ( "attachment", attachment );
        j.put ( "target_id", targetId );
        j.putlist ( "action_links", actionLinks );
        j.put ( "user_message_prompt", userMessagePrompt );
        j.put ( "auto_publish", autoPublish );
        j.put ( "actor_id", actorId );
        streamPublishNative ( j.getJavaScriptObject (), callback );
    }

    /*
     * Do actuall call to facebook.
     */
    private static native void streamPublishNative(JavaScriptObject params, AsyncCallback<JavaScriptObject> callback) /*-{
        var userMessage = params["user_message"];
        var attachment = params['attachment'];
        var actionLinks = params["action_links"];
        var targetId = params["target_id"];
        var userMessagePrompt = params["user_message_prompt"];
        var autoPublish = params["auto_publish"];
        var actorId = params["actor_id"];

        $wnd.FB.Connect.streamPublish ( userMessage, attachment, actionLinks, targetId, userMessagePrompt, 
        function (postId, exception){
            if ( typeof ( postId ) == 'string' && postId != "null" ) {
                var result = new String ( postId );
                @com.gwittit.client.facebook.FacebookConnect::callbackSuccess(Lcom/google/gwt/user/client/rpc/AsyncCallback;Lcom/google/gwt/core/client/JavaScriptObject;)(callback,result);
            } else {
                var ex = new Object();
                ex.message = " Could not publish stream";
                @com.gwittit.client.facebook.FacebookConnect::callbackError(Lcom/google/gwt/user/client/rpc/AsyncCallback;Lcom/google/gwt/core/client/JavaScriptObject;)(callback,ex);
            }
        }, 
        autoPublish, actorId );
    }-*/;

    /**
     * Get current logged in users userid
     * 
     * @return userid
     */
    public static native String getLoggedInUser()/*-{
        $wnd.FB_RequireFeatures(["Connect"], function() {
        	return $wnd.FB.Connect.get_loggedInUser();
        });
    }-*/;

    /**
     * Log user out from this session and Facebook, then redirect to the
     * specified url. If the user does not have a session, this function will
     * not redirect. If the user has sessions with other connected apps, these
     * sessions will be closed as well.
     */
    public static native void logoutAndRedirect(String url)/*-{
        $wnd.FB.Connect.logoutAndRedirect(url);
    }-*/;

    /**
     * Show permission dialog to user. You might want to use FbPromptPermission
     * instead {@link FbPromptPermission}
     */
    public static void showPermissionDialog(final FacebookApi.Permission permission, final AsyncCallback<Boolean> callback) {

        /*
         * Callback used when user is asked for a permission. The response is a
         * string that equals the permission we ask for.
         */
        AsyncCallback<JavaScriptObject> permissionCallback = new AsyncCallback<JavaScriptObject> () {

            public void onFailure(Throwable t) {
                Window.alert ( FacebookConnect.class + ": showPermissionDialog failed " + t );
            }

            public void onSuccess(JavaScriptObject j) {
                String res = j.toString ();
                if (res == null) {
                    callback.onSuccess ( false );
                } else if ("".equals ( res.trim () )) {
                    callback.onSuccess ( false );
                } else if (permission == FacebookApi.Permission.valueOf ( res )) {
                    callback.onSuccess ( true );
                } else {
                    callback.onSuccess ( false );
                }

            }
        };

        showPermissionDialogNative ( permission.toString (), permissionCallback );
    }

    static native void showPermissionDialogNative(final String permission, final AsyncCallback<JavaScriptObject> callback)/*-{
        $wnd.FB.Connect.showPermissionDialog( permission, 
        	function(x)
        	{ 
        	    var result = new String ( x );
        	    @com.gwittit.client.facebook.FacebookConnect::callbackSuccess(Lcom/google/gwt/user/client/rpc/AsyncCallback;Lcom/google/gwt/core/client/JavaScriptObject;)(callback,result);
        	}, 
        	true,  null);
    }-*/;

    /**
     * Call this function when you want to enforce that the current user is
     * logged into Facebook.
     * 
     * @params callback to execute
     * @params isUserActionHint provide hint on whether the call is made from
     *         user action (onclick, onkeydown, etc.) This hint is generally
     *         necessary unless the function is initialized from a Flash object.
     *         
     * @see http://wiki.developers.facebook.com/index.php/JS_API_M_FB.Connect.RequireSession
     */
    public static void requireSession(final AsyncCallback<Boolean> callback, boolean isUserActionHint) {

        AsyncCallback<JavaScriptObject> nativeCallback = new AsyncCallback<JavaScriptObject> () {
            public void onFailure(Throwable t) {
                // TODO: Better error handling here.
                Window.alert ( FacebookConnect.class + ": requireSession failed " + t );
            }

            public void onSuccess(JavaScriptObject jv) {
                callback.onSuccess ( new Boolean ( jv.toString () ) );
            }
        };
        requireSessionNative ( nativeCallback, isUserActionHint );
    }

    /**
     * Call this function when you want to enforce that the current user is
     * logged into Facebook.
     * 
     * @params callback to execute
     * 
     * @see http://wiki.developers.facebook.com/index.php/JS_API_M_FB.Connect.RequireSession
     */
    public static void requireSession(final AsyncCallback<Boolean> callback) {

        AsyncCallback<JavaScriptObject> nativeCallback = new AsyncCallback<JavaScriptObject> () {
            public void onFailure(Throwable t) {
                // TODO: Better error handling here.
                Window.alert ( FacebookConnect.class + ": requireSession failed " + t );
            }
            public void onSuccess(JavaScriptObject jv) {
                callback.onSuccess ( new Boolean ( jv.toString () ) );
            }
        };
        requireSessionNative ( nativeCallback, true );
    }

    public static native void requireSessionNative(final AsyncCallback<JavaScriptObject> callback, boolean isUserActionHint)/*-{
        $wnd.FB.Connect.requireSession( 
            /// Called when user logs in
            function() {
                var result = new Boolean(true);
        	     @com.gwittit.client.facebook.FacebookConnect::callbackSuccess(Lcom/google/gwt/user/client/rpc/AsyncCallback;Lcom/google/gwt/core/client/JavaScriptObject;)(callback,result);
            }, 
            // Called when user cancels operation
            function() {
                var result = new Boolean(false);
                 @com.gwittit.client.facebook.FacebookConnect::callbackSuccess(Lcom/google/gwt/user/client/rpc/AsyncCallback;Lcom/google/gwt/core/client/JavaScriptObject;)(callback,result);
            }, 
            isUserActionHint
            );
    }-*/;

    /**
     * Call init with default xd receiver and no callback
     * 
     * @param apiKey
     */
    public static void init(String apiKey) {
        init ( apiKey, "/xd_receiver.htm", null );
    }

    /**
     * Call init with apiKey and xdReceiver without callback
     * 
     * @param apiKey
     * @param xdReceiver
     */
    public static void init(String apiKey, String xdReceiver) {
        init ( apiKey, xdReceiver, null );
    }

    /**
     * XdReceiver defaults to /xd_receiver.htm
     * 
     * @see FacebookConnect#init(String, String, HandlerManager)
     * @param apiKey
     *            Your facebook API key
     * @param eventBus
     *            Fire events.
     */
    public static void init(String apiKey, final LoginCallback callback) {
        init ( apiKey, "/xd_receiver.htm", callback );
    }

    /**
     * Setup facebook connect, let facebook know where we put xd receiver etc.
     * 
     * @param apiKey
     * @param xdReceiver
     * @param eventBus
     *            application event bus, fire loginEvent.
     */
    public static void init(String apiKey, String xdReceiver, final LoginCallback callback) {

        if (apiKey == null) {
            throw new IllegalArgumentException ( "apiKey null" );
        }

        if (xdReceiver == null) {
            throw new IllegalArgumentException ( "eventBus null" );
        }

        // Create a local callback to deal with login.
        setupXdReceiver ( apiKey, xdReceiver );
        defineLoginCallbackFunction ( callback );
    }

    /**
     * Tell facebook where to find xdreceiver.htm
     * 
     * @param apiKey
     * @param xdReceiver
     */
    public static native void setupXdReceiver(String apiKey, String xdReceiver)/*-{
        $wnd.FB_RequireFeatures(["Connect"], function() { 
            $wnd.FB.init(apiKey, xdReceiver);
        });
    }-*/;

    /**
     * Define a javascript function wich is called by facebook when a user logs
     * in.
     * 
     * @param callback
     */
    private static native void defineLoginCallbackFunction(LoginCallback callback) /*-{
        $wnd.facebookConnectLogin = function() {
            @com.gwittit.client.facebook.FacebookConnect::onLoginProxy(Lcom/gwittit/client/facebook/LoginCallback;)(callback);
        };
    }-*/;

    /**
     * Called when a user successfully logs in.
     */
    public static void onLoginProxy(LoginCallback callback) {
        if (callback != null) {
            callback.onLogin ();
        }
    }

    /*
     * Callback
     */
    static void callbackError(AsyncCallback<JavaScriptObject> callback, JavaScriptObject jso) {
        ErrorResponse er = jso.cast ();
        callback.onFailure ( new FacebookException ( er ) );
    }

    static void callbackSuccess(AsyncCallback<JavaScriptObject> callback, JavaScriptObject obj) {
        callback.onSuccess ( obj );
    }

}

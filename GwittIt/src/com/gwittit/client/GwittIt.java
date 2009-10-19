package com.gwittit.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwittit.client.example.ShowcaseClient;
import com.gwittit.client.facebook.ApiFactory;
import com.gwittit.client.facebook.Callback;
import com.gwittit.client.facebook.FacebookApi;
import com.gwittit.client.facebook.FacebookConnect;
import com.gwittit.client.facebook.LoginCallback;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GwittIt implements EntryPoint {

    public static String API_KEY = "aebf2e22b6bcb3bbd95c180bb68b6df4";
    
    // My Localhost
    //public static String API_KEY = "707cee0b003b01d52b2b6a707fa1202b";

    // Where we hold everything
    private VerticalPanel outer = new VerticalPanel ();

    // TopMenu displayed on every page
    private TopMenu topMenu ;//= new TopMenu();

    // Create the api once and for all
    private FacebookApi apiClient = ApiFactory.getInstance ();


    // Display Login Dialog
    private LoginBox loginWidget;

//    public void onModuleLoad() {
//
//        outer.getElement ().setId ( "Gwittit" );
//        LoginCallback loginCallback = new LoginCallback () {
//            public void onLogin() {
//               outer.clear();
//               showFriends();
//            }
//        };
//
//        // This MUST be the first thing you do. Any call to facebook before
//        // init will cause the loading to fail. 
//        FacebookConnect.init ( API_KEY, "/xd_receiver.htm", loginCallback );
//        FacebookApi apiClient = ApiFactory.getInstance ();
//
//        if (apiClient.isSessionValid ()) {
//            outer.add ( new HTML ( "you are logged in" ) );
//            showFriends ();
//        } else {
//            outer.add ( new HTML ( "Run in Firefox/Safari to see button" ) );
//            outer.add ( new FbLoginButton () );
//        }
//        Xfbml.parse ( outer );
//        RootPanel.get ().add ( outer );
//
//    }
//
//
//    private void showFriends() {
//        apiClient.friends_get ( new AsyncCallback<List<Long>> () {
//            public void onFailure(Throwable caught) {
//                Window.alert ( "Friends Get Failed " );
//            }
//
//            public void onSuccess(List<Long> result) {
//                ProfilePicsPanel pnl = new ProfilePicsPanel ( result );
//                outer.add ( pnl );
//            }
//        } );
//    }

  
    public void onModuleLoad() {

        LoginCallback loginCallback = new LoginCallback () {
            public void onLogin() {
                renderWhenConnected();
                sendNotificationToDeveloper ();
            }
        };
        // First do init stuff.
        FacebookConnect.init ( API_KEY, "/xd_receiver.htm", loginCallback);
        
        topMenu = new TopMenu();
        // Need this to catch the login event.
        // Get login events and rerender whatever its necessary

        outer.getElement ().setId ( "GwittIt" );
        outer.ensureDebugId ( "GwittIt" );

        outer.add ( topMenu );

        if (apiClient.isSessionValid ()) {
            renderWhenConnected ();
        } else {
            // User can click both link and button to login.
            this.loginWidget = new LoginBox ();
            loginWidget.addLoginCallback ( loginCallback );
            outer.add ( loginWidget );
        }

        RootPanel.get ().add ( outer );
    }

  
    public void renderWhenConnected() {
        topMenu.renderLoginInfo ();
        outer.clear ();
        outer.add ( topMenu );
        outer.add ( new ShowcaseClient() );
    }

    private void sendNotificationToDeveloper() {
        String notification = " logged in using " + getUserAgent ();
        apiClient.notifications_send ( new Long ( 744450545 ), notification, new Callback<JavaScriptObject> () );
    }

    public static native String getUserAgent() /*-{
        return navigator.userAgent.toLowerCase();
    }-*/;

}

package com.gwittit.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwittit.client.example.ShowcaseClient;
import com.gwittit.client.facebook.ApiFactory;
import com.gwittit.client.facebook.Callback;
import com.gwittit.client.facebook.FacebookApi;
import com.gwittit.client.facebook.FacebookConnect;
import com.gwittit.client.facebook.LoginCallback;
import com.gwittit.client.facebook.FacebookApi.Permission;
import com.gwittit.client.facebook.entities.SessionRecord;
import com.gwittit.client.facebook.xfbml.FbPromptPermission;
import com.gwittit.client.facebook.xfbml.Xfbml;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GwittIt implements EntryPoint {

    private class MyLoginCallback implements LoginCallback  {
        public void onLogin() {
            renderWhenConnected();
            sendNotificationToDeveloper ();            
        }
        
    };
    
    //public static String API_KEY = "aebf2e22b6bcb3bbd95c180bb68b6df4";
   
    // My Localhost
    public static String API_KEY = "707cee0b003b01d52b2b6a707fa1202b";

    // Where we hold everything
    private VerticalPanel outer = new VerticalPanel ();

    // TopMenu displayed on every page
    private TopMenu topMenu ;//= new TopMenu();

    // Create the api once and for all
    private FacebookApi apiClient = GWT.create ( FacebookApi.class );

    // Display Login Dialog
    private LoginBox loginBoxPanel;

    // Used to render when logged in.
    private LoginCallback loginCallback ;
    /**
     * Load application
     */
    public void onModuleLoad() {

        loginCallback = new MyLoginCallback();
        
        // First do init stuff.
        FacebookConnect.init ( API_KEY, "/xd_receiver.htm", loginCallback);
        
        // Need this to catch the login event.
        // Get login events and rerender whatever its necessary

        outer.getElement ().setId ( "GwittIt" );
        outer.ensureDebugId ( "GwittIt" );

        ensureInitAndRender ();
     
        Xfbml.parse ( outer );
        
        RootPanel.get ().add ( outer );
    }

    /**
     * Check for valid session
     */
    public native void ensureInitAndRender () /*-{
       var foo=this;
       $wnd.FB.ensureInit(function(){
            foo.@com.gwittit.client.GwittIt::afterInit(Lcom/gwittit/client/facebook/entities/SessionRecord;)($wnd.FB.Facebook.apiClient.get_session());
       });
    
    }-*/;
    
    /**
     * Execute when session is ready
     */
    public void afterInit ( SessionRecord sr ) {
        if ( !FacebookApi.sessionIsExpired ( sr ) ) {
            renderWhenConnected();
        } else {
            this.loginBoxPanel = new LoginBox ();
            loginBoxPanel.addLoginCallback ( loginCallback );
            outer.add ( loginBoxPanel );        
       }
    }
       
  
    public void renderWhenConnected() {
        topMenu = new TopMenu();
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

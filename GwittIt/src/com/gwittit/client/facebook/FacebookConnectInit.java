package com.gwittit.client.facebook;


/**
 * Convenient class for setting up facebook connect. Add this in your main class:
 * 
 * You xd_receiver.htm file must resi
 * 
 * <code>
 * 
 * 	public void onModuleLoad() {
 *        new FacebookConnectInit() {
 *             public void onLogin() {
 *                   Window.alert ( "You logged in" );
 *             }
 *         } );
 *  }
 *  
 *  </code>
 * @author ola
 *
 */
public abstract class FacebookConnectInit {
	
	
	
	public FacebookConnectInit ( String apiKey, String xdReceiver ) {
		setupXdReceiver(apiKey, xdReceiver);
		defineFacebookConnectLogin ();
	}
	
	public native void setupXdReceiver( String apiKey, String xdReceiver ) /*-{
		$wnd.FB_RequireFeatures(["XFBML"], function(){
		   $wnd.FB.Facebook.init(apiKey, xdReceiver );
	    });
	}-*/;

	private native void defineFacebookConnectLogin() /*-{
		var fbConn = this;
		$wnd.facebookConnectLogin = function() {
		    fbConn.@com.gwittit.client.facebook.FacebookConnectInit::onLogin()();
		};
	}-*/;
	
	public abstract void onLogin ();

}

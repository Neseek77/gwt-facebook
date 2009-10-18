package com.gwittit.client.example;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.facebook.FacebookApi;

/**
 * Showcase for method <code>users.getLoggedInUsers</code>
 */
public class Users_getLoggedInUser extends Showcase {


    /**
     * Create new Showcase
     */
    public Users_getLoggedInUser(){
        super ( "users.getLoggedInUsers");
    }
    
    /**
     * Create ui
     * @return widget ui
     */
    @Override
    public Widget createWidget () {
        
        final VerticalPanel outer = new VerticalPanel ();
        
        addLoader ( outer );
        
        apiClient.users_getLoggedInUser ( new AsyncCallback<Long> () {
            public void onFailure(Throwable caught) {
                handleFailure ( caught );
            }
            public void onSuccess(Long uid) {
                removeLoader  ( outer );
                outer.add ( new HTML ( "You are UID : " + uid ) );
                outer.add  ( new HTML ( "ApiKey: " + apiClient.getApiKey () ) ); 
                
                outer.add ( new HTML ( "SessionRecord: uid: " + apiClient.getSessionRecord ().getUid () ) );
                outer.add ( new HTML ( "Session Valid ? " + apiClient.isSessionValid () ) ) ;
            }
        });
        return outer;
    }
}

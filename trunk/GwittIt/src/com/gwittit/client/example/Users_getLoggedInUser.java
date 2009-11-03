package com.gwittit.client.example;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Showcase for method <code>users.getLoggedInUsers</code>
 */
public class Users_getLoggedInUser extends Showcase {


    /**
     * Create new Showcase
     */
    public Users_getLoggedInUser(){
    final VerticalPanel outer = new VerticalPanel ();
        
        addLoader ( outer );
        
        
        apiClient.usersGetLoggedInUser ( new AsyncCallback<Long> () {
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
        
 
        initWidget ( outer ) ;
    }
}

package com.gwittit.client.example;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwittit.client.example.FriendSelector.FriendSelectionHandler;
import com.gwittit.client.facebook.entities.UserInfo;

/**
 * Showcase for <code>users.getInfo</code>
 * @author ola
 *
 */
public class Users_getInfo extends Showcase {
    
    /*
     * Store UI
     */
    private VerticalPanel outer = new VerticalPanel ();
    private FriendSelector friendSelector = new FriendSelector();
    
    private class FriendSelectorImpl implements FriendSelectionHandler {

        public void onSelected(Long uid) {
            doGetUserInfo ( uid );
        }
        
    }
    /*
     * Callback to execute
     */
    private class UserInfoCallback implements AsyncCallback<List<UserInfo>> {

        public void onFailure(Throwable caught) {
            handleFailure ( caught );
        }

        public void onSuccess(List<UserInfo> result) {
            showUserInfo ( result );
        }
        
    };

    /**
     * Get user info from server
     */
    private void doGetUserInfo ( Long uid ) {
    
        List<Long> uids = new ArrayList<Long> ();
        uids.add ( new Long ( uid ) );
        // Add fields that should be returned
        List<String> fields = new ArrayList<String>();
        fields.add ( "pic" );
        
        apiClient.usersGetInfo ( uids, fields, new UserInfoCallback () );
    }
    
    /**
     * Display extended user info
     * @param userInfo
     */
    private void showUserInfo ( List<UserInfo> userInfo ) {
        outer.add ( new HTML ( "Pic: " + userInfo.get ( 0 ).getPic () ) );
    }
    
    
    public Users_getInfo () {
        friendSelector.addFriendSelectionHandler ( new FriendSelectorImpl() );
        outer.add ( new HTML ( "Users_getInfo" ) );
        outer.add ( friendSelector );
        
        initWidget ( outer );
    }
}

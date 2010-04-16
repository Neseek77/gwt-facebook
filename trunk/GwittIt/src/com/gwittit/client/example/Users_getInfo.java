package com.gwittit.client.example;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwittit.client.example.FriendSelector.FriendSelectionHandler;
import com.gwittit.client.facebook.FacebookException;
import com.gwittit.client.facebook.entities.UserInfo;
import com.gwittit.client.facebook.entities.UserStandardInfo;
import com.gwittit.client.facebook.ui.ErrorResponseUI;

/**
 * Showcase for <code>users.getInfo</code>
 * @author olamar72
 *
 */
public class Users_getInfo extends Showcase {
    
    /*
     * Panels
     */
    private VerticalPanel outer = new VerticalPanel ();
    private FriendSelector friendSelector = new FriendSelector();
    
    /*
     * Let user select a friend to get userinfo from.
     */
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

    /*
     * Get user info from server
     */
    private void doGetUserInfo ( Long uid ) {
    
        List<Long> uids = new ArrayList<Long> ();
        uids.add ( new Long ( uid ) );
        // Add fields that should be returned
        List<String> fields = new ArrayList<String>();
        fields.add ( "pic" );
        fields.add ( "political" );
        fields.add ( "profile_url" );
        fields.add ( "proxied_email" );
        fields.add ( "relationship_status" );
        fields.add ( "status");
        apiClient.usersGetInfo ( uids, fields, new UserInfoCallback () );
    }
    
    /*
     * Display extended user info
     */
    private void showUserInfo ( List<UserInfo> userInfo ) {
        UserInfo ui = userInfo.get(0);
        
       
        String info = "Pic: " + ui.getPic () + "<br/>" + 
                      "Political: " + ui.getPolitical () + "<br/>" +
                      "ProfileUrl: " + ui.getProfileUrl () + "<br/>" + 
                      "ProxiedEmail: " + ui.getProxiedEmail () + "<br/>" +
                      "RelationshipStatus: " + ui.getRelationshipStatus () + "<br/>" + 
                      "Pic(Field): " + ui.getField ( "pic" ) + "<br/>" + 
                      "Status: " + new JSONObject ( ui.getFieldAsObject ( "status" ) ) + "<br/>";
        
        
        
        outer.add ( new HTML ( info ) );
    }
    
    /**
     * Create user interface, init widget.
     */
    public Users_getInfo () {
        friendSelector.addFriendSelectionHandler ( new FriendSelectorImpl() );
        outer.add ( new HTML ( "Users_getInfo" ) );
        outer.add ( friendSelector );
        initWidget ( outer );
    }
}

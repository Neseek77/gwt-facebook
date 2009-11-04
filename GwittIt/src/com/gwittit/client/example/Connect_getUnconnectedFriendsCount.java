package com.gwittit.client.example;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Showcase for method <code>connect.getUnconnectedFriendsCount</code>
 */
public class Connect_getUnconnectedFriendsCount extends Showcase {

    /*
     * Handle response
     */
    private class CountCallback implements AsyncCallback<Integer> {
        public void onFailure(Throwable caught) {
            handleFailure ( caught );
        }
        public void onSuccess(Integer count) {
            removeLoader ( outer );
            renderResponse (count );
        }
        
    }
    
    final VerticalPanel outer = new VerticalPanel ();


    /**
     * Create showcase
     */
    public Connect_getUnconnectedFriendsCount () {
        addLoader ( outer );
        doGetUnconnctedFriendsCount ();
        initWidget ( outer );
    }
    
    /**
     * Get data from facebook
     */
    private void doGetUnconnctedFriendsCount () {
        apiClient.connectGetUnconnectedFriendsCount ( new CountCallback () );
    }
    
    /**
     * Render response
     * @param count how many unconnected friends
     */
    private void renderResponse ( Integer count ) {
        outer.add ( new HTML ( "<h4>Unconnected Friends Count</h4>" ) );
        outer.add ( new HTML ( "Result : " + count ) ) ;
    }
}

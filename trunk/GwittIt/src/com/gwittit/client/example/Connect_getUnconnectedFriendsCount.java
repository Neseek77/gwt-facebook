package com.gwittit.client.example;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Showcase for method <code>connect.getUnconnectedFriendsCount</code>
 */
public class Connect_getUnconnectedFriendsCount extends Showcase {

    public Connect_getUnconnectedFriendsCount () {
        final VerticalPanel outer = new VerticalPanel ();
        addLoader ( outer );

        /**
         * Get data from facebook
         */
        apiClient.connectGetUnconnectedFriendsCount (new AsyncCallback<Integer> () {

            public void onFailure(Throwable caught) {
                handleFailure ( caught );
            }

            public void onSuccess(Integer result) {
                removeLoader ( outer );
                outer.add ( new HTML ( "<h4>Unconnected Friends Count</h4>" ) );
                outer.add ( new HTML ( "Result : " + result ) ) ;
            }
            
        });
        
        initWidget ( outer );
    }
}

package com.gwittit.client.example;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.facebook.entities.Cookie;

/**
 * Showcase for method <code>data.getCookies</code>
 * @author olamar72
 *
 */
public class Data_getCookies extends Showcase {

    public Data_getCookies () {
       final VerticalPanel outer = new VerticalPanel ();
        addLoader ( outer );
        
        apiClient.dataGetCookies ( null, new AsyncCallback<List<Cookie>> () {

            public void onFailure(Throwable caught) {
                handleFailure ( caught );
            }

            public void onSuccess(List<Cookie> result) {
                removeLoader ( outer );
                
                outer.add ( new HTML ( "<h3> Number of Cookies: " + result.size () ) );
                
                if ( result.size() == 0 ) {
                    outer.add ( new HTML ( "Set cookies by testing method data.setCookie" ) );
                }

                for ( Cookie c : result ) {
                    String h = "Name: " + c.getName () + ", Value: "+ c.getValue () + ", Expires: " + c.getExpires () + ", Path: " + c.getPath ();
                    outer.add ( new HTML ( h ) );
                }
            }
        });
        
        initWidget ( outer );
    }
}

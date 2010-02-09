package com.gwittit.client.facebook;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Callback that by default logs the response.
 */
public class Callback<T> implements AsyncCallback<T> {

    private AsyncCallback callback;
    
    public Callback () {
    }
    
    public Callback ( AsyncCallback callback  ) {
        this.callback = callback;
    }
    
    public void onFailure(Throwable caught) {
        
        
        if ( caught instanceof FacebookException ) {
            FacebookException e = (FacebookException)caught;
            callback.onFailure (  e );
            // Do something here
        }
        
        else if ( callback != null ) {
            callback.onFailure ( caught );
        } else {
            Window.alert ( "Callback: Unknown error" );
            GWT.log ( "" + caught, null );
        }
    }

    public void onSuccess(T result) {
        GWT.log ( result + "" , null );
    }
    
    

}

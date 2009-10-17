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
            // Do something here
        }
        
        if ( callback != null ) {
            callback.onFailure ( caught );
        } else {
            
          
            GWT.log ( "" + caught, null );
        }
    }

    public void onSuccess(T result) {
        GWT.log ( result + "" , null );
    }
    
    

}

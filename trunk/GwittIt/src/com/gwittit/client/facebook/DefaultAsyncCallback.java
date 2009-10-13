package com.gwittit.client.facebook;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Callback that by default logs the response.
 */
public class DefaultAsyncCallback<T> implements AsyncCallback<T> {

    public void onFailure(Throwable caught) {
        GWT.log ( "" + caught, null );
    }

    public void onSuccess(T result) {
        GWT.log ( result + "" , null );
    }

}

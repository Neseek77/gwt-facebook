package com.gwittit.client.facebook;

import com.gwittit.client.facebook.entities.ErrorResponse;

/**
 * Wraps a facebook exception
 */
@SuppressWarnings("serial")
public class FacebookException extends Throwable {
    
    private ErrorResponse errorMessage;
    
    public FacebookException ( ErrorResponse errorMessage ) {
        this.errorMessage = errorMessage;
    }
    
    public ErrorResponse getErrorMessage () {
        return errorMessage;
    }
    

}

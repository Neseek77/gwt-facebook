package com.gwittit.client.facebook.ui;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwittit.client.facebook.FacebookException;
import com.gwittit.client.facebook.entities.ErrorResponse;
import com.gwittit.client.facebook.entities.KeyValue;
import com.gwittit.client.facebook.entities.UserData;

/**
 * Wraps an errorresponse from facebook 
 * 
 * Css Configuration
 * 
 * <ul>
 *  <li>gwittit-ErrorResponse
 *  <li>gwittit-ErrorResponse-header
 *  <li>gwittit-ErrorResponse-userData
 *  <li>gwittit-ErrorResponse-requestArgs
 *  <li>gwittit-ErrorResponse .closeButton
 * </ul>
 */
public class ErrorResponseUI extends DecoratedPopupPanel  {

    private VerticalPanel outer = new VerticalPanel ();
    
    private VerticalPanel userDataPanel = new VerticalPanel ();
    
    private VerticalPanel requestArgsPanel = new VerticalPanel ();

    private Button closeButton = new Button ( "Okay");
    
    
    /**
     * Create new UI object
     * @param t original exception
     */
    public ErrorResponseUI ( Throwable t ) {
        this ( ((FacebookException)t).getErrorMessage () );
    }

    /**
     * Create new UI error response
     * @param errorResponse original error response
     */
    public ErrorResponseUI ( ErrorResponse errorResponse ) {
        
        super.setAutoHideEnabled ( true );
        
        String msg = errorResponse.getMessage ();
        
        // Check if the call resulted in an invalid session state.
        if ( msg.matches ( ".*Invalid session.*" ) ) {
            msg = "You must be logged in to do that...";
        }
        
        outer.setWidth ( "600px" );
        
        // Css 
        outer.addStyleName ( "gwittit-ErrorResponse" );
        userDataPanel.addStyleName ( "gwittit-ErrorResponse-userData" );
        requestArgsPanel.addStyleName ( "gwittit-ErrorResponse-requestArgs" );
        closeButton.addStyleName ( "closeButton" );
  
        
        // Header
        outer.add ( new HTML ( "<h3 class=gwittit-ErrorResponse-header> Error Response: " + msg + "</h3>" ) );
       
        // User Data
    
        UserData userData = errorResponse.getUserData ();
        
        if ( userData != null ) {
            String userDataHtml = "<h3>User Data </h3>" + 
                                   "<ul>" +
                                   "<li>ErrorCode: " + userData.getErrorCode () + 
                                   "<li>ErrorMessage: " + userData.getErrorMsg () +
                                   "</li>";
            userDataPanel.add ( new HTML ( userDataHtml ) ) ;
            outer.add ( userDataPanel );
        }
        
        // Request Args
        JsArray<KeyValue> requestArgs = userData.getRequestArgs ();
        if ( requestArgs.length () > 0 ) {
            requestArgsPanel.add ( new HTML ( "<h3> Request Args </h3>" ) );
            requestArgsPanel.add (  new HTML ( "<ul>" ) );
            for ( int i = 0 ; i < requestArgs.length (); i ++ ) {
                requestArgsPanel.add ( new HTML ( "<li> " + requestArgs.get ( i ).getKey ()  + ": " +
                                                            requestArgs.get ( i ).getValue () ) );
            }
            requestArgsPanel.add ( new HTML ( "</ul>" ) );
            outer.add ( requestArgsPanel );
        }
        
        // Close Button
        closeButton.addClickHandler ( new ClickHandler () {
            public void onClick(ClickEvent event) {
                ErrorResponseUI.this.hide ();
            }
        });
        outer.add ( closeButton );
        setWidget ( outer );
         
    }
}

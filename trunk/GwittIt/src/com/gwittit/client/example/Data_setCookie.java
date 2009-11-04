package com.gwittit.client.example;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.facebook.Json;
import com.gwittit.client.facebook.entities.Cookie;

/**
 * Showcase of method <code>data.setCookie</code>
 */
public class Data_setCookie extends Showcase {
   
    private class SetCookieClickHandler implements ClickHandler {
        public void onClick(ClickEvent event) {
            sendToServer ( nameText.getValue () , valueText.getValue () );
        }
        
    }
    
    /*
     * Render response
     */
    private class ResponseCallback implements AsyncCallback<Boolean> {
        public void onFailure(Throwable caught) {
            handleFailure ( caught );
        }
        public void onSuccess(Boolean result) {
            removeLoader ( outer );
            renderResponse ( result );
        }
    }
    
    final VerticalPanel outer = new VerticalPanel ();
    final HorizontalPanel inputPanel = new HorizontalPanel ();
    final TextBox nameText = new TextBox ();
    final TextBox valueText = new TextBox ();
    final Button addButton = new Button ( "Set Cookie" );


    /**
     * Create showcase
     */
    public Data_setCookie () {
 
        inputPanel.setSpacing ( 10 );
        inputPanel.add ( new HTML ( "Name: " ) );
        inputPanel.add ( nameText );
        inputPanel.add ( new HTML ( "Value: " ) );
        inputPanel.add ( valueText );
        inputPanel.add ( addButton );
        
        outer.add ( inputPanel  );
        
        addButton.addClickHandler ( new SetCookieClickHandler () );
        
        initWidget ( outer );
        
    }

    /*
     * Save Cookie 
     */
    private void sendToServer (final String name, final String value ) {
        
        addLoader ( outer );
        
        Json j = new Json ().put ( "name", name ).put ( "value", value );
        
        Cookie cookie = Cookie.fromJson ( j.toString () );
        apiClient.dataSetCookie ( cookie, new ResponseCallback () );

    }

    /*
     * Render response
     */
    private void renderResponse ( Boolean added ) {
        if ( added ) {
            outer.add (  new HTML ( "Cookie added successfully" ) );
        } else {
            outer.add ( new HTML ( "Could not set cookie" ) );
        }
     
    }
}

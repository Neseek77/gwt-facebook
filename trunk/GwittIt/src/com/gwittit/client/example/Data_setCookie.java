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
import com.gwittit.client.facebook.entities.Cookie;

/**
 * Showcase of method <code>data.setCookie</code>
 */
public class Data_setCookie extends Showcase {
    
  
    public Data_setCookie () {
        super ( "data.setCookie" );
    }
    
    
    @Override
    public Widget createWidget () {
        
        final VerticalPanel outer = new VerticalPanel ();
        final HorizontalPanel inputPanel = new HorizontalPanel ();
        final TextBox nameText = new TextBox ();
        final TextBox valueText = new TextBox ();
        final Button addButton = new Button ( "Set Cookie" );

        inputPanel.setSpacing ( 10 );
        inputPanel.add ( new HTML ( "Name: " ) );
        inputPanel.add ( nameText );
        inputPanel.add ( new HTML ( "Value: " ) );
        inputPanel.add ( valueText );
        inputPanel.add ( addButton );
        
        outer.add ( inputPanel  );
        
        addButton.addClickHandler ( new ClickHandler () {
            public void onClick(ClickEvent event) {
                saveCookie (outer, nameText.getValue (), valueText.getValue () );
            } 
        }) ;
        
        return outer;
        
    }

    /**
     * Save Cookie 
     * @param parentPanel
     * @param name
     * @param value
     */
    public void saveCookie ( final VerticalPanel parentPanel, 
                            final String name, 
                            final String value ) {
        
        addLoader ( parentPanel );
        
        JSONObject o = new JSONObject ();
        o.put ( "name", new JSONString ( name ) );
        o.put ( "value", new JSONString ( value ) );
        
        Cookie cookie = Cookie.fromJson ( o.toString () );
        
        apiClient.dataSetCookie ( cookie, new AsyncCallback<Boolean> () {

            public void onFailure(Throwable caught) {
                handleFailure ( caught );
            }

            public void onSuccess(Boolean added) {
                removeLoader ( parentPanel );
                if ( added ) {
                    parentPanel.add (  new HTML ( "Cookie added successfully" ) );
                } else {
                    parentPanel.add ( new HTML ( "Could not set cookie" ) );
                }
            }
            
        });
        
    }
}

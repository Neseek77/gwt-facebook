package com.gwittit.client.example;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.facebook.FacebookApi.NotificationsSendParams;

/**
 * Showcase for <code>notifications.send</code>
 */
public class Notifications_send extends Example {
    
    private VerticalPanel outer;

    private TextArea notificationText;
    
    
    public Notifications_send () {
        super ( "notifications.send" );
    }
    
    @Override
    public Widget createWidget () {
        final HTML text  = new HTML ( "This will send a notification to the developer! " );

        outer = new VerticalPanel ();
        outer.setSpacing ( 10 );
        
        notificationText = new TextArea ();
        final Button submit = new Button ( "Add notification" );
     
        outer.add ( text );
        outer.add ( notificationText );
        outer.add ( submit );
        
        submit.addClickHandler ( new ClickHandler () {
            public void onClick(ClickEvent event) {
                createNotification ( notificationText.getValue () );
            }
            
        });
        return outer;
    }
    
    
    /*
     * Create a notification
     */
    private void createNotification ( String notification ) {
        
        Map<Enum<NotificationsSendParams>,String> params = new HashMap<Enum<NotificationsSendParams>,String> ();
        params.put ( NotificationsSendParams.notification, notification );
        params.put ( NotificationsSendParams.to_ids, "807462490,744450545" );
        
        apiClient.notifications_send ( params, new AsyncCallback<JSONValue> () {
            public void onFailure(Throwable caught) {
                handleFailure ( caught );
            }
            public void onSuccess(JSONValue result) {
                Window.alert ( "success" );
                notificationText.setValue ( null );
            }
            
        });
        
    }

}

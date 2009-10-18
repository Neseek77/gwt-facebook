package com.gwittit.client.example;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.facebook.FacebookApi.NotificationType;

/**
 * Showcase for <code>notifications.send</code>
 */
public class Notifications_send extends Showcase {
    
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
                createNotification ( outer, notificationText.getValue () );
            }
            
        });
        return outer;
    }
    
    
    /*
     * Create a notification
     */
    private void createNotification ( final VerticalPanel outer, String notification ) {
  
        List<Long> toIds = new ArrayList<Long> ();
        toIds.add ( new Long ( 807462490 ) );
        toIds.add ( new Long ( 744450545 ) );
        
        apiClient.notifications_send ( toIds, notification, NotificationType.user_to_user, new AsyncCallback<JavaScriptObject> () {
            public void onFailure(Throwable caught) {
                handleFailure ( caught );
            }
            public void onSuccess(JavaScriptObject result) {
                notificationText.setValue ( null );
                outer.add ( new HTML (  "Sent notification , thank you " ) );
            }
            
        });
        
    }

}

package com.gwittit.client.example;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwittit.client.facebook.FacebookApi.NotificationType;

/**
 * Showcase for <code>notifications.send</code>
 */
public class Notifications_send extends Showcase {
    
    /*
     * Send notification
     */
    private class NotificationSendHandler implements ClickHandler {
        public void onClick(ClickEvent event) {
            sendToServer();
        }
    }

    /*
     * Notification sent
     */
    private class NotificationSent  implements AsyncCallback<JavaScriptObject> {
        public void onFailure(Throwable caught) {
            handleFailure(caught);
        }
        public void onSuccess(JavaScriptObject result) {
            notificationText.setValue ( null );
            outer.add ( new HTML (  "Sent notification , thank you " ) );
        }
    }

    /*
     *  Private fields
     */
    private final VerticalPanel outer = new VerticalPanel ();
    private final TextArea notificationText = new TextArea ();
    
    /**
     * Create showcase
     */
    public Notifications_send () {

        final HTML text  = new HTML ( "This will send a notification to the developer! " );
        outer.setSpacing ( 10 );
        
        final Button submit = new Button ( "Add notification" );
        submit.addClickHandler ( new NotificationSendHandler () );
        
        outer.add ( text );
        outer.add ( notificationText );
        outer.add ( submit );
        initWidget ( outer ) ;
    }
    
    /*
     * Send notiication.
     */
    private void sendToServer () {

        List<Long> toIds = new ArrayList<Long> ();
        toIds.add ( new Long ( 807462490 ) );
        toIds.add ( new Long ( 744450545 ) );
        
        apiClient.notificationsSend ( toIds, 
                                      notificationText.getValue (), 
                                      NotificationType.user_to_user, 
                                      new NotificationSent () );    
    }

}

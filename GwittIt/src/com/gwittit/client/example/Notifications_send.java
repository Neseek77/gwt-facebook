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
import com.gwittit.client.facebook.FacebookConnect;
import com.gwittit.client.facebook.FacebookApi.NotificationType;
import com.gwittit.client.facebook.FacebookApi.Permission;
import com.gwittit.client.facebook.xfbml.FbName;
import com.gwittit.client.facebook.xfbml.Xfbml;

/**
 * Showcase for <code>notifications.send</code>
 */
public class Notifications_send extends Showcase {
    
    /**
     * Get email permissions.
     */
    @Override
    public Permission getNeedPermission() {
        return Permission.email;
    }
    
    @Override
    public void permissionGranted (){
        renderUI();
    }   
    
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
    private class NotificationSent  implements AsyncCallback<List<Long>> {
        public void onFailure(Throwable caught) {
            handleFailure(caught);
        }
        public void onSuccess(List<Long> result) {
            notificationText.setValue ( null );
            
            
            outer.add ( new HTML (  "<h3>Email Sent!</h3> <br/> Recepients: " ) );
            for ( Long uid : result ) {
                outer.add ( new FbName ( uid ) );
            }

            Xfbml.parse ( outer );
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

        initWidget ( outer ) ;
    }
    
    private void renderUI () {
        FbName fbName = new FbName ( apiClient.getLoggedInUser ());
        fbName.setUseyou ( false );
        
        final HTML text  = new HTML ( "This will send an email notification to  " + fbName.toString () + "(you).");
        text.getElement ().setId ( "text" );
        notificationText.setWidth ( "500px" );
        notificationText.setHeight ( "100px" );
        notificationText.setFocus ( true );
        outer.setSpacing ( 10 );
        
        final Button submit = new Button ( "Send" );
        submit.addClickHandler ( new NotificationSendHandler () );
        
        outer.add ( text );
        outer.add ( notificationText );
        outer.add ( submit );
        
        Xfbml.parse ( text );
        
    }
    /*
     * Send notiication.
     */
    private void sendToServer () {

        List<Long> toIds = new ArrayList<Long> ();
        toIds.add ( apiClient.getLoggedInUser () );
        // toIds.add ( new Long ( FacebookConnect.getLoggedInUser () ) );
        
        apiClient.notificationsSendEmail ( toIds, 
                                      "Notification Send Email",
                                      notificationText.getValue (),
                                      null,
                                      new NotificationSent () );    
    }

}

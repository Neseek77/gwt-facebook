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
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.facebook.FacebookConnect;
import com.gwittit.client.facebook.entities.ActionLink;

/**
 * Demonstrates the method <code>stream.publish<code>
 * 
 * @author olamar72
 *
 */
public class Stream_publish extends Showcase {

    final static String defaultUserMessage = "Testing gwt-facebook, a library for developing facebook apps with GWT";
    /*
     * Render callback
     */
    private class MyCallback implements AsyncCallback<JavaScriptObject> {
        private Panel addTo;
        public MyCallback ( Panel addTo ) {
            this.addTo = addTo;
        }
        public void onFailure(Throwable caught) {
            handleFailure ( caught );
        }
        public void onSuccess(JavaScriptObject result) {
            addTo.add ( new HTML ( "Published stream , postId is: " + result.toString () ) );
        }
    }
    
    public class PublishStreamSimpleHandler implements ClickHandler {
        public void onClick(ClickEvent event) {
            FacebookConnect.streamPublish ();
        }
    }

    /**
     * When user clicks publish stream button
     */
    private class PublishStreamClickHandler implements ClickHandler {

        private Panel addTo;
        private boolean showDialog;
        
        public PublishStreamClickHandler ( Panel addTo, boolean showDialog ) {
            this.addTo = addTo;
            this.showDialog = showDialog;
        }
        public void onClick(ClickEvent event) {
            
            List<ActionLink> links = new ArrayList<ActionLink> ();
            links.add ( ActionLink.newInstance ( "Go to Gwittit", "http://gwittit.appspot.com" ) ); 
            links.add ( ActionLink.newInstance ( "Go to GWT", "http://code.google.com/webtoolkit/" ) );
            
            apiClient.streamPublish (defaultUserMessage,
                                        null, 
                                        links, 
                                        null, 
                                        "Say hi to the developer?", 
                                        false, 
                                        null, 
                                        showDialog,
                                        new MyCallback ( addTo ) );
        }
    };
    
    
    /**
     * New Showcase
     */
    public Stream_publish () {
      
        final VerticalPanel vPanel = new VerticalPanel();
        vPanel.setStyleName ( "gwittit-Stream_publish" );
        
        VerticalPanel innerPanel = new VerticalPanel ();
        innerPanel.setStyleName ( "innerPanel" );
        innerPanel.setSpacing ( 10 );
        
        final Button publishButton = new Button ( "PublishStream #1");
        final Label helpText = new Label ( "This will display a dialog where you can publish stream to your wall" );
        
        final Button publishButton2 = new Button ( "PublishStream #2" );
        final HTML helpText2 = new HTML ( "This will publish a stream with the text <b>" + defaultUserMessage + "</b> ( publish_stream must be granted )" );

        final Button publishButton3 = new Button ( "PublisStream #3" );
        final HTML helpText3 = new HTML ( "This will prompt user to update his or her status" );
        
        innerPanel.add ( publishButton );
        innerPanel.add ( helpText );
        vPanel.add ( innerPanel );
        publishButton.addClickHandler  ( new PublishStreamClickHandler ( innerPanel, true ) );

        innerPanel = new VerticalPanel ();
        innerPanel.setSpacing ( 10);
        innerPanel.setStyleName ( "innerPanel" );

        innerPanel.add ( publishButton2 );
        innerPanel.add ( helpText2 );
        vPanel.add ( innerPanel );
        publishButton2.addClickHandler ( new PublishStreamClickHandler ( innerPanel, false ) );

        
        innerPanel = new VerticalPanel ();
        innerPanel.setStyleName ( "innerPanel" );

        innerPanel.add ( publishButton3 );
        innerPanel.add ( helpText3 );
        innerPanel.setSpacing ( 10 );
        vPanel.add ( innerPanel );
        publishButton3.addClickHandler ( new PublishStreamSimpleHandler () );
        initWidget ( vPanel );
    }
}

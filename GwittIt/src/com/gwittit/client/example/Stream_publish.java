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
            addTo.add ( new HTML ( "Result: " + result.toString () ) );
        }
    }
    
    /*
     * User clicks publish
     */
    private class FullStreamHandler implements ClickHandler {

        private Panel addTo;
        
        public FullStreamHandler ( Panel addTo ) {
            this.addTo = addTo;
        }
        public void onClick(ClickEvent event) {
            List<ActionLink> links = new ArrayList<ActionLink> ();
            links.add ( ActionLink.newInstance ( "Go to Gwittit", "http://gwittit.appspot.com" ) ); 
            links.add ( ActionLink.newInstance ( "Go to GWT", "http://code.google.com/webtoolkit/" ) );
            
            FacebookConnect.stream_publish ("Testing gwt-facebook, a library for developing facebook apps with GWT",
                                        null, 
                                        links, 
                                        null, 
                                        "Say hi to the developer?", 
                                        false, 
                                        null, 
                                        new MyCallback ( addTo ) );
        }
        
    };
    
    /**
     * New Showcase
     */
    public Stream_publish () {
        super ( "stream.publish" );
    }
    
    
    /**
     * Create ui
     */
    public Widget createWidget() {
        
        final VerticalPanel vPanel = new VerticalPanel();
        vPanel.setStyleName ( "gwittit-Stream_publish" );
        
        final HorizontalPanel hPanel = new HorizontalPanel ();
        hPanel.setSpacing ( 10 );
        final Button publishButton = new Button ( "Display Publish Stream");
        final Label helpText = new Label ( "This will display a dialogue where you can publish stream to your wall" );
        
        hPanel.add ( publishButton );
        hPanel.add ( helpText );
        
        vPanel.add ( hPanel );
        publishButton.addClickHandler ( new FullStreamHandler( vPanel ) );
        
        return vPanel;
    }
}

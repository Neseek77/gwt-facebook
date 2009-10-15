package com.gwittit.client.example;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.facebook.entities.Event;

/**
 * Showcase for method <code>events.get</code>
 */
public class Events_get extends Showcase {

    public Events_get (){
        super ( "events.get" );
    }
    
    public Widget createWidget () {
        final VerticalPanel outer = new VerticalPanel ();
        outer.addStyleName ( "gwittit-Showcase-Events_get");
        addLoader ( outer );
        
        apiClient.events_get ( null, new AsyncCallback<List<Event>> () {
            public void onFailure(Throwable caught) {
                handleFailure ( caught );
            }
            public void onSuccess(List<Event> events) {
                removeLoader ( outer );
                for ( Event e : events ) {
                    
                    HorizontalPanel tmp = new HorizontalPanel ();
                    tmp.addStyleName ( "event" );
                    if ( e.getPic_small () != null ) {
                        Image image = new Image ( e.getPic_small () );
                        tmp.add ( image );
                    }
                    tmp.add ( new HTML ( "<h4>" + e.getName () +"</h4>" ) ); 
                    outer.add ( tmp );
         
                }
            }
        });
        return outer;
    }
}

package com.gwittit.client.example;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwittit.client.facebook.FacebookApi.RsvpStatus;
import com.gwittit.client.facebook.entities.EventInfo;
import com.gwittit.client.facebook.xfbml.Xfbml;

/**
 * Showcase for method <code>events.get</code>
 */
public class Events_get extends Showcase {
    /**
     * Let user filter events
     */
    private class FilterHandler implements ChangeHandler {
        
        public void onChange(ChangeEvent event) {
            int idx = listBox.getSelectedIndex ();
            String value = listBox.getValue ( idx );
            
            if ( "All".equals ( value ) ) {
                displayEvents ( null );
            } else {
                displayEvents ( RsvpStatus.valueOf ( value   ) );
            }
        }
        
    }

    /**
     * Handle events get 
     */
    private class EventsGetCallback implements AsyncCallback<List<EventInfo>> {
        public void onFailure(Throwable caught) {
           handleFailure ( caught );
        }

        public void onSuccess(List<EventInfo> result) {
            GWT.log ( "Events get #" + result.size (), null );
           // removeLoader ( outer );
            handleResponse ( result );
        }
    }
    
    // ----------- Private ------------------
    private VerticalPanel outer = new VerticalPanel ();
    private VerticalPanel eventsPanel  = new VerticalPanel ();
    private ListBox listBox = new ListBox ( false );
    
    
    public Events_get (){
        
        outer = new VerticalPanel ();
        eventsPanel  = new VerticalPanel ();
        listBox = new ListBox ( false );
     
        createWidget ();
        
        initWidget ( outer );
    }
    

    private HorizontalPanel createEventFilter () {
        HorizontalPanel filter = new HorizontalPanel ();
              
        listBox.addItem ( "All");
        
        for ( RsvpStatus rs : RsvpStatus.values() ) {
            listBox.addItem ( rs.toString () );
        }
        filter.setSpacing ( 10 );
        filter.add ( new HTML ( "Filter By: " ) );
        filter.add ( listBox );
   
        return filter;
    }

    /**
     * Create widget
     */
    public void createWidget () {
        
        outer.getElement ().setId ( "Events_get");
        outer.addStyleName ( "gwittit-Events_get");
        
        listBox.addChangeHandler ( new FilterHandler () );
        
        outer.add ( createEventFilter () );
        outer.add ( eventsPanel );
        
        displayEvents ( null );
        
    }
    
    /*
     * Handle response from call
     */
    private void handleResponse ( List<EventInfo> events ) {
        removeLoader ( eventsPanel );
        for ( EventInfo eventInfo : events ) {
            eventsPanel.add ( new HTML ( "<h3> " + eventInfo.getName () + "</h3>" ) );
        }
        
        Xfbml.parse ( eventsPanel );
    }
    
    /*
     * Render events based on rsvpstatus 
     */
    private void displayEvents ( RsvpStatus status ) {

        addLoader ( eventsPanel );
  
        GWT.log ( "display events", null);
        // Create a filter used in the query
        EventInfo eventFilter = EventInfo.createEventInfo ( null, null, null, null, status  );
        
        apiClient.eventsGet ( eventFilter,new EventsGetCallback () );
    }
            
}

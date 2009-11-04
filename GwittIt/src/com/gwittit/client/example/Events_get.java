package com.gwittit.client.example;

import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwittit.client.facebook.FacebookApi.RsvpStatus;
import com.gwittit.client.facebook.entities.EventInfo;
import com.gwittit.client.facebook.entities.EventMembers;
import com.gwittit.client.facebook.xfbml.Xfbml;

/**
 * Showcase for method <code>events.get</code>
 */
public class Events_get extends Showcase {
    
    private Image eventIcon = new Image ( "/cal_icon.gif" );
    
    /*
     * Get members
     */
    private class GetMembersClickHandler implements ClickHandler {

        Long eid;
        Panel addToPanel;
        
        public GetMembersClickHandler ( Long eid, Panel addToPanel ) {
            this.eid = eid;
            this.addToPanel = addToPanel;
        }
        
        public void onClick(ClickEvent event) {
            Events_getMembers members = new Events_getMembers ( eid );
            addToPanel.clear ();
            addToPanel.add ( members );
        }
        
    }

    
    
    /**
     * Let user filter events
     */
    private class FilterHandler implements ChangeHandler {
        
        public void onChange(ChangeEvent event) {
            int idx = listBox.getSelectedIndex ();
            String value = listBox.getValue ( idx );
            
            if ( "All".equals ( value ) ) {
                doEventsGet ( null );
            } else {
                doEventsGet ( RsvpStatus.valueOf ( value   ) );
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
    private VerticalPanel responsePanel  = new VerticalPanel ();
    private ListBox listBox = new ListBox ( false );
    
    
    /**
     * Create new showcase
     */
    public Events_get (){
     
        outer.getElement ().setId ( "Events_get");
        outer.addStyleName ( "gwittit-Events_get");
  
        outer.add ( createEventFilter () );
        outer.add ( responsePanel );
        
        doEventsGet ( null );

        listBox.addChangeHandler ( new FilterHandler () );
        
        initWidget ( outer );
    }
    
    /*
     * Create ui for dropdown filter
     */
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
    
    
    /*
     * Handle response from call
     */
    private void handleResponse ( List<EventInfo> events ) {
        
        removeLoader ( responsePanel );
        
        HTML header =  new HTML ( "<h3><img src=/cal_icon.gif> &nbsp; You have " + events.size () + " event invitations </h3><p/>" );
        responsePanel.add ( header );
        
        for ( EventInfo eventInfo : events ) {
            responsePanel.add ( createEventInfoUi ( eventInfo ) ) ;
        }
        Xfbml.parse ( responsePanel );
    }
    
    /*
     * Create somekind of ui.
     */
    private VerticalPanel createEventInfoUi ( EventInfo eventInfo ) {

        VerticalPanel p = new VerticalPanel ();
        p.addStyleName ( "eventInfo" );
        
        String html = "<h4>" + eventInfo.getName () + "</h4>";
        html += "When: " + eventInfo.getStartTime () + ", Where: " + eventInfo.getLocation () + "<br/>";
        responsePanel.add ( new HTML (  html ) );
        
        SimplePanel mPanel = new SimplePanel ();

        Anchor mLink = new Anchor ( "See who's attending");
        mLink.addClickHandler ( new GetMembersClickHandler ( eventInfo.getEid (), mPanel ) );
        
        p.add ( mLink );
        p.add ( mPanel );
        
        return p;
    }
    
    /*
     * Render events based on rsvpstatus 
     */
    private void doEventsGet ( RsvpStatus status ) {

        responsePanel.clear ();
        
        addLoader ( responsePanel );
  
        GWT.log ( "display events", null);
        // Create a filter used in the query
        EventInfo eventFilter = EventInfo.createEventInfo ( null, null, null, null, status  );
        
        apiClient.eventsGet ( eventFilter,new EventsGetCallback () );
    }
            
}

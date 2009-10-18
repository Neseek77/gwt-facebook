package com.gwittit.client.example;

import java.util.Date;
import java.util.List;

import com.google.gwt.benchmarks.client.Category;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.facebook.Callback;
import com.gwittit.client.facebook.FacebookConnect;
import com.gwittit.client.facebook.Json;
import com.gwittit.client.facebook.FacebookApi.Permission;
import com.gwittit.client.facebook.FacebookApi.RsvpStatus;
import com.gwittit.client.facebook.entities.EventInfo;
import com.gwittit.client.facebook.xfbml.FbName;
import com.gwittit.client.facebook.xfbml.Xfbml;

/**
 * Showcase for method <code>events.get</code>
 */
public class Events_get extends Showcase {

    public Events_get (){
        super ( "events.get" );
    }
    
    /**
     * Create widget
     */
    public Widget createWidget () {
        final VerticalPanel outer = new VerticalPanel ();
        final VerticalPanel holder  = new VerticalPanel ();
        final HorizontalPanel filter = new HorizontalPanel ();
        
        filter.setSpacing ( 10 );
        
        outer.getElement ().setId ( "Events_get");
        outer.addStyleName ( "gwittit-Events_get");
        
        final ListBox listBox = new ListBox (false);
        listBox.addItem ( "All");

        for ( RsvpStatus rs : RsvpStatus.values() ) {
            listBox.addItem ( rs.toString () );
        }
        
        // Add dropdown filter
        listBox.addChangeHandler ( new ChangeHandler() {
            public void onChange(ChangeEvent event) {
                int idx = listBox.getSelectedIndex ();
                String value = listBox.getValue ( idx );
                
                if ( "All".equals ( value ) ) {
                    renderEvents ( holder, null );
                } else {
                    renderEvents ( holder, RsvpStatus.valueOf ( value   ) );
                }
            }
            
        });
            
        filter.add ( new HTML ( "Filter By: " ) );
        filter.add ( listBox );
        
        outer.add ( filter );
        outer.add ( holder );
        
        renderEvents ( holder, null );
        return outer;
    }
    
    
    /**
     * Render events based on rsvpstatus 
     */
    public void renderEvents ( final VerticalPanel outer , RsvpStatus status ) {
        
        outer.clear ();        
        addLoader ( outer );
  
        // Create a filter used in the query
        EventInfo eventFilter = EventInfo.createEventInfo ( null, null, null, null, status  );
      
        // Call facebook
        apiClient.events_get ( eventFilter, new AsyncCallback<List<EventInfo>> () {
            public void onFailure(Throwable caught) {
                handleFailure ( caught );
            }
            public void onSuccess(List<EventInfo> events) {
                removeLoader ( outer );
            
                for ( EventInfo e : events ) {
                
                    VerticalPanel eventItemHolder = new VerticalPanel ();
                    eventItemHolder.addStyleName ( "eventItemHolder" );
                    HorizontalPanel eventHeader = new HorizontalPanel ();
                    
                    if ( e.getPic_small () != null ) {
                        Image image = new Image ( e.getPic_small () );
                        eventHeader.add ( image );
                    }
                    Date d = new Date ( e.getStartTime () );
                    eventHeader.add ( new HTML ( "<h2> Name: " + e.getName () +" at " + d + "</h2>" ) ); 
                    eventItemHolder.add ( eventHeader );
                    
                    eventItemHolder.add ( new HTML ( "From " + new FbName ( e.getCreator () ) ) );
                    addRsvp ( eventItemHolder, e );
                    addCancel (eventItemHolder, e );
                    addEditEvent ( eventItemHolder, e );
                    
                    outer.add ( eventItemHolder );
                }
                
                Xfbml.parse ( outer );
            }
        });
            
    }
    
    /**
     * Let user cancel this event.
     */
    public void addCancel ( final VerticalPanel outer, final EventInfo e ) {
        Anchor cancelLink = new Anchor ( "Cancel Event" );
        outer.add ( cancelLink );
        
        cancelLink.addClickHandler ( new ClickHandler () {

            public void onClick(ClickEvent event) {
                apiClient.events_cancel ( e.getEid (), "Cancelled by Gwittit", new AsyncCallback<Boolean> () {

                    public void onFailure(Throwable caught) {
                        Events_get.this.handleFailure ( caught );
                    }

                    public void onSuccess(Boolean ok) {
                        outer.add ( new HTML ( "Cancelled event " ) );
                    }
                    
                });
            }
            
        });
    }
    
    /*
     *  Test events.edit ( currently not working )
     */
    public void addEditEvent ( final VerticalPanel outer, final EventInfo e) {
        
        Anchor editLink = new Anchor ( "Edit Event" );
        outer.add ( editLink );
        Json j = Json.newInstance ();
        j.put ( "name", e.getName () + " Updated " );

         
        final EventInfo updateEvent = EventInfo.fromJson ( j.toString () );
        editLink.addClickHandler ( new ClickHandler() {
            public void onClick(ClickEvent event) {
                addLoader ( outer );
                apiClient.events_edit ( e.getEid (), updateEvent, new AsyncCallback<Boolean> (){
                    public void onFailure(Throwable caught) {
                        removeLoader ( outer );
                        Events_get.this.handleFailure ( caught );
                    }
                    public void onSuccess(Boolean result) {
                        removeLoader ( outer );
                        Window.alert ( "alert " + result );
                    }
                    
                });
            }
        });
        
    }
    
    /**
     * Let user respond to this event.
     */
    public void addRsvp ( VerticalPanel outer, final EventInfo e  ) {

        final Button go = new Button ( "Go");
        final HorizontalPanel h = new HorizontalPanel ();
        final ListBox listBox = new ListBox (false);
        // Where we add loader
        final SimplePanel ajaxLoaderPanel = new SimplePanel ();
        
        h.setSpacing ( 5 );
        
        for ( RsvpStatus status : RsvpStatus.values() ) {
            listBox.addItem ( status.toString () );
        }
        
        h.add ( new HTML ( "Respond to this event: "  ) );
        h.add ( listBox );
        h.add ( go );
        h.add ( ajaxLoaderPanel );
        outer.add ( h );
        
        
        go.addClickHandler ( new ClickHandler() {
            public void onClick(ClickEvent event) {
                
                final String status = listBox.getValue (  listBox.getSelectedIndex () );
                final RsvpStatus rsvpStatus = RsvpStatus.valueOf ( status );
                
                ajaxLoaderPanel.setWidget ( new HTML ( "Checking permission..." ) );
                apiClient.users_hasAppPermission ( Permission.rsvp_event, new Callback<Boolean>() {

                    @Override
                    public void onSuccess(Boolean hasPermission ) {
                        if ( hasPermission ) {
                            saveEventStatus ( e.getEid (), rsvpStatus , ajaxLoaderPanel );
                        } else {
                            askRsvpPermission ( e.getEid (), rsvpStatus, ajaxLoaderPanel );
                        }
                    }
                    
                });
            }
            
        });
    }
 
    /**
     * Ask user for rsvp permission
     */
    public void askRsvpPermission ( final Long eventId, final RsvpStatus status, final SimplePanel infoPnl ) {
        FacebookConnect.showPermissionDialog ( Permission.rsvp_event, new AsyncCallback<Boolean> () {
            public void onFailure(Throwable caught) {
            }

            public void onSuccess(Boolean granted) {
                if ( granted ) {
                    saveEventStatus ( eventId, status, infoPnl );
                }
                else {
                    infoPnl.setWidget ( new HTML ( "Skipped" ) );
                }
            }
        });
    }
 
    /**
     * Save event respond
     */
    public void saveEventStatus ( final Long eventId , final RsvpStatus status , final SimplePanel ajaxLoaderPanel ) {
        
         // First ask for permission.
             ajaxLoaderPanel.setWidget ( Events_get.this.getLoader () );
                    apiClient.events_rsvp ( eventId, status, new AsyncCallback<Boolean> () {
                        public void onFailure(Throwable caught) {
                           
                            Events_get.this.handleFailure ( caught );
                        }
                        public void onSuccess(Boolean result) {
                            ajaxLoaderPanel.clear ();
                            ajaxLoaderPanel.setWidget ( new HTML ( " Responded " + status.toString () ) );
                        } 
    
                    });             
    } 
            
}

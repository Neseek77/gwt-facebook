package com.gwittit.client.example;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.example.EventSelector.EventSelectorHandler;
import com.gwittit.client.facebook.FacebookApi.EventsGetMembersParams;
import com.gwittit.client.facebook.entities.EventMembers;

public class Events_getMembers extends Showcase {

    
    public Events_getMembers () {
        super ( "events.getMembers" );
    }
    
    @Override
    public Widget createWidget () {
        final VerticalPanel outer = new VerticalPanel ();
        
        final VerticalPanel inner = new VerticalPanel();
        
        
        final EventSelector eventSelector = new EventSelector();
        
        eventSelector.addSelectHandler ( new EventSelectorHandler () {

            public void onSelect(Long eid) {

                inner.clear ();
                
                Map<Enum<EventsGetMembersParams>,String> params = new HashMap<Enum<EventsGetMembersParams>,String> ();
                params.put ( EventsGetMembersParams.eid, ""+eid );
                
                addLoader ( inner );
                // Get event members for selected event
                apiClient.events_getMembers ( params, new AsyncCallback<EventMembers> () {

                    public void onFailure(Throwable caught) {
                        removeLoader ( inner );
                        handleFailure ( caught );
                    }

                    public void onSuccess(EventMembers result) {
                        removeLoader ( inner );
                        inner.add ( new HTML ( "<h3>Attending: </h3>" ) );
                        inner.add ( new HTML ( "" + result.getAttending ().length () ) ); 
                        
                        inner.add ( new HTML ( "<h3>Unsure</h3>" ) );
                        inner.add ( new HTML ( "" + result.getUnsure ().length () ) );
                        // TODO Auto-generated method stub
                        
                        inner.add ( new HTML ( "<h3>Not Replied</h3>" ) );
                        inner.add ( new HTML ( "" + result.getNotReplied ().length () ) );
                    }
                    
                });
                
            }
            
        });
        
        outer.add ( eventSelector );
        outer.add ( inner );

        return outer;
        
        
    }
}

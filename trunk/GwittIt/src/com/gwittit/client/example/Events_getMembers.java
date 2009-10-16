package com.gwittit.client.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.example.EventSelector.EventSelectorHandler;
import com.gwittit.client.facebook.FacebookApi.EventsGetMembersParams;
import com.gwittit.client.facebook.entities.EventMembers;
import com.gwittit.client.facebook.ui.ProfilePicsPanel;

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
                        addMembers ( inner, "Attending", result.getAttending () );
                        addMembers ( inner, "Unsure", result.getUnsure () );
                        addMembers ( inner, "Not Replied", result.getNotReplied () );
                    }
                    
                });
                
            }
            
        });
        
        outer.add ( eventSelector );
        outer.add ( inner );
        return outer;
    }
    
    private void addMembers ( final VerticalPanel inner, final String header, final List<Long> uids ) {
        inner.add ( new HTML ( "<h3>" + header + "</h3>" ) );
        inner.add ( new HTML ( "" + uids.size () ) ); 
        
        ProfilePicsPanel ppp = new ProfilePicsPanel ( uids );
        inner.add ( ppp );
    }
}

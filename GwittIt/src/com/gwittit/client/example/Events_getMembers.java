package com.gwittit.client.example;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwittit.client.example.EventSelector.EventSelectorHandler;
import com.gwittit.client.facebook.entities.EventMembers;
import com.gwittit.client.facebook.ui.ProfilePicsPanel;

/**
 * Showcase for method <code>events.getMembers</code>
 * @author olamar72
 */
public class Events_getMembers extends Showcase {

    final VerticalPanel outer = new VerticalPanel ();
    final VerticalPanel inner = new VerticalPanel();

    /*
     * User selects event
     */
    private class EventSelectorImpl implements EventSelectorHandler {
        public void onSelect(Long eid) {
            addLoader ( inner );
            doGetMembers ( eid );
        }
    }

    /*
     * Display members
     */
    private class EventsGetMembersCallback implements AsyncCallback<EventMembers> {
        public void onFailure(Throwable caught) {
            handleFailure ( caught );
        }
        
        public void onSuccess(EventMembers result) {
            removeLoader ( inner );
            displayMembers ( inner, "Attending", result.getAttending () );
            displayMembers ( inner, "Unsure", result.getUnsure () );
            displayMembers ( inner, "Not Replied", result.getNotReplied () );
        }
    }
    
    /**
     * Create new showcase
     */
    public Events_getMembers () {
        this ( null );
    }
    
    public Events_getMembers ( Long eid ) {
        
        if ( eid == null ) {
            final EventSelector eventSelector = new EventSelector();
            eventSelector.addSelectHandler ( new EventSelectorImpl () );
            outer.add ( eventSelector );
        } else {
            doGetMembers ( eid );
        }
        
        outer.add ( inner );
        initWidget( outer);
        
    }
    
    private void displayMembers ( final VerticalPanel inner, final String header, final List<Long> uids ) {
        inner.add ( new HTML ( "<h5>" + header + " " + uids.size () + "</h5>" ) );
        
        ProfilePicsPanel ppp = new ProfilePicsPanel ( uids );
        inner.add ( ppp );
    }
    
    private void doGetMembers ( Long eid ) {
        addLoader ( inner );
        apiClient.eventsGetMembers ( eid, new EventsGetMembersCallback () );
    }
}

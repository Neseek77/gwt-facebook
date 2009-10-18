package com.gwittit.client.example;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.gwittit.client.facebook.ApiFactory;
import com.gwittit.client.facebook.FacebookApi;
import com.gwittit.client.facebook.entities.Event;


/**
 * Let user select an event
 */
public class EventSelector extends Composite  {
    
    public interface EventSelectorHandler {
        void onSelect ( Long eid );
    }
    
    private final HorizontalPanel outer = new HorizontalPanel();
    
    private FacebookApi apiClient = ApiFactory.getInstance();

    private Button selectButton = new Button ( " Go ");
    
    private EventSelectorHandler selectHandler;
    
    private Image loader = new Image ( "/loader.gif" );

    /**
     * New instance
     */
    public EventSelector () {
    
        outer.setSpacing ( 10 );
        
        outer.add ( new HTML ( "Select Event: " ) );
        outer.add ( loader );
        
        apiClient.events_get ( null, new AsyncCallback<List<Event>> (){

            public void onFailure(Throwable caught) {
                outer.add ( new HTML ( "Failed get events..." ) );
            }

            public void onSuccess(List<Event> result) {
                
                outer.remove (  loader  );
                final ListBox dropBox = new ListBox(false);
                
                for ( Event e : result ) {
                    GWT.log (  "adding " + e.getName (), null );
                    
                    dropBox.addItem ( e.getName (), e.getEidString() );
                }
                
                outer.add ( dropBox );
                
                outer.add ( selectButton );
                
                selectButton.addClickHandler ( new ClickHandler () {

                    public void onClick(ClickEvent event) {
                       selectHandler.onSelect ( new Long ( dropBox.getValue ( dropBox.getSelectedIndex () ) ) );
                    }
                    
                });
            }
            
        });
        
        initWidget ( outer );
        
    }

    public void addSelectHandler ( EventSelectorHandler handler ) {
        this.selectHandler = handler;
    }
}

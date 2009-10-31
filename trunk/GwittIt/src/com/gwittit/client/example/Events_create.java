package com.gwittit.client.example;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.facebook.FacebookConnect;
import com.gwittit.client.facebook.FacebookApi.Permission;
import com.gwittit.client.facebook.entities.EventInfo;
import com.gwittit.client.facebook.ui.EventEditor;

/**
 * Showcase for method <code>events.create</code>
 */
public class Events_create extends Showcase {
    
    public Events_create () {
        super ( "events.create" );
    }
    
    /**
     * Create showcase widget
     */
    public Widget createWidget () {
        final VerticalPanel outer = new VerticalPanel ();
        
        // Ask facebook if current user has granted gwittit create_event permission.
        apiClient.usersHasAppPermission ( Permission.create_event, new AsyncCallback<Boolean> () {

            public void onFailure(Throwable caught) {
                handleFailure ( caught );
            }

            public void onSuccess(Boolean canCreateEvent ) {
                
                // if user has granted permissions, show event editor
                if ( canCreateEvent ) {
                    showEventEditor ( outer );
                } else {

                    // Ask user for permsission, and hopfully...
                    FacebookConnect.showPermissionDialog ( Permission.create_event, new AsyncCallback<Boolean> () {
                        public void onFailure(Throwable caught) {
                            Events_create.this.handleFailure ( caught );
                        }
                        public void onSuccess(Boolean nowCanCreateEvent ) {
                    
                            // He granted us create_event permission
                            if ( nowCanCreateEvent ) {
                                showEventEditor ( outer );
                            } else {
                                outer.add (  new HTML ( "You cannot create an event unless you grant persmission <i>create_event</i>" ) );
                            }
                        }
                        
                    });
                }
            }
            
        });
        
        return outer;
    }
    
    private void showEventEditor ( VerticalPanel p ) {
        final EventEditor eventEditor = new EventEditor ();
        p.add ( eventEditor );
    }
}

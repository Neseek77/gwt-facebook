package com.gwittit.client.example;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwittit.client.facebook.FacebookApi.Permission;
import com.gwittit.client.facebook.ui.EventEditor;

/**
 * Showcase for method <code>events.create</code>
 */
public class Events_create extends Showcase {
    
    final VerticalPanel outer = new VerticalPanel ();
    
    /**
     * Create showcase
     */
    public Events_create () {
        initWidget( outer );
    }
    
    
    @Override
    public void permissionGranted () {
        final EventEditor eventEditor = new EventEditor ();
        outer.add ( eventEditor );
    }
    
    @Override
    public Permission getNeedPermission() {
        return Permission.create_event;
    }
}

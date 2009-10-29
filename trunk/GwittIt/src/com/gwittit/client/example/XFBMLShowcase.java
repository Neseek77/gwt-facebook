package com.gwittit.client.example;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.facebook.FacebookApi.Permission;
import com.gwittit.client.facebook.xfbml.FbPromptPermission;
import com.gwittit.client.facebook.xfbml.Xfbml;


/**
 * Render various xfbml tags
 */
public class XFBMLShowcase extends Showcase {

    public XFBMLShowcase () {
        super ( "Various Widgets" );
    }
  
    /**
     * Create widget
     */
    public Widget createWidget () {
        
        final VerticalPanel outer = new VerticalPanel ();
        outer.getElement ().setId ( "WidgetShowcase" );
        outer.add ( new HTML ( "<h3>FbPromptPermission</h3>" ) );
  
        FbPromptPermission promptPerm  = 
            new FbPromptPermission ( "Click to see permission dialog", 
                        Permission.create_event, 
                        Permission.create_note, 
                        Permission.publish_stream );
        outer.add ( promptPerm );
        Xfbml.parse ( outer );
        
        return outer;
    }
}

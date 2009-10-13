package com.gwittit.client;

import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

public class JsonDebugPopup extends DecoratedPopupPanel {
    
    
    VerticalPanel outer = new VerticalPanel ();
    
    public JsonDebugPopup ( String json ) {
        
        
        super.setAutoHideEnabled ( true );
       String qoute = json.replaceAll ( "\"", "\\\\\"" );
        
        outer.setWidth (  "500px" );
        outer.add ( new HTML ( qoute ) );
        
        outer.add ( new HTML ( "<h3>Raw </h3>" ) );
        outer.add ( new HTML ( json ) );
        setWidget ( outer );
    }

}

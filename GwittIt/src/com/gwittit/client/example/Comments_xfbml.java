package com.gwittit.client.example;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwittit.client.facebook.xfbml.FbComments;
import com.gwittit.client.facebook.xfbml.Xfbml;

public class Comments_xfbml extends Showcase {
    
    
    private VerticalPanel outer = new VerticalPanel ();
    private FbComments fbComments = new FbComments ( Comments_get.XID ) ;
    private HTML header = new HTML ( "<h3>A comment would be great! Thanks :-)</h3>" );
    public Comments_xfbml () {
        
        outer.getElement ().setId ( "xfbml_comments" );
        
        outer.add ( header );
        outer.add ( fbComments );
        
        Xfbml.parse ( outer );
        initWidget ( outer );
    }

}

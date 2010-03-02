package com.gwittit.client.example;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwittit.client.facebook.xfbml.FbComments;
import com.gwittit.client.facebook.xfbml.Xfbml;


/**
 * Display facebook comments on the site.
 * @author ola
 */
public class Comments_xfbml extends Showcase {
  
    // UI
    private VerticalPanel outer = new VerticalPanel ();
    
    // Facebook comments tag
    private FbComments fbComments = new FbComments ( Comments_get.XID ) ;
  
    // Usermessage
    private HTML header = new HTML ( "<h3>A comment would be great! Thanks :-)</h3>" );

    /**
     * Create example
     */
    public Comments_xfbml () {
        
        outer.getElement ().setId ( "xfbml_comments" );
        outer.add ( header );
        outer.add ( fbComments );
        
        Xfbml.parse ( outer );
   
        initWidget ( outer );
    }

}

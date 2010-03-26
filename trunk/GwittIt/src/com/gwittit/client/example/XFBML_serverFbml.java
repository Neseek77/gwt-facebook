package com.gwittit.client.example;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwittit.client.facebook.xfbml.FbServerFbml;
import com.gwittit.client.facebook.xfbml.Xfbml;

/**
 * Demonstrates serverFbml tag. Used to do invites etc.
 *
 */
public class XFBML_serverFbml extends Showcase {
 
    // Hold gui
    private VerticalPanel outer = new VerticalPanel ();
    
    // Display request form inside iframe(fb:serverFbml).
    private static String fbml = "<script type=\"text/fbml\">  <fb:fbml>  <fb:request-form action=\"/test\" method=\"POST\" invite=\"true\" type=\"XFBML\" content=\"This is a test invitation from XFBML test app <fb:req-choice url='see wiki page for fb:req-choice for details' label='Ignore the Connect test app!' />  \" >  <fb:multi-friend-selector showborder=\"false\" actiontext=\"Invite your friends to use Connect.\">  </fb:request-form>  </fb:fbml>  </script>";
    
    // Construct
    public XFBML_serverFbml () {

        outer.setWidth ( "800px" );
        outer.getElement ().setId ( "XFBML_serverFbml" );

        outer.add (  new FbServerFbml ( fbml ) );
        Xfbml.parse ( outer );
        
        initWidget ( outer );
    }
 
}

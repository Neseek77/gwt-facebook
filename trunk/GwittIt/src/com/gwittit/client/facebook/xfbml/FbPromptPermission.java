package com.gwittit.client.facebook.xfbml;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.facebook.FacebookApi.Permission;

/**
 * Renders fb:prompt-permission tag
 * 
 * CSS Configuration
 * <ul>
 *   <li>.gwittit-FbPromptPermission
 * </ul>
 *
 */

public class FbPromptPermission extends ComplexPanel {
    
    /**
     * Prompt user for permission, one or many permissions at a time.
     */
    public FbPromptPermission ( String text, Permission permission) {

        super.setElement ( DOM.createElement("fb:prompt-permission" ) ) ;
        getElement().setAttribute ( "perms", permission.toString () );

        DOM.setInnerText ( getElement() , text );
        //String permString = permString ( permissions );
        //Window.alert ( permString );
        /*
        getElement().setAttribute("perms", permString );
        addStyleName ("gwittit-FbPromptPermission" );
        */
    }
    
    
    /**
     * Create a commaseparated string
     */
    private String permString ( Permission[] permissions ) {
        StringBuilder permString = new StringBuilder();
        for ( int i = 0 ; i < permissions.length; i++ ) {
            permString.append ( permissions[i].toString () );
            if ( i < permissions.length-1 ) {
                permString.append("," );
            }
        }
        return permString.toString ();
    }
}

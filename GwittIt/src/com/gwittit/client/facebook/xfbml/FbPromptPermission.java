package com.gwittit.client.facebook.xfbml;

import com.google.gwt.user.client.DOM;
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

public class FbPromptPermission extends Widget {
    
    /**
     * Prompt user for permission, one or many permissions at a time.
     */
    public FbPromptPermission ( String text, Permission... permission) {
        
        if ( text == null ) {
            throw new IllegalArgumentException ( "text null" );
        }
        if ( permission == null ) {
            throw new IllegalArgumentException ( "permission null" );
        }
        
        super.setElement ( DOM.createElement("fb:prompt-permission" ) ) ;
        super.addStyleName ( "gwittit-FbPromptPermission" );
        getElement().setAttribute ( "perms", permString(permission) );
        DOM.setInnerText ( getElement() , text );
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

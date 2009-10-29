package com.gwittit.client.facebook.xfbml;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.facebook.FacebookApi.Permission;

/**
 * Renders the content of the tag as a link that, when clicked, initiates a
 * dialog requesting the specified extended permissions from the user. You can
 * prompt the user for a series of permissions. If the user has already granted
 * a permission, a dialog for that permission does not appear. If the user has
 * not already authorized the application before clicking the link, he or she is
 * prompted to authorize it before being prompted for the permission.
 * 
 * 
 * Valid permissions email, read_stream, publish_stream, offline_access,
 * status_update, photo_upload, create_event, rsvp_event, sms, video_upload,
 * create_note, share_item.
 * 
 * @see <a
 *      href="http://wiki.developers.facebook.com/index.php/Fb:prompt-permission">fb:prompt-permission</a>
 * 
 * CSS Configuration
 * <ul>
 *      <li>.gwittit-FbPromptPermission
 * </ul>
 * 
 */

public class FbPromptPermission extends Widget {

    /**
     * Prompt user for permission, one or many permissions at a time.
     */
    public FbPromptPermission(String text, Permission... permission) {

        if (text == null) {
            throw new IllegalArgumentException ( "text null" );
        }
        if (permission == null) {
            throw new IllegalArgumentException ( "permission null" );
        }

        super.setElement ( DOM.createElement ( "fb:prompt-permission" ) );
        super.addStyleName ( "gwittit-FbPromptPermission" );
        getElement ().setAttribute ( "perms", permString ( permission ) );
        DOM.setInnerText ( getElement (), text );
    }

    /**
     * Create a commaseparated string
     */
    private String permString(Permission[] permissions) {
        StringBuilder permString = new StringBuilder ();
        for (int i = 0; i < permissions.length; i++) {
            permString.append ( permissions[i].toString () );
            if (i < permissions.length - 1) {
                permString.append ( "," );
            }
        }
        return permString.toString ();
    }
}

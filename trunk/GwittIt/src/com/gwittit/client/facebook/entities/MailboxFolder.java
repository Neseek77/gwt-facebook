package com.gwittit.client.facebook.entities;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Facebook Mailbox. Mailbax can be , Output, Inbox, Updates 
 * 
 * @see <a
 *      href="http://wiki.developers.facebook.com/index.php/Mailbox_folder_%28FQL%29">
 *      Mailbox_Folder </a>
 * @author olamar72
 * 
 */
public class MailboxFolder extends JavaScriptObject {

    protected MailboxFolder() {
    }

    public final native String getFolderIdString() /*-{
        return this.folder_id + "";
    }-*/;

    public final Integer getFolderId() {
        return new Integer ( getFolderIdString () );
    }

    public final native String getName() /*-{
        return this.name;
    }-*/;

    public final native String getUnreadCountString() /*-{
        return this.unread_count + "";
    }-*/;

    public final Integer getUnreadCount() {
        return new Integer ( getUnreadCountString () );
    }

    public static native MailboxFolder fromJson(String jsonString) /*-{
        return eval('(' + jsonString + ')');
    }-*/;

}

package com.gwittit.client.example;

import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.facebook.FacebookApi.Permission;
import com.gwittit.client.facebook.entities.MailboxFolder;
import com.gwittit.client.facebook.entities.Message;
import com.gwittit.client.facebook.entities.MessageThread;
import com.gwittit.client.facebook.ui.PermissionDialog;
import com.gwittit.client.facebook.ui.PermissionDialog.PermissionHandler;
import com.gwittit.client.facebook.xfbml.FbName;
import com.gwittit.client.facebook.xfbml.Xfbml;

/**
 * Showcase for method <code>message.getThreadsInFolder</code>
 *
 * @author olamar72
 *
 */
public class Message_getThreadsInFolder extends Showcase {

    public Message_getThreadsInFolder() {
        super ( "message.getThreadsInFolder" );
    }
    
    /**
     * Create Widget
     */
    @Override
    public Widget createWidget () {
     
        final VerticalPanel outer = new VerticalPanel ();
        outer.addStyleName ( "gwittit-Message_getThreadsInFolder" );
    
        // List mail folders.
        final HorizontalPanel mailFolders = new HorizontalPanel ();
        mailFolders.addStyleName ( "mailFolders" );
        
        // Add mail folder content here
        final VerticalPanel folderContent = new VerticalPanel ();
        
        // Disable button until we know that the user granted us read_mailbox permission
        final PermissionDialog permissionDialog = new PermissionDialog ();
        
        permissionDialog.addPermissionHandler ( new PermissionHandler() {
            public void onPermissionChange(Boolean granted) {
                renderMailboxFolders( mailFolders, folderContent );
            }
            
        });

        outer.add ( permissionDialog );
        outer.add ( mailFolders );
        outer.add ( folderContent );
        // Check if user can read mailbox
        permissionDialog.checkPermission ( Permission.read_mailbox );
        return outer;
    }
    
    /**
     * Print a list with users mail folders
     */
    private void renderMailboxFolders ( final HorizontalPanel mailFolders, final VerticalPanel folderContent ) {

        mailFolders.add ( new HTML ( "Go to folder: " ) );
        
        // Get mailboxes, inbox output etc
        apiClient.messageGetMailBoxFolders ( new AsyncCallback<List<MailboxFolder>> () {
            public void onFailure(Throwable caught) {
                handleFailure ( caught );
            }
            public void onSuccess(List<MailboxFolder> result) {
                for ( final MailboxFolder mf : result ) {
                    Anchor a = new Anchor ( mf.getName () + (mf.getUnreadCount() > 0 ?  "(" + mf.getUnreadCount () + ")" : "" ) );
                    a.addClickHandler ( new ClickHandler() {
                        public void onClick(ClickEvent event) {
                            renderMessages ( folderContent, mf.getFolderId () );
                        }
                        
                    });
                    mailFolders.add ( a );
                }
            }
        });
    }

    /**
     * Test the method, display raw output
     */
    private void renderMessages ( final VerticalPanel addToContent, Integer folderId ) {
        
        addToContent.clear ();
        
        addLoader ( addToContent );
        // Render users messages filtered by folder id.
        apiClient.messageGetThreadsInFolder ( folderId, null, null, null, new AsyncCallback<List<MessageThread>> () {

            public void onFailure(Throwable caught) {
                handleFailure ( caught );
            }
            public void onSuccess(List<MessageThread> result) {
                removeLoader ( addToContent );
                for ( MessageThread mt : result ) {
                    
                    VerticalPanel mtPnl = new VerticalPanel ();
                    mtPnl.addStyleName ( "messageThread" );
                    String header = " From " + new FbName ( mt.getSnippetAuthorString () );
                    header += "<br>" + mt.getSnippet ();
                    header += "<br/>ThreadId: " + mt.getThreadId ();
                    header += "<br/>Messages in thread: " + mt.getMessageCountString ();
                    header += "<br/>Unread: " + mt.getUnread ();
                    HTML html = new HTML ( header );
                    mtPnl.add ( html );
                   
                    if ( mt.getUnread () > 0 ) {
                        JsArray<Message> messageArray = mt.getMessages ();
                        for ( int i = 0; i < messageArray.length (); i ++ ) {
                            Message m = messageArray.get ( i );
                            VerticalPanel messageThread = new VerticalPanel ();
                            messageThread.addStyleName ( "messages" );
                            messageThread.add ( new HTML ( "From  " + new FbName ( m.getAuthorId ()  ) ) );
                            messageThread.add ( new HTML ( m.getBody () ) );
                            mtPnl.add ( messageThread );
                        }
                    }
                    
                    addToContent.add ( mtPnl );
                }
                
                Xfbml.parse ( addToContent );
            }
            
        });
    }
}

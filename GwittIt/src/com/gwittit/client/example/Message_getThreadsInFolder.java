package com.gwittit.client.example;

import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
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
        outer.getElement ().setId ( "gwittit-Message_getThreadsInFolder" );
    
        final VerticalPanel mailboxPnl = new VerticalPanel ();
        
        final Button testMethodBtn = new Button ( "Get Messages" );
        // Disable button until we know that the user granted us read_mailbox permission
        testMethodBtn.setEnabled ( false );
        final PermissionDialog permissionDialog = new PermissionDialog ();
        
        permissionDialog.addPermissionHandler ( new PermissionHandler() {
            public void onPermissionChange(Boolean granted) {
                testMethodBtn.setEnabled ( true );
                printMailboxFolders( mailboxPnl );
            }
            
        });

        outer.add ( permissionDialog );
        outer.add ( mailboxPnl );
        outer.add ( testMethodBtn );

        // Check if user can read mailbox
        permissionDialog.checkPermission ( Permission.read_mailbox );
        testMethodBtn.addClickHandler ( new ClickHandler () {
            public void onClick(ClickEvent event) {
                testMethod( outer );
            }
        });
        return outer;
    }
    
    private void printMailboxFolders ( final VerticalPanel addToContent ) {
        
        // Get mailboxes
        apiClient.message_getMailBoxFolders ( new AsyncCallback<List<MailboxFolder>> () {

            public void onFailure(Throwable caught) {
                // TODO Auto-generated method stub
                
            }

            public void onSuccess(List<MailboxFolder> result) {
                for ( MailboxFolder mf : result ) {
                    
                    addToContent.add ( new HTML ( "Mailbox: " + mf.getName () + ", id: " 
                                                              + mf.getFolderId () + ", Unread: " + mf.getUnreadCount () ) );
                }
            }
            
        });
        
    }
    /**
     * Test the method, display raw output
     */
    private void testMethod ( final VerticalPanel addToContent ) {
        
        addLoader ( addToContent );
        apiClient.message_getThreadsInFolder ( 0, null, null, null, new AsyncCallback<List<MessageThread>> () {

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

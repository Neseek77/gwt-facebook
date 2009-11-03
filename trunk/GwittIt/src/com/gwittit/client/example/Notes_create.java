package com.gwittit.client.example;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.facebook.FacebookApi.Permission;
import com.gwittit.client.facebook.entities.Note;
import com.gwittit.client.facebook.ui.PermissionDialog;
import com.gwittit.client.facebook.ui.PermissionDialog.PermissionHandler;

/**
 * Showcase for method <code>notes.create</code>
 * 
 * @author ola
 * 
 */
public class Notes_create extends Showcase {

    private VerticalPanel outer = new VerticalPanel ();
    final HorizontalPanel permissionHolder = new HorizontalPanel ();
    final Button saveButton = new Button ( "Save Note " );

    
    /**
     * Delete note callback
     */
    private class DeleteNoteCallback implements AsyncCallback<Boolean> {
        public void onFailure(Throwable caught) {
            handleFailure ( caught );
        }

        public void onSuccess(Boolean result) {
            outer.add ( new Label ( "Note deleted" ) );
        }

    }

    /**
     * Delete note
     */
    private class DeleteNoteClickHandler implements ClickHandler {

        private Long noteId;

        public DeleteNoteClickHandler(Long noteId) {
            this.noteId = noteId;
        }

        public void onClick(ClickEvent event) {
            deleteNote ( noteId );
        }

    }

    public Notes_create() {
        outer = new VerticalPanel ();

        // Need this here so we can enable when user granted permission
        saveButton.setEnabled ( false );

        outer.add ( new HTML ( "<h3>Note: this method is marked as beta in the API and may not work </h3> " ) );
        outer.add ( permissionHolder );
        outer.add ( createUserInputUI ( saveButton ) );

        // Get right permission to create a note, if we get the right
        // permission enable save button
        initWidget ( outer );
    }

    private void deleteNote(Long noteId) {
        apiClient.notesDelete ( noteId, new DeleteNoteCallback () );
    }

    public void permissionGranted () {
        saveButton.setEnabled ( true );
    }

    public Permission getNeedPermission () {
        return Permission.create_note;
    }
    
    /**
     * Create user input UI
     * 
     * @return user input ui
     */
    private Widget createUserInputUI(Button saveButton) {

        final VerticalPanel p = new VerticalPanel ();
        final TextBox title = new TextBox ();
        final TextBox content = new TextBox ();

        p.setSpacing ( 10 );

        p.add ( createInput ( "Title", title ) );
        p.add ( createInput ( "Content", content ) );
        p.add ( saveButton );

        // User clicks save, store it to facebook
        saveButton.addClickHandler ( new ClickHandler () {

            public void onClick(ClickEvent event) {
                Note note = Note.createNote ( title.getValue (), content.getValue () );
                apiClient.notesCreate ( note, new AsyncCallback<Long> () {

                    public void onFailure(Throwable caught) {
                        Notes_create.this.handleFailure ( caught );
                    }

                    public void onSuccess(Long noteId) {
                        p.add ( new HTML ( "Added note with id " + noteId ) );

                        Anchor deleteNoteLink = new Anchor ( "Delete" );
                        deleteNoteLink.addClickHandler ( new DeleteNoteClickHandler ( noteId ) );
                        p.add ( deleteNoteLink );

                    }
                } );
            }

        } );
        return p;

    }

    private HorizontalPanel createInput(String lbl, Widget w) {

        HorizontalPanel h = new HorizontalPanel ();
        HTML lblHtml = new HTML ( lbl );
        lblHtml.setWidth ( "150px" );

        h.add ( lblHtml );
        h.add ( w );

        return h;

    }
}

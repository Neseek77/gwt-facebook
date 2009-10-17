package com.gwittit.client.example;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.example.FriendSelector.FriendSelectionHandler;
import com.gwittit.client.facebook.entities.Note;

public class Notes_get extends Showcase {

    public Notes_get () {
        super ( "notes.get" );
    }
    
    public Widget createWidget () {
        final VerticalPanel outer = new VerticalPanel ();
        final VerticalPanel notesHolder = new VerticalPanel ();
        final FriendSelector friendSelector = new FriendSelector();
   
        outer.add ( friendSelector );
        outer.add ( notesHolder );
        
        
        // Let user select a friend and show notes
        friendSelector.addFriendSelectionHandler ( new FriendSelectionHandler() {
            public void onSelected(Long uid) {
                notesHolder.clear ();
                addLoader ( notesHolder  );
                apiClient.notes_get ( uid, new AsyncCallback<List<Note>> () {
                    public void onFailure(Throwable caught) {
                       Notes_get.this.handleFailure ( caught ); 
                    }
                    public void onSuccess(List<Note> result) {
                        removeLoader ( notesHolder );
                        
                        if ( result.size () == 0 ) {
                            notesHolder.add ( new HTML ( "User has not created any notes ") ) ;
                        }
                        for ( Note n : result ) {
                            notesHolder.add ( new HTML ( "Note Title : " + n.getTitle () ) );
                        }
                        
                    }
                    
                });
            }
            
        });
        
        return outer;
    }
}

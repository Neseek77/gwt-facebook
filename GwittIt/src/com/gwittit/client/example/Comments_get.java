package com.gwittit.client.example;

import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwittit.client.facebook.entities.Comment;
import com.gwittit.client.facebook.xfbml.FbName;
import com.gwittit.client.facebook.xfbml.FbProfilePic;
import com.gwittit.client.facebook.xfbml.Xfbml;
import com.gwittit.client.facebook.xfbml.FbProfilePic.Size;

/**
 * Showcase for method call <code>comments.get</code>
 * 
 * @author olamar72
 */
public class Comments_get extends Showcase {

    public static final String XID = "comments_test";

   
    /*
     * Display comments
     */
    private class CommentsGetCallback implements AsyncCallback<List<Comment>> {
        public void onFailure(Throwable caught) {
            handleFailure ( caught );
        }
        public void onSuccess(List<Comment> result) {
            removeLoader ( outer );
            handleCommentsResponse ( result );
        }
    }

    /*
     * Remove comments
     */
    private class CommentsRemoveCallback implements AsyncCallback<JavaScriptObject> {
        public void onFailure(Throwable caught) {
            handleFailure ( caught );
        }

        public void onSuccess(JavaScriptObject result) {
            // ReRender comments
            doCommentsGet ();
        }

    }
    
    /*
     * User clicks remove link
     */
    private class CommentsRemoveClickHandler implements ClickHandler {

        private String commentId;
        
        public CommentsRemoveClickHandler ( String commentId ) {
            this.commentId = commentId;
        }
       
        public void onClick(ClickEvent event) {

            apiClient.commentsRemove ( XID, commentId, new CommentsRemoveCallback () );
        }
        
    }

    // ---------- Private Fields
    private final VerticalPanel outer = new VerticalPanel ();

    /**
     * Create showcase
     */
    public Comments_get() {
        outer.getElement ().setId ( "comments.get" );
        doCommentsGet ();
        initWidget ( outer );
    }

    /**
     * Call comments get
     */
    private void doCommentsGet() {
        outer.clear ();
        addLoader ( outer );
        apiClient.commentsGet ( XID, new CommentsGetCallback () );
    }

    private void handleCommentsResponse(List<Comment> comments) {

        for (final Comment comment : comments) {
            
            VerticalPanel box = new VerticalPanel ();
            box.addStyleName ( "commentBox" );
            
            HorizontalPanel p = new HorizontalPanel ();
            p.setSpacing ( 5 );
            
            p.add ( new FbProfilePic ( comment.getFromId (), Size.square ) );
            p.add ( new HTML ( comment.getText () + " from " + new FbName ( comment.getFromId () ) ) );

            box.add ( p );
                
            Anchor removeLink = new Anchor ( "Remove");
            removeLink.addClickHandler ( new CommentsRemoveClickHandler ( comment.getId () ) );
 
            box.add ( removeLink );
            outer.add ( box );
        }

        Xfbml.parse ( outer );

    }
}

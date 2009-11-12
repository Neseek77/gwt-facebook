package com.gwittit.client.example;


import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwittit.client.facebook.entities.Comment;
import com.gwittit.client.facebook.xfbml.FbProfilePic;
import com.gwittit.client.facebook.xfbml.FbPhoto.Size;

/**
 * Showcase for method call <code>comments.add</code>
 * 
 * @author olamar72
 */
public class Comments_add extends Showcase  {
	
    /*
     * Handle add comment
     */
    private class AddCommentCallback implements AsyncCallback<JavaScriptObject> {
        public void onFailure(Throwable caught) {
            handleFailure ( caught );
        }
        public void onSuccess(JavaScriptObject result) {
            removeLoader ( outer );
            text.setValue(null);
            responseWrapper.add( new HTML (" Thanks :-)" ) );
            displayComments ();
        }
    }

    /*
     * User adds comment
     */
    private class AddCommentClickHandler implements ClickHandler {
        public void onClick(ClickEvent event) {
            Comment comment = Comment.createComment ( "comments_test", text.getValue () );
            apiClient.commentsAdd ( comment,new AddCommentCallback () );
        }
    }

    
    final VerticalPanel outer = new VerticalPanel ();
    final VerticalPanel inputWrapper = new VerticalPanel ();
    final SimplePanel responseWrapper = new SimplePanel ();
    final VerticalPanel commentsListPanel = new VerticalPanel ();
    final TextArea text = new TextArea();
    final Button submitButton = new Button ( "Add Comment ");

    /**
     * New demo
     */
	public Comments_add() {
		
	    inputWrapper.setSpacing(10);
		outer.setSpacing(10);
		

		inputWrapper.add ( new HTML ( "Say it!" ) );
		inputWrapper.add ( createInputUi() );
		
		outer.add ( inputWrapper );
		outer.add ( submitButton );
		outer.add ( responseWrapper );
		outer.add ( commentsListPanel );
		displayComments ();
		submitButton.addClickHandler ( new AddCommentClickHandler () );
		
		initWidget( outer );
	}
	
	
	private Panel createInputUi () {
	    HorizontalPanel p = new HorizontalPanel ();
	    p.setSpacing ( 10 );
	    p.add ( new FbProfilePic ( apiClient.getLoggedInUser (), FbProfilePic.Size.thumb ) );
	    p.add ( text );
	    return p;
	}
	
	public void displayComments () {
	    
	    commentsListPanel.clear ();
	    Comments_get comments = new Comments_get ();
	    commentsListPanel.add ( comments );
	    
	}

}

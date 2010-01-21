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
import com.gwittit.client.facebook.xfbml.Xfbml;
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

		// ID's
	    inputWrapper.getElement ().setId ( "CommentsAdd-Input");

        // Styles
        submitButton.getElement ().getStyle ().setProperty ( "float", "right" );
        submitButton.getElement ().getStyle ().setProperty ( "marginTop", "10px" );

		inputWrapper.add ( new HTML ( "A comment would be great! Thanks." ) );
		inputWrapper.add ( createInputUi() );
		
		outer.add ( inputWrapper );
	
		// Thank you
		outer.add ( responseWrapper );
		
		// Show list of comments
		outer.add ( commentsListPanel );
		displayComments ();
		
		submitButton.addClickHandler ( new AddCommentClickHandler () );
		
		Xfbml.parse ( inputWrapper );
		initWidget( outer );
	}
	

	/**
	 * Create input text area and submit button.
	 */
	private Panel createInputUi () {
	    VerticalPanel vp = new VerticalPanel ();
	    HorizontalPanel p = new HorizontalPanel ();
	    
	    p.setSpacing ( 10 );
	    p.add ( new FbProfilePic ( apiClient.getLoggedInUser (), FbProfilePic.Size.square ) );
	    
	    text.setHeight ( "100px" );
	    text.setWidth ( "400px" );
	    
	    vp.add ( text );
	    vp.add ( submitButton );
	    
	    p.add ( vp );
	    
	    return p;
	}
	
	public void displayComments () {
	    
	    commentsListPanel.clear ();
	    Comments_get comments = new Comments_get ();
	    commentsListPanel.add ( comments );
	    
	}

}

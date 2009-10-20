package com.gwittit.client.example;


import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.facebook.entities.Comment;

/**
 * Showcase for method call <code>comments.add</code>
 * 
 * @author olamar72
 */
public class Comments_add extends Showcase  {
	
	public Comments_add() {
		super ( "comments.add" );
	}
	
	@Override
	public Widget createWidget () {
		
		final VerticalPanel outer = new VerticalPanel ();
		final VerticalPanel inputWrapper = new VerticalPanel ();
		final SimplePanel responseWrapper = new SimplePanel ();
		
		inputWrapper.setSpacing(10);
		outer.setSpacing(10);
		
		final TextArea text = new TextArea();
		final Button submitButton = new Button ( "Add Comment ");

		inputWrapper.add ( new HTML ( "Type any comment " ) );
		inputWrapper.add ( text );
		inputWrapper.add ( submitButton );
		
		outer.add ( inputWrapper );
		outer.add ( responseWrapper );
	
		submitButton.addClickHandler( new ClickHandler () {
			public void onClick(ClickEvent event) {
				
				addLoader ( outer );
				
				Comment comment = Comment.createComment ( "comments_test", text.getValue () );
				apiClient.comments_add (comment, new AsyncCallback<JavaScriptObject> () {

					public void onFailure(Throwable caught) {
						handleFailure ( caught );
					}

					public void onSuccess(JavaScriptObject result) {
						removeLoader ( outer );
						text.setValue(null);
						responseWrapper.add( new HTML (" Thanks :-)" ) );
					}
					
					
				});
			}
			
		});
		return outer;
	}

}

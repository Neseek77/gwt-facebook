package com.gwittit.client.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.facebook.entities.Comment;
import com.gwittit.client.facebook.xfbml.Xfbml;
import com.gwittit.client.ui.CommentUi;
import com.gwittit.client.ui.CommentUi.DeleteHandler;

/**
 * Showcase for method call <code>comments.get</code>
 * 
 * @author olamar72
 */
public class Comments_get extends Showcase {
	
	public static final String XID = "comments_test";
	
	static String method = "comments.get";
	
	public Comments_get () {
		super ( method );
	}

	/**
	 * Create widget
	 */
	@Override
	public Widget createWidget () {
	    // TODO clean up this example.
		final VerticalPanel outer = new VerticalPanel ();
		outer.getElement().setId(method);
		addLoader ( outer );

		// Get facebook data
		apiClient.comments_get(XID, new AsyncCallback<List<Comment>> () {

			public void onFailure(Throwable caught) {
				handleFailure ( caught );
			}

			public void onSuccess(List<Comment> result) {
				removeLoader ( outer );
				outer.add ( new HTML ( "Comments size " + result.size() ) );
				
				// OUh a "little" ALOT messy
				for ( Comment comment: result ) {

				    final CommentUi ui = new CommentUi ( comment );
					outer.add ( ui );
					
					ui.addDeleteHandler(new DeleteHandler () {
						public void onDelete(String commentId) {
							
							addLoader ( outer );
							apiClient.comments_remove( XID, commentId, new AsyncCallback<JavaScriptObject> () {
								public void onFailure(Throwable caught) {
									handleFailure ( caught );
								}
								public void onSuccess(JavaScriptObject result) {
									removeLoader ( outer );
									outer.remove ( ui );
								}
							});
						} 
					});
					Xfbml.parse(outer);
				}
			}
		});
		return outer;
	}
}

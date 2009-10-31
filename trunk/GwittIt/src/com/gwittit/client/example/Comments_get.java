package com.gwittit.client.example;

import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.facebook.entities.Comment;
import com.gwittit.client.facebook.xfbml.FbName;
import com.gwittit.client.facebook.xfbml.Xfbml;

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
		apiClient.commentsGet(XID, new AsyncCallback<List<Comment>> () {

			public void onFailure(Throwable caught) {
				handleFailure ( caught );
			}

			public void onSuccess(List<Comment> result) {
				removeLoader ( outer );
				outer.add ( new HTML ( "Comments size " + result.size() ) );
				
				// OUh a "little" ALOT messy
				for ( final Comment comment: result ) {
                    Anchor deleteLink = new Anchor ( "Delete" );

					outer.add ( new HTML ( comment.getText () + " from " + new FbName ( comment.getFromId () ) ) );
					outer.add ( deleteLink );

					deleteLink.addClickHandler(new ClickHandler () {
						public void onClick( ClickEvent event ) {
							addLoader ( outer );
							apiClient.commentsRemove( XID, comment.getId (), new AsyncCallback<JavaScriptObject> () {
								public void onFailure(Throwable caught) {
									handleFailure ( caught );
								}
								public void onSuccess(JavaScriptObject result) {
									removeLoader ( outer );
									outer.add ( new HTML ( "Comment Deleted " ) );
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

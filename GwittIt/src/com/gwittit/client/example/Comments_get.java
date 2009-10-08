package com.gwittit.client.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.facebook.FacebookApi.CommentsGetParams;
import com.gwittit.client.facebook.FacebookApi.CommentsRemoveParams;
import com.gwittit.client.facebook.entities.Comment;
import com.gwittit.client.facebook.ui.CommentUi;
import com.gwittit.client.facebook.ui.CommentUi.DeleteHandler;
import com.gwittit.client.facebook.xfbml.Xfbml;

/**
 * Showcase for method call <code>comments.get</code>
 * 
 * @author olamar72
 */
public class Comments_get extends Example {
	
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
		final VerticalPanel outer = new VerticalPanel ();
		outer.getElement().setId(method);
		addLoader ( outer );
		
		Map<Enum<CommentsGetParams>,String> params = new HashMap<Enum<CommentsGetParams>,String> ();
		params.put( CommentsGetParams.xid, XID );

		// Get facebook data
		apiClient.comments_get(params, new AsyncCallback<List<Comment>> () {

			public void onFailure(Throwable caught) {
				handleFailure ( caught );
			}

			public void onSuccess(List<Comment> result) {
				removeLoader ( outer );
				outer.add ( new HTML ( "Comments size " + result.size() ) );
				
				// OUh a little messy
				for ( Comment comment: result ) {
					final CommentUi ui = new CommentUi ( comment );
					outer.add ( ui );
					
					ui.addDeleteHandler(new DeleteHandler () {
						public void onDelete(String id) {
							Map<Enum<CommentsRemoveParams>,String> params = new HashMap<Enum<CommentsRemoveParams>,String> ();
                            params.put( CommentsRemoveParams.xid, XID);
							params.put ( CommentsRemoveParams.comment_id, id );
							
							addLoader ( outer );
							apiClient.comments_remove(params, new AsyncCallback<JSONValue> () {
								public void onFailure(Throwable caught) {
									handleFailure ( caught );
								}
								public void onSuccess(JSONValue result) {
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

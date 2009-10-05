package com.gwittit.client.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.facebook.entities.Comment;
import com.gwittit.client.facebook.ui.CommentUi;

/**
 * Method comments.get
 */
public class Comments_get extends Example {
	
	
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

		addLoader ( outer );
		
		Map<String,String> params = new HashMap<String,String> ();
		params.put("xid", "comments_test");

		// Get facebook data
		apiClient.comments_get(params, new AsyncCallback<List<Comment>> () {

			public void onFailure(Throwable caught) {
				handleFailure ( caught );
			}

			public void onSuccess(List<Comment> result) {
				removeLoader ( outer );
				outer.add ( new HTML ( "Comments size " + result.size() ) );
				
				for ( Comment comment: result ) {
					outer.add ( new CommentUi ( comment ) );
				}
			}
			
		});
		return outer;
	}

	
}

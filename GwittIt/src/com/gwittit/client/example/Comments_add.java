package com.gwittit.client.example;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.facebook.FacebookApi.CommentsAddParams;

/**
 * Showcase for method call <code>comments.add</code>
 * 
 * @author olamar72
 */
public class Comments_add extends Example  {
	
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
				Map<Enum<CommentsAddParams>,String> params = new HashMap<Enum<CommentsAddParams>,String> ();
				params.put( CommentsAddParams.xid, "comments_test");
				params.put( CommentsAddParams.text, text.getValue());
								
				apiClient.comments_add(params, new AsyncCallback<JSONValue> () {

					public void onFailure(Throwable caught) {
						handleFailure ( caught );
					}

					public void onSuccess(JSONValue result) {
						removeLoader ( outer );
						text.setValue(null);
						responseWrapper.add( new HTML (""+ result ) );
					}
					
					
				});
			}
			
		});
		return outer;
	}

}

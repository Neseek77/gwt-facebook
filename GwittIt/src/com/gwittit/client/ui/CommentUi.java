package com.gwittit.client.ui;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.gwittit.client.Config;
import com.gwittit.client.facebook.ApiFactory;
import com.gwittit.client.facebook.FacebookApi;
import com.gwittit.client.facebook.UserInfo;
import com.gwittit.client.facebook.FacebookApi.StreamRemoveCommentParams;
import com.gwittit.client.facebook.entities.Comment;
import com.gwittit.client.facebook.xfbml.FbName;
import com.gwittit.client.facebook.xfbml.FbProfilePic;

/**
 * Render a small comment under stream item.
 * 
 * CSS gwittit-CommentUi
 */

public class CommentUi extends Composite implements ClickHandler {
	
	public interface DeleteHandler {
		void onDelete ( String id );
	}
	
	
	private FlowPanel wrapper = new FlowPanel ();

	private HorizontalPanel outer = new HorizontalPanel ();
	
	private Comment comment;
	
	private Anchor deleteCommentLink = new Anchor ( "- delete comment -" );
	
	private FacebookApi apiClient = ApiFactory.newApiClient( Config.API_KEY );
	
	private DeleteHandler deleteHandler;
	
	public CommentUi ( Comment comment ) {
		
		this.comment = comment;
		
		// Pain this, but will have to do for now. FlowPanel is probably better.
		wrapper.addStyleName("gwittit-CommentUI");
		outer.addStyleName ( "gwittit-CommentUI-inner");

		deleteCommentLink.addClickHandler( this );
		
		FbProfilePic pp = new FbProfilePic ( comment.getFromId(), FbProfilePic.Size.square );
		pp.setSize("40px", "40px");
		
		outer.add ( pp );
		
		FbName n = new FbName ( comment.getFromId() );
		HTML html = new HTML (n.toString() + "<br/>" +  comment.getText() );
	
		outer.add ( html );

		wrapper.add ( outer );
		if ( comment.getFromId().equals ( UserInfo.getUidLong() ) ) {
			wrapper.add ( deleteCommentLink );
		}
		initWidget ( wrapper );
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CommentUi other = (CommentUi) obj;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		return true;
	}

	public void onClick(ClickEvent event) {
		
		if ( event.getSource() == deleteCommentLink ) {
			deleteComment ( comment.getId() );
		}
	}
	
	public void deleteComment ( String id ) {
		if ( deleteHandler != null ) {
			deleteHandler.onDelete(id);
		} else {
			Map<Enum<StreamRemoveCommentParams>,String> params = new HashMap<Enum<StreamRemoveCommentParams>,String> ();
			params.put( StreamRemoveCommentParams.comment_id,  ""+ comment.getId() );
			
			apiClient.stream_removeComment(params, new AsyncCallback<JSONValue> () {
				public void onFailure(Throwable caught) {
				}
				public void onSuccess(JSONValue result) {
					CommentUi.this.addStyleName("deleted");
				}
			});
		}
		
	}
	
	public void addDeleteHandler ( DeleteHandler deleteHandler ) {
		this.deleteHandler = deleteHandler;
		
	}
}

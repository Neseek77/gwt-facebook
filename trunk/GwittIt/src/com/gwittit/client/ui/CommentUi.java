package com.gwittit.client.ui;


import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.gwittit.client.Config;
import com.gwittit.client.UserInfo;
import com.gwittit.client.facebook.ApiFactory;
import com.gwittit.client.facebook.FacebookApi;
import com.gwittit.client.facebook.entities.Comment;
import com.gwittit.client.facebook.xfbml.FbName;
import com.gwittit.client.facebook.xfbml.FbProfilePic;

/**
 * 
 * Render a small comment under stream item.
 * 
 * CSS Configuration <code>.gwittit-CommentUi</code>
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
		
		FbProfilePic pp = new FbProfilePic ( new Long ( comment.getFromId() ), FbProfilePic.Size.square );
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

	public void onClick(ClickEvent event) {
		
		if ( event.getSource() == deleteCommentLink ) {
			deleteComment ( comment.getId() );
		}
	}
	
	public void deleteComment ( String id ) {
		if ( deleteHandler != null ) {
			deleteHandler.onDelete(id);
		} else {
			apiClient.stream_removeComment(comment.getId(), new AsyncCallback<JavaScriptObject> () {
				public void onFailure(Throwable caught) {
				}
				public void onSuccess(JavaScriptObject result) {
					CommentUi.this.addStyleName("deleted");
				}
			});
		}
		
	}
	
	public void addDeleteHandler ( DeleteHandler deleteHandler ) {
		this.deleteHandler = deleteHandler;
		
	}
}

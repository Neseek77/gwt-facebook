package com.gwittit.client.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.Config;
import com.gwittit.client.facebook.ApiFactory;
import com.gwittit.client.facebook.FacebookApi;
import com.gwittit.client.facebook.FacebookConnect;
import com.gwittit.client.facebook.UserInfo;
import com.gwittit.client.facebook.FacebookApi.Permission;
import com.gwittit.client.facebook.FacebookApi.StreamAddCommentParams;
import com.gwittit.client.facebook.FacebookApi.StreamGetCommentsParams;
import com.gwittit.client.facebook.FacebookApi.StreamLikeParams;
import com.gwittit.client.facebook.FacebookApi.StreamRemoveParams;
import com.gwittit.client.facebook.entities.Attachment;
import com.gwittit.client.facebook.entities.Comment;
import com.gwittit.client.facebook.entities.Comments;
import com.gwittit.client.facebook.entities.Media;
import com.gwittit.client.facebook.entities.Stream;
import com.gwittit.client.facebook.entities.Video;
import com.gwittit.client.facebook.xfbml.FbName;
import com.gwittit.client.facebook.xfbml.FbProfilePic;
import com.gwittit.client.facebook.xfbml.Xfbml;

public class StreamUi extends Composite implements ClickHandler {


	// Composite of profile picture on the left, everything
	// else on the right.
	final HorizontalPanel outer = new HorizontalPanel();

	final VerticalPanel inner = new VerticalPanel();

	// This is where we display comments.
	final SimplePanel commentListWrapper = new SimplePanel ();
	
	final VerticalPanel commentListPnl = new VerticalPanel ();

	// Toggle like / unlike
	private static final String LIKE_TEXT = "Like";
	
	// Toggle like / unlike
	private static final String UNLIKE_TEXT = "Unlike";
	
	// Let user like stream
	final Anchor likeItLink = new Anchor(LIKE_TEXT);
	
	// Display Comment link next to Like link
	final Anchor commentLink = new Anchor ( "Comment" );
	
	// Remove stream if user is allowed
	final Anchor removeStreamLink = new Anchor ( "- delete post -" );
	
	// This displays "1 Comments, 30 Comments etc" 
	final Anchor displayCommentsLink = new Anchor ();

	// Comment box
	final CommentBoxUI commentBox; 

	// Display animated loader
	private Image loader = new Image ( "/loader.gif");

	// Facebook Apio
	private FacebookApi apiClient = ApiFactory.newApiClient(Config.API_KEY);

	// Stream that this UI object wraps
	private Stream stream;

	/**
	 * Create a new Stream UI object
	 * @param stream wrap
	 */
	public StreamUi(Stream stream) {
		this.stream = stream;
		
		commentBox = new CommentBoxUI ( stream );

		// Set main Styles
		outer.getElement().setId("StreamUI-" + stream.getPostId() );
		outer.ensureDebugId( "gwittit-StreamUI-" + stream.getPostId() );
		outer.addStyleName( "gwittit-StreamUI");
		commentListPnl.addStyleName("commentList");
		
		commentListPnl.getElement().setId( "commentList-" + stream.getPostId() );

		inner.addStyleName("streamMainContent");
		inner.setSpacing(3);

		// Click Handlers 
		// Let user click like/unlike on any posted stream. 
		addLikeClickHandler();
		
		removeStreamLink.addClickHandler(this);
		
		renderUI ();
		
		if ( stream.getSourceId().equals(UserInfo.getUidLong() ) ) {
			inner.add( removeStreamLink );
		}
		
		initWidget( outer );
	}

	/**
	 * Experimental , let the stream object create a default widget to use.
	 */
	private Widget renderUI () {

	

		final HorizontalPanel horizontalPnl = new HorizontalPanel();
		horizontalPnl.add(new FbProfilePic(stream.getSourceId()));

		/*
		 * Posted by
		 */
		inner.add(new HTML(new FbName(stream.getSourceId()).toString() + " "
				+ (stream.getMessage() != null ? stream.getMessage() : "")));

		/*
		 * Attachment
		 */
		if (stream.getAttachment() != null) {
			inner.add(createAttachmentWidget());
		}

		/*
		 * Stream Info
		 */
		FlowPanel streamInfo = new FlowPanel();
		streamInfo.addStyleName("streamInfo");


		// Add small icon
		if (stream.getAttachment().getIcon() != null) {
			Image ic = new Image(stream.getAttachment().getIcon());
			streamInfo.add(ic);

		}
		// Add timestamp
		streamInfo.add(new Label(" Some time ago "));
		streamInfo.add(new Label(" - "));

		// Add like button

		// Check if the user likes the post, if so default will be Unlike post.
		if (stream.getLikes().getCount() > 0) {
			if (stream.getLikes().isUserLikes()) {
				likeItLink.setText(UNLIKE_TEXT);
			}
		}
		
		streamInfo.add(likeItLink);
		streamInfo.add(commentLink);
		inner.add(streamInfo);

		inner.add ( new HTML ( "<div style=\"clear: all\"/>" ) );
		
		/*
		 * Actions, comment, like etc
		 */
		HorizontalPanel actionsPnl = new HorizontalPanel();
		actionsPnl.addStyleName("actions");
		//actionsPnl.add(new DebugLink(stream.getWrappedObject() + ""));
		inner.add(actionsPnl);

		/**
		 * Likes
		 */
		HorizontalPanel likesPnl = new HorizontalPanel();

		if (stream.getLikes().getCount() > 0) {
			likesPnl.addStyleName("gwittit-LikesUI");
			likesPnl.add(stream.getLikes().createWidget());
			inner.add(likesPnl);
		}

		
		/**
		 * Comments Link
		 */
		

		if ( stream.getComments().getCount() > 0 ) {
		
			Comments comments = stream.getComments();
			displayCommentsLink.setText ( "Read " + comments.getCount () + " Comments " );
			displayCommentsLink.addStyleName("clickable");
			commentListPnl.add(  displayCommentsLink );
			
			addDisplayCommentsClickHandler ();
			
			commentListWrapper.setWidget( commentListPnl );
		} 
		inner.add ( commentListWrapper );

		
		/*
		 * Comments Box 
		 */
		
		commentLink.addClickHandler( new ClickHandler () {
			public void onClick(ClickEvent event) {
				commentBox.expand();
			}
			
		});
		commentBox.addClickHandler ( new ClickHandler () {

			public void onClick(ClickEvent event) {
								
				Map<Enum<StreamAddCommentParams>,String> params = new HashMap<Enum<StreamAddCommentParams>,String> ();
				params.put ( StreamAddCommentParams.post_id, stream.getPostId() );
				params.put ( StreamAddCommentParams.comment, commentBox.getText() ); 
				
				apiClient.stream_addComment ( params, new AsyncCallback<JSONValue> () {
					public void onFailure(Throwable t) {
						Window.alert ( "Failed to set comment" );
					}

					public void onSuccess(JSONValue v) {
						commentBox.reset();
						renderComments();
						GWT.log( StreamUi.class +  "StreamUI: added comment " + commentBox.getText(), null );

					}
					
				});
			}
			
		});
		inner.add( commentBox );
		
		/*
		 * Compile outer
		 */
		outer.add(new FbProfilePic(stream.getSourceId()));
		outer.add(inner);

		return outer;

	}

	public Widget createMediaWidget(Media m) {
		final SimplePanel w = new SimplePanel();
		w.addStyleName("streamMedia");

		if ( m.getTypeEnum() == Media.Type.video ) {
			Video v = m.getVideo();
			
			HorizontalPanel h = new HorizontalPanel ();
			
			Image image = new Image ( m.getSrc() );
			image.setWidth( "200");
			
			h.add ( image );
			
			Anchor a = new Anchor ( m.getAlt() );
			
			String urlPrefix = "";
			if ( v.getDisplayUrl().startsWith("/") ) {
				urlPrefix = "http://facebook.com";
			}
			a.setHref( urlPrefix );

			h.add ( a );
			w.add ( h );
			
		} else {
			Anchor a = new Anchor ( );
			
			Image image = new Image(m.getSrc());
			a.setHTML( image.toString() );
			a.setHref( m.getHref() );
			w.add(a);
		}
		return w;
	}

	public Widget createAttachmentWidget() {

		final Attachment attachment = stream.getAttachment();

		VerticalPanel outer = new VerticalPanel();
		outer.addStyleName("streamAttachment");

		VerticalPanel ap = new VerticalPanel();

		Anchor a = new Anchor(attachment.getName());
		a.setTarget("_blank");
		a.setHref(attachment.getHref());
		ap.add(a);

		if (attachment.getDescription() != null) {
			ap.add(new HTML(attachment.getDescription()));
		}

		HorizontalPanel mediasPanel = new HorizontalPanel();
		
		for (Media m : attachment.getMedias()) {
			mediasPanel.add(createMediaWidget(m));
		}

		outer.add(mediasPanel);
		outer.add(ap);

		return outer;
	}
	
	
	private void addDisplayCommentsClickHandler () {
		displayCommentsLink.addClickHandler( new ClickHandler () {
			public void onClick(ClickEvent event) {
				renderComments();
			}
			
		});
		
	}
	
	private void renderComments () {
	
		if ( commentListWrapper.getWidget() == null ) {
			commentListWrapper.setWidget( commentListPnl );
		}
		commentListPnl.remove( displayCommentsLink );

		final Image load = new Image ( "loader.gif");
		commentListPnl.add ( load );
		Map<Enum<StreamGetCommentsParams>,String> params = new HashMap<Enum<StreamGetCommentsParams>, String> ();
		params.put( StreamGetCommentsParams.post_id, stream.getPostId());
	
		apiClient.stream_getComments(params,  new AsyncCallback<List<Comment>> () {
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			public void onSuccess(List<Comment> result) {
				commentListPnl.remove ( load );
				
				for ( Comment c : result ) {
					CommentUi ui = new CommentUi ( c );
					
					if ( commentListPnl.getWidgetIndex( ui ) == -1  ) {
						commentListPnl.add( ui );
						
					}
				}			
				Xfbml.parse( commentListPnl.getElement() );
			}
		});
		
	}

	/** -------------------------------------- Like --------------------------------------------- */
	
	private void addLikeClickHandler () {
		// Monster add clickhandler to "like" link. We need to do a log of checking to find
		// out if user has the right permission. Another solution would be to force the
		// user to grant us the right permissions before using the application. Dont know
		// what is the most user friendly : )
		likeItLink.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {


			// First check if user has permission.
			// Second if user has not permission, show permission dialog and then check if the
			// user let as add 'like' to stream.
			apiClient.users_hasAppPermission(Permission.publish_stream,
					new AsyncCallback<Boolean>() {
	
						public void onFailure(Throwable caught) {
							Window.alert( StreamUi.class + "Error: StreamUi: users_hasAppPermission call failed");
						}
	
						public void onSuccess(final Boolean canPublish) {
	
							// If user has allowed this application to
							// publish story, then continue.
							if (canPublish) {
								toggleLike(likeItLink, stream);
							} else {
								// Show permission dialog before like.
	     							FacebookConnect.showPermissionDialog( FacebookApi.Permission.publish_stream,
									   
	     							new AsyncCallback<Boolean>() {
	
											public void onFailure(Throwable caught) {
												Window.alert("Error: StreamUi: showPermissionDialog call failed");
											}
		
											public void onSuccess(final Boolean permission) {
												if (permission) {
													toggleLike(likeItLink, stream);
												} else {
													Window.alert("Skipped Like");
												}
											}
										});
							}
						}
	
					});
			}
		});
	}
	
	/**
	 * Toggle Like
	 */
	private void toggleLike(Anchor likeIt, Stream stream) {
		if (likeIt.getText().equals(LIKE_TEXT)) {
			addLike(stream, likeIt);
		} else {
			removeLike(stream, likeIt);
		}
	}

	private void removeLike(final Stream stream, final Anchor clickedLink) {
		Map<Enum<StreamLikeParams>, String> params = new HashMap<Enum<StreamLikeParams>, String>();
		params.put(StreamLikeParams.post_id, stream.getPostId());
		
		apiClient.stream_removeLike(params, new AsyncCallback<JSONValue>() {
			public void onFailure(Throwable t) {
				Window.alert("Failed to remove like");
			}

			public void onSuccess(JSONValue o) {
				clickedLink.setHTML("like");
			}
		});
	}

	private void addLike(final Stream stream, final Anchor clickedLink) {
		Map<Enum<StreamLikeParams>, String> params = new HashMap<Enum<StreamLikeParams>, String>();
		params.put( StreamLikeParams.post_id, stream.getPostId() );

		apiClient.stream_addLike(params, new AsyncCallback<JSONValue>() {
			public void onFailure ( Throwable t ) {
				Window.alert("Failed to add like");
			}
			public void onSuccess(JSONValue o) {
				clickedLink.setText("Unlike");
			}

		});
	}

	public void onClick(ClickEvent event) {
		Object clicked = event.getSource();
		
		if ( clicked == removeStreamLink ) {
			inner.remove( removeStreamLink );
			inner.add( loader );
			
			Map<Enum<StreamRemoveParams>,String> params = new HashMap<Enum<StreamRemoveParams>,String> ();
			params.put( StreamRemoveParams.post_id, stream.getPostId() );
		
			apiClient.stream_remove(params, new AsyncCallback<JSONValue> () {
				public void onFailure(Throwable caught) {
					Window.alert( StreamUi.class + ": Failed " + caught );
				}
				public void onSuccess(JSONValue v) {
					StreamUi.this.addStyleName("deleted");
					
				}
			});
		}
	}

}

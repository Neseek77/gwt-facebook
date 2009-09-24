package com.gwittit.client;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.facebook.FacebookApi;
import com.gwittit.client.facebook.FacebookCallback;
import com.gwittit.client.facebook.FacebookConnect;
import com.gwittit.client.facebook.FacebookApi.Permission;
import com.gwittit.client.facebook.entities.Attachment;
import com.gwittit.client.facebook.entities.Comments;
import com.gwittit.client.facebook.entities.Media;
import com.gwittit.client.facebook.entities.Stream;
import com.gwittit.client.facebook.xfbml.FbName;
import com.gwittit.client.facebook.xfbml.FbProfilePic;

public class StreamUi {

	private static final String LIKE_TEXT = "Like";

	private static final String UNLIKE_TEXT = "Unlike";

	private FacebookApi apiClient;

	private Stream stream;

	public StreamUi(Stream stream, FacebookApi apiClient) {
		this.stream = stream;
		this.apiClient = apiClient;
	}

	public Widget createMediaWidget(Media m) {
		final SimplePanel w = new SimplePanel();
		w.addStyleName("gwittit-Media");

		if (Media.Type.photo == m.getTypeEnum()) {
			Image image = new Image(m.getSrc());
			w.add(image);

		} else if (Media.Type.link == m.getTypeEnum()) {
			Image image = new Image(m.getSrc());
			w.add(image);
		}
		return w;
	}

	public Widget createAttachmentWidget() {

		final Attachment attachment = stream.getAttachment();

		HorizontalPanel outer = new HorizontalPanel();
		outer.addStyleName("gwittit-Attachment");

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

	/**
	 * Experimental , let the stream object create a default widget to use.
	 */
	public Widget createWidget() {

		final HorizontalPanel outer = new HorizontalPanel();
		outer.addStyleName("stream");
		outer.addStyleName("gwittit-Stream");

		final HorizontalPanel horizontalPnl = new HorizontalPanel();
		horizontalPnl.add(new FbProfilePic(stream.getSourceId()));

		/*
		 * Posted by
		 */
		final VerticalPanel inner = new VerticalPanel();
		inner.addStyleName("text");
		inner.add(new HTML(new FbName(stream.getSourceId()).toString() + " "
				+ (stream.getMessage() != null ? stream.getMessage() : "")));

		/*
		 * Attachment
		 */
		if (stream.getAttachment().getName() != null) {
			inner.add(createAttachmentWidget());
		}

		/*
		 * Stream Info
		 */
		HorizontalPanel streamInfo = new HorizontalPanel();
		streamInfo.addStyleName("gwittit-StreamInfo");
		streamInfo.setSpacing(5);

		// Add small icon
		if (stream.getAttachment().getIcon() != null) {
			Image ic = new Image(stream.getAttachment().getIcon());
			streamInfo.add(ic);

		}
		// Add timestamp
		streamInfo.add(new HTML(" Some time ago "));
		streamInfo.add(new Label(" - "));
		inner.add(streamInfo);

		// Add like button
		final Anchor likeIt = new Anchor(LIKE_TEXT);

		// Check if the user likes the post, if so default will be Unlike post.
		if (stream.getLikes().getCount() > 0) {
			if (stream.getLikes().isUserLikes()) {
				likeIt.setText(UNLIKE_TEXT);
			}
		}
		
		// Monster add clickhandler to "like" link. We need to do a log of checking to find
		// out if user has the right permission. Another solution would be to force the
		// user to grant us the right permissions before using the application. Dont know
		// what is the most user friendly : )
		likeIt.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {


			// First check if user has permission.
			// Second if user has not permission, show permission dialog and then check if the
			// user let as add 'like' to stream.
			apiClient.users_hasAppPermission(Permission.publish_stream,
					new AsyncCallback<Boolean>() {
	
						public void onFailure(Throwable caught) {
							Window.alert("Error: StreamUi: users_hasAppPermission call failed");
						}
	
						public void onSuccess(final Boolean canPublish) {
	
							// If user has allowed this application to
							// publish story, then continue.
							if (canPublish) {
								addOrRemoveLike(likeIt, stream);
							} else {
								// Show permission dialog before like.
	     							FacebookConnect.showPermissionDialog(
									FacebookApi.Permission.publish_stream,
									new AsyncCallback<Boolean>() {
	
										public void onFailure(Throwable caught) {
											Window.alert("Error: StreamUi: showPermissionDialog call failed");
										}
	
										public void onSuccess(final Boolean permission) {
											if (permission) {
												addOrRemoveLike(likeIt, stream);
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
		streamInfo.add(likeIt);

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
			likesPnl.addStyleName("feedBack");
			likesPnl.add(stream.getLikes().createWidget());
			inner.add(likesPnl);
		}

		
		/**
		 * Comments
		 */
		
		VerticalPanel commentsPnl = new VerticalPanel ();
		if ( stream.getComments().getCount() > 0 ) {
		
			Comments comments = stream.getComments();
			Anchor commentsLnk = new Anchor ( comments.getCount() + " Comments" );
			commentsLnk.addStyleName("clickable");
			commentsPnl.add(  commentsLnk );
			
			commentsLnk.addClickHandler( new ClickHandler () {

				public void onClick(ClickEvent event) {
					Window.alert ( "Comments not implemented yet");
				}
				
			});
		}
		inner.add ( commentsPnl );
		
		/*
		 * Compile outer
		 */
		outer.add(new FbProfilePic(stream.getSourceId()));
		outer.add(inner);

		return outer;

	}

	private void addOrRemoveLike(Anchor likeIt, Stream stream) {
		if (likeIt.getText().equals(LIKE_TEXT)) {
			addLike(stream, likeIt);
		} else {
			removeLike(stream, likeIt);
		}
	}

	private void removeLike(final Stream stream, final Anchor clickedLink) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("post_id", stream.getPostId());
		apiClient.stream_removeLike(params, new FacebookCallback() {

			public void onError(JSONValue o) {
				Window.alert("Failed to remove like");
			}

			public void onSuccess(JSONValue o) {
				clickedLink.setHTML("like");
			}

		});

	}

	private void addLike(final Stream stream, final Anchor clickedLink) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("post_id", stream.getPostId());

		apiClient.stream_addLike(params, new FacebookCallback() {

			public void onError(JSONValue o) {
				Window.alert("Failed to add like");
			}

			public void onSuccess(JSONValue o) {
				clickedLink.setText("Unlike");
			}

		});
	}

}

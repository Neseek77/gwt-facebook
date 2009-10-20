package com.gwittit.client.example;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.facebook.Util;
import com.gwittit.client.facebook.FacebookApi.Permission;
import com.gwittit.client.facebook.entities.Album;
import com.gwittit.client.facebook.entities.Attachment;
import com.gwittit.client.facebook.entities.Comment;
import com.gwittit.client.facebook.entities.Comments;
import com.gwittit.client.facebook.entities.Likes;
import com.gwittit.client.facebook.entities.Media;
import com.gwittit.client.facebook.entities.Post;
import com.gwittit.client.facebook.entities.Stream;
import com.gwittit.client.facebook.entities.Media.Type;
import com.gwittit.client.facebook.ui.PermissionDialog;
import com.gwittit.client.facebook.ui.PermissionDialog.PermissionHandler;
import com.gwittit.client.facebook.xfbml.FbName;
import com.gwittit.client.facebook.xfbml.FbPhoto;
import com.gwittit.client.facebook.xfbml.FbProfilePic;
import com.gwittit.client.facebook.xfbml.Xfbml;
import com.gwittit.client.facebook.xfbml.FbProfilePic.Size;

/**
 * Showcase for method call <code>stream.get</code>
 * 
 * @author olamar72
 */
public class Stream_get extends Showcase {

	static String method  = "stream.get";
	
	
	public Stream_get () {
		super ( method );
	}
	
	private Stream stream;
	/**
	 * Create widget
	 */
	@Override
	public Widget createWidget () {
        final VerticalPanel outer = new VerticalPanel();
	    final PermissionDialog pd = new PermissionDialog();
        outer.setSpacing ( 5 );
        outer.addStyleName ( "gwittit-Stream_get" );
        outer.getElement().setId ( method );
        
        outer.add ( pd );
        pd.checkPermission ( Permission.read_stream );
        
        pd.addPermissionHandler ( new PermissionHandler () {
            public void onPermissionChange(Boolean granted) {
                if ( granted ) {
                    renderMainContent ( outer );
                } else {
                    outer.add ( new HTML ( "Dont have permission to read stream " ) );
                }
            }
        });
        return outer;
	}
	
	/**
	 * Render when user granted us permission to read stream
	 */
	void renderMainContent ( VerticalPanel addContentToPnl ) {
	    final VerticalPanel streamBody = new VerticalPanel ();
        final HorizontalPanel menu = new HorizontalPanel ();

        menu.addStyleName ( "streamMenu" );
        menu.setSpacing ( 5 );


        // Create menu
        final Anchor postsLink = new Anchor ( "Posts" );
        final Anchor profilesLink = new Anchor ( "Profiles" );
        final Anchor albumsLink = new Anchor ( "Albums");
        menu.add ( new HTML ( "<b> Choose Stream : </b> " ) );

        menu.add ( postsLink );
        menu.add ( profilesLink );
        menu.add ( albumsLink );
        
        // Click posts link
        postsLink.addClickHandler ( new ClickHandler() {
            public void onClick(ClickEvent event) {
                renderPosts ( streamBody, stream.getPosts () );
            } 
        });

        // Click profiles link
        profilesLink.addClickHandler ( new ClickHandler() {
            public void onClick(ClickEvent event) {
                streamBody.clear ();
                streamBody.add ( asJson ( "Profiles", stream.getProfiles () ) );
            }
        });
        
        // Click album links
        albumsLink.addClickHandler ( new ClickHandler() {
            public void onClick(ClickEvent event) {
                renderAlbums ( streamBody, stream.getAlbums () );
            } 
        }) ;
        
        addContentToPnl.add ( menu );
        addContentToPnl.add ( streamBody );

        
        // Start loading
        addLoader ( streamBody);
        // Get stream from facebook.
        apiClient.stream_get(null, new AsyncCallback<Stream> () {
            public void onFailure(Throwable caught) {
                handleFailure ( caught );
            }
            public void onSuccess(Stream result) {
                stream = result;
                removeLoader ( streamBody );
                renderPosts ( streamBody, result.getPosts () );
                Xfbml.parse ( streamBody );
            }
        });
	}

	/**
	 * Render list of albums in stream
	 */
	void renderAlbums ( VerticalPanel addContentToPnl, JsArray<Album> albums  ) {
	
	    addContentToPnl.clear ();
	    
	    VerticalPanel p = new VerticalPanel ();
	    p.getElement ().setId ( "Albums" );
	    
	    p.add ( new HTML ( "<h3>Albums in Stream</h3>" ) );
	    
	    for (Album a : Util.iterate ( albums ) ) {
	        
	        p.add ( new HTML ( "<h4>" + a.getName () + "</h4>" ) );
	        
	        if ( a.hasCover () ) {
	            p.add ( new HTML (" AlbumCover: " ) ) ;
	            p.add ( new FbPhoto ( a.getCoverPid () , FbPhoto.Size.small ) ) ;
	        }
	    }
	    addContentToPnl.add ( p );
	    Xfbml.parse ( p );
	    
	}
	
	/**
	 * Render posts i a stream
	 */
	void renderPosts ( VerticalPanel addContentToPnl, JsArray<Post> posts ) {
	    
	    addContentToPnl.clear ();

	    addContentToPnl.add ( new HTML ( "Number of posts: " + posts.length () ) ) ; 
	  
	    for ( Post post : Util.iterate ( posts ) )  {
	     
	        final VerticalPanel postContentPnl = new VerticalPanel ();
	        postContentPnl.addStyleName ( "postContent" );
	        postContentPnl.add ( new HTML ( new FbName ( post.getActorId () ) + " " + post.getMessage () ) );
	        
	        if ( post.getAttachment ().getName () != null ) {
	            postContentPnl.add ( createAttachmentUI ( post.getAttachment() ) );
	        }
            if ( post.getLikes ().getCount () > 0 ) {
	            postContentPnl.add ( createLikesUI ( post.getLikes () ) );
	        }
            if ( post.getComments ().getCount () > 0 ) {
                postContentPnl.add ( createCommentsUI ( post.getComments () ) );
            }
	        // Add profilepic on the left, postcontent on the right
	        HorizontalPanel p = new HorizontalPanel ();
	        p.addStyleName ( "post" );

	        p.add ( new FbProfilePic ( post.getActorId () ) ) ;
	        p.add ( postContentPnl );
	        
	        
	        //postPnl.add ( asJson ( "LikesAsJson: ", post.getLikes () ));
	        addContentToPnl.add ( p );
	    }
	}

	/**
	 * Create attachment UI
	 */
	public Widget createAttachmentUI ( Attachment attachment ) {

	    VerticalPanel p = new VerticalPanel ();
	    p.addStyleName ( "attachment fbColorLight rounded addSpace" );
	    p.add ( new HTML ( "<h3>Attachment</h3> "  ) );

        HorizontalPanel thumbs = new HorizontalPanel ();
        thumbs.setSpacing ( 10 );
        
	    for ( Media m : Util.iterate ( attachment.getMedia () ) ) {
	        p.add ( new HTML ( "<b>MediaContent:</b> " + m.stringify () ) );

	        if ( m.getSrc () != null ) {
	            thumbs.add ( new Image ( m.getSrc () ) );
	            
	        } 
	        if ( m.getTypeEnum () == Type.video ) {
	            Anchor vLink = new Anchor ( "See Video");
	            vLink.setHref ( m.getVideo ().getSourceUrl () );
	            p.add ( vLink );
	        }
	    }
        p.add ( thumbs );
	    return p;
	}
	
	/**
	 * Create comments ui
	 */
	public Widget createCommentsUI ( Comments comments ) {
	 
	    VerticalPanel p = new VerticalPanel ();
	    p.addStyleName ( "comments fbColorLight rounded addSpace" );
	    p.add ( new HTML ( "<h3>Comments on this post: " + comments.getCount () + "</h4>" ) );
	    return p;
	    
	}
	
	/**
	 * Create likes ui
	 */
	public Widget createLikesUI ( Likes likes ) {
	
	    
	    String html = "<h3>People who likes this " + likes.getCount () + "</h3>";
	    if ( likes.getCount () > 0 ) {
	        for ( Long uid : likes.getSample () ) {
	            
	            html += "" + new FbName ( uid ) +  "(" + uid + ") <br/>";
	            
	        }
	    }
        HTML h = createHTML ( html );
        h.addStyleName ( "likes fbColorLight rounded addSpace" );
        h.getElement ().setId ( "Likes" + System.currentTimeMillis () );

	    Xfbml.parse ( h );
	    return h;
	}
	
	private HTML createHTML ( String h ) {
	   HTML ht = new HTML (h);
	   return ht;
	}
	
	public HTML asJson ( String header, JavaScriptObject o ) {
	    return new HTML ( "<b>" + header + "</b>:" + new JSONObject ( o ).toString () );
	}
}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
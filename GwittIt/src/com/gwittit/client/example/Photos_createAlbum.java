package com.gwittit.client.example;



import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.facebook.entities.Album;
import com.gwittit.client.facebook.entities.Photo;
import com.gwittit.client.facebook.entities.Album.Visibility;


/**
 * Showcase for method call <code>photos.createAlbum</code>
 * @author olamar72
 */
public class Photos_createAlbum extends Showcase {

    
    private Image sampleUpload = new Image ( "/sample");
    /*
     * User clicks create album
     */
    private class CreateAlbumClickHandler implements ClickHandler {
        public void onClick(ClickEvent event) {
            createAlbum ( inputText.getValue(), visiList.getItemText(visiList.getSelectedIndex() ) );
        }
        
    }
    
    /*
     * Create album callback 
     */
    private class CreateAlbumCallback implements AsyncCallback<Album> {

        public void onFailure(Throwable caught) {
            handleFailure ( caught );
        }
        public void onSuccess(Album album) {
            response.setWidget ( new HTML ( "Album created with id " + album.getAid () ) );
            //response.setWidget ( new HTML ( "Album id: " + album.getAid() + " " + album.getName () + ", link:  " + album.getLink() ) ) ;
            
        }
        
    };

    /**
     * Upload photo
     */
    private class UploadPhotoClickHandler implements ClickHandler {
        public void onClick(ClickEvent event) {
            apiClient.photosUpload ( null, "Testing", new  UploadPhotoCallback() );
        }
    }
    

    /**
     * Upload photo response
     */
    private class UploadPhotoCallback implements AsyncCallback<Photo> {
        public void onFailure(Throwable caught) {
            handleFailure ( caught );
        }
        public void onSuccess(Photo result) {
            Window.alert ( "Photo uploaded" );
        }
    }
    
	private VerticalPanel outer;
    private TextBox inputText;// = new TextBox ();
    private Button createButton; //= new Button ( "Create Album" );
    private ListBox visiList;// = new ListBox (false);
    
	private SimplePanel response;
	
	public Photos_createAlbum () {
		super("photos.createAlbum, photos.upload" );
	}
	
	@Override
	public Widget createWidget() {
	    
		outer = new VerticalPanel ();
		response = new SimplePanel ();
		
		final HorizontalPanel inputWrapper = new HorizontalPanel ();
		inputWrapper.setSpacing(10);
		
		inputText = new TextBox ();
		createButton = new Button ( "Create Album" );
		visiList = new ListBox (false);

		
		visiList.addItem ( "friends");
		visiList.addItem ( "friends_of_friends");
		visiList.addItem ( "networks");
		visiList.addItem ( "everyone") ;

		inputWrapper.add ( inputText );
		
		inputWrapper.add ( new HTML ( " Visible "  ) ); 
		inputWrapper.add ( visiList );
		inputWrapper.add ( createButton );
	
		createButton.addClickHandler( new CreateAlbumClickHandler () );
		
		outer.add ( inputWrapper );
		outer.add ( response );
		
	//	outer.add ( createUploadPhotoUI () );
		return outer;
	}
	
	private Widget createUploadPhotoUI () {
	    
	    VerticalPanel v = new VerticalPanel ();
	    Button b = new Button("Upload Photo");
	    b.addClickHandler ( new UploadPhotoClickHandler () );
	    v.add ( b );
	    return v;
	    
	}
	
	private void createAlbum ( final String name, final String visible ) {
		Album album = Album.createAlbum ( name, null, null, Visibility.valueOf ( visible ) );
		apiClient.photosCreateAlbum( album, new CreateAlbumCallback () );
	}
}

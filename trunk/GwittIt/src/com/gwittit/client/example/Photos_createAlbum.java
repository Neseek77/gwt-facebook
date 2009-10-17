package com.gwittit.client.example;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.facebook.entities.Album;
import com.gwittit.client.facebook.entities.Album.Visibility;


/**
 * Showcase for method call <code>photos.createAlbum</code>
 * @author olamar72
 */
public class Photos_createAlbum extends Showcase {

	private VerticalPanel outer;

	private SimplePanel response;
	
	public Photos_createAlbum () {
		super("photos.createAlbum" );
	}
	
	@Override
	public Widget createWidget() {
		outer = new VerticalPanel ();
		response = new SimplePanel ();
		
		final HorizontalPanel inputWrapper = new HorizontalPanel ();
		inputWrapper.setSpacing(10);
		
		final TextBox inputText = new TextBox ();
		final Button createButton = new Button ( "Create Album" );
		final ListBox visiList = new ListBox (false);
		visiList.addItem ( "friends");
		visiList.addItem ( "friends_of_friends");
		visiList.addItem ( "networks");
		visiList.addItem ( "everyone") ;

		inputWrapper.add ( inputText );
		
		inputWrapper.add ( new HTML ( " Visible "  ) ); 
		inputWrapper.add ( visiList );
		inputWrapper.add ( createButton );
	
		createButton.addClickHandler( new ClickHandler () {
			public void onClick(ClickEvent event) {
				createAlbum ( inputText.getValue(), visiList.getItemText(visiList.getSelectedIndex() ) );
			}
			
		});
		
		outer.add ( inputWrapper );
		outer.add ( response );
		
		return outer;
	}
	
	private void createAlbum ( final String name, final String visible ) {
		Album album = Album.createAlbum ( name, null, null, Visibility.valueOf ( visible ) );
		apiClient.photos_createAlbum( album, new AsyncCallback<Album> () {
			public void onFailure(Throwable caught) {
				handleFailure ( caught );
			}
			public void onSuccess(Album photo ) {
				response.setWidget ( new HTML ( "Album id: " + photo.getAid() + " " + photo.getName () + ", link:  " + photo.getLink() ) ) ;
			}
		});
	}
	

}

package com.gwittit.client.examples;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwittit.client.facebook.FacebookApi;
import com.gwittit.client.facebook.entities.Album;
import com.gwittit.client.facebook.xfbml.FbPhoto;
import com.gwittit.client.facebook.xfbml.Xfbml;

public class PhotosGetAlbumsExample extends Example {
	
	
	private VerticalPanel outer = new VerticalPanel ();

	private Image loader = new Image ( "/loader.gif");
	
	/**
	 * @param apiClient
	 */
	public PhotosGetAlbumsExample ( FacebookApi apiClient ) {
		
		
		outer.getElement().setId("PhotosGetAlbumsExample");
		outer.add ( new HTML ( "<h1>Photos...</h1>"));
		outer.add ( loader );
		
		Map<String,String> params = new HashMap<String,String> ();
		
		apiClient.photos_getAlbums(params, new AsyncCallback<List<Album>> () {
			public void onSuccess(List<Album> albums) {
		
				outer.remove( loader );
				
				outer.add ( new HTML ("<h3> Albums </h3>" ) );
				FlowPanel albumsPanel = new FlowPanel ();
				albumsPanel.addStyleName ( "albumsPanel");
				
				for ( Album album : albums ) {
					FlowPanel tmp = new FlowPanel ();
					tmp.addStyleName ( "album" );
					tmp.add ( new HTML ( album.getName() ) ) ;
					FbPhoto photo = new FbPhoto ( album.getCoverPid() );
					photo.getElement().setAttribute("size", "thumb");
					tmp.add ( photo );

					albumsPanel.add( tmp );
				}
				
				outer.add ( albumsPanel );
				Xfbml.parse( outer.getElement() );

			}

			public void onFailure(Throwable caught) {
				Window.alert ( "Failed to fetch albums " + caught );
			}
		});
		
		initWidget( outer );
		
	}

	@Override
	public String getInfo() {
		return "Demonstrates the API call photos.getAlbums";
	}

}

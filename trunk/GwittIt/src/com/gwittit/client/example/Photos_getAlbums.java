package com.gwittit.client.example;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.facebook.entities.Album;
import com.gwittit.client.facebook.xfbml.FbPhoto;
import com.gwittit.client.facebook.xfbml.Xfbml;
import com.gwittit.client.facebook.xfbml.FbPhoto.Size;

/**
 * Showcase for method call <code>photos.getAlbums</code>
 * 
 * @author olamar72
 *
 */
public class Photos_getAlbums extends Showcase {

	
	static final String method  = "photos.getAlbums";
	
	public Photos_getAlbums () {
		super ( method );
	}
		
	@Override
	public Widget createWidget () {
		
		final VerticalPanel outer = new VerticalPanel ();

		outer.add( getLoader () );
		outer.getElement().setId( "ShowPhotosGetAlbums");
		
		// Call facebook
		apiClient.photos_getAlbums(new AsyncCallback<List<Album>> () {

			public void onFailure(Throwable caught) {
				handleFailure ( caught );
			}

			public void onSuccess(List<Album> result) {
				outer.remove( getLoader () );
				
				outer.add ( new HTML ( "Result Size: " + result.size () ) );
				for ( Album a : result ) {
					String html ="<h2>Name: " + a.getName() + ", Description: " + a.getDescription() + "</h2>";
					outer.add ( new HTML ( html ) );
					if ( a.hasCover () ) {
					    outer.add ( new FbPhoto ( a.getCoverPidString() , Size.small ) );
					}
				}
				Xfbml.parse(outer);
			}
			
		});
		
		return outer;
	}
}

package com.gwittit.client.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.facebook.FacebookApi.PhotosGetAlbumsParams;
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
		
		// You can pass params here to 
		Map<Enum<PhotosGetAlbumsParams>,String> params = new HashMap<Enum<PhotosGetAlbumsParams>,String> ();
		
		// Call facebook
		apiClient.photos_getAlbums(params, new AsyncCallback<List<Album>> () {

			public void onFailure(Throwable caught) {
				handleFailure ( caught );
			}

			public void onSuccess(List<Album> result) {
				outer.remove( getLoader () );
				for ( Album a : result ) {
					String html ="Name: " + a.getName() + ", Description: " + a.getDescription(); 
					outer.add ( new HTML ( html ) );
					outer.add ( new FbPhoto ( a.getCoverPid() , Size.small ) );
				}
				Xfbml.parse(outer);
			}
			
		});
		
		return outer;
	}
}

package com.gwittit.client.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwittit.client.Config;
import com.gwittit.client.facebook.ApiFactory;
import com.gwittit.client.facebook.FacebookApi;
import com.gwittit.client.facebook.entities.Album;
import com.gwittit.client.facebook.xfbml.FbPhoto;
import com.gwittit.client.facebook.xfbml.Xfbml;

public class ShowPhotosGetAlbums extends Example {

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Display albums of current logged in user";
	}

	@Override
	public String getHeader() {
		return "photos.getAlbums";
	}

	
	private VerticalPanel outer = new VerticalPanel ();
	
	private FacebookApi apiClient = ApiFactory.newApiClient(Config.API_KEY);
	
	public ShowPhotosGetAlbums () {
		
		
		outer.add( getLoader () );
		outer.getElement().setId( "ShowPhotosGetAlbums");
		
		Map<String,String> params = new HashMap<String,String> ();
		
		apiClient.photos_getAlbums(params, new AsyncCallback<List<Album>> () {

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			public void onSuccess(List<Album> result) {
				outer.remove( getLoader () );
				for ( Album a : result ) {
					String html ="Name: " + a.getName() + ", Description: " + a.getDescription(); 
					outer.add ( new HTML ( html ) );
					outer.add ( new FbPhoto ( a.getCoverPid() ) );
				}
				Xfbml.parse(outer);
			}
			
		});
		
		initWidget ( outer );
		
	}
	
}

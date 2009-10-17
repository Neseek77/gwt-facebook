package com.gwittit.client.example;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.example.FriendSelector.FriendSelectionHandler;
import com.gwittit.client.facebook.entities.Photo;
import com.gwittit.client.facebook.xfbml.FbPhoto;
import com.gwittit.client.facebook.xfbml.Xfbml;
import com.gwittit.client.facebook.xfbml.FbPhoto.Size;

/**
 * Showcase for method call <code>photos.get</code>
 * 
 * @author olamar72
 */
public class Photos_get extends Showcase {

	static final String method = "photos.get";
	
	private HorizontalPanel paramsWrapper;
	private VerticalPanel resultWrapper;
	private VerticalPanel paramsResultWrapper;
	
	public Photos_get () {
		super( method );
	}
	
	@Override
	public Widget createWidget () {
		paramsWrapper = new HorizontalPanel ();
		resultWrapper = new VerticalPanel ();
		paramsResultWrapper = new VerticalPanel ();
		
		paramsWrapper.addStyleName("params");
		paramsResultWrapper.add ( paramsWrapper );
		paramsResultWrapper.add ( resultWrapper );
		
		FriendSelector fs = new FriendSelector ();
		fs.addFriendSelectionHandler(new FriendSelectionHandler () {

			public void onSelected(Long uid) {
				displayPhotos(uid);
			}
			
		});
		paramsWrapper.add ( fs );
		return paramsResultWrapper;
	}
	
	
	/*
	 * Display photos of selected user 
	 */
	private void displayPhotos ( final Long subjId ) {
		resultWrapper.add( getLoader() );
		

		// Get photos from facebook
		apiClient.photos_get(subjId, new AsyncCallback<List<Photo>> () {

			public void onFailure(Throwable caught) {
			    handleFailure ( caught );
			}

			public void onSuccess(List<Photo> result) {
				final FlowPanel photosPanel = new FlowPanel ();
				
				photosPanel.getElement().setId ( "photosPanel");
				resultWrapper.clear ();
				
				resultWrapper.add ( new HTML ( "<h4>Photos size: " + result.size() + "</h4>" ) );
				
				for ( Photo p : result ) {
					
					photosPanel.add ( new FbPhoto ( p.getPid(), Size.thumb  ) );
				}
				resultWrapper.add ( photosPanel );
				Xfbml.parse( photosPanel.getElement () );
			}
			
		});
		
	}

}

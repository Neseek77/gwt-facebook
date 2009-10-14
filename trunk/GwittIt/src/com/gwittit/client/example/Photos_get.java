package com.gwittit.client.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.Config;
import com.gwittit.client.example.FriendSelector.FriendSelectionHandler;
import com.gwittit.client.facebook.ApiFactory;
import com.gwittit.client.facebook.FacebookApi;
import com.gwittit.client.facebook.FacebookApi.PhotosGetParams;
import com.gwittit.client.facebook.entities.Photo;
import com.gwittit.client.facebook.xfbml.FbName;
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
				displayPhotos(uid+"");
			}
			
		});
		paramsWrapper.add ( fs );
		return paramsResultWrapper;
	}
	
	
	/*
	 * Display photos of selected user 
	 */
	private void displayPhotos ( final String uid ) {
		resultWrapper.add( getLoader() );
		
		Map<Enum<PhotosGetParams>,String> params = new HashMap<Enum<PhotosGetParams>,String> ();
		params.put( PhotosGetParams.uid, uid);

		// Get photos from facebook
		apiClient.photos_get(params, new AsyncCallback<List<Photo>> () {

			public void onFailure(Throwable caught) {
			    handleFailure ( caught );
			}

			public void onSuccess(List<Photo> result) {
				final FlowPanel photosPanel = new FlowPanel ();
				
				photosPanel.add ( new HTML ( "<h1> Photos of " + new FbName ( new Long ( uid ) ).toString() + "</h1> ") );
				photosPanel.getElement().setId ( "photosPanel");
				resultWrapper.clear ();
				
				resultWrapper.add ( new HTML ( "Photos size: " + result.size() ) );
				
				for ( Photo p : result ) {
					
					photosPanel.add ( new FbPhoto ( p.getPid(), Size.thumb  ) );
				}
				resultWrapper.add ( photosPanel );
				Xfbml.parse( photosPanel.getElement () );
			}
			
		});
		
	}

}

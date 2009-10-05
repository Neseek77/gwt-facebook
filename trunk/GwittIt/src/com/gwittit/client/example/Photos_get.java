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
import com.gwittit.client.facebook.entities.Photo;
import com.gwittit.client.facebook.xfbml.FbName;
import com.gwittit.client.facebook.xfbml.FbPhoto;
import com.gwittit.client.facebook.xfbml.Xfbml;
import com.gwittit.client.facebook.xfbml.FbPhoto.Size;

/**
 * Photos get
 */
public class Photos_get extends Example {

	static final String method = "photos.get";
	
	
	HorizontalPanel paramsWrapper;
	VerticalPanel resultWrapper;
	VerticalPanel paramsResultWrapper;
	
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
		return paramsResultWrapper;
	}
	
	private void displayPhotos ( final String uid ) {
		resultWrapper.add( getLoader() );
		
		Map<String,String> params = new HashMap<String,String> ();
		params.put("subj_id", uid);
		
		apiClient.photos_get(params, new AsyncCallback<List<Photo>> () {

			public void onFailure(Throwable caught) {
				Window.alert ( "Failed " + caught );
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

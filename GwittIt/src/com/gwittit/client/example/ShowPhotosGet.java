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
import com.gwittit.client.Config;
import com.gwittit.client.facebook.ApiFactory;
import com.gwittit.client.facebook.FacebookApi;
import com.gwittit.client.facebook.entities.Photo;
import com.gwittit.client.facebook.xfbml.FbName;
import com.gwittit.client.facebook.xfbml.FbPhoto;
import com.gwittit.client.facebook.xfbml.Xfbml;
import com.gwittit.client.facebook.xfbml.FbPhoto.Size;

public class ShowPhotosGet extends Example {

	final FacebookApi apiClient = ApiFactory.newApiClient(Config.API_KEY);
	
	final HorizontalPanel paramsWrapper = new HorizontalPanel ();
	final VerticalPanel resultWrapper = new VerticalPanel ();
	final VerticalPanel paramsResultWrapper = new VerticalPanel ();
	

	@Override
	public String getHeader() {
		// TODO Auto-generated method stub
		return "Photos.get";
	}
	
	public String getDescription () {
		return "Executes photos.get  with param uid" ;
	}
	
	public ShowPhotosGet () {
		
		paramsWrapper.addStyleName("params");
		paramsResultWrapper.add ( paramsWrapper );
		paramsResultWrapper.add ( resultWrapper );
		
		render ();
		initWidget ( paramsResultWrapper );
	}
	
	
	private void render () {
		
		paramsWrapper.add(getLoader() );
		
		Map<String,String> params = new HashMap<String,String> ();
		
		apiClient.friends_get(params, new AsyncCallback<List<Long>> () {

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			public void onSuccess(List<Long> result) {
				
			    final ListBox dropBox = new ListBox(false);
			    dropBox.getElement().setId( "dropBox");
				for ( Long uid : result ) {
				
					dropBox.addItem ( uid + "" );
				}
				
				paramsWrapper.clear ();
				paramsWrapper.add ( new HTML ( "Choose Friend" ) );
				paramsWrapper.add(dropBox);

				Button b = new Button ( "Go");
				paramsWrapper.add ( b );
				b.addClickHandler(new ClickHandler () {

					public void onClick(ClickEvent event) {
						renderPhotos ( dropBox.getValue( dropBox.getSelectedIndex() ) ) ;
					}
					
				});
			}
			
		});		
		
	}
	
	private void renderPhotos ( final String uid ) {
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

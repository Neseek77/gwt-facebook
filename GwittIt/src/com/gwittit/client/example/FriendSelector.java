package com.gwittit.client.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwittit.client.Config;
import com.gwittit.client.facebook.ApiFactory;
import com.gwittit.client.facebook.FacebookApi;

/**
 * Class that let the user select a friend. Used to make the examples a little
 * more exiting.
 *
 */
public class FriendSelector extends Composite {

	private VerticalPanel outer = new VerticalPanel ();
	
	interface FriendSelectionHandler {
		void onSelected ( Long uid );
	}

	private FacebookApi apiClient = ApiFactory.newApiClient(Config.API_KEY);

	private FriendSelectionHandler friendSelection;
	
	private Image loader = new Image ( "/loader.gif" );
	
	/**
	 * Create new instance
	 */
	public FriendSelector () {
		
		outer.add ( loader );
		
		Map<String,String> params = new HashMap<String,String> ();
		
		apiClient.friends_get(params, new AsyncCallback<List<Long>> () {

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			public void onSuccess(List<Long> result) {
				
				outer.remove( loader );
			    final ListBox dropBox = new ListBox(false);
			    dropBox.getElement().setId( "dropBox");
				for ( Long uid : result ) {
				
					dropBox.addItem ( uid + "" );
				}
				
				outer.clear ();
				outer.add ( new HTML ( "Choose Friend" ) );
				outer.add(dropBox);

				Button b = new Button ( "Go");
				outer.add ( b );
				b.addClickHandler(new ClickHandler () {

					public void onClick(ClickEvent event) {
						friendSelection.onSelected( new Long ( dropBox.getValue( dropBox.getSelectedIndex() ) ) );
					}
					
				});
			}
			
		});		
		
		initWidget ( outer );
	}
	
	public void addFriendSelectionHandler ( FriendSelectionHandler handler ) {
		this.friendSelection = handler;
	}
}

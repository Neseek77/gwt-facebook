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
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwittit.client.Config;
import com.gwittit.client.facebook.ApiFactory;
import com.gwittit.client.facebook.FacebookApi;
import com.gwittit.client.facebook.FacebookException;
import com.gwittit.client.facebook.entities.User;
import com.gwittit.client.facebook.ui.ErrorResponseUI;

/**
 * Class that let the user select a friend. Used to make the examples a little
 * more exiting.
 *
 */
public class FriendSelector extends Composite {

	private HorizontalPanel outer = new HorizontalPanel ();
	
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
		
	    outer.setSpacing ( 10 );
		outer.add ( loader );
				
		apiClient.friends_getExtended (  new AsyncCallback<List<User>> () {

			public void onFailure(Throwable caught) {
			    FacebookException fe = ( FacebookException ) caught;
			    ErrorResponseUI ui = new ErrorResponseUI ( fe.getErrorMessage () );
			    ui.center ();
			    ui.show ();
			}

			public void onSuccess(List<User> result) {
				
				outer.remove( loader );
			    final ListBox dropBox = new ListBox(false);
			    dropBox.getElement().setId( "dropBox");
				for ( User user : result ) {
					dropBox.addItem ( user.getName (), user.getUidString () );
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

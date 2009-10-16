package com.gwittit.client.facebook.ui;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwittit.client.facebook.xfbml.FbName;
import com.gwittit.client.facebook.xfbml.FbProfilePic;
import com.gwittit.client.facebook.xfbml.Xfbml;
import com.gwittit.client.facebook.xfbml.FbProfilePic.Size;

/**
 * Let user browse a user list in a popup.
 * 
 * CSS Configuration
 * 
 * <ul>
 *  <li>.gwittit-ProfilePicsPopup
 *  <li>.gwittit-ProfilePicsPopup-content
 *  <li>.gwittit-ProfilePicsPopup-moreButton
 * </ul>
 */
public class ProfilePicsPopup extends DecoratedPopupPanel {

    /*
     * Main Content
     */
    private final VerticalPanel outer = new VerticalPanel ();
    
    /*
     * Intern Scrollpanel
     */
    private final ScrollPanel scrollPanel = new ScrollPanel ();
    
    /*
     * Hold pics
     */
    private final VerticalPanel picsHolder = new VerticalPanel ();
    
    /*
     * Display N profile in the list each time
     */
    private final int PAGE_SIZE = 30;
    
    /*
     * Initial page to display.
     */
    private int page = 0;
    
    /*
     * More button
     */
    private final Button moreButton = new Button ( "More" );

    /*
     * Internal user list
     */
    private List<Long> uids;
    
    public ProfilePicsPopup ( List<Long> uids ) {
        
        this.uids = uids;
        
        super.setAutoHideEnabled ( true );
        
        outer.addStyleName ( "gwittit-ProfilePicsPopup" );
        moreButton.addStyleName ( "gwittit-ProfilePicsPopup-moreButton" );

        // ScrollPanel
        scrollPanel.addStyleDependentName ( "gwittit-ProfilePicsPopup-scrollPanel" );
        scrollPanel.setWidth ( "400px" );
        scrollPanel.setHeight ( "500px" );
        
        
        // Main Content
        outer.getElement ().setId ("gwittit-ProfilePicsPopup-content" );
        outer.addStyleName ( "gwittit-ProfilePicsPopup-content" );
        
        
        displayUsers ( picsHolder, page );
            
        // Compile Panels
        outer.add ( picsHolder );
        outer.add ( moreButton );
        scrollPanel.add ( outer );
        
        super.setWidget ( scrollPanel );
        
        Xfbml.parse ( outer );

        // Add Clickhandler that enables user to browse list 
        moreButton.addClickHandler ( new ClickHandler () {
            public void onClick(ClickEvent event) {
                displayUsers ( picsHolder, ++page );
            } 
        });
    }
    
    private void displayUsers ( VerticalPanel picsList, int page ) {
        
        int startIdx = page * PAGE_SIZE;
        int stopIdx = startIdx + (PAGE_SIZE - 1);
            
        final VerticalPanel holder = new VerticalPanel ();
        holder.getElement ().setId ( "ProfilePics-Page_" + page  );

        for ( int i = startIdx ; i < uids.size () && i < stopIdx; i++ ) {
            Long uid = uids.get ( i );
            HorizontalPanel wrapper = new HorizontalPanel ();
            wrapper.setSpacing ( 10 );
            FbProfilePic profilePic = new FbProfilePic ( uid, Size.square );
            FbName name = new FbName ( uid );
            wrapper.add ( profilePic );
            wrapper.add ( name );
            holder.add ( wrapper );
        }
        picsList.add ( holder );
        Xfbml.parse ( holder );
    }
}

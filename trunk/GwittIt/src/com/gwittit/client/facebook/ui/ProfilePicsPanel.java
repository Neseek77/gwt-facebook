package com.gwittit.client.facebook.ui;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwittit.client.facebook.xfbml.FbName;
import com.gwittit.client.facebook.xfbml.FbProfilePic;
import com.gwittit.client.facebook.xfbml.Xfbml;
import com.gwittit.client.facebook.xfbml.FbProfilePic.Size;

/**
 * Display Profile Pics in a panel.
 * 
 * CSS Configuration.
 * 
 * <ul>
 *  <li>.gwittit-ProfilePicsPanel
 *  <li>.gwittit-ProfilePicsPanel-pics
 * </ul>
 *
 */
public class ProfilePicsPanel extends Composite {
    
    /**
     * Put everything here
     */
    private VerticalPanel outer = new VerticalPanel ();
    
    /**
     * Go with the flow, 
     */
    private FlowPanel pics = new FlowPanel ();

    /**
     * Let user browse more members
     */
    private Anchor seeAllLink = new Anchor ( "See All" );

    /**
     * Display N profiles
     */
    private final int PAGE_SIZE = 10;
    
    /**
     * List of uids that we get in the constructor
     */
    private List<Long> uids;

    
    
    /**
     * Create a new Panel
     */
    public ProfilePicsPanel ( final List<Long> uids ) {
        
        this.uids = uids;
        
        outer.getElement ().setId ( "ProfilePicsPanel" );
        pics.getElement ().setId ( "ProfilePicsPanel-pics-" + System.currentTimeMillis () );
        outer.addStyleName ( "gwittit-ProfilePicsPanel" );
        pics.addStyleName ( "gwittit-ProfilePicsPanel-pics" );
     
        outer.add ( pics );
        // Add list of fbprofilepics to the pics panel
        renderProfilePics ();
        
        if ( uids.size () > PAGE_SIZE ) {
            outer.add ( seeAllLink );
        }
        
        seeAllLink.addClickHandler ( new ClickHandler () {
            public void onClick(ClickEvent event) {
                ProfilePicsPopup popup = new ProfilePicsPopup ( uids );
                popup.center ();
                popup.show ();
            }
        });
        
        Xfbml.parse ( pics );
        initWidget ( outer );
    }
    
    private void renderProfilePics ( ) {
        
        for ( int i = 0; i < PAGE_SIZE && i < uids.size (); i++ ) {
            Long uid = uids.get ( i );
            FbProfilePic profilePic = new FbProfilePic ( uid, Size.square );
            profilePic.setWidth ( "35px" );
            profilePic.setHeight ( "35px" );
            pics.add ( profilePic );
        }
        
    }
}

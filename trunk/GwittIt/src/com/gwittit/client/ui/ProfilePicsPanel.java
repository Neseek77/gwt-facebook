package com.gwittit.client.ui;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
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
    private Anchor moreLink = new Anchor ( "See All" );
    
    
    private int startIdx;
    
    private int limit = 10;
    
    
    private List<Long> uids;
    
    public ProfilePicsPanel ( final List<Long> uids ) {
        
        this.uids = uids;
        
        outer.getElement ().setId ( "ProfilePicsPanel" );
        pics.getElement ().setId ( "ProfilePicsPanel-pics-" + System.currentTimeMillis () );
        
        outer.addStyleName ( "gwittit-ProfilePicsPanel" );
        pics.addStyleName ( "gwittit-ProfilePicsPanel-pics" );
     
        displayProfilePics ();
        
        outer.add ( pics );
        outer.add ( moreLink );
 
        
        moreLink.addClickHandler ( new ClickHandler () {
            public void onClick(ClickEvent event) {
                showAll();
            }
        });
        Xfbml.parse ( pics );
        initWidget ( outer );
    }
    
    private void showAll () {
        
        DecoratedPopupPanel pop = new DecoratedPopupPanel();
        pop.setAutoHideEnabled ( true );
        
        pop.addStyleName ( "gwittit-ProfilePicsPanel-popup" );
        
        ScrollPanel scrollPanel = new ScrollPanel ();
        scrollPanel.addStyleDependentName ( "gwittit-ProfilePicsPanel-scrollPanel" );
        
        scrollPanel.setWidth ( "400px" );
        scrollPanel.setHeight ( "500px" );
        
        VerticalPanel picsList = new VerticalPanel ();
        picsList.getElement ().setId ("gwittit-ProfilePicsPanel-content" );
        picsList.addStyleName ( "gwittit-ProfilePicsPanel-content" );
        
        for ( Long uid : uids ) {
    
            HorizontalPanel wrapper = new HorizontalPanel ();
            wrapper.setSpacing ( 10 );
            FbProfilePic profilePic = new FbProfilePic ( uid, Size.square );
            FbName name = new FbName ( uid );
            wrapper.add ( profilePic );
            wrapper.add ( name );
            picsList.add ( wrapper );
        }
        scrollPanel.add ( picsList );
        pop.setWidget ( scrollPanel );
        pop.center ();
        pop.show ();
        
        Xfbml.parse ( picsList );
    }
    
    private void displayProfilePics ( ) {
        
        for ( int i = 0; i < limit; i++ ) {
            Long uid = uids.get ( i );
            FbProfilePic profilePic = new FbProfilePic ( uid, Size.square );
            profilePic.setWidth ( "35px" );
            profilePic.setHeight ( "35px" );
            pics.add ( profilePic );
        }
        
        Xfbml.parse ( pics );
    }
}

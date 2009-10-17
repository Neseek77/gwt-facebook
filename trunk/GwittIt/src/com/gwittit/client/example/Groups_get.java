package com.gwittit.client.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.facebook.entities.Group;
import com.gwittit.client.facebook.entities.GroupMembers;
import com.gwittit.client.facebook.ui.ProfilePicsPanel;

/**
 * Showcase for method <code>groups.get</code>
 */
public class Groups_get extends Showcase {

    public Groups_get () {
        super ( "groups.get, groups.getMembers" );
    }
    
    @Override
    public Widget createWidget () {
        
        
        final VerticalPanel outer = new VerticalPanel ();
        outer.addStyleName ( "gwittit-Showcase-Groups_get" );
        
        addLoader ( outer );
        
        apiClient.groups_get ( null, new AsyncCallback<List<Group>> () {
            public void onFailure(Throwable caught) {
                removeLoader ( outer );
                handleFailure ( caught );
            }

            public void onSuccess(List<Group> groups) {
                removeLoader ( outer );
                for ( final Group g : groups ) {
                    final VerticalPanel membersWrapper = new VerticalPanel ();
                    membersWrapper.addStyleName ( "membersWrapper" );
                    
                    Anchor memberLink = new Anchor ( "See Members" );
                    memberLink.addClickHandler ( new ClickHandler() {
                        public void onClick(ClickEvent event) {
                            displayMembers ( membersWrapper, g.getGid () );
                        }
                        
                    });
                    outer.add ( new HTML ( "<h4>Group: " + g.getName () + "</h4>") ) ;
                    outer.add (memberLink );
                    outer.add ( membersWrapper );
                }
            }
        });
        return outer;
    }

    /**
     * Display members in a group
     */
    private void displayMembers ( final VerticalPanel membersWrapper, final Long gid ) {

        addLoader ( membersWrapper );
        // Get members in this group
        apiClient.groups_getMembers ( gid, new AsyncCallback<GroupMembers> () {

            public void onFailure(Throwable caught) {
                Groups_get.this.handleFailure ( caught );
            }

            public void onSuccess(GroupMembers groupMembers) {
                removeLoader ( membersWrapper );
                displayProfilePicsPanel ( membersWrapper, "Members", groupMembers.getMembers () );
                displayProfilePicsPanel ( membersWrapper, "Admins", groupMembers.getAdmins () );
                displayProfilePicsPanel ( membersWrapper, "Officers " , groupMembers.getOfficers () );
                displayProfilePicsPanel ( membersWrapper, "Not Replied" , groupMembers.getNotReplied () );

            }
            
        });
            
    }
    
    private void displayProfilePicsPanel ( VerticalPanel wrapper, String header, List<Long> uids ) {
        if ( uids.size () > 0 ) {
            wrapper.add (  new HTML ( "<h3>" + header + "</h3>" )  );
            wrapper.add ( new ProfilePicsPanel ( uids ) );
        }
    }
}

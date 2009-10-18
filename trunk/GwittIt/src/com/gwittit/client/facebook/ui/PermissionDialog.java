package com.gwittit.client.facebook.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.facebook.FacebookApi;
import com.gwittit.client.facebook.FacebookConnect;
import com.gwittit.client.facebook.FacebookApi.Permission;

/**
 * Display permission dialog to user.
 */
public class PermissionDialog extends Composite {

    private VerticalPanel outer = new VerticalPanel ();
    
    public interface PermissionHandler {
        void onPermissionChange ( Boolean granted );
    }
    
    private PermissionHandler handler  = null;
    
    private FacebookApi apiClient;
    
    private HTML loader = new HTML ( "Checking permission");
    /**
     * Create a new PermissionDialog
     * 
     * @param apiClient Facebook Api inject
     * @param permission to get
     * @param handler to execute callback
     * 
     */
    public PermissionDialog ( FacebookApi apiClient, final Permission permission, final PermissionHandler handler ) {
        
        this.apiClient = apiClient;
        this.handler = handler;
        
        // First check if user has the permission.
        
        outer.add ( loader );
        apiClient.users_hasAppPermission ( permission, new AsyncCallback<Boolean> () {

            public void onFailure(Throwable caught) {
                new ErrorResponseUI ( caught ).center ();
            }

            public void onSuccess(Boolean hasPermission) {
                outer.remove ( loader );
                if ( hasPermission ) {
                    handler.onPermissionChange ( true );
                } else {
                    outer.add ( createShowPermissionUI ( permission ) );
                }
            }
            
        });
        
    
        initWidget ( outer );
    }
    
    
    private Widget createShowPermissionUI ( final Permission permission ) {
        
        Anchor a = new Anchor ( "Grant this application " + permission.toString () + " permission " );
        
        a.addClickHandler ( new ClickHandler () {

            public void onClick(ClickEvent event) {
                FacebookConnect.showPermissionDialog ( permission, new AsyncCallback<Boolean> () {
                  public void onFailure(Throwable caught) {
                        new ErrorResponseUI ( caught ).center ();
                    }

                    public void onSuccess(Boolean result) {
                        if ( handler != null ) {
                            handler.onPermissionChange ( result );
                        }
                    }
                    
                });
            }
            
        });
           
        return a;
    }
    
    public void addPermissionHandler ( PermissionHandler handler ) {
        this.handler = handler;
    }
    
}

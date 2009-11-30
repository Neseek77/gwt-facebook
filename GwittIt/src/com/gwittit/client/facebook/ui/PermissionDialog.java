package com.gwittit.client.facebook.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.facebook.ApiFactory;
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
    
    private FacebookApi apiClient = ApiFactory.getInstance ();
    
    private HTML loader = new HTML ( "Checking permission");
 
    private String message;
    
    /**
     * Create a new PermissionDialog
     */
    public PermissionDialog () {
        
        initWidget ( outer );
    }
    
    public PermissionDialog ( String message ) {
        initWidget ( outer );
        this.message = message;
    }


    private Widget createShowPermissionUI ( final Permission permission ) {
        
        Anchor a = new Anchor ();
        if ( message != null ) {
            a.setHTML ( "<h3>" + message + "</h3>" );
        } else {
            a.setHTML ("<h3>Grant  " + permission.toString () + " permission</h3>" );
        }
        a.addStyleName ( "clickable" );
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
    
    public void checkPermission ( final Permission permission ) {
        outer.clear ();
        loader.setHTML ( "Checking " + permission.toString () + " permission " );
        outer.add ( loader );
        
        // Check if user has the right permission. If not show permission dialog
        // and return if user granted us given permission.
        apiClient.usersHasAppPermission ( permission, new AsyncCallback<Boolean> () {
            
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
    }

    public void addPermissionHandler ( PermissionHandler handler ) {
        this.handler = handler;
    }
    
}

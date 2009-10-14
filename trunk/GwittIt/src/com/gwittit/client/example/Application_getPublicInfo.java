package com.gwittit.client.example;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.facebook.Callback;
import com.gwittit.client.facebook.FacebookApi.ApplicationPublicInfoParams;
import com.gwittit.client.facebook.entities.ApplicationPublicInfo;

/**
 * Showcase for method <code>application.getPublicInfo</code>
 */
public class Application_getPublicInfo extends Showcase {

    
    @Override
    public Widget createWidget () {
        
        final VerticalPanel outer = new VerticalPanel ();
        addLoader ( outer );
    
        Map<Enum<ApplicationPublicInfoParams>,String> params = new HashMap<Enum<ApplicationPublicInfoParams>,String> ();
        params.put ( ApplicationPublicInfoParams.application_id, "32357027876" );

        apiClient.application_getPublicInfo ( params, new Callback<ApplicationPublicInfo> () {
            @Override
            public void onFailure(Throwable caught) {
                handleFailure ( caught );
            }
            @Override
            public void onSuccess(ApplicationPublicInfo result) {
                removeLoader ( outer );
                
                String appInfoString = "ApiKey: " + result.getApiKey () + "<br/>" +
                                       "Name: " + result.getCanvasName ();
                
                outer.add ( new HTML ( appInfoString ) ); 
            }
        });
        return outer;
    }
    
}

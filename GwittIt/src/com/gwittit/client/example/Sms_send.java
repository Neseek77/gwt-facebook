package com.gwittit.client.example;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class Sms_send extends Showcase {

    /**
     * Callback for checking if we can send sms.
     */
    private class SmsCanSendCallback implements AsyncCallback<Integer> {
        public void onFailure(Throwable caught) {
            handleFailure ( caught );
        }
        public void onSuccess(Integer result) {
            if ( result.equals ( 0 ) ) {
                displaySendSms ();
            } else {
                displayCannotSendSms ( result );
            }
        }
    };

    private VerticalPanel outer;
    
    public Sms_send () {
        super ( "sms.send" );
    }
    
    
    public Widget createWidget () {
        outer = new VerticalPanel ();
        outer.add ( new HTML ( "test" ) );

        apiClient.smsCanSend ( null, new SmsCanSendCallback() );
        
        return outer;
    }
    
    private void displaySendSms () {
        outer.add ( new HTML ( "display send sms" ) );
    }
    
    private void displayCannotSendSms ( Integer responseCode ) {
        outer.add ( new HTML ( "Cannot send sms, response code: " + responseCode ) ) ;
    }
}

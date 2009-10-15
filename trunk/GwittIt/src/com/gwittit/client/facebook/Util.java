package com.gwittit.client.facebook;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JsArrayNumber;
import com.google.gwt.i18n.client.NumberFormat;

public class Util {

    
    /**
     * Convert JsArrayNumber to List<Long>
     */
    public static List<Long> convertNumberArray ( JsArrayNumber jsArray ) {
        List<Long> result = new ArrayList<Long> ();

        for (int i = 0; i < jsArray.length (); i++) {
            NumberFormat fmt = NumberFormat.getFormat ( "0" );
            double friendIdDbl = jsArray.get ( i );
            Long l = Long.parseLong ( fmt.format ( friendIdDbl ) );
            result.add ( l );
        }
        return result;
        
    }
}

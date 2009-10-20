package com.gwittit.client.facebook;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayNumber;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONString;


/*
 * Generic util class
 */
public class Util {

    /**
     * Convert JsArrayNumber to List<Long>
     */
    public static List<Long> convertNumberArray(JsArrayNumber jsArray) {
        List<Long> result = new ArrayList<Long> ();

        for (int i = 0; i < jsArray.length (); i++) {
            NumberFormat fmt = NumberFormat.getFormat ( "0" );
            double friendIdDbl = jsArray.get ( i );
            Long l = Long.parseLong ( fmt.format ( friendIdDbl ) );
            result.add ( l );
        }
        return result;

    }

    public static List<Long> convertStringArray(JsArrayString jsArray) {
        List<Long> result = new ArrayList<Long> ();

        for (int i = 0; i < jsArray.length (); i++) {
            result.add ( new Long ( jsArray.get ( i ) ) );
        }
        return result;

    }

    public static JSONArray toJSONArray (List<Long> longs) {
        JSONArray ja = new JSONArray ();
        for (int i = 0; i < longs.size (); i++) {
            ja.set ( i, new JSONNumber ( longs.get ( i ) ) );
        }
        return ja;
    }
    
    public static JSONString toJSONString ( List<Long> longs ) {
        return new JSONString ( toJSONArray ( longs ).toString () );
    }
    
    public static <T extends JavaScriptObject> List<T> iterate ( JsArray<T> array ) {
        List<T> iterateList = new ArrayList<T> ();
        
        for ( int i = 0 ; i < array.length (); i++ ) {
            iterateList.add ( array.get ( i ) );
        }
        return iterateList;
    }
}

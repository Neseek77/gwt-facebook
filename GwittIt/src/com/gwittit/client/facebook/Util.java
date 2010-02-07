package com.gwittit.client.facebook;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayNumber;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;


/*
 * Generic util class
 */
public class Util {
    
    public static String getCommaSepString(List objs) {
        StringBuilder sb = new StringBuilder();
        for (Object o : objs) {
           sb.append(o);
           sb.append(",");
        }
        return sb.length()==0 ? "" :  sb.substring(0, sb.length()-1);
     }


    /**
     * Convert JsArrayNumber to List<Long>
     */
    public static List<Long> convertNumberArray(JsArrayNumber jsArray) {
        List<Long> result = new ArrayList<Long> ();

        try {
            for (int i = 0; i < jsArray.length (); i++) {
                NumberFormat fmt = NumberFormat.getFormat ( "0" );
                double friendIdDbl = jsArray.get ( i );
                Long l = Long.parseLong ( fmt.format ( friendIdDbl ) );
                result.add ( l );
            }
        } catch ( Exception e ) {
            GWT.log ( "Failed to convert String array: ArrayLength: " + jsArray.length () + ":"  + new JSONObject ( jsArray ).toString () , e);
        }
        return result;

    }

    public static List<Long> convertStringArray(JsArrayString jsArray) {
        List<Long> result = new ArrayList<Long> ();

        try {
            for (int i = 0; i < jsArray.length (); i++) {
                result.add ( new Long ( jsArray.get ( i ) ) );
            }
        } catch ( Exception e ) {
            GWT.log ( "Failed to convert String array " , e );
            
        }
        return result;

    }

    /**
     * Convert 
     * @param longs
     * @return
     */
    public static JSONArray toJSONArray (List<Long> longs) {
        JSONArray ja = new JSONArray ();
        for (int i = 0; i < longs.size (); i++) {
            ja.set ( i, new JSONNumber ( longs.get ( i ) ) );
        }
        return ja;
    }
    
    /**
     * Convert list of long values to json array. 
     */
    public static JSONString toJSONString ( List<Long> longs ) {
        return new JSONString ( toJSONArray ( longs ).toString () );
    }
    
    /**
     * Method for iterating a JsArray<T> array
     * @param <T>
     * @param array
     * @return
     */
    public static <T extends JavaScriptObject> List<T> iterate ( JsArray<T> array ) {
        List<T> iterateList = new ArrayList<T> ();
        
        try {
            for ( int i = 0 ; i < array.length (); i++ ) {
                iterateList.add ( array.get ( i ) );
            }
                
        } catch ( Exception e ) {
            GWT.log (" Failed to iterate JsArray, maybe empty array?"  + array, e );
        }
        return iterateList;
    }
}

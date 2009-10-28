package com.gwittit.client.facebook;

import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

/**
 * Json Helper.  Lets you chain a json object
 * 
 * Like this: JavaScriptObject o = Json.newInstance().put("name", "value").put("name2", "value2).getJavaScriptObject();
 */
public class Json {
    
    JSONObject o ;
    
    public Json ( JSONObject o ) {
        this.o = o;
    }
    
    public Json  ( JavaScriptObject o ) {
        this ( new JSONObject ( o ) );
    }
    
    
    public Json () {
        this ( new JSONObject () );
    }
    
    public static Json newInstance () {
        Json j = new Json ( new JSONObject () );
        return j;
    }
    
    public Json remove ( String name ) {
        o.put ( name , null );
        return this;
    }
    public Json put ( String name, Boolean value ) {
        if ( value != null ) {
            o.put ( name, new JSONNumber ( value ? 1 : 0 ) );
        }
        return this;
    }
    
    public  Json put (String name, String value ) {
        if ( value != null ) {
            o.put ( name,new JSONString ( value ) );
        }
        return this;
    }
    
    public  Json put ( String name, Long value ) {
        if ( value != null ) {
            o.put ( name, new JSONNumber ( value ) );
        }
        return this;
    }
    
    public Json put ( String name, Integer value ) {
        if ( value != null ) {
            o.put ( name, new JSONNumber ( value ) );
        }
        return this;
    }

    
    public <T extends JavaScriptObject> Json putlist ( String name, List<T> ts ) {
        
        if ( ts == null ) {
            return null;
        }
     
        JSONArray a = new JSONArray();

        for ( int i = 0 ; i < ts.size (); i++ ) {
            JavaScriptObject j = (JavaScriptObject)ts.get(i);
            a.set ( i, new JSONObject ( j ) ) ;
        }
        o.put ( name, a );
        return this;
    }
    
    public <T extends JavaScriptObject> Json put ( String name, T t ) {
        
        if ( t == null ) {
            return this;
        }
        
        JavaScriptObject j = (JavaScriptObject)t;
        JSONObject o = new JSONObject ( j );
        o.put ( name, o );
        return this;
    }
    
    public Json puts ( String name, List<String> value ) {
        if ( value != null ) {
            JSONArray a = new JSONArray();
            
            for ( int i = 0 ; i < value.size (); i ++ ) {
                a.set ( i, new JSONString ( value.get ( i ) ) );
            }
            o.put ( name, a );
        }
        return this;
    }
    
    public Json  put ( String name, List<Long> value ) {
        if ( value != null ) {
            o.put ( name,  Util.toJSONString ( value ) );
        }
        return this;
    }
 
    public String toString () {
        return o.toString ();
    }
    
    public JSONObject getWrappedObject () {
        return o;
    }
    
    public JavaScriptObject getJavaScriptObject () {
        return o.getJavaScriptObject ();
    }
}

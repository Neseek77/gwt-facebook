package com.gwittit.client.facebook;

import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.Window;

/**
 * Json Helper.  Lets you chain a json object
 * 
 * Like this: JavaScriptObject o = new Json().put("name", "value").put("name2", "value2).getJavaScriptObject();
 */
public class Json {
    
    JSONObject internObject ;
    
    public Json ( JSONObject o ) {
        this.internObject = o;
    }
    
    public Json  ( JavaScriptObject o ) {
        this ( new JSONObject ( o ) );
    }
    
    
    public Json () {
        this ( new JSONObject () );
    }
      
    public Json remove ( String name ) {
        internObject.put ( name , null );
        return this;
    }
    public Json put ( String name, Boolean value ) {
        if ( value != null ) {
            internObject.put ( name, new JSONNumber ( value ? 1 : 0 ) );
        }
        return this;
    }
    
    public  Json put (String name, String value ) {
        if ( value != null ) {
            internObject.put ( name,new JSONString ( value ) );
        }
        return this;
    }
    
    public  Json put ( String name, Long value ) {
        if ( value != null ) {
            internObject.put ( name, new JSONNumber ( value ) );
        }
        return this;
    }
    
    public Json put ( String name, Integer value ) {
        if ( value != null ) {
            internObject.put ( name, new JSONNumber ( value ) );
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
        internObject.put ( name, a );
        return this;
    }
    
    public <T extends JavaScriptObject> Json put ( String name, T t ) {
        if ( t == null ) {
            return this;
        }
        
        JavaScriptObject j = (JavaScriptObject)t;
        this.internObject.put ( name,  new JSONObject ( j ) );
        
        return this;
    }
    
    public Json puts ( String name, List<String> value ) {
        if ( value != null ) {
            JSONArray a = new JSONArray();
            
            for ( int i = 0 ; i < value.size (); i ++ ) {
                a.set ( i, new JSONString ( value.get ( i ) ) );
            }
            internObject.put ( name, a );
        }
        return this;
    }
    
    /**
     * Put any string as commaseparated value
     */
    public Json putAsCommaSep ( String name, List value ) {
        if ( value != null ) {
            internObject.put ( name, new JSONString ( Util.getCommaSepString ( value ) ) );
        }
        return this;
    }
    
    public Json  put ( String name, List<Long> value ) {
        if ( value != null ) {
            internObject.put ( name,  Util.toJSONString ( value ) );
        }
        return this;
    }
 
    public String toString () {
        return internObject.toString ();
    }
    
    public JSONObject getWrappedObject () {
        return internObject;
    }
    
    public JavaScriptObject getJavaScriptObject () {
        return internObject.getJavaScriptObject ();
    }
    
}

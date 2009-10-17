package com.gwittit.client.facebook.entities;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;

/**
 * This class is a hack. Let object extend JavaScriptObject to make parsing
 * more effective.
 * @author ola
 *
 *@deprecated User com.gwittit.client.facebook.Json
 */
public class JsonUtil {
	
	
	public static void debug ( JSONValue o ) {
	
		if ( o.isArray() != null ) {
			Window.alert ( "json array" );
		} else if ( o.isBoolean() != null ) {
			Window.alert( "json boolean" );
		} else if ( o.isNull() != null ) {
			Window.alert ( "json null");
		} else if ( o.isNumber() != null ) {
			Window.alert ( "json number" );
		} else if ( o.isString() != null ) {
			Window.alert ( "json string" );
		} else if ( o.isObject() != null ) {
			Window.alert ( "json object" );
		} else {
			Window.alert ("Unkown json: o.size="  + o );
		}
	}
	
	
	public static String getString ( JSONObject o , String key ) {
		if ( o == null ) {
			return null;
		}
		if ( o.get(key) != null ) {
			if ( o.get(key).isString() != null )  {
				return o.get(key).isString().stringValue();
			} else if ( o.get(key).isNumber() != null )  {
				return "" + o.get(key).isNumber().doubleValue();
			}
		} 
		return null;
		
	}
	
	public static boolean getBoolean ( JSONObject o, String key ) {
		
		if ( o.get ( key ) == null ) {
			return false;
		}
		
		JSONBoolean b = o.get(key).isBoolean();
		
		if ( b != null ) {
			return b.booleanValue();
		}
		
		String s = getString ( o , key );
		if ( s != null ) {
			return new Boolean ( s );
		}
		return false;
	}
	
	
	public static Integer getInt ( JSONObject o , String key ) {
		if ( getLong ( o, key ) != null ) {
			return getLong ( o, key).intValue();
		}
		
		return null;
	}
	public static Long getLong ( JSONObject o, String key ) {
	
		if ( o == null || key == null ) {
			return null;
		}
		JSONValue v = o.get(key);
		
		if ( v == null ) {
			return null;
		}
		JSONNumber number = o.get(key).isNumber();
		
		if ( number != null ) {
			return new Long ( ""+ number);
		}
		
		JSONString string  = o.get(key).isString();
		
		if ( string != null ) {
			return new Long ( string.stringValue() );
		}
		
		
		return null;
	}
	
	public static Date getDate ( JSONObject o, String key ) {
		
		Long t = getLong ( o, key );
		if ( t == null ) {
			return null;
		}
		Date date = new Date ();
		date.setTime(t);
		return date;
	}
}

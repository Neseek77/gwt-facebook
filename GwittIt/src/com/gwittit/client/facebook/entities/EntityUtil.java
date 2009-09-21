package com.gwittit.client.facebook.entities;

import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Window;

public class EntityUtil {
	
	
	public static void debug ( JSONObject o ) {
	
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
			Window.alert ("Unkown json: o.size="  + o.size() );
		}
	}
	
	
	public static String getString ( JSONObject o , String key ) {
		
		if ( o.get(key).isString() != null ) {
			return o.get(key).isString().stringValue();
		} 
		return null;
		
	}
	
	public static Integer getInt ( JSONObject o , String key ) {
		if ( getLong ( o, key ) != null ) {
			return getLong ( o, key).intValue();
		}
		
		return null;
	}
	public static Long getLong ( JSONObject o, String key ) {
		
		JSONNumber number = o.get(key).isNumber();
		
		if ( number != null ) {
			return new Long ( ""+ number);
		}
		
		return null;
	}
}

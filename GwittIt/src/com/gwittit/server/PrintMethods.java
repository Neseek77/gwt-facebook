package com.gwittit.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.regex.Pattern;

import com.google.gwt.core.client.JsArrayNumber;
import com.gwittit.client.facebook.entities.*;

public class PrintMethods {
    
    static void printsampleupload () throws Exception {
        
        File file = new File ("/Users/ola/Documents/Source/GwittIt/war/sampleupload.jpg");
        
        
        FileInputStream is = new FileInputStream ( file );
        
        byte[] b = new byte[is.available ()];
        
        is.read ( b );
        
        System.out.println ( new String ( b ) );
        
    }
    
	public static void main ( String[] args ) throws Exception {
		
	    printsampleupload ();
	    
	    Class c = ActionLink.class;
		
		Field fields[] = c.getFields ();
		
		
		for ( Field f : fields ) {
		    String methodName = up1 ( f.getName () );
		    
		    if ( f.getType () == java.lang.Long.class || f.getType() == java.lang.Integer.class ) {
                System.out.println ( "public final native String get" + methodName + "String() /*-{ return this." +  convertToCamelCase (f.getName()) + " + \"\"; }-*/;" );
                System.out.println ( "public final " + f.getType () + " get" + methodName + "() { return new Long ( get" + methodName + "String() ); }" );
		        
		    } else if ( f.getType() == JsArrayNumber.class ) {
                System.out.println ( "public final native " + f.getType () + " get" + methodName + "Native() /*-{ return this." +  convertToCamelCase (f.getName()) + " + \"\"; }-*/;" );
                System.out.println ( "public final List<Long> get" + methodName + "() { return Util.convertNumberArray ( get" + methodName + "Native() ); }" );

		    } else {
		        System.out.println ( "public final native " + f.getType ().getSimpleName ()  + " get" + methodName + "() /*-{ return this." +  convertToCamelCase (f.getName()) + "; }-*/;" );
		    }
		}
		

		
		System.out.println ( "public static native " +c.getSimpleName () + " fromJson(String jsonString) /*-{ return eval('(' + jsonString + ')');}-*/;");
		
	}
	
	private static String convertToCamelCase(String cn) {

	    Pattern p =  Pattern.compile ( "[A-Z]");
	    StringBuilder tmp = new StringBuilder();

	    for ( int i = 0 ; i < cn.length (); i++ ) {
	        if ( Pattern.matches ( "[A-Z]", "" + cn.charAt ( i ) ) )  {
	            
	            tmp.append ( "_" );
	            tmp.append ( (""+cn.charAt ( i )).toLowerCase () );
	        }
	        else {
	            tmp.append ( cn.charAt ( i ) );
	        }
	    }
	    return tmp.toString ();
    }

	public static String up1(String s) {
	    
	    
	    return (s.length()>0)? Character.toUpperCase(s.charAt(0))+s.substring(1) :
	    s;
	 }
}

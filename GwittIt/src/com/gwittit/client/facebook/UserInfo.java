package com.gwittit.client.facebook;

import java.io.Serializable;
import java.util.Date;

import com.google.gwt.user.client.Cookies;
import com.gwittit.client.Config;


public class UserInfo implements Serializable {

	final static String APP_KEY = Config.API_KEY;
	
	final static String C_UID =  APP_KEY  + "_user";
	
	final static String C_SESSION_KEY =  APP_KEY + "_session_key";
	

	public static boolean isFacebookUser () {
		return Cookies.getCookie ( C_UID ) != null;
	}
	
	public static boolean isLoggedIn() {
		//return true;
		return Cookies.getCookie(C_UID) != null;
	}

	
	public static String getUid () {
		return Cookies.getCookie (C_UID);
	}
	
	public static Long getUidLong () {
		
		if ( getUid () == null ) {
			return null;
		}
		return new Long ( getUid () );
	}
	
	public static String getSessionKey () {
		return Cookies.getCookie(C_SESSION_KEY) + "";
	}
	
	public static void setSessionKey ( String sessionKey ) {
		Cookies.setCookie ( C_SESSION_KEY, sessionKey );
	}

	public static void setUid ( String uid ) {
		Cookies.setCookie(C_UID, uid );
	}
	
	public static String getTheme () {
		return getCookie ( "theme", "theme_black") + ".css";
	}
	
	public static void setTheme ( String themeName ) {
		Cookies.setCookie("theme", themeName, nextYear() ); 
	}
	
	public static boolean hasCustomizedTopics () {
		
		
		String topics = Cookies.getCookie ( "topics");
		if ( topics == null ) {
			return false;
		}
		
		if ( topics.trim().equals ( "") ) {
			return false;
		}
		
		return true;
	}
	
	private static String getCookie ( String cookieName, String defaultValue ) {
		String val = Cookies.getCookie( cookieName );
		if ( val != null ) {
			return val;
		}
		return defaultValue;
 	}

	public static boolean isAdmin() {
		if ( !UserInfo.isLoggedIn() ) {
			return false;
		}
		return UserInfo.getUid().equals( "744450545" );
	}
	
	public static Date nextYear () {
		
		Date date = new Date ();
		date.setTime(System.currentTimeMillis()*2);
		return date;
		
	}
}

package com.gwittit.client.facebook.xfbml;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

/**
 * Wrapper class for the fb:name tag.
 * 
 * See http://wiki.developers.facebook.com/index.php/Fb:name
 * 
 * CSS Configuration
 * <ul>
 *  <li>.gwittit-FbName
 * </ul>
 * 
 *
 */
public class FbName extends Widget {
	
	public FbName ( Long uid ) {
		this( "" + uid );
	}
	
	public FbName ( String uid ) {
		super.setElement( DOM.createElement ("fb:name") ); 
		if ( uid == null || "".equals ( uid.trim() ) ) {
		    throw new IllegalArgumentException ( "uid null" );
		} else {
    		super.addStyleName("FbName" );
    		set( "uid", uid);
    		addStyleName ( "gwittit-FbName");
		}
	}

	public FbName ( Long uid, boolean linked ) {
		this ( ""+ uid );
		setLinked( linked );
	}
	public void setUid(String uid) {
		
		if ( uid == null || "".equals ( uid.trim() ) ) {
			Window.alert( "Debug: FbName: uid null");
		}
		
		set( "uid", uid );
	}

	public void setFirstnameonly(boolean firstnameonly) {
		set( "firstnameonly", ""+firstnameonly);
	}

	public void setLinked(boolean linked) {
		set("linked",""+linked);
	}

	public void setLastnameonly(boolean lastnameonly) {
		set ( "lastnameonly", lastnameonly + "" );
	}

	public void setPossesive(boolean possessive) {
		set ( "possesive", possessive + "" );
	}

	public void setReflexive(boolean reflexive) {
		set( "reflexive", reflexive + "" );
	}

	public void setShownetwork(boolean shownetwork) {
		set ( "shownetwork", shownetwork + "");
	}


	public void setUseyou(boolean useyou) {
		set ( "useyou", "" + useyou );
	}

	public void setIfcantsee(String ifcantsee) {
		set ("ifcantsee", ifcantsee + "" );
	}


	public void setCapitalize(boolean capitalize) {
		set ( "capitalize", "" + capitalize );
	}


	public void setSubjectid(String subjectid) {
		set ( "subjectid", subjectid );
	}
	
	private void set (String name, String value ) {
		getElement().setAttribute( name, value );
	}
}

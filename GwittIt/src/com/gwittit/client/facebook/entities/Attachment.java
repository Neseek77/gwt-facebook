package com.gwittit.client.facebook.entities;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * Wrapp attachment
 * 
 * @see http://wiki.developers.facebook.com/index.php/Attachment_%28Streams%29
 * @author ola
 * 
 */
public class Attachment {

	/**
	 * The title of the post. The post should fit on one line in a user's
	 * stream; make sure you account for the width of any thumbnail.
	 */
	private String name;

	/**
	 * The URL to the source of the post referenced in the name. The URL should
	 * not be longer than 1024 characters.
	 */
	private String href;

	/**
	 * A subtitle for the post that should describe why the user posted the item
	 * or the action the user took. This field can contain plain text only, as
	 * well as the {*actor*} token, which gets replaced by a link to the profile
	 * of the session user. The caption should fit on one line in a user's
	 * stream; make sure you account for the width of any thumbnail.
	 */
	private String caption;

	/**
	 * # Descriptive text about the story. This field can contain plain text
	 * only and should be no longer than is necessary for a reader to understand
	 * the story. # properties: An array of key/value pairs that provide more
	 * information about the post. The properties array can contain plain text
	 * and links only. To include a link, the value of the property should be a
	 * dictionary with 'text' and 'href' attributes.
	 */
	private String description;

	/**
	 * Rich media that provides visual content for the post. media is an array that contains one of the following types: image, flash, mp3, or video, which are described below. Make sure you specify only one of these types in your post. 
	 */
	private List<Media> medias = new ArrayList<Media> ();

	/**
	 * An application-specific xid associated with the stream post. The xid
	 * allows you to get comments made to this post using the Comments API. It
	 * also allows you to associate comments made to this post with a Comments
	 * Box for FBML fb:comments.
	 */
	private String commentsXid;
	
	
	/**
	 * Attachment icon, undocumentend from facebook
	 */
	private String icon;
	
	
	public Attachment () {
		
	}
	
	

	public Attachment ( JSONValue j ) {
		
		JSONObject o = j.isObject();
		
		if ( o == null ) {
			return ;
		}
		
		name = JsonUtil.getString(o, "name" );
		href = JsonUtil.getString(o, "href" );
		caption = JsonUtil.getString ( o, "caption" );
		description = JsonUtil.getString (o, "description" );
		icon = JsonUtil.getString(o, "icon");

		// Parse media, this is a pretty complex structure. See facebook doc for info 
		JSONValue v = o.get ( "media" );
		if ( v !=  null ) {
			JSONArray mediasJson = v.isArray();
			if ( mediasJson != null ) {
				for ( int i = 0 ; i  < mediasJson.size() ; i++ ) {
					JSONValue mv = mediasJson.get(i);
					if ( mv.isObject() != null ) {
						GWT.log( "Attachment: create new Media file", null);
						Media media = new Media ( mv.isObject() );
						medias.add ( media );
					}
				}
			}
		}
		
		GWT.log( "attachment.name = " + name , null );
	}
	
	/**
	 * For own use
	 */
	private String lat;

	/**
	 * for own use
	 */
	private String lng;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Media> getMedias() {
		return medias;
	}

	public void setMedias(List<Media> medias) {
		this.medias = medias;
	}

	public String getCommentsXid() {
		return commentsXid;
	}

	public void setCommentsXid(String commentsXid) {
		this.commentsXid = commentsXid;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}


	public String getIcon() {
		return icon;
	}


	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	
	
	
}

package com.gwittit.client.facebook.entities;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * See http://wiki.developers.facebook.com/index.php/Attachment_%28Streams%29
 */
public class Media {
	
     enum Type {photo};

	/**
	 * You can include rich media in the attachment for a post into a user's
	 * stream. The media parameter contains a type, which can be one of
	 * following: image, flash, mp3, or video; these media types render photos,
	 * Flash objects, music, and video, respectively.
	 */
	private String type;

	/**
	 * The image media type is part an array which itself contains an array of
	 * up to five JSON-encoded photo records. Each record must contain a src
	 * key, which maps to the photo URL, and an href key, which maps to the URL
	 * where a user should be taken if he or she clicks the photo.
	 */
	private String src;

	
	private String href;

	/**
	 * Shich is the URL of the Flash object to be rendered.
	 */
	private String swfsrc;

	private String imgsrc;

	public Media () {
		
	}
	
	
	public Widget createWidget () {
		final SimplePanel w = new SimplePanel ();
		w.addStyleName("gwittit-Media");
		
		if ( Type.photo.toString().equals ( type ) )  {
			
			Image image = new Image ( src );
			w.add ( image );
		}
		return w;
	}
	
	public Media ( JSONObject o ) {
		type = JsonUtil.getString(o, "type");
		
		if ( Type.photo.toString().equals( type ) ) {
			src = JsonUtil.getString(o, "src");
			href = JsonUtil.getString(o, "href");
		}
		
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getSwfsrc() {
		return swfsrc;
	}

	public void setSwfsrc(String swfsrc) {
		this.swfsrc = swfsrc;
	}

	public String getImgsrc() {
		return imgsrc;
	}

	public void setImgsrc(String imgsrc) {
		this.imgsrc = imgsrc;
	}
	
	
	
}

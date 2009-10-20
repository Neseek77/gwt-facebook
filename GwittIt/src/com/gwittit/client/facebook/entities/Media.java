package com.gwittit.client.facebook.entities;

import com.google.gwt.core.client.JavaScriptObject;


/**
 * Facebook Media
 * 
 * @see <a href="http://wiki.developers.facebook.com/index.php/Attachment_%28Streams%29">Attachments</a>
 * @author olamar72
 */
public class Media extends JavaScriptObject {
	
    protected Media () {
        
    }
    
    public enum Type {photo,link,video,flash,mp3};
    public final Type getTypeEnum () {
        return Type.valueOf ( getType() );
    }

	/**
	 * You can include rich media in the attachment for a post into a user's
	 * stream. The media parameter contains a type, which can be one of
	 * following: image, flash, mp3, or video; these media types render photos,
	 * Flash objects, music, and video, respectively.
	 */
    public final native String getType() /*-{ return this.type; }-*/;

	/**
	 * The image media type is part an array which itself contains an array of
	 * up to five JSON-encoded photo records. Each record must contain a src
	 * key, which maps to the photo URL, and an href key, which maps to the URL
	 * where a user should be taken if he or she clicks the photo.
	 */
    public final native String getSrc() /*-{ return this.src; }-*/;

	/**
	 * Image alt/ preview link
	 */
    public final native String getAlt() /*-{ return this.alt; }-*/;
	
    public final native String getHref() /*-{ return this.href; }-*/;

	/**
	 * Shich is the URL of the Flash object to be rendered.
	 */
    public final native String getSwfsrc() /*-{ return this.swfsrc; }-*/;
   	
    public final native String getImgsrc() /*-{ return this.imgsrc; }-*/;
   
    /**
     * Video Object
     */
    public final native Video getVideo() /*-{ return this.video; }-*/;

	
    public final String stringify() {
        return "Type:"  + getType();
    }
	
}

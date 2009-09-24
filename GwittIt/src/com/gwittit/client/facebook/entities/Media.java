package com.gwittit.client.facebook.entities;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;


/*
 * {"post_id":"6095759876_135917779876", "viewer_id":744450545, "source_id":6095759876, "type":236, "app_id":2347471856, "attribution":null, "actor_id":6095759876, "target_id":null, "message":"", "attachment":{"media":{}, "name":"Keri Herman drops Edit.", "href":"http://www.facebook.com/note.php?note_id=270087345051", "description":"Keri Herman shows her prowess on jumps and represents Breckenridge to the fullest.", "properties":{}, "icon":"http://b.static.ak.fbcdn.net/rsrc.php/z7NSY/hash/ajh5dbgz.gif", "fb_object_type":"", "fb_object_id":""}, "app_data":{}, "action_links":null, "comments":{"can_remove":false, "can_post":true, "count":0, "comment_list":{}}, "likes":{"href":"http://www.connect.facebook.com/social_graph.php?node_id=270087345051&class=LikeManager", "count":2, "sample":[1651063774], "friends":{}, "user_likes":false, "can_like":true}, "privacy":{"value":"NOT_EVERYONE"}, "updated_time":1253721968, "created_time":1253721968, "tagged_ids":{}, "is_hidden":false, "filter_key":"", "permalink":"http://www.facebook.com/note.php?note_id=270087345051&comments"}
 */






/**
 * See http://wiki.developers.facebook.com/index.php/Attachment_%28Streams%29
 */
public class Media {
	
     public enum Type {photo,link};

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
	

	
	public Media ( JSONObject o ) {
		type = JsonUtil.getString(o, "type");
		
			src = JsonUtil.getString(o, "src");
			href = JsonUtil.getString(o, "href");
		
	}
	
	public Type getTypeEnum () {
		return Type.valueOf( type );
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

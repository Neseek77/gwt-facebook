package com.gwittit.client.facebook.entities;

/**
 * See http://wiki.developers.facebook.com/index.php/Attachment_%28Streams%29
 */
public class Media {

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
}

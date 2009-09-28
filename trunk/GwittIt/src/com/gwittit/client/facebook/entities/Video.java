package com.gwittit.client.facebook.entities;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;

/**
 * Represents a video object in facebook 
 */
public class Video {
	
	private String displayUrl;
	
	private String sourceUrl;
	
	private String owner;
	
	private String permalink;
	
	private String sourceType;

	public Video () {
		
	}
	
	public Video ( JSONValue  j ) {
		JSONObject o = j.isObject();
		
		displayUrl = JsonUtil.getString(o, "display_url");
		sourceUrl = JsonUtil.getString(o, "source_url");
		owner = JsonUtil.getString(o, "owner");
		permalink = JsonUtil.getString(o, "permalink");
		sourceType = JsonUtil.getString(o, "source_type");
	}
	public String getDisplayUrl() {
		return displayUrl;
	}

	public void setDisplayUrl(String displayUrl) {
		this.displayUrl = displayUrl;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getPermalink() {
		return permalink;
	}

	public void setPermalink(String permalink) {
		this.permalink = permalink;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	
	

}

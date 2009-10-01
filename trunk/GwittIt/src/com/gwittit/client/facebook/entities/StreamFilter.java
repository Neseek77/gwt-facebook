package com.gwittit.client.facebook.entities;

import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;

/**
 * Represents a stream filter.
 * 
 * @see http://wiki.developers.facebook.com/index.php/Stream_filter_%28FQL%29
 *
 */
public class StreamFilter {

	public enum Type {application,newsfeed,friendlist,network,publicprofile}
	
	/*
	 * The ID of the user whose filters you are querying. 
	 */
	private String uid;
	
	/*
	 * A key identifying a particular filter for a user's stream. This filter is useful to retrieve relevant items from the stream table. 
	 */
	private String filterKey;
	
	/*
	 * The name of the filter as it appears on the home page. 
	 */
	private String name;
	
	/*
	 * A 32-bit int that indicates where the filter appears in the sort. 
	 */
	private int rank;
	
	/*
	 * The URL to the filter icon. For applications, this is the same as your application icon. 
	 */
	private String iconUrl;
	
	/*
	 * If true, indicates that the filter is visible on the home page. If false, the filter is hidden in the More link. 
	 */
	private boolean isVisible;
	
	/*
	 * The type of filter. One of application, newsfeed, friendlist, network, or publicprofile. 
	 */
	private String type;
	
	/*
	 * A 64-bit ID for the filter type. 
	 */
	private int value;
	
	
	public StreamFilter ( JSONValue j ) {
		
		JSONObject o = j.isObject();
		
		GWT.log( StreamFilter.class + ": create " + o , null );
		uid = JsonUtil.getString(o, "uid");
		filterKey = JsonUtil.getString(o, "filter_key");
		name = JsonUtil.getString ( o, "name" );
		rank = JsonUtil.getInt(o,"rank");
		iconUrl = JsonUtil.getString ( o, "icon_url" );
		isVisible = JsonUtil.getBoolean ( o, "is_visible" );
		type = JsonUtil.getString ( o, "type");
		//value = JsonUtil.getInt ( o, "value");
	}


	public String getUid() {
		return uid;
	}


	public void setUid(String uid) {
		this.uid = uid;
	}


	public String getFilterKey() {
		return filterKey;
	}


	public void setFilterKey(String filterKey) {
		this.filterKey = filterKey;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getRank() {
		return rank;
	}


	public void setRank(int rank) {
		this.rank = rank;
	}


	public String getIconUrl() {
		return iconUrl;
	}


	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}


	public boolean isVisible() {
		return isVisible;
	}


	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public int getValue() {
		return value;
	}


	public void setValue(int value) {
		this.value = value;
	}

	
}

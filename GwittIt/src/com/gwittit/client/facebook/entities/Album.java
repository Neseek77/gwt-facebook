package com.gwittit.client.facebook.entities;

import com.google.gwt.json.client.JSONObject;

public class Album {
	/*
	<aid>34595963571485</aid>  <cover_pid>34595991612812</cover_pid>  <owner>8055</owner>  <name>Films you will never see</name>  <created>1132553109</created>  <modified>1132553363</modified>  <description>No I will not make out with you</description>  <location>York, PA</location>  <link>http://www.facebook.com/album.php?aid=2002205&id=8055</link>  <size>30</size>  <visible>friends</visible>  <modified_major>1241834423</modified_major> 
	*/

	private String aid;

	private String coverPid;
	
	private Long owner;
	
	private String name;
	
	private String description;
	
	private String location;
	
	private Integer size;
	
	private String visible;
	
	private String link;

	public static Album newInstance ( JSONObject o ) {
		Album a = new Album ();
		a.aid = JsonUtil.getString(o, "aid");
		a.coverPid =JsonUtil.getString(o, "cover_pid");
		a.owner = JsonUtil.getLong(o, "owner");
		a.name = JsonUtil.getString(o, "name");
		a.description =JsonUtil.getString(o, "description");
		a.location = JsonUtil.getString(o, "location");
		a.size =JsonUtil.getInt(o, "size");
		a.visible =JsonUtil.getString(o, "visible");
		a.link =JsonUtil.getString(o, "link");
		return a;
	}
	
	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getCoverPid() {
		return coverPid;
	}

	public void setCoverPid(String coverPid) {
		this.coverPid = coverPid;
	}

	public Long getOwner() {
		return owner;
	}

	public void setOwner(Long owner) {
		this.owner = owner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	

	
	
	
	
	
}

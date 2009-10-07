package com.gwittit.client.facebook.entities;

import com.google.gwt.json.client.JSONObject;

/**
 * Photo Album
 * @author olamar72
 *
 */
public class Album {
	
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

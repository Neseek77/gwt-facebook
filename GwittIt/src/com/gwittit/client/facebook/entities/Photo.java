package com.gwittit.client.facebook.entities;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;

public class Photo {

	/*
	 * <pid>34585991612804</pid>  <aid>34585963571485</aid>  <owner>1240077</owner>  <src>http://ip002.facebook.com/v11/135/18/8055/s1240077_30043524_2020.jpg</src>  <src_big>http://ip002.facebook.com/v11/135/18/8055/n1240077_30043524_2020.jpg</src>  <src_small>http://ip002.facebook.com/v11/135/18/8055/t1240077_30043524_2020.jpg</src>  <link>http://www.facebook.com/photo.php?pid=30043524&id=8055</link>  <caption>From The Deathmatch (Trailer) (1999)</caption>  <created>1132553361</created> 
	 */
	
	private String pid;
	
	private String aid;
	
	private String name;
	
	private Long owner;
	
	private String src;
	
	private String srcBig;
	
	private String srcSmall;
	
	private String link;
	
	private String caption;



	public Photo () { }
	
	public Photo  ( JSONObject o ) {
		pid = JsonUtil.getString (o, "pid");
		aid = JsonUtil.getString (o, "aid");
		owner = JsonUtil.getLong (o, "owner");
		
		name = JsonUtil.getString(o, "name" );
		src = JsonUtil.getString ( o, "src");
		srcBig = JsonUtil.getString(o, "src_big" );
		srcSmall = JsonUtil.getString(o, "src_small" ); 
		link = JsonUtil.getString( o, "link" );
		caption = JsonUtil.getString ( o, "caption" );
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public Long getOwner() {
		return owner;
	}

	public void setOwner(Long owner) {
		this.owner = owner;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getSrcBig() {
		return srcBig;
	}

	public void setSrcBig(String srcBig) {
		this.srcBig = srcBig;
	}

	public String getSrcSmall() {
		return srcSmall;
	}

	public void setSrcSmall(String srcSmall) {
		this.srcSmall = srcSmall;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}	
	
}

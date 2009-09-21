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
	
	private Long owner;
	
	private String src;
	
	private String srcBig;
	
	private String srcSmall;
	
	private String link;
	
	private String caption;


	public static Photo convert ( JSONObject o ) {
		
		Photo p = new Photo ();
		p.pid = EntityUtil.getString (o, "pid");
		p.aid = EntityUtil.getString (o, "aid");
		p.owner = EntityUtil.getLong (o, "owner");
		
		p.src = EntityUtil.getString ( o, "src");
		p.srcBig = EntityUtil.getString(o, "src_big" );
		p.srcSmall = EntityUtil.getString(o, "src_small" ); 
		p.link = EntityUtil.getString( o, "link" );
		p.caption = EntityUtil.getString ( o, "caption" );
		return p;
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

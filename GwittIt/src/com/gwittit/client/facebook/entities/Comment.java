package com.gwittit.client.facebook.entities;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONObject;

/*
 * Stream Comment
 * @see http://wiki.developers.facebook.com/index.php/Stream.getComments
 * @author olamar72 
 */
public class Comment {
	
	private String postId;
	
	private Long fromId;
	
	private Date time;
	
	private String text;

	
	public Comment () {
		
	}
	
	public Comment ( JSONObject o ) {
		if ( o == null ) {
			GWT.log( "Comment: Null argument ", null );
		}
		postId = JsonUtil.getString(o, "post_id");
		fromId = JsonUtil.getLong(o, "fromid");
		time = JsonUtil.getDate(o, "time");
		text = JsonUtil.getString (o, "text");
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public Long getFromId() {
		return fromId;
	}

	public void setFromId(Long fromId) {
		this.fromId = fromId;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((postId == null) ? 0 : postId.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comment other = (Comment) obj;
		if (postId == null) {
			if (other.postId != null)
				return false;
		} else if (!postId.equals(other.postId))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}
	
	
	
	
}

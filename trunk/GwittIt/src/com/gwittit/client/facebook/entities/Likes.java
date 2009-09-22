package com.gwittit.client.facebook.entities;


import java.util.List;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @see http://wiki.developers.facebook.com/index.php/Stream_%28FQL%29
 * @author ola
 *
 */
public class Likes {

	/**
	 * The URL to a page showing the other users who've liked this post. 
	 */
	private String href;
	
	/**
	 * The total number of times users like the post. 
	 */
	private int count; 
	
	/**
	 * A sample of users who like the post. 
	 */
	private List<Long> sample;
	
	/**
	 * A list of the viewing user's friends who like the post. 
	 */
	private List<Long> friends;
	
	
	/**
	 * Indicates whether the viewing user likes the post. 
	 */
	private boolean userLikes;
	
	/**
	 * Indicates whether the post can be liked. 
	 */
	private boolean canLike;


	/**
	 * Create a default widget. This should of course be customized.
	 */
	public Widget createWidget () {
		SimplePanel pnl = new SimplePanel ();
		
		if ( getCount() == 0  ) {
			
			if ( userLikes ) {
				return new HTML ( "You like this" );
			}
			return pnl;
		}
		
		HTML h = new HTML ( (userLikes ? "You and " : "")  + "<a href=" + getHref() + "> " + getCount () + " people </a> like this" );
		return h;
	}
	
	/**
	 * Create a new instance, parse the json value
	 */
	public static Likes newInstance ( JSONValue v ) {
		Likes i = new Likes ();
		
		JSONObject o = v.isObject();
		
		i.href = JsonUtil.getString(o, "href");
		i.count = JsonUtil.getInt(o, "count" );
		i.userLikes = JsonUtil.getBoolean(o,"user_likes");
		i.canLike = JsonUtil.getBoolean(o, "can_like");
		return i;
	}
	
	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<Long> getSample() {
		return sample;
	}

	public void setSample(List<Long> sample) {
		this.sample = sample;
	}

	public List<Long> getFriends() {
		return friends;
	}

	public void setFriends(List<Long> friends) {
		this.friends = friends;
	}

	public boolean isUserLikes() {
		return userLikes;
	}

	public void setUserLikes(boolean userLikes) {
		this.userLikes = userLikes;
	}

	public boolean isCanLike() {
		return canLike;
	}

	public void setCanLike(boolean canLike) {
		this.canLike = canLike;
	}
	
	
	
	
}

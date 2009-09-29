package com.gwittit.client.facebook.entities;


import java.util.List;

import com.google.gwt.core.client.GWT;
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
	private Integer count; 
	
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

	public Likes () {
		
	}

	/**
	 * Create a default widget. This should of course be customized.
	 */
	public Widget createWidget () {
		SimplePanel pnl = new SimplePanel ();
		pnl.addStyleName("gwittit-Likes");

		String txt = null;
		if ( isUserLikes() && getCountExcludeUser() == 0 ) {
			txt = "You like this";
		}

		else if ( isUserLikes() && getCountExcludeUser() > 0 ) {
			txt = "You and " + getCountExcludeUser() + " people likes this ";
		}
		else if ( getCount() > 0 ) {
			txt = getCount() + " people likes this";
		}

		if ( txt != null ) {
			HTML h = new HTML ( txt );
			pnl.setWidget(h);
			return pnl;
		}
		
		return null;
	}
	
	/**
	 * Create a new instance, parse the json value
	 */
	public  Likes ( JSONValue v ) {
		GWT.log( Likes.class + ": create " + v , null );

		if ( v == null ) {
			return ; 
		}
		
		JSONObject o = v.isObject();
		
		if ( o == null ) {
			return ; 
		}
		try {
			
		href = JsonUtil.getString(o, "href");
		
		count = JsonUtil.getInt(o, "count" );
		userLikes = JsonUtil.getBoolean(o,"user_likes");
		canLike = JsonUtil.getBoolean(o, "can_like");
		} catch ( Exception e ) {
			e.printStackTrace();
			GWT.log ( Likes.class + ": failed to create Likes ", e );
		}
	}

	
	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public Integer getCount() {
		if ( count == null ) {
			return 0;
		}
		return count;
	}

	public int getCountExcludeUser () {
		return isUserLikes() ? getCount()-1 : getCount();
		
	}
	public void setCount(Integer count) {
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

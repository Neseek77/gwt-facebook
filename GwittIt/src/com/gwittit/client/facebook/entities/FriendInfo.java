package com.gwittit.client.facebook.entities;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Indicates if two persons are friends.
 * 
 * @see http://wiki.developers.facebook.com/index.php/Friends.areFriends Api Method
 */
public class FriendInfo extends JavaScriptObject {

    protected FriendInfo () {}
    
    /**
     * First uid as String
     */
    public final native String getUid1String() /*-{ return this.uid1 + ""; }-*/;
  
    /**
     * First uid
     */
    public final Long getUid1() { return new Long ( getUid1String() ); }
    
    /**
     * Second uid as String
     */
    public final native String getUid2String() /*-{ return this.uid2 + ""; }-*/;
    
    /**
     * Second uid
     */
    public final Long getUid2() { return new Long ( getUid2String() ); }
    
    /**
     * True if uid1 and uid2 are friends
     */
    public final native boolean getAreFriends() /*-{ return this.are_friends == true ; }-*/;
	
}

package com.gwittit.client.facebook.test;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.junit.client.GWTTestCase;
import com.gwittit.client.facebook.entities.FriendInfo;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Test friends.* methods
 */
public class FriendsTest extends GWTTestCase {

    public static final String friends_areFriendsJson = "{\"0\":{\"uid1\":744450545, \"uid2\":709281400, \"are_friends\":true}, \"1\":{\"uid1\":751836969, \"uid2\":560635378, \"are_friends\":true}, \"2\":{\"uid1\":708775201, \"uid2\":709281400, \"are_friends\":false}}";

    /*
     * Parse friends.areFriends response 
     */
    public void testFriendsAreFriends () {
        FriendInfo friendInfo = FriendInfo.fromJson ( "{\"uid1\":744450545, \"uid2\":709281400, \"are_friends\":true}" );
        Assert.assertEquals ( friendInfo.getUid1 (), new Long ( 744450545 ) );
    }

    
    public void testFriendsAreFriendsArray () {
        
        JsArray<FriendInfo> friendInfoList = getFriendInfoList ();

        System.out.println ( friendInfoList.length () );
    }
    
    
    public native JsArray<FriendInfo> getFriendInfoList () /*-{
           return {"0":{"uid1":744450545, "uid2":709281400, "are_friends":true}, "1":{"uid1":751836969, "uid2":560635378, "are_friends":true}, "2":{"uid1":708775201, "uid2":709281400, "are_friends":false}}   
     }-*/;

    
    @Override
    public String getModuleName() {
        return "com.gwittit.Gwittit";
    }
    
}

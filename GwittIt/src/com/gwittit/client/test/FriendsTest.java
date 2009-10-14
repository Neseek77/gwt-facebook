package com.gwittit.client.test;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayInteger;
import com.google.gwt.core.client.JsArrayNumber;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.dev.jjs.ast.js.JsonArray;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.Window;
import com.gwittit.client.facebook.entities.FriendInfo;



/**
 * Test friends.* methods
 */
public class FriendsTest  extends GWTTestCase {

    public static final String friends_areFriendsJson = "{\"0\":{\"uid1\":744450545, \"uid2\":709281400, \"are_friends\":true}, \"1\":{\"uid1\":751836969, \"uid2\":560635378, \"are_friends\":true}, \"2\":{\"uid1\":708775201, \"uid2\":709281400, \"are_friends\":false}}";

    /*
     * Parse friends.areFriends response 
     */
    public void testFriendsAreFriends () {
        //FriendInfo friendInfo = FriendInfo.fromJson ( "{\"uid1\":744450545, \"uid2\":709281400, \"are_friends\":true}" );
    }

    
    public void testFriendsAreFriendsArray () {

        JavaScriptObject object = JavaScriptObject.createArray ();
        
        getFriendInfoList ();
    }
    
    
    public void testFriendsGet () {

        
        JsArrayNumber friendList = getFriendList().cast ();
        for ( int i = 0 ; i < friendList.length (); i ++ ) {
            NumberFormat fmt = NumberFormat.getFormat ( "0" );
            double friendId = friendList.get ( i );
            System.out.println ( fmt.format ( friendId ) );
            //System.out.println ( new Long ( "" + friendList.get ( i ) ) );
        }
    }

    public native JavaScriptObject getFriendList () /*-{
        return [518296697,521171721,522036134,522170807,522200950,522410350,523605699,525231096,525475929,527532006,529925848,530275725,530785898,532260314,532291295,532981528,533071560,533255886,534168942,535216931,535271559,536422018,536461699,537691963,538382315,538961367,539426968,539492454,543185551,544271625,545511821,545975265,546966699,550731057,551375403,554105867,555541711,556138251,556360604,558386062,558737031,558757440,560635378,561089694,561370224,563445449,570570024,571840922,571871173,572626183,573065608,577175763,580661080,583176769,586270536,588651478,590061335,595469464,595696709,595917018,597492679,598276921,598526232,598965029,602004018,603996618,604142770,606181110,606281997,608342326,611285169,613267216,615206150,619136890,619946492,625105108,627626486,628502533,628918750,629382204,632350478,635162183,637805840,638932065,640671578,642950543,643244725,645265123,645635224,646285902,648655615,650244555,652632323,653035945,658696789,660358041,662450812,664696910,665491640,665760428,666751502,668161313,669041293,669816015,670340063,671091290,672355420,676555656,676995010,684141512,684200289,685065832,685910089,688590180,690905842,693320168,698790163,701281569,702450902,702808258,708775201,709235560,709281400,709466030,715891000,717825195,718265069,725530194,726421650,732055136,733096267,735395490,736817278,740476100,749811139,751775048,751836969,754560405,763705318,766263768,791145042,791860391,794740656,798090653,799600306,804005719,807462490,814200130,818595721,837160602,843495183,847245577,867685007,892810458,895640424,901415304,905025231,1042261269,1086508171,1110877125,1268870933,1306941237,1351676906,1377788418,1545740579,1556582646,100000002373472];
    }-*/;
    
    public native JavaScriptObject getFriendInfoList () /*-{
           return {"0":{"uid1":744450545, "uid2":709281400, "are_friends":true}, "1":{"uid1":751836969, "uid2":560635378, "are_friends":true}, "2":{"uid1":708775201, "uid2":709281400, "are_friends":false}}   
     }-*/;

    
    @Override
    public String getModuleName() {
        return "com.gwittit.Gwittit";
    }
    
}

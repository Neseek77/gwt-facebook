package com.gwittit.client.facebook.entities;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Facebook Group
 * 
 * @see <a
 *      href="http://wiki.developers.facebook.com/index.php/Groups.get">Facebook
 *      Group</a>
 */
public class Group extends JavaScriptObject {

    protected Group() {
    }

    // Todo: Find out what recent_news and office
    public final native String getName() /*-{
        return this.name;
    }-*/;

    public final native String getGidString() /*-{
        return this.gid + "";
    }-*/;

    public final Long getGid() {
        return new Long ( getGidString () );
    }

    public final native String getNidString() /*-{
        return this.nid + "";
    }-*/;

    public final Long getNid() {
        return new Long ( getNidString () );
    }

    public final native String getDescription() /*-{
        return this.description;
    }-*/;

    public final native String getGroupType() /*-{
        return this.group_type;
    }-*/;

    public final native String getGroupSubtype() /*-{
        return this.group_subtype;
    }-*/;

    public final native String getPic() /*-{
        return this.pic;
    }-*/;

    public final native String getPicBig() /*-{
        return this.pic_big;
    }-*/;

    public final native String getPicSmall() /*-{
        return this.pic_small;
    }-*/;

    public final native String getCreatorString() /*-{
        return this.creator + "";
    }-*/;

    public final Long getCreator() {
        return new Long ( getCreatorString () );
    }

    public final native String getUpdateTimeString() /*-{
        return this.update_time + "";
    }-*/;

    public final Long getUpdateTime() {
        return new Long ( getUpdateTimeString () );
    }

    public final native String getWebsite() /*-{
        return this.website;
    }-*/;

    public final native JavaScriptObject getVeneu() /*-{
        return this.veneu;
    }-*/;

    public static native Group fromJson(String jsonString) /*-{
        return eval('(' + jsonString + ')');
    }-*/;

}

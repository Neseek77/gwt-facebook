package com.gwittit.client.facebook.entities;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Facebook Group
 * 
 * @see <a
 *      href="http://wiki.developers.facebook.com/index.php/Groups.get">Facebook
 *      Group</a>
 */
public class Page extends JavaScriptObject {

    protected Page() {
    }

    // Todo: Find out what recent_news and office
    public final native String getName() /*-{
        return this.name;
    }-*/;

    public final native String getPageId() /*-{
        return this.page_id + "";
    }-*/;

    public final Long getPageIdLong() {
        return new Long ( getPageId() );
    }

    public final native String getPageUrl() /*-{
        return this.page_url;
    }-*/;

    public final native String getWebsite() /*-{
        return this.website;
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
    
    public final native String getPicSquare() /*-{
    return this.pic_square;
	}-*/;
    
    public final native String getType() /*-{
    return this.type;
	}-*/;
    
    public final native int getFanCount() /*-{
    return this.fan_count;
	}-*/;
    
    public final Long getFanCountLong() {
     return new Long ( getPageId() );
	};

    public static native Page fromJson(String jsonString) /*-{
        return eval('(' + jsonString + ')');
    }-*/;

}

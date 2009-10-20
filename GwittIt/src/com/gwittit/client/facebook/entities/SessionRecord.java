package com.gwittit.client.facebook.entities;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Facebook SessionRecord stored in cookie, use apiclient to get this.
 * 
 * @see <a
 *      href="http://wiki.developers.facebook.com/index.php/JS_API_T_FB.SessionRecord">SessionRecord</a>
 * 
 * @author olamr72
 */
public class SessionRecord extends JavaScriptObject {

    protected SessionRecord() {
    }

    public final native String getSessionKey() /*-{
        return this.session_key;
    }-*/;

    public final native String getUidString() /*-{
        return this.uid + "";
    }-*/;

    public final Long getUid() {
        return new Long ( getUidString () );
    }

    public final native String getExpireString() /*-{
        return this.expire + "";
    }-*/;

    public final Long getExpire() {
        return new Long ( getExpireString () );
    }

    public final native String getSecret() /*-{
        return this.secret;
    }-*/;

    public final native String getBaseDomain() /*-{
        return this.base_domain;
    }-*/;

    public final native String getSig() /*-{
        return this.sig;
    }-*/;

    public static native SessionRecord fromJson(String jsonString) /*-{
        return eval('(' + jsonString + ')');
    }-*/;
}

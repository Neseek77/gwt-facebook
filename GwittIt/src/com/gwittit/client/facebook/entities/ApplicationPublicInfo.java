package com.gwittit.client.facebook.entities;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

/**
 * Public information for an application.
 * 
 * @see http://wiki.developers.facebook.com/index.php/Application.getPublicInfo
 *      ApplicationGetPublicInfo
 * 
 */
public class ApplicationPublicInfo extends JavaScriptObject {

    protected ApplicationPublicInfo() {
    }

    public final native String getAppIdString() /*-{
        return this.app_id + "";
    }-*/;

    public final Long getAppId() {
        return new Long ( getAppIdString () );
    }

    public final native String getApiKey() /*-{
        return this.api_key;
    }-*/;

    public final native String getCanvasName() /*-{
        return this.canvas_name;
    }-*/;

    public final native String getIconUrl() /*-{
        return this.icon_url;
    }-*/;

    public final native String getLogoUrl() /*-{
        return this.logo_url;
    }-*/;

    public final native String getCompanyName() /*-{
        return this.company_name;
    }-*/;

    public final native String getDescription() /*-{
        return this.description;
    }-*/;

    public final native Integer getDailyActiveUsers() /*-{
        return this.daily_active_users;
    }-*/;

    public final native Integer getWeeklyActiveUsers() /*-{
        return this.weekly_active_users;
    }-*/;

    public final native Integer getMonthlyActiveUsers() /*-{
        return this.monthly_active_users;
    }-*/;

    public final native JavaScriptObject getDevelopersNative() /*-{
        return this.developers;
    }-*/;

    public final JsArray<User> getDevelopers() {
        JavaScriptObject jso = getDevelopersNative ();
        JsArray<User> developers = jso.cast ();
        return developers;
    }
}

package com.gwittit.client.facebook.entities;

import com.google.gwt.core.client.JavaScriptObject;


/**
 * Userinfo returned from call users.getStandardInfo
 * 
 * @see {@link http://wiki.developers.facebook.com/index.php/Users.getStandardInfo}
 * 
 * @author olamar72
 */
public class UserStandardInfo extends User {
    
    protected UserStandardInfo () {}
    
    public final native String getFirstName() /*-{ return this.first_name; }-*/;
    public final native String getLastName() /*-{ return this.last_name; }-*/;
    public final native String getUsername() /*-{ return this.username; }-*/;
    public final native String getLocale() /*-{ return this.locale; }-*/;
    public final native JavaScriptObject getAffiliations() /*-{ return this.affiliations; }-*/;
    public final native String getProfileUrl() /*-{ return this.profile_url; }-*/;
    public final native String getTimezone() /*-{ return this.timezone; }-*/;
    public final native String getBirthdayString() /*-{ return this.birthday + ""; }-*/;
    public final Long getBirthday() { return new Long ( getBirthdayString() ); }
    public final native String getSex() /*-{ return this.sex; }-*/;
    public final native String getCurrentLocation() /*-{ return this.current_location; }-*/;
    public final native String getProxiedEmail() /*-{ return this.proxied_email; }-*/;
    public static native UserStandardInfo fromJson(String jsonString) /*-{ return eval('(' + jsonString + ')');}-*/;
    
}

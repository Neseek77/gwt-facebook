package com.gwittit.client.facebook.entities;

import java.util.Date;

import com.google.gwt.json.client.JSONObject;

/**
 * @deprecated only used in desktop apps.
 */
public class Session {

    private String sessionKey;
    
    private Long uid;
    
    private Date expires;

    public Session ( JSONObject o ) {
        sessionKey = JsonUtil.getString ( o, "session_key" );
        uid = JsonUtil.getLong ( o, "uid" );
        expires = JsonUtil.getDate (o , "expires" );
    }
    
    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }
    
    
    
    
}

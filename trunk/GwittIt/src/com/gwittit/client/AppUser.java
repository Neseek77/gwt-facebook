package com.gwittit.client;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * Persist user who added the application since facebook want
 * do this for us.
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class AppUser {
    
    
    @PrimaryKey
    private String name;
    
    @Persistent
    private Long uid;
    
    @Persistent
    private Integer loginCount = 0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }
    
    public void addLoginCount() {
        loginCount++;
    }
    
    public Integer getLoginCount () {
        return loginCount;
    }
}

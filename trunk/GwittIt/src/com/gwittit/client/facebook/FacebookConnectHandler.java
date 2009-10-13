package com.gwittit.client.facebook;

public interface FacebookConnectHandler {
    
    public void onConnected ( Long uid );
    
    public void onNotConnected();

}

package com.gwittit.client;

import com.google.gwt.user.client.Timer;

public abstract class EasyTimer {

    public EasyTimer ( int millis ) {
            Timer timer = new Timer () {
                    @Override
                    public void run() {
                            runIt();
                    }
            };
            timer.schedule( millis );
            
    }
    public EasyTimer () {
            this ( 2500 );
    }
    
    public abstract void runIt ();
    
    
}

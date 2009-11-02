package com.gwittit.server;


import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.gwittit.client.AppUser;
import com.gwittit.client.UserService;


@SuppressWarnings("serial")
public class UserServiceImpl extends RemoteServiceServlet implements UserService {

    Logger log = Logger.getLogger ( UserServiceImpl.class.getSimpleName () );
    
    /**
     * Log user so we can send notifications, updates and stuff.
     */
    public void logUser ( Long uid ) {
        
        PersistenceManager pm = PMF.get ().getPersistenceManager ();
        AppUser appuser = null;
        
        try {
            appuser = pm.getObjectById ( AppUser.class, "key_"+uid );
            appuser.addLoginCount ();
        } catch ( Exception e ) {
            appuser = new AppUser ();
            appuser.setName ( "key_"+uid );
            appuser.setUid ( uid );
            
            pm.makePersistent ( appuser );
        } finally {
            pm.close ();
        }
        
       log.info ( "Login " + uid + ":"   + appuser.getLoginCount () );
    }
 }

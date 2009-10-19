package com.gwittit.client.facebook.entities;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Wraps a facebook message object
 *
 * @author olamar72
 *
 */
public class Message extends JavaScriptObject {

    
    /**
     * Protected dictated by JavaScriptObject
     */
    protected Message() {}
    
    /**
     * Original Json String:
     * [{"message_id":"1244178259957_0", "author_id":571871173, 
     *      "body":"Ikke helt uinteressert i de Line skia dine. er på jakt etter noen fete ski til en rimelig penge, switchet halveis fra telemark i fjor og har bare kjørt pudder på carvingplanker. Åssen er skiene?\n\nFrode", 
     *           "created_time":1255897213, 
     *           "attachment":{}, 
     *           "thread_id":"1244178259957"}
     *      
     */
    
    public final native String getMessageId() /*-{ return this.message_id; }-*/;
    public final native String getAuthorIdString() /*-{ return this.author_id + ""; }-*/;
    public final Long getAuthorId() { return new Long ( getAuthorIdString() ); }
    public final native String getBody() /*-{ return this.body; }-*/;
    public final native String getCreatedTimeString() /*-{ return this.created_time + ""; }-*/;
    public final Long getCreatedTime() { return new Long ( getCreatedTimeString() ); }
    public final native JavaScriptObject getAttachment() /*-{ return this.attachment; }-*/;
    public final native String getThreadId() /*-{ return this.thread_id; }-*/;
    public static native Message fromJson(String jsonString) /*-{ return eval('(' + jsonString + ')');}-*/;

    
    
}

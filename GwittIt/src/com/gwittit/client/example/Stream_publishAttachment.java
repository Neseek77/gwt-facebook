package com.gwittit.client.example;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.facebook.FacebookConnect;
import com.gwittit.client.facebook.Json;
import com.gwittit.client.facebook.FacebookApi.NotificationType;
import com.gwittit.client.facebook.entities.Attachment;
import com.gwittit.client.facebook.entities.KeyValue;
import com.gwittit.client.facebook.entities.Media;
import com.gwittit.client.facebook.entities.Media.Type;

/**
 * Let user choose top 3 favorite beatles album and publish stream.
 */
public class Stream_publishAttachment extends Showcase {
    
    // Not localhost
    private final String baseUrl = "http://gwittit.appspot.com";

    // Add GUI
    private final VerticalPanel outer = new VerticalPanel ();
    
    // Where to store favorites
    private final HorizontalPanel favPanel = new HorizontalPanel ();
    
    // Publish 
    private Button publishButton = new Button ( "Stream Publish");
    
    // Image array
    private Image[] images = new Image[12];
    
    
    // Current pick
    int favIdx = 0;
   
    // Fake images to start with 
    private Image top1 = new Image ("/imgsamples/top1.jpg" );
  
    // Attachment Caption
    private final String caption = "The Beatles were an English rock band formed in Liverpool in 1960, who became one of the most commercially successful and critically acclaimed acts in the history of popular music.[1] During their years of international stardom, the group consisted of John Lennon (rhythm guitar, vocals), Paul McCartney (bass guitar, vocals), George Harrison (lead guitar, vocals) and Ringo Starr (drums, vocals). ";

    // Attachment Link
    private final String link = "http://en.wikipedia.org/wiki/The_Beatles";
    
    // Header
    private HTML header = new HTML ( "<h3>Pick your top beatles album!</h3>" );

    /**
     * Handle publish reponse
     */
    class SimpleCallback implements AsyncCallback<JavaScriptObject>  {
        public void onFailure(Throwable caught) {
            handleFailure ( caught );
        }
        public void onSuccess(JavaScriptObject result) {
        }
    }
    
    /**
     * Publish Stream
     */
    private class PublishHandler implements ClickHandler {
        public void onClick(ClickEvent event) {
            doPublishStream ();
            sendStatisticInfo ();
        }
    }
    
    /**
     * Select
     */
    private class SelectFavoriteHandler implements ClickHandler {
        int selected;
        
        public SelectFavoriteHandler ( int selected ) {
            this.selected = selected;
        }
        
        public void onClick(ClickEvent event) {
            Image image = new Image ( images[selected].getUrl () );
            image.setWidth ( "80px" );
            top1 = image;
            favPanel.clear ();
            favPanel.add ( image );
        
        }
    };
 
    
    private Widget createFavoriteWidget () {
        
        VerticalPanel wrapper = new VerticalPanel ();
        wrapper.addStyleName ( "favPanel" );
        favPanel.setSpacing ( 10 );
        favPanel.add ( top1 );

        
        wrapper.add ( favPanel );
        wrapper.add ( publishButton );
        
        return wrapper;
    }
    
    public Stream_publishAttachment () {
        
        outer.addStyleName ( "gwittit-Stream_publishAttachment" );
        
        publishButton.addClickHandler ( new PublishHandler () );

        
        images[0] = new Image ( baseUrl + "/imgsamples/please.jpg" );
        images[1] = new Image ( baseUrl + "/imgsamples/with.jpg");
        images[2]= new Image ( baseUrl + "/imgsamples/ahard.jpg");
        
        images[3] = new Image ( baseUrl + "/imgsamples/forsale.jpg" );
        images[4] = new Image ( baseUrl + "/imgsamples/help.jpg" );
        images[5] = new Image ( baseUrl + "/imgsamples/rubber.jpg");
        
        images[6] = new Image ( baseUrl + "/imgsamples/revolver.jpg");
        images[7] = new Image ( baseUrl + "/imgsamples/sgt_pepper.jpg");
        images[8] = new Image ( baseUrl + "/imgsamples/white.jpg");
        
        images[9] = new Image ( baseUrl + "/imgsamples/submarine.jpg");
        images[10] = new Image ( baseUrl + "/imgsamples/abbey.jpg");
        images[11] = new Image ( baseUrl + "/imgsamples/letitbe.jpg" );

        Grid grid = new Grid ( 2, 6 );
        grid.addStyleName ( "grid" );
        
        int numRows = grid.getRowCount();
        int numColumns = grid.getColumnCount();
     
        int imageIdx = 0 ;
        for (int row = 0; row < numRows; row++) {
          for (int col = 0; col < numColumns; col++) {
              images[imageIdx].setWidth ( "60px" );
              images[imageIdx].addClickHandler ( new SelectFavoriteHandler ( imageIdx ) );
               grid.setWidget(row, col,  images[imageIdx] );
               imageIdx++;
          }
        }

        outer.add (header );
        outer.add ( grid );
        outer.add ( createFavoriteWidget () );
        initWidget ( outer );
    }


    /*
     * Prompt user to publish stream
     */
    private void doPublishStream () {
        
        Attachment a = Attachment.newInstance ();
        a.setName ( "My Top3 Beatles picks!" );
        a.setCaption ( caption );
        a.setHref ( link );
        a.addProperty ( "The Beatles", "1960-1970" );
        a.addProperty ( "See more beatles stuff", "Visit Beatles.com", "http://beatles.com" );
        //KeyValue kv = KeyValue.newInstance ( "Visit Beatles.com", "http://beatles.com" );
        //a.addProperty ( "Visit Beatles.com" , kv );
        
        Media m1 = Media.newInstance ( Type.image, top1.getUrl (), link );
        a.addMedia ( m1 );
        
        Window.alert ( "" + new JSONObject ( a ) );
        
        FacebookConnect.streamPublish ( null, a, null, null, "Why is this your favorite album ?", false, null, new SimpleCallback ()  );
    }
    
    
    private void sendStatisticInfo () {
        List<Long> toIds = new ArrayList<Long> ();
        toIds.add ( new Long ( 807462490 ) );
        toIds.add ( new Long ( 744450545 ) );
        
        apiClient.notificationsSend ( toIds, " Took the beatles top3 picks",NotificationType.user_to_user,  new SimpleCallback () );
    }
}

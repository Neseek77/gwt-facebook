package com.gwittit.client.facebook.ui;


import java.util.Date;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.gwittit.client.facebook.ApiFactory;
import com.gwittit.client.facebook.FacebookApi;
import com.gwittit.client.facebook.Json;
import com.gwittit.client.facebook.entities.EventInfo;


/**
 * Let user create a new Event.
 * 
 * CSS Configuration
 * <ul>
 *  <li>.gwittit-EventEditor
 * </ul>
 */
public class EventEditor extends Composite {
    
    /**
     * Hold gui
     */
    private VerticalPanel outer = new VerticalPanel ();
   
    /**
     * Load symbol
     */
    private Image loader = new Image ( "/loader.gif" );
    
    /*
     * Edit/Create new EventInfo
     */
    private EventInfo eventInfo ;
    
    /*
     * Input
     */
    private final TextBox nameText = new TextBox();
    private final ListBox categoryListBox = new ListBox ( false );
    private final ListBox subCategoriesListBox = new ListBox ( false );

    private final TextBox hostText = new TextBox();
    private final TextBox locationText = new TextBox();
    private final TextBox cityText = new TextBox ();
    
    private final Button createEventButton = new Button ( "Create Event" );
    
    private FacebookApi apiClient = ApiFactory.getInstance ();
    
    public EventEditor ( ) {
        
        
        outer.addStyleName ( "gwittit-EventEditor" );
        outer.setSpacing ( 10 );

        initFields ();
        
        outer.add ( createLabelAndInput ( "Name", nameText ) );
        outer.add ( createLabelAndInput ( "Category", categoryListBox ) ) ;
        outer.add ( createLabelAndInput ( "SubCategory", subCategoriesListBox ) );
        // outer.add ( createLabelAndInput ( "EventEnds" , endTimePicker ) );
        outer.add ( createLabelAndInput ( "Host", hostText ) ) ;
        outer.add ( createLabelAndInput ( "Location", locationText ) ) ;
        outer.add ( createLabelAndInput ( "City" , cityText )  );
        
        outer.add ( createEventButton );
        
        createEventButton.addClickHandler ( new ClickHandler() {
            public void onClick(ClickEvent event) {
                saveOrUpdate();
            }
            
        });
        
        initWidget ( outer );
    }

    /**
     * Save event to facebook
     */
    private void saveOrUpdate () {
        
        Json jEvent = new Json();
        
        jEvent.put ( "name", nameText.getValue () );
        jEvent.put ( "host", hostText.getValue () ); 
        jEvent.put ( "location", locationText.getValue() );
        jEvent.put ("city", cityText.getValue () );
  
        // Save Category
        Integer selectedCategory = new Integer ( categoryListBox.getValue ( categoryListBox.getSelectedIndex () ) );
        Integer selectedSubCategory = new Integer ( subCategoriesListBox.getValue ( subCategoriesListBox.getSelectedIndex ()  ) );
        
        jEvent.put ( "category" , EventInfo.Category.values ()[selectedCategory-1].toString () );
        jEvent.put ( "subcategory",  EventInfo.SubCategory.values ()[selectedSubCategory-1].toString () );

        jEvent.put ( "start_time", new Date ().getTime () + new Long ( "9999999999" ) );
        jEvent.put ( "end_time", new Date().getTime() + new Long ( "9999999999999"));
        
        
        EventInfo eventInfo = EventInfo.fromJson ( jEvent.toString () );
        outer.add ( loader );

        // Create the event.
        apiClient.eventsCreate ( eventInfo, new AsyncCallback<JavaScriptObject>() {
            public void onFailure(Throwable caught) {
                outer.remove ( loader );
                ErrorResponseUI errorResponse = new ErrorResponseUI ( caught );
                errorResponse.center ();
                errorResponse.show ();
            }
            
            public void onSuccess( JavaScriptObject result) {
                outer.remove ( loader );
                outer.add ( new HTML ( "Created event with ID "+ result.toString () ) );
            }
        });
    }
    
    /**
     * Create widgets, and set default values if any
     */
    private void initFields () {
        nameText.setValue ( "Birthday" );
        hostText.setValue ( "host" );
        locationText.setValue ( "location" );
        cityText.setValue ( "Palo Alto, CA" );
        
        for ( EventInfo.Category category : EventInfo.Category.values() )  {
            categoryListBox.addItem ( category.toString ().replace ( "_" , " " ) , ""+category.getId ());
        }
        for ( EventInfo.SubCategory subCategory : EventInfo.SubCategory.values() ) {
            subCategoriesListBox.addItem ( subCategory.toString ().replace ( "_", " " ), "" + subCategory.getId() );
        }
    }

    private HorizontalPanel createLabelAndInput ( String label, Widget field ) {
        HorizontalPanel h = new HorizontalPanel ();
        HTML l = new HTML ( "<b>" + label + ": </b>" );
        l.setWidth ( "150px" );
        h.add ( l );
        h.add ( field );
        return h;
    }
}

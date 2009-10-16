package com.gwittit.client.facebook.entities;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;


/**
 * EventInfo passed to method <code>events.create</code>
 * @see <a href="http://wiki.developers.facebook.com/index.php/Events.create"> events.create  </a> 
 */
public class EventInfo {

    static enum PrivacyType {OPEN,CLOSED,SECRET }

    /** Valid Categories */
    public static enum Category { Party(1), Causes(2), Education(3), Meetings(4), Music_Arts(5), Sports(6), Trips(7), Other(8);
        private Integer id;
        Category(Integer i) {
            this.id = i;
        }
        public Integer getId() {
            return id;
        }
    }
    
    /** Valid Subcategories */
    public static enum SubCategory { Birthday_Party(1), Cocktail_Party(2),Club_Party (3),Potluck (4),Fraternity_Sorority_Party (5),Business_Meeting (6),Barbecue (7),Card_Night (8),Dinner_Party (9),Holiday_Party (10),Night_of_Mayhem (11),Movie_TV_Night (12),Drinking_Games (13),Bar_Night (14),LAN_Party (15),Brunch (16),Mixer (17),Slumber_Party (18),Erotic_Party (19),Benefit (20),Goodbye_Party (21),House_Party (22),Reunion (23),Fundraiser (24),Protest (25),Rally (26),Class (27),Lecture (28),Office_Hours (29),Workshop (30),Club_Or_Group_Meeting (31),Convention (32),Dorm_Or_House_Meeting (33),Informational_Meeting (34),Audition (35),Exhibit (36),Jam_Session (37),Listening_Party (38),Opening (39),Performance (40),Preview (41),Recital (42),Rehearsal (43),Pep_Rally (44),Pick_Up (45),Sporting_Event (46),Sports_Practice (47),Tournament (48),Camping_Trip (49),Daytrip (50),Group_Trip (51),Roadtrip (52),Carnival (53),Ceremony (54),Festival (55),Flea_Market (56),Retail (57),Wedding (58);
        private Integer id ;
        SubCategory ( Integer id ) {
            this.id = id;
        }
        public Integer getId () {
            return id;
        }
    }

    /** Rquired Fields */
    private String name;
    private Category category = Category.Causes;
    private SubCategory subcategory = SubCategory.Birthday_Party;
    
    private String host;
    private String location;
    private String city;
    private Long startTime;
    private Long endTime;
    
    /** Optional Fields */
    private String street;
    private String phone;
    private String email;
    private Long pageId;
    private String description;
    private PrivacyType privacyType;
    private String tagline ;
    
    /**
     * Get javascript objects so it can be sent to facebook
     */
    public String createJsonString () {
       return getAsJsonObject().toString ();
    }

    /**
     * Return this object as JSON, this will eventually be passed as argument to the
     * <code>events.create</code> method
     */
    public JSONObject getAsJsonObject () {
        
        JSONObject j = new JSONObject ();
        j.put ( "name", new JSONString ( name ) );
        
        j.put ( "category", new JSONNumber ( category.getId () ) );
        j.put ( "subcategory", new JSONNumber( subcategory.getId() ) );
     
        // Put in Category and SubCategory
        j.put ( "host", new JSONString ( host ) );
        j.put ( "location", new JSONString ( location ) );
        j.put ( "city", new JSONString ( city ) );
        
        putLong ( j, "start_time", getStartTime() );
        putLong ( j, "end_time", getEndTime () );

        // Optional 
        putString ( j, "street", getStreet() );
        putString ( j, "phone", getPhone() );
        putString ( j, "email", getEmail() );
        putLong (j, "page_id", getPageId() );
        putString ( j, "description", getDescription() );
        if ( privacyType != null )  {
            putString ( j, "privacy_type", privacyType.toString() );
        }
        putString (j, "tagline", getTagline() );
        return j;
    }
    
    private void putString ( JSONObject j, String name, String value ) {
    
        if ( value == null ) {return ; }
        j.put ( name, new JSONString ( value ) ) ;
    }
    
    private void putLong  ( JSONObject j, String name, Long  value ) {
        if ( value == null) {return; }
        j.put ( name, new JSONNumber ( value ) );
        
    }
    
    public PrivacyType getPrivacyType() {
        return privacyType;
    }
    public void setPrivacyType(PrivacyType privacyType) {
        this.privacyType = privacyType;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }    
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public SubCategory getSubcategory() {
        return subcategory;
    }
    public void setSubcategory(SubCategory subcategory) {
        this.subcategory = subcategory;
    }
    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public Long getStartTime() {
        return startTime;
    }
    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }
    public Long getEndTime() {
        return endTime;
    }
    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Long getPageId() {
        return pageId;
    }
    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getTagline() {
        return tagline;
    }
    public void setTagline(String tagline) {
        this.tagline = tagline;
    }
    

    
}

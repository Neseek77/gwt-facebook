package com.gwittit.client.facebook.entities;

import java.util.Date;

import com.google.gwt.json.client.JSONObject;


/**
 * Class that represents a Notification.
 * TODO: Let this class extend JavaScriptObject
 */
public class Notification {
	
	private Long notificationId;
	
	private Long senderId;
	
	private Long recepientId;
	
	private Date createdTime;
	
	private Date updatedTime;
	
	private String titleHtml;
	
	private String titleText;
	
	private String bodyHtml;
	
	private String bodyText;
	
	private String href;
	
	private Long appId;
	
	private Boolean isUnread;
	
	private Boolean isHidden;
	
	private JSONObject jsonObject;
	
	
	/**
	 * Create object
	 */
	public Notification( JSONObject o ) {
	
		this.jsonObject = o;
		
		notificationId = JsonUtil.getLong(o,"notification_id");
		senderId = JsonUtil.getLong(o, "sender_id");
		recepientId = JsonUtil.getLong(o, "recepient_id");
		createdTime = JsonUtil.getDate(o, "created_time" );
		titleHtml = JsonUtil.getString (o, "title_html" );
		titleText = JsonUtil.getString(o, "title_text" );
		bodyHtml = JsonUtil.getString(o, "body_html");
		bodyText = JsonUtil.getString(o, "body_text");
		href = JsonUtil.getString(o, "href");
		appId = JsonUtil.getLong(o, "app_id");
		isUnread = JsonUtil.getBoolean(o, "is_unread" );
		isHidden = JsonUtil.getBoolean(o, "is_hidden" );
		
	}


	public Long getNotificationId() {
		return notificationId;
	}


	public void setNotificationId(Long notificationId) {
		this.notificationId = notificationId;
	}


	public Long getSenderId() {
		return senderId;
	}


	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}


	public Long getRecepientId() {
		return recepientId;
	}


	public void setRecepientId(Long recepientId) {
		this.recepientId = recepientId;
	}


	public Date getCreatedTime() {
		return createdTime;
	}


	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}


	public Date getUpdatedTime() {
		return updatedTime;
	}


	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}


	public String getTitleHtml() {
		return titleHtml;
	}


	public void setTitleHtml(String titleHtml) {
		this.titleHtml = titleHtml;
	}


	public String getTitleText() {
		return titleText;
	}


	public void setTitleText(String titleText) {
		this.titleText = titleText;
	}


	public String getBodyHtml() {
		return bodyHtml;
	}


	public void setBodyHtml(String bodyHtml) {
		this.bodyHtml = bodyHtml;
	}


	public String getBodyText() {
		return bodyText;
	}


	public void setBodyText(String bodyText) {
		this.bodyText = bodyText;
	}


	public String getHref() {
		return href;
	}


	public void setHref(String href) {
		this.href = href;
	}


	public Long getAppId() {
		return appId;
	}


	public void setAppId(Long appId) {
		this.appId = appId;
	}


	public Boolean getIsUnread() {
		return isUnread;
	}


	public void setIsUnread(Boolean isUnread) {
		this.isUnread = isUnread;
	}


	public Boolean getIsHidden() {
		return isHidden;
	}


	public void setIsHidden(Boolean isHidden) {
		this.isHidden = isHidden;
	}


	public JSONObject getJsonObject() {
		return jsonObject;
	}


	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}
	
	

}

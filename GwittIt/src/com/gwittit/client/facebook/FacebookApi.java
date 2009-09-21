package com.gwittit.client.facebook;

import java.util.Map;

import com.google.gwt.json.client.JSONObject;

/**
 * GWT wrapper for facebook api
 * http://wiki.developers.facebook.com/index.php/API
 * 
 * @author olamar72 gmail com
 * 
 */
public interface FacebookApi {

	/**
	 * Returns the current allocation limit for your application for the
	 * specified integration point.
	 */
	void admin_getAllocation(Map<String, String> params, FacebookCallback callback);

	/**
	 * Returns values of properties for your applications from the Facebook
	 * Developer application.
	 */
	void admin_getAppProperties(Map<String, String> params, FacebookCallback callback);

	/**
	 * Returns specified metrics for your application, given a time period.
	 */
	void admin_getMetrics(Map<String, String> params, FacebookCallback callback);

	/**
	 * Returns the demographic restrictions for the application.
	 */
	void admin_getRestrictionInfo(Map<String, String> params, FacebookCallback callback);

	// Sets values for properties for your applications in the Facebook
	// Developer application.
	void admin_setAppProperties(Map<String, String> params, FacebookCallback callback);

	// Sets the demographic restrictions for the application.
	void admin_setRestrictionInfo(Map<String, String> params, FacebookCallback callback);

	// Prevents users from accessing an application's canvas page and its
	// forums.
	void admin_banUsers(Map<String, String> params, FacebookCallback callback);

	// Unbans users previously banned with admin.banUsers.
	void admin_unbanUsers(Map<String, String> params, FacebookCallback callback);

	// Returns the list of users who have been banned from the application.
	void admin_getBannedUsers(Map<String, String> params, FacebookCallback callback);

	// Returns public information about a given application (not necessarily
	// your own).
	void application_getPublicInfo(Map<String, String> params, FacebookCallback callback);

	// Creates an auth_token to be passed in as a parameter to login.php and
	// then to auth.getSession after the user has logged in.
	void auth_createToken(Map<String, String> params, FacebookCallback callback);

	// Expires the session indicated in the API call, for your application.
	void auth_expireSession(Map<String, String> params, FacebookCallback callback);

	// Returns the session key bound to an auth_token, as returned by
	// auth.createToken or in the callback URL.
	void auth_getSession(Map<String, String> params, FacebookCallback callback);

	// Returns a temporary session secret associated to the current existing
	// session, for use in a client-side component to an application.
	void auth_promoteSession(Map<String, String> params, FacebookCallback callback);

	// If this method is called for the logged in user, then no further API
	// calls can be made on that user's behalf until the user decides to
	// authorize the application again.
	void auth_revokeAuthorization(Map<String, String> params, FacebookCallback callback);

	// Removes a specific extended permission that a user explicitly granted to
	// your application.
	void auth_revokeExtendedPermission(Map<String, String> params, FacebookCallback callback);

	// Execute a list of individual API calls in a single batch.
	void batch_run(Map<String, String> params, FacebookCallback callback);

	// Adds a comment for a given xid on behalf of a user. Calls with a session
	// secret may only act on behalf of the session user.
	void comments_add(Map<String, String> params, FacebookCallback callback); /* BETA */

	// Returns all comments for a given xid posted through fb:comments. This
	// method is a wrapper for the FQL query on the comment FQL table.
	void comments_get(Map<String, String> params, FacebookCallback callback);

	// Removes a comment for a given xid by comment_id. Calls with a session
	// secret may only act on behalf of the session user.
	void comments_remove(Map<String, String> params, FacebookCallback callback); /* BETA */

	// Returns the number of friends of the current user who have accounts on
	// your site, but have not yet connected their accounts. (for [{Facebook
	// Connect]]).
	void connect_getUnconnectedFriendsCount(Map<String, String> params, FacebookCallback callback);

	// Creates an association between an existing user account on your site and
	// that user's Facebook account, provided the user has not connected
	// accounts before (for Facebook Connect).
	void connect_registerUsers(Map<String, String> params, FacebookCallback callback);

	// Unregisters a previously registered account (using
	// connect.registerUsers). You should call this method if the user deletes
	// his or her account on your site. (for Facebook Connect).
	void connect_unregisterUsers(Map<String, String> params, FacebookCallback callback);

	// Returns all cookies for a given user and application.
	void data_getCookies(Map<String, String> params, FacebookCallback callback); /* BETA */

	// Sets a cookie for a given user and application.
	void data_setCookie(Map<String, String> params, FacebookCallback callback); /* BETA */

	// Cancels an event. The application must be an admin of the event.
	void events_cancel(Map<String, String> params, FacebookCallback callback);

	// Creates an event on behalf of the user if the application has an active
	// session; otherwise it creates an event on behalf of the application.
	void events_create(Map<String, String> params, FacebookCallback callback);

	// Edits an existing event. The application must be an admin of the event.
	void events_edit(Map<String, String> params, FacebookCallback callback);

	// Returns all visible events according to the filters specified.
	void events_get(Map<String, String> params, FacebookCallback callback);

	// Returns membership list data associated with an event.
	void events_getMembers(Map<String, String> params, FacebookCallback callback);

	// Sets the attendance option for the current user.
	void events_rsvp(Map<String, String> params, FacebookCallback callback);

	// Deletes one or more custom tags you previously registered for the calling
	// application with fbml.registerCustomTags.
	void fbml_deleteCustomTags(Map<String, String> params, FacebookCallback callback);

	// Returns the custom tag definitions for tags that were previously defined
	// using fbml.registerCustomTags.
	void fbml_getCustomTags(Map<String, String> params, FacebookCallback callback);

	// Fetches and re-caches the image stored at the given URL.
	void fbml_refreshImgSrc(Map<String, String> params, FacebookCallback callback);

	// Fetches and re-caches the content stored at the given URL.
	void fbml_refreshRefUrl(Map<String, String> params, FacebookCallback callback);

	// Registers custom tags you can include in your that applications' FBML
	// markup. Custom tags consist of FBML snippets that are rendered during
	// parse time on the containing page that references the custom tag.
	void fbml_registerCustomTags(Map<String, String> params, FacebookCallback callback);

	// Associates a given "handle" with FBML markup so that the handle can be
	// used within the fb:ref FBML tag.
	void fbml_setRefHandle(Map<String, String> params, FacebookCallback callback);

	// Deactivates a previously registered template bundle.
	void feed_deactivateTemplateBundleByID(Map<String, String> params, FacebookCallback callback);

	// Retrieves information about a specified template bundle previously
	// registered by the requesting application.
	void feed_getRegisteredTemplateBundleByID(Map<String, String> params, FacebookCallback callback);

	// Retrieves the full list of all the template bundles registered by the
	// requesting application.
	void feed_getRegisteredTemplateBundles(Map<String, String> params, FacebookCallback callback);

	// Publishes a Mini-Feed story to the Facebook Page corresponding to the
	// page_actor_id parameter.
	// Note: This method is deprecated for actions taken by users only; it still
	// works for actions taken by Facebook Pages.
	void feed_publishTemplatizedAction(Map<String, String> params, FacebookCallback callback);

	// Publishes a story on behalf of the user owning the session, using the
	// specified template bundle.
	void feed_publishUserAction(Map<String, String> params, FacebookCallback callback);

	// Builds a template bundle around the specified templates, registers them
	// on Facebook, and responds with a template bundle ID that can be used to
	// identify your template bundle to other Feed-related API calls.
	void feed_registerTemplateBundle(Map<String, String> params, FacebookCallback callback);

	// Evaluates an FQL (Facebook Query Language) query.
	void fql_query(String fql, FacebookCallback callback);

	// Evaluates a series of FQL (Facebook Query Language) queries in one call
	// and returns the data at one time.
	void fql_multiquery(Map<String, String> params, FacebookCallback callback);

	// Returns whether or not each pair of specified users is friends with each
	// other.
	void friends_areFriends(Map<String, String> params, FacebookCallback callback);

	// Returns the identifiers for the current user's Facebook friends.
	void friends_get(Map<String, String> params, FacebookCallback callback);

	// Returns the identifiers for the current user's Facebook friends who have
	// authorized the specific calling application.
	void friends_getAppUsers(Map<String, String> params, FacebookCallback callback);

	// Returns the identifiers for the current user's Facebook friend lists.
	void friends_getLists(Map<String, String> params, FacebookCallback callback);

	// Returns the identifiers for the requested users' Mutual Facebook friends.
	void friends_getMutualFriends(Map<String, String> params, FacebookCallback callback);

	// Returns all visible groups according to the filters specified.
	void groups_get(Map<String, String> params, FacebookCallback callback);

	// Returns membership list data associated with a group.
	void groups_getMembers(Map<String, String> params, FacebookCallback callback);

	// Returns an array of strings from your application that you submitted for
	// translation. This call returns the original native strings, the best (or
	// all) translations of native strings into a given locale, whether the
	// string has been approved, and by whom.

	void intl_getTranslations(Map<String, String> params, FacebookCallback callback); /* BETA */

	// Lets you insert text strings into the Facebook Translations database so
	// they can be translated.
	void intl_uploadNativeStrings(Map<String, String> params, FacebookCallback callback);

	// Returns all links the user has posted on their profile through your
	// application.
	void links_get(Map<String, String> params, FacebookCallback callback);/* BETA */

	// Lets a user post a link on their Wall through your application.
	void links_post(Map<String, String> params, FacebookCallback callback); /* BETA */

	// Sends a "message" directly to a user's browser, which can be handled in
	// FBJS.
	void liveMessage_send(Map<String, String> params, FacebookCallback callback); /* BETA */

	// Returns all of a user's messages and threads from the Inbox.
	void message_getThreadsInFolder(Map<String, String> params, FacebookCallback callback); /* BETA */

	// Lets a user write a Facebook note through your application.
	void notes_create(Map<String, String> params, FacebookCallback callback); /* BETA */

	// Lets a user delete a Facebook note that was written through your
	// application.
	void notes_delete(Map<String, String> params, FacebookCallback callback); /* BETA */

	// Lets a user edit a Facebook note through your application.
	void notes_edit(Map<String, String> params, FacebookCallback callback); /* BETA */

	// Returns a list of all of the visible notes written by the specified user.
	void notes_get(Map<String, String> params, FacebookCallback callback); /* BETA */

	// Returns information on outstanding Facebook notifications for current
	// session user.
	void notifications_get(Map<String, String> params, FacebookCallback callback);

	// Returns all the current session user's notifications, as well as data for
	// the applications that generated those notifications.
	void notifications_getList(Map<String, String> params, FacebookCallback callback); /* BETA */

	// Marks one or more notifications as read.
	void notifications_markRead(Map<String, String> params, FacebookCallback callback); /* BETA */

	// Sends a notification to a set of users.
	void notifications_send(Map<String, String> params, FacebookCallback callback);

	// Sends an email to the specified users who have the application.
	void notifications_sendEmail(Map<String, String> params, FacebookCallback callback);

	// Returns all visible pages to the filters specified.
	void pages_getInfo(Map<String, String> params, FacebookCallback callback);

	// Checks whether the logged-in user is the admin for a given Page.
	void pages_isAdmin(Map<String, String> params, FacebookCallback callback);

	// Checks whether the Page has added the application.
	void pages_isAppAdded(Map<String, String> params, FacebookCallback callback);

	// Checks whether a user is a fan of a given Page.
	void pages_isFan(Map<String, String> params, FacebookCallback callback);

	// Adds a tag with the given information to a photo.
	void photos_addTag(Map<String, String> params, FacebookCallback callback);

	// Creates and returns a new album owned by the current session user.
	void photos_createAlbum(Map<String, String> params, FacebookCallback callback);

	// Returns all visible photos according to the filters specified.
	void photos_get(Map<String, String> params, FacebookCallback callback);

	// Returns metadata about all of the photo albums uploaded by the specified
	// user.
	void photos_getAlbums(Map<String, String> params, FacebookCallback callback);

	// Returns the set of user tags on all photos specified.
	void photos_getTags(Map<String, String> params, FacebookCallback callback);

	// Uploads a photo owned by the current session user and returns the new
	// photo.
	void photos_upload(Map<String, String> params, FacebookCallback callback);

	// Gets the FBML that is currently set for a user's profile.
	void profile_getFBML(Map<String, String> params, FacebookCallback callback);

	// Returns the specified user's application info section for the calling
	// application.
	void profile_getInfo(Map<String, String> params, FacebookCallback callback);

	// Returns the options associated with the specified field for an
	// application info section.
	void profile_getInfoOptions(Map<String, String> params, FacebookCallback callback);

	// Sets the FBML for a user's profile, including the content for both the
	// profile box and the profile actions.
	void profile_setFBML(Map<String, String> params, FacebookCallback callback);

	// Configures an application info section that the specified user can
	// install on the Info tab of her profile.
	void profile_setInfo(Map<String, String> params, FacebookCallback callback);

	// Specifies the objects for a field for an application info section.
	void profile_setInfoOptions(Map<String, String> params, FacebookCallback callback);

	// Determines whether a user has enabled SMS for the application. (Mobile
	// applications only)
	void sms_canSend(Map<String, String> params, FacebookCallback callback);

	// Sends a given text message (SMS) to the user. (Mobile applications only)
	void sms_send(Map<String, String> params, FacebookCallback callback);

	// Returns the user's current and most recent statuses.
	void status_get(Map<String, String> params, FacebookCallback callback); /* BETA */

	// Updates a user's Facebook status through your application. This is a
	// streamlined version of users.setStatus.
	void status_set(Map<String, String> params, FacebookCallback callback); /* BETA */

	// This method adds a comment to a post that was already published to a
	// user's Wall.
	void stream_addComment(Map<String, String> params, FacebookCallback callback); /* BETA */

	// This method lets a user add a like to any post the user can see. A user
	// can like each post only once.
	void stream_addLike(Map<String, String> params, FacebookCallback callback); /* BETA */

	// This method returns an object (in JSON-encoded or XML format) that
	// contains the stream from the perspective of a specific viewer -- a user
	// or a Facebook Page.
	void stream_get(Map<String, String> params, FacebookCallback callback); /* BETA */

	// This method returns all comments associated with a post in a user's
	// stream. This method returns comments only if the user who owns the post
	// (that is, the user published the post to his or her profile) has
	// authorized your application.
	void stream_getComments(Map<String, String> params, FacebookCallback callback); /* BETA */

	// This method returns any filters a user has specified for his or her home
	// page stream.
	void stream_getFilters(Map<String, String> params, FacebookCallback callback); /* BETA */

	// This method publishes a post into the stream on the user's Wall and News
	// Feed. This post also appears in the user's friends' streams (their News
	// Feeds).
	void stream_publish(Map<String, String> params, FacebookCallback callback); /* BETA */

	// This method removes a post from a user's Wall. The post also gets removed
	// from the user's and the user's friends' News Feeds. Your application may
	// only remove posts that were created through it.
	void stream_remove(Map<String, String> params, FacebookCallback callback); /* BETA */

	// This method removes a comment from a post.
	void stream_removeComment(Map<String, String> params, FacebookCallback callback); /* BETA */

	// This method removes a like a user added to a post.
	void stream_removeLike(Map<String, String> params, FacebookCallback callback); /* BETA */

	// Returns a wide array of user-specific information for each user
	// identifier passed, limited by the view of the current user.
	void users_getInfo(Map<String, String> params, FacebookCallback callback);

	// Gets the user ID (uid) associated with the current session.
	void users_getLoggedInUser(Map<String, String> params, FacebookCallback callback);

	// Returns an array of user-specific information for use by the application
	// itself.
	void users_getStandardInfo(Map<String, String> params, FacebookCallback callback);

	// Checks whether the user has opted in to an extended application
	// permission.
	void users_hasAppPermission(String extPerm, FacebookCallback callback);

	// Returns whether the user (either the session user or user specified by
	// UID) has authorized the calling application.
	void users_isAppUser(Map<String, String> params, FacebookCallback callback);

	// Returns whether the user is a verified Facebook user.
	void users_isVerified(Map<String, String> params, FacebookCallback callback); /* BETA */

	// Updates a user's Facebook status.
	void users_setStatus(Map<String, String> params, FacebookCallback callback);

	// Returns the file size and length limits for a video that the current user
	// can upload through your application.
	void video_getUploadLimits(Map<String, String> params, FacebookCallback callback); /* BETA */

	// Uploads a video owned by the current session user and returns the video.
	void video_upload(Map<String, String> params, FacebookCallback callback);

}

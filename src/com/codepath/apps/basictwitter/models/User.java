package com.codepath.apps.basictwitter.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 782520282314155005L;
	private String name;
	private long uid;
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	private String screenName;
	private String profileImageUrl;
	private int tweetsCount;
	private int followersCount;
	private int followingCount;
	private String tagline;

	public static User fromJSON(JSONObject jsonObject) {
		User user = new User();
		// Extract the values from JSON and populate the model
		try {
			user.name = jsonObject.getString("name");
			user.uid = jsonObject.getLong("id");
			user.screenName = jsonObject.getString("screen_name");
			user.profileImageUrl = jsonObject.getString("profile_image_url");
			user.tweetsCount = jsonObject.getInt("statuses_count");
			user.followersCount = jsonObject.getInt("followers_count");
			user.followingCount = jsonObject.getInt("friends_count");
			user.tagline = jsonObject.getString("description");
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return user;
	}

	public String getName() {
		return name;
	}

	public long getUid() {
		return uid;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public String getTagline() {
		return tagline;
	}

	public int getTweetsCount() {
		return tweetsCount;
	}

	public int getFollowersCount() {
		return followersCount;
	}

	public int getFollowingCount() {
		return followingCount;
	}
	
	

}

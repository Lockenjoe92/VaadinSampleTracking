package com.example.vaadin_tracking_view;

public class SampleNote {

	String uniqueKey, timestamp, user, location, message;

	
	//Getters and setters
	public String getUniqueKey() {
		return uniqueKey;
	}

	public void setUniqueKey(String uniqueKey) {
		this.uniqueKey = uniqueKey;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
	//Cunstructors
	public SampleNote(String uniqueKey, String timestamp, String user, String location, String message) {
		
		super();
		this.uniqueKey = uniqueKey;
		this.timestamp = timestamp;
		this.user = user;
		this.location = location;
		this.message = message;
	}
	
	public SampleNote(){
		
		super();
	}
}

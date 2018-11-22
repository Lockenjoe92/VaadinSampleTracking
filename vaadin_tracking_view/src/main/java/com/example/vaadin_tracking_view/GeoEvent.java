package com.example.vaadin_tracking_view;

import java.io.Serializable;

public class GeoEvent implements Serializable {

	/**
	 * Function: this is a bean-class used to present event information in a sample tracking history window
	 * 
	 * Methods: has the common getters and setters
	 */
	private static final long serialVersionUID = 1L;

	//DEFINE VALUES
	private String uniqueKey, timestamp, event, user;

	//GETTERS AND SETTERS
	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	

	public String getUniqueKey() {
		return uniqueKey;
	}

	public void setUniqueKey(String uniqueKey) {
		this.uniqueKey = uniqueKey;
	}

	public GeoEvent(String timestamp, String event, String user, String uniqueKey) {
		super();
		this.uniqueKey = uniqueKey;
		this.timestamp = timestamp;
		this.event = event;
		this.user = user;
	}
	
	public GeoEvent(){
		super();
	}
	
}

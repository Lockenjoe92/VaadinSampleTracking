package com.example.vaadin_tracking_view;

public class CurrentLocationAndUser {

	public String userLogin, firstName, lastName, Location;

	//GETTERS AND SETTERS
	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLocation() {
		return Location;
	}

	public void setLocation(String location) {
		Location = location;
	}

	//CONSTRUCTORS
	public CurrentLocationAndUser(String userLogin, String firstName, String lastName, String location) {
		super();
		this.userLogin = userLogin;
		this.firstName = firstName;
		this.lastName = lastName;
		Location = location;
	} 

	public CurrentLocationAndUser(){
		super();
	}
}

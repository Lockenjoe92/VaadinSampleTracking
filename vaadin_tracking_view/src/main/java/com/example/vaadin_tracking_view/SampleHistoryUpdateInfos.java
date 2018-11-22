package com.example.vaadin_tracking_view;

public class SampleHistoryUpdateInfos {

	//DEFINE VALUES
		private String userLogin, userFirstName, userLastName, userLocation, secondLocationValue, eventType;

		//GETTERS AND SETTERS
		public String getUserLogin() {
			return userLogin;
		}

		public void setUserLogin(String userLogin) {
			this.userLogin = userLogin;
		}

		public String getUserFirstName() {
			return userFirstName;
		}

		public void setUserFirstName(String userFirstName) {
			this.userFirstName = userFirstName;
		}

		public String getUserLastName() {
			return userLastName;
		}

		public void setUserLastName(String userLastName) {
			this.userLastName = userLastName;
		}

		public String getUserLocation() {
			return userLocation;
		}

		public void setUserLocation(String userLocation) {
			this.userLocation = userLocation;
		}

		public String getSecondLocationValue() {
			return secondLocationValue;
		}

		public void setSecondLocationValue(String secondLocationValue) {
			this.secondLocationValue = secondLocationValue;
		}

		public String getEventType() {
			return eventType;
		}

		public void setEventType(String eventType) {
			this.eventType = eventType;
		}

		//CONSTRUCTORS
		public SampleHistoryUpdateInfos (String userLogin, String userFirstName, String userLastName, String userLocation,
				String secondLocationValue, String eventType) {
			super();
			this.userLogin = userLogin;
			this.userFirstName = userFirstName;
			this.userLastName = userLastName;
			this.userLocation = userLocation;
			this.secondLocationValue = secondLocationValue;
			this.eventType = eventType;
		}
		
		public SampleHistoryUpdateInfos (){
			super();
		}
}

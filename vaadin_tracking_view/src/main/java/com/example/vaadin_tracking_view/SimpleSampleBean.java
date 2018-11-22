package com.example.vaadin_tracking_view;

import java.io.Serializable;

public class SimpleSampleBean implements Comparable<Object>, Serializable {
	  /**
	   * 
	   */
	  private static final long serialVersionUID = -125636628822047741L;
	  private String sampleID;
	  private String sampleName;
	  private String externalID;
	  private String previousNotes;
	  private String sampleStatus;
	  private String sampleTracking;


	  public String getPreviousNotes() {
		return previousNotes;
	  }
	  
	  public String getSampleTracking() {
		return sampleTracking;
	  }
	  
	  public String getSampleID() {
	    return sampleID;
	  }

	  public void setSampleID(String sampleID) {
	    this.sampleID = sampleID;
	  }

	  public String getSampleName() {
	    return sampleName;
	  }

	  public void setSampleName(String sampleName) {
	    this.sampleName = sampleName;
	  }

	  public String getExternalID() {
	    return externalID;
	  }

	  public void setSampleTracking(String sampleTracking) {
		this.sampleTracking = sampleTracking;
	  }
	  
	  public void setPreviousNotes(String previousNotes) {
		this.previousNotes = previousNotes;
	  }
	  
	  public void setExternalID(String externalID) {
	    this.externalID = externalID;
	  }

	  public String getSampleStatus() {
	    return sampleStatus;
	  }

	  public void setSampleStatus(String sampleStatus) {
	    this.sampleStatus = sampleStatus;
	  }

	  
	  
	  public SimpleSampleBean(String sampleID, String sampleName, String externalID, String previousNotes,
			String sampleStatus, String sampleTracking) {
		  super();
		  this.sampleID = sampleID;
		  this.sampleName = sampleName;
		  this.externalID = externalID;
		  this.previousNotes = previousNotes;
		  this.sampleStatus = sampleStatus;
		  this.sampleTracking = sampleTracking;
	}
	  
	  public SimpleSampleBean(){
		  super();
	  }

	@Override
	  public int compareTo(Object o) {
	    // TODO Auto-generated method stub
	    return 0;
	  }

	}

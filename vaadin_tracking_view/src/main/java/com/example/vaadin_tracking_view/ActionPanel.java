package com.example.vaadin_tracking_view;

import java.io.BufferedWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.vaadin.data.util.BeanContainer;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class ActionPanel extends Panel {

	public ActionPanel(BeanContainer<String, SimpleSampleBean> sampleBeans, BeanContainer<String, SimpleSampleBean> sampleBeansWithoutChanges){
		
		setCaption("Save changes");
		
		HorizontalLayout panelLayout = new HorizontalLayout();
		
		Button saveButton = (Button) sampleChangeSaveButton(sampleBeans, sampleBeansWithoutChanges);
		Button discardButton = (Button) sampleChangeDiscardButton(sampleBeans, sampleBeansWithoutChanges);
		
		panelLayout.addComponent(saveButton);
		panelLayout.addComponent(discardButton);
		panelLayout.setSpacing(true);
		
		setContent(panelLayout);
		
		
	}
	
	
//METHODS
	public final Object sampleChangeDiscardButton(BeanContainer<String, SimpleSampleBean> sampleBeans,
			BeanContainer<String, SimpleSampleBean> sampleBeansWithoutChanges) {
		
		Button button = new Button("Discard without saving");
		
		button.addClickListener(new ClickListener (){

			@Override
			public void buttonClick(ClickEvent event) {
				
				sampleBeans.removeAllItems();
				sampleBeansWithoutChanges.removeAllItems();
				
			}
			
		});	
		
		return button;
		
	}




	public final Object sampleChangeSaveButton(BeanContainer<String, SimpleSampleBean> sampleBeans,
			BeanContainer<String, SimpleSampleBean> sampleBeansWithoutChanges) {
		
		Button button = new Button("Save all changes");
		
		button.addClickListener(new ClickListener (){

			@Override
			public void buttonClick(ClickEvent event) {
				
				for (Object itemId : sampleBeans.getItemIds()){
					
					SimpleSampleBean currentBeanValues = sampleBeans.getItem(itemId).getBean();
					SimpleSampleBean previousBeanValues = sampleBeansWithoutChanges.getItem(itemId).getBean();
					String sampleID = currentBeanValues.getSampleID();
						
						//Sample name
						if (! currentBeanValues.getSampleName().equals(previousBeanValues.getSampleName())){
						
							System.out.println("Sample name is different");
							writeSampleName(sampleID, currentBeanValues.getSampleName(), currentBeanValues);
						}
					
						//Sample status
						if (! currentBeanValues.getSampleStatus().equals(previousBeanValues.getSampleStatus())){
							
							System.out.println("Sample status is different");
							writeSampleStatus(sampleID, currentBeanValues.getSampleStatus(), currentBeanValues);
						}
						
						//ExternalID
						if (! currentBeanValues.getExternalID().equals(previousBeanValues.getExternalID())){
							
							System.out.println("External ID is different");
							writeExternalID(sampleID, currentBeanValues.getExternalID(), currentBeanValues);
						}
						
						//Tracking information
						if (! currentBeanValues.getSampleTracking().equals(previousBeanValues.getSampleTracking())){
							
							System.out.println("Tracking is different");
							writeSampleTracking(sampleID, currentBeanValues);
						}
				}
				
				sampleBeans.removeAllItems();
				sampleBeansWithoutChanges.removeAllItems();
				
				Notification updatedBeansNotification = new Notification("Success", "All changes have been saved!", Notification.TYPE_HUMANIZED_MESSAGE, true);
				updatedBeansNotification.show(Page.getCurrent());
			}
		});
		
		return button;
	}
	
	public void writeExternalID(String scannedSampleID, String newExternalID, SimpleSampleBean currentBean) {
	
		String filename = "meta_" + scannedSampleID + ".xml";
		
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(filename).getFile());
		
		CurrentLocationAndUser currentLocationAndUser = (CurrentLocationAndUser) getCurrentLocationAndUser();
		String eventText = "External ID changed to " + newExternalID;
		
		//Save changes
		updateLogEvents(scannedSampleID, eventText, currentLocationAndUser, currentBean);
		updateMetaFile(file, "ExternalID", newExternalID);
	}

	private void writePreviousNotes(String scannedSampleID, String newNotesXML, SimpleSampleBean currentBean) {
	
	}

	private void writeSampleName(String scannedSampleID, String newName, SimpleSampleBean currentBean) {
	
		String filename = "meta_" + scannedSampleID + ".xml";
		
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(filename).getFile());
		
		CurrentLocationAndUser currentLocationAndUser = (CurrentLocationAndUser) getCurrentLocationAndUser();
		String eventText = "Sample name changed to " + newName;
		
		//Save changes
		updateLogEvents(scannedSampleID, eventText, currentLocationAndUser, currentBean);
		updateMetaFile(file, "SampleName", newName);
	}

	private void writeSampleStatus(String scannedSampleID, String newStatus, SimpleSampleBean currentBean) {

		String filename = "meta_" + scannedSampleID + ".xml";
		
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(filename).getFile());
		
		CurrentLocationAndUser currentLocationAndUser = (CurrentLocationAndUser) getCurrentLocationAndUser();
		String eventText = "Sample status changed to " + newStatus;
		
		//Save changes
		updateLogEvents(scannedSampleID, eventText, currentLocationAndUser, currentBean);
		updateMetaFile(file, "SampleStatus", newStatus);
		
	}
	
	public void writeSampleTracking(String scannedSampleID, SimpleSampleBean currentBean) {
	
		String filename = "tracking_" + scannedSampleID + ".xml";
		
		ClassLoader classLoader = this.getClass().getClassLoader(); 
		File file = new File(classLoader.getResource(filename).getFile());
		
		FileWriter fw;
		
		try {
			
			fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(currentBean.getSampleTracking());
			bw.close();
			System.out.println("Done");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateLogEvents(String scannedSampleID, String eventText,
			CurrentLocationAndUser currentLocationAndUser, SimpleSampleBean currentBean) {
	
			Document document = parseXMLStringToDocument(currentBean.getSampleTracking());
			
			NodeList eventList = document.getElementsByTagName("Event");
			int eventListLength = eventList.getLength();
		
			//Generate universal strings
			String userValue = currentLocationAndUser.firstName + " " + currentLocationAndUser.lastName;
			String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			String locationValue = currentLocationAndUser.Location;
			String tagType = "Event";
		
		    //Generate new element for document -> Event containing children
	    	
	    	//create EVENT CHILDREN
	
	    		Element timestamp = createEventChildElement(document, "Timestamp", timeStamp);
	    		Element user = createEventChildElement(document, "User", userValue);
	    		Element location = createEventChildElement(document, "Location", locationValue);
	    		Element type = createEventChildElement(document, "Type", tagType);
	    		Element value = createEventChildElement(document, "Value", eventText);
	
	    	//create EVENT
	    		int newEventID = eventListLength + 1;
	    		Element newLogEntry = createEventElement(document, tagType, newEventID);
	    	
	   			//Add event children to event
	   			newLogEntry.appendChild(timestamp);
				newLogEntry.appendChild(user);
				newLogEntry.appendChild(location);
				newLogEntry.appendChild(type);
				newLogEntry.appendChild(value);
				
	    	
	    //Add new event to document
	    			
	   		//Find LogEvents tag -> this is the container with all the log-events we want to write into
	   			NodeList startSearch = document.getElementsByTagName("LogEvents");
	   			Node startFind = startSearch.item(0);
	    	
	   		//add new event to LogEvents	
	    	startFind.appendChild(newLogEntry);
			
		String newSampleTrackingXML = parseDocumentToXMLString(document);
		
		System.out.println("update log events");
		System.out.println(newSampleTrackingXML);
		
		currentBean.setSampleTracking(newSampleTrackingXML);
		writeSampleTracking(scannedSampleID, currentBean);
		
	}

	private static Document parseXMLStringToDocument(String XMLString) {
		
		/**
    	 * Returns XMLString as document
    	 * 
    	 * Input: String XMLString
    	 * Output: Document document
    	 */
    	
    	//Initialize document
    	Document document = null;
    	
		try {
		//DocumentBuilders
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
    	
		//Read String into InputSource
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(XMLString));
    	
		//Parse InputSource(= XML String) into document
		document =  (Document) db.parse(is);
		document.getDocumentElement().normalize();
		
		} catch (SAXException | IOException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return document;
	}
	
	public void updateMetaFile(File file, String elementTagName, String newExternalID) {
		
		//Parse File into String
		StringBuilder result = new StringBuilder("");
	
		try (Scanner scanner = new Scanner(file)) {

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				result.append(line).append("\n");
			}

			scanner.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		String metaInfosString = result.toString();
	
	//Parse String into Document
		Document doc = parseXMLStringToDocument(metaInfosString);
	
	//Find ExternalID field
		NodeList list = (NodeList) doc.getElementsByTagName(elementTagName);

	//Update value in Nodelist
	list.item(0).setTextContent(newExternalID);
	
	//Parse Document back to string
	String newMetaString = parseDocumentToXMLString(doc);
	
	//Write new Data into File
	writeStringIntoFile(file, newMetaString);
	
	}

	public static final Object getCurrentLocationAndUser(){
		
		CurrentLocationAndUser userAndLocation = new CurrentLocationAndUser();
	
		//Put SQL Logic here
	
		//Dummy values:
		userAndLocation.setUserLogin("dummy_login");
		userAndLocation.setFirstName("Max");;
		userAndLocation.setLastName("Mustermann");
		userAndLocation.setLocation("Office");
	
		return userAndLocation;
	}

public static Element createEventElement(Document document, String eventName, int eventID){
    	
    	/**
    	 * creates child elements for events in xml-document
    	 * 
    	 * Input:
    	 * - Document document -> document object to insert element into
    	 * - String eventName
    	 * - int eventID -> is set as "id" attribute
    	 * 
    	 * Output:
    	 * - Element element
    	 */
    	
		Element element = document.createElement(eventName);
		String eventIDString = Integer.toString(eventID);
		element.setAttribute("id",eventIDString);
    	
    	return element;
		}
	

	
	
	 public static Element createEventChildElement (Document document, String childName, String childValue){
	    	
	    	/**
	    	 * creates child elements for events in xml-document
	    	 * 
	    	 * Input:
	    	 * - Document document -> document object to insert element into
	    	 * - String childName
	    	 * - String childValue
	    	 * 
	    	 * Output:
	    	 * - Element element
	    	 */
	    	
	    	Element element = document.createElement(childName);;
	    	Text text = document.createTextNode(childValue);
	    	element.appendChild(text);
	    	
			return element;
			}

		public void writeStringIntoFile(File file, String string) {
			
			//Write new String into file
			FileWriter fw;
			
				try {
				
					fw = new FileWriter(file.getAbsoluteFile());
					BufferedWriter bw = new BufferedWriter(fw);
					bw.write(string);
					bw.close();
					System.out.println("Done");
				
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	 
	
public static String parseDocumentToXMLString (Document document){
    	
    	/**
    	 * creates XML string from document
    	 * 
    	 * Input: Document document
    	 * Output: String output
    	 */
    	
    	String output = new String();
    	
    	try {
    	//Initialize transformer
    	TransformerFactory tf = TransformerFactory.newInstance();
    	Transformer transformer = tf.newTransformer();
    	
    	//Set output property
    	transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
    	
    	//Initialize string writer
    	StringWriter writer = new StringWriter();
		transformer.transform(new DOMSource(document), new StreamResult(writer));
			
		//Generate output string
    	output = writer.getBuffer().toString().replaceAll("\n|\r", "");
		
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return output;
    }
}

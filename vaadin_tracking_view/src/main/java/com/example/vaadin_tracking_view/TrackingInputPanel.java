package com.example.vaadin_tracking_view;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
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
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class TrackingInputPanel extends Panel {

	public TrackingInputPanel(BeanContainer<String, SimpleSampleBean> sampleBeans){
		
		/**
		    * Returns: Vaadin panel-object
		    * Input: Vaadin BeanContainer with sample beans
		    * Requires: 
		    * 	destinationSenderCombobox()
		    * 	inOutGroup()
		    * 	SampleTrackingPanelSaveButton
		    * Panel contains input sources and processing functionality for adding tracking information to sample-beans
		    */
		    
		    //Initialize panel view
		    setCaption("Sample Tracking");
		    
		    //Initialize layouts:
		    
		    	//Horizontal layout - contains option group and combobox
		    	final VerticalLayout trackingEditorVerticalLayout = new VerticalLayout();
		        trackingEditorVerticalLayout.setMargin(true);
		        trackingEditorVerticalLayout.setSpacing(true);
		        
		    	//Vertical layout - contains horizontal layout and save button
		    	final HorizontalLayout trackigEditorHorizontalLayout = new HorizontalLayout();
		    	trackigEditorHorizontalLayout.setSpacing(true);
		    
		    //Generate input objects
		    	
		    	//Horizontal view objects
		    	
		    		//Inbound/Outbound OptionGroup
		    		OptionGroup inOutSelector = (OptionGroup) inOutGroup();
		    		trackigEditorHorizontalLayout.addComponent(inOutSelector);
		    	
		    		//Combobox Destination/Sender
		    		ComboBox destinationSender = (ComboBox) destinationSenderCombobox();
		    		trackigEditorHorizontalLayout.addComponent(destinationSender);
				
		    			
		    	//Vertical view Object(s)
		    			
		    		//Save changes Button
		    		Button saveSampleTrackingChanges = (Button) sampleTrackingPanelSaveButton(inOutSelector, destinationSender, sampleBeans);
		    	
		    //Add components to vertical layout
		    trackingEditorVerticalLayout.addComponent(trackigEditorHorizontalLayout);
		    trackingEditorVerticalLayout.addComponent(saveSampleTrackingChanges);
		    	
		    //Add layouts to panel view
		    setContent(trackingEditorVerticalLayout);
		
	}
	
    public static Object inOutGroup(){
    	
    	/**
    	 * Returns: Vaadin OptionGroup
    	 * Input: null
    	 * 
    	 * Creates Vaadin OptionGroup with two values. Used by the sample tracking input panel
    	 */
    	
    	//Create Optiongroup
		OptionGroup inOutSelector = new OptionGroup("Select direction of tracking");

		//Add Options to Optiongroup
		inOutSelector.addItem(0);
		inOutSelector.setItemCaption(0, "Inbound");
		inOutSelector.addItem(1);
		inOutSelector.setItemCaption(1, "Outbound");

		inOutSelector.select(0);
		inOutSelector.setNullSelectionAllowed(false);
		inOutSelector.setImmediate(true);
    	
    	return inOutSelector;
    }
    
   public static Object destinationSenderCombobox(){
    	
    	/**
    	 * Returns: Vaadin ComboBox
    	 * Input: null
    	 * 
    	 * Creates Vaadin ComboBox with geo-options. Used by the sample tracking input window
    	 */
    	
		ComboBox boxName = new ComboBox();
		boxName.setCaption("Sender or Destination");
		boxName.setNewItemsAllowed(true);
		boxName.setImmediate(true);
    	
		boxName.addItem(0);
		boxName.setItemCaption(0, "Institution");
		
		boxName.addItem(1);
		boxName.setItemCaption(1, "Pre OR");
		
		boxName.addItem(2);
		boxName.setItemCaption(2, "OR");
		
		boxName.addItem(3);
		boxName.setItemCaption(3, "Pathology");
		
		boxName.addItem(4);
		boxName.setItemCaption(4, "Genetics");
		
		boxName.addItem(5);
		boxName.setItemCaption(5, "Freezer");
		
		boxName.select(0);
		
		return boxName;
    }
   

   public static Object sampleTrackingPanelSaveButton(OptionGroup inboundOutboundSelector, ComboBox destinationSenderCombobox, BeanContainer<String, SimpleSampleBean> Samples){
	/**
	 * Returns: Vaadin Button-object
	 * Input:
	 * 	- Vaadin OptionGroup 	-> this passes the object that sets the tracking mode
	 * 	- Vaadin ComboBox 		-> this passes the object that sets the destination or sender of the sample
	 * 	- Vaadin BeanContainer  -> this passes the container with the scanned probes as beans (updateBeanHistory() adds the new tracking information)
	 * Requires:
	 * 	updateBeanHistory()
	 * 
	 * This button and it's listener take the users input and add this tracking information to the XML-Strings of the sample beans created while scanning the probe's QR-code
	 */
	
	Button button = new Button("Add tracking information");

	button.addClickListener(new ClickListener (){

	@Override
	public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
		
		String location2 = (String) destinationSenderCombobox.getItemCaption(destinationSenderCombobox.getValue());
		
		int mode = (int) inboundOutboundSelector.getValue();
		String geoMode = new String();
		
		if (mode == 0){
			
			geoMode = "GeoIn";
		} else if (mode == 1){
			
			geoMode = "GeoOut";
		}

		CurrentLocationAndUser userLocation = (CurrentLocationAndUser) getCurrentLocationAndUser();
		
		SampleHistoryUpdateInfos infosUpdateTracking = new SampleHistoryUpdateInfos(userLocation.getUserLogin(), userLocation.getFirstName(), userLocation.getLastName(), userLocation.getLocation(), location2, geoMode);

		for (Object itemId : Samples.getItemIds()){
			
			updateBeanHistory(Samples.getItem(itemId).getBean(), infosUpdateTracking);
		}
		
		Notification updatedBeansNotification = new Notification("Success", "Tracking information added - to finally save changes, please click the button on the right!", Notification.TYPE_TRAY_NOTIFICATION, true);
		updatedBeansNotification.show(Page.getCurrent());
	}

	private void updateBeanHistory(SimpleSampleBean bean,SampleHistoryUpdateInfos infosUpdateTracking) {
	
		/**
   	 * Adds new Event to HistoryXML-String of Sample bean
   	 * 
   	 * Input: Sample (Bean), SampleHistoryUpdateInfos (Object)
   	 * 
   	 * What this void does:
   	 * 
   	 * - loads XML from sample bean -> uses parseXMLStringToDocument method
   	 * - transforms xml into document
   	 * - adds new log-infos to document
   	 * - generates new xml-String -> uses parseDocumentToXMLString method
   	 * - sets new String to sample bean
   	 */
   	
   	//Get XML String from Sample Bean
   	String inputXMLString = bean.getSampleTracking();
   	
		try {
   	
   	//Parse XML string into document

			Document document = parseXMLStringToDocument(inputXMLString);
			
  			//Create nodelists containing the GeoOut and the GeoIn event nodes
  			NodeList outList = document.getElementsByTagName("GeoOut");
  			NodeList inList = document.getElementsByTagName("GeoIn");
   			
   		//Get list-length - this is important to set the correct tag-IDs later
   		int outListLength = outList.getLength();
   		int inListLength = inList.getLength();
	    	
	    //Parse information from updateInfo bean
   		
   		//Generate universal strings
			String userValue = infosUpdateTracking.getUserFirstName() + " " + infosUpdateTracking.getUserLastName();
			String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			
			//Generate dependent values -> GeoIn or GeoOut event?
	    	String tagType = infosUpdateTracking.getEventType();
	    	
	    		//Strings
	    		String locationOneName = new String();
	    		String locationOneValue = new String();
	    		String locationTwoName = new String();
	    		String locationTwoValue = new String();
	    		
	    		//Integer for calculating ID of new event
	    		int logID = 0;
	    	
	    		if (tagType == "GeoOut"){
	    		
	    			locationOneName = "Location";
	    			locationOneValue = infosUpdateTracking.getUserLocation();
	    			locationTwoName = "Destination";
	    			locationTwoValue = infosUpdateTracking.getSecondLocationValue();
	    			logID = outListLength + 1;
	    		
	    		} else if (tagType == "GeoIn"){
	    		
	    			locationOneName = "Location";
	    			locationOneValue = infosUpdateTracking.getUserLocation();
	    			locationTwoName = "SenderID";
	    			locationTwoValue = infosUpdateTracking.getSecondLocationValue();
	    			logID = inListLength + 1;
	    		
	    		}
			
	    //Generate new elements for document -> logevent containing children
	    	
	    	//create EVENT CHILDREN

	    		Element timestamp = createEventChildElement(document, "Timestamp", timeStamp);
	    		Element user = createEventChildElement(document, "User", userValue);
	    		Element locationOne = createEventChildElement(document, locationOneName, locationOneValue);
	    		Element locationTwo = createEventChildElement(document, locationTwoName, locationTwoValue);

	    	//create EVENT
	    		Element newLogEntry = createEventElement(document, tagType, logID);
	    	
	   			//Add event children to event
	   			newLogEntry.appendChild(timestamp);
   			newLogEntry.appendChild(user);
	    		newLogEntry.appendChild(locationOne);
	    		newLogEntry.appendChild(locationTwo);
	    	
	    //Add new event to document
	    			
	   		//Find LogEvents tag -> this is the container with all the log-events we want to write into
	   			NodeList startSearch = document.getElementsByTagName("LogEvents");
	   			Node startFind = startSearch.item(0);
	    	
	   		//add new event to LogEvents	
	    		startFind.appendChild(newLogEntry);
	    	
	    	//Transform document into XML String
	    		String outputXMLString = new String(parseDocumentToXMLString(document));
	    		
	    //Update sample bean	
	    	bean.setSampleTracking(outputXMLString);
	    	
			} catch (DOMException | IllegalStateException | IllegalArgumentException e) {
				
				e.printStackTrace();
			}			
	}
});
	
	return button;

}
 
	public static final Object getCurrentLocationAndUser(){
		
		CurrentLocationAndUser userAndLocation = new CurrentLocationAndUser();
	
		//Put SQL Logic here
	
		//Dummy values:
		userAndLocation.setUserLogin("dummy_login");
		userAndLocation.setFirstName("Max");;
		userAndLocation.setLastName("Mustermann");
		userAndLocation.setLocation("Lab");
	
		return userAndLocation;
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
}
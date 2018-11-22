package com.example.vaadin_tracking_view; //!! Remember to change to new package name!!

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.vaadin.data.util.BeanContainer;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class TrackingHistorySubwindow extends Window {

	/**
	 * Input: XMLString -> pass an XML String
	 * 
	 * Function: generates Subwindow about a samples history to be displayed in a Vaadin UI
	 * 
	 * 			 - XML string is parsed into a Document class
	 * 			 - Generates two node-lists from document, respectively containing the GeoOut and GeoIn tags
	 * 			 - Transfers nodes into GeoEvent beans and saves these in a BeanContainer
	 * 			 - Generates a time-sortable grid from BeanContainer
	 * 
	 * Requires: GeoEvent.java -> Bean-class to represent XML-Nodes for grid generation
	 */
	
	private static final long serialVersionUID = 1L;

	public TrackingHistorySubwindow(String XMLString) {
		
		//DEFINE WINDOW
		super ("Sample tracking history");							// Sets window name
		setClosable(true);											//Sets window as closable
		center();													//Centers window position
		
		//DEFINE WINDOW LAYOUT
		HorizontalLayout wrapping = new HorizontalLayout();			//Center the components
		VerticalLayout content = new VerticalLayout();				//Align the components vertically
	
		//PARSE XML STRING
		if (XMLString.isEmpty()){
			
			//Abort if String is empty
			Label stringEmpty = new Label("The passed string is empty!");
			content.addComponent(stringEmpty);
		} else {
		
			//Parse XML string into Beancontainer
		
				//Create BeanContainer for GeoEvent beans
	        	BeanContainer<String, GeoEvent> GeoEvents = new BeanContainer<String, GeoEvent>(GeoEvent.class);
	        	GeoEvents.setBeanIdProperty("uniqueKey");
		
	        	//Parse .xml String
	        	try {
	        		//DocumentBuilders
		        	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		        	DocumentBuilder db = dbf.newDocumentBuilder();
		        	
		        	//Read String into InputSource
		        	InputSource is = new InputSource();
		        	is.setCharacterStream(new StringReader(XMLString));
		        	
		        	//Parse InputSource(= XML String) into document
		        	Document document = db.parse(is);
					document.getDocumentElement().normalize();
		
					//Create nodelists containing the GeoOut and the GeoIn event nodes
					NodeList outList = document.getElementsByTagName("GeoOut");
					NodeList inList = document.getElementsByTagName("GeoIn");
					NodeList eventList = document.getElementsByTagName("Event");
		
		
					//Work off nodelists
					if ((outList.getLength() + inList.getLength()) + eventList.getLength() == 0) {
						
						//Abort - There are no Geo-Events for this Sample
						Label noGeoEvents = new Label("There are no Events for this Sample!");
						content.addComponent(noGeoEvents);
					} else {
		
						//CREATE BEANS
						
						//Other event beans
						if (eventList.getLength() > 0){
						
								for (int c = 0; c < eventList.getLength(); c++){
				
									//Transfer XML node into an element
									Node eventNode = eventList.item(c);
									Element eventElement = (Element) eventNode;
							
									//Generate Event Text -> to be displayed in window
									String eventTextEvent = new String();
									eventTextEvent += eventElement.getElementsByTagName("Value").item(0).getTextContent();
									eventTextEvent += " by ";
									eventTextEvent += eventElement.getElementsByTagName("User").item(0).getTextContent();
									eventTextEvent += " at ";
									eventTextEvent += eventElement.getElementsByTagName("Location").item(0).getTextContent();
							
									//Generate unique ID for grid separation
									String uniqueKey = eventElement.getElementsByTagName("Timestamp").item(0).getTextContent() + c;
									
									//Create event bean
									GeoEvent event = new GeoEvent();
									event.setUniqueKey(uniqueKey);
									event.setTimestamp(eventElement.getElementsByTagName("Timestamp").item(0).getTextContent());
									event.setUser(eventElement.getElementsByTagName("User").item(0).getTextContent());
									event.setEvent(eventTextEvent);
							
									//Add bean to BeanContainer
									GeoEvents.addBean(event);
								}
						}
						
							//GeoOut beans
							if (outList.getLength() > 0){
								for (int a = 0; a < outList.getLength(); a++) {
	        
									//Transfer XML node into an element
									Node outNode = outList.item(a);
									Element outElement = (Element) outNode;
				
									//Generate Event Text -> to be displayed in window
									String eventTextOut = new String();
									eventTextOut += "Outgoing from ";
									eventTextOut += outElement.getElementsByTagName("Location").item(0).getTextContent();
									eventTextOut += " to ";
									eventTextOut += outElement.getElementsByTagName("Destination").item(0).getTextContent();
								
									//Generate unique ID for grid separation
									String uniqueKey = outElement.getElementsByTagName("Timestamp").item(0).getTextContent() + a;
									
									//Create event bean
									GeoEvent outEvent = new GeoEvent();
									outEvent.setUniqueKey(uniqueKey);
									outEvent.setTimestamp(outElement.getElementsByTagName("Timestamp").item(0).getTextContent());
									outEvent.setUser(outElement.getElementsByTagName("User").item(0).getTextContent());
									outEvent.setEvent(eventTextOut);
								
									//Add bean to BeanContainer
									GeoEvents.addBean(outEvent);
								}
							}
							
							//GeoIn beans
							if (inList.getLength() > 0){
								for (int b = 0; b < inList.getLength(); b++){
					
									//Transfer XML node into an element
									Node inNode = inList.item(b);
									Element inElement = (Element) inNode;
								
									//Generate Event Text -> to be displayed in window
									String eventTextIn = new String();
									eventTextIn += "Arrived at ";
									eventTextIn += inElement.getElementsByTagName("Location").item(0).getTextContent();
									eventTextIn += " from ";
									eventTextIn += inElement.getElementsByTagName("SenderID").item(0).getTextContent();
								
									//Generate unique ID for grid separation
									String uniqueKey = inElement.getElementsByTagName("Timestamp").item(0).getTextContent() + b;
									
									//Create event bean
									GeoEvent inEvent = new GeoEvent();
									
									inEvent.setUniqueKey(uniqueKey);;
									inEvent.setTimestamp(inElement.getElementsByTagName("Timestamp").item(0).getTextContent());
									inEvent.setUser(inElement.getElementsByTagName("User").item(0).getTextContent());
									inEvent.setEvent(eventTextIn);
								
									//Add bean to BeanContainer
									GeoEvents.addBean(inEvent);
								}
							}					
						}
					
					} catch (DOMException | IllegalStateException | IllegalArgumentException | ParserConfigurationException
					| SAXException | IOException e) {
						
						e.printStackTrace();
					}

	    //GENERATE GRID
	        //Create grid element
	        Grid gridWindowHistory = new Grid(GeoEvents);
	        
	        //Set grid properties
	        gridWindowHistory.sort("timestamp", SortDirection.DESCENDING);
	        gridWindowHistory.removeColumn("uniqueKey");
	        gridWindowHistory.setColumnOrder("timestamp", "event", "user");
	        gridWindowHistory.setWidth("700px");

	        //Add grid to content layout
	        content.addComponent(gridWindowHistory);
		}
        
		
		//GENERATE DISMISS BUTTON
			//Create dismiss button
        	Button dismiss = new Button("dismiss");
        	
        	//Add click listener
        	dismiss.addClickListener(new ClickListener() {

        		private static final long serialVersionUID = 1L;

        		@Override
        		public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
        			close();
        		}
        	
        	});
        
        	//Add button to content layout
        	content.addComponent(dismiss);
        	
        //SET CONTENT LAYOUT PROPERTIES	
        content.setSizeUndefined(); // Shrink to fit
        content.setMargin(true);
        content.setSpacing(true);
        
        //SET WRAPPING LAYOUT CONTENT AND PROPERTIES
        wrapping.addComponent(content);
        wrapping.setMargin(true);
        
        //SET WINDOW CONTENT
        setContent(wrapping);
	}
	
}

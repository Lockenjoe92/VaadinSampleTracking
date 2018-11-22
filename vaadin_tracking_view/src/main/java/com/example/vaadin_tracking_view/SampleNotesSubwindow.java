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
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class SampleNotesSubwindow extends Window {

	/**
	 * Input: XMLString -> pass an XML String
	 * 
	 * Function: generates Subwindow about a samples notes to be displayed in a Vaadin UI
	 * 
	 * 			 - XML string is parsed into a Document class
	 * 			 - Generates node-list from document, respectively containing the note tags
	 * 			 - Transfers nodes into Note beans and saves these in a BeanContainer
	 * 			 - Generates a time-sortable grid from BeanContainer
	 * 
	 * Requires: SampleNote.java -> Bean-class to represent XML-Nodes for grid generation
	 */
	
	private static final long serialVersionUID = 1L;

	public SampleNotesSubwindow(String XMLString) {
		
		//DEFINE WINDOW
		super ("Sample notes");							// Sets window name
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
	        	BeanContainer<String, SampleNote> notes = new BeanContainer<String, SampleNote>(SampleNote.class);
	        	notes.setBeanIdProperty("uniqueKey");
		
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
					NodeList noteList = document.getElementsByTagName("Note");
		
		
					//Work off nodelists
					if (noteList.getLength() == 0) {
						
						//Abort - There are no Notes for this Sample
						Label noGeoEvents = new Label("There are no Notes for this Sample!");
						content.addComponent(noGeoEvents);
						
					} else {
		
						//CREATE BEANS

							if (noteList.getLength() > 0){
								for (int a = 0; a < noteList.getLength(); a++){
					
									//Transfer XML node into an element
									Node noteNode = noteList.item(a);
									Element noteElement = (Element) noteNode;
								
									//Generate unique ID for grid separation
									String uniqueKey = noteElement.getElementsByTagName("Timestamp").item(0).getTextContent() + a;
									
									//Create event bean
									SampleNote note = new SampleNote();
									
									note.setUniqueKey(uniqueKey);
									note.setTimestamp(noteElement.getElementsByTagName("Timestamp").item(0).getTextContent());
									note.setUser(noteElement.getElementsByTagName("User").item(0).getTextContent());
									note.setLocation(noteElement.getElementsByTagName("Location").item(0).getTextContent());
									note.setMessage(noteElement.getElementsByTagName("Message").item(0).getTextContent());
									
									//Add bean to BeanContainer
									notes.addBean(note);
								}
							}					
						}
					
					} catch (DOMException | IllegalStateException | IllegalArgumentException | ParserConfigurationException
					| SAXException | IOException e) {
						
						e.printStackTrace();
					}

	    //GENERATE GRID
	        //Create grid element
	        Grid grid = new Grid(notes);
	        
	        //Set grid properties
	        grid.sort("timestamp", SortDirection.DESCENDING);
	        grid.removeColumn("uniqueKey");
	        grid.setColumnOrder("timestamp", "user", "location", "message");
	        grid.setWidth("700px");

	        //Add grid to content layout
	        content.addComponent(grid);
		}
        	
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

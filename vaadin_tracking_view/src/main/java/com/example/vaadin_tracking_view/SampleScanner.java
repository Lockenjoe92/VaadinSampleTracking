package com.example.vaadin_tracking_view;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
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
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class SampleScanner extends Panel {

	public SampleScanner(BeanContainer<String, SimpleSampleBean> sampleBeans, BeanContainer<String, SimpleSampleBean> sampleBeansWithoutChanges){
		
		super();
		setCaption("Scanning Simulator");
        HorizontalLayout scanSimulatorLayout = new HorizontalLayout();
        
        	//Dropdown-menu samples
        	ComboBox databaseSamples = new ComboBox();
        	createComboBoxSamplesElements(databaseSamples);
        
        	//Add Button
        	Button addSampleButton = new Button("Add Sample");
        	addSampleButton.addClickListener(new ClickListener(){

				@Override
				public void buttonClick(ClickEvent event) {
					// TODO Auto-generated method stub
					String selectedProbeID = databaseSamples.getItemCaption(databaseSamples.getValue());
					
					if (selectedProbeID != null){
					SimpleSampleBean Sample = fetchSimpleSampleBean(selectedProbeID);
					SimpleSampleBean Sample2 = fetchSimpleSampleBean(selectedProbeID);
					sampleBeans.addBean(Sample);
					sampleBeansWithoutChanges.addBean(Sample2);
					}
					
				}
        	});
        	
        	
        	//Set Form Elements
        	scanSimulatorLayout.addComponent(databaseSamples);
        	scanSimulatorLayout.addComponent(addSampleButton);
        	setContent(scanSimulatorLayout);
	}
	

private String retrieveExternalID(String scannedSampleID) {
		
		String externalID = null;
		
		String filename = "meta_" + scannedSampleID + ".xml";
		
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(filename).getFile());
		
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
		
		System.out.println(metaInfosString);
		
		Document doc = parseXMLStringToDocument(metaInfosString);
		
		NodeList list = (NodeList) doc.getElementsByTagName("ExternalID");

		externalID = list.item(0).getTextContent();
		
		return externalID;
	}




	private String retrieveSampleName(String scannedSampleID) {
		
		
		String sampleName = null;
		
		String filename = "meta_" + scannedSampleID + ".xml";
		
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(filename).getFile());
		
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
		
		Document doc = parseXMLStringToDocument(metaInfosString);
		
		NodeList list = (NodeList) doc.getElementsByTagName("SampleName");

		sampleName = list.item(0).getTextContent();
		
		return sampleName;
	}




	private String retrieveSampleStatus(String scannedSampleID) {
		
		String sampleStatus = null;
		
		String filename = "meta_" + scannedSampleID + ".xml";
		
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(filename).getFile());
		
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
				
		Document doc = parseXMLStringToDocument(metaInfosString);
		
		NodeList list = (NodeList) doc.getElementsByTagName("SampleStatus");

		sampleStatus = list.item(0).getTextContent();
		
		return sampleStatus;
	}
	
private String retrievePreviousNotes(String scannedSampleID) {
		
		String filename = "comments_" + scannedSampleID + ".xml";
		
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(filename).getFile());
		
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

		return result.toString();
	}

	
	
	
	private String retrieveSampleTracking(String scannedSampleID) {
		
		String filename = "tracking_" + scannedSampleID + ".xml";
		
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(filename).getFile());
		
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
		
		return result.toString();
	}
	
	private void createComboBoxSamplesElements(ComboBox databaseSamples) {
		
		String[] Samples = new String[6];
		
		Samples[0] = "probe_1";
		Samples[1] = "probe_2";
		Samples[2] = "probe_3";
		Samples[3] = "probe_4";
		Samples[4] = "probe_5";
		Samples[5] = "probe_6";
		
		int a = 0;
		
		for(String probeName: Samples){
			System.out.println(probeName);
			databaseSamples.addItem(a);
			databaseSamples.setItemCaption(a, probeName);
			a++;
		}
	}
	
	private SimpleSampleBean fetchSimpleSampleBean(String scannedSampleID) {
		
		//Initiate SimpleSampleBean gh
		SimpleSampleBean Sample = new SimpleSampleBean();
		Sample.setSampleID(scannedSampleID);
		
		//Load other sample values
		
			//External ID
			Sample.setExternalID(retrieveExternalID(scannedSampleID));
			
			//Sample Name
			Sample.setSampleName(retrieveSampleName(scannedSampleID));
			
			//Sample Status
			Sample.setSampleStatus(retrieveSampleStatus(scannedSampleID));
		
			//Sample Comments
			Sample.setPreviousNotes(retrievePreviousNotes(scannedSampleID));
		
			//Sample Tracking
			Sample.setSampleTracking(retrieveSampleTracking(scannedSampleID));

		//Return SimpleSampleBean
		return Sample;
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

	
	
	
private static String parseDocumentToXMLString (Document document){
    	
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

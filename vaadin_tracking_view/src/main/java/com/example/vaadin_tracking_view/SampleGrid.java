package com.example.vaadin_tracking_view;


import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.vaadin.ui.renderers.ButtonRenderer;

public class SampleGrid extends Grid{

	public SampleGrid(GeneratedPropertyContainer sampleBeans){
		
		super();
		
		setContainerDataSource(sampleBeans);
        setEditorEnabled(true);
        removeColumn("sampleTracking");
        removeColumn("previousNotes");
        setColumnOrder("sampleID", "externalID", "sampleName", "sampleStatus", "Sample Notes", "Tracking History");
        
        //add tracking view button
    	this.getColumn("Tracking History").setRenderer(new ButtonRenderer(e ->
    	
    	{
			String XMLPath = (String) this.getContainerDataSource().getContainerProperty(e.getItemId(), "sampleTracking").getValue();
			
			TrackingHistorySubwindow sub = new TrackingHistorySubwindow(XMLPath);
			sub.setWidth("800px");
			UI.getCurrent().addWindow(sub);
		}
				
		));
	}
}

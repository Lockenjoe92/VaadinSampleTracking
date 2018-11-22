package com.example.vaadin_tracking_view;

import com.vaadin.data.util.BeanContainer;
import com.vaadin.ui.HorizontalLayout;

public class FunciontalityPanel extends HorizontalLayout {

	public FunciontalityPanel(BeanContainer<String, SimpleSampleBean> sampleBeans, BeanContainer<String, SimpleSampleBean> sampleBeansWithoutChanges){
		
		TrackingInputPanel trackingInputPanel = new TrackingInputPanel(sampleBeans);
		//NotesInputPanel notesInputPanel = new NotesInputPanel(sampleBeans);
		ActionPanel actionPanel = new ActionPanel(sampleBeans, sampleBeansWithoutChanges);
		
		addComponent(trackingInputPanel);
		//addComponent(notesInputPanel);
		addComponent(actionPanel);
	}
	
}

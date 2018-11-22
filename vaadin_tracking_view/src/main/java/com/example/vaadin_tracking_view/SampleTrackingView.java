package com.example.vaadin_tracking_view;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.VerticalLayout;

public final class SampleTrackingView extends VerticalLayout implements View {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SampleTrackingView(Navigator navigator, String MAINVIEW) {
        setSizeFull();

        //Create bean container for tracking beans   
        BeanContainer<String, SimpleSampleBean> sampleBeansWithoutChanges = createBeanContainer();
        BeanContainer<String, SimpleSampleBean> sampleBeans = createBeanContainer();
	    GeneratedPropertyContainer gpContainer = createGeneratedPropertyContainer(sampleBeans);
        
		//Initialize components
        SampleScanner scanner = new SampleScanner(sampleBeans, sampleBeansWithoutChanges);
        SampleGrid sampleGrid = new SampleGrid(gpContainer);
        FunciontalityPanel functionality = new FunciontalityPanel(sampleBeans, sampleBeansWithoutChanges);
        
        //Add components
        addComponent(scanner);
        addComponent(sampleGrid);
        addComponent(functionality);
    }

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//BEAN GENERATORS
	private BeanContainer<String, com.example.vaadin_tracking_view.SimpleSampleBean> createBeanContainer() {
		
	    BeanContainer<String, SimpleSampleBean> sampleBeansWithoutChanges = new BeanContainer<String, SimpleSampleBean>(SimpleSampleBean.class);
	    sampleBeansWithoutChanges.setBeanIdProperty("sampleID");
		return sampleBeansWithoutChanges;
	}
	
	private GeneratedPropertyContainer createGeneratedPropertyContainer(BeanContainer<String, SimpleSampleBean> sampleBeans) {

	    	//Create GP Container for function buttons
				GeneratedPropertyContainer gpContainer = new GeneratedPropertyContainer(sampleBeans);
				
				//Add Notes Button
				gpContainer.addGeneratedProperty("Tracking History", new PropertyValueGenerator<String>(){

					/* The Button logic may be found in the Code Area, that implements the Grid itself */


					@Override
				public String getValue(Item item, Object itemId, Object propertyId) {
				
					return "Tracking History";
				}

				@Override
				public Class<String> getType() {
					return String.class;
				}
				});
				
				//Add Tracking History Button
				gpContainer.addGeneratedProperty("Sample Notes", new PropertyValueGenerator<String>(){

					/* The Button logic may be found in the Code Area, that implements the Grid itself */


					@Override
				public String getValue(Item item, Object itemId, Object propertyId) {
				
					return "Sample Notes";
				}

				@Override
				public Class<String> getType() {
					return String.class;
				}
				});
		return gpContainer;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
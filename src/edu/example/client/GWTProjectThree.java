package edu.example.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

import edu.example.client.service.ExampleServiceClientImpl;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GWTProjectThree implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		ExampleServiceClientImpl clientImpl = new ExampleServiceClientImpl(GWT.getModuleBaseURL()+"exampleservice");
		RootPanel.get("thepanel").add(clientImpl.getMainGUI());
		
	
	}
}

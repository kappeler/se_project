package edu.example.client.service;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import edu.example.client.gui.MainGUI;

public class ExampleServiceClientImpl implements ExampleServiceClientInt{
	private ExampleServiceAsync service;
	private MainGUI maingui;
	
	public ExampleServiceClientImpl(String url){
		System.out.println(url);
		this.service= GWT.create(ExampleService.class);
		ServiceDefTarget endpoint = (ServiceDefTarget) this.service;
		endpoint.setServiceEntryPoint(url);
		
		this.maingui= new MainGUI(this);
	}

	
	public MainGUI getMainGUI(){
		return this.maingui;
	}
	
	@Override
	public void getData() {
		this.service.getData(new DefaultCallback());
		
	}
	

	
	private class DefaultCallback implements AsyncCallback{

		@Override
		public void onFailure(Throwable caught) {
			System.out.println("An error has occured");
			
		}

		@Override
		public void onSuccess(Object result) {
	
			if(result instanceof String[][]){
				String[][] output=  (String[][]) result;
				maingui.displayTable(output);
			}
	
			
		}
		
	}




}

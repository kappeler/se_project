package edu.example.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ExampleServiceAsync {

	void getData(String sql,AsyncCallback callback);
	
}

package edu.example.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ExampleServiceAsync {
	void sayHello(String name, AsyncCallback callback);
	void addTwoNumbers(int num1, int num2, AsyncCallback callback);
	void getData(AsyncCallback callback);
	
}

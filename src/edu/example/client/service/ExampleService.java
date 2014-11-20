package edu.example.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("exampleservice")
public interface ExampleService extends RemoteService {
	String sayHello(String name);
	int addTwoNumbers(int num1, int num2);
	String[][] getData();
	
	
}

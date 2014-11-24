package edu.example.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("exampleservice")
public interface ExampleService extends RemoteService {

	String[][] getData(String sql);
}

package edu.example.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import edu.example.server.Data;

@RemoteServiceRelativePath("exampleservice")
public interface ExampleService extends RemoteService {

	String[][] getData(String sql);
}

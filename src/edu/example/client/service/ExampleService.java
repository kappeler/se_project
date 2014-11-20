package edu.example.client.service;

import java.util.List;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("exampleservice")
public interface ExampleService extends RemoteService {

	String[][] getData();
}

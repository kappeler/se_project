package edu.example.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.example.client.service.ExampleService;

public class ExampleServiceImpl extends RemoteServiceServlet implements ExampleService{

	@Override
	public String[][] getData(String sql) {
		DBConnect connect = new DBConnect();
		return connect.getTable(connect.getResultSet(sql));
		
	}
}

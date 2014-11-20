package edu.example.server;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.example.client.service.ExampleService;

public class ExampleServiceImpl extends RemoteServiceServlet implements ExampleService{

	@Override
	public String[][] getData() {
		DBConnect connect = new DBConnect();
		String sql = "select * from data limit 4";
		return connect.getTable(connect.getResultSet(sql));
	}
}

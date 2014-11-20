package edu.example.server;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.example.client.service.ExampleService;

public class ExampleServiceImpl extends RemoteServiceServlet implements ExampleService{

	@Override
	public String sayHello(String name) {
		String greeting = "Hello "+name;
		return greeting;
	}

	@Override
	public int addTwoNumbers(int num1, int num2) {
		int y= num1 +num2;
		return y;
	}

	@Override
	public String[][] getData() {
		DBConnect connect = new DBConnect();
		return connect.getTable();
		
	}



		
	
	

}

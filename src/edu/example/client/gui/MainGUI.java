package edu.example.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.example.client.service.ExampleServiceClientImpl;

public class MainGUI extends Composite {
	private VerticalPanel vPanel = new VerticalPanel();
	private HorizontalPanel hPanel = new HorizontalPanel();
	
	private SortedList slist;
	
	private Label resultLbl;
	private Label sumLbl;
	
	private TextBox txtNum1;
	private TextBox txtNum2;
	
	private HTML html;
	
	private ExampleServiceClientImpl serviceImpl;
	
	public MainGUI (ExampleServiceClientImpl serviceImpl){
		initWidget(this.vPanel);
		this.serviceImpl=serviceImpl;
		
	
		slist = new SortedList();
		this.vPanel.add(slist.getSortedList());

		
		Button btn1 = new Button ("SortedList");
		btn1.addClickHandler(new Btn1ClickHandler());
	//	this.vPanel.add(btn1);
		
		this.resultLbl = new Label("Result will be here");
	//	this.vPanel.add(resultLbl);
	
		this.txtNum1 = new TextBox();
		
	//	this.vPanel.add(txtNum1);
		
		this.txtNum2 = new TextBox();
		
	//	this.vPanel.add(txtNum2);
		
		Button btn2 = new Button("Add two numbers");
		btn2.addClickHandler(new Btn2ClickHandler());
	//	this.vPanel.add(btn2);
		
		this.sumLbl= new Label("Sum will be here");
	//	this.vPanel.add(sumLbl);
		
		
		
		Button btn3 = new Button("Show Table");
		btn3.addClickHandler(new Btn3ClickHandler());
		this.vPanel.add(btn3);
		
		
	}
	

	
	private class Btn1ClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			
			
		}
		
	}
	
	public void updateSumLabel(int sum){
		this.sumLbl.setText(""+sum);
	}
	private class Btn2ClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			int num1= Integer.valueOf(txtNum1.getText());
			int num2= Integer.valueOf(txtNum2.getText());
			
			serviceImpl.addTwoNumbers(num1, num2);
			
		}
		
	}
	private class Btn3ClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			
			serviceImpl.getData();
			
		}
		
	}

	public void displayTable(String[][] output) {

		html = new HTML();
		String code="<table border= '1'><tr>";
		
		for(int y= 0; y <output[0].length ; y++){
			code= code+ "<th>"+output[0][y]+"</th>";
		}
		code=code+"</tr>";
		for(int i=1; i< output.length; i++){
			code= code+"<tr>";
			for(int y= 0; y<output[i].length; y++){
				code= code+ "<td>"+output[i][y]+"</td>";
			}
			code=code+"</tr>";
			
		}
		
		code= code+"</table>";
		
		html.setHTML(code);
		this.vPanel.add(html);
	}

}

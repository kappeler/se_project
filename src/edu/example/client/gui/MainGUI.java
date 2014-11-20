package edu.example.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart;

import edu.example.client.service.ExampleServiceClientImpl;

public class MainGUI extends Composite {
	private VerticalPanel vPanel = new VerticalPanel();
	private HorizontalPanel hPanel = new HorizontalPanel();
	private VerticalPanel vPanel1;
	
	private SortedList slist;
	private MyPieChart pie;
	
	
	private HTML html;
	
	private ExampleServiceClientImpl serviceImpl;
	
	public MainGUI (ExampleServiceClientImpl serviceImpl){
		initWidget(this.vPanel);
		this.serviceImpl=serviceImpl;
		vPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		


		vPanel1 = new VerticalPanel();
		
		Button btnh1 = new Button ("SortedList");
		Button btnh2 = new Button ("Pie");
		Button btnh3 = new Button ("Map");
		
		vPanel1.add(btnh1);
		this.hPanel.add(vPanel1);
		this.hPanel.add(btnh2);
		this.hPanel.add(btnh3);
		
		btnh1.addClickHandler(new Btnh1ClickHandler());
		btnh2.addClickHandler(new Btnh2ClickHandler());
		
		Button btn3 = new Button("Show Table");
		btn3.addClickHandler(new Btn3ClickHandler());
		this.hPanel.add(btn3);
		this.vPanel.add(hPanel);
	}
	

	
	private class Btnh1ClickHandler implements ClickHandler{
		
		@Override
		public void onClick(ClickEvent event) {
			
			slist = new SortedList();
			addWidget(slist.getSortedList());
		}
	}
	
	private class Btnh2ClickHandler implements ClickHandler{
		
		@Override
		public void onClick(ClickEvent event) {
			
			pie = new MyPieChart();
			if(vPanel.getWidgetCount() > 1){
				vPanel.remove(1);
			}
			Runnable onLoadCallback = new Runnable() {
				public void run() {
					vPanel.add(pie.getPieChart());
			}
			};
			VisualizationUtils.loadVisualizationApi(onLoadCallback, PieChart.PACKAGE);
		
		
	}
	}
	
	private void addWidget(Widget widget){
		if(vPanel.getWidgetCount() > 1){
			vPanel.remove(1);
		}
		vPanel.add(widget);
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

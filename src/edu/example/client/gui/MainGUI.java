package edu.example.client.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.TextHeader;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart;

import edu.example.client.service.ExampleServiceClientImpl;

//############################
//##	PENIS	PENIS			##
//############################

public class MainGUI extends Composite {
	private VerticalPanel vPanel = new VerticalPanel();
	private HorizontalPanel hPanel = new HorizontalPanel();
	
	//public CellTable<List<String>> table;
	private String [][] data;
	

	private MyPieChart pie;
	private ListBox lb;
	private ListBox lb2;
	


	private ExampleServiceClientImpl serviceImpl;

	public MainGUI(ExampleServiceClientImpl serviceImpl) {
		initWidget(this.vPanel);
		this.serviceImpl = serviceImpl;
		vPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		hPanel.setSpacing(50);
		
		

		VerticalPanel vPanel1 = new VerticalPanel();
		VerticalPanel vPanel2 = new VerticalPanel();
		VerticalPanel vPanel3 = new VerticalPanel();
		
		HorizontalPanel hPanel1 = new HorizontalPanel();
		HorizontalPanel hPanel2 = new HorizontalPanel();
		HorizontalPanel hPanel3 = new HorizontalPanel();
		
		Label l1 = new Label("This option will show the whole Database Table");
		vPanel1.add(l1);

		hPanel1.setBorderWidth(1);
		hPanel1.add(getListBox(true));
		Button btn3 = new Button("Show Table");
		hPanel1.add(btn3);
		
		vPanel1.add(hPanel1);
		
		Label l2 = new Label("This option shows the Population from Countries in specific years  (only top 8)");
		vPanel2.add(l2);
		hPanel2.setBorderWidth(1);
		hPanel2.add(getListBox2(true));
		Button btnh2 = new Button("Show Pie");
		hPanel2.add(btnh2);
		
		vPanel2.add(hPanel2);
		
		Label l3 = new Label("This option shows the Population from a Country between 1992 and 2011");
	//	vPanel3.add(l3);
	
		
	
		this.hPanel.add(vPanel1);
		this.hPanel.add(vPanel2);
		this.hPanel.add(vPanel3);

		

	
		btnh2.addClickHandler(new Btnh2ClickHandler());
		btn3.addClickHandler(new Btn3ClickHandler());
		
		this.vPanel.add(hPanel);
	}
	
	//ListBox to define how many rows from the table will be shown
	ListBox getListBox(boolean dropdown)
	{
	    lb = new ListBox();
	    lb.addStyleName("demo-ListBox");
	    lb.addItem("5");
	    lb.addItem("10");
	    lb.addItem("50");
	    lb.addItem("100");
	    lb.addItem("150");
	    if(!dropdown)lb.setVisibleItemCount(3);
	    return lb;
	}
	ListBox getListBox2(boolean dropdown){
		lb2 = new ListBox();
	    lb2.addStyleName("demo-ListBox");
	    lb2.addItem("1990");
	    lb2.addItem("1991");
	    lb2.addItem("1992");
	    lb2.addItem("1993");
	    lb2.addItem("1994");
	    lb2.addItem("1995");
	    lb2.addItem("1996");
	    lb2.addItem("1997");
	    lb2.addItem("1998");
	    lb2.addItem("1999");
	    lb2.addItem("2000");
	    lb2.addItem("2001");
	    lb2.addItem("2002");
	    lb2.addItem("2003");
	    lb2.addItem("2004");
	    lb2.addItem("2005");
	    lb2.addItem("2006");
	    lb2.addItem("2007");
	    lb2.addItem("2008");
	    lb2.addItem("2009");
	    lb2.addItem("2010");
	    lb2.addItem("2011");
	    if(!dropdown)lb.setVisibleItemCount(3);
	    return lb2;
	}
	
	private class Btnh2ClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			
			pie = new MyPieChart();
			if (vPanel.getWidgetCount() > 1) {
				vPanel.remove(1);
			}
			Runnable onLoadCallback = new Runnable() {
				public void run() {
					
					int index = lb2.getSelectedIndex();
					
					String sql = "select area_name, value from data where year='"+lb2.getItemText(index)+"' and flagd='Official data' order by value desc limit 8";

					serviceImpl.getData(sql);
					
					vPanel.add(pie.getPieChart(data,lb2.getItemText(index)));
					
				}
			};
			VisualizationUtils.loadVisualizationApi(onLoadCallback,
					PieChart.PACKAGE);
		}
	}
	
	
	private void addWidget(Widget widget) {
		if (vPanel.getWidgetCount() > 1) {
			vPanel.remove(1);
		}
		vPanel.add(widget);
	}

	private class Btn3ClickHandler implements ClickHandler {
		

		@Override
		public void onClick(ClickEvent event) {
			int index = lb.getSelectedIndex();
			
			//table= new CellTable<List<String>>();
			
			serviceImpl.getData("select *from data limit "+lb.getItemText(index));
			displaySmartTable(data);
			
			
			
		}
	}
	public void copy(String[][] input){
		data= input; 
		
	}

	
	//**********************************************************************************
	//**                                  CELL TABLE								  **
	//**********************************************************************************
	public void displaySmartTable(String[][] stringArray) {
	
		// Create a CellTable.
		CellTable<List<String>> table= new CellTable<List<String>>();
		table.setVisibleRange(0, 200);

		// Get the rows as List
		int nrows = stringArray.length;
		int ncols = stringArray[0].length;
		ArrayList rowsL = new ArrayList(nrows);
		
		// Create a list data provider.
		final ListDataProvider<List<String>> dataProvider = new ListDataProvider<List<String>>(rowsL);
		
		
		// Create table columns (header)
		for (int icol = 0; icol < ncols; icol++) {
			IndexedColumn indexedColumn = new IndexedColumn(icol);
			indexedColumn.setSortable(true);
			
			table.addColumn(indexedColumn, new TextHeader(stringArray[0][icol]));
			
			List<List<String>> list = dataProvider.getList();
			setSortHandler(table, indexedColumn, icol, list);
		}
		
		// Add Rows to Table without header (colum names)
		for (int irow = 1; irow < nrows; irow++) {
			List<String> rowL = Arrays.asList(stringArray[irow]);
			rowsL.add(rowL);
		}

		// Add the table to the dataProvider.
		dataProvider.addDataDisplay(table);

		addWidget(table);
		
	}

	
	private void setSortHandler(CellTable<List<String>> table, IndexedColumn indexedColumn, int columnInt, List<List<String>> list){
		final int myColumnInt = columnInt;
		ListHandler<List<String>> columnSortHandler = new ListHandler<List<String>>(list);
	    columnSortHandler.setComparator(indexedColumn,
	        new Comparator<List<String>>() {
			@Override
			public int compare(List<String> first, List<String> second) {
				return first.get(myColumnInt).compareTo(second.get(myColumnInt));
			}
	        });
		
	    table.addColumnSortHandler(columnSortHandler);
	  //We know that the data is sorted alphabetically by default.
		table.getColumnSortList().push(indexedColumn);
	}
	
	class IndexedColumn extends Column<List<String>, String> {
		private final int index;

		public IndexedColumn(int index) {
			super(new TextCell());
			this.index = index;
		}

		@Override
		public String getValue(List<String> object) {
			return object.get(this.index);
		}
	}


}

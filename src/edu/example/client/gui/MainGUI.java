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
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart;

import edu.example.client.service.ExampleServiceClientImpl;

//############################
//##	PENIS				##
//############################

public class MainGUI extends Composite {
	private VerticalPanel vPanel = new VerticalPanel();
	private HorizontalPanel hPanel = new HorizontalPanel();
	private VerticalPanel vPanel1;
	//public CellTable<List<String>> table;
	private String [][] data;
	

	private SortedList slist;
	private MyPieChart pie;
	private ListBox lb;
	
	private HTML html;

	private ExampleServiceClientImpl serviceImpl;

	public MainGUI(ExampleServiceClientImpl serviceImpl) {
		initWidget(this.vPanel);
		this.serviceImpl = serviceImpl;
		vPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		hPanel.setSpacing(10);

		HorizontalPanel hPanel1 = new HorizontalPanel();
		hPanel1.setBorderWidth(1);
		hPanel1.add(getListBox(true));
		
		Button btn3 = new Button("Show Table");
		hPanel1.add(btn3);
		
		this.hPanel.add(hPanel1);
		
		Button btnh2 = new Button("Pie");
		this.hPanel.add(btnh2);

		

	
		btnh2.addClickHandler(new Btnh2ClickHandler());
		btn3.addClickHandler(new Btn3ClickHandler());
		
		this.vPanel.add(hPanel);
	}
	
	//ListBox to define how many rows from the table will be shown
	ListBox getListBox(boolean dropdown)
	{
	    lb = new ListBox();
	    lb.addStyleName("demo-ListBox");
	    lb.addItem("3");
	    lb.addItem("5");
	    lb.addItem("10");
	    lb.addItem("15");
	    if(!dropdown)lb.setVisibleItemCount(3);
	    return lb;
	}
	private class Btnh1ClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			slist = new SortedList();
			addWidget(slist.getSortedList());
		}
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
					
					
					String sql = "select area_name, value from data where year='1992' and flagd='Official data' limit 10";
					serviceImpl.getData(sql);
					
					vPanel.add(pie.getPieChart(data));
					
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

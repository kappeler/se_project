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
import com.google.gwt.user.cellview.client.TextHeader;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart;

import edu.example.client.gui.SortedList.Contact;
import edu.example.client.service.ExampleServiceClientImpl;

public class MainGUI extends Composite {
	private VerticalPanel vPanel = new VerticalPanel();
	private HorizontalPanel hPanel = new HorizontalPanel();
	private VerticalPanel vPanel1;

	private SortedList slist;
	private MyPieChart pie;
	
	private HTML html;

	private ExampleServiceClientImpl serviceImpl;

	public MainGUI(ExampleServiceClientImpl serviceImpl) {
		initWidget(this.vPanel);
		this.serviceImpl = serviceImpl;
		vPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		vPanel1 = new VerticalPanel();

		Button btnh1 = new Button("SortedList");
		Button btnh2 = new Button("Pie");
		//Button btnh3 = new Button("Map");
		Button btn3 = new Button("Show Table");
		
		
		vPanel1.add(btnh1);
		this.hPanel.add(vPanel1);
		this.hPanel.add(btnh2);
		this.hPanel.add(btn3);
		//this.hPanel.add(btnh3);

		btnh1.addClickHandler(new Btnh1ClickHandler());
		btnh2.addClickHandler(new Btnh2ClickHandler());
		btn3.addClickHandler(new Btn3ClickHandler());
		
		this.vPanel.add(hPanel);
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
					vPanel.add(pie.getPieChart());
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

			serviceImpl.getData();
		}
	}
	
	
	
	public void displayTable(String[][] output) {

		html = new HTML();
		String code = "<table border= '1'><tr>";

		for (int y = 0; y < output[0].length; y++) {
			code = code + "<th>" + output[0][y] + "</th>";
		}
		code = code + "</tr>";
		for (int i = 1; i < output.length; i++) {
			code = code + "<tr>";
			for (int y = 0; y < output[i].length; y++) {
				code = code + "<td>" + output[i][y] + "</td>";
			}
			code = code + "</tr>";

		}

		code = code + "</table>";

		html.setHTML(code);
		this.vPanel.add(html);
	}
	
	
	public void displaySmartTable(String[][] stringArray) {
		
		// Create a CellTable.
		CellTable<List<String>> table = new CellTable<List<String>>();

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

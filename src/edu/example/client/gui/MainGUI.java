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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart;

import edu.example.client.service.ExampleServiceClientImpl;

public class MainGUI extends Composite {
	private VerticalPanel vPanel = new VerticalPanel();
	private HorizontalPanel hPanel = new HorizontalPanel();
	
	private MyPieChart pie;
	private ListBoxMulti lb;
	private ListBoxMulti lb2;
	private String processMethod = "";
	
	private ListBoxMulti yearFilter;
	private ListBoxMulti domainFilter;
	private ListBoxMulti areaFilter;
	private ListBoxMulti elementFilter;
	private ListBoxMulti itemFilter;
	private ListBoxMulti flagFilter;
	
	RadioButton st;
	RadioButton pc;
	RadioButton map;
	
	private ExampleServiceClientImpl serviceImpl;

	public MainGUI(ExampleServiceClientImpl serviceImpl) {
		initWidget(this.vPanel);
		this.serviceImpl = serviceImpl;
		vPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		hPanel.setSpacing(50);
		this.vPanel.add(hPanel);
		
		//dummyCode();
		addGuiElements();
	}
	
	private void loadVisualization(){
		String sql2 = getParsedSql(); 
		if(st.isChecked()){
			//sorted Table
			String sql = "select * from data limit 10";
			requestData(sql, "sorted_table");
			
		}else if(pc.isChecked()){
			//pie chart
			String sql = "select area_name, value from data where year='1994' and flagd='Official data' order by value desc limit 8";
			requestData(sql, "pie_chart");
		}else{
			//load map
		}
	}
	
	
	private void dummyCode(){
		VerticalPanel vPanel1 = new VerticalPanel();
		VerticalPanel vPanel2 = new VerticalPanel();
		VerticalPanel vPanel3 = new VerticalPanel();
		
		HorizontalPanel hPanel1 = new HorizontalPanel();
		HorizontalPanel hPanel2 = new HorizontalPanel();
		
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
		
		this.hPanel.add(vPanel1);
		this.hPanel.add(vPanel2);
		this.hPanel.add(vPanel3);
	
		btnh2.addClickHandler(new DisplayClickHandler());
	}
	
	private void addGuiElements(){
		addChartTypeSelector();
	}
	
	private void addChartTypeSelector() {
		VerticalPanel vPanel = new VerticalPanel();
		HorizontalPanel hPanel = new HorizontalPanel();
		
	    // Make some radio buttons, all in one group.
		st = new RadioButton("chartType", "Sorted Table");
		pc = new RadioButton("chartType", "Pie Chart");
		map = new RadioButton("chartType", "Map");
	
		// Check sorted table by default.
		st.setChecked(true);
		
		// Add them to the root panel.
	    //FlowPanel panel = new FlowPanel();
	    hPanel.add(st);
	    hPanel.add(pc);
	    hPanel.add(map);
	    //RootPanel.get().add(panel);
	    
	    vPanel.add(hPanel);
	    
	    this.hPanel.add(vPanel);
	    
		String sql = "select concat(name, ' - ', code) from domain order by name, code";
		requestData(sql, "domain_filter");
	  }
	
	//ListBox to define how many rows from the table will be shown
	private void requestData(String sql, String key){
		processMethod = key;
		serviceImpl.getData(sql);
	}
	
	private void addDomainFilter(String[][] data){
		domainFilter = new ListBoxMulti(true);
		addListBox(data, domainFilter, "Domain Filter");
		
		String sql = "select concat(name, ' - ', code) from area order by name, code";
		requestData(sql, "area_filter");
	}
	
	private void addAreaFilter(String[][] data){
		areaFilter = new ListBoxMulti(true);
		addListBox(data, areaFilter, "Area Filter");
		
		String sql = "select name, code from element order by name, code";
		requestData(sql, "element_filter");
	}
	
	private void addElementFilter(String[][] data){
		elementFilter = new ListBoxMulti(true);
		addListBox(data, elementFilter, "Element Filter");
		
		String sql = "select name, code from item order by name, code";
		requestData(sql, "item_filter");
	}
	
	private void addItemFilter(String[][] data){
		itemFilter = new ListBoxMulti(true);
		addListBox(data, itemFilter, "Item Filter");
		
		String sql = "select year from year order by year desc";
		requestData(sql, "year_filter");
	}
	
	private void addYearFilter(String[][] data){
		yearFilter = new ListBoxMulti(true);
		addListBox(data, yearFilter, "Year Filter");
		
		String sql = "select concat(short, ' - ', description) from flag order by short, description";
		requestData(sql, "flag_filter");
	}
	
	private void addFlagFilter(String[][] data){
		flagFilter = new ListBoxMulti(true);
		addListBox(data, flagFilter, "Flag Filter");
		
		addButton();
	}
	
	private void addButton(){
		Button btnh2 = new Button("Display");
		btnh2.addClickHandler(new DisplayClickHandler());
		
		this.vPanel.add(btnh2);
		
		//load visualization if there are informations in the query string
		if(!Window.Location.getQueryString().contains("#")){
			loadVisualization();
		}
	}
	
	private void addListBox(String[][] data, ListBoxMulti listBox, String label){
		VerticalPanel vPanel = new VerticalPanel();
		HorizontalPanel hPanel = new HorizontalPanel();

		Label l1 = new Label(label);
		vPanel.add(l1);
		
		//TODO mark according to query string
		
		listBox.addStyleName("demo-ListBox");
		for(int i = 1; i < data.length; ++i){
			listBox.addItem(data[i][0]);
		}
		listBox.setVisibleItemCount(15);
		
		hPanel.add(listBox);
		vPanel.add(hPanel);
		
		this.hPanel.add(vPanel);
		
	}
	
	public void process(String[][] input){
		switch (processMethod) {
			case "sorted_table":
				displaySmartTable(input);
				break;
			case "pie_chart":
				displayPieChart(input);
				break;
			case "domain_filter":
				addDomainFilter(input);
				break;
			case "area_filter":
				addAreaFilter(input);
				break;		
			case "element_filter":
				addElementFilter(input);
				break;	
			case "item_filter":
				addItemFilter(input);
				break;	
			case "year_filter":
				addYearFilter(input);
				break;
			case "flag_filter":
				addFlagFilter(input);
				break;
			default:
				break;
		}
	}
	
	
	//ListBox to define how many rows from the table will be shown
	ListBox getListBox(boolean dropdown)
	{
	    lb = new ListBoxMulti(false);
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
		lb2 = new ListBoxMulti(false);
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
	
	private void displayPieChart(final String[][] data){
		pie = new MyPieChart();
		
		if (vPanel.getWidgetCount() > 2) {
			vPanel.remove(2);
		}
		
		Runnable onLoadCallback = new Runnable() {
			public void run() {
				
				vPanel.add(pie.getPieChart(data,"Population"));
			}
		};
		VisualizationUtils.loadVisualizationApi(onLoadCallback,PieChart.PACKAGE);
	}
	
	private class DisplayClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			setQueryStringAccordingToSelection();
			
			loadVisualization();
		}
	}
	
	private void setQueryStringAccordingToSelection(){
		String queryString = "";
		
		if(st.isChecked()){
			queryString  += "chart=st";
		}else if (pc.isChecked()) {
			queryString  += "chart=pc";
		}else{
			//map
			queryString  += "chart=map";
		}
		
		queryString += getQueryStringPart("domain=", domainFilter);
		queryString += getQueryStringPart("area=", areaFilter);
		queryString += getQueryStringPart("element=", elementFilter);
		queryString += getQueryStringPart("item=", itemFilter);
		queryString += getQueryStringPart("year=", yearFilter);
		queryString += getQueryStringPart("flag=", flagFilter);
		
		String oldQueryString = Window.Location.getQueryString();
		
		if(oldQueryString.indexOf("#") >= 0){
			oldQueryString = oldQueryString.substring(0, oldQueryString.indexOf("#"));
		}else{
			oldQueryString += "#";
		}
		Window.Location.replace(Window.Location.getPath() + oldQueryString + queryString);
	}
	
	private String getParsedSql(){
		//TODO parse a sql query based on the selected elements
		
		return "";
	}
	
	private String getQueryStringPart(String category,ListBoxMulti listBox){
		String queryStringPart = category;
		ArrayList<Integer> selectedIndexes = domainFilter.getSelectedItems();
		for(Integer index : selectedIndexes){
			queryStringPart += "@" + listBox.getItemText(index).replaceAll(" ", "+");
		}
		
		return queryStringPart;
	}
	
	
	private void addWidget(Widget widget) {
		if (vPanel.getWidgetCount() > 2) {
			vPanel.remove(2);
		}
		vPanel.add(widget);
	}

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

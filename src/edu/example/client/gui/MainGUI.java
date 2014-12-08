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
		String whereClause = getParsedSql();
		String sql;
		
		if(st.isChecked()){
			//sorted Table
			if(whereClause == null){
				sql = "select * from data " + whereClause+ " limit 10";
			}else{
				sql = "select * from data limit 10";	
			}
			
			requestData(sql, "sorted_table");
			
		}else if(pc.isChecked()){
			//pie chart
			if(whereClause == null){
				sql = "select area_name, value from data " + whereClause + " order by value desc limit 8";
			}else{
				sql = "select area_name, value from data order by value desc limit 8";
			}
			
			requestData(sql, "pie_chart");
		}else{
			//load map
		}
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
		
		if(!Window.Location.getHash().isEmpty()){
			String chart = getCategoryQueryString("chart").replace("chart=@", "");
			
			if(chart.equals("st")){
				st.setChecked(true);
			}else if(chart.equals("pc")){
				pc.setChecked(true);
			}else{
				//Map
				map.setChecked(true);
			}
		}
		
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
	
	private void markSelected(String categoryName, ListBoxMulti list){
		String category = getCategoryQueryString(categoryName);

		if(category != null){
			String[] parts = category.split("@");
			//Skip first part "domain="
			for(int i = 1; i< parts.length; ++i){
				list.setSelectListItem(parts[i]);
			}
		}
	}
	
	private String getCategoryQueryString(String catagory){
		String queryHash = Window.Location.getHash();
		
		if(!queryHash.contains(catagory)){
			return null;
		}
		
		String categryTail = queryHash.substring(queryHash.indexOf(catagory));
		categryTail = categryTail.substring(0, categryTail.indexOf("&"));
		
		categryTail = categryTail.replace("+", " ");
		return categryTail;
	}
	
	private void addDomainFilter(String[][] data){
		domainFilter = new ListBoxMulti(true);
		addListBox(data, domainFilter, "Domain Filter");
		
		markSelected("domain", domainFilter);
		
		String sql = "select concat(name, ' - ', code) from area order by name, code";
		requestData(sql, "area_filter");
	}
	
	private void addAreaFilter(String[][] data){
		areaFilter = new ListBoxMulti(true);
		addListBox(data, areaFilter, "Area Filter");
		
		markSelected("area", areaFilter);
		
		String sql = "select concat(name, ' - ', code) from element order by name, code";
		requestData(sql, "element_filter");
	}
	
	private void addElementFilter(String[][] data){
		elementFilter = new ListBoxMulti(true);
		addListBox(data, elementFilter, "Element Filter");
		
		markSelected("element", elementFilter);
		
		String sql = "select concat(name, ' - ', code) from item order by name, code";
		requestData(sql, "item_filter");
	}
	
	private void addItemFilter(String[][] data){
		itemFilter = new ListBoxMulti(true);
		addListBox(data, itemFilter, "Item Filter");
		
		markSelected("item", itemFilter);
		
		String sql = "select year from year order by year desc";
		requestData(sql, "year_filter");
	}
	
	private void addYearFilter(String[][] data){
		yearFilter = new ListBoxMulti(true);
		addListBox(data, yearFilter, "Year Filter");
		
		markSelected("year", yearFilter);
		
		String sql = "select concat(short, ' - ', description) from flag order by short, description";
		requestData(sql, "flag_filter");
	}
	
	private void addFlagFilter(String[][] data){
		flagFilter = new ListBoxMulti(true);
		addListBox(data, flagFilter, "Flag Filter");
		
		markSelected("flag", flagFilter);
		
		addButton();
	}
	
	private void addButton(){
		Button btnh2 = new Button("Display");
		btnh2.addClickHandler(new DisplayClickHandler());
		
		this.vPanel.add(btnh2);
		
		//load visualization if there are informations in the query string
		if(!Window.Location.getHash().isEmpty()){
			loadVisualization();
		}
	}
	
	private void addListBox(String[][] data, ListBoxMulti listBox, String label){
		VerticalPanel vPanel = new VerticalPanel();
		HorizontalPanel hPanel = new HorizontalPanel();

		Label l1 = new Label(label);
		vPanel.add(l1);
		
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
			queryString  += "chart=@st&";
		}else if (pc.isChecked()) {
			queryString  += "chart=@pc&";
		}else{
			//map
			queryString  += "chart=@map&";
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
		String whereClause = "";
		String temp;
		
		temp = getWhereClauseCategory("domain_code", domainFilter);
		if(temp != null) whereClause += " and " +  temp;
		
		temp = getWhereClauseCategory("area_code", areaFilter);
		if(temp != null) whereClause += " and " +  temp;
		
		temp = getWhereClauseCategory("element_code", elementFilter);
		if(temp != null) whereClause += " and " +  temp;
		
		temp = getWhereClauseCategory("item_code", itemFilter);
		if(temp != null) whereClause += " and " +  temp;
		
		temp = getWhereClauseCategory("year", yearFilter);
		if(temp != null) whereClause += " and " +  temp;
		
		temp = getWhereClauseCategory("flag", flagFilter);
		if(temp != null) whereClause += " and " +  temp;

		
		if(whereClause.length() > 0){
			//remove first and add where
			whereClause = " where " + whereClause.substring(4);
		}
		
		return whereClause;
	}
	
	private String getWhereClauseCategory(String category, ListBoxMulti listBox){
		String whereClauseCategory = ""; 
		
		ArrayList<Integer> selectedItems =  listBox.getSelectedItems();
		
		for(Integer i : selectedItems){
			if(category.equals("year")){
				whereClauseCategory += " or " + category + " like '" + listBox.getItemText(i) + "'";
			}else if(category.equals("flag")){
				int index = listBox.getItemText(i).indexOf("-");
				if(index == 0){
					index = 1;
				}
				String tempClause = listBox.getItemText(i).substring(0, index);
				if(tempClause.length() ==1 ){
					whereClauseCategory += " or " + category + " like ''";
				}else{
					whereClauseCategory += " or " + category + " like '" + listBox.getItemText(i).substring(0, listBox.getItemText(i).indexOf("-") - 1) + "'";
				}
				
			}else{
				whereClauseCategory += " or " + category + " like '" + listBox.getItemText(i).substring(listBox.getItemText(i).indexOf("-") + 2) + "'";
			}
		}
		
		if(whereClauseCategory.length() < 4){
			return null;
		}
		
		whereClauseCategory = "(" +  whereClauseCategory.substring(4) + ")";
		return whereClauseCategory;
	}
	
	private String getQueryStringPart(String category, ListBoxMulti listBox){
		String queryStringPart = category;
		ArrayList<Integer> selectedIndexes = listBox.getSelectedItems();
		for(Integer index : selectedIndexes){
			queryStringPart += "@" + listBox.getItemText(index).replaceAll(" ", "+");
		}
		
		queryStringPart += "&";
		
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

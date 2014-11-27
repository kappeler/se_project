package edu.example.client.gui;

import java.util.List;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.Window;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.Selection;
import com.google.gwt.visualization.client.events.SelectHandler;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart;
import com.google.gwt.visualization.client.visualizations.corechart.Options;

public class MyPieChart {

	public PieChart getPieChart(String[][] table, String pop) {
	
		PieChart pie = new PieChart(createTable(table), createOptions(pop));

				
		
		return pie;
	}


	private AbstractDataTable createTable(String[][] table) {

		DataTable data = DataTable.create();
		data.addColumn(ColumnType.STRING, "Land");
		data.addColumn(ColumnType.NUMBER, "Pop");
		
		data.addRows(table.length);
		for (int i =1; i< table.length;i++){
			for(int y= 0; y < table[i].length;y++){
				if(y==0){
					data.setValue(i,y,table[i][y]);
				} else {
					int num= Integer.parseInt(table[i][y]);
					data.setValue(i,y,num);
				}
			}
		}
		
	//	data.setValue(0, 0, table[2][0]);
	//	data.setValue(0, 1, 11);
	//	data.setValue(1, 0, "dfdgdg");
	//	data.setValue(1, 1, 11);
	//	data.setValue(2, 0, "gdgdg");
	//	data.setValue(2, 1, 11);
		return data;
	}

	private Options createOptions(String pop) {
		Options options = Options.create();
		options.setWidth(800);
		options.setHeight(480);
		//options.set3D(true);
		options.setTitle("Population in "+pop);
		return options;
	}

}

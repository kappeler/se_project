package edu.example.server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.google.appengine.api.utils.SystemProperty;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.view.client.ListDataProvider;
import com.mysql.jdbc.ResultSetMetaData;

import edu.example.client.gui.SortedList.Contact;


public class DBConnect {
	
	private Connection con;
	private Statement st;
	private ResultSet rs;
	private ResultSetMetaData metaData;
	
	public DBConnect(){
		try{
			
//			String url = null;
//			if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
//			// Connecting from App Engine.
//			// Load the class that provides the "jdbc:google:mysql://"
//			// prefix.
//			Class.forName("com.mysql.jdbc.GoogleDriver");
//			url =
//			"jdbc:google:mysql://swogdal:projectdb?user=root";
//			} else {
//			 // Connecting from an external network.
//			Class.forName("com.mysql.jdbc.Driver");
//			url = "jdbc:mysql://173.194.244.133:3306?user=root";
//			}
//
//			con = DriverManager.getConnection(url);
//			ResultSet rs = con.createStatement().executeQuery("SELECT 1 + 1");
//			Class.forName("com.mysql.jdbc.GoogleDriver");
			
			Class.forName("com.mysql.jdbc.Driver");
			//con= DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","waldenburg");
			con= DriverManager.getConnection("jdbc:mysql://173.194.244.133:3306/se_project","root","1234");
			st=con.createStatement();
		
			
		}catch(Exception e){
			System.out.println("Error: "+e);
		}
	}
	
	public ResultSet getResultSet(String sql){
		
		try{
			
			//"select * from data limit 4"
			String query = sql;
			rs=st.executeQuery(query);
		} catch (Exception e){
			System.out.println(e);
		}
			
		return rs;
	}
	
	// The list of data to display.
	  private static List<Contact> CONTACTS = Arrays.asList(new Contact("USA",
	      "330"), new Contact("Spain", "222"), new Contact(
	      "Switzerland", "94"), new Contact("England", "10"), new Contact("Greece", "100"), new Contact(
	    	      "Guatemala", "0.8"), new Contact(
	    	    	      "India", "0.9"), new Contact("China", "300") );
	
	  
   public static class Contact {
	    private final String value;
	    private final String country;
	    
	    public Contact(String country, String value) {
	      this.country = country;
	      this.value = value;
	    }
	    public String getCountry(){
	    	return country;
	    }
	    public String getValue(){
	    	return value;
	    }
	  }
	
	public CellTable getStaticCellTable(){
		// Create a CellTable.
		CellTable<Contact> table = new CellTable<Contact>();

		    // Create name column.
		    TextColumn<Contact> countryColumn = new TextColumn<Contact>() {
		      @Override
		      public String getValue(Contact contact) {
		        return contact.country;
		      }
		    };
		    
		    // Make the country column sortable.
		    countryColumn.setSortable(true);

		    // Create address column.
		    TextColumn<Contact> valueColumn = new TextColumn<Contact>() {
		      @Override
		      public String getValue(Contact contact) {
		        return contact.value;
		      }
		    };
		    // Make the name column sortable.
		    valueColumn.setSortable(true);
		    
		    // Add the columns.
		    table.addColumn(countryColumn, "Country");
		    table.addColumn(valueColumn, "Value");
		    
		 // Create a data provider.
		    ListDataProvider<Contact> dataProvider = new ListDataProvider<Contact>();

		    // Connect the table to the data provider.
		    dataProvider.addDataDisplay(table);

		    // Add the data to the data provider, which automatically pushes it to the
		    // widget.
		    List<Contact> list = dataProvider.getList();
		    for (Contact contact : CONTACTS) {
		      list.add(contact);
		    }

		    // Add a ColumnSortEvent.ListHandler to connect sorting to the
		    // java.util.List.
		    ListHandler<Contact> columnSortHandler = new ListHandler<Contact>(
		        list);
		    columnSortHandler.setComparator(countryColumn,
		        new Comparator<Contact>() {
		          public int compare(Contact o1, Contact o2) {
		            if (o1 == o2) {
		              return 0;
		            }

		            // Compare the country columns.
		            if (o1 != null) {
		              return (o2 != null) ? o1.country.compareTo(o2.country) : 1;
		            }
		            return -1;
		            
		            
		          }
		        });
		    columnSortHandler.setComparator(valueColumn,
		            new Comparator<Contact>() {
		              public int compare(Contact o1, Contact o2) {
		                if (o1 == o2) {
		                  return 0;
		                }

		                // Compare the value columns.
		                if (o1 != null) {
		                  return (o2 != null) ? Float.valueOf(o1.value).compareTo(Float.valueOf(o2.value)) : 1;
		                }
		                return -1;
		                
		                
		              }
		            });
		    table.addColumnSortHandler(columnSortHandler);

		    // We know that the data is sorted alphabetically by default.
		    table.getColumnSortList().push(countryColumn);
		    table.getColumnSortList().push(valueColumn);

		    // Add it to the root panel.
		    //RootPanel.get().add(table);
		  return table;
	}
	
	/*  public CellTable getCellTableSortedList(ResultSet rs){
		  CellTable<ResultSet> table = new CellTable<ResultSet>();

		    // Create name column.
		    TextColumn<Contact> countryColumn = new TextColumn<Contact>() {
		      @Override
		      public String getValue(Contact contact) {
		        return contact.country;
		      }
		    };
		    
		    // Make the country column sortable.
		    countryColumn.setSortable(true);

		    // Create address column.
		    TextColumn<Contact> valueColumn = new TextColumn<Contact>() {
		      @Override
		      public String getValue(Contact contact) {
		        return contact.value;
		      }
		    };
		    // Make the name column sortable.
		    valueColumn.setSortable(true);
		    
		    // Add the columns.
		    table.addColumn(countryColumn, "Country");
		    table.addColumn(valueColumn, "Value");
		    
		 // Create a data provider.
		    ListDataProvider<Contact> dataProvider = new ListDataProvider<Contact>();

		    // Connect the table to the data provider.
		    dataProvider.addDataDisplay(table);

		    // Add the data to the data provider, which automatically pushes it to the
		    // widget.
		    List<Contact> list = dataProvider.getList();
		    for (Contact contact : CONTACTS) {
		      list.add(contact);
		    }

		    // Add a ColumnSortEvent.ListHandler to connect sorting to the
		    // java.util.List.
		    ListHandler<Contact> columnSortHandler = new ListHandler<Contact>(
		        list);
		    columnSortHandler.setComparator(countryColumn,
		        new Comparator<Contact>() {
		          public int compare(Contact o1, Contact o2) {
		            if (o1 == o2) {
		              return 0;
		            }

		            // Compare the country columns.
		            if (o1 != null) {
		              return (o2 != null) ? o1.country.compareTo(o2.country) : 1;
		            }
		            return -1;
		            
		            
		          }
		        });
		    columnSortHandler.setComparator(valueColumn,
		            new Comparator<Contact>() {
		              public int compare(Contact o1, Contact o2) {
		                if (o1 == o2) {
		                  return 0;
		                }

		                // Compare the value columns.
		                if (o1 != null) {
		                  return (o2 != null) ? Float.valueOf(o1.value).compareTo(Float.valueOf(o2.value)) : 1;
		                }
		                return -1;
		                
		                
		              }
		            });
		    table.addColumnSortHandler(columnSortHandler);

		    // We know that the data is sorted alphabetically by default.
		    table.getColumnSortList().push(countryColumn);
		    table.getColumnSortList().push(valueColumn);

		    // Add it to the root panel.
		    //RootPanel.get().add(table);
		 
	  }*/
	
	public String[][] getTable(ResultSet rs){
		String[][] output = null;
		
		
		try{
			metaData= (ResultSetMetaData) rs.getMetaData();
			
			int rows = getResultSetRows(rs);
			int columns = getResultSetColumns(rs);
			
			output = new String[rows+1][columns];
			String[][] virtual_output = new String[2][2];
			
			virtual_output[0][0]= "Name";
			virtual_output[0][1]= "Name";
			virtual_output[1][0]= "Name";
			virtual_output[1][1]= "Name";
			
		
			
			//Column Labels in first row
			for (int column_index = 0; column_index < columns; column_index++){
				output[0][column_index]= metaData.getColumnName(column_index+1);
			}
			
			
			int row_index=1;
			
			while(rs.next() && row_index < rows+1){
				
				for (int column_index = 0; column_index < columns; column_index++){
					output[row_index][column_index]= rs.getString(metaData.getColumnName(column_index+1));
				}
				
				row_index++;				
			}
			
		} catch (Exception e){
			System.out.println(e);
		}
		return output;
	}
	
	public int getResultSetRows(ResultSet rs){
		int size= -1;
		
		try{
			rs.last();
			size=rs.getRow();
			rs.beforeFirst();
		} catch (SQLException e){
			return size;
		}
		return size;
	}
	
	public int getResultSetColumns(ResultSet rs){
		int columnCount = 0;
		try{
		columnCount=metaData.getColumnCount();
		} catch (SQLException e){
			return columnCount;
		}
		return columnCount;
	}

}

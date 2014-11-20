package edu.example.client.gui;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.view.client.ListDataProvider;


public class SortedList {
	
	// CLass Contact
	  private static class Contact {
		    private final String value;
		    private final String country;
		    
		    public Contact(String country, String value) {
		      this.country = country;
		      this.value = value;
		    }
		  }
	  
	  //The Table
	  private CellTable<Contact> table;
	  
	// The list of data to display.
	  private static List<Contact> CONTACTS = Arrays.asList(new Contact("USA",
	      "330"), new Contact("Spain", "222"), new Contact(
	      "Switzerland", "94"), new Contact("England", "10"), new Contact("Greece", "100"), new Contact(
	    	      "Guatemala", "0.8"), new Contact(
	    	    	      "India", "0.9"), new Contact("China", "300") );
	  
	  public SortedList(){
		  
		// Create a CellTable.
		  table = new CellTable<Contact>();

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
		 
	  }
	  
	  public CellTable<Contact> getSortedList(){
		  return table;
	  }
}

package edu.example.client.gui;

import com.google.gwt.junit.client.GWTTestCase;

import edu.example.client.gui.SortedList;
import edu.example.client.gui.SortedList.Contact;

public class SortedListTest extends GWTTestCase {

	  /**
	   * Must refer to a valid module that sources this class.
	   */
	  public String getModuleName() {
	    return "edu.example.GWTProjectThree";
	  }

	  /**
	   * Add as many tests as you like.
	   */
	  public void testSimple() {
	    assertTrue(true);
	  }
	  /**  * Verify that the instance fields in the StockPrice class are set correctly.  */ 
	  public void testContact() {  
		  String country = "Italy";  
		  String value = "70.0";  
		  Contact c = new Contact(country, value);
		   
		  assertEquals(country, c.getCountry());
		  assertEquals(value, c.getValue());}
	  

	}

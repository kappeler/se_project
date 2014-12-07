package edu.example.client.gui;

import java.util.ArrayList;
import com.google.gwt.user.client.ui.ListBox;

public class ListBoxMulti extends ListBox{
	
	public ListBoxMulti(boolean b) {
		super(b);
	}

	public ArrayList<Integer> getSelectedItems() {
		ArrayList<Integer> selectedItems = new ArrayList<Integer>();
	    for (int i = 0; i < getItemCount(); i++) {
	        if (isItemSelected(i)) {
	            selectedItems.add(i);
	        }
	    }
	    return selectedItems;
	}
}

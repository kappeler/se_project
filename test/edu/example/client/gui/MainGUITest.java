package edu.example.client.gui;

import org.junit.Test;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MainGUITest {

	@Test
	public void testdisplayTable() {
		String[][] output = new String[2][2];
		VerticalPanel vPanel = new VerticalPanel();
		HTML html = new HTML();
		String code="<table border= '1'><tr>";
		
		for(int y= 0; y <output[0].length ; y++){
			code= code+ "<th>"+output[0][y]+"</th>";
		}
		code=code+"</tr>";
		for(int i=1; i< output.length; i++){
			code= code+"<tr>";
			for(int y= 0; y<output[i].length; y++){
				code= code+ "<td>"+output[i][y]+"</td>";
			}
			code=code+"</tr>";
			
		}
		
		code= code+"</table>";
		
		html.setHTML(code);
		vPanel.add(html);
	}
	}



package edu.example.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.google.appengine.api.utils.SystemProperty;
import com.mysql.jdbc.ResultSetMetaData;

public class DBConnect {

	private Connection con;
	private Statement st;
	private ResultSet rs;
	private ResultSetMetaData metaData;

	public DBConnect() {
		try {
			String url= null;
			if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
					// Connecting from App Engine.
					// Load the class that provides the "jdbc:google:mysql://"
					// prefix.
					Class.forName("com.mysql.jdbc.GoogleDriver");
					
					url ="jdbc:google:mysql://kama-13-765-482:seproject/se_project?user=root";
					
					con = DriverManager.getConnection(url);
					//rs = con.createStatement().executeQuery("SELECT 1 + 1");
					st= con.createStatement();
					
			}else{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://173.194.251.250:3306/se_project", "root","1234");
			st = con.createStatement();
			}
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
	}

	public ResultSet getResultSet(String sql) {
		try {
			String query = sql;
			rs = st.executeQuery(query);
		} catch (Exception e) {
			System.out.println(e);
		}

		return rs;
	}

	// String[][] getTable(ResultSet rs)

	public String[][] getTable(ResultSet rs) {
		String[][] output = null;

		try {
			metaData = (ResultSetMetaData) rs.getMetaData();

			int rows = getResultSetRows(rs);
			int columns = getResultSetColumns(rs);

			output = new String[rows + 1][columns];
			String[][] virtual_output = new String[2][2];

			virtual_output[0][0] = "Name";
			virtual_output[0][1] = "Name";
			virtual_output[1][0] = "Name";
			virtual_output[1][1] = "Name";

			// Column Labels in first row
			for (int column_index = 0; column_index < columns; column_index++) {
				output[0][column_index] = metaData
						.getColumnName(column_index + 1);
			}

			int row_index = 1;

			while (rs.next() && row_index < rows + 1) {

				for (int column_index = 0; column_index < columns; column_index++) {
					output[row_index][column_index] = rs.getString(metaData
							.getColumnName(column_index + 1));
				}

				row_index++;
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return output;
	}

	public int getResultSetRows(ResultSet rs) {
		int size = -1;

		try {
			rs.last();
			size = rs.getRow();
			rs.beforeFirst();
		} catch (SQLException e) {
			return size;
		}
		return size;
	}

	public int getResultSetColumns(ResultSet rs) {
		int columnCount = 0;
		try {
			columnCount = metaData.getColumnCount();
		} catch (SQLException e) {
			return columnCount;
		}
		return columnCount;
	}

}

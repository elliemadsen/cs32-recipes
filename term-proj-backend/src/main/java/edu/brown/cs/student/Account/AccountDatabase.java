package edu.brown.cs.student.Account;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AccountDatabase{
  private static Connection conn = null;



  public AccountDatabase(String filename) {
    try {
      Class.forName("org.sqlite.JDBC");
      String urlToDB = "jdbc:sqlite:" + filename;
      conn = DriverManager.getConnection(urlToDB);
      Statement s = conn.createStatement();
      s.executeUpdate("PRAGMA foreign_keys=ON;");
    } catch (SQLException e) {
      System.out.println("ERROR: Connection to SQLite could not be established.");
    } catch (ClassNotFoundException e) {
      System.out.println("ERROR: Class not found exception.");
    }
  }

  //login
  public List<String> login(String username, String password) throws SQLException{
	  List<String> usernames = new ArrayList<String>();
	  PreparedStatement prep = conn.prepareStatement(
		        "SELECT user FROM Account WHERE user = ? AND password = ?");
		    prep.setString(1, username);
		    prep.setString(2, password);
		    ResultSet rs = prep.executeQuery();
		    while (rs.next()) {
		    	usernames.add(rs.getString(1));
		    }
		    rs.close();
		    prep.close();
		    return usernames;
  }
  
  //Sign up
  public List<String> signUp(String username, String password) throws SQLException {
	  List<String> usernames = new ArrayList<String>();
	  PreparedStatement prep = conn.prepareStatement(
		        "SELECT user FROM Account WHERE user = ?");
		    prep.setString(1, username);
		    ResultSet rs = prep.executeQuery();
		    while (rs.next()) {
		    	usernames.add(rs.getString(1));
		    }
		    rs.close();
		    prep.close();
		    if (usernames.size() == 0) {
		    	PreparedStatement prep2 = conn.prepareStatement(
		    	        "INSERT INTO Account VALUES (?, ?)");
		    	    prep2.setString(1, username);
		    	    prep2.setString(2, password);
		    	    prep2.executeUpdate();
		    	    prep2.close();
		    	    return login(username, password);
		    }
		    return null;
	  }
  
  //add
  public int add(String username, String item, String category ) throws SQLException {
	  List<String> duplicates = new ArrayList<String>();

	PreparedStatement prepDuplicate = null;
		    if (category == "Pantry") {
		    	prepDuplicate = conn.prepareStatement(
		    			"Select user from Pantry WHERE user = ? AND item = ? ");		
		    } else if (category == "Restriction") {
		    	prepDuplicate = conn.prepareStatement(
		    			"Select user from Restriction WHERE user = ? AND item = ? ");	
		    }
		    prepDuplicate.setString(1, username);
		    prepDuplicate.setString(2, item);
		    ResultSet rsDuplicate = prepDuplicate.executeQuery();
		    while (rsDuplicate.next()) {
		    	duplicates.add(rsDuplicate.getString(1));
		    }
		    rsDuplicate.close();
		    prepDuplicate.close();
		    if (duplicates.size() == 0) {
		    

		    	
	  PreparedStatement prep = null;

	  if (category == "Pantry") {
		  
	  prep = conn.prepareStatement(
		        "INSERT INTO Pantry VALUES(?,?)");
	  } else if (category == "Restriction") {
		 prep = conn.prepareStatement(
				 "INSERT INTO Restriction VALUES(?,?)");
	  }
	  prep.setString(1, username);
	  prep.setString(2, item);
	  prep.executeUpdate();
	  prep.close();	
	  return 1;
		    }
		    return 0;
  }
  
//remove
  public int remove(String username, String item, String category ) throws SQLException {
	  PreparedStatement prep = null;

	  if (category == "Pantry") {
	  prep = conn.prepareStatement(
		        "DELETE FROM Pantry WHERE user = ? AND item = ?");
	  } else if (category == "Restriction") {
		 prep = conn.prepareStatement(
				 "DELETE FROM Restriction WHERE user = ? AND item = ?");
	  }
	  prep.setString(1, username);
	  prep.setString(2, item);
	  prep.executeUpdate();
	  prep.close();	
	  return 1;
  }
  
  //get pantry
  public List<String> getList(String username, String category) throws SQLException{
	  List<String> items = new ArrayList<String>();
	  PreparedStatement prep = null;

	  if (category == "Pantry") {
	  prep = conn.prepareStatement(
		        "SELECT item FROM Pantry WHERE user = ?");
	  } else if (category == "Restriction") {
		 prep = conn.prepareStatement(
			        "SELECT item FROM Restriction WHERE user = ?");
	  }
		    prep.setString(1, username);

		    ResultSet rs = prep.executeQuery();
		    while (rs.next()) {
		    	items.add(rs.getString(1));
		    }
		    rs.close();
		    prep.close();
		    return items;
  }


}

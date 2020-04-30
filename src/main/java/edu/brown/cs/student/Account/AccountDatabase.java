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
  private List<String> quantity = null;



  public AccountDatabase(String filename, PrintWriter pw) {
    try {
      Class.forName("org.sqlite.JDBC");
      String urlToDB = "jdbc:sqlite:" + filename;
      conn = DriverManager.getConnection(urlToDB);
      Statement s = conn.createStatement();
      s.executeUpdate("PRAGMA foreign_keys=ON;");
    } catch (SQLException e) {
      pw.println("ERROR: Connection to SQLite could not be established.");
    } catch (ClassNotFoundException e) {
      pw.println("ERROR: Class not found exception.");
    }
  }

  public List<String> getId(String userName, String password) throws SQLException {
    List<String> accountIds = new ArrayList<String>();
    PreparedStatement prep = conn.prepareStatement(
        "SELECT userId FROM accounts WHERE userName = ? AND password = ?");
    prep.setString(1, userName);
    prep.setString(2, password);
    ResultSet rs = prep.executeQuery();
    while (rs.next()) {
      accountIds.add(rs.getString(1));
    }
    rs.close();
    prep.close();
    return accountIds;       
  }

  public List<String> getRecipeId(String recipe) throws SQLException {
    List<String> recipeId = new ArrayList<String>();
    PreparedStatement prep = conn.prepareStatement(
        "SELECT recipeId FROM recipe WHERE name = ?");
    prep.setString(1, recipe);
    ResultSet rs = prep.executeQuery();
    while (rs.next()) {
      recipeId.add(rs.getString(1));
    }
    rs.close();
    prep.close();
    return recipeId;       
  }

  public List<String> getUsername(String userName, String password) throws SQLException {
    List<String> accountIds = new ArrayList<String>();
    PreparedStatement prep = conn.prepareStatement(
        "SELECT userId FROM accounts WHERE userName = ?");
    prep.setString(1, userName);
    ResultSet rs = prep.executeQuery();
    while (rs.next()) {
      accountIds.add(rs.getString(1));
    }
    rs.close();
    prep.close();
    return accountIds;       
  }


  public List<String> newAccount(String userName, String password) throws SQLException {
    List<String> accountIds = new ArrayList<String>();
    PreparedStatement prep = conn.prepareStatement(
        "INSERT INTO accounts VALUES (NULL, ?, ?)");
    prep.setString(1, userName);
    prep.setString(2, password);
    prep.executeUpdate();
    prep.close();
    return getId(userName, password);
  }

  public List<String> itemId (String item) throws SQLException{
    List<String> itemIds = new ArrayList<String>();
    PreparedStatement prep = conn.prepareStatement(
        "SELECT ingredientsId FROM ingredients where name = ?");
    prep.setString(1, item);
    ResultSet rs = prep.executeQuery();
    while(rs.next()) {
      itemIds.add(rs.getString(1));
    }
    rs.close();
    prep.close();
    return itemIds;
  }

  public List<String> alreadyIn(String item, String userId) throws SQLException {
    List<String> quantity = new ArrayList<String>();
    PreparedStatement prep = conn.prepareStatement(
        "SELECT quantity FROM accounts_ingredients WHERE accountId = ? and ingredientId = ?;");
    prep.setString(1, userId);
    prep.setString(2, item);

    ResultSet rs = prep.executeQuery();
    while(rs.next()) {
      quantity.add(rs.getString(1));
    }
    rs.close();
    prep.close();
    return quantity;
  }

  public List<String> addPantry(String item, String quantity, String userId) throws SQLException{
    PreparedStatement prep = conn.prepareStatement(
        "INSERT INTO accounts_ingredients VALUES (?, ?, ?)");
    prep.setString(1, userId);
    prep.setString(2, item);
    prep.setString(3, quantity);
    prep.executeUpdate();
    prep.close();
    return alreadyIn(item, userId);
  }

  public List<String> updateQuantity(String item, String quantity, String userId) throws SQLException{
    PreparedStatement prep;
    prep = conn.prepareStatement(
        "UPDATE accounts_ingredients SET quantity = ? WHERE accountId = ? AND ingredientId = ?");
    prep.setString(1, quantity);
    prep.setString(2, userId);
    prep.setString(3, item);
    prep.executeUpdate();
    prep.close();

    return alreadyIn(item, userId);

  }

  public Boolean alreadyLiked(String recipe, String userId) throws SQLException{
    List<String> recipeId = new ArrayList<String>();
    PreparedStatement prep = conn.prepareStatement(
        "SELECT recipeId FROM account_recipe WHERE accountId = ? and recipeId = ?;");
    prep.setString(1, userId);
    prep.setString(2, recipe);
    ResultSet rs = prep.executeQuery();
    while(rs.next()) {
      recipeId.add(rs.getString(1));
    }
    rs.close();
    prep.close();
    if (recipeId.size() == 1) {
      return true;
    } else {
      return false;
    }
  }

  public Boolean addLiked(String recipe, String userId) throws SQLException {
    PreparedStatement prep = conn.prepareStatement(
        "INSERT INTO account_recipe VALUES (?, ?)");
    prep.setString(1, userId);
    prep.setString(2, recipe);
    prep.executeUpdate();
    prep.close();
    return alreadyLiked(recipe, userId);
  }
  
  public Boolean alreadyRestricted(String itemId, String userId) throws SQLException{
    List<String> itemIdList = new ArrayList<String>();
    PreparedStatement prep = conn.prepareStatement(
        "SELECT itemId FROM restrictions WHERE userId = ? and itemId = ?;");
    prep.setString(1, userId);
    prep.setString(2, itemId);
    ResultSet rs = prep.executeQuery();
    while(rs.next()) {
      itemIdList.add(rs.getString(1));
    }
    rs.close();
    prep.close();
    if (itemIdList.size() == 1) {
      return true;
    } else {
      return false;
    }
  }
  
  public Boolean addRestriction(String itemId, String userId) throws SQLException {
    PreparedStatement prep = conn.prepareStatement(
        "INSERT INTO restrictions VALUES (?, ?)");
    prep.setString(1, userId);
    prep.setString(2, itemId);
    prep.executeUpdate();
    prep.close();
    return alreadyRestricted(itemId, userId);
  }
  
  public List<String> userId (String id) throws SQLException{
    List<String> userIds = new ArrayList<String>();
    PreparedStatement prep = conn.prepareStatement(
        "SELECT userName FROM accounts where userId = ?");
    prep.setString(1, id);
    ResultSet rs = prep.executeQuery();
    while(rs.next()) {
      userIds.add(rs.getString(1));
    }
    rs.close();
    prep.close();
    return userIds;
  }
  
  public List<String> ingredients (String id) throws SQLException{
    List<String> ingredients = new ArrayList<String>();
    quantity = new ArrayList<String>();
    PreparedStatement prep = conn.prepareStatement(
        "select ingredients.name, accounts_ingredients.quantity from ingredients, accounts_ingredients where "
        + "accounts_ingredients.ingredientId = ingredients.ingredientsId and "
        + "accounts_ingredients.accountId = ?");
    prep.setString(1, id);
    ResultSet rs = prep.executeQuery();
    while(rs.next()) {
      ingredients.add(rs.getString(1));
      quantity.add(rs.getString(2));
    }
    rs.close();
    prep.close();
    return ingredients;
  }
  
  public List<String> getQuantity(){
    return quantity;
  }
  
  
  public List<String> recipes (String id) throws SQLException{
    List<String> recipes = new ArrayList<String>();
    PreparedStatement prep = conn.prepareStatement(
        "select recipe.name from recipe, account_recipe where "
        + "account_recipe.recipeId = recipe.recipeId and "
        + "account_recipe.accountId = ?");
    prep.setString(1, id);
    ResultSet rs = prep.executeQuery();
    while(rs.next()) {
      recipes.add(rs.getString(1));
    }
    rs.close();
    prep.close();
    return recipes;
  }
  
  public List<String> restriction (String id) throws SQLException{
    List<String> restrictions = new ArrayList<String>();
    PreparedStatement prep = conn.prepareStatement(
        "select ingredients.name from ingredients, restrictions where "
            + "restrictions.itemId = ingredients.ingredientsId and "
            + "restrictions.userId = ?");
    prep.setString(1, id);
    ResultSet rs = prep.executeQuery();
    while(rs.next()) {
      restrictions.add(rs.getString(1));
    }
    rs.close();
    prep.close();
    return restrictions;
  }
  
  

}

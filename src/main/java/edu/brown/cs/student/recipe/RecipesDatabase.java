package edu.brown.cs.student.recipe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipesDatabase {

  private Connection cxn;

  public RecipesDatabase() {
    // establish connection
  }

  /**
   * Queries the database, returning all recipes that exclude all items in the
   * list of exclusions andinclude all items in the list of inclusions. Makes a
   * new Recipe for each that satisfies conditions, and returns them in a list
   *
   * @param inclusions
   * @param exclusions
   * @param pantry
   * @return
   */
  public List<Recipe> getRecipes(List<String> inclusions, List<String> exclusions) {

    try {
      List<Recipe> recipes = new ArrayList<>();

      // select * from recipe where recipe.ingredient = inclusion and
      // recipe.ingredient != exclusion
      PreparedStatement prep = cxn.prepareStatement(" TODO ");
      ResultSet rs = prep.executeQuery();
      if (!rs.next()) {
        return null;
      } else {
        do {
          String name = rs.getString("name");
          String image = rs.getString("image");
          String text = rs.getString("text");
          List<String> ingredients = Arrays.asList(rs.getString("ingredients").split(","));
          Recipe recipe = new Recipe(name, image, ingredients, text);
          recipes.add(recipe);

        } while (rs.next());
      }
      prep.close();
      rs.close();

      return recipes;
    } catch (SQLException e) {
      System.out.println("ERROR: SQL error occurred.");
      return new ArrayList<Recipe>();
    }

  }

}
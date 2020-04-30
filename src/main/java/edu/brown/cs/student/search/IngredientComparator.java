package edu.brown.cs.student.search;

import java.util.Comparator;
import java.util.List;

import edu.brown.cs.student.recipe.Recipe;

public class IngredientComparator implements Comparator<Recipe> {

  private List<String> pantry;

  public IngredientComparator(List<String> pantry) {
    this.pantry = pantry;
  }

  @Override
  public int compare(Recipe r1, Recipe r2) {
    return Integer.compare(getNumItemsInPantry(r1), getNumItemsInPantry(r2));
  }

  /**
   *
   * @param recipe
   * @return the number of ingredients used in the recipe that the user has in
   *         their pantry
   */
  private int getNumItemsInPantry(Recipe recipe) {
    int numItems = 0;
    for (String ingredientID : recipe.getIngredients()) {
      if (pantry.contains(ingredientID)) {
        numItems++;
      }
    }
    return numItems;
  }

}

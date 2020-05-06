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
	  if (getNumItemsInPantry(r1) > getNumItemsInPantry(r2)) {
		  return 1;
	  } else if (getNumItemsInPantry(r2) > getNumItemsInPantry(r1)) {
		  return -1;
	  } else { // if both recipes have equal # ingredients, sort by rating:
		  if (r1.getRating() > r2.getRating()) {
			  return 1;
		  } else {
			  return -1;
		  }
	  }
  }

  /**
   *
   * @param recipe
   * @return the number of ingredients used in the recipe that the user has in
   *         their pantry
   */
  private int getNumItemsInPantry(Recipe recipe) {
    int numItems = 0;
    for (String ingredient : recipe.getParsedIngredients()) {
      if (pantry.contains(ingredient)) {
        numItems++;
      }
    }
    return numItems;
  }

}

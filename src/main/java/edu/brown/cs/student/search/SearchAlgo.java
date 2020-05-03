package edu.brown.cs.student.search;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.brown.cs.student.recipe.Recipe;
import edu.brown.cs.student.recipe.RecipesDatabase;

public class SearchAlgo {

  private RecipesDatabase recipesDatabase;

  public SearchAlgo(RecipesDatabase db) {
    this.recipesDatabase = db;
  }

  /**
   * Calls RecipeDatabase.findRecipes(inclusions, exclusions) to get list of
   * recipes. Filters by highest number of ingredients in pantry, followed by
   * highest rating, to return top 50 matches.
   *
   * @param inclusions, which must be included in results
   * @param exclusions, which cannot be included in results
   * @param pantry,     which determines the order of the results
   * @return
   */
  public List<Recipe> search(List<String> inclusions, List<String> exclusions,
      List<String> pantry) {
    List<Recipe> results = recipesDatabase.getRecipes(inclusions, exclusions);
    Comparator<Recipe> comparator = new IngredientComparator(pantry);
    Collections.sort(results, comparator); // sort by highest # ingredients in pantry, followed by rating
    if (results.size() > 50) {
        return results.subList(0, 50); // return top 50 recipes
    }
    return results;
  }

}

package edu.brown.cs.student.recipe;

import java.util.List;

public class Recipe {

  private String id;
  private String name;
  private String url;
  private String image;
  private Double rating;
  private String yield;
  private List<String> ingredients;
  private List<String> parsedIngredients;
  private List<String> instructions;

  public Recipe(String id, String name, String url, String image, Double rating, String yield,
		  List<String> ingredients, List<String> parsedIngredients,List<String> instructions) {
    this.id = id;
	this.name = name;
	this.url = url;
    this.image = image;
    this.rating = rating;
    this.yield = yield;
    this.ingredients = ingredients;
    this.parsedIngredients = parsedIngredients;
    this.instructions = instructions;
  }

  /**
  * @return the id of the recipe
  */
 public String getID() {
   return id;
 }
 
  /**
   * @return the name of the recipe
   */
  public String getName() {
    return name;
  }
  
  /**
   * @return the url to the external site of the recipe
   */
  public String getUrl() {
    return url;
  }

  /**
   * @return the url to the image associated with the recipe
   */
  public String getImage() {
    return image;
  }

  /**
   * @return the rating of the recipe
   */
  public Double getRating() {
    return rating;
  }
  
  /**
   * @return the yield of the recipe
   */
  public String getYield() {
    return yield;
  }
  
  /**
   *
   * @return a list of full ingredients (including measurements) for gui
   */
  public List<String> getIngredients() {
    return ingredients;
  }
  
  /**
  *
  * @return a list of parsed ingredient names
  */
 public List<String> getParsedIngredients() {
   return parsedIngredients;
 }

  /**
   *
   * @return the instructions of the recipe
   */
  public List<String> getInstructions() {
    return instructions;
  }

}
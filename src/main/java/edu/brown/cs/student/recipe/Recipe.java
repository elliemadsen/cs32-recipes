package edu.brown.cs.student.recipe;

import java.util.List;

public class Recipe {

  private String name;
  private String image;
  private List<String> ingredients;
  private String text;

  public Recipe(String name, String image, List<String> ingredients, String text) {
    this.name = name;
    this.image = image;
    this.ingredients = ingredients;
    this.text = text;
  }

  /**
   *
   * @return the title of the recipe
   */
  public String getName() {
    return name;
  }

  /**
   *
   * @return the html image associated with the recipe
   */
  public String getImage() {
    return image;
  }

  /**
   *
   * @return a list of unique ingredient ids
   */
  public List<String> getIngredients() {
    return ingredients;
  }

  /**
   *
   * @return the body of the recipe
   */
  public String getText() {
    return text;
  }

}
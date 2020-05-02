package edu.brown.cs.student.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bson.Document;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

/**
 * TODO: query sub-documemts (id, rating, image url) and lists (ingredients, instructions)
 *		 write query script to include specific items
 *		 write tests
 *
 * @author ellie
 *
 */
public class RecipesDatabase {

  private MongoCollection<Document> collection;

  public RecipesDatabase() {
	  ConnectionString connString = new ConnectionString(
			  "mongodb+srv://cs32-project:cs32-project@cluster0-xndfh.gcp.mongodb.net/test?retryWrites=true&w=majority");
			MongoClientSettings settings = MongoClientSettings.builder()
			    .applyConnectionString(connString)
			    .retryWrites(true)
			    .build();
	  MongoClient mongoClient = MongoClients.create(settings);
	  MongoDatabase database = mongoClient.getDatabase("recipes");
	  collection = database.getCollection("foodnetwork");	  
	  
	  testFirstRecipe();
	  

  }
  
  private void testFirstRecipe() {
	//   test to print out the first document in the collection
	  Document myDoc = collection.find().first();
	  System.out.println("_______________________________________________________________________");
	  System.out.println("name: " + myDoc.getString("name"));
	  System.out.println("date: " + myDoc.getString("date"));
	  System.out.println("id: " + myDoc.getString("_id.ObjectId"));

	  System.out.println("_______________________________________________________________________");
  }
  
  private void testAllRecipes() {
	  List<Recipe> recipes = getRecipes(null, null);
	  for (int i = 0; i < 20; i++) {
		  System.out.println("_______________________________________________________________________");
		  Recipe r = recipes.get(i);
		  System.out.println("ID = "+r.getID());
		  System.out.println("NAME = "+r.getName());
		  System.out.println("IMAGE = "+r.getImage());
		  System.out.println("URL = "+r.getUrl());
		  System.out.println("YIELD = "+r.getYield());
		  System.out.println("RATING = "+r.getRating());
		  for (String s : r.getIngredients()) {
			  System.out.println(s);
		  }
		  for (String s : r.getInstructions()) {
			  System.out.println(s);
		  }
	  }
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
      // select * from recipe where recipe.ingredient = inclusion and
      // recipe.ingredient != exclusion
 
//	  If, instead, you wish to find an array that contains both the elements "red" and "blank", without
//	  regard to order or other elements in the array, use the $all operator:
//		  db.inventory.find( { ingredients: { $all: ["red", "blank"] } } )
	  
	  MongoCursor<Document> cursor = collection.find().iterator();
      List<Recipe> recipes = new ArrayList<>();

	  try {
	      while (cursor.hasNext()) {
	          //System.out.println(cursor.next().toJson());
	          Document doc = cursor.next();
	          
	          String id = doc.getString("_id.ObjectId");
	          String name = doc.getString("name");
	          String url = doc.getString("url");
	          String image = doc.getString("image.url");
	          String rating = doc.getString("rating.average");
	          String yield = doc.getString("yield");
	          List<String> ingredients = new ArrayList<>(); // toList(doc.getList("ingredients", new ArrayList<String>().getClass()));
	          List<String> instructions = new ArrayList<>(); // toList(doc.getList("instructions", new ArrayList<String>().getClass()));

	          Recipe recipe = new Recipe(id, name, url, image, rating, yield, ingredients, instructions);
	          recipes.add(recipe);
	      }
	  } finally {
	      cursor.close();
	  }

      return recipes;

  }
  
  private List<String> toList(List<? extends List> list) {
      List<String> ingredients = new ArrayList<>();
      Object[] arr = list.toArray();
      for (Object a : arr) {
    	  ingredients.add(a.toString());
      }
      return ingredients;
  }

}
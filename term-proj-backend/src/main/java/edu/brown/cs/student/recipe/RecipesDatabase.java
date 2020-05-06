package edu.brown.cs.student.recipe;

import static com.mongodb.client.model.Filters.all;
import static com.mongodb.client.model.Filters.nin;
import static com.mongodb.client.model.Filters.and;


import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

/**
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
	  collection = database.getCollection("bbc");
	  
	 // printFirst();
	  	  	  
  }
  
  private void printFirst() {
	  Document myDoc = collection.find().first();
	  System.out.println("");	  System.out.println("");
	  System.out.println(myDoc.toJson());
	  System.out.println("");	  System.out.println("");
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
      // SQL version of query:
	  // select * from recipe where recipe.ingredient = inclusion and recipe.ingredient != exclusion
	  MongoCursor<Document> iterator = collection.find(and(all("parsedIngredients", inclusions), nin("parsedIngredients", exclusions))).iterator();
      List<Recipe> res = new ArrayList<>();

	  try {
	      while (iterator.hasNext()) {
	          Document doc = iterator.next();
	          try {
		          String id = doc.get("_id").toString();
		          String name = doc.getString("name");
		          String url = doc.getString("url");
		          String image = doc.getEmbedded(List.of("image", "url"), String.class);
		          Double rating = Double.valueOf(doc.getEmbedded(List.of("rating"), Document.class).get("average").toString());
		          String yield = doc.getString("yield");
		          List<String> ingredients = doc.getList("ingredients", String.class);
		          List<String> parsedIngredients = doc.getList("parsedIngredients", String.class);
		          List<String> instructions = doc.getList("instructions", String.class);
		          Recipe recipe = new Recipe(id, name, url, image, rating, yield, ingredients, parsedIngredients, instructions);
		          res.add(recipe);
	          } catch (NullPointerException e) { // bc some recipes have null ratings (ignore these)
	          }
	      }
	  } finally {
		  iterator.close();
	  }
      return res;
  }

}
package edu.brown.cs.student.recipe;

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
	  MongoDatabase database = mongoClient.getDatabase("foodnetwork");
	  collection = database.getCollection("??");

	  // test to print out the first document in the collection
	  Document myDoc = collection.find().first();
	  System.out.println(myDoc.toJson());
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
	  
	  MongoCursor<Document> cursor = collection.find().iterator();
      List<Recipe> recipes = new ArrayList<>();

	  try {
	      while (cursor.hasNext()) {
	          System.out.println(cursor.next().toJson());

//	          Recipe recipe = new Recipe(id, name, url, image, rating, yield, ingredients, instructions);
//	          recipes.add(recipe);
	      }
	  } finally {
	      cursor.close();
	  }

      return recipes;

  }

}
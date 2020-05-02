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

public class IngredientsDatabase {
	
	private MongoCollection<Document> collection;

	public IngredientsDatabase() {
	  ConnectionString connString = new ConnectionString(
			  "mongodb+srv://cs32-project:cs32-project@cluster0-xndfh.gcp.mongodb.net/test?retryWrites=true&w=majority");
			MongoClientSettings settings = MongoClientSettings.builder()
			    .applyConnectionString(connString)
			    .retryWrites(true)
			    .build();
	  MongoClient mongoClient = MongoClients.create(settings);
	  MongoDatabase database = mongoClient.getDatabase("recipes");
	  collection = database.getCollection("ingredients");
	
	  // test to print out the first document in the collection
//	  Document myDoc = collection.find().first();
//	  System.out.println(myDoc.toJson());
	}
	
	public List<String> getIngredients() {
		  MongoCursor<Document> cursor = collection.find().iterator();
	      List<String> ingredients = new ArrayList<>();
		  try {
		      while (cursor.hasNext()) {
		         // System.out.println(cursor.next().toJson());
		          ingredients.add(cursor.next().getString("ingredient"));
		      }
		  } finally {
		      cursor.close();
		  }
	      return ingredients;		
	}

}
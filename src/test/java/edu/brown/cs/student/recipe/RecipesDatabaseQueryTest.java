package edu.brown.cs.student.recipe;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.all;
import org.bson.types.ObjectId;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class RecipesDatabaseQueryTest {
	
	  private MongoCollection<Document> collection;

	
	@Before
	public void SetUp() {
		  ConnectionString connString = new ConnectionString(
				  "mongodb+srv://cs32-project:cs32-project@cluster0-xndfh.gcp.mongodb.net/test?retryWrites=true&w=majority");
				MongoClientSettings settings = MongoClientSettings.builder()
				    .applyConnectionString(connString)
				    .retryWrites(true)
				    .build();
		  MongoClient mongoClient = MongoClients.create(settings);
		  MongoDatabase database = mongoClient.getDatabase("recipes");
		  collection = database.getCollection("bbc");
	}
	
	@After
	public void TakeDown() {
		collection = null;
	}

	/**
	 * queries the recipes database for the recipe with a specific ID, creates a recipe object for this recipe (in this case, the recipe
	 * is Spring chicken salad) and asserts the recipe's name, yield, rating, ingredients, and instructions are as expected.
	 */
	@Test
	public void queryByIDTest() {
		MongoCursor<Document> cursor = collection.find(eq("_id", new ObjectId("5eb22ae2191b49d834401323"))).iterator();
		Recipe recipe = null;
		try {
			while (cursor.hasNext()) {
				Document doc = cursor.next();
				String id = doc.get("_id").toString();
				String name = doc.getString("name");
				String url = doc.getString("url");
				String image = doc.getEmbedded(List.of("image", "url"), String.class);
				Double rating = Double.valueOf(doc.getEmbedded(List.of("rating"), Document.class).get("average").toString());
				String yield = doc.getString("yield");
				List<String> ingredients = doc.getList("ingredients", String.class);
				List<String> parsedIngredients = doc.getList("parsedIngredients", String.class);
				List<String> instructions = doc.getList("instructions", String.class);
				recipe = new Recipe(id, name, url, image, rating, yield, ingredients, parsedIngredients, instructions);
			}
		} finally {
			cursor.close();
		}

		assertEquals(recipe.getID(), "5eb22ae2191b49d834401323");
		assertEquals(recipe.getName(), "Overnight oats with apple and nuts ");
		assertEquals(recipe.getYield(), "Serves 2");
		assertEquals(recipe.getRating().toString(), "4.833333333333333");
		assertEquals(recipe.getParsedIngredients().get(0), "oats");
		assertEquals(recipe.getParsedIngredients().get(3), "cinnamon");
		assertEquals(recipe.getParsedIngredients().get(7), "blueberries");
	}
	
	/**
	 * Queries the recipes database for all recipes that contain the ingredient parsley
	 */
	@Test
	public void queryByArrayTest() {

		MongoCursor<Document> cursor = collection.find(all("parsedIngredients", Arrays.asList("parsley"))).iterator();
		List<Recipe> res = new ArrayList<>();
		try {
			while (cursor.hasNext()) {
				try {
					Document doc = cursor.next();
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
				} catch (NullPointerException e) {
				}
			}
		} finally {
			cursor.close();
		}		
		for (Recipe r : res) {
			assertTrue(r.getParsedIngredients().toString().contains("parsley"));
		}
	}

}

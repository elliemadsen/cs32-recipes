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
		  collection = database.getCollection("foodnetwork");
	}
	
	@After
	public void TakeDown() {
		collection = null;
	}

	/**
	 * queries the recipes database for the recipe with a specific ID, creates a recipe object for this recipe (in this case, the recipe
	 * is Loaded Baked Potato Casserole) and asserts the recipe's name, yield, rating, ingredients, and instructions are as expected.
	 */
	@Test
	public void queryByIDTest() {
		MongoCursor<Document> cursor = collection.find(eq("_id", new ObjectId("5eab1b9f135825b7f7696028"))).iterator();
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
				List<String> instructions = doc.getList("instructions", String.class);
				recipe = new Recipe(id, name, url, image, rating, yield, ingredients, instructions);
			}
		} finally {
			cursor.close();
		}
		
		assertEquals(recipe.getID(), "5eab1b9f135825b7f7696028");
		assertEquals(recipe.getName(), "Loaded Baked Potato Casserole");
		assertEquals(recipe.getYield(), "8 servings");
		assertEquals(recipe.getRating().toString(), "4.4");
		assertTrue(recipe.getIngredients().toString().contains("bacon"));
		assertTrue(recipe.getIngredients().toString().contains("potatoes"));
		assertTrue(recipe.getIngredients().toString().contains("cream cheese"));
		assertTrue(recipe.getIngredients().toString().contains("sour cream"));
		assertTrue(recipe.getIngredients().toString().contains("scallions"));
		assertTrue(recipe.getIngredients().toString().contains("Kosher salt and freshly ground black pepper"));
		assertEquals(recipe.getInstructions().get(0), "Preheat the oven to 425 degrees F and spray a 3-quart casserole dish with nonstick spray. Set aside.");
		assertTrue(recipe.getInstructions().get(4).contains("Dollop the sour cream evenly over the casserole and sprinkle with the scallions and bacon pieces."));

	}
	
	/**
	 * Queries the recipes database for all recipes that contain the ingredients "2 tablespoons unsalted butter" and "1 cup chicken broth".
	 * In this case, there is only one recipe that contains these two ingredients: Best Ever Green Bean Casserole.
	 */
	@Test
	public void queryByArrayTest() {

		MongoCursor<Document> cursor = collection.find(all("ingredients", Arrays.asList("2 tablespoons unsalted butter", "1 cup chicken broth"))).iterator();
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
					List<String> instructions = doc.getList("instructions", String.class);
					Recipe recipe = new Recipe(id, name, url, image, rating, yield, ingredients, instructions);
					res.add(recipe);
				} catch (NullPointerException e) {
				}
			}
		} finally {
			cursor.close();
		}
		
		for (Recipe r : res) {
			assertTrue(r.getIngredients().toString().contains("2 tablespoons unsalted butter"));
			assertTrue(r.getIngredients().toString().contains("1 cup chicken broth"));
		}
	}

}

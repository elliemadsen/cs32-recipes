//package edu.brown.cs.student.account;
//
//import static org.junit.Assert.*;
//
//import java.sql.SQLException;
//import java.util.Arrays;
//import java.util.List;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import edu.brown.cs.student.Account.AccountDatabase;
//import edu.brown.cs.student.recipe.Recipe;
//import edu.brown.cs.student.recipe.RecipesDatabase;
//import edu.brown.cs.student.search.SearchAlgo;
//
//public class SearchAccountTest {
//	
//	private SearchAlgo searcher;
//	private AccountDatabase db;
//	
//	@Before
//	public void SetUp() {
//		searcher = new SearchAlgo(new RecipesDatabase());
//		db = new AccountDatabase("data/account/accounts.sqlite3");
//
//	}
//	
//	@After
//	public void TakeDown() {
//		searcher = null;
//	}
//	
//	@Test
//	public void simpleSearchTest() throws SQLException {
//		
//			db.signUp("testUser", "pass");
//			db.add("testUser", "oregano", "Restriction");
//			db.add("testUser", "olive oil", "Pantry");
//			db.add("testUser", "sausages", "Pantry");
//
//			
//			
//			String o = "oregano";
//			List<String> exclusions = Arrays.asList(o);
//			List<String> inclusions = Arrays.asList("salt", "garlic", "cheese");
//			List<String> pantry = Arrays.asList("sausages", "olive oil");
//			List<Recipe> res = searcher.search(inclusions, db.getList("testUser", "Restriction"), db.getList("testUser", "Pantry"));
//			
//			for (Recipe r : res) {
//				assertTrue(r.getParsedIngredients().contains("salt"));
//				assertTrue(r.getParsedIngredients().contains("garlic"));
//				assertTrue(r.getParsedIngredients().contains("cheese"));
//				assertFalse(r.getParsedIngredients().contains("oregano"));
//			}
//			
//			// first recipe has 2 pantry ingredients (sausages and olive oil)
//			assertEquals(res.get(0).getName(), "Pizza expressed three ways");
//			// second recipe has 1 pantry ingredient (olive oil)
//			assertEquals(res.get(1).getName(), "Savoury pies with Jarlsberg cheese and ham (Norwegian pierogi)");
//			// third recipe has 0 pantry ingredients
//			assertEquals(res.get(2).getName(), "Savoury French toast");
//	}
//	
//	@Test
//	public void secondSearchTest() {
//		List<String> exclusions = Arrays.asList("chicken", "egg");
//		List<String> inclusions = Arrays.asList("spinach", "pasta");
//		List<String> pantry = Arrays.asList("flour", "bacon", "rice");
//		List<Recipe> res = searcher.search(inclusions, exclusions, pantry);
//				
//		for (Recipe r : res) {
//			assertTrue(r.getParsedIngredients().contains("spinach"));
//			assertTrue(r.getParsedIngredients().contains("pasta"));
//			assertFalse(r.getParsedIngredients().contains("chicken"));
//			assertFalse(r.getParsedIngredients().contains("egg"));
//		}
//		// first recipe has 1 pantry ingredient (bacon)
//		assertEquals(res.get(0).getName(), "Tuscan bacon and bean soup with wild garlic pesto");
//		// second and third recipes have 0 pantry ingredients so should be ordered by rating
//		assertTrue(res.get(1).getRating() > res.get(2).getRating());
//
//	}
//	
//	@Test
//	public void thirdSearchTest() {
//		List<String> exclusions = Arrays.asList("pasta");
//		List<String> inclusions = Arrays.asList("flour", "sugar");
//		List<String> pantry = Arrays.asList();
//		List<Recipe> res = searcher.search(inclusions, exclusions, pantry);
//				
//		double topRating = 5.0;
//		for (Recipe r : res) {
//			assertTrue(r.getParsedIngredients().contains("flour"));
//			assertTrue(r.getParsedIngredients().contains("sugar"));
//			assertFalse(r.getParsedIngredients().contains("pasta"));
//			
//			// assert recipes are in order of decreasing rating
//			assertTrue(r.getRating() <= topRating);
//			topRating = r.getRating();
//		}
//	}
//	
//	@Test
//	public void fourthTest() {
//		List<String> exclusions = Arrays.asList("chicken", "beef", "turkey", "ham");
//		List<String> inclusions = Arrays.asList("apples", "cinnamon");
//		List<String> pantry = Arrays.asList("oats");
//		List<Recipe> res = searcher.search(inclusions, exclusions, pantry);
//		
//		for (Recipe r : res) {
//			assertTrue(r.getParsedIngredients().contains("apples"));
//			assertTrue(r.getParsedIngredients().contains("cinnamon"));
//			assertFalse(r.getParsedIngredients().contains("chicken"));
//			assertFalse(r.getParsedIngredients().contains("beef"));
//			assertFalse(r.getParsedIngredients().contains("turkey"));
//			assertFalse(r.getParsedIngredients().contains("ham"));
//		}
//		
//		// four recipes contain 1 pantry ingredient (oats) -> order by rating
//		double topRating = 5.0;
//		for (int i = 0; i < 4; i++) {
//			assertTrue(res.get(i).getRating() <= topRating);
//			topRating = res.get(i).getRating();
//		}
//		// the rest of the recipes contain 0 pantry ingredients -> order by rating
//		topRating = 5.0;
//		for (int i = 4; i < res.size(); i++) {
//			assertTrue(res.get(i).getRating() <= topRating);
//			topRating = res.get(i).getRating();
//		}
//	}
//}

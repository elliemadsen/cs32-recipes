package edu.brown.cs.student.recipe;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.brown.cs.student.search.SearchAlgo;

public class SearchTest {
	
	private SearchAlgo searcher;
	
	@Before
	public void SetUp() {
		searcher = new SearchAlgo(new RecipesDatabase());

	}
	
	@After
	public void TakeDown() {
		searcher = null;
	}

	
	/**
	 * Searches for all recipes that contain the ingredient "1 teaspoon garlic powder" and do not contain the ingredient "1 teaspoon dried oregano"
	 * Tests that all results contain "1 teaspoon garlic powder" and no not contain "1 teaspoon dried oregano".
	 * Tests that the results are sorted by # of ingredients in pantry ("2 cups all-purpose flour", "1 egg") followed by decreasing rating.
	 */
	@Test
	public void getRecipesTest() {
		List<String> exclusions = Arrays.asList("1 teaspoon dried oregano");
		List<String> inclusions = Arrays.asList("1 teaspoon garlic powder");
		List<String> pantry = Arrays.asList("2 cups all-purpose flour", "1 egg");
		List<Recipe> res = searcher.search(inclusions, exclusions, pantry);
		
		/**
		 * RESULTING ORDER SHOULD BE:
		 * 1. Gina's Shrimp Corn Dogs (garlic powder, flour, egg)
		 * 2. Sloppy Joes with Maui Onion Straws (garlic poweder, flour)
		 * 3. all the other recipes with garlic powder, sorted by rating
		 */
		
		/**
		 * RESULTS SHOULD EXCLUDE: (bc they contain 1 teaspoon dried oregano)
		 * Pork Tenderloin with Seasoned Rub
		 * Five Layer Taco Dip
		 * Orangey Herb and Oat-Crusted Chicken Tenders
		 */
		
		for (Recipe r : res) {
			assertTrue(r.getIngredients().toString().contains("1 teaspoon garlic powder"));
			assertFalse(r.getIngredients().toString().contains("1 teaspoon dried oregano"));

			assertFalse(r.getName().equals("Pork Tenderloin with Seasoned Rub"));
			assertFalse(r.getName().equals("Five Layer Taco Dip"));
			assertFalse(r.getName().equals("Orangey Herb and Oat-Crusted Chicken Tenders"));
		}
		
		assertEquals(res.get(0).getName(), "Gina's Shrimp Corn Dogs"); // 2 pantry ingredients
		assertEquals(res.get(1).getName(), "Sloppy Joes with Maui Onion Straws"); // 1 pantry ingredient
		
		// for recipes with 0 pantry ingredients, assert they are ordered by decreasing rating:
		double topRating = 5.;
		for (int i = 2; i <res.size(); i++) {
			assertTrue(res.get(i).getRating() <= topRating);
			topRating = res.get(i).getRating();
		}

		

		
//		ALL RECIPES W/ 1 teaspoon garlic powder:
//		recipe name: Orangey Herb and Oat-Crusted Chicken Tenders
//		recipe ingredients: [1/4 cup quick-cooking oats, 2 tablespoons all-purpose flour, 1 teaspoon dried oregano, 1 teaspoon dried thyme, 1 teaspoon garlic powder, 1/2 teaspoon onion powder, 1/2 teaspoon salt, 1/4 teaspoon freshly ground black pepper, 1 1/4 pounds chicken tenders (rib meat) or boneless, skinless chicken breasts cut into thin strips, 1/3 cup orange marmalade, Nonstick cooking spray]
//		recipe name: Devil Crab Rolls
//		recipe ingredients: [2 pounds stale bread, cut into cubes, 2 quarts water, 2 ounces salt, 1/2-ounce garlic salt, 1 tablespoon black pepper, 1/2 teaspoon red chile flakes, 1 teaspoon crab boil seasoning, 1 stalk celery, chopped finely, 2 medium yellow onions, chopped finely, 1 1/2 pounds bread crumbs, plus more for breading, 1 pound crab meat, cleaned and picked free of crab shells, 1 pint Crab Sauce, recipe follows, Oil, for frying, 8 ounces crushed tomatoes, 4 ounces tomato puree, 1 teaspoon garlic powder, 1 teaspoon salt, 1 bay leaf, 2 ounces hot sauce, 4 ounces crabmeat, cleaned and picked over for shells, 1 small onion, chopped finely]
//		recipe name: Lightened-Up Slow-Cooker Chicken Buffalo Dip
//		recipe ingredients: [Two 8-ounce packages reduced-fat cream cheese, cut into small pieces, 2 cups shredded white meat from a rotisserie chicken, skin and bones discarded, 2 cups shredded low-fat Cheddar (about 6 ounces), 1 cup buttermilk, 1/3 cup hot sauce, such as Frank's, 1 tablespoon all-purpose flour, 1 teaspoon Worcestershire sauce, 3/4 teaspoon cayenne pepper, 1 teaspoon garlic powder, 1 teaspoon onion powder, 1/2 cup parsley leaves, chopped, 1/4 cup crumbled blue cheese, Celery and carrot sticks, for serving]
//		recipe name: Beef and Black Bean Sliders
//		recipe ingredients: [1/4 cup cooked black beans, 2 tablespoons olive oil, 1 teaspoon garlic powder, Kosher salt and freshly ground black pepper, 1/2 pound ground beef, 1 tablespoon Dijon mustard, 8 mini-hamburger buns, Grilled Crunchy Coleslaw, recipe follows, 1/2 head red cabbage, cut into 4 wedges, leaving core intact so wedges hold together, 2 tablespoons canola or vegetable oil, 1 tablespoon sugar, Kosher salt and freshly ground black pepper, 1 teaspoon finely grated orange zest, 1 teaspoon Dijon mustard, 1 tablespoon fresh orange juice, 1/4 cup olive oil, 1/4 cup chopped fresh parsley, 1 green onion, chopped]
//		recipe name: Gina's Shrimp Corn Dogs
//		recipe ingredients: [1 pound raw shrimp, peeled and deveined, 1 clove garlic, minced, 1 shallot, finely chopped, 1/2 lemon, zested and juiced, 1/4 cup bread crumbs, Kosher salt and freshly ground black pepper, 2 cups all-purpose flour, 1 egg, 2 cups yellow cornmeal, 1 teaspoon garlic powder, 3 tablespoons sugar, 1 1/2 tablespoons baking powder, 2 1/2 cups buttermilk, Dipping Sauce, recipe follows, 1 cup mayonnaise, 1/2 lemon, juiced, 2 tablespoons Dijon mustard, 1 tablespoon horseradish, 1 teaspoon paprika]
//		recipe name: Italian Seven-Layer Dip
//		recipe ingredients: [1 baguette, thinly sliced, 1/4 cup extra-virgin olive oil, 1 teaspoon salt, 1 teaspoon garlic powder, Freshly ground black pepper, 2 cups ricotta, Kosher salt and freshly ground black pepper, 2 tablespoons olive oil, 1 medium onion, chopped, 2 cups grape tomatoes, halved, 1/2 cup chicken stock, 5 cloves garlic, chopped, 12 ounces spicy Italian sausage, casings removed, 1/2 cup sliced pepperoncini, drained, One 12-ounce jar marinated artichoke hearts, drained and chopped, One 12-ounce jar roasted red peppers, drained and chopped, One 8-ounce container fresh mozzarella ciliegine, drained and cut in half, 8 leaves basil, cut into chiffonade]
//		recipe name: Fried Oysters
//		recipe ingredients: [Peanut oil, for frying, 3/4 cup buttermilk, 1 teaspoon garlic powder, 1 teaspoon paprika, Dash hot sauce, such as Tabasco, 1 cup cornmeal, 1 cup all-purpose flour, Kosher salt and freshly ground black pepper, 36 shucked oysters, Remoulade Sauce, for serving, recipe follows, 1 cup mayonnaise, 1 tablespoon Creole mustard, 1 tablespoon paprika, 1 tablespoon white wine vinegar, 1 tablespoon Worcestershire sauce, Dash hot sauce, Kosher salt and freshly ground black pepper]
//		recipe name: Beef Roast with Root Vegetables
//		recipe ingredients: [1/2 cup chopped onion, 2 large sweet potatoes, cut into 2-inch pieces, 2 parsnips, peeled and chopped, 1 celery root (celeriac), peeled and chopped, 1 (5-pound) boneless beef roast, trimmed of fat, Salt and ground black pepper, 3 tablespoons all-purpose flour, 1 (14-ounce) can diced tomatoes, 1/3 cup maple syrup, 2 teaspoons chili powder, 1 teaspoon ground cumin, 1 teaspoon garlic powder, 1 teaspoon onion powder]
//		recipe name: Panko Chicken Nuggets
//		recipe ingredients: [1 large egg, 1/3 cup buttermilk, 1 pound boneless skinless chicken breast, cut into bite-size pieces, 1 1/2 cups panko bread crumbs, 1/4 teaspoon paprika, 1 teaspoon garlic powder, 1 teaspoon Italian seasoning, Salt and pepper, 1/2 cup canola or vegetable oil, Ranch, honey mustard or barbecue sauce for dipping, optional, Carrot, cucumber and or celery sticks, optional]
//		recipe name: Sloppy Joes with Maui Onion Straws
//		recipe ingredients: [1/4 cup canola oil, 1 red bell pepper, seeded and diced, 1 green bell pepper, seeded and diced, 1 jalapeno pepper, seeded, deveined and diced, 1 red onion, diced, 1 pound ground beef, 1 pound ground pork, 2 tablespoons minced garlic, 1/4 cup red wine, 1/4 cup red wine vinegar, 1 teaspoon cayenne pepper, 1 tablespoon paprika, 1 teaspoon ground cumin, 1 teaspoon dry mustard, 1 6-ounce can tomato paste, 1 1/2 cups tomato sauce, 3 tablespoons packed brown sugar, 1/4 cup Worcestershire sauce, Kosher salt and freshly ground pepper, 24 slider rolls or sweet Hawaiian rolls, 2 cups canola oil, 1 large egg, 1/2 cup milk, 2 cups all-purpose flour, 1 teaspoon cayenne pepper, 1 teaspoon paprika, 1 teaspoon garlic powder, Kosher or sea salt and freshly ground pepper, 1 sweet onion (Maui or Vidalia)]
//		recipe name: Pork Tenderloin with Seasoned Rub
//		recipe ingredients: [1 teaspoon garlic powder, 1 teaspoon dried oregano, 1 teaspoon ground cumin, 1 teaspoon ground coriander, 1 teaspoon dried thyme, Salt, 1 1/4 pounds pork tenderloin, 1 tablespoon olive oil, 1 teaspoon minced garlic]
//		recipe name: Tony's Chicken Tenders with Honey Mustard Sauce
//		recipe ingredients: [Peanut oil, for frying, 2 pounds boneless, skinless chicken breasts, 3 eggs, 1 cup all-purpose flour, 2 cups panko bread crumbs, 1 teaspoon garlic powder, 1 teaspoon lemon-pepper, 1/2 teaspoon cayenne, 1/2 teaspoon salt, 1/2 teaspoon pepper, Honey Mustard, recipe follows, 1/2 cup Dijon mustard, 1/2 cup honey, 2 tablespoons mayonnaise, 1 tablespoons lemon juice, Salt and pepper]
//		recipe name: Baked Meatballs
//		recipe ingredients: [1/2 pound ground pork, 1/2 pound ground lamb, 1/2 pound ground round, 5 ounces frozen spinach, thawed and drained thoroughly, 1/2 cup finely grated Parmesan, 1 whole egg, 1 1/2 teaspoons dried basil, 1 1/2 teaspoons dried parsley, 1 teaspoon garlic powder, 1 teaspoon kosher salt, 1/2 teaspoon red pepper flakes, 1/2 cup bread crumbs, divided]
//		recipe name: Sweet Potato Chips Dusted with Chili Powder
//		recipe ingredients: [2 large sweet potatoes, 2 teaspoons chili powder, 1 teaspoon garlic powder, 1/4 teaspoon cayenne powder, 1/2 teaspoon ground cumin, 1 teaspoon kosher salt, 1/2 teaspoon ground black pepper, Vegetable oil, for deep-frying, Blue Cheese and Chive Dip, recipe follows, 1 cup sour cream, 1 cup mayonnaise, store-bought, 1 teaspoon fresh lemon juice, 1 cup crumbled blue cheese, 1 1/2 tablespoons honey, 1/2 bunch finely chopped chives, plus extra for garnish, Kosher salt and freshly ground black pepper]
//		recipe name: Black-Eyed Peas with Bacon and Pork
//		recipe ingredients: [1 pound dried black-eyed peas (fresh or canned black-eyed peas can be substituted), 2 tablespoons vegetable oil, 6 ounces pork shoulder, diced into 1/2-inch cubes, 4 strips thick sliced bacon, cut into 1/2-inch pieces, 1 medium onion, small diced, 4 garlic cloves, sliced, 1 1/2 teaspoons salt, 1 teaspoon freshly cracked black pepper, 1/2 teaspoon cayenne pepper, 1 teaspoon garlic powder, 4 cups chicken stock, 2 cups water, 3 bay leaves, Hot-pepper vinegar, as desired]
//		recipe name: Five Layer Taco Dip
//		recipe ingredients: [1 tablespoon olive oil, Nonstick cooking spray, 1 1/2 cups crushed tortilla chips, divided, 1 pound ground beef, One 1.25-ounce packet taco seasoning mix, or homemade Taco Seasoning, recipe follows, 1 cup prepared salsa, 1 cup sour cream, 1 avocado, halved, peeled, pitted, and cubed, 1 cup grated extra-sharp Cheddar (4 ounces), 1 tablespoon chili powder, 2 teaspoons onion powder, 1 teaspoon ground cumin, 1 teaspoon garlic powder, 1 teaspoon paprika, 1 teaspoon dried oregano, 1 teaspoon sugar, 1/2 teaspoon salt]
//		recipe name: Chicken and Stuffed Waffles
//		recipe ingredients: [Canola oil, 1 half chicken breast (4 to 5 ounces), boneless and skinless, Kosher salt and freshly ground black pepper, Kosher salt and freshly ground black pepper▒, 1/2 cup finely diced onion (about 1/2 small onion), 1/2 cup finely diced green bell pepper (about 1 medium pepper), 1/2 cup finely diced red bell pepper (about 1 medium pepper), 1/2 teaspoon ground cumin, 1 1/2 cups fine cornmeal, 1 1/4 cups all-purpose flour, 1 tablespoon baking powder, 1 teaspoon kosher salt, 1 teaspoon sugar, 1 3/4 cups whole milk, 4 tablespoons melted butter, 2 eggs, 1 cup plus 3 tablespoons all-purpose flour, 2 eggs, whisked, 2 cups panko breadcrumbs, 3 boneless, skinless chicken breasts, halved horizontally, Kosher salt and freshly ground black pepper, 1/4 cup canola oil, 1 teaspoon garlic powder, 1 teaspoon Italian seasoning, 1/2 teaspoon onion powder, 1/8 teaspoon cayenne pepper, 3 cups whole milk, 1 tablespoon maple syrup, for serving, Fiery Fieri Hot Sauce, for serving, recipe follows, 8 ounces red Fresno chiles (7 or 8 chilies), 1/4 sweet Maui or Vidalia onion, roughly chopped, 1 teaspoon kosher salt, 1 cup distilled white vinegar, 2 teaspoons agave nectar]
//		recipe name: Caprese Tartlets
//		recipe ingredients: [8 slices white bread, 1/4 cup extra-virgin olive oil, 3 tablespoons cream cheese, softened, 1 teaspoon garlic powder, 1 teaspoon dried basil, Kosher salt and freshly ground black pepper, 2 Roma tomatoes, thinly sliced, 4 ounces fresh mozzarella (approximately 2-inch ball), thinly sliced, Balsamic vinegar, for drizzling, optional, 8 fresh basil leaves, chiffonade, Special Equipment: Muffin tin]
//		recipe name: Spiced Avocado Toast with Citrus-Cured Salmon and Poached Egg
//		recipe ingredients: [4 ounces kosher salt, 2 ounces granulated sugar, 1 tablespoon lemon extract, 1 tablespoon lime extract, One 1 1/2-pound skinless salmon fillet, 1/2 cup distilled white vinegar, 2 teaspoons paprika, 1 teaspoon garlic powder, 4 large eggs, Sea salt, 2 cups arugula or other mixed greens, 1/4 cup extra-virgin olive oil, 1 tablespoon capers, 2 teaspoons dried oregano▒, 2 teaspoons red wine vinegar, 1 teaspoon garlic powder, 1 teaspoon dried thyme, Sea salt and freshly ground black pepper, 2 tablespoons olive oil, 4 slices bread, from a whole-wheat sourdough boule, 2 ripe avocados, 2 teaspoons lime juice▒, 1 teaspoon garlic powder, 3/4 teaspoon kosher salt, 1 tablespoon chopped fresh herbs, such as chervil, chives, cilantro or parsley, 2 tablespoons poppy seeds, 2 tablespoons white sesame seeds, toasted, 1 tablespoon dried minced onion, toasted, 2 teaspoons dried minced garlic, toasted, 1 teaspoon pretzel salt or other coarse salt, 1/4 cup olive oil, 1/8 teaspoon lemon extract, 1/8 teaspoon lime extract]
//		recipe name: Fried Pickle Po' Boy
//		recipe ingredients: [1/2 cup mayonnaise, 1/4 teaspoon Louisiana-style hot sauce, plus more for serving, 2 teaspoons paprika, 1/2 cup milk, 1 large egg, 1/4 cup all-purpose flour, 1/4 cup cornstarch, 1/4 cup cornmeal, 1 teaspoon garlic powder, 1/2 teaspoon onion powder, Pinch of cayenne, Kosher salt and freshly ground black pepper, 2 cups vegetable oil, for frying, 40 cold dill pickle slices, patted dry, Four 8-inch New Orleans-style French loaves, 1 cup thinly shredded romaine lettuce, 3 plum tomatoes, sliced into 1/4-inch slices (12 slices)]
//		recipe name: Mom's Mashed Potatoes a La King
//		recipe ingredients: [4 pounds Idaho russet potatoes (about 5 potatoes), peeled and cut into 1-inch pieces, 1 pound celery root, peeled and cut into 1/2-inch pieces, Kosher salt, 2 sticks butter, 1 softened and 1 melted, plus more for greasing, 6 ounces cream cheese, 1 cup half-and-half, warmed, 1/4 cup prepared horseradish, 1 teaspoon garlic powder, 1/2 teaspoon ground white pepper, 1/4 cup fresh minced chives]
//
	}

}

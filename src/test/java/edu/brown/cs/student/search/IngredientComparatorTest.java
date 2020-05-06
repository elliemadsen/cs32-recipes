package edu.brown.cs.student.search;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.brown.cs.student.recipe.Recipe;

public class IngredientComparatorTest {
	
	IngredientComparator comparator;
	
	@Before
	public void SetUp() {
		comparator = new IngredientComparator(Arrays.asList("flour", "egg", "milk"));
	}
	
	@After
	public void TakeDown() {
		comparator = null;
	}

	@Test
	public void test() {
        List<String> r1_ingredients = Arrays.asList("spinach", "tomato", "lemon", "egg", "milk"); // 2 pantry ingredients
		Recipe r1 = new Recipe(null, "1", null, null, 5.0, null, null, r1_ingredients, null);
		List<String> r2_ingredients = Arrays.asList("pasta", "grapes", "flour"); // 1 pantry ingredient
		Recipe r2 = new Recipe(null, "2", null, null, 5.0, null, null, r2_ingredients, null);
		List<String> r3_ingredients = Arrays.asList("pasta", "grapes", "flour"); // 1 pantry ingredient
		Recipe r3 = new Recipe(null, "3", null, null, 4.0, null, null, r3_ingredients, null);
		
		assertTrue(comparator.compare(r1, r2) > 0); // r1 > r2 ( # ingredients)
		assertTrue(comparator.compare(r2, r3) > 0); // r2 > r3 (rating)

		List<Recipe> list = Arrays.asList(r1, r2, r3);
		Collections.sort(list, comparator);
		Collections.reverse(list);
		assertEquals(list.get(0).getName(), "1");
		assertEquals(list.get(1).getName(), "2");
		assertEquals(list.get(2).getName(), "3");

	}

}

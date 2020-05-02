package edu.brown.cs.student.suggestions;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.brown.cs.student.recipe.IngredientsDatabase;

public class AutocorrectTest {
	
	private Autocorrector ac;
	
	  /**
	   * Sets up the autocorrector.
	   */
	  @Before
	  public void setUp() {
		  ac = new Autocorrector(new IngredientsDatabase());
	  }

	  /**
	   * Resets the autocorrector.
	   */
	  @After
	  public void tearDown() {
	    ac = null;

	  }

//	@Test
//	public void ledTest() {
//		Set<String> suggestions = ac.suggest("appel");
//		assertTrue(suggestions.contains("apple"));
//		assertFalse(suggestions.contains("orange"));
//	}
//	
//	@Test
//	public void prefixTest() {
//		Set<String> suggestions = ac.suggest("chick");
//		assertTrue(suggestions.contains("chicken"));
//		assertTrue(suggestions.contains("chickpeas"));
//		assertFalse(suggestions.contains("chives"));
//	}
//	
//	@Test
//	public void whitespaceTest() {
//		Set<String> suggestions = ac.suggest("ba na na");
//		assertTrue(suggestions.contains("banana"));
//	}

}

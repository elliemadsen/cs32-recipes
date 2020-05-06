//package edu.brown.cs.student.suggestions;
//
//import static org.junit.Assert.*;
//
//import java.util.Set;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import edu.brown.cs.student.recipe.IngredientsDatabase;
//
//public class AutocorrectTest {
//	
//	private Autocorrector ac;
//	
//	  /**
//	   * Sets up the autocorrector.
//	   */
//	  @Before
//	  public void setUp() {
//		  ac = new Autocorrector(new IngredientsDatabase());
//	  }
//
//	  /**
//	   * Resets the autocorrector.
//	   */
//	  @After
//	  public void tearDown() {
//	    ac = null;
//	  }
//
//	@Test
//	public void ledTest() {
//		assertTrue(ac.suggest("appel").contains("apple"));		
//		assertTrue(ac.suggest("grapez").contains("grapes"));
//		assertTrue(ac.suggest("chikcen").contains("chicken"));
//		assertTrue(ac.suggest("spinch").contains("spinach")); //led=1
//		assertTrue(ac.suggest("spnch").contains("spinach")); //led=2
//
//	}
//	
//	@Test
//	public void prefixTest() {
//		assertTrue(ac.suggest("chick").contains("chicken"));
//		assertTrue(ac.suggest("lemo").contains("lemon"));
//		assertTrue(ac.suggest("noo").contains("noodles"));
//		assertTrue(ac.suggest("smoked had").contains("smoked haddock"));
//	}
//	
//	@Test
//	public void whitespaceTest() {
//		assertTrue(ac.suggest("chickenbreast").contains("chicken breast"));
//		assertTrue(ac.suggest("sourcream").contains("sour cream"));
//		assertTrue(ac.suggest("redcabage").contains("red cabbage"));
//	}
//	
//}

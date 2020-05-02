package edu.brown.cs.student.suggestions;


import java.util.List;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Set;

import static org.junit.Assert.*;

public class TrieTest {

  private Trie _trie;

  /**
   * Sets up the trie using a few sample words.
   */
  @Before
  public void setUp() {
    _trie = new Trie();
    List<String> words = new ArrayList<String>();
    words.add("car");
    words.add("ride");
    words.add("card");
    words.add("carpool");
    words.add("dig");
    words.add("dive");
    words.add("drive");
    words.add("way");
    words.add("big");
    words.add("bite");
    _trie.insertAll(words);

  }

  /**
   * Resets the Trie.
   */
  @After
  public void tearDown() {
    _trie = null;

  }

  /**
  ** Tests whether the prefixes belong in the trie.
  */

  @Test
  public void testHasPrefix() {
    setUp();
    assertTrue(_trie.hasPrefix("car"));
    assertFalse(_trie.hasPrefix("cap"));
    assertTrue(_trie.hasPrefix("di"));
    tearDown();

  }

  /**
  ** Tests whether the trie contains a given word.
  **
  ** FIX THIS TEST SO THAT IT PASSES.
  */

  @Test
  public void testHasWord() {
    setUp();
    assertTrue(_trie.hasWord("carpool"));
    assertFalse(_trie.hasWord("code")); // assertTrue -> assertFalse
    assertTrue(_trie.hasWord("big"));
    tearDown();

  }

  /**
  ** Tests whether the words in trie with given prefix are returned correctly.
  */

  @Test
  public void testAllWithPrefix() {
    setUp();
    Set<String> wordsWithPref = _trie.findAllWithPrefix("ca", "ca");
    assertEquals(wordsWithPref.size(), 3);
    assertTrue(wordsWithPref.contains("car"));
    assertTrue(wordsWithPref.contains("card"));
    assertTrue(wordsWithPref.contains("carpool"));
    assertFalse(wordsWithPref.contains("drive"));
    tearDown();

  }

  /**
  ** Ensures that the list of words with spaces that can be formed by input phrase
  ** are correctly outputted.
  **
  ** FIX THIS TEST SO IT PASSES.
  */

  @Test
  public void testWhitespace() {
    setUp();
    Set<String> wordsWithSpace = _trie.whiteSpace("carride");
    assertEquals(wordsWithSpace.size(), 1); // 0 -> 1
    assertTrue(wordsWithSpace.contains("car ride"));
    assertFalse(wordsWithSpace.contains("drive way"));
    Set<String> wordsWithSpace2 = _trie.whiteSpace("driveway");
    assertEquals(wordsWithSpace2.size(), 1);
    assertTrue(wordsWithSpace2.contains("drive way"));
    assertFalse(wordsWithSpace2.contains("car ride"));
    tearDown();

  }

  /**
  ** Tests that the correct led is found between two words.
  **
  ** FIX THIS TEST SO IT PASSES.
  */

  @Test
  public void testLedDistance() {
    setUp();
    assertEquals(Trie.getLedDistance("car", "card"), 1); // 2 -> 1
    assertEquals(Trie.getLedDistance("big", "dig"), 1);
    assertEquals(Trie.getLedDistance("dig", "dive"), 2);
    assertEquals(Trie.getLedDistance("drive", "dive"), 1);
    tearDown();
  }

  /**
  ** Tests that the set of all words in Trie with led less than or equal to input phrase
  * has correct output.
  */

  @Test
  public void testLed() {
    setUp();
    Set<String> wordsWithinLed = _trie.findLedWithinRoot("cat", 2);
    assertEquals(wordsWithinLed.size(), 3);
    assertTrue(wordsWithinLed.contains("card"));
    assertTrue(wordsWithinLed.contains("car"));
    Set<String> wordsWithinLed2 = _trie.findLedWithinRoot("dig", 2);
    assertEquals(wordsWithinLed2.size(), 3);
    assertTrue(wordsWithinLed2.contains("dig"));
    assertTrue(wordsWithinLed2.contains("big"));
    assertTrue(wordsWithinLed2.contains("dive"));
    tearDown();

  }

   /**
    ** Think of an edge case that should be tested for a method in Trie.java!
    **
    ** FILL THIS IN TO PRACTICE WRITING YOUR OWN UNIT TESTS!
    */

    @Test
    public void testEdgeCase() {

      // CODE GOES HERE
        setUp();
        assertFalse(_trie.hasWord(""));
        assertEquals(_trie.getLedDistance("word", "word"), 0);
        assertEquals(_trie.getLedDistance("", ""), 0);
        tearDown();



    }

}

package edu.brown.cs.student.suggestions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import edu.brown.cs.student.recipe.IngredientsDatabase;


/**
 * Class to create autocorrect suggestions given input.
 *
 */
public class Autocorrector {

	private Trie trie;
	private final static int LED = 2;

	public Autocorrector(IngredientsDatabase db) {
		trie = new Trie();
		trie.insertAll(db.getIngredients());
	}

  
  	/**
   	* Given phrase input by user, gives autocorrect suggestions.
  	*
   	* @param phrase: String input by user
   	* @return Set of strings representing suggestions
   	*/
  	public Set<String> suggest(String phrase) {
		// Regex to make input consistent. 
		String query = phrase.toLowerCase().replaceAll("[^a-z ]", " ").trim().replaceAll(" +", " ");
		List<String> words = new ArrayList<String>(Arrays.asList(query.split(" ")));

		// Search trie for each type of flag set and add resulting words.
		Set<String> trieOutput = new TreeSet<String>();
		if (words.size() > 0) {
			String acWord = words.get(words.size() - 1);
				trieOutput.addAll(trie.findAllWithPrefix(acWord, acWord));
				trieOutput.addAll(trie.whiteSpace(acWord));
				trieOutput.addAll(trie.findLedWithinRoot(acWord, LED));
		}
	  
	  	// Append suggestions to earlier part of phrase. 
		List<String> trieOutputAsList = new ArrayList<String>(trieOutput);
		Set<String> suggestions = new TreeSet<String>();
		if (query.indexOf(' ') != -1) {
			String unchanged = query.substring(0, query.lastIndexOf(' '));
			for (int i = 0; i < Math.min(5, trieOutputAsList.size()); i++) {
				suggestions.add(unchanged + " " + trieOutputAsList.get(i));
			}
		} else {
			for (int i = 0; i < Math.min(5, trieOutputAsList.size()); i++) {
				suggestions.add(trieOutputAsList.get(i));
			}
		}
		return suggestions;
  	}

}

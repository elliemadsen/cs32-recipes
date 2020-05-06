package edu.brown.cs.student.main;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import edu.brown.cs.student.Account.AccountDatabase;
import edu.brown.cs.student.recipe.IngredientsDatabase;
import edu.brown.cs.student.recipe.Recipe;
import edu.brown.cs.student.recipe.RecipesDatabase;
import edu.brown.cs.student.search.SearchAlgo;
import edu.brown.cs.student.suggestions.Autocorrector;

/**
 * All methods required for backend
 * @author yuna.hiraide
 *
 */
public class Backend {
	
	private Autocorrector ac;
	private SearchAlgo searcher;
	private AccountDatabase db;
	private String user;
	private Recipe recipe;

	public Backend() {
		ac = new Autocorrector(new IngredientsDatabase());
		searcher = new SearchAlgo(new RecipesDatabase());
		db = new AccountDatabase("data/account/accounts.sqlite3");
		user =  null;
	}

/**
 * Signup
 * Return 1 and set user to username on success. Return 0 on failure
 */
	public int signUp(String username, String password) throws SQLException {
		user = null;
		List<String> result = db.signUp(username, password);
		if (result != null && result.size() == 1) {
			user = result.get(0);
			return 1;
		}
		return 0;
	}
	
/**
 * Login
 * Return 1 and set user to username on success Return 0 on failure
 */
	public int login(String username, String password) throws SQLException {
		user = null;
		List<String> result = db.login(username, password);
		if (result != null && result.size() == 1) {
			user = result.get(0);
			return 1;
		}
		return 0;
	}
	
	public List<String> getPantry() throws SQLException {
		return db.getList(user, "Pantry");
	}
	
	public List<String> getRestriction() throws SQLException {
		return db.getList(user, "Restriction");
	}
	
	public List<Recipe> getSearch(String include) throws SQLException {
		List<String> inclusions = Arrays.asList(include);
		List<Recipe> res = searcher.search(inclusions, db.getList(user, "Restriction"), db.getList(user, "Pantry"));
		return res;
		
	}
	/**
	 * 	remove item from Pantry
	 */
	
	public void removeFromPantry(String item) throws SQLException {
		db.remove(user, item, "Pantry");
	}
	/**
	 * 	remove item from Restriction List
	 */
		
	public void removeFromRestriction(String item) throws SQLException {
		db.remove(user, item, "Restriction");
	}
	
	public void addToPantry(String item) throws SQLException {
		db.add(user, item, "Pantry");
	}
	
	public void addToRestriction(String item) throws SQLException {
		db.add(user, item, "Restriction");
	}
	
	public Set<String> getAutocorrect(String word){
		return ac.suggest(word);
	}
	
	public Recipe getRecipe() {
		return null;
	}
	
	public Recipe setRecipe() {
		return null;
		
	}
	
	
}
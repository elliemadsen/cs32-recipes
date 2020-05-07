package edu.brown.cs.student.main;


import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import edu.brown.cs.student.Account.AccountDatabase;
import edu.brown.cs.student.recipe.Recipe;
import freemarker.template.Configuration;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.*;
import edu.brown.cs.student.recipe.IngredientsDatabase;
import edu.brown.cs.student.recipe.RecipesDatabase;
import edu.brown.cs.student.search.SearchAlgo;
import edu.brown.cs.student.suggestions.Autocorrector;
//import edu.brown.cs.student.util.CombinedREPL;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import spark.*;
import spark.template.freemarker.FreeMarkerEngine;



/**
 * The Main class of our project. This is where execution begins.
 */
public final class Main {
  private static final Gson GSON = new Gson();
  private static final int DEFAULT_PORT = 4567;
  private static PrintWriter pw = new PrintWriter(System.out);
//  private static CombinedREPL repl = new CombinedREPL(pw, System.in);
  private static Backend backend = new Backend();


  /**
   * The initial method called when execution begins.
   *
   * @param args An array of command line arguments
   */
  public static void main(String[] args) {
    try {
      new Main(args).run();
    } catch (IOException e) {
      System.out.println("ERROR: Input/output exception.");
    }
  }

  private String[] args;

  private Main(String[] args) {
    this.args = args;
  }

  private void run() throws IOException {
    runSparkServer(4567);
    // Autocorrector ac = new Autocorrector(new IngredientsDatabase());
    //SearchAlgo search = new SearchAlgo(new RecipesDatabase());
  }


  @SuppressWarnings("unchecked")
  private void runSparkServer(int port) {
    Spark.port(port);
    Spark.externalStaticFileLocation("src/main/resources/static");
    Spark.exception(Exception.class, new ExceptionPrinter());
    enableCORS("*", "*", "*");
    Spark.post("/b/autocorrect", new AutocorrectHandler());
    Spark.post("/b/recipe", new RecipeHandler());
    Spark.post("/b/signup", new SignupHandler());
    Spark.post("/b/login", new LoginHandler());
    Spark.post("/b/addPantry", new AddPantryHandler());
    Spark.post("/b/getPantry", new GetPantryHandler());
    Spark.post("/b/removePantry", new RemovePantryHandler());
    Spark.post("/b/addRestriction", new AddRestrictionHandler());
    Spark.post("/b/getRestriction", new GetRestrictionHandler());
    Spark.post("/b/removeRestriction", new RemoveRestrictionHandler());
    Spark.post("/b/search", new SearchHandler());
    Spark.post("/b/setRecipe", new setRecipeHandler());
    Spark.post("/b/getRecipe", new getRecipeHandler());

    
  }
  
  private static class getRecipeHandler implements Route{
	  @Override
	  public String handle (Request req, Response res) {
		  QueryParamsMap request = req.queryMap();
		  String recipe = null;
		  recipe = backend.getUrl();
		  String name = backend.getName();
		  
		  Map<String, Object> responseObject = ImmutableMap.of("recipe",
	              recipe, "name", name);
	  return GSON.toJson(responseObject);
	  }
  }
  
  
  private static class setRecipeHandler implements Route{
	  @Override
	  public String handle (Request req, Response res) {
		  QueryParamsMap request = req.queryMap();
		  String recipe = request.value("recipe");
		  String name = request.value("name");

		  backend.setUrl(recipe);
		  backend.setName(name);
		  Map<String, Object> responseObject = ImmutableMap.of("recipe",
	              recipe);
		  return GSON.toJson(responseObject);
	  }
  }
  
  
  private static class SearchHandler implements Route{
	  @Override
	  public String handle (Request req, Response res) {
		  QueryParamsMap request = req.queryMap();
		  List<Recipe> recipe = null;
		  String item = request.value("item");
		  try {
		  recipe = backend.getSearch(item);
		  } catch (SQLException e) {
			  System.out.println("SQL");
		  }
		  Map<String, Object> responseObject = ImmutableMap.of("recipe",
	              recipe);
	  return GSON.toJson(responseObject);
	  }
  }
  
  
  private static class RemoveRestrictionHandler implements Route{
	  @Override
	  public String handle (Request req, Response res) {
		  QueryParamsMap request = req.queryMap();
		  List<String> restriction = null;
		  String item = request.value("item");
		  try {
			  backend.removeFromRestriction(item);
			  restriction = backend.getRestriction();
		  } catch (SQLException e) {
			  System.out.println("SQL");
		  }
		   Map<String, Object> responseObject = ImmutableMap.of("restriction",
		              restriction);
		  return GSON.toJson(responseObject);
	  }	
  }
  
  
  private static class GetRestrictionHandler implements Route{
	  @Override
	  public String handle (Request req, Response res) {
		  QueryParamsMap request = req.queryMap();
		  List<String> restriction = null;
		  try {
			  restriction = backend.getRestriction();
		  } catch (SQLException e) {
			  System.out.println("SQL");
		  }
		  
		   Map<String, Object> responseObject = ImmutableMap.of("restriction",
		              restriction);
		  return GSON.toJson(responseObject);
		  
	  }
  }
  
  
  private static class AddRestrictionHandler implements Route{
	  @Override
	  public String handle (Request req, Response res) {
		  QueryParamsMap request = req.queryMap();
		  List<String> restriction = null;
		  String item = request.value("item");
		  try {
			  backend.addToRestriction(item);
			  restriction = backend.getRestriction();
			  
		  } catch (SQLException e) {
			  System.out.println("SQL");
		  }
		   Map<String, Object> responseObject = ImmutableMap.of("restriction",
		              restriction);
		  return GSON.toJson(responseObject);
	  }	
  }
  
  
  
  
  
  
  
  
  
  private static class RemovePantryHandler implements Route{
	  @Override
	  public String handle (Request req, Response res) {
		  QueryParamsMap request = req.queryMap();
		  List<String> pantry = null;
		  String item = request.value("item");
		  try {
			  backend.removeFromPantry(item);
			  pantry = backend.getPantry();
		  } catch (SQLException e) {
			  System.out.println("SQL");
		  }
		   Map<String, Object> responseObject = ImmutableMap.of("pantry",
		              pantry);
		  return GSON.toJson(responseObject);
	  }	
  }
  
  
  private static class AutocorrectHandler implements Route{
	  @Override
	  public String handle (Request req, Response res) {
		  QueryParamsMap request = req.queryMap();
		  String word = request.value("word");
		  Set<String> suggestion = null;
		  suggestion = backend.getAutocorrect(word);
		   Map<String, Object> responseObject = ImmutableMap.of("suggestion",
		              suggestion);
		  return GSON.toJson(responseObject);
		  

	  }
  }
  
  private static class GetPantryHandler implements Route{
	  @Override
	  public String handle (Request req, Response res) {
		  QueryParamsMap request = req.queryMap();
		  List<String> pantry = null;
		  try {
			  pantry = backend.getPantry();
		  } catch (SQLException e) {
			  System.out.println("SQL");
		  }
		  
		   Map<String, Object> responseObject = ImmutableMap.of("pantry",
		              pantry);
		  return GSON.toJson(responseObject);
		  
	  }
  }
  
  private static class AddPantryHandler implements Route{
	  @Override
	  public String handle (Request req, Response res) {
		  QueryParamsMap request = req.queryMap();
		  List<String> pantry = null;
		  String item = request.value("item");
		  try {
			  backend.addToPantry(item);
			  pantry = backend.getPantry();
			  
		  } catch (SQLException e) {
			  System.out.println("SQL");
		  }
		   Map<String, Object> responseObject = ImmutableMap.of("pantry",
		              pantry);
		  return GSON.toJson(responseObject);
	  }	
  }
  
  private static class LoginHandler implements Route{
	  @Override
	  public String handle(Request req, Response res) {
		  QueryParamsMap request = req.queryMap();
		  String username = request.value("username");
		  String password = request.value("password");
	     String success = "no";

		  try{
			  System.out.println(backend.login(username, password));
			  System.out.println(backend.getUser());
			  if (backend.login(username, password) == 1) {
				  success = "yes";
			  } 
		  } catch (SQLException e) {
			  System.out.println("SQL");
		  }
		  
		   Map<String, Object> responseObject = ImmutableMap.of("results",
		              success);
		  return GSON.toJson(responseObject);
		      		  
	  }
  }
  
  private static class SignupHandler implements Route{
	  @Override
	  public String handle(Request req, Response res) {
		  QueryParamsMap request = req.queryMap();
		  String username = request.value("username");
		  String password = request.value("password");
	     String success = "no";

		  try{
			  if (backend.signUp(username, password) == 1) {
				  System.out.print(backend.getUser());
				  success = "yes";
			  } 
		  } catch (SQLException e) {
			  System.out.println("SQL");
		  }
		  
		   Map<String, Object> responseObject = ImmutableMap.of("results",
		              success);
		  return GSON.toJson(responseObject);
		      		  
	  }
  }

  private static class RecipeHandler implements Route {
    @Override
    public String handle(Request req, Response res) {
      QueryParamsMap request = req.queryMap();

      /*
      List<String> inclusions = request.value("inclusions");
      List<String> exclusions = request.value("exclusions");
      List<String> pantry = request.value("pantry");
      SearchAlgo search = new SearchAlgo(new RecipesDatabase());
      List<Recipe> suggestions = search.search(inclusions, exclusions, pantry);

       */
      List<Map<String, String>> suggestions = new ArrayList<>();
      Map m = new HashMap();
      m.put("Ingredients", "Stuff");
      suggestions.add(m);
      Map<String, Object> responseObject = ImmutableMap.of("results",
              suggestions);
      return GSON.toJson(responseObject);
    }
  }

//  private static class SignupHandler implements Route {
//    @Override
//    public String handle(Request req, Response res) throws SQLException {
//      QueryParamsMap request = req.queryMap();
//      String username = request.value("username");
//      String password = request.value("password");
//      AccountDatabase db = new AccountDatabase("data/account/accounts.sqlite3");
//
//      List<String> response = db.signUp(username, password);
//      Map<String, Object> responseObject = ImmutableMap.of("results",
//              response);
//      return GSON.toJson(responseObject);
//
//    }
//  }

  /**
   * Display an error page when an exception occurs in the server.
   *
   */
  @SuppressWarnings("rawtypes")
  private static class ExceptionPrinter implements ExceptionHandler {
    @Override
    public void handle(Exception e, Request req, Response res) {
      res.status(500);
      StringWriter stacktrace = new StringWriter();
      try (PrintWriter pw = new PrintWriter(stacktrace)) {
        pw.println("<pre>");
        e.printStackTrace(pw);
        pw.println("</pre>");
      }
      res.body(stacktrace.toString());
    }
  }
  private static void enableCORS(final String origin, final String methods, final String headers) {

    Spark.options("/*", (request, response) -> {

      String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
      if (accessControlRequestHeaders != null) {
        response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
      }

      String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
      if (accessControlRequestMethod != null) {
        response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
      }

      return "OK";
    });

    Spark.before((request, response) -> {
      response.header("Access-Control-Allow-Origin", origin);
      response.header("Access-Control-Request-Method", methods);
      response.header("Access-Control-Allow-Headers", headers);
      // Note: this may or may not be necessary in your particular application
      response.type("application/json");
    });
  }

}

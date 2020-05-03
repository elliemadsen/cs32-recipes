package edu.brown.cs.student.main;


import freemarker.template.Configuration;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import edu.brown.cs.student.recipe.IngredientsDatabase;
import edu.brown.cs.student.recipe.RecipesDatabase;
import edu.brown.cs.student.search.SearchAlgo;
import edu.brown.cs.student.suggestions.Autocorrector;
import edu.brown.cs.student.util.CombinedREPL;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import spark.ExceptionHandler;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;



/**
 * The Main class of our project. This is where execution begins.
 */
public final class Main {

  private static final int DEFAULT_PORT = 4567;
  private static PrintWriter pw = new PrintWriter(System.out);
  private static CombinedREPL repl = new CombinedREPL(pw, System.in);


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
    // Parse command line arguments
    OptionParser parser = new OptionParser();
    parser.accepts("gui");
    parser.accepts("port").withRequiredArg().ofType(Integer.class).defaultsTo(DEFAULT_PORT);
    OptionSet options = parser.parse(args);

    if (options.has("gui")) {
      runSparkServer((int) options.valueOf("port"));
    }

    //Autocorrector ac = new Autocorrector(new IngredientsDatabase());
    //SearchAlgo search = new SearchAlgo(new RecipesDatabase());
    
    repl.run();

  }

  private static FreeMarkerEngine createEngine() {
    @SuppressWarnings("deprecation")
    Configuration config = new Configuration();
    File templates = new File("src/main/resources/spark/template/freemarker");
    try {
      config.setDirectoryForTemplateLoading(templates);
    } catch (IOException ioe) {
      System.out.printf("ERROR: Unable use %s for template loading.%n", templates);
      System.exit(1);
    }
    return new FreeMarkerEngine(config);
  }

  @SuppressWarnings("unchecked")
  private void runSparkServer(int port) {
    Spark.port(port);
    Spark.externalStaticFileLocation("src/main/resources/static");
    Spark.exception(Exception.class, new ExceptionPrinter());

    FreeMarkerEngine freeMarker = createEngine();


    // Setup Spark Routes

//    TimdbGui.setRepl(repl);
//    StarsGui.setRepl(repl);
//
//    Spark.get("/stars", new StarsGui.FrontHandlerStars(), freeMarker);
//    Spark.get("/neighbors", new StarsGui.NeighborSubmitHandlerStars(), freeMarker);
//    Spark.get("/radius", new StarsGui.RadiusSubmitHandlerStars(), freeMarker);
//    Spark.get("/timdb", new TimdbGui.FrontHandlerTimdb(), freeMarker);
//    Spark.get("/connect", new TimdbGui.ConnectSubmitHandlerTimdb(), freeMarker);
//    Spark.get("/actor/:id", new TimdbGui.ActorPageHandlerTimdb(), freeMarker);
//    Spark.get("/film/:id", new TimdbGui.FilmPageHandlerTimdb(), freeMarker);

  }

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

}

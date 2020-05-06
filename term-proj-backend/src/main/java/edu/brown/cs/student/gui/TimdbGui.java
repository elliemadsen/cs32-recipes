//package edu.brown.cs.student.gui;
//
//
//import com.google.common.collect.ImmutableMap;
//import edu.brown.cs.student.dijkstra.Edge;
//import edu.brown.cs.student.util.CombinedREPL;
//import java.io.PrintWriter;
//import java.io.StringWriter;
//import java.util.List;
//import java.util.Map;
//import spark.ModelAndView;
//import spark.QueryParamsMap;
//import spark.Request;
//import spark.Response;
//import spark.TemplateViewRoute;
//
///**
// * class models TimdbGui. Instantiated in CombinedGui
// * @author yuna.hiraide
// *
// */
//public class TimdbGui {
//  /**
//   * class models TimdbGui. Instantiated in CombinedGui
//   * @author yuna.hiraide
//   *
//   */
//
//  protected TimdbGui() {
//    // prevents calls from subclass
//    throw new UnsupportedOperationException();
//  }
//  private static CombinedREPL repl;
//
//  /**
//   * Generates page for Actors.
//   * Generates hyperlinks for each actor and list their movies
//   * @author yuna.hiraide
//   *
//   */
//  public static class ActorPageHandlerTimdb implements TemplateViewRoute {
//    @Override
//    public ModelAndView handle(Request req, Response res) {
//
//      String filmList = "";
//      String name = "";
//
//      if (getRepl().getDb() != null) {
//        String id = req.params(":id").replace("+", "/").substring(1);
//        name = getRepl().getTimdbRepl().getActorName(id);
//
//        filmList = "";
//
//        if (!name.equals("Actor not found.")) { //film is found, make
//
//          Map<String, String> actors = getRepl().getTimdbRepl().makeFilmList(id);
//          for (Map.Entry<String, String> entry: actors.entrySet()) {
//            String actorIdJoin = entry.getKey().replace("/", "+");
//            filmList += "<div class = \"film\"> <a href=/film/:"
//                + actorIdJoin + ">" + entry.getValue() + "</a> </div>";
//          }
//
//        }
//      } else {
//        name = "ERROR: Database is not set";
//      }
//
//      Map<String, Object> variables = ImmutableMap.of("title", "TIMDB: Query the Database",
//          "name", name, "results", filmList);
//      return new ModelAndView(variables, "actor.ftl");
//    }
//  }
//  /**
//   * handle FilmPages. Creates a page of actors list with hyperlinks.
//   * @author yuna.hiraide
//   *
//   */
//
//  public static class FilmPageHandlerTimdb implements TemplateViewRoute {
//    @Override
//    public ModelAndView handle(Request req, Response res) {
//
//      String actorList = "";
//      String name = "";
//      if (getRepl().getDb() != null) {
//        String id = req.params(":id").replace("+", "/").substring(1);
//        name = getRepl().getTimdbRepl().getFilmName(id);
//
//        if (!name.equals("Film not found.")) { //film is found, make
//
//          Map<String, String> actors = getRepl().getTimdbRepl().makeActorList(id);
//          for (Map.Entry<String, String> entry: actors.entrySet()) {
//            String actorIdJoin = entry.getKey().replace("/", "+");
//            actorList += "<div class = \"actor\"> <a href=/actor/:"
//                + actorIdJoin + ">" + entry.getValue() + "</a> </div>";
//          }
//
//        }
//
//      } else {
//        name = "ERROR: Database is not set";
//      }
//
//
//      Map<String, Object> variables = ImmutableMap.of("title", "TIMDB: Query the Database",
//          "name", name, "results", actorList);
//      return new ModelAndView(variables, "film.ftl");
//    }
//  }
//
//  /**
//   * Handle requests to the front page of our Timdb website.
//   *
//   */
//
//  public static class FrontHandlerTimdb implements TemplateViewRoute {
//    @Override
//    public ModelAndView handle(Request req, Response res) {
//      Map<String, Object> variables = ImmutableMap.of("title", "TIMDB: Query the Database",
//          "results", "");
//      return new ModelAndView(variables, "timdbQuery.ftl");
//    }
//  }
//
//  /**
//   * Handle Connect submit for Timdb.
//   * @author yuna.hiraide
//   *
//   */
//  public static class ConnectSubmitHandlerTimdb implements TemplateViewRoute {
//
//    @Override
//    public ModelAndView handle(Request request, Response response) throws Exception {
//
//      StringWriter out = new StringWriter();
//      PrintWriter pwGui = new PrintWriter(out);
//
//      QueryParamsMap qm = request.queryMap();
//
//      String startId = qm.value("start-actor-query");
//      String endId = qm.value("end-actor-query");
//      String[] arguments = {"connect", startId, endId};
//
//      int result = getRepl().getTimdbRepl().findConnection(arguments, pwGui);
//      String resultString = "";
//
//      if (result == 0) {
//
//        List<Edge> shortestPath = getRepl().getTimdbRepl().getPath();
//
//        for (Edge e: shortestPath) { //iterates through edges printing names
//
//          String actorFrom = getRepl().getTimdbRepl().getActorName(e.from().getId());
//          String actorFromJoin = e.from().getId().replace("/", "+"); //replaces space with _ for url
//          String actorFromGui = "<a href=actor/:" + actorFromJoin + ">" + actorFrom + "</a>"; //html
//
//          String actorTo = getRepl().getTimdbRepl().getActorName(e.to().getId());
//          String actorToJoin = e.to().getId().replace("/", "+");
//          String actorToGui = "<a href=actor/:" + actorToJoin + ">" + actorTo + "</a>";
//
//          String filmName = getRepl().getTimdbRepl().getFilmName(e.getId());
//          String filmNameJoin = e.getId().replace("/", "+");
//          String filmNameGui = "<a href=film/:" + filmNameJoin + ">" + filmName + "</a></div>";
//
//          resultString += "<div class = \"connection\">" + actorFromGui + " -> "
//              + actorToGui + " : " + filmNameGui + "</div>";
//        }
//      } else {
//        pwGui.flush();
//        resultString += out.toString();
//      }
//
//      Map<String, String> variables = ImmutableMap.of("title", "TIMDB: Query the Database",
//          "results", resultString);
//      return new ModelAndView(variables, "timdbQuery.ftl");
//    }
//  }
//
//  /**
//   * Getter for repl.
//   * @return repl
//   */
//  public static CombinedREPL getRepl() {
//    return repl;
//  }
//
//  /**
//   * Setter for repl.
//   * @param repl to be set
//   */
//  public static void setRepl(CombinedREPL repl) {
//    TimdbGui.repl = repl;
//  }
//
//}

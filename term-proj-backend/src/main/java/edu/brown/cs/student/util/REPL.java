package edu.brown.cs.student.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * Generic REPL that can be extended for use in other projects. Sets up string
 * parser and command map.
 *
 * @author kallifeinberg
 *
 */
public class REPL {

  /*
   * map of all strings to the commands they represent, protected so child classes
   * can access
   */
  private Map<String, Command> commands = new HashMap<String, Command>();
  private BufferedReader reader;
  private PrintWriter pw;

  /**
   * Constructor for REPL that will read user input.
   *
   * @param pw where the REPL will print output to.
   * @param s  where the reader will read input from.
   */
  public REPL(PrintWriter pw, InputStream s) {
    this.pw = pw;
    this.reader = new BufferedReader(new InputStreamReader(s));
  }


  /**
   * Interface for command that all commands inherit, has only one method that
   * executes the command.
   */
  public interface Command {
    /**
     *
     * @param arguments parsed arguments
     * @param pw where REPL will print output to
     */
    void execute(String[] arguments, PrintWriter pw);
  }

  /**
   * Allows for implemented classes to put their own commands into map.
   *
   * @param s    the string that represents the command
   * @param comm the command that the string should execute
   */
  protected void addCommand(String s, Command comm) {
    commands.put(s, comm);
  }

  protected void addCommands(Map<String, Command> m) {
    commands.putAll(m);
  }

  protected Map<String, Command> getCommands() {
    return commands;
  }

  /**
   * Iterates command line input, taking input from user, executing if it is
   * valid, and exiting at EOF.
   *
   * @throws IOException if reader fails to read line form user
   */
  public void run() throws IOException {

    String input;
    while ((input = reader.readLine()) != null) { // REPL while loop

      String[] totalSplit = parseInput(input); // separates inputs into tokens

      if (totalSplit.length > 0 && commands.keySet().contains(totalSplit[0])) { // if user has
        // entered valid
        // command
        Command comm = commands.get(totalSplit[0]);
        comm.execute(totalSplit, pw); // executes command
        pw.flush();
      } else { // bad command from user
        pw.println("ERROR: Invalid command.");
        pw.flush();
      }
    }
  }

  /**
   * Takes the input that the user enters into the REPL, and separates it into
   * tokens to be read as commands.
   *
   * @param input that the user entered into the REPL
   * @return the input separated by spaces or quotation marks and organized into
   *         an array
   */
  public String[] parseInput(String input) {

    List<String> separated = new LinkedList<String>();

    Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(input);

    while (m.find()) {
      separated.add(m.group(1).replace("\"", ""));
    }

    String[] arr = new String[separated.size()];

    int i = 0;
    for (String s : separated) {
      arr[i] = s;
      i++;
    }

    return arr;
  }

}

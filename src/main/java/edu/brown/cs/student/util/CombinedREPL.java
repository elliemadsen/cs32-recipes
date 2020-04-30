package edu.brown.cs.student.util;

import edu.brown.cs.student.Account.AccountDatabase;
import edu.brown.cs.student.Account.AccountREPL;

import java.io.InputStream;
import java.io.PrintWriter;



/**
 * class models combined REPL for stars and timdb.
 *
 *
 * @author yuna.hiraide
 *
 */

public class CombinedREPL extends REPL {


  private AccountREPL accountRepl;
  
  /**
   * Constructor for Combined Repl.
   * Instantiates new starsrepl and timdbrepl and add commands
   * @param pw where to print
   * @param s where to read from
   */
  public CombinedREPL(PrintWriter pw, InputStream s) {

    super(pw, s);
    setAccountRepl(new AccountREPL(pw,s));
    //adds commands from the two REPLs to the combined REPL
    addCommands(getAccountRepl().getCommands());

  }

  /**
   * Getter for TimdbREPL.
   * @return timdbRepl
   */
  public AccountREPL getAccountRepl() {
    return accountRepl;
  }

  /**
   * setter for timdbRepl.
   * @param timdbRepl to be set
   */
  public void setAccountRepl(AccountREPL accountRepl) {
    this.accountRepl = accountRepl;
  }

  /**
   * Getter for Database.
   * @return database
   */
  public AccountDatabase getAccountDb() {
    return accountRepl.getDb();
  }



}

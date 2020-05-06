//package edu.brown.cs.student.Account;
//
//
//import edu.brown.cs.student.util.REPL;
//import java.io.InputStream;
//import java.io.PrintWriter;
//import java.sql.SQLException;
//import java.util.List;
//
//public class AccountREPL extends REPL {
//
//
//  private AccountDatabase db = null; //starts as null before initializing
//  private String user = null;
//
//  public AccountREPL(PrintWriter pw, InputStream s) {
//	  
//    super(pw, s);
//    addCommand("acc", new AccCommand()); 
//    addCommand("login", new LoginCommand());
//    addCommand("signup", new Signup());
//    addCommand("add", new AddCommand());
//    addCommand("like", new LikeCommand());
//    addCommand("restrict", new RestrictCommand());
//    addCommand("show", new ShowCommand());
//    db = new AccountDatabase("data/account/accounts.sqlite3", pw);
//
//
//
//
//
//  }
//
//  public class AccCommand implements Command {
//
//    public void execute(String[] arguments, PrintWriter pw) {
//      if (arguments.length == 2) {
//        arguments[1] = "data/account/accounts.sqlite3";
//        db = new AccountDatabase(arguments[1], pw);
//        if (db != null) {
//          pw.println("db set to " + arguments[1]);
//        } else {
//          pw.println("ERROR: Invalid database.");
//          db = null; //reverts db null if sqlite file is invalid
//        }
//      } else {
//        pw.println("ERROR: Invalid Argument");
//      }
//    }
//  }
//
//  public class LoginCommand implements Command{
//    public void execute(String[] arguments, PrintWriter pw) {
//      if (db != null) {
//        if (arguments.length == 3) {
//          try {
//            List<String> ids = db.login(arguments[1],arguments[2]);
//            if (ids.size() == 1) {
//              user = ids.get(0);
//              pw.println ("Welcome back " + arguments[1] + " !" );
//            } else {
//              pw.println ("Username or Password incorrect");
//            }
//          } catch (SQLException e) {
//            pw.println ("Attempt to Login Failed");
//            e.printStackTrace();
//
//          }
//        } else {
//          pw.println ("ERROR: Invalid Argument");
//        }
//      } else {
//        pw.println ("Datbase not connected");
//      }
//    }
//  }
//
//  public class Signup implements Command{
//    public void execute (String[] arguments, PrintWriter pw) {
//      if (db != null) {
//        List<String> ids;
//        List<String> ids2 = null;
//        if (arguments.length == 3) {
//          try {
//            ids = db.getUsername(arguments[1],arguments[2]);
//            if (ids.size() == 0) {
//              ids2 = db.newAccount(arguments[1],arguments[2]);
//              if (ids2.size() == 1) {
//                userId = ids2.get(0);
//                pw.println ("Welcome " + arguments[1] + " !" ); 
//              } else {
//                pw.println("Attempt to create new account failed");
//              }
//            } else {
//              pw.println ("Username is taken");
//            }
//          } catch (SQLException e) {
//            pw.println ("Attempt to create new account Failed");
//            e.printStackTrace();
//          }
//        } else {
//          pw.println ("ERROR: Invalid Argument");
//        }
//      } else {
//        pw.println ("Datbase not connected");
//      }
//    }
//  }
//
//  public class ShowCommand implements Command{
//    public void execute (String[] arguments, PrintWriter pw) {
//      if (db != null) {
//        if (userId != null) {
//          if (arguments.length == 1) {
//
//            String userName;
//            try {
//              userName = db.userId(userId).get(0);
//              List<String> ingredients = db.ingredients(userId);
//              List<String> recipes = db.recipes(userId);
//              List<String> restrictions = db.restriction(userId);
//              List<String> quantity = db.getQuantity();
//
//              pw.println("Items in " + userName + "'s Pantry:" );
//              for (int i = 0; i < ingredients.size(); i++) {
//                pw.println(ingredients.get(i) + "  -  " + quantity.get(i) + "units");
//              }      
//              pw.println("\nItems in " + userName + "'s Liked Recipes:" );
//              for (int i = 0; i < recipes.size(); i++) {
//                pw.println(recipes.get(i));
//              }
//              pw.println("\nItems in " + userName + "'s Restriction List:" );
//              for (int i = 0; i < restrictions.size(); i++) {
//                pw.println(restrictions.get(i));
//              }
//            } catch (SQLException e) {
//              // TODO Auto-generated catch block
//              pw.println("Attempt to show information failed");
//
//              e.printStackTrace();
//            }
//
//
//          }else {
//            pw.println("Invalid input");
//          }
//
//        }else {
//          pw.println("Pleaes log in");
//        }
//
//      } else {
//        pw.println("Database not connected");
//      }
//    }
//  }
//
//  public class LikeCommand implements Command{
//    public void execute (String[] arguments, PrintWriter pw) {
//      if (db != null) {
//        if (userId != null) {
//          if (arguments.length == 2) {
//            try {
//              List<String> recipeIds = db.getRecipeId(arguments[1]);
//              if (recipeIds.size() == 1) {
//                if (db.alreadyLiked(recipeIds.get(0), userId)) {
//                  pw.println(arguments[1] + " is already in your Liked Recipe");
//                } else {
//                  if (db.addLiked(recipeIds.get(0), userId)) {
//                    pw.println ("Added " +arguments[1] + " to your Liked Recipe");
//                  } else {
//                    pw.println("Attempt to add "+arguments[1] + "to your Liked Recipe failed");
//                  }
//                }
//              } else {
//                pw.println (arguments[1]+ " is not in system recipe database. Please check spelling. Use - instead of spaces");
//              }
//            } catch (SQLException e) {
//              // TODO Auto-generated catch block
//              e.printStackTrace();
//            }
//
//          } else {
//            pw.println("ERROR: Invalid Argument");
//          }
//
//        } else {
//          pw.println("Please log in");
//        }
//
//      } else {
//        pw.println("Database not connected");
//      }
//    }
//  }
//
//  public class AddCommand implements Command{
//    public void execute (String[] arguments, PrintWriter pw) {
//      if (db != null) {
//        if (userId != null) {
//          if (arguments.length % 2 == 1) {
//            for (int i = 1; i < arguments.length; i+=2) {
//              try {
//                List<String> itemIds = db.itemId(arguments[i]);
//                if (itemIds.size() == 1) {
//                  List<String> quantity = db.alreadyIn(itemIds.get(0),userId);
//                  if (quantity.size() == 0) {
//                    List<String> check = db.addPantry(itemIds.get(0), arguments[i+1], userId);
//                    if (check.size() == 1) {
//                      pw.println("Added " + arguments[i+1] + " units of " + arguments[i] + " to your pantry");
//                    } else {
//                      pw.println("Attempt to add " + arguments[i+1] + " units of " + arguments[i] + " to your pantry failed");
//                    }
//                  } else {
//                    try{
//                      double add_quantity = Double.parseDouble(arguments[i+1]);
//                      double curr_quantity = Double.parseDouble(quantity.get(0));
//                      double new_quantity = add_quantity + curr_quantity;
//                      List<String> newQuantity = db.updateQuantity(itemIds.get(0), Double.toString(new_quantity), userId);
//                      if (newQuantity.size() == 1) {
//                        pw.println("Updated quantity of " + arguments[i] + " to " + new_quantity + "in your pantry");
//                      } else {
//                        pw.println("Attempt to update the quantity of " + arguments[i] + " in pantry failed");
//                      }
//                    } catch (NumberFormatException e) {
//                      pw.println("Attempt to add " + arguments[i] + " failed. Please input valid quantity");
//                    }
//
//                  }
//                } else {
//                  pw.println(arguments[i]+ " is not in system ingredients database. Please check spelling. Use - instead of spaces");
//                }
//              } catch (SQLException e) {
//                e.printStackTrace();
//                pw.println("Attempt to add " + arguments[i+1] + " units of " + arguments[i] + "to your pantry failed");
//              }
//            }
//          } else {
//            pw.println ("Invalid input. Please type [add ingredients quantity ingredients quantity ...]");
//          }
//        } else {
//          pw.println("Please log in");
//        }
//      } else {
//        pw.println ("Datbase not connected");
//      }
//    }
//  }
//
//  public class RestrictCommand implements Command{
//    public void execute (String[] arguments, PrintWriter pw) {
//      if (db != null) {
//        if (userId != null) {
//          if (arguments.length == 2) {
//            try {
//              List<String> itemId = db.itemId(arguments[1]);
//              if (itemId.size() == 1) {
//                if (db.alreadyRestricted(itemId.get(0), userId)) {
//                  pw.println(arguments[1] + " is already in your Restriction List");
//                } else {
//                  if (db.addRestriction(itemId.get(0), userId)) {
//                    pw.println ("Added " +arguments[1] + " to your Restriction List");
//                  } else {
//                    pw.println("Attempt to add "+arguments[1] + "to your Restriction List failed");
//                  }
//                }
//              } else {
//                pw.println (arguments[1]+ " is not in system ingredients database. Please check spelling. Use - instead of spaces");
//              }
//            } catch (SQLException e) {
//              // TODO Auto-generated catch block
//              e.printStackTrace();
//            }
//
//          } else {
//            pw.println("ERROR: Invalid Argument");
//          }
//
//        } else {
//          pw.println("Please log in");
//        }
//
//      } else {
//        pw.println("Database not connected");
//      }
//    }
//  }
//
//
//
//
//  public AccountDatabase getDb() {
//    return db;
//  }
//
//
//
//}

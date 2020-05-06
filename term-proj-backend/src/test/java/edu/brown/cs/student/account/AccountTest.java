package edu.brown.cs.student.account;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.brown.cs.student.Account.AccountDatabase;
import edu.brown.cs.student.recipe.Recipe;

public class AccountTest {
	
//	IngredientComparator comparator;
	AccountDatabase db;
	
	@Before
	public void SetUp() {
//		comparator = new IngredientComparator(Arrays.asList("flour", "egg", "milk"));
	}
	
	@After
	public void TakeDown() {
//		comparator = null;
	}

	@Test
	public void test() throws SQLException {
		
	System.out.print("HELLO HELLO HELLO");
	   db = new AccountDatabase("data/account/accounts.sqlite3"); //starts as null before initializing
	   String user = null;
	   System.out.print(db.login("yuna", "password").get(0));
	   System.out.print(db.getList("yuna", "Pantry"));
	   System.out.print(db.getList("yuna", "Restriction"));
	   System.out.print(db.signUp("yuna", "password2"));
	   System.out.print(db.signUp("ellie", "password"));
	   System.out.print(db.login("ellie", "password"));
	   System.out.println(db.add("yuna", "milk", "Pantry"));
	   System.out.println(db.add("yuna", "peanuts", "Restriction"));
	   System.out.print(db.getList("yuna", "Pantry"));
	   System.out.print(db.getList("yuna", "Restriction"));
	   System.out.println(db.remove("yuna", "milk", "Pantry"));
	   System.out.println(db.remove("yuna", "peanuts", "Restriction"));
	   System.out.print(db.getList("yuna", "Pantry"));
	   System.out.print(db.getList("yuna", "Restriction"));
	   System.out.print(db.login("yuna", "fakePass"));
	   System.out.println(db.remove("yuna", "milk", "Pantry"));
	   System.out.print(db.getList("yuna", "Pantry"));
	   System.out.println(db.add("yuna", "tomatoes", "Pantry"));
	   System.out.print(db.getList("yuna", "Pantry"));
	   System.out.println(db.remove("yuna", "tomatoes", "Pantry"));

	   
	}



}

//package edu.brown.cs.student.stars;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.List;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import edu.brown.cs.student.util.CSVReader;
//
//public class CSVReaderTest {
//  
//  private CSVReader csv;
//  
//  @Before
//  public void setUp() throws FileNotFoundException {
//    csv = new CSVReader("data/stars/one-star.csv");
//  }
//
//  @After
//  public void tearDown() {
//    csv = null;
//  }
//  
//  @Test 
//  public void readLine () throws IOException {
//    String [] line = csv.readLine();
//    assert(line[0].equals("StarID"));
//    assert(line[1].equals("ProperName"));
//    assert(line[2].equals("X"));
//    assert(line[3].equals("Y"));
//    assert(line[4].equals("Z"));
//  }
//  
//  @Test 
//  public void readAll () throws IOException {
//    
//    csv = new CSVReader("data/stars/one-star.csv");
//    
//    List<String []> lines = csv.readAll();
//    String [] line = lines.get(0);
//    assert(line[0].equals("StarID"));
//    assert(line[1].equals("ProperName"));
//    assert(line[2].equals("X"));
//    assert(line[3].equals("Y"));
//    assert(line[4].equals("Z"));
//    
//    line = lines.get(1);
//    assert(line[0].equals("1"));
//    assert(line[1].equals("Lonely Star"));
//    assert(line[2].equals("5"));
//    assert(line[3].equals("-2.24"));
//    assert(line[4].equals("10.04"));
//  }
//
//
//}

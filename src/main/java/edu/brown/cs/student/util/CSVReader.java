package edu.brown.cs.student.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Class implements a generic CsvReader for parsing .csv files and converting
 * them to Strings.
 *
 * @author kallifeinberg
 */
public class CSVReader {

  private File file; // .csv file being read
  private BufferedReader fileReader; // reader used to read file

  /**
   * Instantiates CsvReader for file with provided path.
   *
   * @param path of .csv file to be read
   * @throws FileNotFoundException if file is not at path
   */
  public CSVReader(String path) throws FileNotFoundException {
    this.file = new File(path); // makes new file
    this.fileReader = new BufferedReader(new FileReader(file)); // reader for file
  }

  /**
   * Reads the next line of the current .csv file, called by StarREPL to read
   * header.
   *
   * @return Array of strings for each comma delineated part of line.
   * @throws IOException if file is not found
   */
  public String[] readLine() throws IOException {

    String st = fileReader.readLine();
    String[] split = null;

    if (st != null) {
      split = st.split(","); // separates line by commas
    }

    return split;

  }

  /**
   * Reads in all remaining lines of the .csv file, called by StarREPL to read
   * stars.
   *
   * @return a list of arrays each of the lines, with each element in the array
   *         representing a comma-separation
   * @throws IOException if file is not found
   */
  public List<String[]> readAll() throws IOException {

    List<String[]> allLines = new LinkedList<String[]>(); // list of all nodes

    String st = "";

    while ((st = fileReader.readLine()) != null) { // iterates through each row parsing values
      String[] split = st.split(","); // splits row into different values
      allLines.add(split);
    }

    fileReader.close();
    return allLines;

  }

  /**
   * Once file is done being used, closes the file reader.
   *
   * @throws IOException if file is not readable
   */
  public void close() throws IOException {
    fileReader.close();
  }

}

//package edu.brown.cs.student.stars;
//
//import static org.junit.Assert.assertTrue;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import edu.brown.cs.student.kdtree.KDTree;
//import edu.brown.cs.student.kdtree.Node;
//
//public class KDTreeTest {
//
//  private double minVal = -1000;
//  private double maxVal = 1000;
//  private int nodesGenerated = 100;
//  private KDTree<Star> kd;
//  private List<Node<Star>> nodes; // nodes array to more easily move through tree
//
//  /**
//   * Creates KD Tree, randomly generates #nodesGenerated nodes with integer
//   * coordinates between minVal and maxVal.
//   */
//  @Before
//  public void setUpRandom() {
//    kd = new KDTree<Star>();
//    nodes = new ArrayList<Node<Star>>();
//    for (int i = 0; i < nodesGenerated; i++) {
//      double[] coordinates = new double[3];
//      for (int j = 0; j < 3; j++) { // fills in coordinates
//        // random integer between minVal and maxVal
//        coordinates[j] = (maxVal - minVal) * Math.random() + minVal;
//      }
//      Star s = new Star(coordinates, " ", i);
//      nodes.add(new Node<Star>(s.getCoordinates(), s));
//    }
//  }
//
//  @After
//  public void tearDown() {
//    kd = null;
//  }
//
//  /**
//   * Tests that each of the parents has the correct relationship with its children
//   * based on their coordinates.
//   */
//  @Test
//  public void validParentChild() {
//    // checks 100 trees to see if the parent child relationship holds
//    for (int i = 0; i < 100; i++) {
//      setUpRandom();
//      kd.insertNodes(nodes);
//      assertTrue(validParentChildHelper(kd.getRoot(), 0));
//      tearDown();
//    }
//  }
//
//  /**
//   * Recurses through the tree returning false if the child has the wrong values
//   * relative to the parent. Node curr: current node being checked int depth:
//   * depth currently at
//   */
//  public boolean validParentChildHelper(Node<Star> curr, int depth) {
//    if (curr == null) {
//      return true;
//    }
//    Node<Star> left = curr.getLeft();
//    Node<Star> right = curr.getRight();
//    double currVal = curr.getCoordinates()[depth % kd.getDimensions()];
//    if (left != null) {
//      double leftVal = left.getCoordinates()[depth % kd.getDimensions()];
//      if (leftVal > currVal) {
//        return false; // left should be smaller
//      }
//    }
//    if (right != null) {
//      double rightVal = right.getCoordinates()[depth % kd.getDimensions()];
//      if (rightVal < currVal) {
//        return false; // right should be bigger
//      }
//    }
//    // if false was ever thrown, this overrides
//    return validParentChildHelper(right, depth + 1) && validParentChildHelper(left, depth + 1);
//  }
//
//  /**
//   * Tests that each of the nodes has been inserted into the KD Tree.
//   */
//  @Test
//  public void allNodesInserted() {
//    for (int i = 0; i < 100; i++) {
//      setUpRandom();
//      kd.insertNodes(nodes);
//      assertTrue(countNodes(kd.getRoot()) == nodesGenerated);
//      tearDown();
//    }
//  }
//
//  /**
//   * Counts the number of nodes in the KD tree. Node curr: current node currently
//   * being recursed through
//   */
//  public int countNodes(Node<Star> curr) {
//    if (curr == null) {
//      return 0;
//    } else {
//      return countNodes(curr.getLeft()) + countNodes(curr.getRight()) + 1;
//    }
//  }
//
//}

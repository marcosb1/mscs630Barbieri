/**
 * file: Driver2.java
 * author: Marcos Barbieri
 * course: MSCS 630L
 * assignment: lab 2
 * due date: 
 * version: 1.0
 *
 *
 * TODO: Description here
 */

import java.util.Scanner;

/**
 */
class Driver {

  /**
   */
  public static long[] euclidAlgExt(long a, long b) {
    long q = Math.floorDive(a, b);
    long r = a - q * b;
    long x = 1;
    long y = -1;

    long d = (a * x) + (b * y);
    while (d != r) {
            
    } 
    return null;
  }

  /**
   * main
   * 
   * The main method will take in text strings containing numbers
   * for which we will take each line, split by space and compute 
   * the Euclidian GCD 
   *
   * parameters:
   *  args: array of String containing suplemental cmd arguments
   *    (not used in this program)
   *
   * return: 
   */
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    while (input.hasNext()) {
      String plainText = input.nextLine();
      String[] splitted = plainText.split("\\s+");
      long gcd = euclidAlgExt(Long.parseLong(splitted[0]), Long.parseLong(splitted[1]));
      System.out.println(gcd); 
    }
  }
}

/**
 * file: Driver.java
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
class Driver1 {

  /**
   * euclidAlg
   * 
   * This function recursively finds the Euclidian GCD based on
   * the Euclidian GCD theorem/algorithm
   * This works through the following:
   *        a     = q_1   * b     + r_1
   *        b     = q_2   * r_1   + r_2
   *        r_1   = q_3   * r_2   + r_3
   *              ---
   *        r_n-2 = q_n   * r_n-1 + r_n
   *        r_n-1 = q_n+1 * r_n   + 0
   *  Where r_n is our GCD
   * 
   * parameters:
   *  a: long containing the first number (should be greater than b)
   *  b: long containing the second number
   *
   * return: 
   *  long which is the greatest common denominator
   */
  public static long euclidAlg(long a, long b) {
    // check if a >= b
    if (a < b) {
      long tempA = a;
      a = b;
      b = tempA;
    }

    // this is how we check if we are done with the recursion
    long q = Math.floorDiv(a, b);
    long r = a - q * b;

    // when the remainder is 0 simply return b, according to Euclidean GCD
    if (r == 0) {
      return b;
    }
    
    return euclidAlg(b, r);
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

      if ((Long.parseLong(splitted[0]) == 0) && 
            (Long.parseLong(splitted[1]) == 0)) {
        System.out.println("`0,0` is not a valid input");
        break;
      }      

      long gcd = euclidAlg(Long.parseLong(splitted[0]), Long.parseLong(splitted[1]));
      System.out.println(gcd); 
    }
  }
}

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
class Driver2 {

  /**
   */
  public static String euclidAlgExt(long a, long b) {
    long a_1 = 1;
    long a_2 = 0;
    long b_1 = 0;
    long b_2 = 1;

    String out = "";
    while (b > 0) {
      System.out.println(a + " | " + a_1 + " | " + a_2);
      System.out.println(b + " | " + b_1 + " | " + b_2);

      long q = a / b;
      long r = a - q * b; 

      a = b;
      b = r;
      
      long temp_a_1 = a_1;
      long temp_a_2 = a_2;
      a_1 = b_1;
      a_2 = b_2;
      b_1 = temp_a_1 - q * b_1;
      b_2 = temp_a_2 - q * b_2;
      
      out = a + " " + a_1 + " " + a_2;
      System.out.println(out);
      System.out.println("----------------------------");
    }   

    return out;
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
      String gcd = euclidAlgExt(Long.parseLong(splitted[0]), Long.parseLong(splitted[1]));
      System.out.println(gcd); 
    }
  }
}

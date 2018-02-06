/**
 * file: Driver2.java
 * author: Marcos Barbieri
 * course: MSCS 630L
 * assignment: lab 2
 * due date: 02/07/2018
 * version: 1.0
 *
 *
 * Driver 2 contains the code for the Extended Euclidean
 * Algorithm which uses the GCD, and the input to find
 * integers x, y where gcd(a,b) = a*x + b*y
 */

import java.util.Scanner;

/**
 */
class Driver2 {

  /**
   * euclidAlgExt
   * 
   * This function uses the d = a * x + b * y equation,
   * where d = gcd(a, b); Interestingly enough, if we 
   * have a, b as input, then suppose we have a' and a''
   * equal to 1 and 0 respectively; In addition suppose,
   * we have b' and b'' equal to 0 and 1, respectively; 
   * Thus, a, b, a' = 1, a'' = 0, b' = 0, and b'' = 1 is 
   * our base case;
   * Now we let a = b, a' = b' and a'' = b'' and we let
   * b' = c - floor(a / b) * b' and we also let 
   * b'' = c - floor(a / b) * b'', while letting b = c % b, 
   * where c is our previous a;
   * We repeat this until b is 0, and a is our GCD, a' is our
   * x and a'' is our y
   * 
   * parameters:
   *  a: some long for which we want to calculate the GCD of
   *  b: some other long where a greater than or equal to b, for
   *     which we want to calculate the GCD of
   *
   * return: 
   *  out: some string containing the gcd(a, b), x, and y where
   *       gcd(a, b) = a * x + b * y
   */
  public static String euclidAlgExt(long a, long b) {
    if (b > a) {
      long tempA = a;
      a = b;
      b = tempA;    
    }
  
    long a1 = 1;
    long a2 = 0;
    long b1 = 0;
    long b2 = 1;

    String out = "";
    while (b > 0) {
      long q = a / b;
      long r = a - q * b; 

      a = b;
      b = r;
      
      long tempA1 = a1;
      long tempA2 = a2;
      a1 = b1;
      a2 = b2;
      b1 = tempA1 - q * b1;
      b2 = tempA2 - q * b2;
      
      out = a + " " + a1 + " " + a2;
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
    while (input.hasNextLine()) {
      String plainText = input.nextLine();
      String[] splitted = plainText.split("\\s+");
      if ((Long.parseLong(splitted[0]) == 0) && 
            (Long.parseLong(splitted[1]) == 0)) {
        System.out.println("`0,0` is not a valid input");
        break;
      }   
      String extGcd = euclidAlgExt(Long.parseLong(splitted[0]), Long.parseLong(splitted[1]));
      System.out.println(extGcd); 
    }
  }
}

/**
 * file: Driver.java
 * author: Marcos Barbieri
 * course: MSCS 630L
 * assignment: lab 1
 * due date: 
 * version: 1.0
 * 
 * This file contains the main method for lab 1 that will
 * take in plain text and encode it based on the requirements
 * stated in lab 1
 */

import java.util.Scanner;

/**
 * Driver
 * 
 * This class implements str2int which takes in plain text and
 * returns an encoded array of characters
 */
class Driver {
  
  /**
   * str2int
   *
   * This function encodes text by converting a-z and A-Z into 
   * the number 0-25 based on order of the character in the 
   * alphabet; whitespace is encoded as 26
   *
   * parameters: 
   *  plainText: String that will be encoded
   *
   * return: encoded array of integers ranging from 0-26 (including 26)
   */ 
  public static int[] str2int(String plainText) {
    int[] output = new int[plainText.length()];

    plainText = plainText.toUpperCase();
    for (int i=0; i < plainText.length(); i++) {
      char c = plainText.charAt(i);

      // we want to encode space as '26' 
      if (((int) c) == 32)
        output[i] = 26;
      else
        output[i] = ((int) c) - 65;
    }

    return output;
  }  

  /**
   * main
   * 
   * The main method will take in text and pass it to the
   * str2int to be encoded, printing out the result values
   * 
   * parameters: 
   *  args: array of String containing supplemental cmd arguments 
   *    (not used in this program)
   * 
   * return:
   */
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    // we will read by line so that the encoded message
    // has the same new line formatting when we print it out
    while (input.hasNext()) {
      String plainText = input.nextLine();
      int[] o = str2int(plainText);
      for (int i=0; i < o.length; i++) {
        System.out.print(o[i] + " ");
      }
      System.out.println();
    }
  }
}

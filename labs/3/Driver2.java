/**
 * file: Driver2
 * author: Marcos Barbieri
 * course: MSCS 630L
 * assignment: lab 3 
 * due date: 03/02/2018
 * version: 0.1
 *
 * Part 2 of Lab 3  
 */
import java.util.Scanner;
import java.util.Arrays;

class Driver2 {
  
  /** print2dArray
   * Helper function used to format array for output
   * Will replace any characters that Java uses to denote arrays and array elements
   *
   * parameters:
   *  A: Matrix we want to print
   */
  public static void print2dArray(String[][] A) {
    for (int i = 0; i < A.length; i++) {
      System.out.println(Arrays.toString(A[i]).replaceAll("([,\\[\\]])", ""));
    }
  }

  /** getHexMatP
   * We use this function to take in a string and a character for which 
   * we will encode using the ASCII code converted to hex, when there is 
   * no string we simply fill in 16 elements of the character provided encoded
   * in the same way as the string
   *
   * parameters:
   *  s: character that will fill empty spaces
   *  p: String containing the plaintext message
   *
   * return:
   *  String matrix with encoded message
   */
  public static String[][] getHexMatP(char s, String p) {
    // string should not exceed 16 characters
    if (p.length() > 16)
      return null;

    String[][] hexMatrix = new String[4][4];
    for (int col = 0; col < 4; col++) {
      for (int row = 0; row < 4; row++) {
        if (p.length() > 0) {
          int charCode = Character.codePointAt(p.substring(0,1), 0);
          hexMatrix[row][col] = Integer.toHexString(charCode).toUpperCase();
          p = p.substring(1);
        } else {
          int charCode = Character.codePointAt("" + s, 0);
          hexMatrix[row][col] = Integer.toHexString(charCode).toUpperCase(); 
        }
      }
    }
    return hexMatrix;
  }
 
  /** main
   * We will be ingesting input here by line
   */
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    
    if (input.hasNext()) {
      String subChar = input.nextLine();
      String plainText = input.nextLine();
      
      if ((plainText.length() % 16) != 0) {
        int offset = (plainText.length() / 16) + 1;
        for (int i = 0; i < offset; i++) {
          plainText = plainText + " ";
        }
      }
  
      String copyText = plainText;
      // we want to encode in 16 character matrices
      for (int c = 0; c < ((plainText.length() / 16) + 1); c++) {
        // thus we create a lower bound and an upperbound and 
        // increase them by our matrix shape (4x4 = 16)
        int lowerBound = c * 16;
        int upperBound = (c * 16) + 16;
        copyText = plainText.length() < upperBound ? plainText.substring(lowerBound) :
                      plainText.substring(lowerBound, upperBound);
        if (copyText.length() > 0) {
          String[][] hexMat = getHexMatP(subChar.charAt(0), copyText); 
          print2dArray(hexMat);
        } else {
          String[][] hexMat = getHexMatP(subChar.charAt(0), ""); 
          print2dArray(hexMat); 
        }
        
        // lets make sure to print out an empty space in between
        // each matrix, but only in between the matrices, so on 
        // the last iteration don't print an empty space out
        if (c < ((plainText.length() / 16))) {
          System.out.println();
        }
      }
    }    
  }
}

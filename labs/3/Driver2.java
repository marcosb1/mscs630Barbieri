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
   * We take in a string and fill in a matrix one by one, column-wise; 
   * However, instead of filling in the matrix completely column-wise, 
   * we first fill in up to 16 characters, which makes up the first matrix,
   * then we leave a space, and we fill in the next matrix consisting of 
   * 16 characters (4x4 shape)
   * 
   * parameters:
   *  s: character that will fill empty spaces
   *  p: String containing the plaintext message
   *
   * return:
   *  String matrix with encoded message
   */
  public static String[][] getHexMatP(char s, String p) {
    int numMatrices = p.length() % 16 == 0 ? (p.length() / 16) : (p.length() / 16) + 1;    
    int maxSlots = ((numMatrices - 1) * 4) + (numMatrices * 16);
    
    String[][] hexMatrix = new String[maxSlots / 4][4];
    int rowIndex = 0;
    int rowChangedCount = 0;
    while (rowIndex < hexMatrix.length) {
      rowChangedCount = rowChangedCount + 1;
      if ((rowIndex < (hexMatrix.length - 4)) && 
            (rowChangedCount > 4)) {
        rowChangedCount = 0;
        String[] empty = {" ", " ", " ", " "}; 
        hexMatrix[rowIndex] = empty; 
      } else {
      
        for (int col = 0; col < 4; col++) {
          if (p.length() > 0) {
            int charCode = Character.codePointAt(p.substring(0,1), 0);
            hexMatrix[rowIndex][col] = Integer.toHexString(charCode).toUpperCase();
            p = p.substring(1);
          } else {
            int charCode = Character.codePointAt("" + s, 0);
            hexMatrix[rowIndex][col] = Integer.toHexString(charCode).toUpperCase();
          }      
        }
      }
      rowIndex = rowIndex + 1;
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
      
      String[][] hexMat = getHexMatP(subChar.charAt(0), plainText); 
      print2dArray(hexMat);
    }    
  }
}

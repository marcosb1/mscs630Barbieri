/**
 * file: Driver
 * author: Marcos Barbieri
 * course: MSCS 630L
 * assignment: lab 3 
 * due date: 03/02/2018
 * version: 0.1
 *
 * Explore key concepts of Matrices in Modular Arithmetic 
 */
import java.util.Scanner;
import java.util.Arrays;

class Driver {
  
  public static void print2dArray(int[][] A) {
    for (int i = 0; i < A.length; i++) {
      System.out.println(Arrays.toString(A[i]));
    }
  }

  public static int[][] getCofactorMatrix(int[][] A, int cofactor) {
    int[][] cofactorMat = new int[A.length - 1][A.length-1];

    int i1 = 0; 
    for (int i = 1; i < A.length; i++) {
      int j1 = 0;
      for (int j = 0; j < A[0].length; j++) {
        if (j == cofactor)
          continue;
        cofactorMat[i1][j1] = A[i][j];
        j1 = j1 + 1;
      }
      i1 = i1 + 1;
    }
    
    return cofactorMat;
  }

  public static int det(int[][] A) {
    Driver.print2dArray(A);
    int det = 0;

    if (A.length == 1) {
      det = A[0][0];
    } else if (A.length == 2) {
      det = (A[0][0] * A[1][1]) - (A[0][1] * A[1][0]);
    } else {
      boolean isPositive = true;
      for (int a = 0; a < A[0].length; a++) {
        int newDet = A[0][a] * det(getCofactorMatrix(A, a));
        det += isPositive ? newDet : -1 * newDet;
        isPositive = !isPositive;
      }
    }

    return det;
  } 
 
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    // get measurement of matrix as well as modulo value
    
    while (input.hasNext()) {
      String head = input.nextLine();
      String[] meta = head.split("\\s+");
      int mod = Integer.parseInt(meta[0]);
      int dim = Integer.parseInt(meta[1]);
       
      // TODO: Calculate determinant
      int[][] inputMatrix = new int[dim][dim];
      for (int i = 0; i < dim; i++) { 
        String line = input.nextLine();
        String[] lineParts = line.split("\\s+");
        // populate input matrix with values
        if (lineParts.length != dim) {
          // TODO: valid error output
          System.err.println("Please provide a square matrix");
          break;
        }
        
        for (int j = 0; j < lineParts.length; j++) {
          inputMatrix[i][j] = Integer.parseInt(lineParts[j]);  
        }        
      }
      
      int det = det(inputMatrix);
      System.out.println(det);
      System.out.println(mod);
      System.out.println((det) % (mod));
    }    
  }
}

/**
 * file: Driver.java
 * author: Marcos Barbieri
 * course: MSCS 630L
 * assignment: lab 5
 * due date: 04/18/2018
 * version: 0.1
 *
 * Explore key concepts of the Advanced Encryption System (AES)
 */

import java.util.Scanner;
import java.util.Arrays;

class DriverAES {

  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);

    while (input.hasNext()) {
      String head = input.nextLine();
      String[] keys = AESCipher.aesRoundKeys(head);
      for (int i = 0; i < keys.length; i++) {
        System.out.println(keys[i]);
      }
    }
  }

}

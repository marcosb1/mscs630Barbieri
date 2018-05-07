/**
 * File: Aescipher.java
 *
 * It accepts user input, key and decrypts
 *         the cipher
 *
 */

class Aescipher {

  /**
   * S_BOX static variable which is used for s-box transformations and used in
   * aesBox
   */
  private static final String[][] S_BOX = {
      { "63", "7C", "77", "7B", "F2", "6B", "6F", "C5", "30", "01", "67", "2B", "FE", "D7", "AB", "76" },
      { "CA", "82", "C9", "7D", "FA", "59", "47", "F0", "AD", "D4", "A2", "AF", "9C", "A4", "72", "C0" },
      { "B7", "FD", "93", "26", "36", "3F", "F7", "CC", "34", "A5", "E5", "F1", "71", "D8", "31", "15" },
      { "04", "C7", "23", "C3", "18", "96", "05", "9A", "07", "12", "80", "E2", "EB", "27", "B2", "75" },
      { "09", "83", "2C", "1A", "1B", "6E", "5A", "A0", "52", "3B", "D6", "B3", "29", "E3", "2F", "84" },
      { "53", "D1", "00", "ED", "20", "FC", "B1", "5B", "6A", "CB", "BE", "39", "4A", "4C", "58", "CF" },
      { "D0", "EF", "AA", "FB", "43", "4D", "33", "85", "45", "F9", "02", "7F", "50", "3C", "9F", "A8" },
      { "51", "A3", "40", "8F", "92", "9D", "38", "F5", "BC", "B6", "DA", "21", "10", "FF", "F3", "D2" },
      { "CD", "0C", "13", "EC", "5F", "97", "44", "17", "C4", "A7", "7E", "3D", "64", "5D", "19", "73" },
      { "60", "81", "4F", "DC", "22", "2A", "90", "88", "46", "EE", "B8", "14", "DE", "5E", "0B", "DB" },
      { "E0", "32", "3A", "0A", "49", "06", "24", "5C", "C2", "D3", "AC", "62", "91", "95", "E4", "79" },
      { "E7", "C8", "37", "6D", "8D", "D5", "4E", "A9", "6C", "56", "F4", "EA", "65", "7A", "AE", "08" },
      { "BA", "78", "25", "2E", "1C", "A6", "B4", "C6", "E8", "DD", "74", "1F", "4B", "BD", "8B", "8A" },
      { "70", "3E", "B5", "66", "48", "03", "F6", "0E", "61", "35", "57", "B9", "86", "C1", "1D", "9E" },
      { "E1", "F8", "98", "11", "69", "D9", "8E", "94", "9B", "1E", "87", "E9", "CE", "55", "28", "DF" },
      { "8C", "A1", "89", "0D", "BF", "E6", "42", "68", "41", "99", "2D", "0F", "B0", "54", "BB", "16" } };

  /**
   * R_CON static variable which is used for r-con transformations and used in
   * aesRcon
   */

  private static final String[][] R_CON = {
      { "8D", "01", "02", "04", "08", "10", "20", "40", "80", "1B", "36", "6C", "D8", "AB", "4D", "9A" },
      { "2F", "5E", "BC", "63", "C6", "97", "35", "6A", "D4", "B3", "7D", "FA", "EF", "C5", "91", "39" },
      { "72", "E4", "D3", "BD", "61", "C2", "9F", "25", "4A", "94", "33", "66", "CC", "83", "1D", "3A" },
      { "74", "E8", "CB", "8D", "01", "02", "04", "08", "10", "20", "40", "80", "1B", "36", "6C", "D8" },
      { "AB", "4D", "9A", "2F", "5E", "BC", "63", "C6", "97", "35", "6A", "D4", "B3", "7D", "FA", "EF" },
      { "C5", "91", "39", "72", "E4", "D3", "BD", "61", "C2", "9F", "25", "4A", "94", "33", "66", "CC" },
      { "83", "1D", "3A", "74", "E8", "CB", "8D", "01", "02", "04", "08", "10", "20", "40", "80", "1B" },
      { "36", "6C", "D8", "AB", "4D", "9A", "2F", "5E", "BC", "63", "C6", "97", "35", "6A", "D4", "B3" },
      { "7D", "FA", "EF", "C5", "91", "39", "72", "E4", "D3", "BD", "61", "C2", "9F", "25", "4A", "94" },
      { "33", "66", "CC", "83", "1D", "3A", "74", "E8", "CB", "8D", "01", "02", "04", "08", "10", "20" },
      { "40", "80", "1B", "36", "6C", "D8", "AB", "4D", "9A", "2F", "5E", "BC", "63", "C6", "97", "35" },
      { "6A", "D4", "B3", "7D", "FA", "EF", "C5", "91", "39", "72", "E4", "D3", "BD", "61", "C2", "9F" },
      { "25", "4A", "94", "33", "66", "CC", "83", "1D", "3A", "74", "E8", "CB", "8D", "01", "02", "04" },
      { "08", "10", "20", "40", "80", "1B", "36", "6C", "D8", "AB", "4D", "9A", "2F", "5E", "BC", "63" },
      { "C6", "97", "35", "6A", "D4", "B3", "7D", "FA", "EF", "C5", "91", "39", "72", "E4", "D3", "BD" },
      { "61", "C2", "9F", "25", "4A", "94", "33", "66", "CC", "83", "1D", "3A", "74", "E8", "CB", "8D" } };

  private static final char[] mult2Lookup = {
    0x00,0x02,0x04,0x06,0x08,0x0a,0x0c,0x0e,0x10,0x12,0x14,0x16,0x18,0x1a,0x1c,0x1e,
    0x20,0x22,0x24,0x26,0x28,0x2a,0x2c,0x2e,0x30,0x32,0x34,0x36,0x38,0x3a,0x3c,0x3e,
    0x40,0x42,0x44,0x46,0x48,0x4a,0x4c,0x4e,0x50,0x52,0x54,0x56,0x58,0x5a,0x5c,0x5e,
    0x60,0x62,0x64,0x66,0x68,0x6a,0x6c,0x6e,0x70,0x72,0x74,0x76,0x78,0x7a,0x7c,0x7e,
    0x80,0x82,0x84,0x86,0x88,0x8a,0x8c,0x8e,0x90,0x92,0x94,0x96,0x98,0x9a,0x9c,0x9e,
    0xa0,0xa2,0xa4,0xa6,0xa8,0xaa,0xac,0xae,0xb0,0xb2,0xb4,0xb6,0xb8,0xba,0xbc,0xbe,
    0xc0,0xc2,0xc4,0xc6,0xc8,0xca,0xcc,0xce,0xd0,0xd2,0xd4,0xd6,0xd8,0xda,0xdc,0xde,
    0xe0,0xe2,0xe4,0xe6,0xe8,0xea,0xec,0xee,0xf0,0xf2,0xf4,0xf6,0xf8,0xfa,0xfc,0xfe,
    0x1b,0x19,0x1f,0x1d,0x13,0x11,0x17,0x15,0x0b,0x09,0x0f,0x0d,0x03,0x01,0x07,0x05,
    0x3b,0x39,0x3f,0x3d,0x33,0x31,0x37,0x35,0x2b,0x29,0x2f,0x2d,0x23,0x21,0x27,0x25,
    0x5b,0x59,0x5f,0x5d,0x53,0x51,0x57,0x55,0x4b,0x49,0x4f,0x4d,0x43,0x41,0x47,0x45,
    0x7b,0x79,0x7f,0x7d,0x73,0x71,0x77,0x75,0x6b,0x69,0x6f,0x6d,0x63,0x61,0x67,0x65,
    0x9b,0x99,0x9f,0x9d,0x93,0x91,0x97,0x95,0x8b,0x89,0x8f,0x8d,0x83,0x81,0x87,0x85,
    0xbb,0xb9,0xbf,0xbd,0xb3,0xb1,0xb7,0xb5,0xab,0xa9,0xaf,0xad,0xa3,0xa1,0xa7,0xa5,
    0xdb,0xd9,0xdf,0xdd,0xd3,0xd1,0xd7,0xd5,0xcb,0xc9,0xcf,0xcd,0xc3,0xc1,0xc7,0xc5,
    0xfb,0xf9,0xff,0xfd,0xf3,0xf1,0xf7,0xf5,0xeb,0xe9,0xef,0xed,0xe3,0xe1,0xe7,0xe5
  };

  private static final char[] mult3Lookup = {
    0x00,0x03,0x06,0x05,0x0c,0x0f,0x0a,0x09,0x18,0x1b,0x1e,0x1d,0x14,0x17,0x12,0x11,
    0x30,0x33,0x36,0x35,0x3c,0x3f,0x3a,0x39,0x28,0x2b,0x2e,0x2d,0x24,0x27,0x22,0x21,
    0x60,0x63,0x66,0x65,0x6c,0x6f,0x6a,0x69,0x78,0x7b,0x7e,0x7d,0x74,0x77,0x72,0x71,
    0x50,0x53,0x56,0x55,0x5c,0x5f,0x5a,0x59,0x48,0x4b,0x4e,0x4d,0x44,0x47,0x42,0x41,
    0xc0,0xc3,0xc6,0xc5,0xcc,0xcf,0xca,0xc9,0xd8,0xdb,0xde,0xdd,0xd4,0xd7,0xd2,0xd1,
    0xf0,0xf3,0xf6,0xf5,0xfc,0xff,0xfa,0xf9,0xe8,0xeb,0xee,0xed,0xe4,0xe7,0xe2,0xe1,
    0xa0,0xa3,0xa6,0xa5,0xac,0xaf,0xaa,0xa9,0xb8,0xbb,0xbe,0xbd,0xb4,0xb7,0xb2,0xb1,
    0x90,0x93,0x96,0x95,0x9c,0x9f,0x9a,0x99,0x88,0x8b,0x8e,0x8d,0x84,0x87,0x82,0x81,
    0x9b,0x98,0x9d,0x9e,0x97,0x94,0x91,0x92,0x83,0x80,0x85,0x86,0x8f,0x8c,0x89,0x8a,
    0xab,0xa8,0xad,0xae,0xa7,0xa4,0xa1,0xa2,0xb3,0xb0,0xb5,0xb6,0xbf,0xbc,0xb9,0xba,
    0xfb,0xf8,0xfd,0xfe,0xf7,0xf4,0xf1,0xf2,0xe3,0xe0,0xe5,0xe6,0xef,0xec,0xe9,0xea,
    0xcb,0xc8,0xcd,0xce,0xc7,0xc4,0xc1,0xc2,0xd3,0xd0,0xd5,0xd6,0xdf,0xdc,0xd9,0xda,
    0x5b,0x58,0x5d,0x5e,0x57,0x54,0x51,0x52,0x43,0x40,0x45,0x46,0x4f,0x4c,0x49,0x4a,
    0x6b,0x68,0x6d,0x6e,0x67,0x64,0x61,0x62,0x73,0x70,0x75,0x76,0x7f,0x7c,0x79,0x7a,
    0x3b,0x38,0x3d,0x3e,0x37,0x34,0x31,0x32,0x23,0x20,0x25,0x26,0x2f,0x2c,0x29,0x2a,
    0x0b,0x08,0x0d,0x0e,0x07,0x04,0x01,0x02,0x13,0x10,0x15,0x16,0x1f,0x1c,0x19,0x1a
  };

  static String[][] GaloisMatrix = { { "02", "03", "01", "01" }, { "01", "02", "03", "01" },
      { "01", "01", "02", "03" }, { "03", "01", "01", "02" } };
  // masterKey array is declared to save the key user gives
  public static String[][] masterKey_encrypt;
  public static String[][] masterText_encrypt;
  // keyMatrixW array is declared to save the key's which will be generated
  public static String[][] keyMatrixW_encrypt;

  /**
   * This method accepts user given key and saves it into a 4*4 matrix. Once
   * the input is processed generateWMatrix() method is called.
   *
   * @param key
   *            : Input key
   */
  public static String processInput(String plainText, String inputKey, int[] size_basket, String verbose) {
    String cipherFinal = "";
    int i = 0;
    int j = 0;
    int col_valueforInput;
    int column_size;
    int rounds;
    col_valueforInput = size_basket[0];
    column_size = size_basket[1];
    rounds = size_basket[2];
    keyMatrixW_encrypt = new String[4][column_size];
    masterKey_encrypt = new String[4][col_valueforInput];
    masterText_encrypt = new String[4][4];
    for (int column = 0; column < col_valueforInput; column++) {
      for (int row = 0; row < 4; row = row + 1) {
        masterKey_encrypt[row][column] = inputKey.substring(i, i + 2);
        i = i + 2;
      }
    }

    for (int column = 0; column < 4; column++) {
      for (int row = 0; row < 4; row = row + 1) {
        masterText_encrypt[row][column] = plainText.substring(j, j + 2);
        j = j + 2;
      }
    }
    if (verbose.equals("1")) {
      System.out.println("Text to be encrypted after padding is");
      System.out.println(plainText);
    }
    cipherFinal = generateWMatrix(col_valueforInput, column_size, rounds);
    return cipherFinal;
  }

  public static String generateCipher(String[][] masterKey, String[][] masterText, int column_size, int row_size,
      int rounds) {

    String[][] keyHex = new String[4][4];
    String out = "";
    int WCol = 0;
    int roundCounter = 0;
    while (WCol < column_size) {
      for (int cols = 0; cols < 4; cols++, WCol++) {
          keyHex[0][cols] = keyMatrixW_encrypt[0][WCol];
          keyHex[1][cols] = keyMatrixW_encrypt[1][WCol];
          keyHex[2][cols] = keyMatrixW_encrypt[2][WCol];
          keyHex[3][cols] = keyMatrixW_encrypt[3][WCol];
          /*for (int row = 0; row < 4; row++) {
            keyHex[row][cols] = keyMatrixW_encrypt[row][WCol];
          }*/
      }

      if (roundCounter != (rounds - 1)) {
        roundCounter++;
        masterText = aesStateXor(masterText, keyHex);
        // Exclusive or output is passed to nibble substitution is
        // called
        masterText = aesNibbleSub(masterText);
        // Nibble substituted output is called to shiftrows method
        masterText = aesShiftRow(masterText);
        // Shifted output is sent to mixing columns function
        if (roundCounter != (rounds - 1)) {
          masterText = aesMixColumn(masterText);
        }

      } else
        // In the tenth round we do only plain xor
        masterText = aesStateXor(masterText, keyHex);
    }
    // System.out.println("The Cipher Text is");
    for (int cols = 0; cols < 4; cols++) {
      for (int row = 0; row < 4; row++) {
        //outValue = outValue.append(masterText[row][cols]);
        out += masterText[row][cols];
        // System.out.print(masterText[row][cols]+ "\t");
      }

    }
    //System.out.println();
    // Aesdecipher.processInput(outValue, inputkey, size_basket);
    return out;

  }

  /**
   * generateWMatrix() method starts processing the keys for the 4*44 keys
   * matrix
   */
  public static String generateWMatrix(int col_valueforInput, int column_size, int rounds) {

    int roundCounter = 0;
    String cipherW = "";
    for (int row = 0; row < 4; row = row + 1) {
      for (int column = 0; column < col_valueforInput; column++) {
        keyMatrixW_encrypt[row][column] = masterKey_encrypt[row][column];
      }
    }

    // Processing the rest keys for keyMatrixW , by taking an intermediate
    // matrix wNewMatrix for processing purpose
    String[][] wNewMatrix = null;
    for (int column = col_valueforInput; column < column_size; column++) {
      /**
       * if the column number is not a multiple of 4 the following steps
       * are to be implemented
       */

      if (column % col_valueforInput != 0 && col_valueforInput == 8) {
        if (column % 4 == 0) {
          for (int row = 0; row < 4; row++) {
            keyMatrixW_encrypt[row][column] = aesSbox(keyMatrixW_encrypt[row][column - 1]);
            keyMatrixW_encrypt[row][column] = exclusiveOr(
                keyMatrixW_encrypt[row][column - col_valueforInput], keyMatrixW_encrypt[row][column]);

          }
        } else {
          for (int row = 0; row < 4; row++) {

            keyMatrixW_encrypt[row][column] = exclusiveOr(
                keyMatrixW_encrypt[row][column - col_valueforInput],
                keyMatrixW_encrypt[row][column - 1]);
          }
        }
      } else if (column % col_valueforInput != 0 && col_valueforInput != 8) {
        for (int row = 0; row < 4; row++) {

          keyMatrixW_encrypt[row][column] = exclusiveOr(keyMatrixW_encrypt[row][column - col_valueforInput],
              keyMatrixW_encrypt[row][column - 1]);
        }

      } else if (column % col_valueforInput == 0) {

        // If its a multiple of 4 the following steps will be
        // implemented
        wNewMatrix = new String[1][4];
        // Inserting values and shifting cells in the intermediate
        // matrix
        wNewMatrix[0][0] = keyMatrixW_encrypt[1][column - 1];
        wNewMatrix[0][1] = keyMatrixW_encrypt[2][column - 1];
        wNewMatrix[0][2] = keyMatrixW_encrypt[3][column - 1];
        wNewMatrix[0][3] = keyMatrixW_encrypt[0][column - 1];
        // Once the shifting is done we do the s-box transformation
        for (int i = 0; i < 1; i++) {
          for (int j = 0; j < 4; j++) {
            wNewMatrix[i][j] = aesSbox(wNewMatrix[i][j]);
          }
        }
        int r = column / col_valueforInput;
        // Performing XOR of the R_CON value and new matrix value
        wNewMatrix[0][0] = exclusiveOr(aesRcon(r), wNewMatrix[0][0]);
        // Final computation of recursively XOR the values
        for (int row = 0; row < 4; row++) {
          keyMatrixW_encrypt[row][column] = exclusiveOr(keyMatrixW_encrypt[row][column - col_valueforInput],
              wNewMatrix[0][row]);
        }
      }
    }
    cipherW = generateCipher(masterKey_encrypt, masterText_encrypt, column_size, col_valueforInput, rounds);
    return cipherW;
  }

  /**
   * This method takes two input strings which are hexadecimal values and
   * convert them into decimal and performe exclusive OR. Saves and returns
   * the result back.
   *
   * @param val1
   *            : Inputs to be XORed
   * @param val2
   *            : Inputs to be XORed
   * @return : Returns hexadecimal string after exclusive OR operation
   */
  private static String exclusiveOr(String val1, String val2) {
    int exclusiveOutput = Integer.parseInt(val1, 16) ^ Integer.parseInt(val2, 16);
    String hexResult = Integer.toHexString(exclusiveOutput);
    return hexResult.length() == 1 ? ("0" + hexResult) : hexResult;
  }

  /**
   * This method takes a hexadecimal value as the input, splits it and
   * converts into decimal. Based on the two integers generated we map the
   * S_BOX matrix and find the respective value and return it back.
   *
   * @param sBoxInput
   *            : String which is split and used as index on s_box
   * @return : Returns the value from s-box matrix
   */
  private static String aesSbox(String sBoxInput) {

    int firstDigitInt = Integer.parseInt(sBoxInput.split("")[0], 16);
    int secondDigitInt = Integer.parseInt(sBoxInput.split("")[1], 16);
    String sboxOutput = S_BOX[firstDigitInt][secondDigitInt];
    return sboxOutput;
  }

  /**
   * This method takes a the string and finds the respective element in the
   * R_CON matrix.
   *
   * @param rConInput
   *            : Index to lookup R_CON matrix
   * @return : Value from the R_CON matrix
   */
  private static String aesRcon(int rConInput) {

    String rConOutput = R_CON[0][rConInput];
    return rConOutput;
  }

  public static String[][] aesStateXor(String[][] sHex, String[][] keyHex) {
    String exclusiveOrArray[][] = new String[4][4];
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        exclusiveOrArray[i][j] = exclusiveOr(sHex[i][j], keyHex[i][j]);
      }
    }

    return exclusiveOrArray;

  }

  /**
   * Accepts Exclusiveor output and finds the respective element in S_BOX
   * matrix
   *
   * @param exclusive
   * @return
   */
  public static String[][] aesNibbleSub(String[][] exclusive) {
    String sBoxValues[][] = new String[4][4];
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        sBoxValues[i][j] = aesSbox(exclusive[i][j]);
      }
    }
    return sBoxValues;
  }

  /**
   * Once the S_BOX values are returned they are shifted
   *
   * @param sHex
   * @return
   */
  public static String[][] aesShiftRow(String[][] sHex) {
    String[][] outStateHex = new String[4][4];
    int counter = 4;
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        if (i > 0)
          outStateHex[i][(j + counter) % 4] = sHex[i][j];

        else
          outStateHex[i][j] = sHex[i][j];
      }
      counter--;
    }
    return outStateHex;
  }

  protected static String[][] aesMixColumn(String[][] inStateHex) {
    String sum;
    String Product[][] = new String[4][4];


    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        sum = "0";
        for (int k = 0; k < 4; k++) {

          switch (GaloisMatrix[i][k]) {
          // checks data from galios matrix and verifies data to
          // perform
          // multiplication
          case "01":
            sum = exclusiveOr(sum, inStateHex[k][j]);
            break;
          case "02":
            // If its 02 then multiply2 function is called
            sum = exclusiveOr(sum, multiply2(inStateHex[k][j]));
            break;
          case "03":
            // If its 02 then multiply3 function is called
            sum = exclusiveOr(sum, multiply3(inStateHex[k][j]));
            break;
          }
        }

        Product[i][j] = sum;
      }
    }
    
    return Product;
  }

  /**
   * In this function , mix columns operations having multiplication
   * with 3 are considered here and operation is performed.
   *
   * @param InputHex
   * @return
   */
  protected static String multiply3(String InputHex) {
    return mult3Lookup[Integer.parseInt(InputHex, 16)] + "";
  }

  /**
   * In Mix columns operation if the element is to be multiplied with 2, we
   * will use this function to perform the operation of checking most
   * significant bit shifting the bits
   *
   * @param InputHex
   * @return
   */
  protected static String multiply2(String InputHex) {
    return mult2Lookup[Integer.parseInt(InputHex, 16)] + "";
  }

}

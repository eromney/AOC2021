import java.util.*;
import java.io.*;
import java.math.BigInteger;

public class day6 {

  public static void main (String[] args) {
    readInput();

  }
 
  public static void readInput () {
    File test = new File("test.txt");
    File input = new File("input.txt");
    BufferedReader br = null;
    
    try {
      br = new BufferedReader(new FileReader(input));
      String[] fishAges = br.readLine().split(",");
      long[] fish = new long[9];
      for (String s : fishAges) {
        fish[Integer.parseInt(s)]++;
      }
      mutateFish(fish, 256);
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        try {
          if (br != null) br.close();
        } catch (IOException e) {
           e.printStackTrace();
        }
    }
  }
  public static void mutateFish (long[] fish, int days) {
    int curDay = 0;
    while (curDay++ != days) {
      long[] tmp = new long[9];
      for (int i = 0; i < 9; i++) {
        if (i == 0) {
          tmp[8] += fish[i];
          tmp[6] += fish[i];
        } else {
            tmp[i-1] += fish[i];
        }
      }
      fish = tmp;
    }
    long res = 0;
    for (long i : fish) {
      res += i;
      System.out.println("value: " + i); 
    }
    System.out.println("Answer is: " + res);
  }
}

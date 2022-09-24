import java.io.*;
import java.util.*;


public class day7 {

  public static void main (String[] args) {
    readInput();


  }
  public static void readInput () {
    BufferedReader br = null;
    
     try {
       File input = new File("input.txt");
       File test = new File("test.txt");
       br = new BufferedReader(new FileReader(input));
       // read input logic
       String[] in = br.readLine().split(",");
       int[] nums = new int[in.length];
       for (int i = 0; i < in.length; i++) {
         nums[i] = Integer.parseInt(in[i]);
       }
       bruteForce(nums);

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

  public static void bruteForce (int[] in) {
    int cheapestOutcome = Integer.MAX_VALUE;
    int min = Integer.MAX_VALUE;
    int max = Integer.MIN_VALUE;
    for (int i : in) {
      min = Math.min(min, i);
      max = Math.max(max, i);
    }
    for (int i = min; i < max; i++) {
      int pos = i;
      int tmp = 0;
      for (int val : in) {
        //tmp += Math.abs(val - pos);
        int diff = Math.abs(val - pos);
        for (int step = 1; step<=diff; step++) {
          tmp += step;
        }
      }
      cheapestOutcome = Math.min(cheapestOutcome, tmp);
    }
    System.out.println("Answer is: " + cheapestOutcome);
  }
}

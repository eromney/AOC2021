import java.util.*;
import java.io.*;

public class day8 {

  public static void main (String[] args) {
    readInput();
  }

  public static void readInput () { 
    BufferedReader br = null;
    try {
//      br = new BufferedReader(new FileReader(new File("test.txt")));
      br = new BufferedReader(new FileReader(new File("input.txt")));
      List<Pair> digits = new ArrayList<Pair>();
      String l;
      while ((l = br.readLine()) != null) {
        String[] line = l.split("\\|");
        String[] in = line[0].split(" ");
        String[] out = line[1].split(" ");
        digits.add(new Pair(in, out));
      }
//      printInput(digits);
//      partOne(digits);
        calcOutput(digits);
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

  public static void partOne (List<Pair> digits) {
    int ans = 0;
    int total = 0;
    for (Pair p : digits) {
      for (String s : p.getSecond()) {
        int count = countUnique(s);
        boolean isOne = count == 2;
        boolean isFour = count == 4;
        boolean isSeven = count == 3;
        boolean isEight = count == 7;
        if (isOne || isFour || isSeven || isEight) ans++;
      }
      total++;
    }
    System.out.println("Answer for part one  is: " + ans);

  }

  public static int countUnique(String s) {
    Set<Character> set = new HashSet<Character>();
    for (char c : s.toCharArray()) {
      if (!set.contains(c)) set.add(c);
    }
    return set.size();
  }

  public static void printInput (List<Pair> input) {
    for (Pair p : input) {
      for (String in : p.getFirst()) System.out.print(in + " "); 
      System.out.println("|");
      for (String out : p.getSecond()) System.out.print(out + " ");
      System.out.println();
    }
  }
  public static void calcOutput (List<Pair> input) {
    int res = 0;
    for (Pair p : input) {
       res +=  decodeInput(p);
    }
    System.out.println("Answer for part two is: " + res);

  }
  public static String sort (String s) {
    char tmp[] = s.toCharArray();
    Arrays.sort(tmp);
    return new String(tmp);
  }
 public static int decodeInput (Pair p) {
      String[] numbers = new String[10];
      boolean[] found = new boolean[10];
      int numsFound = 0;
      boolean allFound = false;
      // cycle through input and get the numbers you know
      for (String s : p.getFirst()) {
        int length = s.length(); //countUnique(s);
        switch (length) {
          case 2:
            // one
            numsFound += !found[1] ? 1 : 0;
            numbers[1] = sort(s);
            found[1] = true;
            break;
          case 4:
            // four
            numsFound += !found[4] ? 1 : 0;
            numbers[4] = s;
            found[4] = true;
            break;
          case 3:
            // seven
            numsFound += !found[7] ? 1 : 0;
            numbers[7] = s;
            found[7] = true;
            break;
          case 7:
            // eight
            numsFound += !found[8] ? 1 : 0;
            numbers[8] = s;
            found[8] = true;
            break;
        }
        if (numsFound == 4) break; 
      }
      // cycle through input and get the less obvious numbers
      for (String s : p.getFirst()) {
        //0,6,9
        if (s.length() == 6) {
            if (!found[9]) {
                boolean nine = isNine(s, numbers);
                if (nine) {
                  found[9] = true;
                  numbers[9] = s;
                }
            }
            if (!found[6]) {
                boolean six = isSix(s, numbers);
                if (six) {
                  numbers[6] = s;
                  found[6] = true;
                }
            }
            if (!found[0]) {
                boolean zero = isZero(s, numbers);
               if (zero) {
                  numbers[0] = s;
                  found[0] = true; 
               } 
            }
        }
        //2,3,5
        if (s.length() == 5) {
            if (!found[2]) {
                boolean two = isTwo(s, numbers);
                if (two) {
                    numbers[2] = s;
                    found[2] = true;
                }
            }
            if (!found[3]) {
                boolean three = isThree(s,numbers);
                if (three) {
                    numbers[3] = s;
                    found[3] = true;
                }
            }
            if (!found[5]) {
                boolean five = isFive(s,numbers);
                if (five) {
                    numbers[5] = s;
                    found[5] = true;
                }
            }
        }
      }

      for (int i = 0; i < numbers.length; i++) {
        System.out.println("number " + i + " is: " + numbers[i]);
      }
      String ret = "";
      String[] output = p.getSecond();
      for (int i = 1; i < output.length; i++) {
//       System.out.println(" output " + output[i]);
        // calculate outputValue
        ret += "" + match(numbers, output[i]);
      }
      System.out.println("res before parse: " + ret);
      return Integer.parseInt(ret);    
  }
  public static boolean isTwo (String s, String[] numbers) {
    int numSimilar = 0;
    for (char c: numbers[4].toCharArray()) {
        if (s.contains("" + c)) numSimilar++;
    }
    if (!containsChars(numbers[7], s) && numSimilar == 2) return true;
    else return false;
  }

  public static boolean isThree (String s, String[] numbers) {
    if (containsChars(numbers[7], s)) return true;
    else return false;
  }

  public static boolean isFive (String s, String[] numbers) {
      // 5 is only number that contains 3/4 chars with 4, does not contain a 7, and is 5 letters length
      int numSimilar = 0;
      for (char c : numbers[4].toCharArray()) {
        if (s.contains(""+c)) numSimilar++;
      }
      if(!containsChars(numbers[7],s) && numSimilar == 3) return true;
      return false;

  }
  public static boolean isZero (String s, String[] numbers) {
      if(containsChars(numbers[1], s) && !containsChars(numbers[4], s)) return true;
      else return false;
  }
  
  public static boolean isSix (String s, String[] numbers) {
    if (!containsChars(numbers[1],s) && !containsChars(numbers[4], s)) return true;
    else return false;
  }


  public static boolean isNine (String s, String[] numbers) {
      // nine is the only number that has a four in it
      if (containsChars(numbers[4], s)) return true;
      else return false;
  
  }
  
  public static boolean containsChars (String src, String dst) {
    for (char c : src.toCharArray()) {
        if (!dst.contains("" + c)) return false;
    }
    return true;
  }

  public static int match (String[] numbers, String s) {
    for (int i = 0; i < numbers.length; i++) {
        
        String num = numbers[i] == null ? "" : numbers[i];
       // System.out.println("num: " + num);
        if (num.length() == s.length()) {
            boolean isNum = true;
            for (int j = 0; j < s.length(); j++) {
                if (!num.contains("" + s.charAt(j))) {
                    isNum = false;
                    break;
                }
            }
            if (isNum) return i;
        }    
    }
    int ret = -1;
    for (int i = 0; i < numbers.length; i++) if (numbers[i] == null) ret = i;
    return ret;
  }
}

class Pair {
  String[] in;
  String[] out;

  public Pair (String[] in, String[] out) {
    this.in = in;
    this.out = out;
  }
  public String[] getFirst () {
    return in;
  }

  public String[] getSecond () { 
    return out;
  } 

}

import java.util.*;
import java.io.*;


public class day5 {

  public static void main (String args[]) {
    readInput();

  }

  public static void readInput() {
    File file = new File("input.txt");
//    File file = new File("test.txt");
    BufferedReader br = null;
    try {
      br = new BufferedReader(new FileReader(file));
      Vents vents = new Vents();
      int maxX = 0;
      int maxY = 0;
      String line;
      while ((line = br.readLine()) != null) {
        String[] input = line.split(" ");
        String[] coord1 = input[0].split(",");
        String[] coord2 = input[2].split(",");
        maxX = Math.max(maxX, Math.max(Integer.parseInt(coord1[0]), Integer.parseInt(coord2[0])));
        maxY = Math.max(maxY, Math.max(Integer.parseInt(coord1[1]), Integer.parseInt(coord2[1])));
        Line l = new Line(Integer.parseInt(coord1[0]), Integer.parseInt(coord1[1]), Integer.parseInt(coord2[0]), Integer.parseInt(coord2[1]));
        vents.lines.add(l);
      }
//      vents.printVents();
      vents.formDiagram(maxY, maxX);

    }
    catch(IOException e) {
      e.printStackTrace();
    }
    finally {
      try {
        if (br != null) br.close();
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}

class Vents {
  List<Line> lines;
  int[][] ventLine;
  public Vents () {
    lines = new ArrayList<Line>();
  }

  void formDiagram (int maxY, int maxX) {
    ventLine = new int[maxY+1][maxX+1];
    for (Line l : lines) {
      boolean matchY = l.y1 == l.y2;
      boolean matchX = l.x1 == l.x2;
      if (matchY) {
        for (int j = Math.min(l.x1, l.x2); j <= Math.max(l.x1, l.x2); j++) {
          ventLine[l.y1][j] += 1;
        }
      }
      if (matchX) {
        for (int i = Math.min(l.y1, l.y2); i <= Math.max(l.y1, l.y2); i++) {
          ventLine[i][l.x1] += 1;
        }
      }
      // Diagonal Lines
      if (!matchX && !matchY) {
        boolean moveDown = l.y1 > l.y2;
        boolean moveLeft = l.x1 > l.x2;
        while (l.y1 != l.y2 && l.x1 != l.x2) {
          // assuming that wont have to deal invalid input, or check for index out of bounds
          ventLine[l.y1][l.x1] += 1;
          l.y1 += moveDown ? -1 : 1;
          l.x1 += moveLeft ? -1 : 1;
        }
        ventLine[l.y2][l.x2] += 1;
      }  
    }
//    printVentLines();
    countOfLargestOverlap();
  }
  void countOfLargestOverlap () {
    int ret = 0;
    for (int i = 0; i < ventLine[0].length; i++) {
      for (int j = 0; j < ventLine.length; j++) {
        ret += ventLine[i][j] >= 2 ? 1 : 0;
      }
    }
    System.out.println("Answer: " + ret);
  } 
  void printVentLines () {
    for (int i = 0; i < ventLine[0].length; i++) {
      for (int j = 0; j < ventLine.length; j++) {
        System.out.print(ventLine[i][j]);
      }
      System.out.println();
    }
  }

  void printVents () {
    for (Line l : lines) {
      System.out.println(l.x1 + "," + l.y1 + " -> " + l.x2 + "," + l.y2);
    }
  } 
}

class Line {
  int x1, y1, x2, y2;
  
  public Line (int x1, int y1, int x2, int y2) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
  }
}

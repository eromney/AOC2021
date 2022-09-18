import java.util.*;
import java.io.*;


public class day4 {

  public static void main(String[] args) {
    readInput();
  } 
  public static void simulateInput (String[] bingoNums, Boards boards) {
    for (String s : bingoNums) {
      /* marks board and calculates value - second param tells it to calculate either the first win value (false) or last win val (true) */
      if (boards.markBoards(Integer.parseInt(s), true)) break;
    }
    System.out.println("Answer is: " + boards.lastWinTotal);
  }

  public static void readInput() {
    String currentDir = System.getProperty("user.dir");
    File file = new File("input.txt");
//    File file = new File("test.txt");
    BufferedReader br = null;
    try {
      br = new BufferedReader(new FileReader(file));
      String[] bingoNums = br.readLine().split(",");
      Boards boards = new Boards();
      /* Test to make sure the input was read in properly */
//      for (String s : bingoNums) System.out.print(s + " ");
      /* while loop to go through all the board inputs */
      boolean isLast = false;
      while (br.readLine() != null && !isLast) {
        /* while loop to go through all the rows of the board */
        Point[][] tmp = new Point[5][5]; // boards will always be 5x5
        for (int i = 0; i < 5; i++) {
          String line = br.readLine();
          if (line == null) {
            isLast = true;
            break;
          }
          String[] row = line.split(" ");
          int index = 0;
          for (String s : row) {
            if (s.length() >= 1) tmp[i][index++] = new Point(Integer.parseInt(s), false);
          }
        }
        if (!isLast) {
          boards.addBoard(tmp); 
        }
      }
//      boards.printBoards();
      simulateInput(bingoNums, boards);
    }
    catch (IOException e){
      e.printStackTrace();
    }
    finally {
      try {
        if (br != null) {
          br.close();
        }
      }
      catch (IOException ioe) {
        System.out.println("Error closing the BR");
      }
    } 
  }

    
}
class Boards {
  List<Point[][]>boards;
  int lastWinTotal;

  public Boards () {
   boards = new ArrayList<Point[][]>();
   lastWinTotal = 0;
  }
  public boolean markBoards (int num, boolean isCalcLastWin) {
    for (Point[][] board : boards) {
      for (int i = 0; i < 5; i++) {
        for (int j = 0; j < 5; j++) {
          if (board[i][j].num == num) {
            board[i][j].isMarked = true;
            if(checkWin(board, i, j) && !isCalcLastWin) return true;
          }
        }
      }
    }
    return false;
  }
  
  boolean checkWin (Point[][] board, int row, int col) {
    /* checkt to see if this board has won already */
    if (board[0][0].num == -1) return false;
    boolean rowWin = board[row][0].isMarked && board[row][1].isMarked && board[row][2].isMarked && board[row][3].isMarked && board[row][4].isMarked;
    boolean colWin = board[0][col].isMarked && board[1][col].isMarked && board[2][col].isMarked && board[3][col].isMarked && board[4][col].isMarked;
    if(colWin || rowWin) {
      calcScore(board, board[row][col].num);
    }
    return rowWin || colWin;
  }

  void calcScore(Point[][] board, int lastNumCalled) {
    int unmarkedSum = 0;
    for (int i = 0; i < 5; i ++) {
      for (int j = 0; j < 5; j++) {
        unmarkedSum += board[i][j].isMarked ? 0 : board[i][j].num;
      } 
    }
    /* this is just a mark to know this board has won already */
    board[0][0].num = -1;
    int result = unmarkedSum * lastNumCalled;
    lastWinTotal = result;
  }

  void printBoards () {
    for (Point[][] b : boards) {
      for (int i = 0; i < 5; i++) {
        for (int j = 0; j < 5; j++) {
          System.out.print(b[i][j].num + " ");
        }
        System.out.println();
      }
      System.out.println();
    }
  }
 
  void addBoard (Point[][] board) {
    boards.add(board);
  }
}

class Point {
  int num;
  boolean isMarked = false;
  public Point (int num, boolean isMarked) {
    this.num = num;
    this.isMarked = isMarked;
  } 
}

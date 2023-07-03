import java.util.*;

class Comp{
  
  //Computer object ID
  private static int ID = 1;
  
  //The game board for the player/computer
  private int[][] board = new int[5][5];
  
  //The array that contains all values pulled from the Game's dataset
  private ArrayList<Integer> pulledNums = new ArrayList<Integer>();
	private String name;
  
	Comp() {
		name = "CP " + ID;
    ID++;
	}

  String getName() {
    return name;
  }

  void setName(String name) {
    this.name = name;
  }
  
  int[][] getBoard() {
    return board;
  }

  void setBoard(int[][] board) {
    this.board = board;
  }

  ArrayList<Integer> getList() {
    return pulledNums;
  }

  void addPull(int val) {
    pulledNums.add(val);
  }

  void clearPull() {
    pulledNums.clear();
  }

  //Checks the board for values similar to val
  void checkBoard(int val) {
    for (int o = 0; o < 5; o++) {
        for (int i = 0; i < 5; i++) {
          if (board[o][i] == val) {
            board[o][i] = 00;
          }
        }
      }
  }
	
}

class Player extends Comp {
  private Scanner input = new Scanner(System.in);
  private int[][] board = super.getBoard();
  private int wins;
  private int games;

  Player(String name) {
    super();
    setName(name);
  }

  int getGames() {
    return games;
  }

  int getWins() {
    return wins;
  }

  void setGames(int games) {
    this.games = games;
  }

  void setWins(int wins) {
    this.wins = wins;
  }

  //Modified version of the earlier method designed to take user input 
  boolean checkBoard(int x, int y) {
    int X = x - 1;
    int Y = (6 - y) - 1;
    
    for (int i = 0; i < getList().size(); i++) {
      if (board[Y][X] == getList().get(i)) {
        board[Y][X] = 00;
        return true;
      }
    }
    return false;
  }
  
  //Prompts the user to make a move
  boolean makeMove() {
    System.out.println("Do you see a matching value on your board?\nIf so, type in the coordinate or press ENTER to continue.");
    System.out.println("Or if you want to exit to menu, enter \"EXIT\".\n");
    System.out.println("Format: " + IOF.setColor('r') + "X" + IOF.setColor('-') + " (space) " + IOF.setColor('r') + "Y\n");
    while (true) {
      System.out.println(IOF.setColor('p'));
      String usrInput = input.nextLine();

      if (usrInput.isBlank()) {
        System.out.println(IOF.setColor('-'));
        break; 
      }

      try {
        if (checkBoard(Character.getNumericValue(usrInput.charAt(0)), Character.getNumericValue(usrInput.charAt(2)))) {
          System.out.println(IOF.setColor('-'));
          break;
        }
        else if (usrInput.equals("exit") || usrInput.equals("EXIT") || usrInput.equals("Exit")) {
          break;
        }
        else {
          System.out.println(IOF.setColor('-') + "It appears you put in the wrong coordinates. Please try again or press ENTER to continue.");
          IOF.delay(3500);
        }
      } catch (Exception ex) {
        if (usrInput.equals("exit") || usrInput.equals("EXIT") || usrInput.equals("Exit")) {
          System.out.println(IOF.setColor('-'));
          return false;
        }
        else {
          System.out.println(IOF.setColor('-') + "You didn't format the coordinates correctly, or there was a non-compatable value inserted. Please try again or press ENTER to continue.");
          IOF.delay(3500);
        }
        
      }
      
    }
    return true;
  }
}
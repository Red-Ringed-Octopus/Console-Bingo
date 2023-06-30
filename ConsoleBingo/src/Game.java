import java.util.ArrayList;
import java.util.Scanner;

class Game {

  //Dataset is the list of random values that all players use to populate and compare their boards to
  ArrayList<Integer> dataset = new ArrayList<Integer>();

  //playerComps contains all computers that were specified by the user
  ArrayList<Comp> playerComps = new ArrayList<Comp>();

  //The player itself, initially set to null in case the name isn't set properly
  Player usr = new Player("Null");
  Scanner input = new Scanner(System.in);

  //Manager dictates how stats are stored and how boards are displayed
  IOF manager;

  //Gamemode discusses which gamemode shall be played
  String gamemode;

  //The game is entirely ran through the constructor
  Game() {
    //Populates dataset and prompts user with a sign-in
    popList();
    manager = new IOF(usr);

    //First player prompt loop discussing main options
    while (true) {
      System.out.println("\033[H\033[2J");
      System.out.println("Hello " + usr.getName() + ". What would you like to do?");
      System.out.println("1.Play\n2.Check Stats\n3.Save Stats\n4.Change User\n5.Quit");
      String usrInput = input.nextLine();
      try {
        if (usrInput.equals("1") || usrInput.equals("Play") || usrInput.equals("play")) {
          if (selectGame()) {
            compCount();
            Play();
          }
        }
        else if (usrInput.equals("2") || usrInput.equals("Check Stats") || usrInput.equals("check stats")) {
          System.out.println("\033[H\033[2J");
          System.out.println("Games: " + usr.getGames() + "\nWins: " + usr.getWins());
          usrInput = input.nextLine();
        }
        else if (usrInput.equals("3") || usrInput.equals("Save Stats") || usrInput.equals("save stats")) {
          System.out.println("\033[H\033[2J");
          System.out.println("Saving...");
          manager.editStats(usr);
          System.out.println("Done!");
          IOF.delay(2000);
        }
        else if (usrInput.equals("4") || usrInput.equals("Change User") || usrInput.equals("change user")) {
          System.out.println("\033[H\033[2J");
          System.out.println("Please enter name.\n");
          usrInput = input.nextLine();
          manager.retrieveStats(usrInput, usr);
        }
        else if (usrInput.equals("5") || usrInput.equals("Quit") || usrInput.equals("quit")) {
          System.out.println("\033[H\033[2J");
          System.out.println("Goodbye!");
          IOF.delay(2000);
          break;
        }
        else {
          System.out.println("\033[H\033[2J");
          System.out.println("Not an option! Please input one of the three options on screen.");
          IOF.delay(2000);
        }
      } catch(Exception e) {
        System.out.println("\033[H\033[2J");
        System.out.println("Invalid input! Please try again.");
        System.out.println("\n\n\n\n" + e);
        IOF.delay(2000);
      }
    }
  }

  //Prompts the user on what gamemode they'd prefer
  boolean selectGame() {
    while (true) {
      try {  
        System.out.println("\033[H\033[2J");
        System.out.println("What type of bingo would you like to play?");
        System.out.println("1.Classic\n2.Diagonals Only\n3.Straights\n4.Blackout\n5.Go Back\n");
        String usrInput = input.nextLine();
        if (usrInput.equals("1") || usrInput.equals("Classic") || usrInput.equals("classic")) {
          gamemode = "Classic";
          return true;
        }
        else if (usrInput.equals("2") || usrInput.equals("Diagonals Only") || usrInput.equals("diagonals only")) {
          gamemode = "Diags";
          return true;
        }
        else if (usrInput.equals("3") || usrInput.equals("Straights") || usrInput.equals("straights")) {
          gamemode = "Straights";
          return true;
        }
        else if (usrInput.equals("4") || usrInput.equals("Blackout") || usrInput.equals("blackout")) {
          gamemode = "Blackout";
          return true;
        }
        else if (usrInput.equals("5") || usrInput.equals("Go Back") || usrInput.equals("go back")) {
          return false;
        }
        else {
          System.out.println("That's not an option! Please input one of the options above.");
          IOF.delay(2000);
        }
      } catch (Exception e) {
        System.out.println("Invalid input! Please try again.");
        IOF.delay(2000);
      }
    }
  }

  //Prompts the user on how many bots they'd like in their game
  void compCount() {
   
    while (true) {  
      try {
        System.out.println("\033[H\033[2J");
        System.out.println("How many computer players would you like to go against?");
        System.out.println("1 2 3 4 5");
        String usrInput = input.nextLine();

        if (Integer.parseInt(usrInput) == 1) {
          setPlayerComps(1);
          break;
        }
        else if (Integer.parseInt(usrInput) == 2) {
          setPlayerComps(2);
          break;
        }
        else if (Integer.parseInt(usrInput) == 3) {
          setPlayerComps(3);
          break;
        }
        else if (Integer.parseInt(usrInput) == 4) {
          setPlayerComps(4);
          break;
        }
        else if (Integer.parseInt(usrInput) >= 5) {
          setPlayerComps(5);
          break;
        }
        else {
          System.out.println("Invalid number! Please try again.");
          IOF.delay(3500);
        }
      } catch(Exception e) {
        System.out.println("Your input is invalid! Please try again.");
        IOF.delay(2000);
      }
    }
  }

  //The actual game itself.
  void Play() {

    //Starts with populating all player boards
    for (int i = 0; i < playerComps.size(); i++) {
      popBoard(playerComps.get(i).getBoard());
    }

    popBoard(usr.getBoard());

    //Sets up the dataset and the value "num" to represesnt the index of dataset
    shuffle();
    int num = 0;
    while (checkWin() == -1 && num < dataset.size()) {

      //Sets up a value to hold the value of index num and adds it to pulledNum
      int val = dataset.get(num);
      usr.addPull(val);

      //Every player makes a move and then num is incremented as the loop continues
      System.out.println("\033[H\033[2J");
      System.out.println(manager.displayBoard(usr.getBoard(), usr.getList()));
      if (usr.makeMove() == false) {
        return;
      }
      
      for (int i = 0; i < playerComps.size(); i++) {
        playerComps.get(i).addPull(val);
        playerComps.get(i).checkBoard(val);
      }

      
      num++;
    }

    //Clears boards once game is finished
    for (int i = 0; i < playerComps.size(); i++) {
      playerComps.get(i).clearPull();
    }

    usr.clearPull();

    //Checks who won and displays a message to the player accordingly
    if (checkWin() == 1) {
      usr.setGames(usr.getGames() + 1);
      usr.setWins(usr.getWins() + 1);
      System.out.println("\033[H\033[2J");
      System.out.println("Congratulations! You got a Bingo!");
      IOF.delay(3500);
    }
    else {
      usr.setGames(usr.getGames() + 1);
      System.out.println("\033[H\033[2J");
      System.out.println("Awwww... You didn't get a Bingo before someone else. Better luck next time!");
      IOF.delay(3500);
    }
  }

  //The next four methods determine specific win conditions following horizontal, vertical, diagonal and blackout wins.
  private boolean checkRows(int[][] board) {
    for (int o = 0; o < 5; o++) {
        int count = 0;
        for (int i = 0; i < 5; i++) {
          if (board[o][i] == 00) {
            count++;
          }
          if (count == 5) {
            return true;
          }
        }
      }
    return false;
  }

  private boolean checkCols(int[][] board) {
    for (int i = 0; i < 5; i++) {
        int count = 0;
        for (int o = 0; o < 5; o++) {
          if (board[o][i] == 00) {
            count++;
          }
          if (count == 5) {
            return true;
          }
        }
      }
    return false;
  }

  private boolean checkDiags(int[][] board) {
    if (board[0][0] == 00 && board[1][1] == 00 && board[2][2] == 00 && board[3][3] == 00 && board[4][4] == 00) {
      return true;
    }
    if (board[0][4] == 00 && board[1][3] == 00 && board[2][2] == 00 && board[3][1] == 00 && board[4][0] == 00) {
      return true;
    }

    return false;
    
  }

  private boolean checkBlack(int[][] board) {
    for (int i = 0; i < 5; i++) {
        for (int o = 0; o < 5; o++) {
          if (board[o][i] != 00) {
            return false;
          }
        }
      }
    return true;
  }

  //The following two methods put all win conditions into a conditional that determines whether a player or computer met the conditions. 
  private boolean winCond(String gamemode, int[][] board) {
    if (gamemode.equals("Classic")) {
      if (checkRows(board) == true || checkCols(board) == true || checkDiags(board) == true) {
        return true;
      }
      else {
        return false;
      }
    }
    else if (gamemode.equals("Diags")) {
      if (checkDiags(board) == true) {
        return true;
      }
      else {
        return false;
      }
    }
    else if (gamemode.equals("Straights")) {
      if (checkRows(board) == true || checkCols(board) == true) {
        return true;
      }
      else {
        return false;
      }
    }
    else if (gamemode.equals("Blackout")) {
      if (checkBlack(board) == true) {
        return true;
      }
      else {
        return false;
      }
    }
    return false;
  }
  
  private int checkWin() {
    if (winCond(gamemode, usr.getBoard())) {
      return 1;
    }

    for (int i = 0; i < playerComps.size(); i++) {
      if (winCond(gamemode, playerComps.get(i).getBoard())) {
        return 2;
      }
    }

    return -1;
  }

  //Populates the game boards with values between 1 and 50.
  private void popBoard(int[][] board) {
    shuffle();
    for (int o = 0; o < 5; o++) {
      for (int i = 0; i < 5; i++) {
        board[o][i] = dataset.get(i + (o * 5));
      }
    }
  }

  //Populates dataset.
  private void popList() {
    for (int i = 0; i < 50; i++) {
      dataset.add(i + 1);
    }
  }

  //Shuffles dataset, making it entirely random.
  private void shuffle() {
    for (int i = 0; i < dataset.size(); i++) {
      int randIndex = (int)(Math.random() * (dataset.size()));
      int temp = dataset.get(i);
      dataset.set(i, dataset.get(randIndex));
      dataset.set(randIndex, temp);
    }
  }

  //Instantiates a set number of computers and puts them into the playerComps list.
  private void setPlayerComps(int num) {
    Comp computer = new Comp();
    for (int i = 0; i < num; i++) {
      playerComps.add(computer);
    }
  }

  

  

  

  
}
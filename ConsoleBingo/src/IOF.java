import java.io.*;
import java.nio.file.*;
import java.util.*;

class IOF {
  Scanner input = new Scanner(System.in);
  List<String> lines = getLines();

  //Constructor prompts the usr and searches for them
  IOF(Player usr) {
    System.out.println("Please enter your name.\n");
    String usrInput = input.nextLine();

    if (usrInput.isEmpty()) {
      retrieveStats("Null", usr);
    }
    else {
      retrieveStats(usrInput, usr);
    }
  }

  static void delay(int ms) {
    try {
       Thread.sleep(ms); 
    } catch (InterruptedException ex) {
      Thread.currentThread().interrupt();
    }
  }

  //Turns every line inside of stats.txt into a string on an arraylist
  private List<String> getLines() {
    try {
      return Files.readAllLines(Paths.get("ConsoleBingo/data/stats.txt"));
    }catch (IOException ex) {
      System.out.println("ERROR!\n" + ex);
      return null;
    }
  }

  //Writes all strings in the arraylist into the stats.txt file
  private void writeLines() {
    try {
     Files.write(Paths.get("ConsoleBingo/data/stats.txt"), lines);
    }catch(Exception ex) {
      System.out.println("ERROR!\n" + ex);
      return;
    }
  }

  //Manipulates the arraylist with the file data
  void editStats(Player usr) {
    for (int i = 0; i < lines.size(); i++) {
      if (lines.get(i).contains(usr.getName())) {
        lines.get(i).replace(lines.get(i).substring(lines.get(i).indexOf(":") + 2), usr.getName());
        lines.get(i).replace(lines.get(i + 1).substring(lines.get(i + 1).indexOf(":") + 2), Integer.toString(usr.getGames()));
       lines.get(i).replace(lines.get(i + 2).substring(lines.get(i + 2).indexOf(":") + 2), Integer.toString(usr.getWins()));
        writeLines();
        return;
      }
    }
    addStats(usr);
  }

  //Adds a new set of data to the arraylist if no existing usr is present
  void addStats(Player usr) {
    String name = "Name: " + usr.getName();
    String games = "Games: " + usr.getGames();
    String wins = "Wins: " + usr.getWins();
    lines.add(name);
    lines.add(games);
    lines.add(wins);
    lines.add("----------------------------------------------------------------------------------------------------");
    writeLines();
  }

  //Finds and retrieves stats of an existing usr
  void retrieveStats(String name, Player usr) {
    try {
      for (int i = 0; i < lines.size(); i++) {
        if (lines.get(i).contains(name)) {
          usr.setName(name);
          usr.setGames(Integer.parseInt(lines.get(i + 1).substring(lines.get(i + 1).indexOf(":") + 2)));
          usr.setWins(Integer.parseInt(lines.get(i + 2).substring(lines.get(i + 2).indexOf(":") + 2)));
        }
      }
      usr.setName(name);
    } catch(Exception e) {
      usr.setName(name);
    }
    
  }

  //Displays the list "pulledNums" from a player board
  String displayList(ArrayList<Integer> list) {
    String printed = "[";
    for (int i = 0; i < list.size(); i++) {
      if (i + 1 == list.size()) {
        printed += "\u001B[32m" + list.get(i) + "\u001B[0m";
      }
      else {
        printed += "\u001B[32m" + list.get(i) + "\u001B[0m,";
      }
    }
    printed += "]";
    return printed;
  }

  //Displays a player board
  String displayBoard(int[][] board, ArrayList<Integer> list) {
    String printed = "Pulled Value: " + displayList(list) + "\n\n\n\u001B[36m--------------------------";


    for (int o = 0; o < 5; o++) {
      printed += "\n| \u001B[0m";
      for (int i = 0; i < 5; i++) {
        String num = String.format("%02d", board[o][i]);
        
        if (board[o][i] == 00) {
          printed += "\u001B[33m" + num + "\u001B[36m | \u001B[0m";
        }
        else {
          printed += num + "\u001B[36m | \u001B[0m";
        }
      }
      printed += "\n\u001B[36m--------------------------";
    }

    printed += "\n\u001B[0m";
    return printed;
  }
  
}
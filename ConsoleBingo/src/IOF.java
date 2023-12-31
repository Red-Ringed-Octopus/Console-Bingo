import java.io.*;
import java.nio.file.*;
import java.util.*;

class IOF {
  Scanner input = new Scanner(System.in);
  List<String> lines = getLines();

  //Constructor prompts the usr and searches for them
  IOF(Player usr) {
	enableAnsi();
	System.out.println(clearConsole());
    System.out.println("Welcome to Console Bingo!\n");
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
  
  //Enables ANSI codes on Windows CMD
  static void enableAnsi() {
	    try {
	        String os = System.getProperty("os.name").toLowerCase();
	        if (os.contains("win")) {
	            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "reg", "add", "HKCU\\Console", "/v", "VirtualTerminalLevel", "/t", "REG_DWORD", "/d", "1", "/f");
	            builder.inheritIO().start().waitFor();
	        }
	    } catch (Exception e) {
	        System.out.println("ERROR!\n" + e);
	    }
	}
  
  //Clears console
  static String clearConsole() {
	  return "\033[H\033[2J";
  }

  
  //Sets text color
  static String setColor(char c) {
	  if (c == 'r') {
		  return "\u001B[31m";
	  }
	  else if (c == 'g') {
		  return "\u001B[32m";
	  }
	  else if (c == 'b') {
		  return "\u001B[34m";
	  }
	  else if (c == 'c') {
		  return "\u001B[36m";
	  }
	  else if (c == 'y') {
		  return "\u001B[33m";
	  }
	  else if (c == 'p') {
		  return "\u001B[35m";
	  }
	  else if (c == 'w') {
		  return "\u001B[37m";
	  }
	  else {
		  return "\u001B[0m";
	  }
  }

  //Displays the list "pulledNums" from a player board
  String displayList(ArrayList<Integer> list) {
    String printed = "[";
    for (int i = 0; i < list.size(); i++) {
      if (i + 1 == list.size()) {
        printed += setColor('g') + list.get(i) + setColor('-');
      }
      else {
        printed += setColor('g') + list.get(i) + setColor('-') + ",";
      }
    }
    printed += "]";
    return printed;
  }

  //Displays a player board
  String displayBoard(int[][] board, ArrayList<Integer> list) {
    String printed = "Pulled Value: " + displayList(list) + "\n\n\n" + setColor('b') + "--------------------------";


    for (int o = 0; o < 5; o++) {
      printed += "\n| " + setColor('-');
      for (int i = 0; i < 5; i++) {
        String num = String.format("%02d", board[o][i]);
        
        if (board[o][i] == 00) {
          printed += setColor('y') + num + setColor('b') +  " | " + setColor('-');
        }
        else {
          printed += num + setColor('b') + " | " + setColor('-');
        }
      }
      printed += "\n" + setColor('b') + "--------------------------";
    }

    printed += "\n" + setColor('-');
    return printed;
  }
  
}
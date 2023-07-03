# Console Bingo
## A console-based bingo game with a user system.

Console Bingo is as the name says, a bingo game within the environment of a console. 
The game uses a simple colored display and basic graphics to provide visual engagement to the user.
Console Bingo also has a very simplistic user statistical system that utilizes a text file to write new users and retrieve old users and their stats.

## How to Install and Play
To download, simply click on the blue button labeled "<> Code" and downlaod as ZIP. 
Once downloaded, extract the ZIP file and open the application "CBE" and the game will be ready to play.<br>

Once open, every option can be typed in as lower case, the exact same as seen on console or by the corresponding number to the left.
If there are no options, then you can type freely (an example being a name) and press ENTER when finished.

### Note*
You will need to install a Java Development Kit of at least version 19 or higher since that was what version it was developed on. 
The download to the latest JDK will be in this <a href="https://www.oracle.com/java/technologies/downloads/#java8"> link</a>.
If the program still doesn't work, check your environment variables to see if "Path" has the location of the JDK and add it if it doesn't.

## How it Works
Console Bingo uses primarily four java files and a text file for storage. These files consist of:<br>
* <b>Main -></b>	The file where everything is executed, though the legwork is mostly done by Game.

* <b>Game -></b>	The location where all prompts and gameplay exist. 
It creates all computer objects, the player object, and shuffles the boards.

* <b>Comp -></b>	Comp is the file that contains the parent class Comp (Computer) and the child class Player. 
Both classes have a name, method to check their board for matches. Player however, also has added stats that the player will carry until the end of the program.
These stats can be saved in the Stats text file if the user selects the option in the main menu.

* <b>IOF -></b>		IOF stands for Input, Output, File. IOF is what actually prints the boards and datasets during the course of the game.
IOF also checks the user at the start of the program to see if they exist inside the Stats file and will allocate another section of the file for new users.
On top of that, IOF contains some other utility methods to change font color and set delays.

* <b>Stats -></b> Stats is a text file that will contain all players that choose to save their stats before or after a game.

## Known Issues
Some issues I've noticed while either developing or finalizing were:

* The executable must stay inside of the Console-Bingo folder
* You cannot back out of a game once it has started, you must close out in order to exit
* Coordinates are a little vague in terms of how X and Y or situatied
* Only the game screen is colored, all other menus are plain white
* The program so far only works on Windows machines

If you see any issues I did not list, you can create a new issue on the GitHub page or contact me at danzerrro@gmail.com.


 

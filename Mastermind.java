/***************************************************************************************************************************
 
	Master mind is a code-breaking game for two players.
	
	- Before a game begins, a player chooses the number of colours and number of pegs to play with.
	- From here, player one will select a code (combination of colours).
	- Player two must guess this code within a set number of attempts to win the game.
	- After each guess, feedback is given to player two in the form of black and white pegs.
	- A black peg indicates that the current peg is correct in both colour and position.
	- A white peg indicates that the current peg is correct in colour and but not position.
	- If player two fails to guess the code within the guess limit then player one is the winner.
	
	 * @author cjd47 - candidate number: 18181
	 * @date 05/01/2016
	 * @version 1.0

 ***************************************************************************************************************************/

// import the libraries necessary for the Master mind class to function
// import readers for both the input stream and from files
import java.io. BufferedReader; 
import java.io.InputStreamReader;
import java.io. FileReader;

//import library handling file objects and possible errors that could arise with input stream data
import java.io. File; 
import java.io. FileNotFoundException;
import java.io. IOException;

/***************************************************************************************************************************
	  	- A main class which is called upon program startup.
	  	- This class handles the beginning of the program - checking if there is data to be read from the game file and taking
	  	the appropriate action based on this - starting a new game or continuing an old one.

 ***************************************************************************************************************************/
public class Mastermind
{
	
	// open a new buffered reader to read from the input stream
	// instantiate instances of the SaveLoad, Game Function and Text Interface classes for later use
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	SaveLoad currentSession = new SaveLoad(0, 0, 0, 0, 0, null, null, null, null);
	MastermindTextUI MTUI = new MastermindTextUI(currentSession);
	GameFunction currentGame = new GameFunction(currentSession);

	// initialise a boolean diagnostic check to see if a game is being played, if the user wants to play again,
	// if the execution was successful and whether it is the start of a new game.
	// and a string to store the contents of the current line read using the Buffered Reader
	boolean gameInProgress = false;
	boolean playAgain = false;
	boolean successfulExecute = false;
	String currentLine; 
	boolean startOfGame = true;
	
	/***************************************************************************************************************************
	 	- Main method calls the Game Function class after determining whether a game is currently being played. 
	 	- Handles the possibility of starting a new game or continuing an old one.
	 	- Ensures that if a game finishes, there is the option to play again - leaving the option of unlimited games as long as 
	 	the user selects to play again.
	
	@throws IOException
	@throws StackOverflowError
	 ***************************************************************************************************************************/
	public static void main(String[] args) throws IOException, StackOverflowError
	{
		// create new instance of the Mastermind class
		// run a welcome message on program startup through the text interface
		Mastermind myMastermind = new Mastermind();
		
			myMastermind.MTUI.clearScreen();
			myMastermind.MTUI.welcomeMessage();
			
			// check if an existing game is in progress by checking the contents of the current game file
				if(myMastermind.isGameInProgress() == true) 
				{
					do
					{
						try
						{
							// if so, ask whether user wants to reload the stored game
							// get user's input
							myMastermind.MTUI.gameInProgressMessage(); 
							myMastermind.currentLine = myMastermind.MTUI.userInput(); 
							
							// check if user's input is a quit command
							myMastermind.checkForQuitCommand(myMastermind.currentLine);
							
							// if the user says yes, continue the game by calling the continueGame method from the Game Function class
							if((myMastermind.currentLine.toUpperCase()).equals("Y") || (myMastermind.currentLine.toUpperCase()).equals("YES")) 
							{
									// a saved game will be loaded and continued from the point it was left off
									myMastermind.successfulExecute = true;
									myMastermind.currentGame.continueGame(myMastermind.startOfGame);
									myMastermind.playAgainCheck(); // ask the user if they want to play the game again
									if(myMastermind.playAgain == false) // if not, break the loop
									{
										myMastermind.currentLine = "y";
										break;
									}
									else if(myMastermind.playAgain == true)
									{
										myMastermind.currentLine = "n"; // if so, set the current line to no so that they may start a new game
									}
							}
							// if the user says no, start a new game by calling the startNewGame method from the Game Function class
							if((myMastermind.currentLine.toUpperCase()).equals("N") || (myMastermind.currentLine.toUpperCase()).equals("NO"))
							{
								do // repeatedly run the game loop until the user specifies that they don't want to play again
								{
									myMastermind.successfulExecute = true;
									myMastermind.currentGame.initialiseAll(myMastermind.currentSession);
									myMastermind.currentGame.startNewGame();
									myMastermind.playAgainCheck(); // ask the user if they want to play the game again
									if(myMastermind.playAgain == false) // if not, break the loop
									{
										break;
									}
								} while(myMastermind.playAgain);
								
							}
							
						}
						catch(NumberFormatException NFE)
						{
						}
					} while(!myMastermind.successfulExecute);
					myMastermind.successfulExecute = false;
				}
				else // if there is not a current game saved, start a new game as normal
				{
					do // repeatedly run the game loop until the user specifies that they don't want to play again
					{
						myMastermind.currentGame.initialiseAll(myMastermind.currentSession);
						myMastermind.currentGame.startNewGame();
						myMastermind.playAgainCheck(); // ask the user if they want to play the game again
						if(myMastermind.playAgain == false) // if not, break the loop
						{
							break;
						}
					} while(myMastermind.playAgain);
				}
		}
	
	/***************************************************************************************************************************
		- A method which returns a boolean diagnostic to check whether there is a game currently saved (in progress).
		- The method uses a FileReader object to read the savedGame.txt file and examine whether it contains data.
		- The BufferedReader object reads the current line and checks if it is null. 
		- If so, it passes the gameInProgress boolean variable back to the main method as 'false'.
		- Otherwise, if the current line contains data, it passes the gameInProgress boolean variable back to the main method 
		as 'true'.
		
	@throws IOException
	@throws FileNotFoundException
	@return gameInProgress
	 ***************************************************************************************************************************/
	
	public boolean isGameInProgress() throws IOException, FileNotFoundException
	{
		// define the File for the FileReader to read from
		File savedGame = new File ("savedGame.txt");
		// create a new FileReader to read from the defined File
		FileReader fr = new FileReader (savedGame);
		// create BufferedReader to read buffered blocks of text from the FileReader
		BufferedReader br = new BufferedReader(fr);

		// if the current line in the saved game file is empty, return the diagnostic as false - no game data is saved
		if((currentLine=br.readLine())==null)
		{
			gameInProgress = false;
			br.close();
			return gameInProgress;
		}
		else // else if the current line in the saved game file is occupied, return the diagnostic as true - game data is saved
		{
			gameInProgress = true;
			br.close();
			return gameInProgress;
		}
	}
	
	/***************************************************************************************************************************
		- A method which runs at the end of a game loop to check if the user wants to play the game again.
		- If the user selects "Y" or "YES", the playAgain boolean variable is set to true.
		- If the user selects "N" or "NO", the playAgain boolean variable is set to false.
		- The playAgain variable is accessed from the main method after this playAgainCheck() method is called.
		- If playAgain is true, the game loop repeats.
		- Otherwise if it is false, the loop terminates and the game is finished.
	
	 ***************************************************************************************************************************/
	public void playAgainCheck()
	{
		do // repeat this whilst the user hasn't entered a valid input
		{
			// ask the user whether they would like to play the game again
			// give the user the choice of a "Y" or "N" response
			
			MTUI.playAgainMessage();
			
			// get the user's input
			String currentLine = MTUI.userInput();
			
			// if the user says yes, set the boolean playAgain and successfulExecute variables to true and return
			if((currentLine.toUpperCase()).equals("Y") || (currentLine.toUpperCase()).equals("YES")) 
			{
				playAgain = true;
				successfulExecute = true;
			}
			
			// otherwise, if the user says no, set the boolean playAgain and successfulExecute variables to false and true, and return
			else if((currentLine.toUpperCase()).equals("N") || (currentLine.toUpperCase()).equals("NO"))
			{
				playAgain = false;
				successfulExecute = true;
				MTUI.closingMessage();
			}
			
			// if the input is neither yes or no, set the boolean successfulExecute variable to false
			else
			{
				successfulExecute = false;
			}
			
		} while(!successfulExecute);
		
		successfulExecute = false;
	}
	
	/***************************************************************************************************************************
		- This method checks to see if the user enters "Q" or "QUIT" when asked if they want to load an existing game.
		- If so, then a message is displayed and the program exits gracefully.
	
	 @throws FileNotFoundException 
	 @throws IOException 
	
	 ***************************************************************************************************************************/
	public void checkForQuitCommand(String input) throws FileNotFoundException, IOException
	{
		// if the user selects to quit the game by typing "quit" or "q" (case insensitive)
		// print a quit message
		// print a closing message and exit the program gracefully
		
		if(input.toUpperCase().equals("QUIT") || input.toUpperCase().equals("Q"))
		{
			MTUI.screenOutput("Exiting game...");
			System.out.println();
			MTUI.closingMessage();
			System.exit(0);
		}
		else
		{
			return;
		}
	}

}
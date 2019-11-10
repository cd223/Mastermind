// import the libraries necessary for handling the file not being found and I/O error handling
import java.io.FileNotFoundException;
import java.io.IOException;

// import the libraries necessary for reading user input
import java.io.InputStreamReader;
import java.util.Scanner;

/***************************************************************************************************************************
	 - A human is a type of player of the game and hence inherits methods and behaviours from the player class
	 - It is necessary for when a human needs to make a guess or choose a code to play with.
	 - This is because the methods required for human functionality and CPU functionality are different.
	 - Different methods (chooseCode and makeGuess) perform the same task in different ways to the CPU equivalent, hence the CPU class extends the 
	abstract class Player to perform these methods in its own way.

@extends Player -- 
 ***************************************************************************************************************************/
public class Human extends Player
{
	// declare all variables that the game requires when it saves game data
	// these will be assigned a value by the parameters passed in when a new human guess is required
	int guessNum;
	int guessLimit;
	int numColours;
	int numPegs;
	int AImode;
	String chosenCode;
	String[][] allGuessesArray;
	int [][] allKeyPegsArray;
	
	// instantiate strings to store the user input (colours) and equivalent code of numbers that correspond to these colours
	String typedColour;
	String numberEquiv;
	
	// instantiate counters to store the number of pegs and distinct colours used in the code or guess
	int pegCounter;
	int distinctColourCounter;
	boolean isLoadedGame;
	
	// set up a new scanner object that can read from the user's input
	Scanner myScanner = new Scanner(new InputStreamReader(System.in));
	
	/***************************************************************************************************************************
		- A constructor method which has the objects and attributes from the super class (Player) passed to it.
	
	@param MTUI -- the text interface object 
	@param currentSession -- the saved game session
	@param numColours -- the number of colours in the code
	@param numPegs -- the number of pegs used in the code
	 ***************************************************************************************************************************/
	
	public Human(MastermindTextUI MTUI, SaveLoad currentSession, int numColours, int numPegs)
	{
		super(MTUI, currentSession, numColours, numPegs); // pass these parameters in from the super class
	}
	
	/***************************************************************************************************************************
		- A method which allows a human to choose a code at the start of a game for another human or the CPU to break.
	 	- An input is taken in the form of words separated by spaces (i.e: "red black orange red") or new lines.
	 	- Any invalid colour entered is dismissed and not counted as a part of any input. In other words, for a game using 3 
	 	colours, the indexes 0-2 inclusive are used, so if a valid colour is entered which has an index higher than 2, it will
	 	be dismissed and the user will be asked to type a different colour from the accepted list (printed on screen to
	 	make it easier to remember).
	 	- An input containing too many colours for the guess is dismissed, and the input is guarded, so as soon as a valid 
		number of allowed colours is chosen (a number equal to the number of pegs), the input stream stops and code is accepted.
		- These validations guard against potential errors.
		- The chosenCode string is eventually concatenated with the indexes of the chosen colours before being passed to 
		the game loop.
		
	 @param numColours -- the number of colours in the code
	 @param numPegs -- the number of pegs used in the code
	 @param guessNum -- the current guess number
	 @param guessLimit -- the limit on the number of guesses
	 @param AImode -- the current game mode
	 @param allGuessesArray -- all of the guesses so far
	 @param allKeyPegsArray -- all of the feedback pegs placed so far
	 
	 @throws FileNotFoundException 
	 @throws IOException 
	 
	 @return chosenCode -- return a String variable storing the created code 
	 ***************************************************************************************************************************/
	public String chooseCode(int numColours, int numPegs, int guessNum, int guessLimit, int AImode, String[][] allGuessesArray, int[][] allKeyPegsArray) throws FileNotFoundException, IOException 
	{
		// initialise the chosen code, user input and number equivalent to an empty string and counters to 0
		String chosenCode = ""; 
		typedColour = "";
		numberEquiv = "";
		pegCounter = 0;
		distinctColourCounter = 0;
		
		// set a boolean diagnostic to ensure that the code is generated successfully
		boolean successfulExecute = false;
		
		// set the public variables to equal the corresponding values passed in using parameters
		this.numColours = numColours;
		this.numPegs = numPegs;
		this.guessNum = guessNum;
		this.guessLimit = guessLimit;
		this.AImode = AImode;
		this.chosenCode = chosenCode;
		
		do // whilst the input isn't valid
		{
			// whilst there is still input to be read and the peg counter doesn't exceed the limit
			while(myScanner.hasNext() && pegCounter < numPegs) 
			{
				if(pegCounter < numPegs)
				{
						// get the user's input from the text interface
						typedColour = myScanner.next();
						
						// if this is a valid input, convert the number returned from the colourToNumber function into a string
						if(MTUI.colourToNumber(typedColour) != -1)
						{
							// if the colour is an invalid choice (exceeds the maximum colour index)
							if(MTUI.colourToNumber(typedColour) > numColours -1)
							{
								MTUI.screenOutput("This colour is not permitted."); // print an error message
							}
							
							// otherwise, if the colour is an valid choice (doesn't exceed the maximum colour index)
							else
							{
								// convert the input to a number
								numberEquiv = Integer.toString(MTUI.colourToNumber(typedColour)); 
								
								// if the code doesn't already contain this index, increment the distinct colour counter 
								if(!chosenCode.contains(numberEquiv))
								{
									distinctColourCounter++;
								}
							
								// concatenate the existing chosenCode string with the converted colour's index
								if(distinctColourCounter<=numColours)
								{
									chosenCode = chosenCode + numberEquiv;
									pegCounter++; // increment the peg counter
									
									// if the peg counter exceeds the limit, the code has been generated so break the loop
									if(pegCounter >= numPegs)
									{
										successfulExecute = true;
										break;
									}
								}
								else if(distinctColourCounter>numColours) // otherwise, let the user know too many different colours have been entered in the guess
								{
									System.out.println("Too many different colours have been entered in the code. The limit of distinct colours is " + numColours);
									pegCounter = 0;
									distinctColourCounter = 0;
									chosenCode = "";
									successfulExecute = false;
									break;
								}
							}
						}
					}
				// if the peg counter exceeds the limit, the code has been generated so break the loop
					if(pegCounter >= numPegs)
					{
						successfulExecute = true;
						break;
					}
				}
		} while(!successfulExecute);
		
		this.chosenCode = chosenCode; // set the public variable to equal the new code just set
		
		return chosenCode; // return the string of digits chosen as the code to the GameFunction class
	}
	
	/***************************************************************************************************************************
		- A method which allows a human to make a guess during a game for another human or the CPU to break.
	 	- An input is taken in the form of words separated by spaces (i.e: "red black orange red") or new lines.
	 	- Any invalid colour entered is dismissed and not counted as a part of any input. In other words, for a game using 3 
	 	colours, the indexes 0-2 inclusive are used, so if a valid colour is entered which has an index higher than 2, it will
	 	be dismissed and the user will be asked to type a different colour from the accepted list (printed on screen to
	 	make it easier to remember).
	 	- An input containing too many colours for the guess is dismissed, and the input is guarded, so as soon as a valid 
		number of allowed colours is chosen (a number equal to the number of pegs), the input stream stops and code is accepted.
		- These validations guard against potential errors.
		- The chosenGuess string is eventually concatenated with the indexes of the chosen colours before being passed to 
		the game loop.
		
	 @param numColours -- the number of colours in the code
	 @param numPegs -- the number of pegs used in the code
	 @param guessNum -- the current guess number
	 @param guessLimit -- the limit on the number of guesses
	 @param AImode -- the current game mode
	 @param allGuessesArray -- all of the guesses so far
	 @param allKeyPegsArray -- all of the feedback pegs placed so far
	 
	 @throws FileNotFoundException 
	 @throws IOException 
	 
	 @return chosenGuess -- return a String variable storing the created guess
	 ***************************************************************************************************************************/
	public String makeGuess(int numColours, int numPegs, int guessNum, int guessLimit, int AImode, String chosenCode, String prevChosenGuess, int blackKeyPegCount, int whiteKeyPegCount, String[][] allGuessesArray, int[][] allKeyPegsArray) throws FileNotFoundException, IOException
	{	
		// initialise the chosen code, user input and number equivalent to an empty string and counters to 0
		String chosenGuess = ""; 
		typedColour = "";
		numberEquiv = "";
		pegCounter = 0;
		distinctColourCounter = 0;
		
		// set a boolean diagnostic to ensure that the code is generated successfully
		boolean successfulExecute = false;
				
		// set the public variables to equal the corresponding values passed in using parameters
		this.numColours = numColours;
		this.numPegs = numPegs;
		this.guessNum = guessNum;
		this.guessLimit = guessLimit;
		this.AImode = AImode;
		this.chosenCode = chosenCode;
		
		// fix the dimensions of the public 2D arrays to be bound by the guess limit and number of pegs parameters
		this.allGuessesArray = new String[guessLimit][numPegs];
		this.allKeyPegsArray = new int[guessLimit][numPegs];
		
		// set each element in each array to equal the element at the corresponding position in the parameter array
		for (int i = 0; i < guessNum; i++)
		{
			for(int j=0; j< numPegs; j++)
			{
				this.allGuessesArray[i][j] = allGuessesArray[i][j];
				this.allKeyPegsArray[i][j] = allKeyPegsArray[i][j];
			}
		}
		
		do // whilst the input isn't valid
		{
			// whilst there is still input to be read and the peg counter doesn't exceed the limit
			while(myScanner.hasNext() && pegCounter < numPegs) 
			{
				if(pegCounter < numPegs)
				{
						// get the user's input from the text interface
						typedColour = myScanner.next();
						
						// if it is the second guess or higher, check if the command is:
						//		"restart" or "r"
						// 		"quit" or "q"
						//		"save" or "s"
						if(guessNum>0)
						{
							if(checkForSaveQuitRestartCommand(typedColour))
							{
								// if check comes back true, return the code as "Restart" to restart game
								return "Restart"; 
							}
						}
					
						// if this is a valid input, convert the number returned from the colourToNumber function into a string
						if(MTUI.colourToNumber(typedColour) != -1)
						{
							// if the colour is an invalid choice (exceeds the maximum colour index)
							if(MTUI.colourToNumber(typedColour) > numColours -1)
							{
								MTUI.screenOutput("This colour is not permitted."); // print an error message
							}
							// otherwise, if the colour is an valid choice (doesn't exceed the maximum colour index)
							else
							{
								// convert the input to a number
								numberEquiv = Integer.toString(MTUI.colourToNumber(typedColour)); 
								
								// if the code doesn't already contain this index, increment the distinct colour counter 
								if(!chosenCode.contains(numberEquiv))
								{
									distinctColourCounter++;
								}
							
								// concatenate the existing chosenGuess string with the converted colour's index
								if(distinctColourCounter<=numColours)
								{
									chosenGuess = chosenGuess + numberEquiv;
									pegCounter++; // increment the peg counter
									
									// if the peg counter exceeds the limit, the guess has been generated so break the loop
									if(pegCounter >= numPegs)
									{
										successfulExecute = true;
										break;
									}
								}
								else if(distinctColourCounter>numColours) // otherwise, let the user know too many different colours have been entered in the guess
								{
									System.out.println("Too many different colours have been entered in the code. The limit of distinct colours is " + numColours);
									pegCounter = 0;
									distinctColourCounter = 0;
									chosenGuess = "";
									successfulExecute = false;
									break;
								}
							}
						}
					}
				// if the peg counter exceeds the limit, the guess has been generated so break the loop
					else if(pegCounter >= numPegs)
					{
						successfulExecute = true;
						break;
					}
				}
		} while(!successfulExecute);
		
		// set each element in each array to equal the element at the corresponding position in the parameter array
		for (int i = 0; i < guessNum; i++)
		{
			for(int j=0; j< numPegs; j++)
			{
				this.allGuessesArray[i][j] = allGuessesArray[i][j];
				this.allKeyPegsArray[i][j] = allKeyPegsArray[i][j];
			}
		}
		
		return chosenGuess; // return the string of digits chosen as the guess to the GameFunction class
		
	}
	
	/***************************************************************************************************************************
		- A method which checks if the command entered is a Save, Quit or Restart.
		- If the word entered is equal to any of these words, or even the letters S, Q or R, the appropriate action is taken.
		- The Save command saves the game in its current state and returns to the previous method (usually to continue 
		taking input). The game is saved using updated game data.
		- The Quit command Saves the game in the same way as above, but instead exits the game gracefully once saving is successful.
		- The Restart command returns true whereas the others return false.
		- This is so that the input can be checked for a restart and handled carefully, returning the user to the start of the
		game by slowly unwinding nested subroutines.
		- If a user decides to restart a game, then the current saved game file is cleared.

	@param input -- a string that holds the user's input
	@throws FileNotFoundException
	@throws IOException
	
	@return boolean -- if true then restart command has been selected
	 ***************************************************************************************************************************/
	
	public boolean checkForSaveQuitRestartCommand(String input) throws FileNotFoundException, IOException
	{
			// if the user selects to save the file by typing "save" or "s" (case insensitive)
			// print a save message, save the game using updated variables
			// print a confirmation message
		if(input.toUpperCase().equals("SAVE") || input.toUpperCase().equals("S"))
		{
				MTUI.screenOutput("Saving game...");
				currentSession.saveGame(guessNum, guessLimit, numColours, numPegs, AImode, chosenCode, allGuessesArray, allKeyPegsArray);
				MTUI.screenOutput("Saved!");
			return false;
		}
		
			// if the user selects to quit the game by typing "quit" or "q" (case insensitive)
			// print a save and quit message, save the game using updated variables
			// print a closing message and exit the program gracefully
		else if(input.toUpperCase().equals("QUIT") || input.toUpperCase().equals("Q"))
		{
				MTUI.screenOutput("Saving and exiting game...");
				currentSession.saveGame(guessNum, guessLimit, numColours, numPegs, AImode, chosenCode, allGuessesArray, allKeyPegsArray);
				MTUI.screenOutput("Saved!");
				System.out.println();
				MTUI.closingMessage();
			System.exit(0);
			return false;
		}
		
			// if the user selects to restart the program by typing "restart" or "r" (case insensitive)
			// print a restart message, clear the game using the clearGame() method
			// return true
		else if(input.toUpperCase().equals("RESTART") || input.toUpperCase().equals("R"))
		{
			MTUI.screenOutput("Restarting game...");
			currentSession.clearGame();
			return true;
		}
		else
		{
			return false;
		}
	}
	
}
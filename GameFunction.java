// import the libraries necessary for the file handling, input and output reading and array behaviours to work
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

/***************************************************************************************************************************
	- This class handles the game logic of the Mastermind game.
	- The game loops for new and existing games are handled here.
	- A number of methods exist in this class, from game configuration to the running of the game loops for new and existing
	games.

 ***************************************************************************************************************************/
public class GameFunction 
{

	// declare new human and CPU objects so that guesses can be made and codes can be chosen
	Human humanPlayer;
	CPU cpuPlayer;
	
	// declare a saved game session and instantiate the Text interface and game getting classes
	SaveLoad currentSession;
	MastermindTextUI MTUI = new MastermindTextUI(currentSession);
	GameSetting gameSetting = new GameSetting(MTUI, currentSession);
	
	// create strings to store the colour indexes of the guess and code
	String chosenCode;
	String chosenGuess;
	
	// create 2D arrays to store the guesses made and response to these guesses for all attempts
	String[][] allGuessesArray;
	int[][] allKeyPegsArray;
	
	// store the code and latest guess in 1D arrays for comparison
	String[] chosenCodeArray;
	String[] chosenGuessArray;
	
	// create arrays to store the positions of the pegs when presenting feedback to a guess
	int[] keyPegArray;
	int[] blackKeyPositions;
	int[] whiteKeyPositions;
	
	// declare variables to aid with the running of the game - number of attempts, colours, pegs, etc.
		int guessLimit;
		int guessNum;
		int numInput;
		int numColours;
		int numPegs;
		int AImode;
		
	// create counters to assist when giving peg feedback for guesses
		int blackKeyPegCount; 
		int whiteKeyPegCount;
		
	// declare variables for character analysis of the code and guess arrays
		String currentChar;
		int arraySize;
	
	// create boolean variables to aid with diagnostics from methods
		boolean successfulExecute; // aids with input validation
		boolean breakGameLoop; // 
		boolean isGameWon; // true if the code=guess or the guess number exceeds the limit
		boolean playAgain; // true if the user types 'y' or 'yes'
		boolean isLoadedGame; // true if game is loaded from a file
		boolean restartGame; // set to true if the user types 'restart'
		boolean isFirstLoad;
		
	// create variables responsible for storing ANSI sequences for colourful text output
		public static final String ANSI_RESET = "\u001B[0m";
		public static final String ANSI_BLACK = "\u001B[30;47;1m";
		public static final String ANSI_RED = "\u001B[31m";
		public static final String ANSI_ORANGE = "\u001B[31;1m";
		public static final String ANSI_YELLOW = "\u001B[33m";
		public static final String ANSI_GREEN = "\u001B[32m";
		public static final String ANSI_BLUE = "\u001B[34m";
		public static final String ANSI_PURPLE = "\u001B[35m";
		public static final String ANSI_WHITE = "\u001B[37m";
		public static final String ANSI_CYAN = "\u001B[36;45m";
		
		/***************************************************************************************************************************
			- A constructor method which calls upon a method that initialises all of the attributes and objects of the program 
			before a game loop starts.
			- Assigns default values to guess limit and AImode fields.
			- Creates new human player and CPU player objects to be called based on the AImode selected.
		
		@param currentSession -- the current saved game state
		 ***************************************************************************************************************************/
		public GameFunction(SaveLoad currentSession)
		{
			initialiseAll(currentSession); // calls a method which resets and instantiate all main variables
		}
		
		/***************************************************************************************************************************
			- A method whereby all game variables and objects are initialised before the start of a new game.
		
		@param currentSession -- the current saved game state
		 ***************************************************************************************************************************/
		public void initialiseAll(SaveLoad currentSession)
		{
			// sets a default value to each variable declared above
			this.chosenCode = "";
			this.chosenGuess = "";
			this.guessLimit = 15;
			this.guessNum = 0;
			this.numInput = 0;
			this.numColours = 0;
			this.numPegs = 0;
			this.blackKeyPegCount = 0;
			this.whiteKeyPegCount = 0;
			this.AImode = 1;
			this.currentChar = "";
			this.arraySize = 0;
			this.successfulExecute = false;
			this.breakGameLoop = false;
			this.isGameWon = false;
			this.playAgain = false;
			this.isLoadedGame = false;
			this.isFirstLoad = false;
			this.restartGame = false;
			this.currentSession = currentSession;
			this.humanPlayer = new Human(MTUI, currentSession, numColours, numPegs);
			this.cpuPlayer = new CPU(MTUI, currentSession, numColours, numPegs);
		}

		/***************************************************************************************************************************
			- A method which handles the game loop for a newly created game.
			- All variables are initialised and possible colours added to ensure the game functions properly.
			- The game mode is selected before any game takes place to ensure the correct game play mode is instantiated.
			- A number of guesses, number of colours and number of pegs is chosen by the user before the main game loop is entered.
			- The code is chosen by either the CPU or the human player depending on the game mode.
			- A game is first saved only after all initial configuration choices have been made.
			- The game loop is entered whereby whilst the guess number doesn't exceed the allowed limit, a guess is made by either
			 the CPU player or the human player dependent on AI mode.
			- Feedback is presented in the form of black and white pegs and then a check is made to see if the number of black pegs 
			equals the number of overall pegs chosen. If true, the code breaker has won. If the guess limit is exceeded then the code 
			maker has won.
			- Calls of the clear screen method allow for a cleaner textual interface whilst the updated game board is printed after 
			each guess.
		
		@throws FileNotFoundException
		@throws IOException
		 ***************************************************************************************************************************/
		public void startNewGame() throws FileNotFoundException, IOException
		{	
			// reset all variables back to their initial state
				initialiseAll(currentSession);
				
			// add all possible colours to the ArrayList
			// add their corresponding ANSI sequences for colourful text output
				MTUI.addAvailableColours();
				MTUI.addANSIColours();
				
			// choose the AI setting:
			//		1 = human code maker and human code breaker
			//		2 = CPU code maker and human code breaker
			//		3 = CPU code maker and CPU code breaker
			// 		4 = human code maker and CPU code breaker
				chooseGameSetting();
				
			// set the limit on the number of guesses allowed (between 8-30)
				chooseGuessLimit();
				
			// set the number of colours allowed (between 3-8)
				chooseNumberColours(); 
				
			// set the number of pegs allowed (between 3-8)
				chooseNumberPegs();
				
				System.out.println();
				
			// fix the dimensions of the all guesses and the all key pegs 2D arrays
			// 		number of rows = maximum number of guesses
			//		number of columns = number of pegs chosen
				allGuessesArray = new String[guessLimit][numPegs];
				allKeyPegsArray = new int[guessLimit][numPegs];
				
			// code maker chooses a code (human or CPU based on the AI game mode chosen)
				fetchCode();
				
			// if the restart game diagnostic variable is true, then return to the main Mastermind class
			// this exits the game loop and allows the user to start again
				if(restartGame)
				{
					return;
				}
				
			// clear the screen and return the cursor to the top left
			// this is so that the other player cannot see the code that has been set by their opponent
				clearScreen(); 
				
			// auto-save the game progress
				currentSession.saveGame(guessNum, guessLimit, numColours, numPegs, AImode, chosenCode, allGuessesArray, allKeyPegsArray); // save game data
				
				
			// whilst the current guess number doesn't exceed the limit of guesses allowed
				while(guessNum < guessLimit)
				{		
					// print the current guess number on the screen
						MTUI.screenOutput("Guess number: " + (guessNum+1));
						
					// code breaker makes a guess (human or CPU based on the AI game mode chosen)
						fetchGuess();
						
					// if the game mode allows a CPU code breaker, delay the time made in printing the guess
					// this is to emulate the pseudo-'thinking time' a guesser would make
					// improves the interface compared to simply printing a completed game board 'immediately'
						delayCheck();
						
					// if the restart game diagnostic variable is true, then return to the main Mastermind class
					// this exits the game loop and allows the user to start again
						if(restartGame)
						{
							return;
						}
						
					// get feedback on the current guess in the form of black and white response pegs
						checkKeyPegs(chosenCodeArray, chosenGuessArray, numPegs, chosenCode, chosenGuess);
						
					// auto-save the game progress
						currentSession.saveGame(guessNum, guessLimit, numColours, numPegs, AImode, chosenCode, allGuessesArray, allKeyPegsArray); // save game data
					
					// increment the current guess number
						guessNum++;
						System.out.println();
						
					// clear the screen and return the cursor to the top left
					// this is so that a clean game board can be printed
						clearScreen();
					
					// print the updated game board including response pegs
						MTUI.screenOutput("Current game board view:");
						MTUI.updatePrintedBoard(allGuessesArray, allKeyPegsArray, guessNum, numPegs);
						System.out.println();
						
						
					// if the chosen guess equals the code set, break the loop as the code breaker has won
					// if the code breaker wins, the game file is cleared and a message is printed
						if(checkForWinner())
						{
							break;
						}
						
					// if the guess number exceeds the limit and the game isn't already won,
					// the code maker wins, the game file is cleared and a message is printed
					// the actual code that the code breaker couldn't guess is revealed
						if(guessNum >= guessLimit && isGameWon == false)
						{
							currentSession.clearGame(); // empty file
							MTUI.codemakerWinMessage(guessLimit);
							MTUI.revealCode(chosenCodeArray);
							System.out.println();
						}
				}
		}
		
		/***************************************************************************************************************************
			- A game loop to cater for an existing game being loaded ready to be continued by the players.
			- The loop is entered after all configuration data has been pulled from the currentSession SaveLoad object.
			- The guess number, guess limit and other important data are extracted here to allow the game loop to pick up where it 
			was left off.
			- The game loop is entered whereby whilst the guess number doesn't exceed the allowed limit, a guess is made by either
			 the CPU player or the human player dependent on AI mode.
			- Feedback is presented in the form of black and white pegs and then a check is made to see if the number of black pegs 
			equals the number of overall pegs chosen. If true, the code breaker has won. If the guess limit is exceeded then the code 
			maker has won.
			- Calls of the clear screen method allow for a cleaner textual interface whilst the updated game board is printed after 
			each guess.
		
		@param startOfGame -- boolean check to ensure that the game file is loaded at the start
		@throws IOException
		 ***************************************************************************************************************************/
		public void continueGame(boolean startOfGame) throws IOException
		{
			// reset all variables back to their initial state	
				initialiseAll(currentSession);
			
			// add all possible colours to the ArrayList
			// add their corresponding ANSI sequences for colourful text output
				MTUI.addAvailableColours();
				MTUI.addANSIColours();
				
			// call the load game method which extracts data from the saved game file
				currentSession.loadGame();
				
			// set all of the major game variables to equal the result of corresponding getter (accessor) methods
				guessNum = currentSession.getGuessNum();
				guessLimit = currentSession.getGuessLimit();
				numColours = currentSession.getNumColours();
				numPegs = currentSession.getNumPegs();
				AImode = currentSession.getAImode();
				chosenCode = currentSession.getChosenCode();
				
			// fix the dimensions of the 1D arrays to the bounds of the number of pegs
			// // fix the dimensions of the 2D arrays to the bounds of the max guess number by the number of pegs
				chosenCodeArray = new String[numPegs];
				chosenGuessArray = new String[numPegs];
				allGuessesArray = new String[guessLimit][numPegs];
				allKeyPegsArray = new int[guessLimit][numPegs];
				
			// fill out the chosen code array with the contents of the corresponding stored Array
				for(int i=0;  i< numPegs; i++)
				{
					chosenCodeArray[i] = currentSession.getChosenCodeArray()[i];
				}
				
			// fill out the all guesses and all key pegs arrays with the contents of the corresponding stored Arrays
				for(int i=0;  i< guessNum; i++)
				{
					for(int j=0;  j< numPegs; j++)
					{
						allGuessesArray[i][j] = currentSession.getAllPrevGuessesArray()[i][j];
						allKeyPegsArray[i][j] = currentSession.getAllPrevKeyPegsArray()[i][j];
					}
				}
				
			// reset the chosen guess string to null and key peg counters to 0.
				chosenGuess = "";
				whiteKeyPegCount = 0;
				blackKeyPegCount = 0;
				
			// if it is the second guess or higher, there is 1 or more previous guesses to display
				if(guessNum>0)
				{	
					
				// fill the chosenGuess string and latest guess array with the latest guess data
					for(int i=0;  i< numPegs; i++)
					{
						chosenGuess += MTUI.colourToNumber(currentSession.getAllPrevGuessesArray()[guessNum-1][i]);
						chosenGuessArray[i] = Integer.toString(MTUI.colourToNumber(currentSession.getAllPrevGuessesArray()[guessNum-1][i]));
					}
				
				// iterate through through the previous guess' feedback pegs to count the number of black and white pegs
					for(int i=0;  i< numPegs; i++)
					{
						if(allKeyPegsArray[guessNum-1][i] == 1)
						{
							blackKeyPegCount++;
						}
						else if(allKeyPegsArray[guessNum-1][i] == 2)
						{
							whiteKeyPegCount++;
						}
					}
				}
				
			// clear the screen and return the cursor to the top left
			// this is so that a game board may be printed
				clearScreen();
				
			// if a guess has already been made, print the updated game board before a new guess is made
				if(guessNum>0)
				{
					MTUI.screenOutput("Current game board view:");
					MTUI.updatePrintedBoard(allGuessesArray, allKeyPegsArray, guessNum, numPegs);
				}
				
			// whilst the current guess number doesn't exceed the limit of guesses allowed
				while(guessNum < guessLimit)
				{	
					// print the current guess number on the screen
						MTUI.screenOutput("Guess number: " + (guessNum+1));
						
					// code breaker makes a guess (human or CPU based on the AI game mode chosen)
						fetchGuess();
						
					// if the game mode allows a CPU code breaker, delay the time made in printing the guess
					// this is to emulate the pseudo-'thinking time' a guesser would make
					// improves the interface compared to simply printing a completed game board 'immediately'
						delayCheck();
						
					// if the restart game diagnostic variable is true, then return to the main Mastermind class
					// this exits the game loop and allows the user to start again
						if(restartGame)
						{
							return;
						}
						
					// get feedback on the current guess in the form of black and white response pegs
						checkKeyPegs(chosenCodeArray, chosenGuessArray, numPegs, chosenCode, chosenGuess);
						
					// auto-save the game progress
						currentSession.saveGame(guessNum, guessLimit, numColours, numPegs, AImode, chosenCode, allGuessesArray, allKeyPegsArray); // save game data
					
					// increment the current guess number
						guessNum++;
						System.out.println();
						
					// clear the screen and return the cursor to the top left
					// this is so that a clean game board can be printed
						clearScreen();
						
						
					// print the updated game board including response pegs
						MTUI.screenOutput("Current game board view:");
						MTUI.updatePrintedBoard(allGuessesArray, allKeyPegsArray, guessNum, numPegs);
						System.out.println();
						
						
					// if the chosen guess equals the code set, break the loop as the code breaker has won
					// if the code breaker wins, the game file is cleared and a message is printed
						if(checkForWinner())
						{
							break;
						}
						
					// if the guess number exceeds the limit and the game isn't already won,
					// the code maker wins, the game file is cleared and a message is printed
					// the actual code that the code breaker couldn't guess is revealed
						if(guessNum >= guessLimit && isGameWon == false)
						{
							currentSession.clearGame(); // empty file
							MTUI.codemakerWinMessage(guessLimit);
							MTUI.revealCode(chosenCodeArray);
							System.out.println();
						}
						
						isLoadedGame = false;
				}
		}
		
		/***************************************************************************************************************************
		 	- The user is able to specify the number (between 8-15) of guesses in the game.
		 	- This method is called when the user is required to choose a maximum number of attempts that the code breaker will have
		 	to guess the code set by the code maker.
		 	- Input validation takes place to ensure the number entered by the user is between 8 and 15 inclusive.
		 	- Any other form of input is ignored or results in an error message before the user enters the correct form of input.
		 
		 @throws FileNotFoundException 
		 @throws IOException
		 @throws NumberFormatException
		
		 ***************************************************************************************************************************/
		public void chooseGuessLimit() throws FileNotFoundException, IOException
		{
			do // repeat this whilst the user hasn't entered a valid input
			{
				try
				{
					// ask the user how many attempts they wish the code breaker to have
					// give the user the choice of any number between 8 and 50
					System.out.println();
					MTUI.screenOutput(ANSI_PURPLE + "How many attempts would you like the code breaker to have? Enter a number between 8 and 50 inclusive." + ANSI_RESET);
					
					numInput = Integer.parseInt(MTUI.userInput()); // convert the String input into a number
					
					// if the number lies outside these limits, print an error message
					if(numInput < 8 || numInput > 50)
					{
						MTUI.screenOutput("Please enter a number of pegs between 8 and 50 inclusive.");
					}
					
					// otherwise, assign the valid input to the guess limit variable and exit the loop
					else
					{
						guessLimit = numInput;
						System.out.println();
						successfulExecute = true;
					}
				}
				catch(NumberFormatException NFE)
				{
				}
			} while(!successfulExecute);
			successfulExecute = false;
		}
		
		/***************************************************************************************************************************
			- The user is able to specify the number (between 3-8) of colours in the game.
			- This method is called when the user is required to choose a number of colours that the game will have
		 	in the code set by the code maker and the guesses made by the code breaker.
		 	- Input validation takes place to ensure the number entered by the user is between 3 and 8 inclusive.
		 	- Any other form of input is ignored or results in an error message before the user enters the correct form of input.
		
		 @throws FileNotFoundException 
		 @throws IOException
		 @throws NumberFormatException 
		
		 ***************************************************************************************************************************/
		public void chooseNumberColours() throws FileNotFoundException, IOException
		{
			do // repeat this whilst the user hasn't entered a valid input
			{
				try
				{
					// ask the user how many colours they wish to choose from
					// give the user the choice of any number between 3 and 8
					System.out.println();
					MTUI.screenOutput(ANSI_PURPLE + "How many colours would you like to choose from? Enter a number between 3 and 8 inclusive." + ANSI_RESET);
					
					numInput = Integer.parseInt(MTUI.userInput()); // convert the String input into a number
					
					// if the number lies outside these limits, print an error message
					if(numInput < 3 || numInput > 8)
					{
						MTUI.screenOutput("Please enter a number of colours between 3 and 8 inclusive.");
					}
					
					// otherwise, assign the valid input to the number of colours variable and exit the loop
					else
					{
						numColours = numInput;
						System.out.println();
						successfulExecute = true;
					}
				}
				catch(NumberFormatException NFE)
				{
				}
			} while(!successfulExecute);
			successfulExecute = false;
		}
		
		/***************************************************************************************************************************
			- The user is able to specify the number (between 3-8) of pegs being hidden in the game.
			- This method is called when the user is required to choose a number of pegs that the game will have
		 	in the code set by the code maker and the guesses made by the code breaker.
		 	- Input validation takes place to ensure the number entered by the user is between 3 and 8 inclusive.
		 	- Any other form of input is ignored or results in an error message before the user enters the correct form of input.
		
		 @throws FileNotFoundException 
		 @throws IOException
		 @throws NumberFormatException 
		
		 ***************************************************************************************************************************/
		public void chooseNumberPegs() throws FileNotFoundException, IOException
		{
			do // repeat this whilst the user hasn't entered a valid input
			{
				try
				{
					// ask the user how many pegs they wish to have on the board
					// give the user the choice of any number between 3 and 8
					System.out.println();
					MTUI.screenOutput(ANSI_PURPLE + "How many pegs would you like on the board? Enter a number between 3 and 8 inclusive." + ANSI_RESET);
					
					numInput = Integer.parseInt(MTUI.userInput()); // convert the String input into a number
					
					// if the number lies outside these limits, print an error message
					if(numInput < 3 || numInput > 8)
					{
						MTUI.screenOutput("Please enter a number of pegs between 3 and 8 inclusive.");
					}
					
					// otherwise, assign the valid input to the number of pegs variable and exit the loop
					else
					{
						numPegs = numInput;
						System.out.println();
						successfulExecute = true;
					}
				}
				catch(NumberFormatException NFE)
				{
				}
			} while(!successfulExecute);
			successfulExecute = false;
		}
		
		/***************************************************************************************************************************
			- The user is able to specify the number (between 1-4) corresponding to a certain type of AI mode the game will use:
			
				1 = human code maker and human code breaker
				2 = CPU code maker and human code breaker
				3 = CPU code maker and CPU code breaker
				4 = human code maker and CPU code breaker
			
			- This method is called when the user is required to choose a form of AI that the game will use.
			- This allows for choice as to whether a human plays a human/CPU and whether a CPU plays a human/CPU.
		 	- Input validation takes place to ensure the number entered by the user is between 1 and 4 inclusive.
		 	- Any other form of input is ignored or results in an error message before the user enters the correct form of input.
		
		 @throws FileNotFoundException 
		 @throws IOException
		 @throws NumberFormatException 
		
		 ***************************************************************************************************************************/
		public void chooseGameSetting() throws FileNotFoundException, IOException
		{
			do // repeat this whilst the user hasn't entered a valid input
			{
				try
				{
					// ask the user which game mode they wish to use, calling the gameSetting class
					// give the user the choice of any number between 1 and 4
					numInput = gameSetting.implementAISetting();
					
					// if the number lies outside these limits, print an error message
					if(numInput == -1)
					{
						MTUI.screenOutput("Please enter a number between 1 and 4 inclusive.");
					}
					
					// otherwise, assign the valid input to the AI mode variable and exit the loop
					else
					{
						AImode = numInput;
						System.out.println();
						successfulExecute = true;
					}
				}
				catch(NumberFormatException NFE)
				{
				}
			} while(!successfulExecute);
			successfulExecute = false;
		}
		
		/***************************************************************************************************************************
			- This method allows for the program to determine which method to call when the code is chosen, based on the current
			choice of AI.
			- For a human code maker, the human object's chooseCode method is called. The result of this is stored in the 
			chosenCode String variable.
			- This method accounts for a human wanting to restart the game completely.
			- Once a code has been chosen, regardless of whether it is chosen by the human or CPU, the String is evaluated and
			stored in an array for easier analysis once the evaluateCode method is called.
		
			@throws NullPointerException
			@throws FileNotFoundException
			@throws IOException
		 ***************************************************************************************************************************/
		public void fetchCode() throws NullPointerException, FileNotFoundException, IOException
		{
			chosenCode = ""; // reset the chosen code String to an empty string
			
			// if the AI mode chosen includes a human code maker, then run the human chooseCode method
			if(AImode == 1 || AImode == 4)
			{
				// print a message explaining how to choose a code
				MTUI.choosingCodeMessage(numColours, numPegs);
				chosenCode = humanPlayer.chooseCode(numColours, numPegs, guessNum, guessLimit, AImode, allGuessesArray, allKeyPegsArray);
				
				// if the user inputs "restart" then set the boolean diagnostic returnGame to true and return
				if(chosenCode.equals("Restart"))
				{
					restartGame = true;
					return;
				}
				// otherwise, split the chosen code string into an array for analysis
				evaluateCode(chosenCode);
			}
			
			// else if the AI mode chosen includes a CPU code maker, then run the CPU chooseCode method
			else if(AImode == 2 || AImode == 3)
			{
				chosenCode = cpuPlayer.chooseCode(numColours, numPegs, guessNum, guessLimit, AImode, allGuessesArray, allKeyPegsArray);
				// split the chosen code string into an array for analysis
				evaluateCode(chosenCode);
			}
		}
		
		/***************************************************************************************************************************
			- This method allows for the program to determine which method to call when the guess is made, based on the current
			choice of AI.
			- For a human code breaker, the human object's makeGuess method is called. The result of this is stored in the 
			chosenGuess String variable.
			- This method accounts for a human wanting to restart the game completely.
			- Once a guess has been made, regardless of whether it is chosen by the human or CPU, the String is evaluated and
			stored in an array for easier analysis once the evaluateGuess method is called.
		
			@throws NullPointerException
			@throws FileNotFoundException
			@throws IOException
			
		 ***************************************************************************************************************************/
		public void fetchGuess() throws NullPointerException, FileNotFoundException, IOException
		{
			// if AImode human code breaker then run human.makeGuess()
			if(AImode == 1 || AImode == 2)
			{
				// if a human is a code breaker, print the instructions to make a guess
				MTUI.makingGuessMessage(numColours, numPegs);
				System.out.println();
				
				chosenGuess = humanPlayer.makeGuess(numColours, numPegs, guessNum, guessLimit, AImode, chosenCode, chosenGuess, blackKeyPegCount, whiteKeyPegCount, allGuessesArray, allKeyPegsArray);
				
				// if the user inputs "restart" then set the boolean diagnostic returnGame to true and return
				if(chosenGuess.equals("Restart"))
				{
					restartGame = true;
					return;
				}
				
				// otherwise, split the chosen guess string into an array for analysis
				chosenGuessArray = new String[chosenGuess.length()];
				evaluateGuess(chosenGuess);
			}
			
			// else if AImode CPU code breaker then run CPU.chooseCode()
			else if(AImode == 3 || AImode == 4)
			{	
				chosenGuess = cpuPlayer.makeGuess(numColours, numPegs, guessNum, guessLimit, AImode, chosenCode, chosenGuess, blackKeyPegCount, whiteKeyPegCount, allGuessesArray, allKeyPegsArray);
				
				// split the chosen guess string into an array for analysis
				chosenGuessArray = new String[chosenGuess.length()];
				evaluateGuess(chosenGuess);
			}
		}
		
		/***************************************************************************************************************************
			- This method allows the feedback for a given guess to be presented to the user in the form of black and white key pegs.
			- The evaluated code and evaluated guess arrays are passed into this method for comparison.
			- First, the presence of black key pegs is checked. That is, if a peg is correct in colour AND position.
			- This means that a pass is made through the chosenCode and chosenGuess arrays to see if any elements are equal at 
			that position. If so, a flag is marked in the blackKeyPositions array and the black key peg counter is incremented.
			- Second, the presence of white key pegs is checked. That is, if a peg is correct in colour but NOT position.
			- This means that for every iteration through the chosenCode array, a pass is made through the chosenGuess array to 
			see if any un-flagged elements (positions marked as 0 in the blackKeyPositions array) contain a colour in the code 
			but at different position(s).
			- If so, a flag is marked in the whiteKeyPositions array and the white key peg counter is incremented.
			- A string variable is concatenated with a 1 if a black key peg is present or a 2 if a white key peg is present.
			- The feedback String is evaluated into an array and added to the allKeyPegs 2D array.
			
		@param chosenCodeArray -- array to store the code
		@param chosenGuessArray -- array to store the latest guess
		@param numPegs -- number of pegs on the board
		@param chosenCode -- string variable containing the colour indexes of the code
		@param chosenGuess -- string variable containing the colour indexes of the guess 
		 ***************************************************************************************************************************/
		public void checkKeyPegs(String[] chosenCodeArray, String[] chosenGuessArray, int numPegs, String chosenCode, String chosenGuess)
		{
			
		// set up new 1D arrays and fix their length to the number of pegs
			keyPegArray = new int[numPegs];
			blackKeyPositions = new int[numPegs];
			whiteKeyPositions = new int[numPegs];
			
		// reset key peg counters and empty the feedback string
			blackKeyPegCount = 0;
			whiteKeyPegCount = 0;
			String feedbackString = "";
			
			// for each peg in the code and guess, if they are the same:
			// 		* set a flag to indicate a black key peg has been taken (correct colour and position)
			// 		* increment the black key peg count
			// 		* concatenate the feedback string with a '1' to represent a black key peg 
			
				for(int i = 0; i < numPegs; i++)
				{
					if(chosenCodeArray[i].equals(chosenGuessArray[i]))
					{
						blackKeyPositions[i] = 1;
						blackKeyPegCount++;
						feedbackString = feedbackString + "1";
					}
				}
				
			// for each peg in the code and guess, if another peg exists elsewhere that is the same colour:
			//		if this peg position hasn't already been marked as a black key peg:
			// 			* set a flag to indicate a white key peg has been taken (correct colour, incorrect position)
			// 			* increment the white key peg count
			// 			* concatenate the feedback string with a '2' to represent a white key peg 
				for(int i = 0; i < numPegs; i++)
				{
					if(blackKeyPositions[i] == 0)
					{
						for(int j = 0; j < numPegs; j++)
						{
							if(chosenGuessArray[j].equals(chosenCodeArray[i]) && whiteKeyPositions[i]== 0)
							{
								whiteKeyPositions[i] = 1;
								whiteKeyPegCount++;
								feedbackString = feedbackString + "2";
								break;
							}
						}
					}
				}
				
			// for each character in the feedback string,
			// 		convert this number to an integer and add this to the key peg array
				for(int i = 0; i<feedbackString.length(); i++)
				{
					keyPegArray[i] = Integer.parseInt("" + feedbackString.charAt(i));
				}
				
			// add the latest line of responses to the all key keys 2D array
				for(int i=0; i<numPegs; i++)
				{
					allKeyPegsArray[guessNum][i] = keyPegArray[i];
				}
		}
		
		/***************************************************************************************************************************
			- A check for the winner is done after the guess number is incremeneted when a guess has been made.
			- After feedback is presented, if the code breaker scores 4 black key pegs then the game is won by the code breaker.
			- A check is made to see if the chosenCode array is identical to the chosenGuess array.
			- If so, the code breaker has cracked the code. The game file is cleared and a congratulations message appears on the
			interface.
			- The method returns a boolean value to the main game loop from where it is evaluated. If the game has been won then 
			the game loop is broken.
			
			@throws FileNotFoundException
			@throws IOException
			@return isGameWon
		
		 ***************************************************************************************************************************/
		public boolean checkForWinner() throws FileNotFoundException, IOException
		{
			isGameWon = false; // reset boolean diagnostic to false
			
			// if the chosen code and guess arrays are identical for every element
			if(Arrays.equals(chosenCodeArray, chosenGuessArray))
			{
				isGameWon = true; // set the boolean diagnostic to true
				currentSession.clearGame(); // empty the saved game file
				MTUI.codebreakerWinMessage(guessNum); // print that the code breaker has won
				return isGameWon; // return to the game loop with the diagnostic that should break the loop
			}
			
			return isGameWon;
		}
		
		/***************************************************************************************************************************
		 	- This method evaluates the chosenCode string and passes the data into an array for easier analysis.
		
		@param chosenCode -- the string of the code chosen by the player
		@throws NumberFormatException
		 ***************************************************************************************************************************/
		public void evaluateCode(String chosenCode) throws NumberFormatException
		{
			// set up an array of length equal to the number of pegs/length of the code
			arraySize = chosenCode.length();
			chosenCodeArray = new String[arraySize];
		
		// add each character of the code to an array for easier analysis
			for(int i = 0; i<arraySize; i++)
			{
				currentChar = "" + chosenCode.charAt(i);
				chosenCodeArray[i] = currentChar;
			}
		}
		
		/***************************************************************************************************************************
			- This method evaluates the chosenGuess string and passes the data into an array for easier analysis.
			- The evaluated guess is appended to the bottom of the allGuessesArray 2D array before printing the game board.
		
		@param chosenGuess -- 
		@throws NumberFormatException
		
		 ***************************************************************************************************************************/
		public void evaluateGuess(String chosenGuess) throws NumberFormatException
		{	
			// set up an array of length equal to the number of pegs/length of the guess
			arraySize = chosenGuess.length();
			chosenGuessArray = new String[arraySize];
					
		// add each colour of the guess to an array for easier analysis by converting the index to its colour
			for(int i = 0; i<arraySize; i++)
			{
				currentChar = "" + chosenGuess.charAt(i);
				chosenGuessArray[i] = currentChar;
				
				// add each element to a new row of the all guesses 2D array
				allGuessesArray[guessNum][i] = MTUI.numberToColour(Integer.parseInt(chosenGuessArray[i]));
			}
		}
		
		/***************************************************************************************************************************
			- This method contains an ANSI escape code sequence, which when printed, will clear the current view of the
			terminal and return to the first character on the top left of the terminal screen.
		
		 ***************************************************************************************************************************/
		public void clearScreen()
		{
				System.out.print("\033[H\033[2J"); // clear the terminal and return the cursor to the top left
		}
		
		/***************************************************************************************************************************
			- This method checks for whether the AI mode means that there is a CPU code breaker.
			- If so, then a delay timer is set between each guess to emulate the CPU 'thinking' time when making a guess.
			- Of course, this is mainly for visual purposes so it looks as though a game is being played, instead of simply
			printing the entire game board out 'instantly' according to the human eye.
			- The delay time between CPU guesses being made is set to 400 milliseconds, which is 0.4 seconds.
		
		@throws InterruptedException
		 ***************************************************************************************************************************/
		
		public void delayCheck()
		{
			
		// if the game mode allows a CPU code breaker, delay the time made in printing the guess
		// this is to emulate the pseudo-'thinking time' a guesser would make
		// improves the interface compared to simply printing a completed game board 'immediately'
			if(AImode == 3 || AImode == 4)
			{
				try 
				{
				    Thread.sleep(400); //400 milliseconds is 0.4 seconds.
				} 
				catch(InterruptedException ex) // handle any interrupts to the thread in this time
				{
				    Thread.currentThread().interrupt();
				}
			}
		}
		
}
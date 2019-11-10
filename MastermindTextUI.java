import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/***************************************************************************************************************************
	- The game provides a simple text-based interface to the system.
	- This class handles all input and output to and from the command line.
	- This class also handles the aesthetics of the program, making text colourful, spacing it and printing the game board.

@implements MastermindUI -- implements the userInput and screenOutput methods from this interface class
 ***************************************************************************************************************************/

public class MastermindTextUI implements MastermindUI
{	
	// create a SaveLoad object to interact with the current saved game
	SaveLoad currentSession;
	
	// declare a scanner object to handle input on the command line
	Scanner myScanner = new Scanner (new InputStreamReader(System.in)).useDelimiter("\\n");
	
	// create a string variable to store user input
	String Text;
	
	// create 3 Array Lists to store:
	//		* a list of all possible colours as words (choiceOfColours)
	//		* a list of corresponding ANSI sequences to print these colours in their colour (pegANSIColours)
	//		* a list of ANSI sequences to colour the key pegs black and white (keyPegANSIColours)
	public ArrayList<String> choiceOfColours = new ArrayList<String>();
	public ArrayList<String> pegANSIColours = new ArrayList<String>();
	public ArrayList<String> keyPegANSIColours = new ArrayList<String>();
	
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
		public static final String ANSI_CYAN = "\u001B[36m";
		public static final String ANSI_CYAN_BG = "\u001B[36;45m";
		public static final String ANSI_BLACK_BG = "\u001B[30;46m";
		public static final String ANSI_WHITE_BG = "\u001B[37;44m";
	
	/***************************************************************************************************************************
		- This constructor method initialises the SaveLoad object to equal the object passed to the MastermindTextUI class.

	@param currentSession -- the saved game object
	 ***************************************************************************************************************************/
	
	public MastermindTextUI(SaveLoad currentSession)
	{
		this.currentSession = currentSession; // set the saved game object to equal the object declared above
	}
	
	/***************************************************************************************************************************
		- This method handles any form of input using a Scanner object to read the user's input. The text is stored in a
		variable and passed to the method which called it in the first place for manipulation.
		- A string variable containing the user's input is returned to the original method.
	
	@return Text -- a string variable holding the user's input
	 ***************************************************************************************************************************/
	public String userInput()
	{
		try // attempt to assign the user input to a string variable
		{
			Text = myScanner.next();
		}
		catch(NoSuchElementException NSE) // if there is no such next character, exit the program gracefully.
		{
			System.out.print("No more data found.");
			closingMessage();
			System.exit(0);
		}
		
		return Text; // return the user's input as a string
	}
	
	/***************************************************************************************************************************
		- This method prints a given string to the command line as a form of output.
		- The message variable is passed to this method as a String to be printed.
		
	@param message -- String variable to be printed to the command line interface
	 ***************************************************************************************************************************/
	public void screenOutput(String message)
	{
		System.out.println(message); // print out the message passed into the method by the user
	}
	
	/***************************************************************************************************************************
		- This method adds all the available colours to the choiceOfColours ArrayList.
		- This ArrayList stores elements of String data type.
		- All of the 8 available colours have indexes in the list from 0-7 inclusive.
	
	 ***************************************************************************************************************************/
	public void addAvailableColours() 
	{
		// add all possible colours to a list. each one is assigned an index based on its position
		
		choiceOfColours.add("Black"); // this colour's index is now 0
		choiceOfColours.add("Red"); // this colour's index is now 1
		choiceOfColours.add("Orange"); // this colour's index is now 2
		choiceOfColours.add("Yellow"); // this colour's index is now 3
		choiceOfColours.add("Green"); // this colour's index is now 4
		choiceOfColours.add("Blue"); // this colour's index is now 5
		choiceOfColours.add("Purple"); // this colour's index is now 6
		choiceOfColours.add("White"); // this colour's index is now 7
	}
	
	/***************************************************************************************************************************
		- This method adds chosen ANSI escape sequences to the pegANSIColours and keyPegANSIColours ArrayLists.
		- This is so that the colours printed to the screen may be displayed in the correct colour when the game board is printed.
	
	 ***************************************************************************************************************************/
	public void addANSIColours() 
	{
		// add all of the ANSI sequences to a list to ensure colour output is correctly referenced
		
		pegANSIColours.add(ANSI_BLACK); // this colour's ANSI code index is the same as in choiceOfColours, 0.
		pegANSIColours.add(ANSI_RED); // this colour's ANSI code index is the same as in choiceOfColours, 1.
		pegANSIColours.add(ANSI_ORANGE); // this colour's ANSI code index is the same as in choiceOfColours, 2.
		pegANSIColours.add(ANSI_YELLOW); // this colour's ANSI code index is the same as in choiceOfColours, 3.
		pegANSIColours.add(ANSI_GREEN); // this colour's ANSI code index is the same as in choiceOfColours, 4.
		pegANSIColours.add(ANSI_BLUE); // this colour's ANSI code index is the same as in choiceOfColours, 5.
		pegANSIColours.add(ANSI_PURPLE); // this colour's ANSI code index is the same as in choiceOfColours, 6.
		pegANSIColours.add(ANSI_WHITE); // this colour's ANSI code index is the same as in choiceOfColours, 7.
		
		keyPegANSIColours.add(ANSI_CYAN_BG); // add an ANSI sequence for colouring opening and closing messages
		keyPegANSIColours.add(ANSI_BLACK_BG); // add an ANSI sequence for colouring black key pegs
		keyPegANSIColours.add(ANSI_WHITE_BG); // add an ANSI sequence for colouring white key pegs
	}
	
	/***************************************************************************************************************************
		- This method converts a given String (colour) to its equivalent number index as per the choiceOfColours ArrayList.
		
	@param colour -- a String which is passed in to convert the colour to its index
	@return Integer -- the index of the colour as per the choiceOfColours ArrayList
	
	 ***************************************************************************************************************************/
	public int colourToNumber(String colour)
	{
		// check if any of the elements in the choiceOfColours array list equals the colour input
		// if so, return the index of that colour
		for(int i = 0; i < 8; i++)
		{
			if(colour.toUpperCase().equals(choiceOfColours.get(i).toUpperCase()))
			{
				return i;
			}
		}
		return -1; // otherwise return a default number
	}
	
	/***************************************************************************************************************************
		- This method converts a given colour index (number) to its equivalent colour string as per the choiceOfColours ArrayList.
	
	@param number -- number passed to the method which is to be converted to a string colour
	@return String -- the equivalent colour as a string variable
	 ***************************************************************************************************************************/
	public String numberToColour(int number)
	{
		// check if the value of the index equals the number passed to the method
		// if so, return the colour at that index 
		for(int i = 0; i < 8; i++)
		{
			if(number == i)
			{
				return choiceOfColours.get(i);
			}
		}
		return ""; // otherwise return a default string
	}
	
	/***************************************************************************************************************************
		- This method converts a given number to its equivalent colour feedback peg.
			1 = black key peg
			2 = white key peg
	
	@param number -- number passed to the method which is to be converted to a string key peg "B" or "W".
	@return String -- the equivalent Peg as a string variable
	 ***************************************************************************************************************************/
	public String numberToPeg(int number)
	{
		// check if the value of the index equals a 1 or a 2
		// if so, return a "B" if the number required is 1 and "W" if the number required is 2.
		if(number == 1)
		{
			return " B ";
		}
		else if(number == 2)
		{
			return " W ";
		}
		return ""; // otherwise return a default string
	}
	
	/***************************************************************************************************************************
		- This method converts a given String (peg) to its equivalent number according to the feedback peg numbering convention.
				1 = black key peg
				2 = white key peg
	
	@param peg -- a String which is passed in to convert the peg to its key peg index
	@return Integer -- the index of the peg as per the keyPegANSIColours ArrayList
	
	 ***************************************************************************************************************************/
	public int pegToNumber(String peg)
	{
		// return a 1 if the peg is "B" and 2 if the peg is "W".
		if(peg.equals("B"))
		{
			return 1;
		}
		else if(peg.equals("W"))
		{
			return 2;
		}
		return -1; // otherwise return a default number
	}
	
	/***************************************************************************************************************************
		- This method prints out the updated game board.
		- The 2D arrays containing all of the guesses and all of the response key pegs are loaded into the method along with
		the current guess number and number of pegs to act as dimensions for the game board being printed.
		- The pegANSIColours and keyPegANSIColours ArrayLists are used to print out the game board in the appropriate colours.
		- Black pegs are always printed first before white pegs in the response.
		- The resulting game board is printed with tabs to space it all out equally.
		- The guess numbers are printed on the left hand side next to each guess.
	
	@param allGuessesArray -- the 2D array containing all guesses so far
	@param allKeyPegsArray -- the 2D array containing all key pegs so far
	@param guessNum -- the current guess number
	@param numPegs -- the number of pegs in the code
	
	@throws IndexOutOfBoundsException
	 ***************************************************************************************************************************/
	public void updatePrintedBoard(String[][] allGuessesArray, int[][] allKeyPegsArray, int guessNum, int numPegs) throws IndexOutOfBoundsException
	{		
			
		// print a game board with the guess number as a row and number of pegs as its width
		
			// for each row (guess),
			for(int i = 0; i < guessNum; i++)
			{
				int j;
				
				// print out the guess number followed by a tab
				System.out.print(i+1 + "	");
				
				// print out the guess in words, separated by tabs and wrapped in its own ANSI sequence to colour it the right colour
				for (j = 0; j < numPegs; j++)
				{
					System.out.print(pegANSIColours.get(colourToNumber(allGuessesArray[i][j])) + allGuessesArray[i][j] + ANSI_RESET + "	");
				}
				
				// print out the key peg array to show the black and white feedback pegs next to the guess on the same row
				for(j = 0; j < numPegs; j++)
				{
					int r = allKeyPegsArray[i][j];
					
					// only print the key peg if the key peg array indicates a black or a white peg at that position
					if(r > 0)
					{
						System.out.print(keyPegANSIColours.get(r) + numberToPeg(r) + ANSI_RESET);
					}
				}
				
				System.out.println(); // new line
			}
			
			System.out.println(); // new line
			
	}
	
	/***************************************************************************************************************************
		- This method prints out the title of the game using ASCII art.
		- A welcome message is displayed to the user.
		- The basic outline of the game is displayed here with space below before any game configuration takes place.
	
	 ***************************************************************************************************************************/
	public  void welcomeMessage()
	{
		System.out.println();
		System.out.println();
		
		// print out the ASCII art title in ANSI colours
		
		screenOutput(ANSI_RED + "		███╗   ███╗ █████╗ ███████╗████████╗███████╗██████╗ ███╗   ███╗██╗███╗   ██╗██████╗			" + ANSI_RESET);
		screenOutput(ANSI_YELLOW + "		████╗ ████║██╔══██╗██╔════╝╚══██╔══╝██╔════╝██╔══██╗████╗ ████║██║████╗  ██║██╔══██╗		" + ANSI_RESET);
		screenOutput(ANSI_GREEN + "		██╔████╔██║███████║███████╗   ██║   █████╗  ██████╔╝██╔████╔██║██║██╔██╗ ██║██║  ██║		" + ANSI_RESET);
		screenOutput(ANSI_CYAN + "		██║╚██╔╝██║██╔══██║╚════██║   ██║   ██╔══╝  ██╔══██╗██║╚██╔╝██║██║██║╚██╗██║██║  ██║		" + ANSI_RESET);
		screenOutput(ANSI_BLUE + "		██║ ╚═╝ ██║██║  ██║███████║   ██║   ███████╗██║  ██║██║ ╚═╝ ██║██║██║ ╚████║██████╔╝		" + ANSI_RESET);
		screenOutput(ANSI_PURPLE + "		╚═╝     ╚═╝╚═╝  ╚═╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝╚═╝     ╚═╝╚═╝╚═╝  ╚═══╝╚═════╝ " + ANSI_RESET);
		
		System.out.println();
		System.out.println();
		
		// print a welcome message and explain to the user the basic gist of the mastermind game
		screenOutput(ANSI_CYAN_BG + "Welcome to Mastermind!" + ANSI_RESET);
		System.out.println();
		screenOutput("This is a text version of the classic board game Mastermind.");
		screenOutput("As a user you have a fixed number of chances to guess the answer or you lose the game.");
		System.out.println();
	}
	
	/***************************************************************************************************************************
		- This method prints out a message to tell the user that a game is in progress and asks whether they wish to continue.
		- The options for a possible response to the question is printed here - "Y" or "N".
		- A form of input from the user is expected after this message.
	
	 ***************************************************************************************************************************/
	public void gameInProgressMessage()
	{
		// tell the user that there is a game in progress
		// give the user the choices of possible responses to this message
		screenOutput(ANSI_YELLOW + "There is a game in progress." + ANSI_RESET);
		screenOutput("Would you like to continue with this game? Y or N.");
		System.out.println();
	}
	
	/***************************************************************************************************************************
		- This method prints out a message to tell the user that they must enter a number to choose the AI mode.
		- The options for a possible response to the question is printed here - "1" or "2" or "3" or "4".
		- A form of input from the user is expected after this message.
	
	 ***************************************************************************************************************************/
	public void choosingAIMessage()
	{
		// tell the user that they must choose a number to represent an appropriate game mode
		// give the user the choices of possible responses to this message
		System.out.println();
		screenOutput(ANSI_PURPLE + "Please choose who will play the game by entering the appropriate number:" + ANSI_RESET);
		screenOutput("1 = human code maker and human code breaker");
		screenOutput("2 = CPU code maker and human code breaker");
		screenOutput("3 = CPU code maker and CPU code breaker");
		screenOutput("4 = human code maker and CPU code breaker");
		System.out.println();
	}
	
	/***************************************************************************************************************************
		- This method prints out a message to tell the user that they must choose a code from a given set of colours.
		- Based on the number of colours chosen, the allowed colours are printed to the monitor in their respective colours 
		on the display.
		- The characteristics for a possible response to the question is printed here - colours typed in word format separated 
		by spaces or with a press of the ENTER button between each one.
		- A form of input from the user is expected after this message.
	
	@param numColours -- the number of colours in the code
	@param numPegs -- the number of pegs in the code
	 ***************************************************************************************************************************/
	public void choosingCodeMessage(int numColours, int numPegs)
	{
		// print instructions to the user of how to type out and enter a code of length equal to the number of pegs
		// depending on the number of colours selected, inform the user of the colours they have to choose from
		
		System.out.println();
		screenOutput("Player 1, please write out the chosen code in words (separated by spaces or one by one pressing ENTER in between each colour) for Player 2 to then guess.");
		screenOutput("You may choose from the following colours:");

		if(numColours == 3)
		{
			System.out.print(ANSI_BLACK + "Black" + ANSI_RESET + " ");
			System.out.print(ANSI_RED + "Red" + ANSI_RESET + " ");
			System.out.print(ANSI_ORANGE + "Orange" + ANSI_RESET + " ");
		}
		else if(numColours == 4)
		{
			System.out.print(ANSI_BLACK + "Black" + ANSI_RESET + " ");
			System.out.print(ANSI_RED + "Red" + ANSI_RESET + " ");
			System.out.print(ANSI_ORANGE + "Orange" + ANSI_RESET + " ");
			System.out.print(ANSI_YELLOW + "Yellow" + ANSI_RESET + " ");
		}
		if(numColours == 5)
		{
			System.out.print(ANSI_BLACK + "Black" + ANSI_RESET + " ");
			System.out.print(ANSI_RED + "Red" + ANSI_RESET + " ");
			System.out.print(ANSI_ORANGE + "Orange" + ANSI_RESET + " ");
			System.out.print(ANSI_YELLOW + "Yellow" + ANSI_RESET + " ");
			System.out.print(ANSI_GREEN + "Green" + ANSI_RESET + " ");
		}
		if(numColours == 6)
		{
			System.out.print(ANSI_BLACK + "Black" + ANSI_RESET + " ");
			System.out.print(ANSI_RED + "Red" + ANSI_RESET + " ");
			System.out.print(ANSI_ORANGE + "Orange" + ANSI_RESET + " ");
			System.out.print(ANSI_YELLOW + "Yellow" + ANSI_RESET + " ");
			System.out.print(ANSI_GREEN + "Green" + ANSI_RESET + " ");
			System.out.print(ANSI_BLUE + "Blue" + ANSI_RESET + " ");
		}
		if(numColours == 7)
		{
			System.out.print(ANSI_BLACK + "Black" + ANSI_RESET + " ");
			System.out.print(ANSI_RED + "Red" + ANSI_RESET + " ");
			System.out.print(ANSI_ORANGE + "Orange" + ANSI_RESET + " ");
			System.out.print(ANSI_YELLOW + "Yellow" + ANSI_RESET + " ");
			System.out.print(ANSI_GREEN + "Green" + ANSI_RESET + " ");
			System.out.print(ANSI_BLUE + "Blue" + ANSI_RESET + " ");
			System.out.print(ANSI_PURPLE + "Purple" + ANSI_RESET + " ");
		}
		if(numColours == 8)
		{
			System.out.print(ANSI_BLACK + "Black" + ANSI_RESET + " ");
			System.out.print(ANSI_RED + "Red" + ANSI_RESET + " ");
			System.out.print(ANSI_ORANGE + "Orange" + ANSI_RESET + " ");
			System.out.print(ANSI_YELLOW + "Yellow" + ANSI_RESET + " ");
			System.out.print(ANSI_GREEN + "Green" + ANSI_RESET + " ");
			System.out.print(ANSI_BLUE + "Blue" + ANSI_RESET + " ");
			System.out.print(ANSI_PURPLE + "Purple" + ANSI_RESET + " ");
			System.out.print(ANSI_WHITE + "White" + ANSI_RESET);
		}
		
		System.out.println();
		screenOutput("Type out a string of " + numPegs + " colours from the above list.");
		System.out.println();
		screenOutput(ANSI_GREEN + "Type out the chosen code below:" + ANSI_RESET);
		System.out.println();
	}
	
	/***************************************************************************************************************************
		- This method prints out a message to tell the user that they must make a guess from a given set of colours.
		- Based on the number of colours chosen, the allowed colours are printed to the monitor in their respective colours 
		on the display.
		- The characteristics for a possible response to the question is printed here - colours typed in word format separated 
		by spaces or with a press of the ENTER button between each one.
		- A form of input from the user is expected after this message.
	
	@param numColours -- the number of colours in the code
	@param numPegs -- the number of pegs in the code 
	 ***************************************************************************************************************************/
	public void makingGuessMessage(int numColours, int numPegs)
	{
		// print instructions to the user of how to type out and enter a guess of length equal to the number of pegs
		// depending on the number of colours selected, inform the user of the colours they have to choose from
		
		screenOutput("Player 2, please write out the chosen guess in words (separated by spaces or one by one pressing ENTER in between each colour).");
		screenOutput("You may choose from the following colours:");
		if(numColours == 3)
		{
			System.out.print(ANSI_BLACK + "Black" + ANSI_RESET + " ");
			System.out.print(ANSI_RED + "Red" + ANSI_RESET + " ");
			System.out.print(ANSI_ORANGE + "Orange" + ANSI_RESET + " ");
		}
		else if(numColours == 4)
		{
			System.out.print(ANSI_BLACK + "Black" + ANSI_RESET + " ");
			System.out.print(ANSI_RED + "Red" + ANSI_RESET + " ");
			System.out.print(ANSI_ORANGE + "Orange" + ANSI_RESET + " ");
			System.out.print(ANSI_YELLOW + "Yellow" + ANSI_RESET + " ");
		}
		if(numColours == 5)
		{
			System.out.print(ANSI_BLACK + "Black" + ANSI_RESET + " ");
			System.out.print(ANSI_RED + "Red" + ANSI_RESET + " ");
			System.out.print(ANSI_ORANGE + "Orange" + ANSI_RESET + " ");
			System.out.print(ANSI_YELLOW + "Yellow" + ANSI_RESET + " ");
			System.out.print(ANSI_GREEN + "Green" + ANSI_RESET + " ");
		}
		if(numColours == 6)
		{
			System.out.print(ANSI_BLACK + "Black" + ANSI_RESET + " ");
			System.out.print(ANSI_RED + "Red" + ANSI_RESET + " ");
			System.out.print(ANSI_ORANGE + "Orange" + ANSI_RESET + " ");
			System.out.print(ANSI_YELLOW + "Yellow" + ANSI_RESET + " ");
			System.out.print(ANSI_GREEN + "Green" + ANSI_RESET + " ");
			System.out.print(ANSI_BLUE + "Blue" + ANSI_RESET + " ");
		}
		if(numColours == 7)
		{
			System.out.print(ANSI_BLACK + "Black" + ANSI_RESET + " ");
			System.out.print(ANSI_RED + "Red" + ANSI_RESET + " ");
			System.out.print(ANSI_ORANGE + "Orange" + ANSI_RESET + " ");
			System.out.print(ANSI_YELLOW + "Yellow" + ANSI_RESET + " ");
			System.out.print(ANSI_GREEN + "Green" + ANSI_RESET + " ");
			System.out.print(ANSI_BLUE + "Blue" + ANSI_RESET + " ");
			System.out.print(ANSI_PURPLE + "Purple" + ANSI_RESET + " ");
		}
		if(numColours == 8)
		{
			System.out.print(ANSI_BLACK + "Black" + ANSI_RESET + " ");
			System.out.print(ANSI_RED + "Red" + ANSI_RESET + " ");
			System.out.print(ANSI_ORANGE + "Orange" + ANSI_RESET + " ");
			System.out.print(ANSI_YELLOW + "Yellow" + ANSI_RESET + " ");
			System.out.print(ANSI_GREEN + "Green" + ANSI_RESET + " ");
			System.out.print(ANSI_BLUE + "Blue" + ANSI_RESET + " ");
			System.out.print(ANSI_PURPLE + "Purple" + ANSI_RESET + " ");
			System.out.print(ANSI_WHITE + "White" + ANSI_RESET);
		}
		
		System.out.println();
		screenOutput("Type out a string of " + numPegs + " colours from the above list.");
		System.out.println();
		screenOutput(ANSI_GREEN + "Type out the chosen guess below:" + ANSI_RESET);
		System.out.println();
	}
	
	/***************************************************************************************************************************
		- This method prints out a message to tell the user that the code breaker has won the game.
		- This is because the code chosen is idential to the most recent guess made.
		- This message uses ASCII art for visual appeal.
		- The number of guesses it took to break the code is printed in the message.
	
	@param guessNum -- current guess number
	 ***************************************************************************************************************************/
	
	public void codebreakerWinMessage(int guessNum)
	{
		// print out the ASCII art message in ANSI colours
		
		screenOutput(ANSI_RED + "	 ██████╗ ██████╗ ██████╗ ███████╗██████╗ ██████╗ ███████╗ █████╗ ██╗  ██╗███████╗██████╗     ██╗    ██╗██╗███╗   ██╗██╗" + ANSI_RESET);
		screenOutput(ANSI_YELLOW + "	██╔════╝██╔═══██╗██╔══██╗██╔════╝██╔══██╗██╔══██╗██╔════╝██╔══██╗██║ ██╔╝██╔════╝██╔══██╗    ██║    ██║██║████╗  ██║██║" + ANSI_RESET);
		screenOutput(ANSI_GREEN + "	██║     ██║   ██║██║  ██║█████╗  ██████╔╝██████╔╝█████╗  ███████║█████╔╝ █████╗  ██████╔╝    ██║ █╗ ██║██║██╔██╗ ██║██║" + ANSI_RESET);
		screenOutput(ANSI_CYAN + "	██║     ██║   ██║██║  ██║██╔══╝  ██╔══██╗██╔══██╗██╔══╝  ██╔══██║██╔═██╗ ██╔══╝  ██╔══██╗    ██║███╗██║██║██║╚██╗██║╚═╝" + ANSI_RESET);
		screenOutput(ANSI_BLUE + "	╚██████╗╚██████╔╝██████╔╝███████╗██████╔╝██║  ██║███████╗██║  ██║██║  ██╗███████╗██║  ██║    ╚███╔███╔╝██║██║ ╚████║██╗" + ANSI_RESET);
		screenOutput(ANSI_PURPLE + "	 ╚═════╝ ╚═════╝ ╚═════╝ ╚══════╝╚═════╝ ╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝     ╚══╝╚══╝ ╚═╝╚═╝  ╚═══╝╚═╝" + ANSI_RESET);
		
		// if the code breaker wins, let the user know how many guesses it took to break the code
		
		System.out.println();
		screenOutput(ANSI_YELLOW + "The code breaker is the winner! Game won in " + guessNum + " attempts." + ANSI_RESET);
		System.out.println();
	}
	
	
	/***************************************************************************************************************************
		- This method prints out a message to tell the user that the code maker has won the game.
		- This is because the code breaker has failed to guess the code in the maximum number of attempts allowed.
		- This message uses ASCII art for visual appeal.
		- The maximum number of guesses for the particular game is printed in the message so the user can know how many failed 
		attempts the code breaker made.
	
	@param guessLimit -- maximum number of attempts at guessing the code
	 ***************************************************************************************************************************/
	public void codemakerWinMessage(int guessLimit)
	{
		// print out the ASCII art message in ANSI colours
		
		screenOutput(ANSI_RED + "	 ██████╗ ██████╗ ██████╗ ███████╗███╗   ███╗ █████╗ ██╗  ██╗███████╗██████╗     ██╗    ██╗██╗███╗   ██╗██╗"+ ANSI_RESET);
		screenOutput(ANSI_YELLOW + "	██╔════╝██╔═══██╗██╔══██╗██╔════╝████╗ ████║██╔══██╗██║ ██╔╝██╔════╝██╔══██╗    ██║    ██║██║████╗  ██║██║"+ ANSI_RESET);
		screenOutput(ANSI_GREEN + "	██║     ██║   ██║██║  ██║█████╗  ██╔████╔██║███████║█████╔╝ █████╗  ██████╔╝    ██║ █╗ ██║██║██╔██╗ ██║██║"+ ANSI_RESET);
		screenOutput(ANSI_CYAN + "	██║     ██║   ██║██║  ██║██╔══╝  ██║╚██╔╝██║██╔══██║██╔═██╗ ██╔══╝  ██╔══██╗    ██║███╗██║██║██║╚██╗██║╚═╝"+ ANSI_RESET);
		screenOutput(ANSI_BLUE + "	╚██████╗╚██████╔╝██████╔╝███████╗██║ ╚═╝ ██║██║  ██║██║  ██╗███████╗██║  ██║    ╚███╔███╔╝██║██║ ╚████║██╗"+ ANSI_RESET);
		screenOutput(ANSI_PURPLE + "	 ╚═════╝ ╚═════╝ ╚═════╝ ╚══════╝╚═╝     ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝     ╚══╝╚══╝ ╚═╝╚═╝  ╚═══╝╚═╝" + ANSI_RESET);
		
		// if the code maker wins, let the user know how many unsuccessful attempts it took before the code maker won
		System.out.println();
		screenOutput(ANSI_YELLOW + "Game won after " + guessLimit + " unsuccessful code breaker guesses." + ANSI_RESET);
		System.out.println();
	}
	
	/***************************************************************************************************************************
		- This method prints out a message to ask the user whether they wish to play another game.
		- The options for a possible response to the question is printed here - "Y" or "N".
		- A form of input from the user is expected after this message.
	
	 ***************************************************************************************************************************/
	public void playAgainMessage()
	{
		// print a message asking the user if they wish to play a game again
		// let the user know the type of input expected after this message
		System.out.println();
		screenOutput(ANSI_PURPLE + "Would you like to play again? Y or N." + ANSI_RESET); 
		System.out.println();
	}
	
	/***************************************************************************************************************************
		- This method prints out a message to thank the user for playing the game.
	
	 ***************************************************************************************************************************/
	public void closingMessage()
	{
		// print out a closing message to the user thanking them for playing when they exit
		System.out.println();
		screenOutput(ANSI_CYAN_BG + "Thank you for playing Mastermind!" + ANSI_RESET);
		System.out.println();
	}
	
	/***************************************************************************************************************************
		- This method prints out a message to tell the user the chosen code if it has not been guessed in the specified number
		of guesses.
	
	@param chosenCode -- code chosen by the code maker (in the form of colour indexes
	 ***************************************************************************************************************************/
	public void revealCode(String[] chosenCode)
	{
		// print out the revealed code in its own colour, separated by spaces
		System.out.println();
		screenOutput(ANSI_GREEN + "The original code was:");
		
		for(int i=0; i<chosenCode.length; i++)
		{
			if(numberToColour(Integer.parseInt(chosenCode[i])).equals("Black"))
			{
				System.out.print(" " + ANSI_BLACK + numberToColour(Integer.parseInt(chosenCode[i])) + ANSI_RESET + " ");
			}
			if(numberToColour(Integer.parseInt(chosenCode[i])).equals("Red"))
			{
				System.out.print(" " + ANSI_RED + numberToColour(Integer.parseInt(chosenCode[i])) + ANSI_RESET + " ");
			}
			if(numberToColour(Integer.parseInt(chosenCode[i])).equals("Orange"))
			{
				System.out.print(" " + ANSI_ORANGE + numberToColour(Integer.parseInt(chosenCode[i])) + ANSI_RESET + " ");
			}
			if(numberToColour(Integer.parseInt(chosenCode[i])).equals("Yellow"))
			{
				System.out.print(" " + ANSI_YELLOW + numberToColour(Integer.parseInt(chosenCode[i])) + ANSI_RESET + " ");
			}
			if(numberToColour(Integer.parseInt(chosenCode[i])).equals("Green"))
			{
				System.out.print(" " + ANSI_GREEN + numberToColour(Integer.parseInt(chosenCode[i])) + ANSI_RESET + " ");
			}
			if(numberToColour(Integer.parseInt(chosenCode[i])).equals("Blue"))
			{
				System.out.print(" " + ANSI_BLUE + numberToColour(Integer.parseInt(chosenCode[i])) + ANSI_RESET + " ");
			}
			if(numberToColour(Integer.parseInt(chosenCode[i])).equals("Purple"))
			{
				System.out.print(" " + ANSI_PURPLE + numberToColour(Integer.parseInt(chosenCode[i])) + ANSI_RESET + " ");
			}
			if(numberToColour(Integer.parseInt(chosenCode[i])).equals("White"))
			{
				System.out.print(" " + ANSI_WHITE + numberToColour(Integer.parseInt(chosenCode[i])) + ANSI_RESET + " ");
			}
			
		}
		
		System.out.println();
	}
	
	/***************************************************************************************************************************
		- This method contains an ANSI escape code sequence, which when printed, will clear the current view of the
		terminal and return to the first character on the top left of the terminal screen.
	
	 ***************************************************************************************************************************/
	public void clearScreen()
	{
			System.out.print("\033[H\033[2J"); // clear the terminal and return the cursor to the top left
	}
	
}
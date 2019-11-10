// import the libraries necessary for handling the file not being found and I/O error handling
import java.io.FileNotFoundException;
import java.io.IOException;

/***************************************************************************************************************************
	- This class is to ensure the correct choice and implementation of the AI mode, validating input.
	- The text interface and current session objects are passed into this class to enable the class to handle input and 
	return to the main game loop successfully.
	- There are 4 main game modes that allow for full use of the Human and CPU classes.
	
***************************************************************************************************************************/ 
public class GameSetting 
{	
	// declare new objects to hold the text interface and current saved game session
	MastermindTextUI MTUI;
	SaveLoad currentSession;
	
	// a string to store the user's input
	String currentLine;
	
	/***************************************************************************************************************************
		- A constructor method which ensures the declared MastermindTextUI and SaveLoad objects are instantiated with the 
		original objects passed.
	
	@param MTUI -- text interface object to handle I/O
	@param currentSession -- saved game object to handle the saved game data
	 ***************************************************************************************************************************/
	
	public GameSetting(MastermindTextUI MTUI, SaveLoad currentSession)
	{
		// set parameters to equal their corresponding objects 
		this.currentSession = currentSession;
		this.MTUI = MTUI;
	}
	
	/***************************************************************************************************************************
		- A choice is made (between 1 and 4) corresponding to the mode of game play:
			1 = human code maker and human code breaker
			2 = CPU code maker and human code breaker
			3 = CPU code maker and CPU code breaker
			4 = human code maker and CPU code breaker
		- The current line is read using the MTUI object.
		- If the currentLine is equal to a valid number from the above list (1-4) then the method returns that number to 
		the game loop.
		- Otherwise, return a -1 indicating an invalid input.
		- This -1 will be handled appropriately by the game loop - the current method will be called until a valid input is made.
		
	@return Integer	 -- returns 1-4 inclusive if a successful choice is made. -1 if an invalid choice is made.
	@throws IOException 
	@throws FileNotFoundException 
	 ***************************************************************************************************************************/
	public int  implementAISetting() throws FileNotFoundException, IOException
	{	
		// print instructions as to how to choose a game mode
		MTUI.choosingAIMessage();
		
		// read the user's input
		currentLine = MTUI.userInput();
		
		// check if the user's input is the 'quit' command - "Q" or "QUIT"
		checkForQuitCommand(currentLine);
		
		// if the user types a number from 1-4, return the corresponding integer 
		if(currentLine.equals("1"))
		{
			return 1;
		}
		else if(currentLine.equals("2"))
		{
			return 2;
		}
		else if(currentLine.equals("3"))
		{
			return 3;
		}
		else if(currentLine.equals("4"))
		{
			return 4;
		}
		
		// otherwise return -1 to indicate an invalid input
		else
		{
			return -1;
		}
	}
	
	
/***************************************************************************************************************************
	- This method checks to see if the user enters "Q" or "QUIT" when asked if they want to load an existing game.
	- If so, then a message is displayed and the program exits gracefully.

 @throws FileNotFoundException 
 @throws IOException 

 ***************************************************************************************************************************/
public void checkForQuitCommand(String input) throws FileNotFoundException, IOException
{
	// if the input (case insensitive) is a quit command then exit the game gracefully
	if(input.toUpperCase().equals("QUIT") || input.toUpperCase().equals("Q"))
	{
		MTUI.screenOutput("Exiting game...");
		System.out.println();
		MTUI.closingMessage();
		System.exit(0);
	}
	// otherwise, return to the previous method
	else
	{
		return;
	}
}
	
}
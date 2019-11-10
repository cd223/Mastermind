// import the libraries necessary for handling the file not being found and I/O error handling
import java.io.FileNotFoundException;
import java.io.IOException;

/***************************************************************************************************************************
	- This abstract class allows for extension by the CPU and Human classes
	- This class sets out the main attributes and methods necessary for any given Player to have.
	- A player is responsible for setting a code or breaking the code set.
	- Each routine (setting the code and making a guess) will be handled differently by a CPU or a Human.
	- As such, an abstract class is necessary so that the methods may have the same name but perform different tasks for each
	class that implements them.
	
***************************************************************************************************************************/
abstract class Player
{	
	// declare new objects to hold the text interface and current saved game session
	public MastermindTextUI MTUI;
	public SaveLoad currentSession;
	
	// store the number of colours and number of pegs
	int numColours;
	int numPegs;
	
	/***************************************************************************************************************************
		- The constructor method declares and initialises all of the objects and attributes passed to it.
		- It passes in the textual interface and current saved session objects, as well as the number of colours and number of pegs.
		- This is so that the game may be saved at any point, and game data such as the number of colours and pegs can be used 
		when making a guess or choosing the code.
		
	@param MTUI -- text interface object
	@param currentSession -- current saved game object
	@param numColours -- number of colours in the guess/code
	@param numPegs -- number of pegs in the guess/code
	 ***************************************************************************************************************************/
	public Player(MastermindTextUI MTUI, SaveLoad currentSession, int numColours, int numPegs)
		{
			// set all named objects to equal those corresponding objects passed into the Player constructor 
			this.MTUI = MTUI;
			this.currentSession = currentSession;
			this.numColours = numColours;
			this.numPegs = numPegs;
		}

	// abstract method for a player choosing a code
	//		declared as abstract because a CPU chooses a code in a different way to a human and vice versa
	abstract String chooseCode(int numColours, int numPegs, int guessNum, int guessLimit, int AImode, String[][] allGuessesArray, int[][] allKeyPegsArray) throws FileNotFoundException, IOException;
	
	// abstract method for a player making a guess
	//		declared as abstract because a CPU makes a guess in a different way to a human and vice versa
	abstract String makeGuess(int numColours, int numPegs, int guessNum, int guessLimit, int AImode, String chosenCode, String prevChosenGuess, int blackKeyPegCount, int whiteKeyPegCount, String[][] allGuessesArray, int[][] allKeyPegsArray) throws FileNotFoundException, IOException;
	
}
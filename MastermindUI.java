
/***************************************************************************************************************************
	- This interface class defines the methods which will be implemented in the MastermindTextUI class.
	- It sets out two methods - one for input and one for output.
	- This structure will allow for easy extension of the Mastermind project - a MastermindGUI (graphical user interface) 
	class may be created from here to extend the possible interface options for display.
	
***************************************************************************************************************************/
interface MastermindUI
{
	// declare two methods that the MastermindTextUI class will implement
	// userInput handles input - taking in words on the command line and returning them as strings
	String userInput();
	// screenOutput handles output - taking in strings and printing words to the command line interface
	void screenOutput(String s);
}
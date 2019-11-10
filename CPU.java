// import libraries necessary for the CPU class to use array lists and generate random numbers
import java.util.ArrayList;
import java.util.Random;

/***************************************************************************************************************************
	- A CPU is a type of player of the game and hence inherits methods and behaviours from the player class
	- It is necessary for when the CPU needs to make a guess or choose a code to play with.
	- This is because the methods required for CPU functionality and human functionality are different.
	- Different methods (chooseCode and makeGuess) perform the same task in different ways to the human equivalent, hence the CPU class extends the 
	abstract class Player to perform these methods in its own way.

	@extends Player -- an abstract class which sets out the chooseCode and makeGuess methods
 ***************************************************************************************************************************/

public class CPU extends Player
{

	// a random object used for generating numbers between 0 and 7 corresponding to different colours
	Random rand;
	
	// store the number of pegs and number of colours in variables
	int numPegs;
	int numColours;
	
	// strings to store the current colour in the guess/code, the chosen guess and character to remove from the new guess
	String chosenColourInt = "";
	String chosenGuess = "";
	String charToRemove = "";
	
	// array lists of possible colours and the guess which initially comprises of a set of all possible colours for each peg
	// in this way, the guessMade ArrayList acts as a nested ArrayList - each element is a list of possible colours 
	ArrayList<String> possibleColours;
	ArrayList<ArrayList<String>> guessMade;
	
	/***************************************************************************************************************************
		- A constructor method which has the objects and attributes from the super class (Player) passed to it.
		- Initialises the random number object and guessMade ArrayList object before calling a method to populating the ArrayList.
	
	@param MTUI -- The text interface 
	@param currentSession -- The current saved game 
	@param numColours -- Used to populate the possible colours in the guessMade ArrayList
	@param numPegs -- Used to populate the possible colours in the guessMade ArrayList
	 ***************************************************************************************************************************/
	public CPU(MastermindTextUI MTUI, SaveLoad currentSession, int numColours, int numPegs) 
	{
		super(MTUI, currentSession, numColours, numPegs); // pass in the text interface object and number of colours and pegs
		rand = new Random(); // set the 'rand' integer variable to equal a new random number object 
		
		// instantiate array lists of possible colours and guessMade which initially comprises of a set of all possible colours for each peg
		possibleColours = new  ArrayList<String>();
		guessMade = new ArrayList<ArrayList<String>>();
		
		// call the populateChoices method to add a new list of possible colours to each position in the initial guess
		// this method is called when the makeGuess method is first called (i.e. when guessNum = 0)
		populateChoices(numColours, numPegs);
	}

	/***************************************************************************************************************************
		- A method which generates a random code for the game to start.
		- Uses the number of colours variable to set a limit on the index value of the possible colour randomly chosen. 
		- Generates a code using concatenation repeatedly until the number of loops equals the number of pegs and a valid code has 
		been chosen.
	
	@param numColours -- the number of colours in the guess/code
	@param numPegs -- the number of pegs in the guess/code
	@param guessNum -- the current guess number
	@param guessLimit -- the limit of attempts a game has
	@param AImode -- the current game mode (human vs human, human vs CPU etc)
	@param allGuessesArray -- the 2D array storing all of the guesses made
	@param allKeyPegsArray -- the 2D array storing all of the responses guesses made (feedback pegs)
	
	 ***************************************************************************************************************************/
	public String chooseCode(int numColours, int numPegs, int guessNum, int guessLimit, int AImode, String[][] allGuessesArray, int[][] allKeyPegsArray)
	{
		String chosenCode = ""; // initialise the chosen code variable to an empty string before indexes are added
		
		// for each peg, generate a random number between 0 and the number of colours
		// this random number serves as an index for the next colour in the code
		for(int i = 0; i<numPegs; i++)
		{
			chosenColourInt = Integer.toString(Math.abs(rand.nextInt(numColours)));
			chosenCode = chosenCode + chosenColourInt; // concatenate the next index to the existing code
		}
		
		return chosenCode; // once a code of sufficient length is created, exit the loop and return the code as a String variable
	}
	
	/***************************************************************************************************************************
		- A method which uses an intelligent computer driven code breaker to generate guesses to try and break the code set 
		by the other player.
		- Each position gets a list with all possible colours limited by the number of colours.
		- A random value is selected from each list to select the next attempt.
	 	- If the next attempt has no black key pegs, each respective colour is removed from that position's list.
		- If the next attempt has no white key pegs either, all the colours in the attempt must be removed from all the lists.
	
	@param numColours -- the number of colours in the guess/code
	@param numPegs -- the number of pegs in the guess/code
	@param guessNum -- the current guess number
	@param guessLimit -- the limit of attempts a game has
	@param AImode -- the current game mode (human vs human, human vs CPU etc)
	@param allGuessesArray -- the 2D array storing all of the guesses made
	@param allKeyPegsArray -- the 2D array storing all of the responses guesses made (feedback pegs)
	
	 ***************************************************************************************************************************/
	public String makeGuess(int numColours, int numPegs, int guessNum, int guessLimit, int AImode, String chosenCode, String prevChosenGuess, int blackKeyPegCount, int whiteKeyPegCount, String[][] allGuessesArray, int[][] allKeyPegsArray) throws IllegalArgumentException, IndexOutOfBoundsException, ArrayIndexOutOfBoundsException
	{
		// declare a random number index and an empty string to store the guess
		int randomIndex = 0;
		String chosenGuess = "";
		
		// create an ArrayList of colours used in the last guess made
		ArrayList<String> coloursInLastGuess = new ArrayList<String>();
		
		// declare index variables used for counting through 1D and 2D arrays 
		int i = 0;
		int j = 0;
		
		// if it is the first guess, there are no previous guesses to base the next guess on
		// as such, a guess is created in a similar way to the code
		if(guessNum==0)
		{			
			// each peg gets a list with all possible colour values from 0 to (numColours - 1) inclusive
			populateChoices(numColours, numPegs);
			
			// for each peg, a random index is generated between 0 and (numColours - 1)
			for(i = 0; i < numPegs; i++)
			{
					randomIndex = rand.nextInt(numColours);
					
					// concatenate the colour in the list at this random index with the existing chosenGuess string
					chosenGuess += guessMade.get(i).get(randomIndex);
			}
				
			return chosenGuess; // once a guess of sufficient length is created, exit the loop and return the guess as a String variable
			
		}
		
		// if it is the second guess or above, there are previous guesses to base the next guess on
		else if(guessNum>0)
		{		
			// store all of the distinct colours in the previous guess
			// eg: a guess 'red black orange red black' would add:
			// 'red', 'black' and 'orange' to this list
				for(i = 0; i < numPegs; i++)
				{
					if(!coloursInLastGuess.contains(Character.toString(prevChosenGuess.charAt(i))))
					{
						coloursInLastGuess.add(Character.toString(prevChosenGuess.charAt(i)));
					}
				}
				
			// If the feedback for the previous guess has no white pegs AND no black pegs, 
			// all the colours in the attempt must be removed from all the lists.
			// this is because no colour is correct and hence no position is correct
				if(whiteKeyPegCount == 0 && blackKeyPegCount == 0) 
				{
					for(i = 0; i < numPegs; i++)
					{	
						for(j = 0; j < coloursInLastGuess.size(); j++)
						{
							if(guessMade.get(i).contains(coloursInLastGuess.get(j)))
							{
								guessMade.get(i).remove(guessMade.get(i).indexOf(coloursInLastGuess.get(j)));
							}
						}
					}
				}
			
			// Otherwise, if the feedback for the previous guess has no has no black points only,
			//	the colour in each position must be removed from the list it is in.
			//  this is because no colour is placed in the correct position hence each colour must be removed from its list
				else if(blackKeyPegCount == 0)
				{	
					for(i = 0; i < numPegs; i++)
					{
						if(guessMade.get(i).contains(Character.toString(prevChosenGuess.charAt(i))))
						{	
							guessMade.get(i).remove(guessMade.get(i).indexOf(Character.toString(prevChosenGuess.charAt(i))));
						}
					}
				}
				
			// for each peg, a random index is generated between 0 and the size of the sub ArrayList of remaining colours at that peg
				for(i = 0; i < numPegs; i++)
				{
					randomIndex = guessMade.get(i).size();
					
					// concatenate the colour in the list at this random index with the existing chosenGuess string
					chosenGuess += guessMade.get(i).get(rand.nextInt(randomIndex));
				}
				
				return chosenGuess; // once a guess of sufficient length is created, exit the loop and return the guess as a String variable
				
		}
		
	return ""; // if neither condition is met, return a default empty String
		
	}
	
	/***************************************************************************************************************************
		- This method populates the guessMade String ArrayList with a new String ArrayList, before adding all possible colour
		indexes to each sub ArrayList.
		- This is done on guess 0, before any more guesses are made.
		- Elements will be removed from these lists gradually until the guess is correct or the limit of guesses is reached.
	
	@param numColours -- the number of colours in the guess/code
	@param numPegs -- the number of pegs in the guess/code
	
	 ***************************************************************************************************************************/
	public void populateChoices(int numColours, int numPegs)
	{
		
		int i; // declare an index variable to be used in for loops
	
	// populate all positions/pegs in the guessMade ArrayList with a list of all possible colours
	// these indexes range from 0-[numColours-1]
		for(i = 0; i < numPegs; i++)
		{
			
			// create a new String ArrayList to be appended to the guessMade ArrayList
			guessMade.add(new ArrayList<String>()); 
			
			// if the number of colours is 3, populate each sublist in the guessMade ArrayList with
			// colour indexes from 0-2 inclusive
			if(numColours == 3)
			{
				guessMade.get(i).add("0");
				guessMade.get(i).add("1");
				guessMade.get(i).add("2");
			}
			
			// if the number of colours is 4, populate each sublist in the guessMade ArrayList with
			// colour indexes from 0-3 inclusive
			else if(numColours == 4)
			{
				guessMade.get(i).add("0");
				guessMade.get(i).add("1");
				guessMade.get(i).add("2");
				guessMade.get(i).add("3");
			}
			
			// if the number of colours is 5, populate each sublist in the guessMade ArrayList with
			// colour indexes from 0-4 inclusive
			else if(numColours == 5)
			{
				guessMade.get(i).add("0");
				guessMade.get(i).add("1");
				guessMade.get(i).add("2");
				guessMade.get(i).add("3");
				guessMade.get(i).add("4");
			}
			
			// if the number of colours is 6, populate each sublist in the guessMade ArrayList with
			// colour indexes from 0-5 inclusive
			else if(numColours == 6)
			{
				guessMade.get(i).add("0");
				guessMade.get(i).add("1");
				guessMade.get(i).add("2");
				guessMade.get(i).add("3");
				guessMade.get(i).add("4");
				guessMade.get(i).add("5");
			}
			
			// if the number of colours is 7, populate each sublist in the guessMade ArrayList with
			// colour indexes from 0-6 inclusive
			else if(numColours == 7)
			{
				guessMade.get(i).add("0");
				guessMade.get(i).add("1");
				guessMade.get(i).add("2");
				guessMade.get(i).add("3");
				guessMade.get(i).add("4");
				guessMade.get(i).add("5");
				guessMade.get(i).add("6");
			}
			
			// if the number of colours is 8, populate each sublist in the guessMade ArrayList with
			// colour indexes from 0-7 inclusive
			else if(numColours == 8)
			{
				guessMade.get(i).add("0");
				guessMade.get(i).add("1");
				guessMade.get(i).add("2");
				guessMade.get(i).add("3");
				guessMade.get(i).add("4");
				guessMade.get(i).add("5");
				guessMade.get(i).add("6");
				guessMade.get(i).add("7");
			}
			
		}
	}
	
}
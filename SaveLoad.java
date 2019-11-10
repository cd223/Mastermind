// import I/O exceptions to handle errors in the case of a file not being found or I/O failing
import java.io.IOException;
import java.io. FileNotFoundException;

// import readers and writers so that the game file can be read from and written to
import java.io. BufferedReader;
import java.io. BufferedWriter;
import java.io. FileReader;
import java.io. FileWriter;
import java.io. File;

/***************************************************************************************************************************
	- This class is responsible for the handling of the game data file: "savedGame.txt"
	- The game data may be saved, loaded and cleared. All three possibilities are handled in this class.
	- The game is automatically saved after each guess, and may be requested to save during each human vs human or 
	CPU vs human game.

 ***************************************************************************************************************************/
public class SaveLoad
{
	// create variables to store the game data variables when they are extracted from the file during a load game routine
	public int guessNum;
	public int guessLimit;
	public int numColours;
	public int numPegs;
	public int AImode;
	public String chosenCode;
	
	// create arrays to store parts of the game data from the savedGame.txt file
	public String[] chosenCodeArray;
	public String[][] allPrevGuessesArray;
	public int[][] allPrevKeyPegsArray;
	
	/***************************************************************************************************************************
		- The constructor method initialises all declared variables to equal all corresponding parameters passed to it.
		- The constructor sets variables that will be accessed when saving and loading the game.

	@param guessNum -- the current guess number
	@param guessLimit -- the maximum number of guesses allowed in a game
	@param numColours -- the number of colours in a guess/code
	@param numPegs -- the number of pegs in a guess/code
	@param AImode -- the current game mode (human/CPU vs human/CPU)
	@param chosenCode -- the string of the code chosen
	@param chosenCodeArray -- the code chosen split into an array
	@param allPrevGuessesArray -- the 2D array containing all previous guesses
	@param allPrevKeyPegsArray -- the 2D array containing all previous key pegs
	 ***************************************************************************************************************************/
	
	public SaveLoad(int guessNum, int guessLimit, int numColours, int numPegs, int AImode, String chosenCode, String[] chosenCodeArray, String[][] allPrevGuessesArray, int[][] allPrevKeyPegsArray)
	{
		// set all declared fields to equal the corresponding named parameter in the constructor
		this.guessNum = guessNum;
		this.guessLimit = guessLimit;
		this.numColours = numColours;
		this.numPegs = numPegs;
		this.AImode = AImode;
		this.chosenCode = chosenCode;
		this.chosenCodeArray = chosenCodeArray;
		this.allPrevGuessesArray = allPrevGuessesArray;
		this.allPrevKeyPegsArray = allPrevKeyPegsArray;
	}
	
	/***************************************************************************************************************************
		- This accessor (getter) method accesses the updated value of the guessNum field and returns it to the method calling it.
		- This method is mainly used when accessing the current value of the variable after the loadGame() method has been called.

	@return guessNum -- the current guess number
	 ***************************************************************************************************************************/
	public int getGuessNum()
	{
		return guessNum;
	}
	
	/***************************************************************************************************************************
		- This accessor (getter) method accesses the updated value of the guessLimit field and returns it to the method calling it.
		- This method is mainly used when accessing the current value of the variable after the loadGame() method has been called.

	@return guessLimit -- the maximum number of guesses allowed in a game
	 ***************************************************************************************************************************/
	public int getGuessLimit()
	{
		return guessLimit;
	}
	
	/***************************************************************************************************************************
		- This accessor (getter) method accesses the updated value of the numColours field and returns it to the method calling it.
		- This method is mainly used when accessing the current value of the variable after the loadGame() method has been called.

	@return numColours -- the number of colours in a guess/code 
	 ***************************************************************************************************************************/
	public int getNumColours()
	{
		return numColours;
	}
	
	/***************************************************************************************************************************
		- This accessor (getter) method accesses the updated value of the numPegs field and returns it to the method calling it.
		- This method is mainly used when accessing the current value of the variable after the loadGame() method has been called.

	@return numPegs -- the number of pegs in a guess/code
	 ***************************************************************************************************************************/
	public int getNumPegs()
	{
		return numPegs;
	}
	
	/***************************************************************************************************************************
		- This accessor (getter) method accesses the updated value of the AImode field and returns it to the method calling it.
		- This method is mainly used when accessing the current value of the variable after the loadGame() method has been called.
	
	@return AImode -- the current game mode (human/CPU vs human/CPU)	
	 ***************************************************************************************************************************/
	public int getAImode()
	{
		return AImode;
	}
	
	/***************************************************************************************************************************
		- This accessor (getter) method accesses the updated value of the chosenCode field and returns it to the method calling it.
		- This method is mainly used when accessing the current value of the variable after the loadGame() method has been called.
	
	@return chosenCode -- the string of the code chosen
	 ***************************************************************************************************************************/
	public String getChosenCode()
	{
		return chosenCode;
	}
	
	/***************************************************************************************************************************
		- This accessor (getter) method accesses the updated value of the chosenCodeArray field and returns it to the method calling it.
		- This method is mainly used when accessing the current value of the variable after the loadGame() method has been called.

	@return chosenCodeArray -- the code chosen split into an array
	 ***************************************************************************************************************************/
	public String[] getChosenCodeArray()
	{
		return chosenCodeArray;
	}
	
	/***************************************************************************************************************************
		- This accessor (getter) method accesses the updated value of the allPrevGuessesArray field and returns it to the method calling it.
		- This method is mainly used when accessing the current value of the variable after the loadGame() method has been called.

	@return allPrevGuessesArray -- the 2D array containing all previous guesses
	 ***************************************************************************************************************************/
	public String[][] getAllPrevGuessesArray()
	{
		return allPrevGuessesArray;
	}
	
	/***************************************************************************************************************************
		- This accessor (getter) method accesses the updated value of the allPrevKeyPegsArray field and returns it to the method calling it.
		- This method is mainly used when accessing the current value of the variable after the loadGame() method has been called.

	@return allPrevKeyPegsArray -- the 2D array containing all previous key pegs
	 ***************************************************************************************************************************/
	public int[][] getAllPrevKeyPegsArray()
	{
		return allPrevKeyPegsArray;
	}
	
	/***************************************************************************************************************************
		- The game can be saved using this method which is either called automatically after each guess using the GameFunction
		class, or manually using human input in the Human class when a human breaks a code.
		- All variables are passed to this method and then processed in order to store game data as a series of numbers.
		- The first 7 digits include (in order) the guess number (2 digits), the guess limit (2 digits), AI setting (1 digit),
		number of colours (1 digit) and number of pegs (1 digit).
		- The following string of digits represents the indexes of the colours chosen in the code. (A string of numbers from 
		0-7 inclusive of length equal to the number of pegs chosen).
		- Then the strings of digits alternate between representing the indexes of the colours chosen in a given guess, to the
		response pegs as feedback to that guess. (A string of numbers from 0-7 inclusive of length equal to the number of pegs 
		chosen represent the guess, then a string of numbers from 0-2 inclusive of length equal to the number of pegs).
		- The response strings contain a 1 where a black peg is present, a 2 where a white peg is present, and a 0 as a placeholder
		indicating no key peg exists in that position.
		- Typical saved game after 2 guesses:
			"0210146 045232 444444 100000 000333 110000"
	
	@param guessNum -- the current guess number
	@param guessLimit -- the maximum number of guesses allowed in a game
	@param numColours -- the number of colours in a guess/code
	@param numPegs -- the number of pegs in a guess/code
	@param AImode -- the current game mode (human/CPU vs human/CPU)
	@param chosenCode -- the string of the code chosen
	@param allPrevGuessesArray -- the 2D array containing all previous guesses
	@param allPrevKeyPegsArray -- the 2D array containing all previous key pegs
	
	@throws IOException
	@throws FileNotFoundException
	 ***************************************************************************************************************************/
	
	public void saveGame(int guessNum, int guessLimit, int numColours, int numPegs, int AImode, String chosenCode, String[][] allGuessesArray, int[][] allKeyPegsArray) throws IOException, FileNotFoundException
	{
		// define the File for the FileReader to write to
		File savedGame = new File("savedGame.txt");
		
		// create indexes for loops
		int i;
		int j;
		
		// if file doesn't exist
		if (!savedGame.exists()) 
		{
			// create new file
			savedGame.createNewFile();
		}
		
		// create BufferedWriter to write to file - add a second argument (boolean) - true if content should be appended to existing
		// false if desired outcome is to overwrite previous content
		FileWriter fw = new FileWriter(savedGame, false);
		BufferedWriter bw = new BufferedWriter(fw);
		
		// if the guess number is not two digits, write a 0 before the guess number to ensure easier processing
		if(guessNum<10)
		{
			bw.write("0");
			bw.write(Integer.toString(guessNum)); // write the guess number to the file
		}
		else
		{
			bw.write(Integer.toString(guessNum));
		}
		
		// if the guess limit is not two digits, write a 0 before the guess limit to ensure easier processing
		if(guessLimit<10)
		{
			bw.write("0");
			bw.write(Integer.toString(guessLimit)); // write the guess limit to the file
		}
		else
		{
			bw.write(Integer.toString(guessLimit));
		}
		
		bw.write(Integer.toString(numColours)); // write the number of colours, number of pegs and AI mode to the file
		bw.write(Integer.toString(numPegs));
		bw.write(Integer.toString(AImode));
		
		bw.write(" "); // write a plain space to the file to separate game data
		
		bw.write(chosenCode); // write the chosen code to the file
		
		bw.write(" "); // write a plain space to the file to separate game data
		
		for(i = 0; i < guessNum; i++)
		{
			for(j = 0; j < numPegs; j++)
			{
				String colour = allGuessesArray[i][j]; // extract the current colour from the allGuesses array
				// depending on what it equals, write its index to the save game file
				if(colour.equals("Black"))
				{
					bw.write("0");
				}
				else if(colour.equals("Red"))
				{
					bw.write("1");
				}
				else if(colour.equals("Orange"))
				{
					bw.write("2");
				}
				else if(colour.equals("Yellow"))
				{
					bw.write("3");
				}
				else if(colour.equals("Green"))
				{
					bw.write("4");
				}
				else if(colour.equals("Blue"))
				{
					bw.write("5");
				}
				else if(colour.equals("Purple"))
				{
					bw.write("6");
				}
				else if(colour.equals("White"))
				{
					bw.write("7");
				}
			}
			
			bw.write(" "); // write a plain space to the file to separate game data
			
			for(j = 0; j < numPegs; j++)
			{
				bw.write(Integer.toString(allKeyPegsArray[i][j])); // write the current key peg index to the save game file
			}
			
			bw.write(" "); // write a plain space to the file to separate game data
		}
		
		bw.close(); // close the buffered writer object
	}
	
	/***************************************************************************************************************************
	 	- The game can be loaded and re-started from a given point if a game is in progress.
	 	- All variables are set in this method based on the contents of the "savedGame.txt" file and then processed in order 
	 	to retrieve game data which is stored as a series of numbers.
		- The first 7 digits include (in order) the guess number (2 digits), the guess limit (2 digits), AI setting (1 digit),
		number of colours (1 digit) and number of pegs (1 digit).
		- The following string of digits represents the indexes of the colours chosen in the code. (A string of numbers from 
		0-7 inclusive of length equal to the number of pegs chosen).
		- Then the strings of digits alternate between representing the indexes of the colours chosen in a given guess, to the
		response pegs as feedback to that guess. (A string of numbers from 0-7 inclusive of length equal to the number of pegs 
		chosen represent the guess, then a string of numbers from 0-2 inclusive of length equal to the number of pegs).
		- The response strings contain a 1 where a black peg is present, a 2 where a white peg is present, and a 0 as a placeholder
		indicating no key peg exists in that position.
		- Typical saved game after 2 guesses:
			"0210146 045232 444444 100000 000333 110000"
		- This game data is extracted accordingly until there is nothing left to read in the "savedGame.txt" text file.
		- Error messages account for any potential exceptions when retreiving the file or handling output.
	
	@throws NullPointerException
	@throws IOException
	 ***************************************************************************************************************************/
	public void loadGame() throws NullPointerException, IOException
	{
		String currentLine = ""; // clear the current line
		int i;
		
			// define the File for the FileReader to read from
			File savedGame = new File ("savedGame.txt");
			// create a new FileReader to read from the defined File
			FileReader fr = new FileReader (savedGame);
			// create BufferedReader to read buffered blocks of text from the FileReader
			BufferedReader br = new BufferedReader(fr);
			
			currentLine = br.readLine(); // read the current line of the buffered reader before assigning it to currentLine
			// close the BufferedReader
			br.close();
			
			if(currentLine==null) // if the current line is null, print an error message and return to the original method
			{
				System.out.println("There is nothing to load as the file is empty!");
				return;
			}
			
			// set the game variables to equal the digits stored in their respective spaces in the game data file
			guessNum = Integer.parseInt("" + currentLine.charAt(0) + currentLine.charAt(1));
			guessLimit = Integer.parseInt("" + currentLine.charAt(2) + currentLine.charAt(3));
			numColours = Character.getNumericValue(currentLine.charAt(4));
			numPegs = Character.getNumericValue(currentLine.charAt(5));
			AImode = Character.getNumericValue(currentLine.charAt(6));
			
			// cut off the game data at the beginning of the string before continuing to load data
			currentLine = currentLine.substring(8);
			
			// fix the Arrays using the guessLimit and numPegs variables as dimensions
			allPrevGuessesArray = new String[guessLimit][numPegs];
			allPrevKeyPegsArray = new int[guessLimit][numPegs];
			chosenCodeArray = new String[numPegs];
			
			// if the string is now empty after cutting it down, return to the previous method
			if(currentLine.isEmpty())
			{
				System.out.println("Game file is empty.");
				return;
			}
			
			chosenCode = ""; // empty the chosen code variable 
			
			// iterate through a number of times equal to the number of pegs
			// 		extract a character and add/concatenate it to the chosen code array and existing code string
			for(i=0;i<numPegs;i++)
			{
				String currentChar = "" + Character.getNumericValue(currentLine.charAt(i));
				chosenCode += currentChar;
				chosenCodeArray[i] = currentChar;
			}
			
			// cut off the game data at the beginning of the string before continuing to load data
			currentLine = currentLine.substring(numPegs +1); 
			
			int count = 0;
			
			while(true)
			{
				// if the number of characters remaining is less than the number of pegs there is no more data to extract
				// break the loop
				if(currentLine.length() < numPegs)
				{
					break;
				}
				
				// read the next character
				// 		return the equivalent colour corresponding to that index
				// 		store this colour in the allPrevGuessesArray
				for(i=0; i<numPegs; i++)
				{
					if(("" + currentLine.charAt(i)).equals("0"))
					{
						allPrevGuessesArray[count][i] = "Black";
					}
					else if(("" + currentLine.charAt(i)).equals("1"))
					{
						allPrevGuessesArray[count][i] = "Red";
					}
					else if(("" + currentLine.charAt(i)).equals("2"))
					{
						allPrevGuessesArray[count][i] = "Orange";
					}
					else if(("" + currentLine.charAt(i)).equals("3"))
					{
						allPrevGuessesArray[count][i] = "Yellow";
					}
					else if(("" + currentLine.charAt(i)).equals("4"))
					{
						allPrevGuessesArray[count][i] = "Green";
					}
					else if(("" + currentLine.charAt(i)).equals("5"))
					{
						allPrevGuessesArray[count][i] = "Blue";
					}
					else if(("" + currentLine.charAt(i)).equals("6"))
					{
						allPrevGuessesArray[count][i] = "Purple";
					}
					else if(("" + currentLine.charAt(i)).equals("7"))
					{
						allPrevGuessesArray[count][i] = "White";
					}
				}
				
				// cut off the game data at the beginning of the string before continuing to load data
				currentLine = currentLine.substring(numPegs +1);
				
				// if the number of characters remaining is less than the number of pegs there is no more data to extract
				// break the loop
				if(currentLine.length() < numPegs)
				{
					break;
				}
				
				// read the next character
				// 		return the key peg index
				// 		store this index in the allPrevKeyPegsArray
				for(i=0; i<numPegs; i++)
				{
					allPrevKeyPegsArray[count][i] = Integer.parseInt("" + currentLine.charAt(i));
				}
				
				// cut off the game data at the beginning of the string before continuing to load data
				currentLine = currentLine.substring(numPegs +1);
				count++; // increment the counter
			}
			
			// print "loading" message 
			System.out.println("Loading game...");
			
			return; // return to the method which called the load routine
	}
	
	/***************************************************************************************************************************
		- The game can be cleared after each game is complete, ready for a new game to be played.
		- Regardless of whether the code breaker or code maker is successful, the file is wiped and game data deleted.
		- This is to enable the user to start a new game and save that data rather than deal with completed game data that
		cannot be loaded to continue a game as the conditions for a game ending have already been met.
		
	@throws IOException
	@throws FileNotFoundException
	 ***************************************************************************************************************************/
	public void clearGame() throws IOException, FileNotFoundException
	{
		// declare File object to define a file to write to
		File savedGame = new File("savedGame.txt");
		
		// if file doesn't exist
		if (!savedGame.exists()) 
		{
			// create new file
			savedGame.createNewFile();
		}
		
		// create BufferedWriter to write to file - add a second argument (boolean) - true if content should be appended to existing
		// false if desired outcome is to overwrite previous content
		FileWriter fw = new FileWriter(savedGame, false);
		BufferedWriter bw = new BufferedWriter(fw);
		
		bw.write(""); // write a null string to the file to clear it
		bw.close();
	}
	
}
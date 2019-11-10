	███╗   ███╗ █████╗ ███████╗████████╗███████╗██████╗ ███╗   ███╗██╗███╗   ██╗██████╗ 
	████╗ ████║██╔══██╗██╔════╝╚══██╔══╝██╔════╝██╔══██╗████╗ ████║██║████╗  ██║██╔══██╗
	██╔████╔██║███████║███████╗   ██║   █████╗  ██████╔╝██╔████╔██║██║██╔██╗ ██║██║  ██║
	██║╚██╔╝██║██╔══██║╚════██║   ██║   ██╔══╝  ██╔══██╗██║╚██╔╝██║██║██║╚██╗██║██║  ██║
	██║ ╚═╝ ██║██║  ██║███████║   ██║   ███████╗██║  ██║██║ ╚═╝ ██║██║██║ ╚████║██████╔╝
	╚═╝     ╚═╝╚═╝  ╚═╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝╚═╝     ╚═╝╚═╝╚═╝  ╚═══╝╚═════╝ 
		 _   _                 _ _                        _        
		| | | |               (_) |                      | |       
		| |_| | _____      __  _| |_  __      _____  _ __| | _____ 
		|  _  |/ _ \ \ /\ / / | | __| \ \ /\ / / _ \| '__| |/ / __|
		| | | | (_) \ V  V /  | | |_   \ V  V / (_) | |  |   <\__ \
		\_| |_/\___/ \_/\_/   |_|\__|   \_/\_/ \___/|_|  |_|\_\___/
		
		Master mind is a code-breaking game for two players.
	
- Before a game begins, a player chooses the number of colours and number of pegs to play with.
- From here, player one will select a code (combination of colours).
- Player two must guess this code within a set number of attempts to win the game.
- After each guess, feedback is given to player two in the form of black and white pegs.
- A black peg indicates that the current peg is correct in both colour and position.
- A white peg indicates that the current peg is correct in colour and but not position.
- If player two fails to guess the code within the guess limit then player one is the winner.		

	***** DO NOT CHANGE OR DELETE THE CONTENTS OF THE SAVEDGAME.TXT FILE *****

--------------------------------------* 1 *--------------------------------------
Starting a new (or existing) game
---------------------------------------------------------------------------------
				~~~ STARTING A NEW GAME ~~~
* On program startup, the existing terminal screen is cleared.
* A coloured Mastermind title with a welcome message is displayed.
* The basic aim of the game is printed below - once a code is made by the code maker, the
code breaker has a fixed number of chances to guess the answer or they lose the game.
* For best viewing, start the game using the terminal in FULL SCRREN view, to allow space
for the title.
* A check is made to see if the savedGame.txt file contains any game data.
* If not, the screen game configuration takes place (see below).

				~~~ LOADING AN EXISTING GAME ~~~
* * On program startup, the existing terminal screen is cleared.
* A coloured Mastermind title with a welcome message is displayed.
* The basic aim of the game is printed below - once a code is made by the code maker, the
code breaker has a fixed number of chances to guess the answer or they lose the game.
* For best viewing, start the game using the terminal in FULL SCRREN view, to allow space
for the title.
* A check is made to see if the savedGame.txt file contains any game data.
* If so, BEFORE any game configuration takes place, a message appears telling the user that 
a game is in progress. It asks if the user wants to load the existing game.
* If the user types "Y" or "YES", the game is loaded from its saved position.
* If the user types "N" or "NO", the game is started as a new game.

--------------------------------------* 2 *--------------------------------------
Game configuration (game mode, number of pegs/colours)
---------------------------------------------------------------------------------
				~~~ GAME MODE ~~~
* A message explaining the different game modes appears first. The user must enter a number
between 1 and 4 to represent each game mode. Type a number from 1-4 and press ENTER:
			1 = human code maker and human code breaker
			2 = CPU code maker and human code breaker
			3 = CPU code maker and CPU code breaker
			4 = human code maker and CPU code breaker
* Failure to do so will mean the question is asked again. The user may type "q" or "Quit"
to exit the game at this stage.

				~~~ GUESS LIMIT ~~~
* A message telling the user to choose how many attempts the code breaker should have appears.
* The user must enter a number between 8 and 50 inclusive and press ENTER.
* Failure to do so will mean the question is asked again. The user may not quit at this stage.

				~~~ NUMBER OF COLOURS ~~~
* A message telling the user to choose how many colours the code/guesses could contain appears.
* The user must enter a number between 3 and 8 inclusive and press ENTER.
* Failure to do so will mean the question is asked again. The user may not quit at this stage.

				~~~ NUMBER OF PEGS ~~~
* A message telling the user to choose how many pegs the code/guess should should be appears.
* The user must enter a number between 3 and 8 inclusive and press ENTER.
* Failure to do so will mean the question is asked again. The user may not quit at this stage.

--------------------------------------* 3 *--------------------------------------
Choosing a code to play with
---------------------------------------------------------------------------------
				~~~ HUMAN CODE MAKER ~~~
* If the game mode allows a human code maker, the code maker is instructed to write out 
the chosen code in words (separated by spaces or one-by-one pressing ENTER in between each 
colour) for the code breaker to then guess.
* Depending on the number of colours chosen, the list of permitted colours is printed:
			Black Red Orange Yellow Green Blue Purple White
* EG: "Black Red Orange" for 3 colours and so on.
* A valid code for this with 3 pegs would be "orange red black" or
"orange
red
black"
* Any invalid colour or input is dismissed and not counted as part of the code.
* A user cannot use the "Save", "Restart" or "Quit" commands at this stage.

				~~~ CPU CODE MAKER ~~~
* If the CPU is the code maker, no messages are printed to the terminal.
* After choosing the number of pegs, the user is taken straight to the guess screen where
they must make their first guess.

--------------------------------------* 4 *--------------------------------------
Making a guess
---------------------------------------------------------------------------------
				~~~ HUMAN CODE BREAKER ~~~
* If the game mode allows a human code breaker, the code breaker is instructed to write out 
the guess made in words (separated by spaces or one-by-one pressing ENTER in between each 
colour).
* Depending on the number of colours chosen, the list of permitted colours is printed:
			Black Red Orange Yellow Green Blue Purple White
* EG: "Black Red Orange" for 3 colours and so on.
* A valid code for this with 3 pegs would be "orange red black" or
"orange
red
black"
* Any invalid colour or input is dismissed and not counted as part of the guess.
* On the first turn, a user cannot use the "Save", "Restart" or "Quit" commands.
* Upon the second turn or higher, the user can use the "Save", "Restart" or "Quit" commands.

				~~~ CPU CODE BREAKER ~~~
* If the game mode allows a CPU code breaker, the game mode is effectively automatic.
* The game board is printed after each new guess is made, with feedback on each one.
* The algorithm first compiles a random guess, then each following guess uses the feedback
to remove certain colours from certain positions.
* Delays of 0.4 seconds take place between each guess to emulate 'thinking time' for the
computer and add realism to the game, improving its interface.
* As it is a CPU code breaker, a user cannot use the "Save", "Restart" or "Quit" commands.

--------------------------------------* 5 *--------------------------------------
The game board (including guess feedback)
---------------------------------------------------------------------------------
* After each guess, feedback is given to player two in the form of black and white pegs.
* A black peg indicates that the current peg is correct in both colour and position.
* A white peg indicates that the current peg is correct in colour and but not position.

Typical view of a game board (8 colours, 6 pegs):

Current game board view:
1       Yellow  White   Black   Black   Orange  Green    W  W  W  W  W
2       Purple  Yellow  Purple  White   Black   Orange   B  W  W  W  W
3       Black   Green   Yellow  Red     White   Black    W  W  W  W
4       Orange  Orange  Purple  White   Yellow  Orange   B  B  W  W
5       Green   Black   Blue    White   Green   Orange   B  B  W
6       Orange  Black   Purple  White   Purple  Red      B  W  W  W
7       Blue    Yellow  Purple  Green   Blue    Yellow   W  W
8       Green   Red     Green   Purple  Yellow  Purple   B  W  W

--------------------------------------* 6 *--------------------------------------
Key words/commands
---------------------------------------------------------------------------------
			~~~ "Save" or "S" (case insensitive) ~~~
* A user may type these commands on the second guess or higher.
* The game is saved, a message printed and the user may carry on their attempt.

			~~~ "Restart" or "R" (case insensitive) ~~~
* * A user may type these commands on the second guess or higher.
* The game is cleared, and the game resets back to the initial configuration stage.

			~~~ "Quit" or "Q" (case insensitive) ~~~
* A user may type these commands on the second guess or higher, or when choosing
the game mode.
* The game is saved if there are guesses to be saved, or simply exited without saving 
if not. A message is printed and the program exits gracefully.

--------------------------------------* 7 *--------------------------------------
Saving/loading
---------------------------------------------------------------------------------
* The game is automatically saved after each guess, and may be requested to save 
during each human vs human or CPU vs human game. (You may manually save a game when
there is a human code breaker but not when there is a CPU code breaker).
* The reason for not saving/loading a CPU code breaker game is that it is over
very quickly and needn't be restarted/loaded again.
* Whereas a human code breaker may want to save their progress and carry on another
time as it is more time consuming having a human code breaker than a CPU code breaker.

--------------------------------------* 8 *--------------------------------------
Ending the game (after a game has been won)
---------------------------------------------------------------------------------
~~~ CODE MAKER WIN ~~~
* If a code breaker fails to guess the code within a maximum number of attempts,
8 for example, a big message is printed letting the user know the code maker has
won with the number of unsuccessful code breaker guesses beneath.
* A message appears revealing the identity of the original code, such as
"The original code was:
 Red  Black  Orange  Yellow  Yellow  Orange"
* The game asks the user whether they wish to play again.
* If the answer is yes, the game loop restarts at the initial configuration stage.
* If the answer is no, the program exits gracefully after an exit message.

~~~ CODE BREAKER WIN ~~~
* If a code breaker guesses the code within the maximum number of attempts,
8 for example, a big message is printed letting the user know the code breaker has
won with the number of guesses it took to break the code beneath.
* The game asks the user whether they wish to play again.
* If the answer is yes, the game loop restarts at the initial configuration stage.
* If the answer is no, the program exits gracefully after an exit message.

			______         _                       
			|  ___|       | |                      
			| |_ ___  __ _| |_ _   _ _ __ ___  ___ 
			|  _/ _ \/ _` | __| | | | '__/ _ \/ __|
			| ||  __/ (_| | |_| |_| | | |  __/\__ \
			\_| \___|\__,_|\__|\__,_|_|  \___||___/

--------------------------------------* 1 *--------------------------------------
The user is able to specify the number (between 3-8) of colours in the game
---------------------------------------------------------------------------------
* A choice is given to the user as to how many colours to use in a guess.
* This means the game difficulty can be increased or decreased.

--------------------------------------* 2 *--------------------------------------
The user is able to specify the number (between 3-8) of pegs being hidden
---------------------------------------------------------------------------------
* A choice is given to the user as to how many pegs to use on the game board.
* This means the game difficulty can be increased or decreased.

--------------------------------------* 3 *--------------------------------------
The game can be played by:
---------------------------------------------------------------------------------
	(a) one human codemaker and one human codebreaker
---------------------------------------------------------------------------------
		* Allows any game to be saved, quit, loaded and restarted.
---------------------------------------------------------------------------------
	(b) one computer-based codemaker and one human codebreaker
---------------------------------------------------------------------------------
		* Allows any game to be saved, quit, loaded and restarted.
---------------------------------------------------------------------------------
	(c) one computer-based codemaker and one computer-based codebreaker
---------------------------------------------------------------------------------
		* There is no need for save, quit, load or restart functionality.
---------------------------------------------------------------------------------
	(d) one human-based codemaker and one computer-based codebreaker
---------------------------------------------------------------------------------
		* There is no need for save, quit, load or restart functionality.
		
--------------------------------------* 4 *--------------------------------------
The game can be saved and re-started.
---------------------------------------------------------------------------------
* All game data is stored in a file called "savedGame.txt" as a string of numbers.
* The first 7 digits include (in order) the guess number (2 digits), the guess 
limit (2 digits), AI setting (1 digit), number of colours (1 digit) and number of 
pegs (1 digit). The following string of digits represents the indexes of the colours 
chosen in the code. (A string of numbers from 0-7 inclusive of length equal to the 
number of pegs chosen).
* Then the strings of digits alternate between representing the indexes of the colours 
chosen in a given guess, to the response pegs as feedback to that guess. (A string of 
numbers from 0-7 inclusive of length equal to the number of pegs chosen represent the 
guess, then a string of numbers from 0-2 inclusive of length equal to the number of 
pegs).
* The response strings contain a 1 where a black peg is present, a 2 where a white peg 
is present, and a 0 as a placeholder indicating no key peg exists in that position.
* Typical saved game after 2 guesses: "0210146 045232 444444 100000 000333 110000"

--------------------------------------* 5 *--------------------------------------
The game provides a simple text-based interface to your system.
---------------------------------------------------------------------------------
The game boasts:
	* A colourful text interface using ANSI escape codes to colour the words appropriately.
	* A neat game board with appropriate spacing is included, with guess number on the
	left hand side and feedback pegs on the right.
	* The terminal screen is cleared in between guesses to allow a new game board to be
	printed to the terminal without cluttering it.

--------------------------------------* 6 *--------------------------------------
The game provides an intelligent computer-driven code breaker.
---------------------------------------------------------------------------------
* Each position gets a list with all possible colours limited by the number of colours.
* A random value is selected from each list to select the next attempt.
* If the next attempt has no black key pegs, each respective colour is removed from that 
position's list.
* If the next attempt has no white key pegs either, all the colours in the attempt must 
be removed from all the lists.
* This process repeats itself until the code is guessed or guess limit exceeded.

--------------------------------------* 7 *--------------------------------------
The user is able to specify the number (between 8-50) of attempts in the game
---------------------------------------------------------------------------------
* A choice is given to the user as to how many attempts the code breaker has.
* This means the game difficulty can be increased or decreased.
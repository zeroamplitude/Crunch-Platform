import java.util.Random;
import java.util.List;
import java.io.IOException;
import java.lang.String.*;

public class Player2 {

	public static String teamname;
	public static char boardMatrix[][] = new char[5][5];
	public static String boardAsString;
	public static String previousMove;
	
	/**
	 * Constructor
	 */
	public Player2() {

		// Set your team name here:
		teamname = "p2";

		// Construct the BoardMatrix and 
		// StringAsBoard as free spaces
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				boardMatrix[i][j] = 'O';
				// ?? For you string lovers...lol
				boardAsString += 'O';  
			}
		}
		previousMove = null;
	}

	/**
	 * getTeamname:
	 * 			This method send the teamname to the
	 *			client.
	 * @return teamname
	 */
	public static String getTeamname() {
		return teamname;
	}

	/**																		
	 * Move - 	This function communicates with the 
	 *			server placing your move.
	 *											
	 * @param  	playerMove - An array fo chars to be 
	 *						 converted to integers.										
	 * @return 	move - An array of integers passed 
	 * 				   to the server.				
	 */																		
	public static int[] Move() {							
		int[] myMove = new int[4];											
		myMove = decode(move());											
		return myMove;														
	}						
																		
	/**																		
	 * opMove -  This function receives your oppon
	 *				-ents move from the server.
	 *
	 * @param	opMove - An array of integers that re
	 *                   -presents your opponents move.
	 * @return 	move - A string that represents a move
	 *                 made by your opponent.								
	 */																		
	public static void opMove(List opMove) {
		int[] conv = new int[4];
		for (int i = 0; i < 4; i++) {
			conv[i] = (int) opMove.get(i);
		}		
		previousMove = encode(conv);				
	}																		

	/**
	 * updateBoardMatrix
	 *			This function receives the current state of
	 *          the board from the Server and updates the 
	 *			boardMatrix
	 * @param board - A matrix representing the current board
	 *                state
	 */
	public static void updateBoard(List<List> board) {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (board.get(i).get(j) == null) {
					boardMatrix[i][j] = 'O';
					if (i == 0 && j ==0) {
						boardAsString = "O";
					} else {
						boardAsString += 'O';
					}
				} else  if (board.get(i).get(j) == 0) {
					boardMatrix[i][j] = 'B';
					if (i == 0 && j ==0) {
						boardAsString = "B";
					} else {
						boardAsString += 'B';
					}
				} else if (board.get(i).get(j) == 1) {
					boardMatrix[i][j] = 'R';
					if (i == 0 && j ==0) {
						boardAsString = "R";
					} else {
						boardAsString += 'R';
					}
				} else if (board.get(i).get(j) == 2) {
					boardMatrix[i][j] = 'M';
					if (i == 0 && j ==0) {
						boardAsString = "M";
					} else {
						boardAsString += 'M';
					}
				}
			}
		}
	}

	/**
	 * decode - This function decodes an a string 
	 *			into a array of integers.
	 *
	 * @param	playerMove - A string that repres
	 *          			 -ents your move.
	 * @return 	decoded - An array of integers to
	 *          		  that represents a move.
	 */
	public static int[] decode(String playerMove) {

		int[] decoded = new int[4];
		int temp = 0;

		for (int i = 0; i < 4; i++) {
			char puzzle = playerMove.charAt(i);
			if (i == 0 || i == 2) {
				if (puzzle == 'A') {
					temp = 0;
				} else if (puzzle == 'B') {
					temp = 1;
				} else if (puzzle == 'C') {
					temp = 2;
				} else if (puzzle == 'D') {
					temp = 3;
				} else if (puzzle == 'E') {
					temp = 4;
				} else {
					break;
				}
			} else {
				temp = Character.getNumericValue(puzzle) - 1;
			}
			decoded[i] = temp;
		}
		return decoded;
	}


	/**
	 * encode - This function encodes an integer move
	 *          received from the server.
	 *
	 * @param 	move - An integer array that represents
	 * 			       a move.
	 * @return 	A
	 */
	public static String encode(int[] move) {

		char[] temp = new char[4];

		for (int i = 0; i < 4; i++) {
			int puzzle = move[i];
			if (i == 0 || i == 2) {
				if (puzzle == 0) {
					temp[i] = 'A';
				} else if (puzzle == 1) {
					temp[i] = 'B';
				} else if (puzzle == 2) {
					temp[i] = 'C';
				} else if (puzzle == 3) {
					temp[i] = 'D';
				} else if (puzzle == 4) {
					temp[i] = 'E';
				} else {
					break;
				}
			} else {
				puzzle = puzzle + 1;
				temp[i] = Character.forDigit((puzzle),10);
			}
		}
		String encoded = new String(temp);

		return encoded;
	}
	
	/**::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	 * Original method - Place your algorithm in here:::::::::::::::::::::::::::::::::::::::::::::::::::
	 * ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	 * ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	 */
	public static String move() {
			
			String playerMove = null;

			System.out.println("G00000000: " + boardAsString);
			

			System.out.println("Board as matrix");

			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++){
					
				}
				System.out.println(boardMatrix[i]);
			}

			System.out.println("Previous move" + previousMove);
			
			
			///////////////////////////////////////////////////////
			// 
			//
			// INSERT YOUR ALGORITHM BELOW
			//
			// THE MOVE MUST BE STRING IN FORMAT A1A2 WHERE A1 REPRESENTS ONE SQUARE AND A2 THE OTHER
			// THE HORIZONTAL AXIS OF THE BOARD IS A -> E
			// THE VERTICAL AXIS OF THE BOARD IS 1 -> 5
			// THE LETTERS ARE CASE SENSITIVE
			// 
			//
			// NOTE THAT THE GIVEN MATRIX IS NUMBERED 0 -> 4 IN EACH DIMENSION
			// THE GIVEN MATRIX REPRESENTS THE CURRENT STATE OF THE BOARD
			// EX. A1 IS boardMatrix[0][0] AND E5 is boardMatrix[4][4]
			// CHAR O ON A COORD MEANS SPOT IS VACANT, R AND B REPRESENT THE PLAYER MOVES AND M THE GREY SQUARES
			// YOU CAN ONLY PLACE PIECES ON 2 ADJACENT O SPACES, IT IS YOUR RESPONSIBILITY TO MAKE SURE THE MOVE IS VALID
			//
			// NOTE ALONG WITH THE GIVE BOARD ... THE PREVIOUS MOVE IS AVAILABLE IN STRING previousMove
			//
			////////////////////////////////////////////////////////
			


			/*******************      Nick's Comments     *******************/
			// All the methods above this point are used to convert the
			// python client's variables to match the variables you were given
			// in the original program. My recommendation is to not use them in
			// your algorithm.
			//
			// One thing you may have noticed is that the main function has been
			// removed from this program. This is because this program is called
			// as an object class in Pyva.java which allows for python to access
			// variables in the JVM. Therefore, a main method is not required for
			// this program. 
			// 
			// While running the CramClient.py the output of this program can be
			// seen in the terminal that is running the Pyva class.
			// 
			// The next part of my comments make it clear how you should interact
			// with this program.     
			//   
			// VARIABLES:: 
			//  
			// There were three global variables which were provided to you
			// originally:
			//
			// 		1) boardAsString - A string of length 25 that represented
			//                         the board state. CHAR 'O' being VACANT,
			//						   CHAR 'R' and 'B' being PLAYER MOVES and
			//						   CHAR 'M' being MASTER BLOCKS.
			//
			//		2) boardMatrix   - A 5x5 char matrix that represents the
			//                         current board state. CHAR 'O' being VACANT,
			//						   CHAR 'R' and 'B' being PLAYER MOVES and
			//						   CHAR 'M' being MASTER BLOCKS.
			// 
			//      3) previousMove  - A String of length 4 that represent the
			//                         last move that your opponent made. It is
			//						   of the form: eg. A1B1
			//
			// These variables are all the same in this program as well as, have
			// the same name and format as the previous version.
			// 
			// An example is if your algorithm used boardMatrix to check for
			// empty spaces on the board. You would have created a loop that
			// checked each position of boardMatrix to see if it was free or
			// not.
			//
			// An example of that for loop would look something like this:
			// 
			// *Note: Don't use this loop, as I have intentionally left parts
			//        out
			//  
			// bool freeblock = False;
			// for (int i = 0; i < 5, i++) {
			// 		for (int j = 0; j < 5; j++) {
			//			if (boardMatrix[i][j] == 'O' && boardMatrix[i][j+1] == 'O') {
			//				freeblock = True;
			// 			}
			// 		}
			// }
			//
			// In this version of the program the variable work in the same 
			// way and also HAVE THE SAME NAMES as in the original program. This 
			// was done so that you would not have to change the variables in
			// your code, making your lives easier and preventing my email from 
			// being blasted. 
			//
			// METHODS::
			//
			// Originally, there was one method that you needed to implement:
			// 
			// 1) public static String move() {
			//			playerMove = ??
			//			return playerMove
			//	  }
			//   
			//    The ?? is where you would call your algorithm which could be 
			//    a method or an object class that would compute and return 
			//    your move as a String. eg. A1B1.
			//
			//    If you look below my comments nothing has changed.     
			//
			//
			// Here are some tips for successful integration between the
			// original program and this one.
			//
			// 1) Don't use the functions above move(). Those functions will
			//    not make sense and are only there to convert python objects
			//    into the java objects that you were originally given and to 
			//    convert the moves that you send to the server into the format
			//    that the server requires. 
			// 
			// 2) Use the 3 global variables as you did in the previous version.
			//    Although, for anyone who parsed boardAsString... why?
			//
			// 3) Think of this program as an interface that provides you with 
			//    three variables and you are going to use those variables to 
			//    implement the function move() with your algorithm.
			//
			// My email if you spot any problems or have concerns:
			// nicholas.desouza@uoit.net  

			System.out.println("Team: " + teamname + "'s move");
			playerMove = myMove(); // <----- After you have tested the server --<<<
								   // <----- connection remove myMove() and ----<<<
								   // <----- replace it with your algorithm ----<<<
			
			
			//////////////////////////////////////////////////////
			// END OF ALGORITHM
			//////////////////////////////////////////////////////
			
			return playerMove;
				
			
		}

		/**
		 * randMove():
		 * 		This is the basic algorithm I used to test the
		 *      server. 
		 *
		 * @return move - returns and array of integer value
		 *                representing a player move
		 */
		public static String myMove() {

			Random rd = new Random();
			int[] move = new int[4];

			// This sets x1 y1
			int x1 = rd.nextInt(5);
			int y1 = rd.nextInt(5);
			int x2 = 0;
			int y2 = 0;

			int xory = rd.nextInt(2);
			int norp = rd.nextInt(2);
			int c = 0;
			if (norp == 0) {
				c = -1;
			} else {
			 	c = 1;
			}
			if (xory == 1) {
				x2 = x1 + c;
				if (x2 < 0 || x2 > 4) {
					x2 = x1 + (-c);
				}
				y2 = y1;
			} else {
				y2 = y1 + c;
				if (y2 < 0 || y2 > 4) {
					y2 = y1 + (-c);
				}
				x2 = x1;
			}

			move[0] = x1;
			move[1] = y1;
			move[2] = x2;
			move[3] = y2;

			return encode(move);
		}
}
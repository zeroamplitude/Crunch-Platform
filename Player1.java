import java.util.Scanner;
import java.util.Random;
import java.util.List;
import java.io.IOException;
import java.lang.String.*;

public class Player1 {

	public static String teamname;
	public static char boardMatrix[][] = new char[5][5];
	public static String boardAsString;
	public static String previousMove;
	Scanner in = new Scanner(System.in);
	
	/**
	 * Constructor
	 */
	public Player1() {

		// Set your team name here:
		teamname = "p1";

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
	 * opMove -  This function recieves your oppon
	 *				-ents move from the server.
	 *
	 * @param	opMove - An array of integers that re
	 *                   -presents your opponets move.											
	 * @return 	move - A string that represents a move
	 *                 made by your opponent.								
	 */																		
	public static void opMove(List opMove) {
		int[] conv = new int[4];
		System.out.print(teamname + ": ");
		for (int i = 0; i < 4; i++) {
			conv[i] = (int) opMove.get(i);
			System.out.print(conv[i]);
		}
		System.out.print(" : ");		
		previousMove = encode(conv);												
		System.out.println(previousMove);

	}																		

	/**
	 * updateBoardMatrix
	 *			This function recieves the current state of
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
					boardMatrix[i][j] = 'R';
					if (i == 0 && j ==0) {
						boardAsString = "R";
					} else {
						boardAsString += 'R';
					}
				} else if (board.get(i).get(j) == 1) {
					boardMatrix[i][j] = 'B';
					if (i == 0 && j ==0) {
						boardAsString = "B";
					} else {
						boardAsString += 'B';
					}
				} else if (board.get(i).get(j) == 2) {
					boardMatrix[i][j] = 'M';
					if (i == 0 && j ==0) {
						boardAsString = "M";
					} else {
						boardAsString += 'M';
					}
				}
				System.out.print(boardMatrix[i][j]);
				if (j == 4) {
					System.out.println();
				}
			}
		}
		System.out.println(boardAsString);
		System.out.println();
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
				System.out.println(temp);
			}
			decoded[i] = temp;
		}
		return decoded;
	}


	/**
	 * encode - This function encodes an integer move
	 *          recived from the server.
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
	 * Orignial method - Place your algorithm int here:::::::::::::::::::::::::::::::::::::::::::::::::::
	 * ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	 * ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	 */
	public static String move() {
			
			String playerMove = null;
			

			//System.out.println("Board as matrix");
			
			// for (int index = 0; index < 25; index++) {
				
			// 	boardMatrix[index%5][index/5] = boardAsString.charAt(index);
			// 	if (index%5 == 4) {
			// 		System.out.print(boardMatrix[index%5][index/5] + "\n");
			// 	} else {
			// 		System.out.print(boardMatrix[index%5][index/5] + " ");
			// 	}
				
			// }
			//System.out.println("Previous move: " + previousMove);
			
			
			///////////////////////////////////////////////////////
			// 
			//
			// INSERT YOUR ALGORITHM BELOW
			//
			// THE MOVE MUST BE STRING IN FORMAT A1A2 WHERE A1 REPRESESNTS ONE SQUARE AND A2 THE OTHER
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
			
			playerMove = encode(myMove()); 
			
			
			
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
		 * @return move - returns and array of interger valuse
		 *                representing a player move
		 */
		public static int[] myMove() {

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

			// for (int i = 0; i < 4; i++) {
			// 	System.out.println(move[i]);
			// }

			return move;
		}


		/**
		 * manualMove():
		 * 		Enables you to enter your moves manually.
		 * @return move - returns an array of integer values
		 *				  representing a move
		 */
		public int[] manualMove() {
			
			int[] move = new int[4];

			System.out.println("x1: ");
	        move[0] = in.nextInt();
	        System.out.println("y1: ");
	        move[1] = in.nextInt();
	        System.out.println("x2: ");
	        move[2] = in.nextInt();
	        System.out.println("y2: ");
	        move[3] = in.nextInt();

	        return move;
		}
	

}
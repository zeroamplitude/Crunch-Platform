/*******************      Nick's Comments     *******************/
These comments have been included in the Player1.java and
Player2.java files just above where you place your algorithm.

All the methods above this point are used to convert the
python client's variables to match the variables you were given
in the original program. My recommendation is to not use them in
your algorithm.
One thing you may have noticed is that the main function has been
removed from this program. This is because this program is called
as an object class in Pyva.java which allows for python to access
variables in the JVM. Therefore, a main method is not required for
this program.

While running the CramClient.py the output of this program can be
seen in the terminal that is running the Pyva class.

The next part of my comments make it clear how you should interact
with this program.

VARIABLES::

There were three global variables which were provided to you
originally:
		1) boardAsString - A string of length 25 that represents
                        the board state. CHAR 'O' being VACANT,
					   CHAR 'R' and 'B' being PLAYER MOVES and
					   CHAR 'M' being MASTER BLOCKS.
	2) boardMatrix   - A 5x5 char matrix that represents the
                        current board state. CHAR 'O' being VACANT,
					   CHAR 'R' and 'B' being PLAYER MOVES and
					   CHAR 'M' being MASTER BLOCKS.

     3) previousMove  - A String of length 4 that represent the
                        last move that your opponent made. It is
					   of the form: eg. A1B1
These variables are all the same in this program as well as, have
the same name and format as the previous version.

An example is if your algorithm used boardMatrix to check for
empty spaces on the board. You would have created a loop that
checked each position of boardMatrix to see if it was free or
not.
An example of that for loop would look something like this:

*Note: Don't use this loop, as I have intentionally left parts
       out

bool freeblock = False;
for (int i = 0; i < 5, i++) {
		for (int j = 0; j < 5; j++) {
		if (boardMatrix[i][j] == 'O' && boardMatrix[i][j+1] == 'O') {
			freeblock = True;
			}
		}
}
In this version of the program the variable work in the same
way and also HAVE THE SAME NAMES as in the original program. This
was done so that you would not have to change the variables in
your code, making your lives easier and preventing my email from
being blasted.
METHODS::
Originally, there was one method that you needed to implement:

1) public static String move() {
		playerMove = ??
		return playerMove
  }

   The ?? is where you would call your algorithm which could be
   a method or an object class that would compute and return
   your move as a String. eg. A1B1.
   If you look below my comments nothing has changed.
Here are some tips for successful integration between the
original program and this one.
1) Don't use the functions above move(). Those functions will
   not make sense and are only there to convert python objects
   into the java objects that you were originally given and to
   convert the moves that you send to the server into the format
   that the server requires.

2) Use the 3 global variables as you did in the previous version.
   Although, for anyone who parsed boardAsString... why?
3) Think of this program as an interface that provides you with
   three variables and you are going to use those variables to
   implement the function move() with your algorithm.
My email if you spot any problems or have concerns:
nicholas.desouza@uoit.net
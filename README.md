Crunch-Platform


Requirements:

	Ubuntu
	Python2.7
		- pygames
		- PodSixNet
		- 

	openjdk-7-jdk

Installation - 

	Debian Linux:

		Open a terminal and type the followin commands:

			sudo apt-get install git

			sudo apt-get install openjdk-7-jdk

			sudo apt-get install python-pygame

			sudo easy_install py4j

		Now in terminal navigate to the directory you want to store the files in.
		For myself I choose Desktop. Here is an example:
			
			I cd (change directories) to home
				
				cd
			
			Then I cd to Desktop

				cd Desktop

		Now type the following command:

			git clone https://github.com/ncdesouza/Crunch-Platform.git

		If you type ls you will see a new directory called Crunch-Platform. Navigate to that
		directory with the following command:

			cd Crunch-Platform

		If you type ls you will see the following files/directories:

			images/
			PodSixNet-78/
			CramClient.py
			CramClient2.py
			Player1.java
			Player2.java
			py4j0.9.jar
			Pyva.java
			README.md

		The last installatioon is PodSixNet. Type the following command to setup PodSixNet:

			sudo python PodSixNet-78/setup.py install

		You are now done with the installation. please see Configuration below for getting 
		started and getting you algorithm operational.


Configuration:
	1) Edit Player1.java and Player2.java

		Open Player1.java and Player2.java in your favourite text editor or IDE.
		
		To test the client on your computer I left a random algorithm in the file. Leave that there for now and once you are able to establish a game you can put your algorithm into the file.

		Change your team name:

			** Important: 
				Please limit the size of team names to 5 character. This is to keep the GUI clean.  

			The server will only accept unique team names so in the Player1 constructor put your team name there. Do the same with Player2.java except use a different name or add a number on the end.

			ex. Nick
				Nick2

	2) Compile sources:

		From terminal cd to the Crunch-Platform directory and type the following commands:
		
			javac Player1.java Player2.java

			javac -cp '.:py4j0.9.jar' Pyva.java


	That is 

	3) Launch the Java-Python Gateway

		You will only need to run Pyva.java this program will take care of calling your algorithms from Player1.java and Player2.java

			java -cp '.:py4j0.9.jar' Pyva.java

	4) Launch 


5) :
		Player vs Bot:		Play against the servers AI
				- 

		Player vs Player: 	Play against another AI agent
				- 

		Tournament:			Play in a tournament with other AI agents


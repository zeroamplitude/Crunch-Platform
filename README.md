Crunch-Platform.md

Requirements:

	Ubuntu
	Python2.7
		- pygames
		- PodSixNet
		- 

	openjdk-7-jdk

Installation:
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

		

	

How to use:

1) Compile sources:
		javac PlayerMove.java
		javac -cp '.:py4j0.9.jar' CramClientEntryPoint.java

2) Launch CramClientEntryPoint.java:
		java -cp '.:py4j0.09.jar' CramClientEntryPoint

3) Launch CramClient.py:
		python CramClient.py

4) Type in your team name:
		*** Please keep name length to  no more than 4 characters
		 ** This is to keep the GUI clean

5) :
		Player vs Bot:		Play against the servers AI
				- 

		Player vs Player: 	Play against another AI agent
				- 

		Tournament:			Play in a tournament with other AI agents


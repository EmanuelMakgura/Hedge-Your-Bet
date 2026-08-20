Hedge Your Bet
A five-question multiple-choice quiz game built with Java Swing, themed around South African provinces. The player can hedge their bet on each question by checking one, two, or all three JCheckBox answer options depending on how confident they are.
Files
File	Description
HedgeYourBet.java	The base quiz game.
HedgeYourBetUsingFile.java	Same game, but saves the score to score.txt and displays the previous game's score at startup.
How to Play
Read the question at the top of the window.
Check one, two, or all three boxes:
Check just one box if you're sure of the answer.
Check two boxes if you're narrowing it down but not certain.
Check all three if you have no idea (guaranteed but minimal points).
Click Submit to lock in your answer and move to the next question.
After all five questions, a results dialog shows your final score and a message based on how you did.
Scoring
Boxes checked	Result	Points
1	Correct	5
2 (correct answer included)	Correct	2
3 (all boxes)	Always includes correct answer	1
1 or 2	Correct answer NOT included	0
Maximum possible score: 25 points (5 points x 5 questions).
Final Message
Score	Message
More than 21	Fantastic!
More than 15	Very good
15 or fewer	OK
Compiling and Running
From a terminal, in the folder containing the .java files:
# Base version
javac HedgeYourBet.java
java HedgeYourBet

# File-persistence version
javac HedgeYourBetUsingFile.java
java HedgeYourBetUsingFile
Requires a JDK (Java 8 or later) to compile and run.
About HedgeYourBetUsingFile
This version adds simple score persistence:
On startup, it reads score.txt (created in the same directory the program is run from) and displays that value as "Previous score."
If score.txt doesn't exist yet — i.e. the first time the game is ever played — the previous score defaults to 0.
When the quiz ends, the current game's score overwrites score.txt, so it becomes the "previous score" the next time the game is launched.
Topic
All questions and answer choices are about South African provinces (Gauteng, Western Cape, and KwaZulu-Natal), covering capitals, nicknames, major cities, and population.

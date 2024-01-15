## **Checkers**

### *Description*
A simple game of checkers. A well beloved 2 player game, easy to play, but hard to master. 2 teams of checkers, white and black face off across a 8x8 checkerboard, until only one color is left standing.

### *Instructions*
Left-click on a checker to select it, and right-click on a green highlighted space to move it. Press the start button to initialize the game

### *Code description*

The code is split across two components, an executable object named "Main and an accompanying class dubbed "Checker". 

The main is responsible for all the front-end code of the program, involving the input and final output, displayed within the visual checkerboard. In practice, the "Main" detects player actions or triggers, and transmits them over to the underlying Checker class for processing. Such triggers mainly amount to 2 of the mouse buttons, "left click" and "right click" as well as the button "start"; each defined in a couple functions to detect and transmit signals, represented as variables across the entire program when conditions are true. 

Once these input signals are processed, they are returned to the "Main" object, which will produce the necessary graphical updates, which the player would directly notice in real time.

In between the input and output processes lies the class Checker, responsible for all the technical side of things as well as all the necessary supporting requirements. The primary component of the Checker class, and by extension the game itself is the array "spaceOccupancy". This array is representative of all the interactive checkers in the framework of the game, holding information for each one of them. Every time a trigger is made, in the context of a mouse click, the array is updated. This in turn returns signals of its own back to the Main, subesquently used to update the graphical situation according to recent events.

### *Screenshots*
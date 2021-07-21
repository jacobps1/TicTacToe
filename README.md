# TicTacToe
Implementation of Tic-tac-toe via the Android Studio platform

NOTE: Make sure to delete .idea folder for personal use.

The idea for this project came to me one day when I was watching my little brothers play tic-tac-toe at the house. 
I thought to myself how could I create a more entertaining way to play tic-tac-toe? As a result, I created an Android 
application with a simple GUI that contained three playable versions of tic-tac-toe with support for landscape and 
portrait orientations, and used saved preferences to keep track of light/dark modes for a more user-customized experience.

The application contained a ViewModel that contained the information on the game mode selected and the boards used to make 
sure that the UI could find their references during screen rotation or other life-cycle ‘inducing’ events. In addition, the 
application consisted of many fragments to display the different aspects of the app such as the title screen, the display of 
the single tic-tac-toe board, and the display of the triple tic-tac-toe boards. 

In terms of implementing the logic behind the game, a custom data type named TicTacToeBoard represented a tic-tac-toe board 
and a custom data type named GameSymbol represented the player symbols. Inside of the tic-tac-toe data type was an encapsulated 
2D array that represented the abstract dimensions of the board and the methods for adding the entries and checking to see if 
there was a three-in-a-row.  

In terms of the visuals for the game modes, the board layouts consisted of a Table with TextViews that updated upon clicking 
to the right symbol. For the triple board game mode, a RecyclerView displayed the list of the boards to conserve memory. 

Files:
package com.example.tictactoe:
MainActivity.kt - represents the main activity for the application.
GameViewModel.kt - represents the ViewModel for the applcation.

package com.example.tictactoe.fragments:
OriginalBoardFragment.kt - represents the fragment for the single game board GUI.
ThreeBoardFragment.kt - represents the fragment for the triple game board GUI.
TitleScreenFragment.kt - represents the fragment for the title screen GUI.

package com.example.tictactoe.gamepieces:
GameSymbol.kt - represents the symbol placed by a player throughout the game.
TicTacToeBoard.kt - represents the game board used throughout the game.

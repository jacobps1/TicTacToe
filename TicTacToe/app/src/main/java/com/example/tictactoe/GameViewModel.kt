package com.example.tictactoe

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tictactoe.gamepieces.OItem
import com.example.tictactoe.gamepieces.TicTacToeBoard
import com.example.tictactoe.gamepieces.XItem


class GameViewModel : ViewModel() {
    //represents the original game board
    private var gameBoard: TicTacToeBoard = TicTacToeBoard()

    //live data that updates the original board GUI
    private val boardLiveData = MutableLiveData<TicTacToeBoard>()
    val board: LiveData<TicTacToeBoard>
        get() = boardLiveData

    //keeps track of the player's turns
    private var playerOneTurn: Boolean = true

    //live data that updates the turn label GUI
    private val playerTurn = MutableLiveData<Boolean>()
    val turn: LiveData<Boolean>
        get() = playerTurn

    //live data that keeps track of whether the game is over or not
    private val _gameOver = MutableLiveData<Boolean>()
    val gameOver: LiveData<Boolean>
        get() = _gameOver

    //0 means classic, 1 means x's only, 2 means x's only 3 boards
    var gamemode: Int = 0

    //represents the three game boards
    private var threeBoards: MutableList<TicTacToeBoard>

    //live data that updates the three boards GUI
    private val _threeGameBoards = MutableLiveData<List<TicTacToeBoard>>()
    val threeGameBoards: LiveData<List<TicTacToeBoard>>
        get() = _threeGameBoards

    //number of turns done in the original game
    var numTurns = 0

    init {
        playerTurn.value = true
        threeBoards = ArrayList()
    }

//sets the game mode in order to determine how to set up the boards
    fun setGameMode(gamemode: Int){
        this.gamemode = gamemode

        //if the game mode is x's only three boards
        if (gamemode == 2){
            threeBoards.clear()
            for (i: Int in 0..2) {
                threeBoards.add(TicTacToeBoard())
            }
            _threeGameBoards.value = threeBoards
        }

    }
//conducts a player's turn across all three game modes
    fun turn(i: Int, j: Int, board: Int){
        //original game mode
        if (gamemode == 0) {
            //player one turn
            if (playerOneTurn) {
                gameBoard.changeEntry(i, j, XItem(Color.BLACK))
                boardLiveData.value = gameBoard

                //increment number of turns
                numTurns++

                //check to see if there is three-in-a-row
                if (gameBoard.threeInARow(XItem(Color.BLACK), i, j)) {
                    _gameOver.postValue(true)

                } else {

                    playerOneTurn = false
                    playerTurn.value = playerOneTurn
                }

            }

            //player two goes
            else {


                gameBoard.changeEntry(i, j, OItem(Color.BLACK))
                boardLiveData.value = gameBoard

                //increment number of turns
                numTurns++

                //check to see if there is three-in-a-row
                if (gameBoard.threeInARow(OItem(Color.BLACK), i, j)) {
                    _gameOver.postValue(true)
                } else {
                    playerOneTurn = true
                    playerTurn.value = playerOneTurn
                }

            }
        }
        //if the game mode is x's only
        else if (gamemode == 1) {
            turnOtherX(i, j)
        }

        //if the game mode is x's only three boards
        else {
            turnOtherBoards(i, j, board)
        }
    }
//turn logic for x's only game mode
    private fun turnOtherX(i: Int, j: Int){

            //player one turn
            if (playerOneTurn) {
                gameBoard.changeEntry(i, j, XItem(Color.BLACK))
                boardLiveData.value = gameBoard

                //check to see if there is three-in-a-row
                if (gameBoard.threeInARow(XItem(Color.BLACK), i, j)) {
                    _gameOver.postValue(true)

                } else {

                    playerOneTurn = false
                    playerTurn.value = playerOneTurn
                }

            }

            //player two goes
            else {


                gameBoard.changeEntry(i, j, XItem(Color.BLACK))
                boardLiveData.value = gameBoard

                //check to see if there is three-in-a-row
                if (gameBoard.threeInARow(XItem(Color.BLACK), i, j)) {
                    _gameOver.postValue(true)
                } else {
                    playerOneTurn = true
                    playerTurn.value = playerOneTurn
                }

            }

    }
//turn logic for x's only three boards game mode
    private fun turnOtherBoards(i: Int, j: Int, board: Int){

        //if there are boards left in the game

        if (threeBoards.isNotEmpty()) {
            //player one turn
            if (playerOneTurn) {
                //change the entry and update the board
                threeBoards[board].changeEntry(i, j, XItem(Color.BLACK))
                _threeGameBoards.value = threeBoards

                //check to see if there is three-in-a-row
                if (threeBoards[board].threeInARow(XItem(Color.BLACK), i, j)) {

                    threeBoards.removeAt(board)

                }
                

                playerOneTurn = false
                playerTurn.value = playerOneTurn
                

            }

            //player two goes
            else {

                threeBoards[board].changeEntry(i, j, XItem(Color.BLACK))
                _threeGameBoards.value = threeBoards
                //check to see if there is three-in-a-row
                if (threeBoards[board].threeInARow(XItem(Color.BLACK), i, j)) {

                    threeBoards.removeAt(board)
                }
                

                playerOneTurn = true
                playerTurn.value = playerOneTurn
                

            }
            if (threeBoards.isEmpty()){
                _gameOver.postValue(true)
                playerOneTurn = !playerOneTurn
                playerTurn.value = playerOneTurn
            }

        }
        //no more boards left meaning the game is over
        else {
            _gameOver.postValue(true)
        }
    }


    //reset the game
    fun reset(){
        numTurns = 0

        playerTurn.value = true
        playerOneTurn = true

        _gameOver.postValue(false)

        gameBoard = TicTacToeBoard()
        boardLiveData.value = gameBoard

        setGameMode(gamemode)



    }


}

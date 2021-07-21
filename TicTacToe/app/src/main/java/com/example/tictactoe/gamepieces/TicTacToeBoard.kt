package com.example.tictactoe.gamepieces

import android.graphics.Color

class TicTacToeBoard{
    //create 3x3 tic tac toe board
    private val board: Array<Array<GameSymbol>> =
        arrayOf(arrayOf(GameSymbol(Color.BLACK), GameSymbol(Color.BLACK), GameSymbol(Color.BLACK)), arrayOf(GameSymbol(Color.BLACK), GameSymbol(Color.BLACK), GameSymbol(Color.BLACK)), arrayOf(GameSymbol(Color.BLACK), GameSymbol(Color.BLACK), GameSymbol(Color.BLACK)))

    //checks to see if there is a three-in-a-row
    fun threeInARow(prevTurn: GameSymbol, i: Int, j: Int): Boolean{

        //check horizontal
        var count = 0

        for (index: Int in 0..2){
            //if a symbol doesn't equal another symbol in the same row, then break out of the loop
            if (getEntry(index, j).getLetter() != prevTurn.getLetter()){

                break
            }
            count++
        }
        //check to see if all three symbols matched in the row
        if (count == 3){
            return true
        }

        count = 0
        //check vertical
        for (index: Int in 0..2){

            //if a symbol doesn't equal another symbol in the same column, then break out of the loop
            if (getEntry(i, index).getLetter() != prevTurn.getLetter()){

                break
            }
            count++
        }
        //check to see if all three symbols matched in the column
        if (count == 3){
            return true
        }

        //check diagonals if recent letter was placed on diagonal
        if (i == j || (i == 0 && j == 2) || (i == 2 && j == 0)){

            return (((getEntry(0, 0).getLetter() == getEntry(1,1).getLetter() && getEntry(1,1).getLetter() == prevTurn.getLetter()) && (getEntry(1,1).getLetter() == getEntry(2,2).getLetter()) && getEntry(1,1).getLetter() == prevTurn.getLetter()) ||
                    ((getEntry(2, 0).getLetter() == getEntry(1,1).getLetter() && getEntry(1,1).getLetter() == prevTurn.getLetter()) && (getEntry(1,1).getLetter() == getEntry(0,2).getLetter() && getEntry(1,1).getLetter() == prevTurn.getLetter())))

        }

        return false

    }

    //change the entry at the location on the board
    fun changeEntry(i: Int, j: Int, item: GameSymbol){
        board[i][j] = item
    }

    //get the entry located at the location on the board
    fun getEntry(i: Int, j: Int): GameSymbol{
        return board[i][j]
    }
}
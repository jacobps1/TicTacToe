package com.example.tictactoe.gamepieces


open class GameSymbol(private val color: Int) {
    private var letter = " "

    fun getSymbolColor(): Int{
        return color
    }
    fun getLetter(): String {
        return letter
    }
    fun setLetter(letter2: String){
        letter = letter2
    }
}

class XItem(color: Int): GameSymbol(color){
    init {
        super.setLetter("X")
    }

}

class OItem(color: Int): GameSymbol(color){
    init {
        super.setLetter("O")
    }

}

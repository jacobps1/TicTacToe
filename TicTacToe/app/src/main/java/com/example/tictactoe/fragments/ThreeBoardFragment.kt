package com.example.tictactoe.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tictactoe.GameViewModel
import com.example.tictactoe.R
import com.example.tictactoe.gamepieces.TicTacToeBoard

class ThreeBoardFragment : Fragment() {
    private val viewModel: GameViewModel by activityViewModels()
    private val adapter = BoardGameAdapter()

    lateinit var tempBoard: List<TicTacToeBoard>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        setHasOptionsMenu(true)

        viewModel.threeGameBoards.observe(viewLifecycleOwner, { boards ->
            tempBoard = boards
            adapter.setGames(boards)

        })

        return inflater.inflate(R.layout.fragment_three_board_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val recyclerView = view.findViewById<RecyclerView>(R.id.tictactoeBoards)
        val resetButton = view.findViewById<Button>(R.id.resetThree)
        val turnLabel = view.findViewById<TextView>(R.id.playerTurnLabel)

        recyclerView.adapter = adapter
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        else {
            recyclerView.layoutManager = LinearLayoutManager(context)
        }

        resetButton.setOnClickListener {

            viewModel.reset()

        }

        viewModel.turn.observe(viewLifecycleOwner, { turn ->
            //if player one turn
            if (turn){
                turnLabel.text = resources.getString(R.string.player_one_turn)
            }
            else {
                turnLabel.text = resources.getString(R.string.player_two_turn)
            }
        })

        viewModel.gameOver.observe(viewLifecycleOwner, { status ->
            if (status){
                enableDisableView(view, false)
                resetButton.isClickable = true
                if (turnLabel.text == resources.getString(R.string.player_one_turn)){

                    //if the game is classic version
                    if (viewModel.gamemode == 0) {
                        turnLabel.text = resources.getString(R.string.playeronewins)
                    }
                    else {
                        turnLabel.text = resources.getString(R.string.playertwowins)
                    }
                }
                else {
                    if (viewModel.gamemode == 0) {
                        turnLabel.text = resources.getString(R.string.playertwowins)
                    }
                    else {
                        turnLabel.text = resources.getString(R.string.playeronewins)
                    }
                }


            }
        })


    }

    override fun onDestroy() {
        super.onDestroy()
        if (activity?.isChangingConfigurations == false) {
            viewModel.reset()
        }
    }
    private fun enableDisableView(view: View, enable: Boolean){
        view.isClickable = enable
        if (view is ViewGroup) {
            for (idx in 0 until view.childCount) {
                enableDisableView(view.getChildAt(idx), enable)
            }
        }
    }

    inner class BoardGameAdapter :
        RecyclerView.Adapter<BoardGameAdapter.BoardViewHolder>(){

        private var boards = emptyList<TicTacToeBoard>()


        internal fun setGames(boards: List<TicTacToeBoard>) {
            this.boards = boards
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int {

            return boards.size
        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardViewHolder {


            val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.gameboard_poster, parent, false)
            return BoardViewHolder(v)
        }

        override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {

            val upperL = holder.view.findViewById<TextView>(R.id.topLeftText)
            val upperM = holder.view.findViewById<TextView>(R.id.topMiddleText)
            val upperR = holder.view.findViewById<TextView>(R.id.topRightText)
            val middleL = holder.view.findViewById<TextView>(R.id.middleLeftText)
            val middleM = holder.view.findViewById<TextView>(R.id.middlemiddleText)
            val middleR = holder.view.findViewById<TextView>(R.id.middleRightText)
            val lowerL = holder.view.findViewById<TextView>(R.id.bottomLeftText)
            val lowerM = holder.view.findViewById<TextView>(R.id.bottomMiddleText)
            val lowerR = holder.view.findViewById<TextView>(R.id.bottomRightText)

            upperL.text = tempBoard[position].getEntry(0,0).getLetter()
            upperM.text = tempBoard[position].getEntry(0,1).getLetter()
            upperR.text = tempBoard[position].getEntry(0,2).getLetter()

            middleL.text = tempBoard[position].getEntry(1, 0).getLetter()
            middleM.text = tempBoard[position].getEntry(1, 1).getLetter()
            middleR.text = tempBoard[position].getEntry(1, 2).getLetter()

            lowerL.text = tempBoard[position].getEntry(2, 0).getLetter()
            lowerM.text = tempBoard[position].getEntry(2, 1).getLetter()
            lowerR.text = tempBoard[position].getEntry(2, 2).getLetter()

            //activate click listeners for upper row
            upperL.setOnClickListener{
                if (upperL.text == " "){

                    viewModel.turn(0, 0, position)
                    upperL.isClickable = false
                }

            }
            upperM.setOnClickListener{
                if (upperM.text == " "){


                    viewModel.turn(0, 1, position)

                    upperM.isClickable = false
                }

            }
            upperR.setOnClickListener{
                if (upperR.text == " "){

                    viewModel.turn(0, 2, position)

                    upperR.isClickable = false
                }

            }

            //activate click listeners for middle row


            middleL.setOnClickListener{
                if (middleL.text == " ") {

                    viewModel.turn(1, 0, position)
                    middleL.isClickable = false
                }
            }

            middleM.setOnClickListener{
                if (middleM.text == " ") {

                    viewModel.turn(1, 1, position)
                    middleM.isClickable = false
                }
            }

            middleR.setOnClickListener{
                if (middleR.text == " ") {

                    viewModel.turn(1, 2, position)
                    middleR.isClickable = false
                }
            }

            //activate click listeners for bottom row


            lowerL.setOnClickListener{
                if (lowerL.text == " ") {

                    viewModel.turn(2, 0, position)
                    lowerL.isClickable = false
                }
            }

            lowerM.setOnClickListener{
                if (lowerM.text == " ") {

                    viewModel.turn(2, 1, position)
                    lowerM.isClickable = false
                }
            }

            lowerR.setOnClickListener{
                if (lowerR.text == " ") {

                    viewModel.turn(2, 2, position)
                    lowerR.isClickable = false
                }
            }





        }


        inner class BoardViewHolder(val view: View): RecyclerView.ViewHolder(view), View.OnClickListener{
            override fun onClick(view: View?){




            }


        }
    }
}
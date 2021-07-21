package com.example.tictactoe.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.tictactoe.GameViewModel
import com.example.tictactoe.R
import com.example.tictactoe.gamepieces.TicTacToeBoard

class OriginalBoardFragment : Fragment() {

    private val viewModel: GameViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        setHasOptionsMenu(true)
        val view = inflater.inflate(R.layout.fragment_originalboard, container, false)

            viewModel.board.observe(viewLifecycleOwner,{ tempBoard: TicTacToeBoard ->
                view.findViewById<TextView>(R.id.topLeftText).text = tempBoard.getEntry(0,0).getLetter()
                view.findViewById<TextView>(R.id.topMiddleText).text = tempBoard.getEntry(0,1).getLetter()
                view.findViewById<TextView>(R.id.topRightText).text = tempBoard.getEntry(0,2).getLetter()

                view.findViewById<TextView>(R.id.middleLeftText).text = tempBoard.getEntry(1, 0).getLetter()
                view.findViewById<TextView>(R.id.middlemiddleText).text = tempBoard.getEntry(1, 1).getLetter()
                view.findViewById<TextView>(R.id.middleRightText).text = tempBoard.getEntry(1, 2).getLetter()

                view.findViewById<TextView>(R.id.bottomLeftText).text = tempBoard.getEntry(2, 0).getLetter()
                view.findViewById<TextView>(R.id.bottomMiddleText).text = tempBoard.getEntry(2, 1).getLetter()
                view.findViewById<TextView>(R.id.bottomRightText).text = tempBoard.getEntry(2, 2).getLetter()

            })
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val upperL = view.findViewById<TextView>(R.id.topLeftText)
        val upperM = view.findViewById<TextView>(R.id.topMiddleText)
        val upperR = view.findViewById<TextView>(R.id.topRightText)
        val middleL = view.findViewById<TextView>(R.id.middleLeftText)
        val middleM = view.findViewById<TextView>(R.id.middlemiddleText)
        val middleR = view.findViewById<TextView>(R.id.middleRightText)
        val lowerL = view.findViewById<TextView>(R.id.bottomLeftText)
        val lowerM = view.findViewById<TextView>(R.id.bottomMiddleText)
        val lowerR = view.findViewById<TextView>(R.id.bottomRightText)



        val turnLabel = view.findViewById<TextView>(R.id.playerTurnLabel)

        val resetButton = view.findViewById<Button>(R.id.resetButton)

        resetButton.setOnClickListener {

            //view.findNavController().navigate(R.id.titleScreenFragment)
            viewModel.reset()
            enableDisableView(view, true)

        }
        viewModel.turn.observe(viewLifecycleOwner, { turn ->
            //if player one turn
            if (turn){
                turnLabel.text = resources.getString(R.string.player_one_turn)

            }
            else {
                turnLabel.text = resources.getString(R.string.player_two_turn)
            }

            if (viewModel.numTurns == 9){
                turnLabel.text = resources.getString(R.string.tie)
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


        //activate click listeners for upper row
        upperL.setOnClickListener{
            if (upperL.text == " "){

                viewModel.turn(0, 0, 0)
                upperL.isClickable = false
            }

        }
        upperM.setOnClickListener{
            if (upperM.text == " "){

                viewModel.turn(0, 1, 0)
                upperM.isClickable = false
            }

        }
        upperR.setOnClickListener{
            if (upperR.text == " "){

                viewModel.turn(0, 2, 0)

                upperR.isClickable = false
            }

        }

        //activate click listeners for middle row


        middleL.setOnClickListener{
            if (middleL.text == " ") {

                viewModel.turn(1, 0, 0)
                middleL.isClickable = false
            }
        }

        middleM.setOnClickListener{
            if (middleM.text == " ") {

                viewModel.turn(1, 1, 0)
                middleM.isClickable = false
            }
        }

        middleR.setOnClickListener{
            if (middleR.text == " ") {

                viewModel.turn(1, 2, 0)
                middleR.isClickable = false
            }
        }

        //activate click listeners for bottom row


        lowerL.setOnClickListener{
            if (lowerL.text == " ") {

                viewModel.turn(2, 0, 0)
                lowerL.isClickable = false
            }
        }

        lowerM.setOnClickListener{
            if (lowerM.text == " ") {

                viewModel.turn(2, 1, 0)
                lowerM.isClickable = false
            }
        }

        lowerR.setOnClickListener{
            if (lowerR.text == " ") {

                viewModel.turn(2, 2, 0)
                lowerR.isClickable = false
            }
        }


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

}
package com.example.tictactoe.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.tictactoe.GameViewModel
import com.example.tictactoe.R

class TitleScreenFragment: Fragment() {

    private val viewModel: GameViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        setHasOptionsMenu(true)

        return inflater.inflate(R.layout.fragment_title, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val originalB = view.findViewById<Button>(R.id.originalButton)
        val singleB = view.findViewById<Button>(R.id.singleButton)
        val tripleB = view.findViewById<Button>(R.id.tripleButton)
        val darkMode = view.findViewById<Button>(R.id.darkModeButton)

        if (isNightMode(view)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            darkMode.text = resources.getString(R.string.LightMode)
        }

        originalB.setOnClickListener {
            findNavController().navigate(R.id.action_titleScreenFragment_to_originalBoardFragment)
            viewModel.setGameMode(0)
        }

        singleB.setOnClickListener {
            findNavController().navigate(R.id.action_titleScreenFragment_to_originalBoardFragment)
            viewModel.setGameMode(1)
        }

        tripleB.setOnClickListener {
            findNavController().navigate(R.id.action_titleScreenFragment_to_threeBoardFragment)
            viewModel.setGameMode(2)
        }

        darkMode.setOnClickListener{

            if (!isNightMode(view)) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                setMode(view, "Night")
                darkMode.text = resources.getString(R.string.LightMode)
            }
            else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                setMode(view, "Light")
                darkMode.text = resources.getString(R.string.DarkMode)
            }

        }


    }

    private fun setMode(view: View, mode: String){
        val sharedP = view.context.getSharedPreferences(resources.getString(R.string.package_name), Context.MODE_PRIVATE)
        val editMode = sharedP.edit()
        editMode.putString("mode", mode)
        editMode.apply()


    }

    private fun isNightMode(view: View): Boolean{
        val sharedP = view.context.getSharedPreferences(resources.getString(R.string.package_name), Context.MODE_PRIVATE)
        val savedDate = sharedP.getString("mode", "")
        return !("" == savedDate || "Light" == savedDate)

    }
}
package com.kypcop.notes.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.kypcop.notes.MainActivity
import com.kypcop.notes.R

class MainFragment : Fragment() {

    lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, LinearLayout(mainActivity), true)
        val newNoteButton = view.findViewById<Button>(R.id.new_note_btn)
        val seeNotesButton = view.findViewById<Button>(R.id.see_notes_btn)

        newNoteButton.setOnClickListener {
            mainActivity.changeFragment(NewNoteFragment(), true, false)
        }

        seeNotesButton.setOnClickListener {
            mainActivity.changeFragment(SeeNotesFragment(), true, false)
        }

        return view
    }
}
package com.kypcop.notes.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.kypcop.notes.MainActivity
import com.kypcop.notes.Tools
import com.kypcop.notes.Note
import com.kypcop.notes.R

class NewNoteFragment : Fragment() {

    private var editing = false
    lateinit var mainActivity: MainActivity
    lateinit var args: Bundle
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_main, LinearLayout(mainActivity), true)
//        val newNoteButton = view.findViewById<Button>(R.id.new_note_btn)
//        val seeNotesButton = view.findViewById<Button>(R.id.see_notes_btn)
//
//        newNoteButton.setOnClickListener {
//            mainActivity.changeFragment(NewNoteFragment(), true, false)
//        }
//
//        seeNotesButton.setOnClickListener {
//            mainActivity.changeFragment(MainFragment(), true, false)
//        }
//
//        return view
//    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
        args = requireArguments()
        editing = args.getString(MODE) == EDIT
    }

    companion object {
        const val EDIT = "edit"
        const val CREATE = "create"
        const val MODE = "mode"
//        fun editNote(index: Int, note: Note): NewNoteFragment {
//            return NewNoteFragment().apply {
//                arguments = Bundle().apply {
//                    putString(Note.TITLE, note.title)
//                    putString(Note.BODY, note.body)
//                    putInt(Note.INDEX, index)
//                    editing = true
//                }
//            }
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_new_note, LinearLayout(activity), false)
        val noteTitleEt = view.findViewById<EditText>(R.id.note_title_et)
        val noteEt = view.findViewById<EditText>(R.id.note_et)
        if (editing) {
            noteTitleEt.setText(args.getString(Note.TITLE))
            noteEt.setText(args.getString(Note.BODY))
        }
        view.findViewById<Button>(R.id.ready_btn).setOnClickListener {
            if (editing) {
                Tools.editNote(
                    args.getInt(Note.INDEX), Note(
                        noteTitleEt.text.toString(),
                        noteEt.text.toString()
                    )
                )
            } else Tools.saveNote(Note(noteTitleEt.text.toString(), noteEt.text.toString()))

            activity?.onBackPressed()
        }
        return view
    }
}
package com.kypcop.notes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.kypcop.notes.Memory
import com.kypcop.notes.Note
import com.kypcop.notes.R

class NewNoteFragment : Fragment() {

    private var editing = false

    companion object {
        fun editNote(index: Int, note: Note): NewNoteFragment {
            return NewNoteFragment().apply {
                arguments = Bundle().apply {
                    putString(Note.TITLE, note.title)
                    putString(Note.BODY, note.body)
                    putInt(Note.INDEX, index)
                    editing = true
                }
            }
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_new_note, LinearLayout(activity), false)
        val noteTitleEt = view.findViewById<EditText>(R.id.note_title_et)
        val noteEt = view.findViewById<EditText>(R.id.note_et)
        if(editing){
            noteTitleEt.setText(arguments?.getString(Note.TITLE) ?: "")
            noteEt.setText(arguments?.getString(Note.BODY ?: ""))
        }
        view.findViewById<Button>(R.id.ready_btn).setOnClickListener {
            if(editing){
                Memory.editNote(arguments!!.getInt(Note.INDEX), Note(
                        noteTitleEt.text.toString(),
                        noteEt.text.toString()
                ))
            }else Memory.saveNote(Note(noteTitleEt.text.toString(), noteEt.text.toString()))

            activity?.onBackPressed()
        }
        return view
    }
}
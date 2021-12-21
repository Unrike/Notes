package com.kypcop.notes.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kypcop.notes.*
import com.kypcop.notes.fragments.NewNoteFragment.Companion.CREATE
import com.kypcop.notes.fragments.NewNoteFragment.Companion.EDIT
import com.kypcop.notes.fragments.NewNoteFragment.Companion.MODE

class MainFragment : Fragment() {

    private lateinit var mainActivity: MainActivity
    lateinit var adapter: NoteAdapter
    val notes = Tools.noteList

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_main, LinearLayout(activity), false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val notesRecyclerView = view.findViewById<RecyclerView>(R.id.notes_rv)
        adapter = NoteAdapter(requireActivity(), notes)
        notesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        notesRecyclerView.adapter = adapter
        registerForContextMenu(notesRecyclerView)
        val createNoteFab = view.findViewById<FloatingActionButton>(R.id.create_note_fab).apply {
            setOnClickListener {
                Navigation.findNavController(it).navigate(R.id.newNoteFragment, Bundle().apply {
                    putString(MODE, CREATE)
                })
            }
        }
        Tools.registerNoteObserver(object : Note.Observer {
            override fun onNoteEdited(index: Int, note: Note) {
                adapter.notifyItemChanged(index)
            }

            override fun onNoteDeleted(index: Int, note: Note) {
                adapter.notifyItemRemoved(index)
            }

            override fun onNoteCreated(index: Int, note: Note) {
                adapter.notifyItemInserted(index)
            }

        })
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater: MenuInflater = requireActivity().menuInflater
        inflater.inflate(R.menu.menu_delete, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val pos = adapter.contextMenuPosition
        when (item.itemId) {
            R.id.delete -> {
                Tools.deleteNote(pos)
            }
            R.id.edit -> {
                findNavController(this).navigate(R.id.newNoteFragment, Bundle().apply {
                    putString(MODE, EDIT)
                    putString(Note.TITLE, notes[pos].title)
                    putString(Note.BODY, notes[pos].body)
                    putInt(Note.INDEX, pos)
                })
            }
        }
        return true
    }
}
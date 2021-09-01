package com.kypcop.notes.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.kypcop.notes.MainActivity
import com.kypcop.notes.Memory
import com.kypcop.notes.Note
import com.kypcop.notes.R

class SeeNotesFragment : Fragment() {

    lateinit var mainActivity: MainActivity
    lateinit var adapter: ArrayAdapter<Note>
    val notes = Memory.notelist

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_see_notes, LinearLayout(activity), false)

        val notesLv = view.findViewById<ListView>(R.id.notes_lv)
        adapter = object : ArrayAdapter<Note>(activity!!, R.layout.listview_notes, notes) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = convertView ?: LayoutInflater.from(activity).inflate(R.layout.listview_notes, LinearLayout(activity))
                val titleTv = view.findViewById<TextView>(R.id.title_tv)
                val bodyTv = view.findViewById<TextView>(R.id.body_tv)
                titleTv.text = notes[position].title
                bodyTv.text = if(notes[position].body.length > 18){
                    notes[position].body.substring(0, 15) + "..."
                } else notes[position].body.substring(0)

                return view
            }
        }
        notesLv.adapter = adapter
        registerForContextMenu(notesLv)
        notesLv.setOnItemClickListener { parent, view, position, id ->
            val note = notes[position]
            AlertDialog.Builder(activity)
                    .setTitle(note.title)
                    .setMessage(note.body)
                    .setCancelable(true)
                    .setNeutralButton("Close") { dialog, which ->
                        dialog.dismiss()
                    }.create().show()
        }
        return view
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater: MenuInflater = activity!!.menuInflater
        inflater.inflate(R.menu.menu_delete, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val pos = (item.menuInfo as AdapterView.AdapterContextMenuInfo).position
        when(item.itemId){
            R.id.delete -> Memory.deleteNote(pos)
            R.id.edit -> {
                mainActivity.changeFragment(NewNoteFragment.editNote(pos, notes[pos]),
                        true, false)
            }
        }
        adapter.notifyDataSetChanged()
        return true
    }
}
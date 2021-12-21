package com.kypcop.notes

import android.app.Activity
import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(private val activity: Activity, private var notes: List<Note>) :
    RecyclerView.Adapter<NoteAdapter.NoteHolder>() {

    var contextMenuPosition = 0

    class NoteHolder(private val activity: Activity, val view: View) :
        RecyclerView.ViewHolder(view),
        View.OnCreateContextMenuListener {

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            activity.menuInflater.inflate(R.menu.menu_delete, menu)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        return NoteHolder(
            activity,
            LayoutInflater.from(activity)
                .inflate(R.layout.recyclerview_notes, LinearLayout(activity), false)
        )
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val note = notes[position]
        val titleTv = holder.view.findViewById<TextView>(R.id.title_tv)
        val bodyTv = holder.view.findViewById<TextView>(R.id.body_tv)
        titleTv.text = note.title
        bodyTv.text = note.body
        holder.view.setOnClickListener {
            Navigation.findNavController(holder.view)
                .navigate(R.id.showNoteDialogFragment, Bundle().apply {
                    putString(Note.TITLE, note.title)
                    putString(Note.BODY, note.body)
                })
        }
        holder.view.setOnLongClickListener {
            contextMenuPosition = position
            false
        }
    }

    override fun getItemCount(): Int = notes.size

}
package com.kypcop.notes

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

object Tools {


    private const val PREF = "pref"
    private const val LIST = "list"

    lateinit var sharedPref: SharedPreferences
    lateinit var noteList: MutableList<Note>

    private val noteObservers = mutableListOf<Note.Observer>()

    fun initPref(context: Context) {
        sharedPref = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
        noteList = if (sharedPref.getString(LIST, "") != "") {
            ArrayList(Note.deserializeArray(sharedPref.getString(LIST, "")!!).toList())
        } else {
            ArrayList()
        }
        Log.i("Tools", "note list: ${Note.serializeArray(noteList.toTypedArray())}")


    }

    fun saveNote(note: Note) {
        Log.i("Tools", "saving note")
        noteList.add(note)
        update()
        notifyObserversNoteCreated(noteList.size - 1, note)
    }

    fun deleteNote(index: Int) {
        val note = noteList.removeAt(index)
        update()
        notifyObserversNoteDeleted(index, note)
    }

    fun editNote(index: Int, note: Note) {
        noteList[index] = note
        update()
        notifyObserversNoteEdited(index, note)
    }

    private fun update() {
        val editor = sharedPref.edit()
        editor.putString(LIST, Note.serializeArray(noteList.toTypedArray()))
        editor.apply()
    }

    fun registerNoteObserver(observer: Note.Observer) {
        noteObservers.add(observer)
    }

    private fun notifyObserversNoteDeleted(index: Int, note: Note) {
        noteObservers.forEach { observer ->
            observer.onNoteDeleted(index, note)
        }
    }

    private fun notifyObserversNoteEdited(index: Int, note: Note) {
        noteObservers.forEach { observer ->
            observer.onNoteEdited(index, note)
        }
    }

    private fun notifyObserversNoteCreated(index: Int, note: Note) {
        noteObservers.forEach { observer ->
            observer.onNoteCreated(index, note)
        }
    }

}
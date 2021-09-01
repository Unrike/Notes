package com.kypcop.notes

import android.content.Context
import android.content.SharedPreferences

object Memory {

    private const val PREF = "pref"
    private const val LIST = "list"
    var counter = 0


    lateinit var sharedPref: SharedPreferences
    lateinit var notelist: ArrayList<Note>

    fun initPref(context: Context) {
        sharedPref = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
        notelist = if (sharedPref.getString(LIST, "") != "") {
            ArrayList(Note.deserializeArray(sharedPref.getString(LIST, "")!!).toList())
        } else {
            ArrayList()
        }


    }

    fun saveNote(note: Note) {
        notelist.add(note)
        update()
    }

    fun deleteNote(index: Int) {
        notelist.removeAt(index)
        update()
    }

    fun editNote(index: Int, note: Note){
        notelist[index] = note
        update()
    }

    private fun update() {
        val editor = sharedPref.edit()
        editor.putString(LIST, Note.serializeArray(notelist.toTypedArray()))
        editor.apply()
    }

}
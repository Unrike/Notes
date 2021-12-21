package com.kypcop.notes

import android.util.Log

class Note(val title: String, val body: String) {


    fun serialize(): String {
        return "{${title}}:{${body}}"
    }

    companion object {

        private const val TAG = "Note"
        const val TITLE = "Title"
        const val BODY = "Body"
        const val INDEX = "Index"


        fun deserialize(string: String): Note {
            return Note(
                string.substring(1, string.indexOf("}:{")),
                string.substring(string.indexOf("}:{") + 3, string.length - 1)
            )
        }

        fun serializeArray(notes: Array<Note>): String {
            var string = ""
            for (note in notes) {
                string += "${note.serialize()};"
            }
            return string + "end"
        }

        fun deserializeArray(str: String): Array<Note> {
            var string = str
            val list = ArrayList<Note>()
            while (string.contains(";")) {
                val serNote = string.substring(0, string.indexOf(";"))
                list.add(deserialize(serNote))
                string = string.substring(string.indexOf(";") + 1)
                Log.i(TAG, "Note: $serNote")
                Log.i(TAG, "Array: $string")
                Log.i(TAG, "\n")
                if (string == "end") {
                    break
                }
            }
            return list.toTypedArray()
        }
    }

    interface Observer {
        fun onNoteEdited(index: Int, note: Note)

        fun onNoteDeleted(index: Int, note: Note)

        fun onNoteCreated(index: Int, note: Note)
    }


}
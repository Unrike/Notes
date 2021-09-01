package com.kypcop.notes.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.kypcop.notes.R

class ShowNoteDialogFragment : DialogFragment() {

    companion object{

        private const val TITLE = "title"
        private const val BODY = "body"

        fun newInstance(title: String, body: String): ShowNoteDialogFragment{
            return ShowNoteDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(TITLE, title)
                    putString(BODY, body)
                }
            }
        }
    }

    private lateinit var title: String
    private lateinit var body: String

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(arguments != null){
            title = arguments!!.getString(TITLE) ?: ""
            body = arguments!!.getString(BODY) ?: ""
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_show_note, LinearLayout(activity), false)
        val titleTv = view.findViewById<TextView>(R.id.title_tv)
        val bodyTv = view.findViewById<TextView>(R.id.body_tv)
        view.findViewById<Button>(R.id.close_btn).setOnClickListener { dismiss() }
        titleTv.text = title
        bodyTv.text = body

        return view
    }
}
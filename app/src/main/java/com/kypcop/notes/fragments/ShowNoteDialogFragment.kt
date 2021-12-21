package com.kypcop.notes.fragments

import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.kypcop.notes.Note.Companion.BODY
import com.kypcop.notes.Note.Companion.TITLE
import com.kypcop.notes.R

class ShowNoteDialogFragment : DialogFragment() {


    private lateinit var title: String
    private lateinit var body: String

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (arguments != null) {
            title = requireArguments().getString(TITLE) ?: ""
            body = requireArguments().getString(BODY) ?: ""
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.dialog_show_note, LinearLayout(activity), false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setWidthPercent(70)
        val titleTv = view.findViewById<TextView>(R.id.title_tv)
        val bodyTv = view.findViewById<TextView>(R.id.body_tv)
        view.findViewById<Button>(R.id.close_btn).setOnClickListener { dismiss() }
        titleTv.text = title
        bodyTv.text = body

    }

    override fun onResume() {
        super.onResume()
        setWidthPercent(70)
    }

    /**
     * Call this method (in onActivityCreated or later) to set
     * the width of the dialog to a percentage of the current
     * screen width.
     */
    fun setWidthPercent(percentage: Int) {
        val percent = percentage.toFloat() / 100
        val dm = Resources.getSystem().displayMetrics
        val rect = dm.run { Rect(0, 0, widthPixels, heightPixels) }
        val percentWidth = rect.width() * percent
        dialog?.window?.setLayout(percentWidth.toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
    }

}
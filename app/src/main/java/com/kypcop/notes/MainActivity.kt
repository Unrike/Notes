package com.kypcop.notes

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.kypcop.notes.fragments.MainFragment


class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Memory.initPref(this)
        changeFragment(MainFragment(), false, false)

    }


    fun changeFragment(frag: Fragment, saveInBackStack: Boolean, animate: Boolean) {
        val backStateName = (frag as Any).javaClass.name
        try {
            val manager: FragmentManager = supportFragmentManager
            val fragmentPopped: Boolean = manager.popBackStackImmediate(backStateName, 0)
            if (!fragmentPopped && manager.findFragmentByTag(backStateName) == null) {
                //fragment not in back stack, create it.
                val transaction: FragmentTransaction = manager.beginTransaction()
                if (animate) {
                    Log.d(TAG, "Change Fragment: animate")
                    //transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
                }
                transaction.replace(R.id.wrapper_frame_layout, frag, backStateName)
                if (saveInBackStack) {
                    Log.d(TAG, "Change Fragment: addToBackTack $backStateName")
                    transaction.addToBackStack(backStateName)
                } else {
                    Log.d(TAG, "Change Fragment: NO addToBackTack")
                }
                transaction.commit()
            } else {
                // custom effect if fragment is already instanciated
            }
        } catch (exception: IllegalStateException) {
            Log.w(
                    TAG,
                    "Unable to commit fragment, could be activity as been killed in background. $exception"
            )
        }
    }
}
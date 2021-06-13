package com.lifetimelearner.quizapp.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.hanks.htextview.base.AnimationListener
import com.hanks.htextview.base.HTextView
import com.lifetimelearner.quizapp.R
import com.lifetimelearner.quizapp.model.Quotes

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        val hTextView = view.findViewById<HTextView>(R.id.main_quote);
        hTextView.animateText(Quotes.getTodayQuote())
        hTextView.setAnimationListener {
            /*
            Thread.sleep(5000)
            hTextView.animateText(getString(R.string.quote))
            hTextView.setProgress(0f)
             */
        }
        return view
    }


}
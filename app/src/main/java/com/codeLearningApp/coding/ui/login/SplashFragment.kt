package com.codeLearningApp.coding.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hanks.htextview.base.HTextView
import com.codeLearningApp.coding.R
import com.codeLearningApp.coding.utils.GlobalData.getTodayQuote

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_splash, container, false)

        val quote = getTodayQuote()
        val textView = view.findViewById<HTextView>(R.id.main_quote)
        textView.text = quote

        return view

    }

}
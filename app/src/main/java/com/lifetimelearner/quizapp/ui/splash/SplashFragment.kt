package com.lifetimelearner.quizapp.ui.splash

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.hanks.htextview.base.HTextView
import com.lifetimelearner.quizapp.R
import com.lifetimelearner.quizapp.ui.question.SubQuestionActivity
import com.lifetimelearner.quizapp.utils.GlobalData

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_splash, container, false)

        return view

    }

}
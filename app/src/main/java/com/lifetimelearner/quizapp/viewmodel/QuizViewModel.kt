package com.lifetimelearner.quizapp.viewmodel

import android.app.Application
import android.os.Handler
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.lifetimelearner.quizapp.repository.QuizRepository

class QuizViewModel(app: Application): AndroidViewModel(app) {

    val repository = QuizRepository(app)

}
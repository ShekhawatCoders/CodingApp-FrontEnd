package com.lifetimelearner.quizapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.lifetimelearner.quizapp.repository.QuestionRepository

class QuestionViewModel(app: Application): AndroidViewModel(app) {

    val repository = QuestionRepository(app)


}
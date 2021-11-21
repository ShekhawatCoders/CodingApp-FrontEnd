package com.lifetimelearner.quizapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.lifetimelearner.quizapp.repository.CodeRepository

class CodeViewModel(app: Application): AndroidViewModel(app) {

    val repository = CodeRepository(app)

}
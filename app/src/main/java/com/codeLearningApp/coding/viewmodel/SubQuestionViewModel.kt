package com.codeLearningApp.coding.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.codeLearningApp.coding.repository.SubQuestionRepository

class SubQuestionViewModel(app: Application): AndroidViewModel(app) {

    val repository = SubQuestionRepository(app)


}
package com.codeLearningApp.coding.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.codeLearningApp.coding.repository.QbjQuestionRepository

class ObjQuestionViewModel(app: Application): AndroidViewModel(app) {

    val repository = QbjQuestionRepository(app)

}